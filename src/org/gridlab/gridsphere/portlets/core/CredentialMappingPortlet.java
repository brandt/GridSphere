/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Feb 14, 2003
 * Time: 6:16:50 AM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.portlets.core;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.event.ActionEvent;
import org.gridlab.gridsphere.portlets.core.beans.CredentialManagerBean;

import javax.servlet.UnavailableException;
import java.io.IOException;
import java.io.PrintWriter;

public class CredentialMappingPortlet extends AbstractPortlet {
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
        CredentialManagerBean credentialManagerBean = getCredentialManagerBean(request, response);
        // Then perform given action
        credentialManagerBean.doAction(action);
        getPortletLog().debug("Exiting actionPerformed()");
    }

    public void doView(PortletRequest request, PortletResponse response)
            throws PortletException, IOException {
        getPortletLog().debug("Entering doView()");
        // Get instance of user manager bean
        CredentialManagerBean credentialManagerBean = getCredentialManagerBean(request, response);
        // If no action performed, then perform list users
        if (credentialManagerBean.getActionPerformed() == null) {
            credentialManagerBean.doListCredentialMapping();
        }
        // Get next page to display
        String nextPage = credentialManagerBean.getNextPage();
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
        CredentialManagerBean credentialManagerBean = getCredentialManagerBean(request, response);
        // Get next title to display
        String title = credentialManagerBean.getNextTitle();
        // Print the given title
        response.getWriter().println(title);
        getPortletLog().debug("Exiting doTitle()");
    }

    private CredentialManagerBean getCredentialManagerBean(PortletRequest request,
                                               PortletResponse response)
            throws PortletException {
        getPortletLog().debug("Entering getCredentialManagerBean()");
        CredentialManagerBean credentialManagerBean =
                (CredentialManagerBean)request.getAttribute("credentialManagerBean");
        if (credentialManagerBean == null) {
            getPortletLog().debug("Creating instance of user manager bean");
            PortletConfig config = getPortletConfig();
            credentialManagerBean = new CredentialManagerBean(config, request, response);
            request.setAttribute("credentialManagerBean", credentialManagerBean);
        }
        getPortletLog().debug("Exiting getCredentialManagerBean()");
        return credentialManagerBean;
    }
}
