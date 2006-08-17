/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout.event.impl;

import org.gridlab.gridsphere.layout.PortletComponent;
import org.gridlab.gridsphere.layout.PortletTitleBar;
import org.gridlab.gridsphere.layout.event.ComponentAction;
import org.gridlab.gridsphere.layout.event.PortletTitleBarEvent;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletWindow;
import org.gridlab.gridsphere.portlet.Mode;
import org.gridlab.gridsphere.portlet.impl.SportletProperties;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

import java.security.Principal;


/**
 * A <code>PortletTitleBarEventImpl</code> is the concrete implementation of
 * <code>PortletTitleBarEvent</code>
 */
public class PortletTitleBarEventImpl implements PortletTitleBarEvent {

    private ComponentAction action = null;
    private int id;
    private PortletTitleBar titleBar = null;
    private PortletRequest request;
    private boolean hasStateAction = false;
    private boolean hasModeAction = false;

    /**
     * Constructs an instance of a PortletTitleBarEventImpl from a general
     * portal event and the portlet title bar component id
     *
     * @param event the GridSphereEvent
     * @param id    the portlet title bar component id
     */
    public PortletTitleBarEventImpl(PortletTitleBar titleBar, GridSphereEvent event, int id) {
        this.titleBar = titleBar;
        this.request = event.getPortletRequest();
        //User user = request.getUser();
        //if (!(user instanceof GuestUser)) {
        Principal principal = request.getUserPrincipal();
        if (principal != null) {
            this.id = id;
            if (request.getParameter(SportletProperties.PORTLET_MODE) != null) {
                action = PortletTitleBarEvent.TitleBarAction.MODE_MODIFY;
                hasModeAction = true;
            }
            if (request.getParameter(SportletProperties.PORTLET_WINDOW) != null) {
                action = PortletTitleBarEvent.TitleBarAction.WINDOW_MODIFY;
                hasStateAction = true;
            }
        }
    }

    public PortletRequest getRequest() {
        return request;
    }

    public boolean hasAction() {
        return (action != null);
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
    public Mode getMode() {
        Mode mode;
        String pMode = request.getParameter(SportletProperties.PORTLET_MODE);
        if (pMode != null) {
            try {
                mode = Mode.toMode(pMode);
            } catch (Exception e) {
                mode = Mode.VIEW;
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
        PortletWindow.State state;
        String s = request.getParameter(SportletProperties.PORTLET_WINDOW);
        if (s != null) {
            try {
                state = PortletWindow.State.toState(s);
            } catch (Exception e) {
                state = PortletWindow.State.NORMAL;
            }
            return state;
        }
        return null;
    }

    /**
     * Returns true if this title bar event signals a window state change
     *
     * @return true if this title bar event signals a window state change
     */
    public boolean hasWindowStateAction() {
        return hasStateAction;
    }

    /**
     * Returns true if this title bar event signals a portlet mode change
     *
     * @return true if this title bar event signals a portlet mode change
     */
    public boolean hasPortletModeAction() {
        return hasModeAction;
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
