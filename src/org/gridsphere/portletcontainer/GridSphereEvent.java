/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: GridSphereEvent.java 5032 2006-08-17 18:15:06Z novotny $
 */
package org.gridsphere.portletcontainer;

import org.gridsphere.event.Event;
import org.gridsphere.layout.event.PortletComponentEvent;
import org.gridsphere.portlet.PortletContext;
import org.gridsphere.portlet.PortletResponse;
import org.gridsphere.portlet.DefaultPortletAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;

/**
 * A <code>GridSphereEvent</code> represents a general portlet container
 * event. The <code>GridSphereEvent</code> is passed into components that
 * need to access the <code>PortletRequest</code>
 * <code>PortletResponse</code> objects, such as the layout components.
 */
public interface GridSphereEvent extends Event {

    public HttpServletRequest getHttpServletRequest();

    public HttpServletResponse getHttpServletResponse();


    /**
     * Return the portlet context associated with this event
     *
     * @return the portlet context associated with this event
     */
    public PortletContext getPortletContext();

    /**
     * Return the portlet response associated with this event
     *
     * @return the portlet response associated with this event
     */
    public PortletResponse getPortletResponse();

    /**
     * Return the portlet action associated with this event
     *
     * @return the portlet action associated with this event
     */
    public DefaultPortletAction getAction();

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

}
