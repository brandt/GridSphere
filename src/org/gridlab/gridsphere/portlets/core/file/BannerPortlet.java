/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets.core.file;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.provider.event.FormEvent;
import org.gridlab.gridsphere.provider.portlet.ActionPortlet;
import org.gridlab.gridsphere.provider.portletui.beans.FrameBean;
import org.gridlab.gridsphere.provider.portletui.beans.TextFieldBean;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;
import org.gridlab.gridsphere.services.core.secdir.SecureDirectoryService;
import org.gridlab.gridsphere.services.core.secdir.ResourceInfo;
import org.apache.oro.text.perl.Perl5Util;

import javax.servlet.UnavailableException;
import java.util.Map;
import java.util.HashMap;
import java.io.*;

public class BannerPortlet extends ActionPortlet {

    private SecureDirectoryService secureDirectoryService = null;

    private static final String CONFIGURE_JSP = "banner/configure.jsp";
    private static final String HELP_JSP = "banner/help.jsp";
    private static final String EDIT_JSP = "banner/edit.jsp";

    private static final String rootDir = "commander";
    private static final int BUFFER_SIZE = 4 * 1024;
    private static Map userDatas = java.util.Collections.synchronizedMap(new HashMap());

    private static final String TITLE = "title";
    private static final String FILE = "file";

    private Perl5Util util = new Perl5Util();

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        try {
            secureDirectoryService = (SecureDirectoryService) config.getContext().getService(SecureDirectoryService.class);
        } catch (PortletServiceException e) {
            log.error("Unable to initialize FileManagerService", e);
        }
        DEFAULT_VIEW_PAGE = "doViewFile";
        DEFAULT_EDIT_PAGE = "doEditViewFile";
        DEFAULT_CONFIGURE_PAGE = "doConfigureViewFile";

