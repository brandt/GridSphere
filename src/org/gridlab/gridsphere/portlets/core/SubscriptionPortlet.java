/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets.core;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;

import org.gridlab.gridsphere.services.container.registry.PortletRegistryService;
import org.gridlab.gridsphere.services.container.registry.PortletUserRegistryService;
import org.gridlab.gridsphere.services.container.registry.PortletRegistryServiceException;
import org.gridlab.gridsphere.event.ActionEvent;


import javax.servlet.UnavailableException;
import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * The subscription portlet offers portlet access to the portlet registry service to enable users to
 * add and remove portlets from their current subscription list.
 */
public class SubscriptionPortlet extends AbstractPortlet {

    private PortletUserRegistryService userRegistryService = null;

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        PortletContext context = config.getContext();
        try {
            userRegistryService = (PortletUserRegistryService)context.getService(PortletUserRegistryService.class);
        } catch (PortletServiceUnavailableException e) {
            throw new UnavailableException("in PortletInit: Service instance unavailable: " + e.toString());
        } catch (PortletServiceNotFoundException s) {
            throw new UnavailableException("in PortletInit: Service instance not found: " + s.toString());
        }
    }

    public void actionPerformed(ActionEvent evt) throws PortletException {
        PortletAction _action = evt.getAction();
        System.err.println("actionPerformed() in SubscriptionPortlet");

        if (_action instanceof DefaultPortletAction) {
            DefaultPortletAction action = (DefaultPortletAction)_action;
            if (action.getName().equals("reload")) {
                try {
                    userRegistryService.reloadPortlets();
                } catch (PortletRegistryServiceException e) {
                    throw new PortletException("Unable to reload classes: " + e.getMessage());
                }
            }
        }
    }

    public void service(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        PortletURI reloadURI = response.createURI();
        DefaultPortletAction reloadAction = new DefaultPortletAction("reload");
        reloadURI.addAction(reloadAction);
        request.setAttribute("reload", reloadURI.toString());
        getPortletConfig().getContext().include("/jsp/subscription.jsp", request, response);
    }

}
