/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout.event.impl;

import org.gridlab.gridsphere.layout.PortletComponent;
import org.gridlab.gridsphere.layout.PortletTitleBar;
import org.gridlab.gridsphere.layout.event.ComponentAction;
import org.gridlab.gridsphere.layout.event.PortletTitleBarEvent;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletProperties;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;


/**
 * A <code>PortletTitleBarEventImpl</code> is the concrete implementation of
 * <code>PortletTitleBarEvent</code>
 */
public class PortletTitleBarEventImpl implements PortletTitleBarEvent {

    private ComponentAction action = null;
    private int id;
    private PortletRequest req;
    private PortletTitleBar titleBar = null;

    /**
     * Constructs an instance of a PortletTitleBarEventImpl from a general
     * portal event and the portlet title bar component id
     *
     * @param event the GridSphereEvent
     * @param id the portlet title bar component id
     */
    public PortletTitleBarEventImpl(PortletTitleBar titleBar, GridSphereEvent event, int id) {
        this.titleBar = titleBar;
        this.req = event.getPortletRequest();
        User user = req.getUser();
        if (!(user instanceof GuestUser)) {
            this.id = id;
            if (req.getParameter(SportletProperties.PORTLET_MODE) != null) {
                action = PortletTitleBarEvent.TitleBarAction.MODE_MODIFY;
            } else if (req.getParameter(SportletProperties.PORTLET_WINDOW) != null) {
                action = PortletTitleBarEvent.TitleBarAction.WINDOW_MODIFY;
            }
        }
    }

    /**
     * Returns the portlet title bar event action
     *
     * @return the portlet title bar event action
     */
    public ComponentAction getAction() {
        return action;
    }

    /**
     * Returns the portlet title bar mode
     *
     * @return mode the portlet title bar mode
     */
    public Portlet.Mode getMode() {
        Portlet.Mode mode = null;
        String pMode = req.getParameter(SportletProperties.PORTLET_MODE);
        if (pMode != null) {
            try {
                mode = Portlet.Mode.toMode(pMode);
            } catch (Exception e) {

            }
            PortletRole role = req.getRole();
            /*
            if (mode.equals(Portlet.Mode.CONFIGURE)) {
                if (role.compare(role, PortletRole.ADMIN) <  0) {
                    System.err.println("im role: " + role.toString() + " about to send null");
                    return null;
                }
            }
            */
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
        String s = req.getParameter(SportletProperties.PORTLET_WINDOW);
        if (s != null) {
            try {
                state = PortletWindow.State.toState(s);
            } catch (Exception e) {
            }
            return state;
        }
        return null;
    }

    public PortletComponent getPortletComponent() {
        return titleBar;
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
