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
import org.gridlab.gridsphere.portlets.core.beans.AccessControllerBean;

import javax.servlet.UnavailableException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Iterator;
import java.util.List;

public class AccessControllerPortlet extends AbstractPortlet {

    private static PortletLog _log = SportletLog.getInstance(AccessControllerPortlet.class);

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        _log.info("Exiting init()");
    }

    public void initConcrete(PortletSettings settings) throws UnavailableException {
        super.initConcrete(settings);
        _log.info("Exiting initConcrete()");
    }

    public void actionPerformed(ActionEvent event) throws PortletException {
        _log.debug("Entering actionPerformed()");
        PortletAction action = event.getAction();
        //'Get the portlet request and response
        PortletRequest request = event.getPortletRequest();
        PortletResponse response = event.getPortletResponse();
        // Get instance of group manager bean
        AccessControllerBean aclManagerBean = getAccessControlManagerBean(request, response);
        // Then perform given action
        aclManagerBean.doAction(action);
        _log.debug("Exiting actionPerformed()");
    }

    public void doView(PortletRequest request, PortletResponse response)
            throws PortletException, IOException {
        _log.debug("Entering doView()");
        // Get instance of group manager bean
        AccessControllerBean aclManagerBean = getAccessControlManagerBean(request, response);
        // If no action performed, then perform list groups
        if (aclManagerBean.getActionPerformed() == null) {
            aclManagerBean.doListGroups();
        }
        // Get next page to display
        String nextPage = aclManagerBean.getNextPage();
        // Include the given page
        getPortletConfig().getContext().include(nextPage, request, response);
        _log.debug("Exiting doView()");
    }

    public void doEdit(PortletRequest request, PortletResponse response)
            throws PortletException, IOException {
        _log.debug("Entering doEdit()");
        PrintWriter out = response.getWriter();
        out.println("Edit mode not yet implemented.");
        _log.debug("Exiting doEdit()");
    }

    public void doTitle(PortletRequest request, PortletResponse response)
            throws PortletException, IOException {
        _log.debug("Entering doTitle()");
        // Get instance of group manager bean
        AccessControllerBean aclManagerBean = getAccessControlManagerBean(request, response);
        // Get next title to display
        String title = aclManagerBean.getNextTitle();
        // Print the given title
        response.getWriter().println(title);
        _log.debug("Exiting doTitle()");
    }

    private AccessControllerBean getAccessControlManagerBean(PortletRequest request,
                                               PortletResponse response)
            throws PortletException {
        _log.debug("Entering getAccessControllerBean()");
        AccessControllerBean aclManagerBean =
                (AccessControllerBean)request.getAttribute(AccessControllerBean.ATTRIBUTE_ACL_MANAGER_BEAN);
        if (aclManagerBean == null) {
            _log.debug("Creating instance of acl manager bean");
            PortletConfig config = getPortletConfig();
            aclManagerBean = new AccessControllerBean(config, request, response);
            request.setAttribute(AccessControllerBean.ATTRIBUTE_ACL_MANAGER_BEAN, aclManagerBean);
        }
        _log.debug("Exiting getAccessControllerBean()");
        return aclManagerBean;
    }
}
