/*
 * $Id$
 */
package org.gridlab.gridsphere.portlets.core;

import org.gridlab.gridsphere.event.ActionEvent;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlets.core.beans.AccessControllerBean;

import javax.servlet.UnavailableException;
import java.io.IOException;
import java.io.PrintWriter;

public class AccessControllerPortlet extends AbstractPortlet {

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
        // Get instance of group manager bean
        AccessControllerBean aclManagerBean = getAccessControlManagerBean(request, response);
        // Then perform given action
        aclManagerBean.doViewAction(action);
        getPortletLog().debug("Exiting actionPerformed()");
    }

    public void doView(PortletRequest request, PortletResponse response)
            throws PortletException, IOException {
        getPortletLog().debug("Entering doView()");
        // Get instance of group manager bean
        AccessControllerBean aclManagerBean = getAccessControlManagerBean(request, response);
        // If no action performed, then perform list groups
        if (aclManagerBean.getActionPerformed() == null) {
            aclManagerBean.doDefaultViewAction();
        }
        // Get next page to display
        String nextPage = aclManagerBean.getPage();
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
        // Get instance of group manager bean
        AccessControllerBean aclManagerBean = getAccessControlManagerBean(request, response);
        // Get next title to display
        String title = aclManagerBean.getTitle();
        // Print the given title
        response.getWriter().println(title);
        getPortletLog().debug("Exiting doTitle()");
    }

    private AccessControllerBean getAccessControlManagerBean(PortletRequest request,
                                               PortletResponse response)
            throws PortletException {
        getPortletLog().debug("Entering getAccessControllerBean()");
        AccessControllerBean aclManagerBean =
                (AccessControllerBean)request.getAttribute("aclManagerBean");
        if (aclManagerBean == null) {
            getPortletLog().debug("Creating instance of AccessControllerBean");
            PortletConfig config = getPortletConfig();
            aclManagerBean = new AccessControllerBean(config, request, response);
            request.setAttribute("aclManagerBean", aclManagerBean);
        }
        getPortletLog().debug("Exiting getAccessControllerBean()");
        return aclManagerBean;
    }
}
