/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: PortletApplicationManager.java 5032 2006-08-17 18:15:06Z novotny $
 */
package org.gridsphere.portlets.core.admin.portlets;

import org.gridsphere.portletcontainer.ApplicationPortlet;
import org.gridsphere.portletcontainer.DefaultPortletAction;
import org.gridsphere.portletcontainer.PortletStatus;
import org.gridsphere.portletcontainer.PortletWebApplication;
import org.gridsphere.provider.event.jsr.ActionFormEvent;
import org.gridsphere.provider.event.jsr.RenderFormEvent;
import org.gridsphere.provider.portlet.jsr.ActionPortlet;
import org.gridsphere.provider.portlet.jsr.PortletServlet;
import org.gridsphere.provider.portletui.beans.*;
import org.gridsphere.provider.portletui.model.DefaultTableModel;
import org.gridsphere.services.core.registry.PortletManagerService;
import org.gridsphere.services.core.registry.PortletRegistryService;
import org.gridsphere.services.core.tomcat.TomcatManagerException;
import org.gridsphere.services.core.tomcat.TomcatManagerService;
import org.gridsphere.services.core.tomcat.TomcatWebAppDescription;
import org.gridsphere.services.core.tomcat.TomcatWebAppResult;

import javax.portlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;

/**
 * The PortletApplicationManager is a wrapper for the Tomcat manager webapp which allows dynamic
 * ui application management and hence dynamic portlet management. This class needs to be adapted for
 * other servlet containers.
 */
public class PortletApplicationManager extends ActionPortlet {

    public static final String LIST_APPS_JSP = "admin/portlets/listPortletApps.jsp";
    public static final String VIEW_APP_JSP = "admin/portlets/viewPortletApp.jsp";
    public static final String HELP_JSP = "admin/portlets/help.jsp";

    private TomcatManagerService tomcatManager;
    private PortletManagerService portletManager = null;
    private PortletRegistryService registry = null;

    public void init(PortletConfig config) throws PortletException {
        super.init(config);
        portletManager = (PortletManagerService) createPortletService(PortletManagerService.class);
        tomcatManager = (TomcatManagerService) createPortletService(TomcatManagerService.class);
        registry = (PortletRegistryService) createPortletService(PortletRegistryService.class);
        DEFAULT_VIEW_PAGE = "listPortlets";
        DEFAULT_HELP_PAGE = HELP_JSP;
    }

    public void listPortlets(ActionFormEvent event) {
        setNextState(event.getActionRequest(), "listPortlets");
    }

    public void listPortlets(RenderFormEvent event) {

        PortletRequest req = event.getRenderRequest();
        PortletResponse res = event.getRenderResponse();
        List portletapps;
        List otherapps;
        try {
            portletapps = getPortletAppList(req, res);
            otherapps = getNonPortletAppList(req, res);
            log.info("result is OK");
        } catch (TomcatManagerException e) {
            log.error("Unable to retrieve list of portlets.", e);
            MessageBoxBean msg = event.getMessageBoxBean("msg");
            msg.setValue(e.getMessage());
            msg.setMessageType(MessageStyle.MSG_ERROR);
            portletapps = new ArrayList();
            otherapps = new ArrayList();
        }

        event.getRenderRequest().setAttribute("result", portletapps);
        event.getRenderRequest().setAttribute("others", otherapps);
        //if (result != null) log.debug("result: " + result.getReturnCode() + " " + result.getDescription());
        setNextState(req, LIST_APPS_JSP);

    }

