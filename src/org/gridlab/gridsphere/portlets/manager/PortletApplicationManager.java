/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets.manager;

import org.gridlab.gridsphere.event.ActionEvent;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.services.registry.PortletManagerService;
import org.gridlab.gridsphere.portlets.manager.tomcat.TomcatManagerWrapper;
import org.gridlab.gridsphere.portlets.manager.tomcat.TomcatWebAppResult;

import javax.servlet.UnavailableException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * The PortletApplicationManager is a wrapper for the Tomcat manager webapp in 4.1.X which allows dynamic
 * web application management and hence dynamic portlet management. This class needs to be adapted for
 * other servlet containers.
 */
public class PortletApplicationManager extends AbstractPortlet {

    private PortletManagerService portletManager = null;
    private TomcatManagerWrapper tomcat = TomcatManagerWrapper.getInstance();

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        try {
            portletManager = (PortletManagerService)config.getContext().getService(PortletManagerService.class);
        } catch (PortletServiceUnavailableException e) {
            System.err.println("PortletRegistry service unavailable! ");
        } catch (PortletServiceNotFoundException e) {
            System.err.println("PortletRegistryService not found! ");
        }
    }

    public void actionPerformed(ActionEvent event) throws PortletException {
        DefaultPortletAction action = event.getAction();
        PortletRequest req = event.getPortletRequest();
        PortletResponse res = event.getPortletResponse();
        User user = event.getPortletRequest().getUser();
        Map params = action.getParameters();
        String operation = (String)params.get("operation");
        String appName = (String)params.get("context");
        TomcatWebAppResult result = null;
        if ((operation != null) && (appName!= null)) {
            if (operation.equals("start")) {
                result = tomcat.startWebApp(appName);
                portletManager.installPortletWebApplication(appName, req, res);
            } else if (operation.equals("stop")) {
                portletManager.destroyPortletWebApplication(appName, req, res);
                result = tomcat.stopWebApp(appName);
            } else if (operation.equals("reload")) {
                result = tomcat.reloadWebApp(appName);
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
            } else if (operation.equals("install")) {
                String warFile = (String)params.get("warfile");
                result = tomcat.installWebApp(appName, warFile);
                portletManager.installPortletWebApplication(appName, req, res);
            }
        }
        if (result != null) System.err.println("result: " + result.getReturnCode() + " " + result.getDescription());
    }


    public void doView(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        List webapps = portletManager.listPortletWebApplications();
        for (int i = 0; i < webapps.size(); i++) {
            System.err.println("webapp " + i + " " + webapps.get(i));
        }
        TomcatWebAppResult result = tomcat.getWebAppList();
        List allwebapps = result.getWebAppDescriptions();
        System.err.println(result.getReturnCode() + " : " + result.getDescription());
        request.setAttribute("result", result);
        getPortletConfig().getContext().include("/jsp/list.jsp", request, response);
    }

}
