/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: GridSphereEventImpl.java 5032 2006-08-17 18:15:06Z novotny $
 */
package org.gridsphere.portletcontainer.impl;

import org.gridsphere.layout.event.PortletComponentEvent;
import org.gridsphere.portlet.*;
import org.gridsphere.portlet.impl.*;
import org.gridsphere.portletcontainer.GridSphereEvent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * The <code>GridSphereEventImpl</code> is an implementation of the <code>GridSphereEvent</code> interface.
 * <p/>
 * A <code>GridSphereEvent</code> represents a general portlet container
 * event. The <code>GridSphereEvent</code> is passed into components that
 * need to access the <code>PortletRequest</code>
 * <code>PortletResponse</code> objects, such as the layout components.
 */
public class GridSphereEventImpl implements GridSphereEvent {

    protected static PortletLog log = SportletLog.getInstance(GridSphereEventImpl.class);
    protected SportletRequest portletRequest;
    protected SportletResponse portletResponse;
    protected PortletContext portletContext;

    protected String componentID = null;
    protected String layoutID = null;

    protected DefaultPortletAction action = null;
    protected PortletMessage message = null;

    protected Stack events = null;

    public GridSphereEventImpl(PortletContext ctx, HttpServletRequest req, HttpServletResponse res) {

        portletRequest = new SportletRequest(req);
        portletResponse = new SportletResponse(res, portletRequest);
        portletContext = ctx;

        events = new Stack();

        String compVar = (String)req.getAttribute(SportletProperties.COMPONENT_ID_VAR);
        if (compVar == null) compVar = SportletProperties.COMPONENT_ID;
        componentID = req.getParameter(compVar);
        if (componentID == null) {
            log.debug("Received a null component ID");
            componentID = "";
        } else {
            log.debug("Received cid= " + componentID);
        }

        layoutID = req.getParameter(SportletProperties.LAYOUT_PAGE_PARAM);
        if (layoutID == null) {
            log.debug("Received a null layout ID");
            layoutID = "";
        } else {
            log.debug("Received layout id= " + layoutID);
            req.setAttribute(SportletProperties.LAYOUT_PAGE, layoutID);
        }


        action = createAction(portletRequest);

        //log.debug("Received action=" + action);
        /* This is where a DefaultPortletMessage gets put together if one exists */
        String messageStr = portletRequest.getParameter(SportletProperties.DEFAULT_PORTLET_MESSAGE);
        if (messageStr != null) {
            log.debug("Received message: " + messageStr);
            message = new DefaultPortletMessage(messageStr);
        }
    }

    public static DefaultPortletAction createAction(Object request) {
        /* This is where a DefaultPortletAction gets put together if one exists */
        DefaultPortletAction myaction = null;
        Enumeration e = null;
        String name, newname, value;
        String actionStr = null;
        if (request instanceof PortletRequest) {
            actionStr = ((PortletRequest)request).getParameter(SportletProperties.DEFAULT_PORTLET_ACTION);
        }
        if (request instanceof javax.portlet.PortletRequest) {
            actionStr = ((javax.portlet.PortletRequest)request).getParameter(SportletProperties.DEFAULT_PORTLET_ACTION);
        }
        if (actionStr != null) {
            myaction = new DefaultPortletAction(actionStr);
            String prefix = null;
            if (request instanceof PortletRequest) {
                prefix = ((PortletRequest)request).getParameter(SportletProperties.PREFIX);
                e =  ((PortletRequest)request).getParameterNames();
            }
            if (request instanceof javax.portlet.PortletRequest) {
                prefix = ((javax.portlet.PortletRequest)request).getParameter(SportletProperties.PREFIX);
                e = ((javax.portlet.PortletRequest)request).getParameterNames();
            }
            if ((prefix != null) && (e != null)) {
                while (e.hasMoreElements()) {
                    name = ((String) e.nextElement());
                    if (name.startsWith(prefix)) {
                        newname = name.substring(prefix.length() + 1);
                        value = "";
                        if (request instanceof PortletRequest) {
                            value =  ((PortletRequest)request).getParameter(name);
                        }
                        if (request instanceof javax.portlet.PortletRequest) {
                            value = ((javax.portlet.PortletRequest)request).getParameter(name);
                        }
                        //newname = decodeUTF8(newname);
                        //value = decodeUTF8(newname);
                        myaction.addParameter(newname, value);
                    }
                }
            }
        } else {
            if (request instanceof PortletRequest) {
                e =  ((PortletRequest)request).getParameterNames();
            }
            if (request instanceof javax.portlet.PortletRequest) {
                e = ((javax.portlet.PortletRequest)request).getParameterNames();
            }
            if (e != null) {

                /// Check to see if action is of form action_name generated by submit button
                while (e.hasMoreElements()) {
                    name = (String) e.nextElement();
                    if (name.startsWith(SportletProperties.DEFAULT_PORTLET_ACTION)) {
                        // check for parameter names and values

                        name = name.substring(SportletProperties.DEFAULT_PORTLET_ACTION.length() + 1);

                        StringTokenizer st = new StringTokenizer(name, "&");
                        if (st.hasMoreTokens()) {
                            newname = st.nextToken();
                        } else {
                            newname = "";
                        }
                        myaction = new DefaultPortletAction(newname);
                        log.debug("Received " + myaction);
                        String paramName;
                        String paramVal = "";
                        Map tmpParams = new HashMap();
                        String prefix = "";
                        while (st.hasMoreTokens()) {
                            // now check for "=" separating name and value
                            String namevalue = st.nextToken();
                            int hasvalue = namevalue.indexOf("=");
                            if (hasvalue > 0) {
                                paramName = namevalue.substring(0, hasvalue);
                                paramVal = namevalue.substring(hasvalue + 1);
                                if (paramName.equals(SportletProperties.PREFIX)) {
                                    prefix = paramVal;
                                } else {
                                    tmpParams.put(paramName, paramVal);
                                }
                            } else {
                                tmpParams.put(namevalue, paramVal);
                            }
                        }
                        // put unprefixed params in action
                        Iterator it = tmpParams.keySet().iterator();
                        while (it.hasNext()) {
                            String n = (String) it.next();
                            String v = (String) tmpParams.get(n);
                            if (!prefix.equals("")) {
                                n = n.substring(prefix.length() + 1);
                            }

                            myaction.addParameter(n, v);
                        }
                    }
                }
            }
        }
        return myaction;
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
        return (action != null);
    }

    public boolean hasMessage() {
        return false;
    }

    public PortletMessage getMessage() {
        return message;
    }

    public String getComponentID() {
        return componentID;
    }

    public String getLayoutID() {
        return layoutID;
    }

    public void addNewRenderEvent(PortletComponentEvent evt) {
        if (evt != null) events.push(evt);
    }

    public PortletComponentEvent getLastRenderEvent() {
        return (events.isEmpty() ? null : (PortletComponentEvent) events.pop());
    }
}
