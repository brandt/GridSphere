/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.impl;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletRequestImpl;
import org.gridlab.gridsphere.portlet.impl.SportletResponse;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;
import org.gridlab.gridsphere.portletcontainer.GridSphereProperties;
import org.gridlab.gridsphere.portletcontainer.PortletInvoker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

public class GridSphereEventImpl implements GridSphereEvent {

    protected PortletRequest portletRequest;
    protected PortletResponse portletResponse;
    protected PortletContext portletContext;

    protected int PortletComponentID = -1;
    protected DefaultPortletAction action = null;
    protected DefaultPortletMessage message = null;

    public GridSphereEventImpl(PortletContext ctx, HttpServletRequest req, HttpServletResponse res) {
        portletRequest = new SportletRequestImpl(req);
        portletResponse = new SportletResponse(res, portletRequest);
        portletContext = ctx;

        String cidStr = req.getParameter(GridSphereProperties.COMPONENT_ID);
        try {
            PortletComponentID = new Integer(cidStr).intValue();
        } catch (NumberFormatException e) {
        }

        /* This is where a DefaultPortletAction gets put together if one exists */
        String actionStr = portletRequest.getParameter(GridSphereProperties.ACTION);
        if (actionStr != null) {
            System.err.println("Received action: " + actionStr);
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
            System.err.println("Received message: " + messageStr);
            message = new DefaultPortletMessage(messageStr);
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
        return PortletComponentID;
    }

}
