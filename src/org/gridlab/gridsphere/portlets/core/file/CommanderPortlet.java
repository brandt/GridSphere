/**
 * @author <a href="mailto:tkucz@icis.pcz.pl">Tomasz Kuczynski</a>
 * @version 0.97 2004/05/17
 */
package org.gridlab.gridsphere.portlets.core.file;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.services.core.secdir.SecureDirectoryService;
import org.gridlab.gridsphere.services.core.secdir.ResourceInfo;
import org.gridlab.gridsphere.provider.portlet.ActionPortlet;
import org.gridlab.gridsphere.provider.event.FormEvent;
import org.gridlab.gridsphere.provider.portletui.beans.FrameBean;
import org.gridlab.gridsphere.provider.portletui.beans.FileInputBean;
import org.gridlab.gridsphere.provider.portletui.beans.TextFieldBean;
import org.apache.oro.text.perl.Perl5Util;

import javax.servlet.UnavailableException;
import java.io.StringBufferInputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class CommanderPortlet extends ActionPortlet {
    public static final String VIEW_JSP = "commander/explorer.jsp";
    public static final String EDIT_JSP = "commander/editfile.jsp";
    public static final String HELP_JSP = "commander/help.jsp";
    public static final String rootDir = "commander";

    private Perl5Util util = new Perl5Util();
    private static Map userDatas = java.util.Collections.synchronizedMap(new HashMap());

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        DEFAULT_VIEW_PAGE = "showExplorer";
        DEFAULT_HELP_PAGE = HELP_JSP;
    }

    public void showExplorer(FormEvent event) throws PortletException {
        FrameBean frame = event.getFrameBean("errorFrame");
        User user = event.getPortletRequest().getUser();
        UserData userData = (UserData) userDatas.get(user.getID());

        if (userData == null) {
            userData = new UserData();
            readDirectories(event, userData);
            userDatas.put(user.getID(), userData);
        }
        setAttributes(event);

        if (userData.getState().equals("explore")) {
            if (!userData.getCorrect().booleanValue()) {
                frame.setKey("COMMANDER_ERROR_INIT");
                frame.setStyle(FrameBean.ERROR_TYPE);
            }
            setNextState(event.getPortletRequest(), VIEW_JSP);
        } else {
            setNextState(event.getPortletRequest(), EDIT_JSP);
        }
    }

    public void changeDir(FormEvent event) throws PortletException {
        PortletRequest request = event.getPortletRequest();
        String newDirParam = request.getParameter("newDir");
        String sideParam = request.getParameter("side");

        User user = request.getUser();
        UserData userData = (UserData) userDatas.get(user.getID());

        String newDir = userData.getPath(sideParam);
        if (newDirParam.equals("..")) {
            newDir = util.substitute("s!/[^/]+/$!/!", newDir);
        } else {
            newDir += newDirParam + "/";
        }
        userData.setPath(sideParam, newDir);
        readDirectories(event, userData);
    }

    public void uploadFileLeft(FormEvent event) throws PortletException {
        PortletRequest request = event.getPortletRequest();

        User user = request.getUser();
        UserData userData = (UserData) userDatas.get(user.getID());

        try {
            FileInputBean fi = event.getFileInputBean("userfileLeft");
            String filename = fi.getFileName();

            if (fi.getSize() < FileInputBean.MAX_UPLOAD_SIZE) {
                SecureDirectoryService secureDirectoryService = (SecureDirectoryService) getPortletConfig().getContext().getService(SecureDirectoryService.class);
                filename = util.substitute("s!\\\\!/!g", filename);
                if (util.match("m!/([^/]+)$!", filename)) {
                    filename = util.group(1);
                }

                filename = util.substitute("s! !!g", filename);

                String path = userData.getPath("left");
                secureDirectoryService.writeFromStream(user.getID(), rootDir, path + filename, fi.getInputStream());
                readDirectories(event, userData);
            } else
                log.error("Size of uploaded file is to big");
        } catch (PortletServiceUnavailableException e) {
            log.error("Secure service unavailable", e);
        } catch (PortletServiceNotFoundException e) {
            log.error("Secure service not found", e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void uploadFileRight(FormEvent event) throws PortletException {
        PortletRequest request = event.getPortletRequest();

        User user = request.getUser();
        UserData userData = (UserData) userDatas.get(user.getID());

        try {
            FileInputBean fi = event.getFileInputBean("userfileRight");
            String filename = fi.getFileName();

            if (fi.getSize() < FileInputBean.MAX_UPLOAD_SIZE) {
                SecureDirectoryService secureDirectoryService = (SecureDirectoryService) getPortletConfig().getContext().getService(SecureDirectoryService.class);
                filename = util.substitute("s!\\\\!/!g", filename);
                if (util.match("m!/([^/]+)$!", filename)) {
                    filename = util.group(1);
                }

                filename = util.substitute("s! !!g", filename);

                String path = userData.getPath("right");
                secureDirectoryService.writeFromStream(user.getID(), rootDir, path + filename, fi.getInputStream());
                readDirectories(event, userData);
            } else
                log.error("Size of uploaded file is to big");
        } catch (PortletServiceUnavailableException e) {
            log.error("Secure service unavailable", e);
        } catch (PortletServiceNotFoundException e) {
            log.error("Secure service not found", e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void newDirectory(FormEvent event) throws PortletException {
        PortletRequest request = event.getPortletRequest();
        String sideParam = request.getParameter("side");
        TextFieldBean textFieldBean = event.getTextFieldBean("resourceName" + sideParam);
        String resourceName = textFieldBean.getValue();

        User user = request.getUser();
        UserData userData = (UserData) userDatas.get(user.getID());

        if (resourceName != null && !resourceName.equals("")) {
            try {
                SecureDirectoryService secureDirectoryService = (SecureDirectoryService) getPortletConfig().getContext().getService(SecureDirectoryService.class);
                String path = userData.getPath(sideParam);
                resourceName = util.substitute("s! !!g", resourceName);

                File newDirectory = secureDirectoryService.getFile(request.getUser().getID(), rootDir, path + resourceName);
                newDirectory.mkdir();
                textFieldBean.setValue("");
                readDirectories(event, userData);
            } catch (PortletServiceUnavailableException e) {
                log.error("Secure service unavailable", e);
            } catch (PortletServiceNotFoundException e) {
                log.error("Secure service not found", e);
            }
        }
    }

    public void newFile(FormEvent event) throws PortletException {
        PortletRequest request = event.getPortletRequest();
        String sideParam = request.getParameter("side");
        TextFieldBean textFieldBean = event.getTextFieldBean("resourceName" + sideParam);
        String resourceName = textFieldBean.getValue();

        User user = request.getUser();
        UserData userData = (UserData) userDatas.get(user.getID());

        if (resourceName != null && !resourceName.equals("")) {
            try {
                SecureDirectoryService secureDirectoryService = (SecureDirectoryService) getPortletConfig().getContext().getService(SecureDirectoryService.class);
                String path = userData.getPath(sideParam);
                resourceName = util.substitute("s! !!g", resourceName);

                File newFile = secureDirectoryService.getFile(request.getUser().getID(), rootDir, path + resourceName);
                newFile.createNewFile();
                textFieldBean.setValue("");
                readDirectories(event, userData);
            } catch (PortletServiceUnavailableException e) {
                log.error("Secure service unavailable", e);
            } catch (PortletServiceNotFoundException e) {
                log.error("Secure service not found", e);
            } catch (IOException e) {
                log.error("Unable to create new file", e);
            }
        }
    }

    public void gotoRootDirLeft(FormEvent event) throws PortletException {
        PortletRequest request = event.getPortletRequest();

        User user = request.getUser();
        UserData userData = (UserData) userDatas.get(user.getID());

        userData.setPath("left", "/");
        readDirectories(event, userData);
    }

    public void gotoRootDirRight(FormEvent event) throws PortletException {
        PortletRequest request = event.getPortletRequest();

        User user = request.getUser();
        UserData userData = (UserData) userDatas.get(user.getID());

        userData.setPath("right", "/");
        readDirectories(event, userData);
    }

    public void copy(FormEvent event) throws PortletException {
        PortletRequest request = event.getPortletRequest();
        String sideParam = request.getParameter("side");

        User user = request.getUser();
        UserData userData = (UserData) userDatas.get(user.getID());

        String sourcePath = userData.getPath(sideParam);
        String destinationPath = (sideParam.equals("left") ?
                userData.getPath("right") :
                userData.getPath("left"));

        ResourceInfo[] resources = null;
        resources = (sideParam.equals("left") ?
                userData.getLeftResourceList() :
                userData.getRightResourceList());
        try {
            SecureDirectoryService secureDirectoryService = (SecureDirectoryService) getPortletConfig().getContext().getService(SecureDirectoryService.class);
            Enumeration params = request.getParameterNames();

            while (params.hasMoreElements()) {
                String param = (String) params.nextElement();
                if (util.match("m!^" + sideParam + "_(\\d)+$!", param)) {
                    secureDirectoryService.saveResourceCopy(request.getUser().getID(), rootDir, sourcePath + resources[Integer.parseInt(util.group(1))].getResource(), destinationPath + resources[Integer.parseInt(util.group(1))].getResource());
                }
            }
            readDirectories(event, userData);
        } catch (PortletServiceUnavailableException e) {
            log.error("Secure service unavailable", e);
        } catch (PortletServiceNotFoundException e) {
            log.error("Secure service not found", e);
        }
    }

    public void move(FormEvent event) throws PortletException {
        PortletRequest request = event.getPortletRequest();
        String sideParam = request.getParameter("side");

        User user = request.getUser();
        UserData userData = (UserData) userDatas.get(user.getID());

        String sourcePath = userData.getPath(sideParam);
        String destinationPath = (sideParam.equals("left") ?
                userData.getPath("right") :
                userData.getPath("left"));

        ResourceInfo[] resources = null;
        resources = (sideParam.equals("left") ?
                userData.getLeftResourceList() :
                userData.getRightResourceList());
        try {
            SecureDirectoryService secureDirectoryService = (SecureDirectoryService) getPortletConfig().getContext().getService(SecureDirectoryService.class);
            Enumeration params = request.getParameterNames();

            while (params.hasMoreElements()) {
                String param = (String) params.nextElement();
                if (util.match("m!^" + sideParam + "_(\\d)+$!", param)) {
                    secureDirectoryService.saveResourceMove(request.getUser().getID(), rootDir, sourcePath + resources[Integer.parseInt(util.group(1))].getResource(), destinationPath + resources[Integer.parseInt(util.group(1))].getResource());
                }
            }
            readDirectories(event, userData);
        } catch (PortletServiceUnavailableException e) {
            log.error("Secure service unavailable", e);
        } catch (PortletServiceNotFoundException e) {
            log.error("Secure service not found", e);
        }
    }

    public void delete(FormEvent event) throws PortletException {
        PortletRequest request = event.getPortletRequest();
        String sideParam = request.getParameter("side");

        User user = request.getUser();
        UserData userData = (UserData) userDatas.get(user.getID());

        String path = userData.getPath(sideParam);

        ResourceInfo[] resources = null;
        resources = (sideParam.equals("left") ?
                userData.getLeftResourceList() :
                userData.getRightResourceList());
        try {
            SecureDirectoryService secureDirectoryService = (SecureDirectoryService) getPortletConfig().getContext().getService(SecureDirectoryService.class);
            Enumeration params = request.getParameterNames();

            while (params.hasMoreElements()) {
                String param = (String) params.nextElement();
                if (util.match("m!^" + sideParam + "_(\\d)+$!", param)) {
                    secureDirectoryService.deleteResource(request.getUser().getID(), rootDir, path + resources[Integer.parseInt(util.group(1))].getResource(), true);
                }
            }
            readDirectories(event, userData);
        } catch (PortletServiceUnavailableException e) {
            log.error("Secure service unavailable", e);
        } catch (PortletServiceNotFoundException e) {
            log.error("Secure service not found", e);
        }
    }

    public void editfile(FormEvent event) throws PortletException {
        PortletRequest request = event.getPortletRequest();
        String sideParam = request.getParameter("side");
        String fileNumberParam = request.getParameter("fileNumber");

        User user = request.getUser();
        UserData userData = (UserData) userDatas.get(user.getID());

        try {
            SecureDirectoryService secureDirectoryService = (SecureDirectoryService) getPortletConfig().getContext().getService(SecureDirectoryService.class);
            int fileNumber = Integer.parseInt(fileNumberParam);
            ResourceInfo[] resources = null;
            resources = (sideParam.equals("left") ?
                    userData.getLeftResourceList() :
                    userData.getRightResourceList());

            File file = secureDirectoryService.getFile(request.getUser().getID(), rootDir, userData.getPath(sideParam) + resources[fileNumber].getResource());
            userData.setEditSide(sideParam.equals("left") ? "left" : "right");
            userData.setEditFile(file);
            userData.setState("edit");
        } catch (NumberFormatException e) {
            log.error("Unable to parse fileNumberParam (" + fileNumberParam + ")", e);
        } catch (PortletServiceUnavailableException e) {
            log.error("Secure service unavailable", e);
        } catch (PortletServiceNotFoundException e) {
            log.error("Secure service not found", e);
        }


    }

    public void save(FormEvent event) throws PortletException {
        PortletRequest request = event.getPortletRequest();
        String fileData = request.getParameter("fileData");

        User user = request.getUser();
        UserData userData = (UserData) userDatas.get(user.getID());

        try {
            StringBufferInputStream stringBufferInputStream = new StringBufferInputStream(fileData);
            SecureDirectoryService secureDirectoryService = (SecureDirectoryService) getPortletConfig().getContext().getService(SecureDirectoryService.class);
            secureDirectoryService.writeFromStream(request.getUser().getID(), rootDir, userData.getPath(userData.getEditSide()) + userData.getEditFile().getName(), stringBufferInputStream);
            readDirectories(event, userData);
            userData.setState("explore");
        } catch (PortletServiceUnavailableException e) {
            log.error("Secure service unavailable", e);
        } catch (PortletServiceNotFoundException e) {
            log.error("Secure service not found", e);
        }
    }

    public void cancel(FormEvent event) throws PortletException {
        PortletRequest request = event.getPortletRequest();

        User user = request.getUser();
        UserData userData = (UserData) userDatas.get(user.getID());

        userData.setEditFile(null);
        userData.setState("explore");
    }

    private void readDirectories(FormEvent event, UserData userData) {
        try {
            PortletResponse response = event.getPortletResponse();
            SecureDirectoryService secureDirectoryService = (SecureDirectoryService) getPortletConfig().getContext().getService(SecureDirectoryService.class);
            User user = event.getPortletRequest().getUser();
            if (secureDirectoryService.appHasDirectory(user.getID(), rootDir, true)) {
                String leftPath = userData.getPath("left");
                String rightPath = userData.getPath("right");
                ResourceInfo[] leftResourceList = secureDirectoryService.getResourceList(user.getID(), rootDir, leftPath);
                ResourceInfo[] rightResourceList = secureDirectoryService.getResourceList(user.getID(), rootDir, rightPath);

                String[] leftURIs = null;
                String[] rightURIs = null;

                if (leftResourceList != null) {
                    leftURIs = new String[leftResourceList.length];
                    for (int i = 0; i < leftResourceList.length; ++i) {
                        if (leftResourceList[i].isDirectory()) {
                            PortletURI uri = response.createURI();
                            uri.addAction("changeDir");
                            uri.addParameter("newDir", leftResourceList[i].getResource());
                            uri.addParameter("side", "left");
                            leftURIs[i] = uri.toString();
                        } else {
                            leftURIs[i] = secureDirectoryService.getFileUrl(user.getID(), rootDir, leftPath + leftResourceList[i].getResource(), leftResourceList[i].getResource());
                        }
                    }
                }
                if (rightResourceList != null) {
                    rightURIs = new String[rightResourceList.length];
                    for (int i = 0; i < rightResourceList.length; ++i) {
                        if (rightResourceList[i].isDirectory()) {
                            PortletURI uri = response.createURI();
                            uri.addAction("changeDir");
                            uri.addParameter("newDir", rightResourceList[i].getResource());
                            uri.addParameter("side", "right");
                            rightURIs[i] = uri.toString();
                        } else {
                            rightURIs[i] = secureDirectoryService.getFileUrl(user.getID(), rootDir, rightPath + rightResourceList[i].getResource(), rightResourceList[i].getResource());
                        }
                    }
                }
                userData.setLeftResourceList(leftResourceList);
                userData.setRightResourceList(rightResourceList);
                userData.setLeftURIs(leftURIs);
                userData.setRightURIs(rightURIs);
                userData.setCorrect(new Boolean(true));
            } else {
                userData.setCorrect(new Boolean(false));
            }
        } catch (PortletServiceUnavailableException e) {
            log.error("Secure service unavailable", e);
            userData.setCorrect(new Boolean(false));
        } catch (PortletServiceNotFoundException e) {
            log.error("Secure service not found", e);
            userData.setCorrect(new Boolean(false));
        }
    }

    private void setAttributes(FormEvent event) {
        PortletRequest request = event.getPortletRequest();
        PortletResponse response = event.getPortletResponse();
        User user = request.getUser();

        UserData userData = (UserData) userDatas.get(user.getID());
        PortletURI uri;

        String[] leftEditURIs;
        String[] rightEditURIs;

        ResourceInfo[] resources = userData.getLeftResourceList();

        leftEditURIs = new String[resources != null && resources.length > 0 ? resources.length : 1];

        if (resources != null) {
            for (int i = 0; i < resources.length; ++i) {
                if (!resources[i].isDirectory()) {
                    uri = response.createURI();
                    uri.addAction("editfile");
                    uri.addParameter("side", "left");
                    uri.addParameter("fileNumber", Integer.toString(i));
                    leftEditURIs[i] = uri.toString();
                }
            }
        }

        request.setAttribute("leftEditURIs", Arrays.asList(leftEditURIs));

        resources = userData.getRightResourceList();
        rightEditURIs = new String[resources != null && resources.length > 0 ? resources.length : 1];
        if (resources != null) {
            for (int i = 0; i < resources.length; ++i) {
                if (!resources[i].isDirectory()) {
                    uri = response.createURI();
                    uri.addAction("editfile");
                    uri.addParameter("side", "right");
                    uri.addParameter("fileNumber", Integer.toString(i));
                    rightEditURIs[i] = uri.toString();
                }
            }
        }

        request.setAttribute("rightEditURIs", Arrays.asList(rightEditURIs));
        request.setAttribute("userData", userData);
    }

}