/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.impl;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.*;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;
import org.gridlab.gridsphere.portletcontainer.GridSphereProperties;
import org.gridlab.gridsphere.services.core.security.acl.AccessControlManagerService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * The <code>GridSphereEventImpl</code> is an implementation of the <code>GridSphereEvent</code> interface.
 * <p>
 * A <code>GridSphereEvent</code> represents a general portlet container
 * event. The <code>GridSphereEvent</code> is passed into components that
 * need to access the <code>PortletRequest</code>
 * <code>PortletResponse</code> objects, such as the layout components.
 */
public class GridSphereEventImpl implements GridSphereEvent {

    protected transient PortletLog log = SportletLog.getInstance(GridSphereEventImpl.class);
    protected SportletRequest portletRequest;
    protected SportletResponse portletResponse;
    protected PortletContext portletContext;

    protected int portletComponentID = -1;
    protected DefaultPortletAction action = null;
    protected DefaultPortletMessage message = null;

    public GridSphereEventImpl(AccessControlManagerService  aclService, PortletContext ctx, HttpServletRequest req, HttpServletResponse res) {

        portletRequest = new SportletRequestImpl(req);
        portletResponse = new SportletResponse(res, portletRequest);
        portletContext = ctx;

        String cidStr = req.getParameter(GridSphereProperties.COMPONENT_ID);
        try {
            portletComponentID = new Integer(cidStr).intValue();
        } catch (NumberFormatException e) {
           log.debug("Received a non-number portlet component ID: " + cidStr);
        }

        /* This is where a DefaultPortletAction gets put together if one exists */
        String actionStr = portletRequest.getParameter(GridSphereProperties.ACTION);
        if (actionStr != null) {
            log.info("Received action: " + actionStr);
            action = new DefaultPortletAction(actionStr);
            String prefix = portletRequest.getParameter(GridSphereProperties.PREFIX);
            if (prefix != null) {
                Enumeration enum = portletRequest.getParameterNames();
                String name, newname, value;
                while (enum.hasMoreElements()) {
                    name = (String) enum.nextElement();
                    if (name.startsWith(prefix)) {
                        newname = name.substring(prefix.length() + 1);
                        value = portletRequest.getParameter(name);
                        action.addParameter(newname, value);
                    }
                }
            }
        }

        /* This is where a DefaultPortletMessage gets put together if one exists */
        String messageStr = portletRequest.getParameter(GridSphereProperties.MESSAGE);
        if (messageStr != null) {
            log.debug("Received message: " + messageStr);
            message = new DefaultPortletMessage(messageStr);
        }

        /* This is where we get ACL info and update sportlet request */
        User user = portletRequest.getUser();
        if (! (user instanceof GuestUser) ) {
            log.debug("Role information for user: " + user.getUserID());
            List groups = aclService.getGroups(user);
            Iterator git = groups.iterator();
            PortletGroup group = null;
            while (git.hasNext()) {
                group = (PortletGroup)git.next();
                PortletRole role = aclService.getRoleInGroup(portletRequest.getUser(), group);
                portletRequest.setRole(group, role);
                log.debug("Group: " + group.toString() + " Role: " + role.toString());
            }
        }
    }

    public PortletRequest getPortletRequest() {
        return portletRequest;
    }

    public PortletResponse getPortletResponse() {
        return portletResponse;
    }

    public PortletContext getPortletContext() {
        return portletContext;
    }

    public DefaultPortletAction getAction() {
        return action;
    }

    public boolean hasAction() {
        return (action != null) ? true : false;
    }

    public boolean hasMessage() {
        return false;
    }

    public DefaultPortletMessage getMessage() {
        return message;
    }

    public int getPortletComponentID() {
        return portletComponentID;
    }

}
