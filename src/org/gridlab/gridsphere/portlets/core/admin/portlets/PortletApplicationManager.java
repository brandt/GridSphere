/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets.core.admin.portlets;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.services.core.registry.impl.tomcat.TomcatManagerException;
import org.gridlab.gridsphere.services.core.registry.impl.tomcat.TomcatManagerWrapper;
import org.gridlab.gridsphere.services.core.registry.impl.tomcat.TomcatWebAppResult;
import org.gridlab.gridsphere.services.core.registry.impl.tomcat.TomcatWebAppDescription;
import org.gridlab.gridsphere.provider.event.FormEvent;
import org.gridlab.gridsphere.provider.portlet.ActionPortlet;
import org.gridlab.gridsphere.provider.portletui.beans.*;
import org.gridlab.gridsphere.services.core.registry.PortletManagerService;
import org.gridlab.gridsphere.services.core.security.group.GroupManagerService;

import javax.servlet.UnavailableException;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

/**
 * The PortletApplicationManager is a wrapper for the Tomcat manager webapp in 4.1.X which allows dynamic
 * ui application management and hence dynamic portlet management. This class needs to be adapted for
 * other servlet containers.
 */
public class PortletApplicationManager extends ActionPortlet {

    public static final String VIEW_JSP = "admin/portlets/view.jsp";
    public static final String HELP_JSP = "admin/portlets/help.jsp";

