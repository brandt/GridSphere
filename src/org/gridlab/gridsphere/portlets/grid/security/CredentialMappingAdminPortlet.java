/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Feb 14, 2003
 * Time: 6:16:50 AM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.portlets.grid.security;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.event.ActionEvent;

import javax.servlet.UnavailableException;
import java.io.IOException;
import java.io.PrintWriter;

public class CredentialMappingAdminPortlet extends AbstractPortlet {
    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        getPortletLog().info("Exiting init()");
    }

    public void initConcrete(PortletSettings settings) throws UnavailableException {
        super.initConcrete(settings);
        getPortletLog().info("Exiting initConcrete()");
    }

    public void actionPerformed(ActionEvent event) throws PortletException {
        getPortletLog().debug("Entering actionPerformed()");
        PortletAction action = event.getAction();
        //'Get the portlet request and response
        PortletRequest request = event.getPortletRequest();
        PortletResponse response = event.getPortletResponse();
        // Get instance of user manager bean
        CredentialMappingAdminBean credentialMappingAdminBean = getCredentialMappingAdminBean(request, response);
        // Then perform given action
        credentialMappingAdminBean.doViewAction(action);
        getPortletLog().debug("Exiting actionPerformed()");
    }

    public void doView(PortletRequest request, PortletResponse response)
            throws PortletException, IOException {
        getPortletLog().debug("Entering doView()");
        // Get instance of user manager bean
        CredentialMappingAdminBean credentialMappingAdminBean = getCredentialMappingAdminBean(request, response);
        // If no action performed, then perform list users
        if (credentialMappingAdminBean.getActionPerformed() == null) {
            credentialMappingAdminBean.doDefaultViewAction();
        }
        // Get next page to display
        String nextPage = credentialMappingAdminBean.getPage();
        // Include the given page
        getPortletConfig().getContext().include(nextPage, request, response);
        getPortletLog().debug("Exiting doView()");
    }

    public void doEdit(PortletRequest request, PortletResponse response)
            throws PortletException, IOException {
        getPortletLog().debug("Entering doEdit()");
        PrintWriter out = response.getWriter();
        out.println("Edit mode not yet implemented.");
        getPortletLog().debug("Exiting doEdit()");
    }

    public void doTitle(PortletRequest request, PortletResponse response)
            throws PortletException, IOException {
        getPortletLog().debug("Entering doTitle()");
        // Get instance of user manager bean
        CredentialMappingAdminBean credentialMappingAdminBean = getCredentialMappingAdminBean(request, response);
        // Get next title to display
        String title = credentialMappingAdminBean.getTitle();
        // Print the given title
        response.getWriter().println(title);
        getPortletLog().debug("Exiting doTitle()");
    }

    private CredentialMappingAdminBean getCredentialMappingAdminBean(PortletRequest request,
                                               PortletResponse response)
            throws PortletException {
        getPortletLog().debug("Entering getCredentialMappingAdminBean()");
        CredentialMappingAdminBean credentialMappingAdminBean =
                (CredentialMappingAdminBean)request.getAttribute("credentialMappingAdminBean");
        if (credentialMappingAdminBean == null) {
            getPortletLog().debug("Creating instance of CredentialMappingAdminBean");
            PortletConfig config = getPortletConfig();
            credentialMappingAdminBean = new CredentialMappingAdminBean(config, request, response);
            request.setAttribute("credentialMappingAdminBean", credentialMappingAdminBean);
        }
        getPortletLog().debug("Exiting getCredentialMappingAdminBean()");
        return credentialMappingAdminBean;
    }
}
