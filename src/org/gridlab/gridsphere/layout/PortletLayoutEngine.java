/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.GuestUser;
import org.gridlab.gridsphere.portletcontainer.GridSphereProperties;

import javax.servlet.ServletConfig;
import java.io.*;
import java.util.Map;
import java.util.Hashtable;

public class PortletLayoutEngine {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(PortletLayoutEngine.class);
    private static PortletLayoutEngine instance = null;
    private PortletConfig config = null;

    private PortletLayoutDescriptor templateLayout = null;
    private String templateLayoutPath, layoutMappingPath;
    private PortletLayoutDescriptor guestLayout = null;
    private String guestLayoutPath = null;

    private boolean reload = false;
    private String error = "";

    private Map userLayouts = new Hashtable();

    public PortletLayoutEngine(PortletConfig config) throws PortletLayoutDescriptorException {
        this.config = config;
        // load in template guest layout.xml file
        String appRoot = config.getServletContext().getRealPath("") + "/";
        String layoutMappingFile = config.getInitParameter("layout-mapping.xml");
        layoutMappingPath = appRoot + layoutMappingFile;

        //String layoutConfigFile = config.getInitParameter("guest-layout.xml");
        String layoutConfigFile = config.getInitParameter("layout.xml");
        if ((layoutConfigFile == null) || (layoutMappingFile == null)) {
            throw new PortletLayoutDescriptorException("Unable to get guest layout info from web.xml");
        }
        guestLayoutPath = appRoot + layoutConfigFile;
        layoutMappingPath = appRoot + layoutMappingFile;
        guestLayout = new PortletLayoutDescriptor(guestLayoutPath, layoutMappingPath);
    }

    private static synchronized void doSync() {}

    public static PortletLayoutEngine getInstance(PortletConfig config) throws PortletLayoutDescriptorException {
        if (instance == null) {
            doSync();
            instance = new PortletLayoutEngine(config);
        }
        return instance;
    }

    public void setAutomaticReload(boolean reload) {
        this.reload = reload;
    }

    public boolean getAutomaticReload() {
        return reload;
    }

    public void doRender(PortletRequest req, PortletResponse res) throws PortletLayoutException {
        log.debug("in doRender()");

        PortletContainer pc = null;

        // Check for user
        User user = req.getUser();

        // if user is guest then use guest template
        if (user instanceof GuestUser) {
            pc = guestLayout.getPortletContainer();

        // Check if we have user's layout already
        } else if (userLayouts.containsKey(user)) {

            pc = (PortletContainer)userLayouts.get(user);

        // If not we try to load it in (creating new one if necessary)
        } else {

            try {
                PortletLayoutDescriptor pld = loadUserLayout(user);
                pc = pld.getPortletContainer();
            } catch (IOException e) {
                log.error("Unable to loadUserLayout for user: " + user, e);
                doRenderError(req, res, e);
            }
            userLayouts.put(user, pc);
        }

        // XXX: How do we signal a user has logged out so we can userLayouts.remove(user)???

        // for now just render
        if (reload) {
            //layout.reload();
        }

        try {
            pc.doRender(config.getContext(), req, res);
        } catch (IOException e) {
            error = e.getMessage();
            log.error("Caught IOException: ", e);
            throw new PortletLayoutException("Caught IOException", e);
        }
    }

    public void doRenderError(PortletRequest req, PortletResponse res, Throwable t) {
        PrintWriter out = null;
        try {
            out = res.getWriter();
        } catch (IOException e) {
            log.error("in doRenderError: ", e);
        }
        out.println("<h>Portlet Layout Engine unable to render!</h>");
        out.println("<b>" + error + "</b>");
    }

    protected PortletLayoutDescriptor loadUserLayout(User user) throws PortletLayoutDescriptorException, IOException {
        this.config = config;
        // load in layout.xml file
        String appRoot = config.getServletContext().getRealPath("") + "/";
        String userLayoutDir = config.getInitParameter("user-layouts");
        String layoutMappingFile = config.getInitParameter("layout-mapping.xml");

        if ((userLayoutDir == null) || (layoutMappingFile == null)) {
            throw new PortletLayoutDescriptorException("Unable to get user layout directory info from web.xml. Please specify user-layouts-dir in web.xml.");
        }
        File layDir = new File(appRoot + userLayoutDir);
        if (!layDir.exists()) {
            layDir.mkdir();
        }

        String layoutPath = appRoot + userLayoutDir + "/" + user.getID();
        File f = new File(layoutPath);
        // if no layout file exists for user, make new one from template
        if (!f.exists()) {
            f.createNewFile();
            copyFile(new File(guestLayoutPath), f);
        }

        PortletLayoutDescriptor userLayout = new PortletLayoutDescriptor(layoutPath, layoutMappingPath);

        return userLayout;
    }

    protected void copyFile(File oldFile, File newFile) throws IOException {
        // Destination and streams
        log.info("in copyFile(): oldFile: " + oldFile.getAbsolutePath() + " newFile: " + newFile.getCanonicalPath());
        FileInputStream fis = new FileInputStream(oldFile);
        FileOutputStream fos = new FileOutputStream(newFile);

        // Amount of data to copy
        long fileLength = oldFile.length();
        byte[] bytes = new byte[1024]; // 1K at a time
        int length = 0;
        long totalLength = 0;
        while (length > -1) {
            length = fis.read(bytes);
            if (length > 0) {
                fos.write(bytes, 0, length);
                totalLength += length;
            }
        }
        // Test that we copied all the data
        if (fileLength != totalLength) {
            throw new IOException("File copy size missmatch");
        }
        fos.flush();
        fos.close();
        fis.close();
    }

}
