/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets.core;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;

import org.gridlab.gridsphere.services.container.registry.PortletRegistryService;
import org.gridlab.gridsphere.event.ActionEvent;


import javax.servlet.UnavailableException;
import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;

public class SubscriptionSportlet extends AbstractPortlet {

    private PortletRegistryService registryService = null;

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        PortletContext context = config.getContext();
        try {
            registryService = (PortletRegistryService)context.getService(PortletRegistryService.class);
        } catch (PortletServiceUnavailableException e) {
            throw new UnavailableException("in PortletInit: Service instance unavailable: " + e.toString());
        } catch (PortletServiceNotFoundException s) {
            throw new UnavailableException("in PortletInit: Service instance not found: " + s.toString());
        }
    }

    public void actionPerformed(ActionEvent evt) { }

    public void execute(PortletRequest request) throws PortletException {
        // Convert PortletInfo Registry information into bean

    }

    public void service(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        getPortletConfig().getContext().include("/jsp/subscription.jsp", request, response);
    }

}
