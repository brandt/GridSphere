/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.impl;

import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletContext;
import org.gridlab.gridsphere.portlet.AbstractPortlet;
import org.gridlab.gridsphere.portlet.DefaultPortletAction;
import org.gridlab.gridsphere.portlet.impl.*;
import org.gridlab.gridsphere.portletcontainer.*;
import org.gridlab.gridsphere.services.security.acl.AccessControlService;
import org.gridlab.gridsphere.services.registry.PortletManagerService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletConfig;
import java.util.Enumeration;

public class GridSphereEventImpl implements GridSphereEvent {

    protected SportletRequest portletRequest;
    protected SportletResponse portletResponse;
    protected PortletContext portletContext;

    protected int PortletComponentID = -1;
    protected String ActivePortletID = null;
    protected DefaultPortletAction action = null;
    protected PortletEventDispatcher eventDispatcher = null;

    public GridSphereEventImpl(PortletContext ctx, HttpServletRequest req, HttpServletResponse res) {
        portletRequest = new SportletRequestImpl(req);
        portletResponse = new SportletResponse(res, portletRequest);
        portletContext = ctx;

        eventDispatcher = new PortletEventDispatcher(portletRequest, portletResponse);
        String cidStr = req.getParameter(GridSphereProperties.COMPONENT_ID);
        try {
            PortletComponentID = new Integer(cidStr).intValue();
        } catch (NumberFormatException e) {
        }

        ActivePortletID = req.getParameter(GridSphereProperties.PORTLETID);

        /* This is where a DefaultPortletAction gets put together if one exists */
        String actionStr = portletRequest.getParameter(GridSphereProperties.ACTION);
        action = new DefaultPortletAction(actionStr);
        String prefix = portletRequest.getParameter(GridSphereProperties.PREFIX);
        if (prefix != null) {
            Enumeration enum = portletRequest.getParameterNames();
            String name, newname, value;
            while (enum.hasMoreElements()) {
                name = (String)enum.nextElement();
                if (name.startsWith(prefix)) {
                    newname = name.substring(prefix.length()+1);
                    value = portletRequest.getParameter(name);
                    action.addParameter(newname, value);
                }
            }
        }
    }

    public PortletEventDispatcher getPortletEventDispatcher() {
        return eventDispatcher;
    }

    public SportletRequest getSportletRequest() {
        return portletRequest;
    }

    public SportletResponse getSportletResponse() {
        return portletResponse;
    }

    public PortletContext getPortletContext() {
        return portletContext;
    }

    public DefaultPortletAction getAction() {
        return action;
    }

    public boolean hasAction() {
        if (getPortletComponentID() != -1) return true;
        return false;
    }

    public boolean hasMessagingAction() {
        return false;
    }

    public SportletURI createNewAction(int PortletComponentID, String ActivePortletID) {
        SportletURI sportletURI = new SportletURI(portletResponse, portletRequest.getContextPath());
        String sid = new Integer(PortletComponentID).toString();
        sportletURI.addParameter(GridSphereProperties.COMPONENT_ID, sid);
        if (ActivePortletID != null) {
            sportletURI.addParameter(GridSphereProperties.PORTLETID, ActivePortletID);
        }
        return sportletURI;
    }

    public int getPortletComponentID() {
        return PortletComponentID;
    }

    public String getActivePortletID() {
        return ActivePortletID;
    }

}
