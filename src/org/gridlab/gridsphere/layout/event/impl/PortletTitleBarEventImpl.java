/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout.event.impl;

import org.gridlab.gridsphere.layout.event.PortletTitleBarEvent;
import org.gridlab.gridsphere.portlet.Portlet;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletWindow;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;
import org.gridlab.gridsphere.portletcontainer.GridSphereProperties;


/**
 * A <code>PortletTitleBarEventImpl</code> is the concrete implementation of
 * <code>PortletTitleBarEvent</code>
 */
public class PortletTitleBarEventImpl implements PortletTitleBarEvent {

    private PortletTitleBarEvent.Action action;
    private int id;
    private PortletRequest req;

    /**
     * Constructs an instance of a PortletTitleBarEventImpl from a general
     * portal event and the portlet title bar component id
     *
     * @param event the GridSphereEvent
     * @param id the portlet title bar component id
     */
    public PortletTitleBarEventImpl(GridSphereEvent event, int id) {
        this.req = event.getPortletRequest();
        this.id = id;
        if (req.getParameter(GridSphereProperties.PORTLETMODE) != null) {
            action = PortletTitleBarEvent.Action.MODE_MODIFY;
        } else if (req.getParameter(GridSphereProperties.PORTLETWINDOW) != null) {
            action = PortletTitleBarEvent.Action.WINDOW_MODIFY;
        }
    }

    /**
     * Returns the portlet title bar event action
     *
     * @return the portlet title bar event action
     */
    public PortletTitleBarEvent.Action getAction() {
        return action;
    }

    /**
     * Returns the portlet title bar mode
     *
     * @return mode the portlet title bar mode
     */
    public Portlet.Mode getMode() {
        Portlet.Mode mode = null;
        String pMode = req.getParameter(GridSphereProperties.PORTLETMODE);
        if (pMode != null) {
            try {
                mode = Portlet.Mode.toMode(pMode);
            } catch (Exception e) {
            }
            return mode;
        }
        return null;
    }

    /**
     * Returns the portlet title bar window state
     *
     * @return the portlet title bar window state
     */
    public PortletWindow.State getState() {
        PortletWindow.State state = null;
        String s = req.getParameter(GridSphereProperties.PORTLETWINDOW);
        if (s != null) {
            try {
                state = PortletWindow.State.toState(s);
            } catch (Exception e) {
            }
            return state;
        }
        return null;
    }

    /**
     * Returns the portlet title bar component id
     *
     * @return the portlet title bar component id
     */
    public int getID() {
        return id;
    }

}