    public void doPortletManager(ActionFormEvent event) throws PortletException {
        log.debug("In doPortletManager");
        DefaultPortletAction action = event.getAction();
        PortletRequest req = event.getActionRequest();
        PortletResponse res = event.getActionResponse();

        HttpServletRequest hReq = (HttpServletRequest) req;
        HttpServletResponse hRes = (HttpServletResponse) res;

        MessageBoxBean msg = event.getMessageBoxBean("msg");


        Map params = action.getParameters();
        String operation = (String) params.get("operation");
        String appName = (String) params.get("context");
        TomcatWebAppResult result = null;

        try {
            if ((operation != null) && (appName != null)) {
                if (operation.equals("start")) {
                    result = tomcatManager.startWebApp(req, res, appName);
                    this.createSuccessMessage(event, this.getLocalizedText(req, "PORTLET_SUC_TOMCAT"));
                    portletManager.initPortletWebApplication(appName, hReq, hRes);
                } else if (operation.equals("stop")) {
                    portletManager.destroyPortletWebApplication(appName, hReq, hRes);
                    result = tomcatManager.stopWebApp(req, res, appName);
                    this.createSuccessMessage(event, this.getLocalizedText(req, "PORTLET_SUC_TOMCAT"));
                } else if (operation.equals("reload")) {
                    portletManager.destroyPortletWebApplication(appName, hReq, hRes);
                    result = tomcatManager.stopWebApp(req, res, appName);
                    result = tomcatManager.startWebApp(req, res, appName);
                    this.createSuccessMessage(event, this.getLocalizedText(req, "PORTLET_SUC_TOMCAT"));
                    portletManager.initPortletWebApplication(appName, hReq, hRes);
                } else if (operation.equals("remove")) {
                    portletManager.destroyPortletWebApplication(appName, hReq, hRes);
                    result = tomcatManager.removeWebApp(req, res, appName);
                    log.debug("removing application tab :" + appName);
                    this.createSuccessMessage(event, this.getLocalizedText(req, "PORTLET_SUC_TOMCAT"));
                } else if (operation.equals("deploy")) {
                    result = tomcatManager.deployWebApp(req, res, appName);
                    result = tomcatManager.startWebApp(req, res, appName);
                    this.createSuccessMessage(event, this.getLocalizedText(req, "PORTLET_SUC_TOMCAT"));
                    portletManager.initPortletWebApplication(appName, hReq, hRes);
                } else if (operation.equals("undeploy")) {
                    result = tomcatManager.undeployWebApp(req, res, appName);
                    this.createSuccessMessage(event, this.getLocalizedText(req, "PORTLET_SUC_TOMCAT"));
                    portletManager.destroyPortletWebApplication(appName, hReq, hRes);
                }

            }
        } catch (Exception e) {
            log.error("Portlet Manager error", e);
            msg.setKey("PORTLET_ERR_MANAGER");
            msg.setMessageType(MessageStyle.MSG_ERROR);
        }
        req.setAttribute("result", result);
        if (result != null) log.debug("result: " + result.getReturnCode() + " " + result.getDescription());
        setNextState(req, DEFAULT_VIEW_PAGE);
    }

