/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Jan 17, 2003
 * Time: 11:57:10 AM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.portlets.core;

import org.gridlab.gridsphere.event.ActionEvent;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.*;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portletcontainer.GridSphereProperties;
import org.gridlab.gridsphere.portlets.core.beans.UserManagerBean;

import javax.servlet.UnavailableException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Iterator;
import java.util.List;

public class UserManagerPortlet extends AbstractPortlet {

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
        //PortletAction action = event.getAction();
        //'Get the portlet request and response
        PortletRequest request = event.getPortletRequest();
        PortletResponse response = event.getPortletResponse();
        // Get instance of user manager bean
        UserManagerBean userManagerBean = getUserManagerBean(request, response);
        // Then perform given action
        userManagerBean.doViewAction(event);
        //userManagerBean.doViewAction(action);
        getPortletLog().debug("Exiting actionPerformed()");
    }

    public void doView(PortletRequest request, PortletResponse response)
            throws PortletException, IOException {
        getPortletLog().debug("Entering doView()");
        // Get instance of user manager bean
        UserManagerBean userManagerBean = getUserManagerBean(request, response);
        //if (userManagerBean.getActionPerformed() == null) {
        if (userManagerBean.getActionEvent() == null) {
            userManagerBean.doDefaultViewAction();
        }
        // Get next page from do view
        String nextPage = userManagerBean.getPage();
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
        UserManagerBean userManagerBean = getUserManagerBean(request, response);
        // Get next title to display
        String title = userManagerBean.getTitle();
        // Print the given title
        response.getWriter().println(title);
        getPortletLog().debug("Exiting doTitle()");
    }

    private UserManagerBean getUserManagerBean(PortletRequest request,
                                               PortletResponse response)
            throws PortletException {
        getPortletLog().debug("Entering getUserManagerBean()");
        UserManagerBean userManagerBean =
                (UserManagerBean)request.getAttribute("userManagerBean");
        if (userManagerBean == null) {
            getPortletLog().debug("Creating instance of UserManagerBean");
            PortletConfig config = getPortletConfig();
            userManagerBean = new UserManagerBean(config, request, response);
            request.setAttribute("userManagerBean", userManagerBean);
        }
        getPortletLog().debug("Exiting getUserManagerBean()");
        return userManagerBean;
    }
}
