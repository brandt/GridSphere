/**
 * @author <a href="mailto:tkucz@icis.pcz.pl">Tomasz Kuczynski</a>
 * @version 0.91 2004/03/30
 */
package org.gridlab.gridsphere.portlets.core.file;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.event.ActionEvent;
import org.gridlab.gridsphere.services.core.secdir.SecureDirectoryService;
import org.gridlab.gridsphere.services.core.secdir.ResourceInfo;
import org.apache.oro.text.perl.Perl5Util;
import org.apache.oro.text.perl.MalformedPerl5PatternException;
import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.FileItem;

import javax.servlet.UnavailableException;
import java.io.IOException;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringBufferInputStream;
import java.util.*;

public class CommanderPortlet extends AbstractPortlet {
    private static final int MAX_REQUEST_SIZE = 5 * 1024 * 1024; //5 Mb
    private Perl5Util util = new Perl5Util();
    private static Map userDatas = java.util.Collections.synchronizedMap(new HashMap());

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
    }

    public void actionPerformed(ActionEvent event) {
        PortletRequest request = event.getPortletRequest();
        String action = event.getAction().getName();
        String userID = request.getUser().getID();
        UserData userData = (UserData) userDatas.get(userID);

        if (action.equals("changeDir")) {
            String newDirParam = request.getParameter("newDir");
            String sideParam = request.getParameter("side");
            String newDir = userData.getPath(sideParam);
            if (newDirParam.equals("..")) {
                newDir = util.substitute("s!/[^/]+/$!/!", newDir);
            } else {
                newDir += newDirParam + "/";
            }
            userData.setPath(sideParam, newDir);
            readDirectories(request, event.getPortletResponse(), userData);
        } else if (action.equals("explorer")) {
            try {
                HashMap requestData = new HashMap();

                DiskFileUpload upload = new DiskFileUpload();
                upload.setSizeThreshold(MAX_REQUEST_SIZE);
                List items = upload.parseRequest(request);

                Iterator iter = items.iterator();
                while (iter.hasNext()) {
                    FileItem item = (FileItem) iter.next();
                    if (item.isFormField()) {
                        requestData.put(item.getFieldName(), item.getString());
                    } else {
                        requestData.put(item.getFieldName(), item);
                    }
                }
                SecureDirectoryService secureDirectoryService = (SecureDirectoryService) getPortletConfig().getContext().getService(SecureDirectoryService.class);
                String sideParam = request.getParameter("side");
                String formAction = (String) requestData.get("formAction");

                if (formAction.equals("upload")) {
                    FileItem fileItem = (FileItem) requestData.get("file");
                    String filename = fileItem.getName();

                    if (util.match("m!/([^/]+)$!", filename)) {
                        filename = util.group(1);
                    }

                    filename = util.substitute("s! !!g", filename);

                    String path = userData.getPath(sideParam);
                    File file = secureDirectoryService.getFile(request.getUser(), "commander", path + filename);
                    fileItem.write(file);
                } else if (formAction.equals("touch")) {
                    String resourceName = (String) requestData.get("resourceName");
                    if (resourceName != null && !resourceName.equals("")) {
                        String path = userData.getPath(sideParam);
                        resourceName = util.substitute("s! !!g", resourceName);
                        File newFile = secureDirectoryService.getFile(request.getUser(), "commander", path + resourceName);
                        newFile.createNewFile();
                    }
                } else if (formAction.equals("mkdir")) {
                    String resourceName = (String) requestData.get("resourceName");
                    if (resourceName != null && !resourceName.equals("")) {
                        String path = userData.getPath(sideParam);
                        resourceName = util.substitute("s! !!g", resourceName);
                        File newDirectory = secureDirectoryService.getFile(request.getUser(), "commander", path + resourceName);
                        newDirectory.mkdir();
                    }
                } else if (formAction.equals("move")) {
                    String sourcePath = userData.getPath(sideParam);
                    String destinationPath = (sideParam.equals("left") ?
                            userData.getPath("right") :
                            userData.getPath("left"));

                    ResourceInfo[] resources = null;
                    resources = (sideParam.equals("left") ?
                            userData.getLeftResourceList() :
                            userData.getRightResourceList());

                    Iterator params = items.iterator();
                    while (params.hasNext()) {
                        FileItem item = (FileItem) params.next();
                        if (item.isFormField()) {
                            String param = item.getFieldName();
                            if (util.match("m!^" + sideParam + "_(\\d)+$!", param)) {
                                secureDirectoryService.saveResourceMove(request.getUser(), "commander", sourcePath + resources[Integer.parseInt(util.group(1))].getResource(), destinationPath + resources[Integer.parseInt(util.group(1))].getResource());
                            }
                        }
                    }
                } else if (formAction.equals("copy")) {
                    String sourcePath = userData.getPath(sideParam);
                    String destinationPath = (sideParam.equals("left") ?
                            userData.getPath("right") :
                            userData.getPath("left"));

                    ResourceInfo[] resources = null;
                    resources = (sideParam.equals("left") ?
                            userData.getLeftResourceList() :
                            userData.getRightResourceList());

                    Iterator params = items.iterator();
                    while (params.hasNext()) {
                        FileItem item = (FileItem) params.next();
                        if (item.isFormField()) {
                            String param = item.getFieldName();
                            if (util.match("m!^" + sideParam + "_(\\d)+$!", param)) {
                                secureDirectoryService.saveResourceCopy(request.getUser(), "commander", sourcePath + resources[Integer.parseInt(util.group(1))].getResource(), destinationPath + resources[Integer.parseInt(util.group(1))].getResource());
                            }
                        }
                    }
                } else if (formAction.equals("delete")) {
                    String path = userData.getPath(sideParam);
                    ResourceInfo[] resources = null;
                    resources = (sideParam.equals("left") ?
                            userData.getLeftResourceList() :
                            userData.getRightResourceList());

                    String checkPath = (sideParam.equals("left") ?
                            userData.getPath("right") :
                            userData.getPath("left"));

                    Iterator params = items.iterator();
                    while (params.hasNext()) {
                        FileItem item = (FileItem) params.next();
                        if (item.isFormField()) {
                            String param = item.getFieldName();
                            try {
                                if (util.match("m!^" + sideParam + "_(\\d)+$!", param)) {
                                    String resourcePath = path + resources[Integer.parseInt(util.group(1))].getResource();
                                    if (!util.match("m#^" + resourcePath + "#", checkPath))
                                        secureDirectoryService.deleteResource(request.getUser(), "commander", resourcePath, true);
                                }
                            } catch (MalformedPerl5PatternException e) {
                            }
                        }
                    }
                }
                readDirectories(request, event.getPortletResponse(), userData);
            } catch (FileUploadException e) {
                log.error("Unable to parse request", e);
            } catch (PortletServiceUnavailableException e) {
                log.error("Secure service unavailable", e);
            } catch (PortletServiceNotFoundException e) {
                log.error("Secure service not found", e);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        } else if (action.equals("editfile")) {
            String sideParam = request.getParameter("side");
            String fileNumberParam = request.getParameter("fileNumber");
            try {
                SecureDirectoryService secureDirectoryService = (SecureDirectoryService) getPortletConfig().getContext().getService(SecureDirectoryService.class);
                int fileNumber = Integer.parseInt(fileNumberParam);
                ResourceInfo[] resources = null;
                resources = (sideParam.equals("left") ?
                        userData.getLeftResourceList() :
                        userData.getRightResourceList());

                File file = secureDirectoryService.getFile(request.getUser(), "commander", userData.getPath(sideParam) + resources[fileNumber].getResource());
                userData.setEditSide(sideParam.equals("left") ? "left" : "right");
                userData.setEditFile(file);
                userData.setState("edit");
            } catch (NumberFormatException e) {
            } catch (PortletServiceNotFoundException e) {
            } catch (PortletServiceUnavailableException e) {
            }
        } else if (action.equals("commit")) {
            String formAction = request.getParameter("formAction");
            if (formAction.equals("cancel")) {
                userData.setEditFile(null);
            } else if (formAction.equals("save")) {
                String fileData = request.getParameter("fileData");
                try {
                    StringBufferInputStream stringBufferInputStream = new StringBufferInputStream(fileData);
                    SecureDirectoryService secureDirectoryService = (SecureDirectoryService) getPortletConfig().getContext().getService(SecureDirectoryService.class);
                    secureDirectoryService.writeFromStream(request.getUser(), "commander", userData.getPath(userData.getEditSide()) + userData.getEditFile().getName(), stringBufferInputStream);
                    readDirectories(request, event.getPortletResponse(), userData);
                } catch (PortletServiceNotFoundException e) {
                } catch (PortletServiceUnavailableException e) {
                }
            }
            userData.setState("explore");
        }
    }

    public void doView(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        User user = request.getUser();
        UserData userData = (UserData) userDatas.get(user.getID());

        if (userData == null) {
            userData = new UserData();
            readDirectories(request, response, userData);
            userDatas.put(user.getID(), userData);
        }
        setAttributes(request, response);
        if (userData.getState().equals("explore")) {
            getPortletConfig().getContext().include("/jsp/commander/explorer.jsp", request, response);
        } else { // if(userData.getState().equals("edit")){
            getPortletConfig().getContext().include("/jsp/commander/editfile.jsp", request, response);
        }
    }

    public void doHelp(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        PrintWriter out = response.getWriter();
        out.println("Help mode not implemented yet !!!");
    }

    private void readDirectories(PortletRequest request, PortletResponse response, UserData userData) {
        try {
            SecureDirectoryService secureDirectoryService = (SecureDirectoryService) getPortletConfig().getContext().getService(SecureDirectoryService.class);
            User user = request.getUser();
            if (secureDirectoryService.appHasDirectory(user, "commander", true)) {
                String leftPath = userData.getPath("left");
                String rightPath = userData.getPath("right");
                ResourceInfo[] leftResourceList = secureDirectoryService.getResourceList(request.getUser(), "commander", leftPath);
                ResourceInfo[] rightResourceList = secureDirectoryService.getResourceList(request.getUser(), "commander", rightPath);

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
                            leftURIs[i] = secureDirectoryService.getFileUrl(user, "commander", leftPath + leftResourceList[i].getResource(), leftResourceList[i].getResource());
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
                            rightURIs[i] = secureDirectoryService.getFileUrl(user, "commander", rightPath + rightResourceList[i].getResource(), rightResourceList[i].getResource());
                        }
                    }
                }
                if (leftURIs != null && rightURIs != null) {
                    userData.setLeftResourceList(leftResourceList);
                    userData.setRightResourceList(rightResourceList);
                    userData.setLeftURIs(leftURIs);
                    userData.setRightURIs(rightURIs);
                    userData.setCorrect(new Boolean(true));
                } else {
                    userData.setCorrect(new Boolean(false));
                }
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

    private void setAttributes(PortletRequest request, PortletResponse response) {
        User user = request.getUser();
        UserData userData = (UserData) userDatas.get(user.getID());

        HashMap URIs = new HashMap();

        PortletURI uri = response.createURI();
        uri.addAction("explorer");
        uri.addParameter("side", "left");
        URIs.put("explorer_left", uri.toString());

        uri = response.createURI();
        uri.addAction("explorer");
        uri.addParameter("side", "right");
        URIs.put("explorer_right", uri.toString());

        uri = response.createURI();
        uri.addAction("commit");
        URIs.put("commit", uri.toString());

        if (userData.getCorrect().booleanValue()) {
            ResourceInfo[] resources = userData.getLeftResourceList();
            String[] leftEditURIs = new String[resources.length];
            for (int i = 0; i < resources.length; ++i) {
                if (!resources[i].isDirectory()) {
                    uri = response.createURI();
                    uri.addAction("editfile");
                    uri.addParameter("side", "left");
                    uri.addParameter("fileNumber", Integer.toString(i));
                    leftEditURIs[i] = uri.toString();
                }
            }
            request.setAttribute("leftEditURIs", Arrays.asList(leftEditURIs));
            resources = userData.getRightResourceList();
            String[] rightEditURIs = new String[resources.length];
            for (int i = 0; i < resources.length; ++i) {
                if (!resources[i].isDirectory()) {
                    uri = response.createURI();
                    uri.addAction("editfile");
                    uri.addParameter("side", "right");
                    uri.addParameter("fileNumber", Integer.toString(i));
                    rightEditURIs[i] = uri.toString();
                }
            }
            request.setAttribute("rightEditURIs", Arrays.asList(rightEditURIs));

        }
        request.setAttribute("formURIs", URIs);
        request.setAttribute("userData", userData);
    }

}