    private TomcatManagerWrapper tomcat = TomcatManagerWrapper.getInstance();
    private PortletManagerService portletManager = null;
    private GroupManagerService groupManagerService = null;

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        try {
            portletManager = (PortletManagerService) getConfig().getContext().getService(PortletManagerService.class);
            groupManagerService = (GroupManagerService) getConfig().getContext().getService(GroupManagerService.class);
        } catch (PortletServiceException e) {
            log.error("Unable to get portlet manager instance", e);
        }
        DEFAULT_VIEW_PAGE = "listPortlets";
        DEFAULT_HELP_PAGE = HELP_JSP;
    }

    public void initConcrete(PortletSettings settings) throws UnavailableException {
        super.initConcrete(settings);
    }

    public void listPortlets(FormEvent event) throws PortletException {
        PortletRequest req = event.getPortletRequest();
        List portletapps = new ArrayList();
        List otherapps = new ArrayList();

        try {
            portletapps = getPortletAppList(req);
            otherapps = getNonPortletAppList(req);
            event.getPortletRequest().setAttribute("result", portletapps);
            event.getPortletRequest().setAttribute("others", otherapps);
            log.info("result is OK");
        } catch (TomcatManagerException e) {
            log.error("Unable to retrieve list of portlets. Make sure tomcat-users.xml has been edited according to the UserGuide.");
            event.getPortletRequest().setAttribute("result", portletapps);
            event.getPortletRequest().setAttribute("others", otherapps);
            MessageBoxBean msg = event.getMessageBoxBean("msg");
            msg.setKey("PORTLET_ERR_LIST");
            msg.setMessageType(MessageStyle.MSG_ERROR);
        }

        //if (result != null) log.debug("result: " + result.getReturnCode() + " " + result.getDescription());
        setNextState(event.getPortletRequest(), VIEW_JSP);
    }

    public void doPortletManager(FormEvent event) throws PortletException {
        log.debug("In doPortletManager");
        DefaultPortletAction action = event.getAction();
        PortletRequest req = event.getPortletRequest();
        PortletResponse res = event.getPortletResponse();

        MessageBoxBean msg = event.getMessageBoxBean("msg");


        Map params = action.getParameters();
        String operation = (String) params.get("operation");
        String appName = (String) params.get("context");
        TomcatWebAppResult result = null;

        try {
            if ((operation != null) && (appName != null)) {
                if (operation.equals("start")) {
                    result = tomcat.startWebApp(req, appName);
                    this.createSuccessMessage(event, this.getLocalizedText(req, "PORTLET_SUC_TOMCAT"));
                    portletManager.initPortletWebApplication(appName, req, res);
                } else if (operation.equals("stop")) {
                    portletManager.destroyPortletWebApplication(appName, req, res);
                    result = tomcat.stopWebApp(req, appName);
                    this.createSuccessMessage(event, this.getLocalizedText(req, "PORTLET_SUC_TOMCAT"));
                } else if (operation.equals("reload")) {
                    portletManager.destroyPortletWebApplication(appName, req, res);
                    result = tomcat.stopWebApp(req, appName);
                    result = tomcat.startWebApp(req, appName);
                    this.createSuccessMessage(event, this.getLocalizedText(req, "PORTLET_SUC_TOMCAT"));
                    portletManager.initPortletWebApplication(appName, req, res);
                } else if (operation.equals("remove")) {
                    portletManager.destroyPortletWebApplication(appName, req, res);
                    result = tomcat.removeWebApp(req, appName);
                    log.debug("removing application tab :" + appName);
                    this.createSuccessMessage(event, this.getLocalizedText(req, "PORTLET_SUC_TOMCAT"));
                } else if (operation.equals("deploy")) {
                    result = tomcat.deployWebApp(req, appName);
                    result = tomcat.startWebApp(req, appName);
                    this.createSuccessMessage(event, this.getLocalizedText(req, "PORTLET_SUC_TOMCAT"));
                    portletManager.initPortletWebApplication(appName, req, res);
                } else if (operation.equals("undeploy")) {
                    result = tomcat.undeployWebApp(req, appName);
                    this.createSuccessMessage(event, this.getLocalizedText(req, "PORTLET_SUC_TOMCAT"));
                    portletManager.destroyPortletWebApplication(appName, req, res);
                }

            }
        } catch (IOException e) {
            log.error("Caught IOException!", e);
            msg.setKey("PORTLET_ERR_IO");
            msg.setMessageType(MessageStyle.MSG_ERROR);
        } catch (TomcatManagerException e) {
            log.error("Caught TomcatmanagerException!", e);
            msg.setKey("PORTLET_ERR_TOMCAT");
            msg.setMessageType(MessageStyle.MSG_ERROR);
        }
        req.setAttribute("result", result);
        if (result != null) log.debug("result: " + result.getReturnCode() + " " + result.getDescription());
        setNextState(req, DEFAULT_VIEW_PAGE);
    }

    public void uploadPortletWAR(FormEvent event) throws PortletException {

        log.debug("in FileManagerPortlet: doUploadFile");
        PortletRequest req = event.getPortletRequest();
        PortletResponse res = event.getPortletResponse();

        try {
            FileInputBean fi = event.getFileInputBean("userfile");

            String fileName = fi.getFileName();
            int fdx = fileName.lastIndexOf(File.separator);
            if (fdx > 0) fileName = fileName.substring(fdx);
            if (fileName.substring(0,1).equals(File.separator)) fileName = fileName.substring(1);

            log.info("filename = " + fileName);

            String webappPath = this.getPortletConfig().getContext().getRealPath("");
            int idx = webappPath.lastIndexOf("webapps");


            if (fileName.equals("")) return;

            int isWar = fileName.indexOf(".war");
            if (isWar > 0) {
                String appName = fileName.substring(0, isWar);
                log.debug("installing and initing webapp: " + appName);

                webappPath = webappPath.substring(0, idx) + "webapps" + File.separator;
                //System.err.println(webappPath + fileName);
                fi.saveFile(webappPath + fileName);

                tomcat.installWebApp(req, appName, fileName);

                File pfile = new File(webappPath + appName + File.separator + "WEB-INF" + File.separator + "portlet.xml");
                System.err.println(webappPath + appName + File.separator + "WEB-INF" + File.separator + "portlet.xml");
                if (pfile.exists()) {
                    //System.err.println("file exists");
                    portletManager.initPortletWebApplication(appName, req, res);
                }
                 // add portlet app to gridsphere portlet app directory
                String portletAppFile = this.getPortletConfig().getContext().getRealPath("/WEB-INF/CustomPortal/portlets/" + appName);
                File portletFile = new File(portletAppFile);
                portletFile.createNewFile();
                System.err.println(portletAppFile);
                createSuccessMessage(event, this.getLocalizedText(req, "PORTLET_SUC_DEPLOY") + " " + appName);
            }
            log.debug("fileinputbean value=" + fi.getValue());
        } catch (Exception e) {
            createErrorMessage(event, this.getLocalizedText(req, "PORTLET_ERR_DEPLOY"));
            log.error("Unable to store uploaded file ", e);
        }
        setNextState(req, DEFAULT_VIEW_PAGE);
    }

    public void deployWebapp(FormEvent event) throws PortletException {

        log.debug("in PortletApplicationManager: deployWebapp");
        PortletRequest req = event.getPortletRequest();
        PortletResponse res = event.getPortletResponse();

        try {
            TextFieldBean tf = event.getTextFieldBean("webappNameTF");

            String webappName = tf.getValue();
            if (webappName == null) return;
            String webappPath = this.getConfig().getContext().getRealPath("");
            int idx = webappPath.lastIndexOf(File.separator);
            webappPath = webappPath.substring(0, idx+1);
            tomcat.installWebApp(req, webappName);

            File pfile = new File(webappPath + webappName + File.separator + "WEB-INF" + File.separator + "portlet.xml");
            System.err.println(webappPath + webappName + File.separator + "WEB-INF" + File.separator + "portlet.xml");
            if (pfile.exists()) {
                portletManager.initPortletWebApplication(webappName, req, res);
            }

            createSuccessMessage(event, this.getLocalizedText(req, "PORTLET_SUC_DEPLOY") + " " + webappName);
        } catch (Exception e) {
            createErrorMessage(event, this.getLocalizedText(req, "PORTLET_ERR_DEPLOY"));
            log.error("Unable to deploy webapp  ", e);
        }
        setNextState(req, DEFAULT_VIEW_PAGE);
    }

    public List getPortletAppList(PortletRequest req) throws TomcatManagerException {
        List webapps = portletManager.getPortletWebApplicationNames();

        List l = new ArrayList();
        TomcatWebAppResult result = tomcat.getWebAppList(req);
        if (result != null) {
            Iterator it = result.getWebAppDescriptions().iterator();
            while (it.hasNext()) {
                TomcatWebAppDescription webAppDesc = (TomcatWebAppDescription) it.next();
                //System.err.println(webAppDesc.toString());
                if (webapps.contains((webAppDesc.getContextPath()))) {
                    String desc = portletManager.getPortletWebApplicationDescription(webAppDesc.getContextPath());
                    webAppDesc.setDescription(desc);
                    l.add(webAppDesc);
                }
            }
        }
        return l;
    }

    public List getNonPortletAppList(PortletRequest req) throws TomcatManagerException {
        List webapps = portletManager.getPortletWebApplicationNames();
        List l = new ArrayList();
        TomcatWebAppResult result = tomcat.getWebAppList(req);
        if (result != null) {
            Iterator it = result.getWebAppDescriptions().iterator();
            while (it.hasNext()) {
                TomcatWebAppDescription webAppDesc = (TomcatWebAppDescription) it.next();
                //System.err.println(webAppDesc.toString());
                if (!webapps.contains((webAppDesc.getContextPath()))) {
                    //String desc = pm.getPortletWebApplicationDescription(webAppDesc.getContextPath());
                    //webAppDesc.setDescription("");
                    l.add(webAppDesc);
                }
            }
        }
        return l;
    }


    private void createErrorMessage(FormEvent event, String msg) {
        MessageBoxBean msgBox = event.getMessageBoxBean("msg");
        msgBox.setMessageType(MessageStyle.MSG_ERROR);
        msgBox.setValue(msg);
    }

    private void createSuccessMessage(FormEvent event, String msg) {
        MessageBoxBean msgBox = event.getMessageBoxBean("msg");
        msgBox.setMessageType(MessageStyle.MSG_SUCCESS);
        msgBox.setValue(msg);
    }
}
