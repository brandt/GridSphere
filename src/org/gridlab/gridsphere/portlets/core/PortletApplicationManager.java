/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets.core;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlets.core.tomcat.TomcatManagerWrapper;
import org.gridlab.gridsphere.portlets.core.tomcat.TomcatWebAppResult;
import org.gridlab.gridsphere.portlets.core.tomcat.TomcatManagerException;
import org.gridlab.gridsphere.services.core.registry.PortletManagerService;
import org.gridlab.gridsphere.provider.ActionPortlet;
import org.gridlab.gridsphere.provider.portletui.beans.FrameBean;
import org.gridlab.gridsphere.provider.event.FormEvent;

import javax.servlet.UnavailableException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

/**
 * The PortletApplicationManager is a wrapper for the Tomcat manager webapp in 4.1.X which allows dynamic
 * ui application management and hence dynamic portlet management. This class needs to be adapted for
 * other servlet containers.
 */
public class PortletApplicationManager extends ActionPortlet {

    private PortletManagerService portletManager = null;
    private TomcatManagerWrapper tomcat = TomcatManagerWrapper.getInstance();

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        DEFAULT_VIEW_PAGE = "listPortlets";
    }

    public void initConcrete(PortletSettings settings) throws UnavailableException {
        super.initConcrete(settings);
    }

    public void listPortlets(FormEvent event) throws PortletException {
        FrameBean frame = event.getFrameBean("errorFrame");
        User user = event.getPortletRequest().getUser();
        try {
            portletManager = (PortletManagerService)getConfig().getContext().getService(PortletManagerService.class, user);
        } catch (PortletServiceException e) {
            frame.setValue("PortletRegistry service unavailable! " + e.getMessage());
            throw new PortletException("PortletRegistry service unavailable! ", e);
        }
        //TomcatWebAppResult result = tomcat.getWebAppList();
        List webapps = portletManager.getPortletWebApplicationNames();
        List result = new ArrayList();
        try {
            result = tomcat.getPortletAppList(webapps);
            event.getPortletRequest().setAttribute("result", result);
            System.err.println("result is OK");
        } catch (TomcatManagerException e) {
            log.error("Unable to retrieve list of portlets. Make sure tomcat-users.xml has been edited according to the UserGuide.");
            event.getPortletRequest().setAttribute("result", result);
            frame.setValue("Unable to retrieve list of portlets. Make sure tomcat-users.xml has been edited according to the UserGuide.");
            frame.setStyle(FrameBean.ERROR_TYPE);
        }

        //if (result != null) log.debug("result: " + result.getReturnCode() + " " + result.getDescription());
        setNextState(event.getPortletRequest(), "portletmanager/list.jsp");
    }

    public void doPortletManager(FormEvent event) throws PortletException {
        log.debug("In doPortletManager");
        DefaultPortletAction action = event.getAction();
        PortletRequest req = event.getPortletRequest();
        PortletResponse res = event.getPortletResponse();

        User user = event.getPortletRequest().getUser();
        FrameBean frame = event.getFrameBean("errorFrame");

        try {
            portletManager = (PortletManagerService)getConfig().getContext().getService(PortletManagerService.class, user);
        } catch (PortletServiceException e) {
            frame.setValue("PortletRegistry service unavailable! " + e.getMessage());
            throw new PortletException("PortletRegistry service unavailable! ", e);
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
                            portletManager.removePortletWebApplication(webAppName, req, res);
                            result = tomcat.removeWebApp(webAppName);
                        }
                    }
                }

                //String portletWar = warFile.getAbsolutePath();
                if (webAppContext != null) {
                    //result = tomcat.installWebApp(webAppContext, portletWar);
                    //result = tomcat.startWebApp(webAppName);
                    portletManager.installPortletWebApplication(webAppName, req, res);
                }
            } else if ((operation != null) && (appName!= null)) {
                if (operation.equals("start")) {
                    result = tomcat.startWebApp(appName);
                    portletManager.installPortletWebApplication(appName, req, res);
                } else if (operation.equals("stop")) {
                    portletManager.destroyPortletWebApplication(appName, req, res);
                    result = tomcat.stopWebApp(appName);
                } else if (operation.equals("reload")) {
                    System.err.println("First we remove web app");
                    portletManager.removePortletWebApplication(appName, req, res);
                    System.err.println("Second we use tomcat to reload web app");
                    result = tomcat.reloadWebApp(appName);
                    System.err.println("Third we init new web app");
                    portletManager.initPortletWebApplication(appName, req, res);
                } else if (operation.equals("remove")) {
                    portletManager.removePortletWebApplication(appName, req, res);
                    result = tomcat.removeWebApp(appName);
                } else if (operation.equals("deploy")) {
                    result = tomcat.deployWebApp(appName);
                    portletManager.installPortletWebApplication(appName, req, res);
                } else if (operation.equals("undeploy")) {
                    result = tomcat.undeployWebApp(appName);
                    portletManager.removePortletWebApplication(appName, req, res);
                }
            }
        } catch (IOException e) {
            log.error("Caught IOException!", e);
            frame.setValue("Caught IOException! " + e.getMessage());
        } catch (TomcatManagerException e) {
            log.error("Caught TomcatmanagerException!", e);
            frame.setValue("Caught TomcatManagerException " + e.getMessage());
        }
        req.setAttribute("result", result);
        if (result != null) log.debug("result: " + result.getReturnCode() + " " + result.getDescription());
        setNextState(req, "portletmanager/list.jsp");
    }

}
