/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets.manager;

import org.gridlab.gridsphere.event.ActionEvent;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.services.registry.PortletRegistryService;
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

    private TomcatManagerWrapper manager = TomcatManagerWrapper.getInstance();

    private PortletRegistryService registryService = null;

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        try {
            registryService = (PortletRegistryService)config.getContext().getService(PortletRegistryService.class);
        } catch (PortletServiceUnavailableException e) {
            System.err.println("PortletRegistry service unavailable! ");
        } catch (PortletServiceNotFoundException e) {
            System.err.println("PortletRegistryService not found! ");
        }
    }

    public void actionPerformed(ActionEvent event) throws PortletException {
        DefaultPortletAction action = event.getAction();
        User user = event.getPortletRequest().getUser();
        Map params = action.getParameters();
        String operation = (String)params.get("operation");
        String appName = (String)params.get("context");
        TomcatWebAppResult result = null;
        if ((operation != null) && (appName!= null)) {
            if (operation.equals("start")) {
                result = manager.startWebApp(appName);
            } else if (operation.equals("stop")) {
                result = manager.stopWebApp(appName);
                registryService.removePortletWebApplication(user, appName);
            } else if (operation.equals("reload")) {
                result = manager.reloadWebApp(appName);
                registryService.reloadPortletWebApplication(user, appName);
            } else if (operation.equals("remove")) {
                result = manager.removeWebApp(appName);
                registryService.removePortletWebApplication(user, appName);
            } else if (operation.equals("deploy")) {
                result = manager.deployWebApp(appName);
                registryService.addPortletWebApplication(user, appName);
            } else if (operation.equals("undeploy")) {
                result = manager.undeployWebApp(appName);
                registryService.removePortletWebApplication(user, appName);
            } else if (operation.equals("deploy")) {
                String warFile = (String)params.get("warfile");
                result = manager.installWebApp(appName, warFile);
                registryService.addPortletWebApplication(user, appName);
            }
        }
        if (result != null) System.err.println("result: " + result.getReturnCode() + " " + result.getDescription());
    }


    public void doView(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        List webapps = registryService.listPortletWebApplications();
        for (int i = 0; i < webapps.size(); i++) {
            System.err.println("webapp " + i + " " + webapps.get(i));
        }
        TomcatWebAppResult result = manager.getWebAppList();
        List allwebapps = result.getWebAppDescriptions();
        //Iterator it = webapps.iterator();
        //List newlist = new Vector();
        //while (it.hasNext()) {
        //    TomcatWebAppResult.TomcatWebAppDescription d = (TomcatWebAppResult.TomcatWebAppDescription)it.next();
        //    if (list.contains(d.getContextPath().substring(1)))
        // }

        System.err.println(result.getReturnCode() + " : " + result.getDescription());
        request.setAttribute("result", result);
        getPortletConfig().getContext().include("/jsp/list.jsp", request, response);
    }

}
