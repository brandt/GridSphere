/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout.event.impl;

import org.gridlab.gridsphere.layout.event.PortletFrameEvent;

/**
 * A PortletFrameEvent is created by a PortletFrame when a window event has been
 * triggered.
 */
public class PortletFrameEventImpl implements PortletFrameEvent {

    private PortletFrameEvent.Action action;
    private int id;

    /**
     * Constructs an instance of PortletFrameEventImpl from an action
     * and the component id of teh PortletFrame
     *
     * @param action a window action
     * @param id the component id of the PortletFrame
     */
    public PortletFrameEventImpl(PortletFrameEvent.Action action, int id) {
        this.action = action;
        this.id = id;
    }

    /**
     * Returns the type of PortletFrame action received
     *
     * @return the window action
     */
    public PortletFrameEvent.Action getAction() {
        return action;
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
