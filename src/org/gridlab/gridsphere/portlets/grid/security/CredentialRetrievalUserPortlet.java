 /*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets.grid.security;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.event.ActionEvent;

import javax.servlet.UnavailableException;
import java.io.IOException;
import java.io.PrintWriter;

public class CredentialRetrievalUserPortlet extends AbstractPortlet {
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
        //'Get the portlet request and response
        PortletRequest request = event.getPortletRequest();
        PortletResponse response = event.getPortletResponse();
        // Get instance of user manager bean
        CredentialRetrievalUserBean credentialRetrievalUserBean = getCredentialRetrievalUserBean(request, response);
        // Then perform given action
        credentialRetrievalUserBean.doViewAction(event);
        getPortletLog().debug("Exiting actionPerformed()");
    }

    public void doView(PortletRequest request, PortletResponse response)
            throws PortletException, IOException {
        getPortletLog().debug("Entering doView()");
        // Get instance of user manager bean
        CredentialRetrievalUserBean credentialRetrievalUserBean = getCredentialRetrievalUserBean(request, response);
        // If no action performed, then perform list users
        if (credentialRetrievalUserBean.getActionEvent() == null) {
            credentialRetrievalUserBean.doDefaultViewAction();
        }
        // Get next page to display
        String nextPage = credentialRetrievalUserBean.getPage();
        System.out.println("CredentialRetrievalUserPortlet nextPage = " + nextPage);
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
        CredentialRetrievalUserBean credentialRetrievalUserBean = getCredentialRetrievalUserBean(request, response);
        // Get next title to display
        String title = credentialRetrievalUserBean.getTitle();
        // Print the given title
        response.getWriter().println(title);
        getPortletLog().debug("Exiting doTitle()");
    }

    private CredentialRetrievalUserBean getCredentialRetrievalUserBean(PortletRequest request,
                                               PortletResponse response)
            throws PortletException {
        getPortletLog().debug("Entering getCredentialRetrievalUserBean()");
        CredentialRetrievalUserBean credentialRetrievalUserBean =
                (CredentialRetrievalUserBean)request.getAttribute("credentialRetrievalUserBean");
        if (credentialRetrievalUserBean == null) {
            getPortletLog().debug("Creating instance of CredentialRetrievalUserBean");
            PortletConfig config = getPortletConfig();
            credentialRetrievalUserBean = new CredentialRetrievalUserBean(config, request, response);
            request.setAttribute("credentialRetrievalUserBean", credentialRetrievalUserBean);
        }
        getPortletLog().debug("Exiting getCredentialRetrievalUserBean()");
        return credentialRetrievalUserBean;
    }
}
