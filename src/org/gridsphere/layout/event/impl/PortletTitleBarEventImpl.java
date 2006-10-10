/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: PortletTitleBarEventImpl.java 5032 2006-08-17 18:15:06Z novotny $
 */
package org.gridsphere.layout.event.impl;

import org.gridsphere.layout.PortletComponent;
import org.gridsphere.layout.PortletTitleBar;
import org.gridsphere.layout.event.ComponentAction;
import org.gridsphere.layout.event.PortletTitleBarEvent;
import org.gridsphere.portlet.impl.SportletProperties;
import org.gridsphere.portletcontainer.GridSphereEvent;

import javax.portlet.ActionRequest;
import javax.portlet.PortletMode;
import javax.portlet.WindowState;
import java.security.Principal;


/**
 * A <code>PortletTitleBarEventImpl</code> is the concrete implementation of
 * <code>PortletTitleBarEvent</code>
 */
public class PortletTitleBarEventImpl implements PortletTitleBarEvent {

    private ComponentAction action = null;
    private int id;
    private PortletTitleBar titleBar = null;
    private ActionRequest request;
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
        this.request = event.getActionRequest();
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

    public ActionRequest getRequest() {
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
    public PortletMode getMode() {
        PortletMode mode;
        String pMode = request.getParameter(SportletProperties.PORTLET_MODE);
        if (pMode != null) {
            try {
                mode = new PortletMode(pMode);
            } catch (Exception e) {
                mode = PortletMode.VIEW;
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
    public WindowState getState() {
        WindowState state;
        String s = request.getParameter(SportletProperties.PORTLET_WINDOW);
        if (s != null) {
            try {
                state = new WindowState(s);
            } catch (Exception e) {
                state = WindowState.NORMAL;
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
