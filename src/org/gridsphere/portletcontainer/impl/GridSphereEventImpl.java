/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: GridSphereEventImpl.java 5032 2006-08-17 18:15:06Z novotny $
 */
package org.gridsphere.portletcontainer.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gridsphere.layout.event.PortletComponentEvent;
import org.gridsphere.portlet.impl.*;
import org.gridsphere.portletcontainer.Client;
import org.gridsphere.portletcontainer.DefaultPortletAction;
import org.gridsphere.portletcontainer.DefaultPortletRender;
import org.gridsphere.portletcontainer.GridSphereEvent;

import javax.portlet.*;
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

    protected Log log = LogFactory.getLog(GridSphereEventImpl.class);

    protected HttpServletRequest req;
    protected HttpServletResponse res;

    protected PortletContext portletContext;

    protected RenderRequest renderRequest;
    protected RenderResponse renderResponse;
    protected ActionRequest actionRequest;
    protected ActionResponse actionResponse;

    protected String componentID = null;
    protected String layoutID = null;

    protected DefaultPortletAction action = null;
    protected DefaultPortletRender render = null;

    protected Stack<PortletComponentEvent> events = null;

    public GridSphereEventImpl(PortletContext ctx, HttpServletRequest req, HttpServletResponse res) {

        this.req = req;
        this.res = res;
        this.portletContext = ctx;

        this.renderRequest = new RenderRequestImpl(req, ctx);
        this.renderResponse = new RenderResponseImpl(req, res);
        this.actionRequest = new ActionRequestImpl(req, ctx);
        this.actionResponse = new ActionResponseImpl(req, res);

        renderRequest.setAttribute(SportletProperties.RENDER_REQUEST, renderRequest);
        renderRequest.setAttribute(SportletProperties.RENDER_RESPONSE, renderResponse);

        events = new Stack<PortletComponentEvent>();

        //req.setAttribute(SportletProperties.COMPONENT_ID, componentID);

        componentID = req.getParameter(SportletProperties.COMPONENT_ID);
        if (componentID == null) {
            log.debug("Received a null component ID");
            componentID = "";
        } else {
            log.debug("Received cid= " + componentID);
        }

        layoutID = req.getParameter(SportletProperties.LAYOUT_PAGE_PARAM);

        action = createAction();
        render = createRender();

        if (action != null) {
            log.debug("Received action event=" + action.getName());
        } else if (render != null) {
            log.debug("Received render event=" + render.getName());
        }

    }

    public DefaultPortletAction createAction() {
        DefaultPortletAction myaction = null;
        String actionStr = req.getParameter(SportletProperties.DEFAULT_PORTLET_ACTION);
        if (actionStr != null) {
            myaction = new DefaultPortletAction(actionStr);
            myaction.setParameters(getPhaseParams());
        } else {
            Map<String, String> params = parsePhaseParams(SportletProperties.DEFAULT_PORTLET_ACTION);
            if (params != null) {
                actionStr = (String) params.get(SportletProperties.DEFAULT_PORTLET_ACTION);
                params.remove(SportletProperties.DEFAULT_PORTLET_ACTION);
                myaction = new DefaultPortletAction(actionStr);
                myaction.setParameters(params);
            }
        }
        return myaction;
    }

    public DefaultPortletRender createRender() {
        DefaultPortletRender myrender = null;
        String renderStr = req.getParameter(SportletProperties.DEFAULT_PORTLET_RENDER);
        if (renderStr != null) {
            myrender = new DefaultPortletRender(renderStr);
            myrender.setParameters(getPhaseParams());
        } else {
            Map<String, String> params = parsePhaseParams(SportletProperties.DEFAULT_PORTLET_RENDER);
            if (params != null) {
                renderStr = (String) params.get(SportletProperties.DEFAULT_PORTLET_RENDER);
                params.remove(SportletProperties.DEFAULT_PORTLET_RENDER);
                myrender = new DefaultPortletRender(renderStr);
                myrender.setParameters(params);
            }
        }
        return myrender;
    }

    protected Map<String, String> getPhaseParams() {
        String prefix = null;
        String name, newname, value;
        prefix = req.getParameter(SportletProperties.PREFIX);
        Enumeration e = req.getParameterNames();
        Map<String, String> params = new HashMap<String, String>();
        if ((prefix != null) && (e != null)) {
            while (e.hasMoreElements()) {
                name = ((String) e.nextElement());
                if (name.startsWith(prefix)) {
                    newname = name.substring(prefix.length() + 1);
                    value = req.getParameter(name);
                    params.put(newname, value);
                }
            }
        }
        return params;
    }

    protected Map<String, String> parsePhaseParams(String phase) {
        /* This is where a DefaultPortletAction or a DefaultPortletRender gets put together if one exists */

        Enumeration e = null;
        String name, newname;
        Map<String, String> params = new HashMap<String, String>();
        e = req.getParameterNames();
        if (e != null) {

            /// Check to see if action is of form action_name generated by submit button
            while (e.hasMoreElements()) {
                name = (String) e.nextElement();
                if (name.startsWith(phase)) {
                    // check for parameter names and values

                    name = name.substring(phase.length() + 1);

                    StringTokenizer st = new StringTokenizer(name, "&");
                    if (st.hasMoreTokens()) {
                        newname = st.nextToken();
                    } else {
                        newname = "";
                    }

                    params.put(phase, newname);
                    //log.debug("Received " + myaction);
                    String paramName;
                    String paramVal = "";
                    Map<String, String> tmpParams = new HashMap<String, String>();
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
                    for (Object o : tmpParams.keySet()) {
                        String n = (String) o;
                        String v = (String) tmpParams.get(n);
                        if (!prefix.equals("")) {
                            n = n.substring(prefix.length() + 1);
                        }

                        params.put(n, v);
                    }
                    tmpParams = null;
                    return params;
                }

            }
        }
        return null;
    }

    /**
     * Returns an object representing the client device that the user connects to the portal with.
     *
     * @return the client device
     */
    public Client getClient() {
        Client client = (Client) this.getHttpServletRequest().getSession().getAttribute(SportletProperties.CLIENT);
        if (client == null) {
            client = new ClientImpl(this.getHttpServletRequest());
            this.getHttpServletRequest().getSession().setAttribute(SportletProperties.CLIENT, client);
        }
        return client;
    }

    public HttpServletRequest getHttpServletRequest() {
        return req;
    }

    public HttpServletResponse getHttpServletResponse() {
        return res;
    }

    public RenderRequest getRenderRequest() {
        return renderRequest;
    }

    public RenderResponse getRenderResponse() {
        return renderResponse;
    }

    public ActionRequest getActionRequest() {
        return actionRequest;
    }

    public ActionResponse getActionResponse() {
        return actionResponse;
    }

    public PortletContext getPortletContext() {
        return portletContext;
    }

    public DefaultPortletAction getAction() {
        return action;
    }

    public DefaultPortletRender getRender() {
        return render;
    }

    public void setAction(DefaultPortletAction action) {
        this.action = action;
    }

    public boolean hasAction() {
        return (action != null);
    }

    public boolean hasMessage() {
        return false;
    }

    public void setComponentID(String componentID) {
        this.componentID = componentID;
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
