/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets.core.admin.portlets;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlets.core.admin.portlets.tomcat.TomcatManagerException;
import org.gridlab.gridsphere.portlets.core.admin.portlets.tomcat.TomcatManagerWrapper;
import org.gridlab.gridsphere.portlets.core.admin.portlets.tomcat.TomcatWebAppResult;
import org.gridlab.gridsphere.provider.event.FormEvent;
import org.gridlab.gridsphere.provider.portlet.ActionPortlet;
import org.gridlab.gridsphere.provider.portletui.beans.FrameBean;
import org.gridlab.gridsphere.provider.portletui.beans.FileInputBean;
import org.gridlab.gridsphere.provider.portletui.beans.TextFieldBean;
import org.gridlab.gridsphere.services.core.registry.PortletManagerService;

import javax.servlet.UnavailableException;
import java.io.IOException;
import java.util.*;

/**
 * The PortletApplicationManager is a wrapper for the Tomcat manager webapp in 4.1.X which allows dynamic
 * ui application management and hence dynamic portlet management. This class needs to be adapted for
 * other servlet containers.
 */
public class PortletApplicationManager extends ActionPortlet {

    public static final String VIEW_JSP = "admin/portlets/view.jsp";
    public static final String HELP_JSP = "admin/portlets/help.jsp";

