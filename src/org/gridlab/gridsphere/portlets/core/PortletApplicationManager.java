/*
 * Created by IntelliJ IDEA.
 * User: novotny
 * Date: Dec 22, 2002
 * Time: 11:52:58 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.portlets.core;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.event.ActionEvent;
import org.gridlab.gridsphere.portlets.core.beans.TomcatManagerWrapper;
import org.gridlab.gridsphere.portlets.core.beans.TomcatWebAppResult;
import org.gridlab.gridsphere.services.registry.PortletRegistryService;

import javax.servlet.UnavailableException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

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

    public void actionPerformed(ActionEvent evt) throws PortletException {



    }

    public void doView(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        List webapps = registryService.listPortletWebApplications();
        for (int i = 0; i < webapps.size(); i++) {
            System.err.println("webapp " + i + " " + webapps.get(i));
        }
        TomcatWebAppResult result = manager.getWebAppList(webapps);

        System.err.println(result.getReturnCode() + " : " + result.getDescription());
        request.setAttribute("result", result);
        getPortletConfig().getContext().include("/jsp/manager/list.jsp", request, response);
    }

}
