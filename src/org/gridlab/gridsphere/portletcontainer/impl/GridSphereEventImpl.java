/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.impl;

import org.gridlab.gridsphere.portlet.DefaultPortletAction;
import org.gridlab.gridsphere.portlet.PortletContext;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletResponse;
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

    public boolean hasMessagingAction() {
        return false;
    }

    public int getPortletComponentID() {
        return PortletComponentID;
    }

}