    private TomcatManagerWrapper tomcat = TomcatManagerWrapper.getInstance();

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        DEFAULT_VIEW_PAGE = "listPortlets";
        DEFAULT_HELP_PAGE = HELP_JSP;
    }

    public void initConcrete(PortletSettings settings) throws UnavailableException {
        super.initConcrete(settings);
    }

    public void listPortlets(FormEvent event) throws PortletException {
        FrameBean frame = event.getFrameBean("errorFrame");
        PortletRequest req = event.getPortletRequest();
        List result = new ArrayList();
        try {
            result = tomcat.getPortletAppList(req);
            event.getPortletRequest().setAttribute("result", result);
            System.err.println("result is OK");
        } catch (TomcatManagerException e) {
            log.error("Unable to retrieve list of portlets. Make sure tomcat-users.xml has been edited according to the UserGuide.");
            event.getPortletRequest().setAttribute("result", result);
            frame.setKey("PORTLET_ERR_LIST");
            frame.setStyle(FrameBean.ERROR_TYPE);
        }

        //if (result != null) log.debug("result: " + result.getReturnCode() + " " + result.getDescription());
        setNextState(event.getPortletRequest(), VIEW_JSP);
    }

    public void doPortletManager(FormEvent event) throws PortletException {
        checkSuperRole(event);
        log.debug("In doPortletManager");
        DefaultPortletAction action = event.getAction();
        PortletRequest req = event.getPortletRequest();
        PortletResponse res = event.getPortletResponse();

        User user = event.getPortletRequest().getUser();
        FrameBean frame = event.getFrameBean("errorFrame");
        PortletManagerService portletManager = null;
        try {
            portletManager = (PortletManagerService)getConfig().getContext().getService(PortletManagerService.class, user);
        } catch (PortletServiceException e) {
            frame.setKey("PORTLET_ERR_REGISTRY");
            frame.setStyle("error");
        }

        Map params = action.getParameters();
        String operation = (String)params.get("operation");
        String appName = (String)params.get("context");
        TomcatWebAppResult result = null;

        try {
           if (action.getName().equals("install")) {
                log.debug("In actionPerformed doing an install");


                String portletWar = null;
                /*
                FileFormEvent fileformEvent = new FileFormEventImpl(event);
                try {
                    portletWar = fileformEvent.saveFile(PortletManagerService.WEB_APPLICATION_PATH);
                } catch (FileFormException ffe) {
                    log.error("Unable to save file from form: " + ffe.getMessage());
                }
                */
                /* Remove old portlet web app if it exists */
                int idx = -1;
                String webAppContext = null;
                String webAppName = null;
                List webappsList = portletManager.getPortletWebApplicationNames();
                if ((idx = portletWar.lastIndexOf(".")) > 0) {
                    webAppName = portletWar.substring(0, idx);
                    if ((idx = webAppName.lastIndexOf("/")) > 0) {
                        webAppContext = webAppName.substring(idx);
                        webAppName = webAppContext.substring(1);
                        if (webappsList.contains(webAppName)) {
                            portletManager.destroyPortletWebApplication(webAppName, req, res);
                            result = tomcat.removeWebApp(req, webAppName);
                        }
                    }
                }

                //String portletWar = warFile.getAbsolutePath();
                if (webAppContext != null) {
                    //result = tomcat.installWebApp(webAppContext, portletWar);
                    //result = tomcat.startWebApp(webAppName);
                    portletManager.initPortletWebApplication(webAppName, req, res);
                }
            } else if ((operation != null) && (appName!= null)) {
                if (operation.equals("start")) {
                    result = tomcat.startWebApp(req, appName);
                    portletManager.destroyPortletWebApplication(appName, req, res);
                    portletManager.initPortletWebApplication(appName, req, res);
                } else if (operation.equals("stop")) {
                    result = tomcat.stopWebApp(req, appName);
                    //portletManager.destroyPortletWebApplication(appName, req, res);
                } else if (operation.equals("reload")) {
                    portletManager.destroyPortletWebApplication(appName, req, res);
                    result = tomcat.stopWebApp(req, appName);
                    result = tomcat.startWebApp(req, appName);
                    portletManager.initPortletWebApplication(appName, req, res);
                } else if (operation.equals("remove")) {
                    portletManager.destroyPortletWebApplication(appName, req, res);
                    result = tomcat.removeWebApp(req, appName);
                } else if (operation.equals("deploy")) {
                    result = tomcat.deployWebApp(req, appName);
                    result = tomcat.startWebApp(req, appName);
                    portletManager.initPortletWebApplication(appName, req, res);
                } else if (operation.equals("undeploy")) {
                    result = tomcat.undeployWebApp(req, appName);
                    portletManager.destroyPortletWebApplication(appName, req, res);
                }
            }
        } catch (IOException e) {
            log.error("Caught IOException!", e);
            frame.setKey("PORTLET_ERR_IO");
            frame.setStyle("error");
        } catch (TomcatManagerException e) {
            log.error("Caught TomcatmanagerException!", e);
            frame.setKey("PORTLET_ERR_TOMCAT");
            frame.setStyle("error");
        }
        req.setAttribute("result", result);
        if (result != null) log.debug("result: " + result.getReturnCode() + " " + result.getDescription());
        setNextState(req, DEFAULT_VIEW_PAGE);
    }

    public void uploadPortletWAR(FormEvent event) throws PortletException {
        checkSuperRole(event);
        log.debug("in FileManagerPortlet: doUploadFile");
        PortletRequest req = event.getPortletRequest();
        PortletResponse res = event.getPortletResponse();
        try {
            FileInputBean fi = event.getFileInputBean("userfile");
            User user = event.getPortletRequest().getUser();
            String fileName = fi.getFileName();
            System.err.println("filename = " + fileName);
            if (fileName.equals("")) return;
            PortletManagerService portletManager = null;
            try {
                portletManager = (PortletManagerService)getConfig().getContext().getService(PortletManagerService.class, user);
            } catch (PortletServiceException e) {
                FrameBean frame = event.getFrameBean("errorFrame");
                frame.setKey("PORTLET_ERR_REGISTRY");
                throw new PortletException("PortletRegistry service unavailable! ", e);
            }

            int isWar = fileName.indexOf(".war");
            if (isWar > 0) {
                String appName = fileName.substring(0, isWar);
                log.debug("installing and initing webapp: " + appName);
                tomcat.installWebApp(req, appName, fileName);
                portletManager.initPortletWebApplication(appName, req, res);
            }
            log.debug("fileinputbean value=" + fi.getValue());
        } catch (Exception e) {
            FrameBean errMsg = event.getFrameBean("errorFrame");
            errMsg.setKey("PORTLET_ERR_UPLOAD");
            errMsg.setStyle("error");
            log.error("Unable to store uploaded file ", e);
        }
        setNextState(req, DEFAULT_VIEW_PAGE);
    }

    public void deployWebapp(FormEvent event) throws PortletException {
        checkSuperRole(event);
        log.debug("in FileManagerPortlet: deployWebapp");
        PortletRequest req = event.getPortletRequest();
        PortletResponse res = event.getPortletResponse();
        try {
            TextFieldBean tf = event.getTextFieldBean("webappNameTF");
            User user = event.getPortletRequest().getUser();
            String webappName = tf.getValue();
            if (webappName == null) return;
            PortletManagerService portletManager = null;
            try {
                portletManager = (PortletManagerService)getConfig().getContext().getService(PortletManagerService.class, user);
            } catch (PortletServiceException e) {
                FrameBean frame = event.getFrameBean("errorFrame");
                frame.setKey("PORTLET_ERR_REGISTRY");
                throw new PortletException("PortletRegistry service unavailable! ", e);
            }

            tomcat.installWebApp(req, webappName);
            portletManager.initPortletWebApplication(webappName, req, res);

        } catch (Exception e) {
            FrameBean errMsg = event.getFrameBean("errorFrame");
            errMsg.setKey("PORTLET_ERR_DEPLOY");
            errMsg.setStyle("error");
            log.error("Unable to deploy webapp  ", e);
        }
        setNextState(req, DEFAULT_VIEW_PAGE);
    }
}