    public void uploadPortletWAR(ActionFormEvent event) throws PortletException {
        PortletRequest req = event.getActionRequest();
        PortletResponse res = event.getActionResponse();
        HttpServletRequest hReq = (HttpServletRequest) req;
        HttpServletResponse hRes = (HttpServletResponse) res;

        try {
            FileInputBean fi = event.getFileInputBean("userfile");

            String fileName = fi.getFileName();
            int fdx = fileName.lastIndexOf(File.separator);
            if (fdx > 0) fileName = fileName.substring(fdx);
            if (fileName.substring(0, 1).equals(File.separator)) fileName = fileName.substring(1);

            log.info("filename = " + fileName);

            String webappPath = getPortletContext().getRealPath("");
            int idx = webappPath.lastIndexOf("webapps");


            if (fileName.equals("")) return;

            int isWar = fileName.indexOf(".war");
            if (isWar > 0) {
                String appName = fileName.substring(0, isWar);
                log.debug("installing and initing webapp: " + appName);

                webappPath = webappPath.substring(0, idx) + "webapps" + File.separator;
                //System.err.println(webappPath + fileName);
                fi.saveFile(webappPath + fileName);

                tomcatManager.installWebApp(req, res, appName, fileName);

                File pfile = new File(webappPath + appName + File.separator + "WEB-INF" + File.separator + "portlet.xml");
                System.err.println(webappPath + appName + File.separator + "WEB-INF" + File.separator + "portlet.xml");
                if (pfile.exists()) {
                    //System.err.println("file exists");
                    portletManager.initPortletWebApplication(appName, hReq, hRes);
                }
                // add portlet app to gridsphere portlet app directory
                String portletAppFile = getPortletContext().getRealPath("/WEB-INF/CustomPortal/portlets/" + appName);
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

    public void deployWebapp(ActionFormEvent event) throws PortletException {

        log.debug("in PortletApplicationManager: deployWebapp");
        PortletRequest req = event.getActionRequest();
        PortletResponse res = event.getActionResponse();
        HttpServletRequest hReq = (HttpServletRequest) req;
        HttpServletResponse hRes = (HttpServletResponse) res;

        try {
            TextFieldBean tf = event.getTextFieldBean("webappNameTF");

            String webappName = tf.getValue();
            if (webappName == null) return;
            String webappPath = getPortletContext().getRealPath("");
            int idx = webappPath.lastIndexOf(File.separator);
            webappPath = webappPath.substring(0, idx + 1);
            tomcatManager.installWebApp(req, res, webappName);

            File pfile = new File(webappPath + webappName + File.separator + "WEB-INF" + File.separator + "portlet.xml");
            System.err.println(webappPath + webappName + File.separator + "WEB-INF" + File.separator + "portlet.xml");
            if (pfile.exists()) {
                portletManager.initPortletWebApplication(webappName, hReq, hRes);
            }

            createSuccessMessage(event, this.getLocalizedText(req, "PORTLET_SUC_DEPLOY") + " " + webappName);
        } catch (Exception e) {
            createErrorMessage(event, this.getLocalizedText(req, "PORTLET_ERR_DEPLOY"));
            log.error("Unable to deploy webapp  ", e);
        }
        setNextState(req, DEFAULT_VIEW_PAGE);
    }

    public void displayWebapp(ActionFormEvent event) {
        ActionRequest req = event.getActionRequest();
        String webapp = event.getAction().getParameter("appname");

        req.setAttribute("webappname", webapp);

        setNextState(req, "displayWebApp");
    }

    public void displayWebApp(RenderFormEvent event) {
        RenderRequest req = event.getRenderRequest();
        RenderResponse res = event.getRenderResponse();
        String webapp = (String) req.getAttribute("webappname");

        PanelBean panel = event.getPanelBean("panel");
        FrameBean frame = new FrameBean();
        DefaultTableModel model = new DefaultTableModel();


        TableRowBean tr = new TableRowBean();
        tr.setHeader(true);
        TableCellBean tc = new TableCellBean();
        TextBean text = new TextBean();
        text.setValue(portletManager.getPortletWebApplicationDescription(webapp));
        tc.addBean(text);
        tr.addBean(tc);
        tc = new TableCellBean();
        text = new TextBean();
        text.setValue(this.getLocalizedText(req, "SUBSCRIPTION_DESC"));
        tc.addBean(text);
        tr.addBean(tc);

        TableCellBean tc2 = new TableCellBean();
        TextBean text2 = new TextBean();
        text2.setValue(this.getLocalizedText(req, "PORTLET_STATUS"));
        tc2.addBean(text2);
        tr.addBean(tc2);
        model.addTableRowBean(tr);

        List appColl = registry.getApplicationPortlets(webapp);
        if (appColl.isEmpty()) appColl = registry.getApplicationPortlets(webapp);
        for (Object anAppColl : appColl) {
            ApplicationPortlet app = (ApplicationPortlet) anAppColl;

            System.err.println("app portlet= \n" + app.toString());
            System.err.println("app statusmsg = " + app.getApplicationPortletStatusMessage());
            System.err.println("app status = " + app.getApplicationPortletStatus());

            // System.err.println("conc portlet= \n" + conc.toString());


            String concID = app.getConcretePortletID();

            // we don't want to list PortletServlet loader!
            if (concID.startsWith(PortletServlet.class.getName())) continue;

            TableRowBean newtr = new TableRowBean();
            TableCellBean newtc2 = new TableCellBean();
            TextBean tb = new TextBean();

            // set 2nd column to portlet display name from concrete portlet
            Locale loc = req.getLocale();
            String dispName = app.getDisplayName(loc);
            tb.setValue(dispName);
            newtc2.addBean(tb);
            newtr.addBean(newtc2);
            TableCellBean newtc = new TableCellBean();
            TextBean tb2 = new TextBean();

            // set 3rd column to portlet description from concrete portlet

            //tb2.setValue(conc.getPortletSettings().getDescription(loc, null));
            tb2.setValue(app.getDescription(loc));
            newtc.addBean(tb2);
            newtr.addBean(newtc);

            newtc = new TableCellBean();
            //newtc.setAlign("center");
            tb = new TextBean();


            ImageBean img = new ImageBean();
            if (app.getApplicationPortletStatus().equals(PortletStatus.FAILURE)) {
                img.setSrc(req.getContextPath() + "/themes/brush/default/images/msgicons/portlet-msg-error.gif");
                newtc.addBean(img);
                tb.setValue("&nbsp;&nbsp;&nbsp;" + getLocalizedText(req, "FAILURE"));
                tb.setId(app.getApplicationPortletName());
                tb.setStyle(MessageStyle.MSG_UNDERLINE);
                TooltipBean tt = new TooltipBean();
                tt.setName(app.getApplicationPortletName());
                tt.setId("tt" + app.getApplicationPortletName());
                String status = app.getApplicationPortletStatusMessage();
                tt.setValue(status);
                tt.setRenderResponse(res);
                newtc.addBean(tb);
                newtc.addBean(tt);
            } else {
                img.setSrc(req.getContextPath() + "/themes/brush/default/images/msgicons/portlet-msg-success.gif");
                newtc.addBean(img);
                tb.setValue("&nbsp;&nbsp;&nbsp;" + getLocalizedText(req, "SUCCESS"));
                newtc.addBean(tb);
            }
            newtr.addBean(newtc);
            model.addTableRowBean(newtr);
        }

        frame.setTableModel(model);
        panel.addBean(frame);
        setNextState(req, VIEW_APP_JSP);
    }

    public List getPortletAppList(PortletRequest req, PortletResponse res) throws TomcatManagerException {
        List webapps = portletManager.getPortletWebApplicationNames();

        List l = new ArrayList();
        TomcatWebAppResult result = tomcatManager.getWebAppList(req, res);
        if (result != null) {
            Iterator it = result.getWebAppDescriptions().iterator();
            while (it.hasNext()) {
                TomcatWebAppDescription webAppDesc = (TomcatWebAppDescription) it.next();
                //System.err.println(webAppDesc.toString());
                if (webapps.contains((webAppDesc.getContextPath()))) {
                    String desc = portletManager.getPortletWebApplicationDescription(webAppDesc.getContextPath());
                    webAppDesc.setDescription(desc);
                    PortletWebApplication webapp = registry.getWebApplication(webAppDesc.getContextPath());
                    if (webapp != null) {
                        webAppDesc.setStatus(webapp.getWebApplicationStatus());
                        webAppDesc.setStatusMessage(webapp.getWebApplicationStatusMessage());
                    }
                    l.add(webAppDesc);
                }
            }
        }
        return l;
    }

    public List getNonPortletAppList(PortletRequest req, PortletResponse res) throws TomcatManagerException {
        List webapps = portletManager.getPortletWebApplicationNames();
        List l = new ArrayList();
        TomcatWebAppResult result = tomcatManager.getWebAppList(req, res);
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

}
