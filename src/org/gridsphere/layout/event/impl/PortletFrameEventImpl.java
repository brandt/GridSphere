/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
package org.gridsphere.layout.event.impl;

import org.gridsphere.layout.PortletComponent;
import org.gridsphere.layout.PortletFrame;
import org.gridsphere.layout.event.ComponentAction;
import org.gridsphere.layout.event.PortletFrameEvent;


/**
 * A <code>PortletFrameEventImpl</code> is the concrete implementation of
 * <code>PortletFrameEvent</code>
 */
public class PortletFrameEventImpl implements PortletFrameEvent {

    private ComponentAction action;
    private int id;
    private String width = "";
    private PortletFrame frame = null;

    /**
     * Constructs an instance of PortletFrameEventImpl from an action
     * and the component id of teh PortletFrame
     *
     * @param frame the portlet frame
     * @param action a window action
     * @param id     the component id of the PortletFrame
     */
    public PortletFrameEventImpl(PortletFrame frame, ComponentAction action, int id) {
        this.frame = frame;
        this.action = action;
        this.id = id;
    }

    public boolean hasAction() {
        return (action != null);
    }

    /**
     * Returns the type of PortletFrame action received
     *
     * @return the window action
     */
    public ComponentAction getAction() {
        return action;
    }

    public PortletComponent getPortletComponent() {
        return frame;
    }

    /**
     * Returns the component id of  the PortletFrame that triggered the event
     *
     * @return the component id of  the PortletFrame
     */
    public int getID() {
        return id;
    }

    /**
     * Used in the case of portlet frame resize when it needs to know the original width of the frame
     *
     * @param originalWidth the portlet frame original width
     */
    public void setOriginalWidth(String originalWidth) {
        this.width = originalWidth;
    }

    /**
     * Used in the case of portlet frame resize when it needs to know the original width of the frame
     *
     * @return the portlet frame original width
     */
    public String getOriginalWidth() {
        return width;
    }
}