        DEFAULT_HELP_PAGE = HELP_JSP;
    }

    public String getTitle() {
        String defaultTitle = this.getPortletSettings().getAttribute(TITLE);
        if (defaultTitle == null) defaultTitle = "";
        return defaultTitle;
    }

    public String getFileURL() {
        String defaultFileURL = this.getPortletSettings().getAttribute(FILE);
        if (defaultFileURL == null) defaultFileURL = "";
        return defaultFileURL;
    }

    /**
     * Configure mode allows the displayed file to be set in PortletSettings
     *
     * @param event
     * @throws PortletException
     */
    public void doConfigureViewFile(FormEvent event) throws PortletException {
        PortletRequest req = event.getPortletRequest();
        checkAdminRole(event);
        TextFieldBean displayTitle = event.getTextFieldBean("displayTitle");
        displayTitle.setValue(getTitle());

        TextFieldBean displayFile = event.getTextFieldBean("displayFile");
        displayFile.setValue(getFileURL());
        setNextState(req, CONFIGURE_JSP);
    }

    public void setConfigureDisplayFile(FormEvent event) throws PortletException {
        log.debug("in BannerPortlet: setConfigureDisplayFile");
        checkAdminRole(event);
        PortletRequest req = event.getPortletRequest();
        TextFieldBean displayFile = event.getTextFieldBean("displayFile");
        String defaultFileURL = displayFile.getValue();
        getPortletSettings().setAttribute(FILE, defaultFileURL);

        TextFieldBean displayTitle = event.getTextFieldBean("displayTitle");
        String defaultTitle = displayTitle.getValue();

        getPortletSettings().setAttribute(TITLE, defaultTitle);
        FrameBean alert = event.getFrameBean("alert");
        try {
            getPortletSettings().store();
            alert.setValue(this.getLocalizedText(req, "BANNER_CONFIGURE"));
        } catch (IOException e) {
            log.error("Unable to save portlet settings", e);
            alert.setValue(this.getLocalizedText(req, "BANNER_FAILURE"));
            alert.setStyle("error");
        }
        setNextState(req, CONFIGURE_JSP);
    }

    public void setEditDisplayFile(FormEvent event) throws PortletException {
        log.debug("in BannerPortlet: setEditDisplayFile");
        checkUserRole(event);
        FrameBean alert = event.getFrameBean("alert");
        PortletRequest request = event.getPortletRequest();

        String fileNumberParam = request.getParameter("fileNumber");

        User user = request.getUser();
        UserData userData = (UserData) userDatas.get(user.getID());
        String fileURL = null;

        try {
            int fileNumber = Integer.parseInt(fileNumberParam);
            ResourceInfo[] resources = userData.getLeftResourceList();
            fileURL = userData.getPath("left") + resources[fileNumber].getResource();
        } catch (Exception e) {
        }

        PortletData data = request.getData();
        if (fileURL == null) {
            log.error("did not select a file");
            alert.setValue(this.getLocalizedText(request, "BANNER_NOFILE_SELECTED"));
            alert.setStyle("error");
        } else
            data.setAttribute(FILE, fileURL);

        TextFieldBean displayTitle = event.getTextFieldBean("displayTitle");
        String title = displayTitle.getValue();
        if (title != null && !title.equals("")) {
            data.setAttribute(TITLE, title);
        }
        try {
            data.store();
            alert.setValue(this.getLocalizedText(request, "BANNER_CONFIGURE"));
        } catch (IOException e) {
            log.error("Unable to save portlet data");
            alert.setValue(this.getLocalizedText(request, "BANNER_FAILURE"));
            alert.setStyle("error");
        }

        setNextState(request, EDIT_JSP);
    }

    /**
     * Edit mode allows the displayed file to be set in PortletData
     *
     * @param event
     * @throws PortletException
     */
    public void doEditViewFile(FormEvent event) throws PortletException {
        checkUserRole(event);
        FrameBean alert = event.getFrameBean("errorFrame");
        User user = event.getPortletRequest().getUser();
        UserData userData = (UserData) userDatas.get(user.getID());

        if (userData == null) {
            userData = new UserData();
            userDatas.put(user.getID(), userData);
        }

        PortletData data = event.getPortletRequest().getData();
        TextBean fileName = event.getTextBean("fileName");
        fileName.setValue(data.getAttribute(FILE));
        TextBean title = event.getTextBean("title");
        title.setValue(data.getAttribute(TITLE));

        readDirectories(event, userData);
        event.getPortletRequest().setAttribute("userData", userData);

        if (!userData.getCorrect().booleanValue()) {
            alert.setKey("COMMANDER_ERROR_INIT");
            alert.setStyle(FrameBean.ERROR_TYPE);
        }

        setNextState(event.getPortletRequest(), EDIT_JSP);
    }

    public void doViewFile(FormEvent event) throws PortletException {
        log.debug("in BannerPortlet: doViewFile");
        PortletRequest request = event.getPortletRequest();
        PortletResponse response = event.getPortletResponse();
        User user = request.getUser();
        String title = getTitle();
        String fileURL = null;
        boolean userFile = false;
        if (!(user instanceof GuestUser)) {
            PortletData data = request.getData();
            fileURL = data.getAttribute(FILE);
            userFile = true;
            // if user hasn't configured banner, show them help
            /*
            if (fileURL == null) {
                setNextState(request, HELP_JSP);
                return;
            }
            */
        }

        if (fileURL == null) {
            fileURL = getFileURL();
            userFile = false;
        }

        //setNextState(request, fileURL);
        PrintWriter out = null;
        try {
            out = response.getWriter();
            if (fileURL.equals("")) {
                out.println(this.getLocalizedText(request, "BANNER_FILE_NOTFOUND"));
            } else {
                if (userFile) {
                    File file = secureDirectoryService.getFile(request.getUser(), rootDir, fileURL);
                    if (file == null)
                        throw new IOException("Unable to get " + fileURL + " form secure directory service.");
                    FileReader fileReader = new FileReader(file);
                    rewrite(fileReader, out);
                } else
                    portletConfig.getContext().include(fileURL, request, response);
            }
        } catch (IOException e) {
            out.println(this.getLocalizedText(request, "BANNER_FILE_NOTFOUND") + " " + fileURL + "!");
            log.error("Unable to find file: " + fileURL);
        }
    }

    public void cancelEditFile(FormEvent event) throws PortletException {
        log.debug("in BannerPortlet: cancelEdit");
        PortletRequest req = event.getPortletRequest();
        req.setMode(Portlet.Mode.VIEW);
        setNextState(event.getPortletRequest(), DEFAULT_VIEW_PAGE);
    }

    public void doTitle(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        PrintWriter out = response.getWriter();
        if (request.getMode() == Portlet.Mode.VIEW) {
            User user = request.getUser();
            String title = null;
            if (!(user instanceof GuestUser)) {
                PortletData data = request.getData();
                title = data.getAttribute(TITLE);
                if (title == null) title = this.getLocalizedText(request, "BANNER_HELP");
            } else {
                title = getTitle();
            }
            out.println(title);
        } else if (request.getMode() == Portlet.Mode.HELP) {
            out.println(this.getLocalizedText(request, "BANNER_HELP"));
        } else {
            out.println(this.getLocalizedText(request, "BANNER_EDIT"));
        }
    }

    public void changeDir(FormEvent event) throws PortletException {
        PortletRequest request = event.getPortletRequest();
        String newDirParam = request.getParameter("newDir");

        User user = request.getUser();
        UserData userData = (UserData) userDatas.get(user.getID());

        String newDir = userData.getPath("left");
        if (newDirParam.equals("..")) {
            newDir = util.substitute("s!/[^/]+/$!/!", newDir);
        } else {
            newDir += newDirParam + "/";
        }
        userData.setPath("left", newDir);
        readDirectories(event, userData);
        setNextState(request, EDIT_JSP);
    }

    public void gotoRootDirLeft(FormEvent event) throws PortletException {
        PortletRequest request = event.getPortletRequest();

        User user = request.getUser();
        UserData userData = (UserData) userDatas.get(user.getID());

        userData.setPath("left", "/");
        readDirectories(event, userData);
        setNextState(request, EDIT_JSP);
    }

    private void readDirectories(FormEvent event, UserData userData) {
        PortletResponse response = event.getPortletResponse();
        User user = event.getPortletRequest().getUser();
        if (secureDirectoryService.appHasDirectory(user, rootDir, true)) {
            String path = userData.getPath("left");
            ResourceInfo[] resourceList = secureDirectoryService.getResourceList(user, rootDir, path);

            String[] URIs = null;

            if (resourceList != null) {
                URIs = new String[resourceList.length];
                for (int i = 0; i < resourceList.length; ++i) {
                    if (resourceList[i].isDirectory()) {
                        PortletURI uri = response.createURI();
                        uri.addAction("changeDir");
                        uri.addParameter("newDir", resourceList[i].getResource());
                        URIs[i] = uri.toString();
                    }
                }
            }
            userData.setLeftResourceList(resourceList);
            userData.setLeftURIs(URIs);
            userData.setCorrect(new Boolean(true));
        } else {
            userData.setCorrect(new Boolean(false));
        }
    }

    private void rewrite(InputStreamReader input, Writer output) throws IOException {
        int numRead = 0;
        char[] buf = new char[BUFFER_SIZE];
        while (!((numRead = input.read(buf)) < 0)) {
            output.write(buf, 0, numRead);
        }
    }

}
