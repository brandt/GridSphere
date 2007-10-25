/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
package org.gridsphere.portletcontainer;

import org.gridsphere.layout.event.PortletComponentEvent;

import javax.portlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A <code>GridSphereEvent</code> represents a general portlet container
 * event. The <code>GridSphereEvent</code> is passed into components that
 * need to access the <code>PortletRequest</code>
 * <code>PortletResponse</code> objects, such as the layout components.
 */
public interface GridSphereEvent {

    /**
     * Return the portlet context associated with this event
     *
     * @return the portlet context associated with this event
     */
    public PortletContext getPortletContext();

    public HttpServletRequest getHttpServletRequest();

    public HttpServletResponse getHttpServletResponse();

    public RenderRequest getRenderRequest();

    public RenderResponse getRenderResponse();

    public ActionRequest getActionRequest();

    public ActionResponse getActionResponse();

    /**
     * Return the portlet action event
     *
     * @return the portlet action event
     */
    public DefaultPortletAction getAction();

    /**
     * Return the portlet render event
     *
     * @return the portlet render event
     */
    public DefaultPortletRender getRender();

    /**
     * Determines if there is an action associated with this event
     *
     * @return <code>true</code> if there is an action associated with this
     *         event, <code>false</code> otherwise
     */
    public boolean hasAction();

    /**
     * Returns the layout component id associated with this page and event
     *
     * @return the layout component id or empty String if none exists
     */
    public String getComponentID();

    /**
     * Returns the page layout id associated with this event
     *
     * @return page layout id or empty String if none exists
     */
    public String getLayoutID();

    public void addNewRenderEvent(PortletComponentEvent evt);


    public PortletComponentEvent getLastRenderEvent();

    /**
     * Returns an object representing the client device that the user connects
     * to the portal with.
     *
     * @return the client device
     */
    public Client getClient();
}
