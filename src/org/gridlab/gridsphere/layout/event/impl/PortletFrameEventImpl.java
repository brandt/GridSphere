/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout.event.impl;

import org.gridlab.gridsphere.layout.event.PortletFrameEvent;
import org.gridlab.gridsphere.layout.event.ComponentAction;
import org.gridlab.gridsphere.layout.PortletComponent;
import org.gridlab.gridsphere.layout.PortletFrame;

/**
 * A <code>PortletFrameEventImpl</code> is the concrete implementation of
 * <code>PortletFrameEvent</code>
 */
public class PortletFrameEventImpl implements PortletFrameEvent {

    private ComponentAction action;
    private int id;
    private PortletFrame frame = null;

    /**
     * Constructs an instance of PortletFrameEventImpl from an action
     * and the component id of teh PortletFrame
     *
     * @param action a window action
     * @param id the component id of the PortletFrame
     */
    public PortletFrameEventImpl(PortletFrame frame, ComponentAction action, int id) {
        this.frame = frame;
        this.action = action;
        this.id = id;
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

}
