/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets.grid.job;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlets.grid.security.CredentialManagerAdminBean;
import org.gridlab.gridsphere.event.ActionEvent;

import javax.servlet.UnavailableException;
import java.io.IOException;
import java.io.PrintWriter;

public class JobManagerPortlet extends AbstractPortlet {

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
        JobManagerBean jobPortletBean = getJobPortletBean(request, response);
        // Then perform given action
        jobPortletBean.doViewAction(action);
        getPortletLog().debug("Exiting actionPerformed()");
    }

    public void doView(PortletRequest request, PortletResponse response)
            throws PortletException, IOException {
        getPortletLog().debug("Entering doView()");
        // Get instance of user manager bean
        JobManagerBean jobPortletBean = getJobPortletBean(request, response);
        // If no action performed, then perform list users
        if (jobPortletBean.getActionPerformed() == null) {
            jobPortletBean.doDefaultViewAction();
        }
        // Get next page to display
        String nextPage = jobPortletBean.getPage();
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
        JobManagerBean jobPortletBean = getJobPortletBean(request, response);
        // Get next title to display
        String title = jobPortletBean.getTitle();
        // Print the given title
        response.getWriter().println(title);
        getPortletLog().debug("Exiting doTitle()");
    }

    private JobManagerBean getJobPortletBean(PortletRequest request,
                                             PortletResponse response)
            throws PortletException {
        getPortletLog().debug("Entering getCredentialManagerAdminBean()");
        JobManagerBean jobPortletBean =
                (JobManagerBean)request.getAttribute("jobPortletBean");
        if (jobPortletBean == null) {
            getPortletLog().debug("Creating instance of CredentialManagerAdminBean");
            PortletConfig config = getPortletConfig();
            jobPortletBean = new JobManagerBean(config, request, response);
            request.setAttribute("credentialManagerAdminBean", jobPortletBean);
        }
        getPortletLog().debug("Exiting getCredentialManagerAdminBean()");
        return jobPortletBean;
    }
}
