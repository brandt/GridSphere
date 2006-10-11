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

import javax.portlet.PortletMode;
import javax.portlet.WindowState;
import javax.servlet.http.HttpServletRequest;


/**
 * A <code>PortletTitleBarEventImpl</code> is the concrete implementation of
 * <code>PortletTitleBarEvent</code>
 */
public class PortletTitleBarEventImpl implements PortletTitleBarEvent {

    private ComponentAction action = null;
    private int id;
    private PortletTitleBar titleBar = null;
    private boolean hasStateAction = false;
    private boolean hasModeAction = false;
    private PortletMode portletMode;
    private WindowState windowState;

    /**
     * Constructs an instance of a PortletTitleBarEventImpl from a general
     * portal event and the portlet title bar component id
     *
     * @param titleBar the portlet title bar
     * @param event the GridSphereEvent
     * @param id    the portlet title bar component id
     */
    public PortletTitleBarEventImpl(PortletTitleBar titleBar, GridSphereEvent event, int id) {
        this.titleBar = titleBar;
        HttpServletRequest request = event.getHttpServletRequest();
        this.id = id;
        String pMode = request.getParameter(SportletProperties.PORTLET_MODE);
        if (pMode != null) {
            action = PortletTitleBarEvent.TitleBarAction.MODE_MODIFY;
            hasModeAction = true;
            try {
                portletMode = new PortletMode(pMode);
            } catch (Exception e) {
                portletMode = PortletMode.VIEW;
            }
        }
        String wState = request.getParameter(SportletProperties.PORTLET_WINDOW);
        if (wState != null) {
            action = PortletTitleBarEvent.TitleBarAction.WINDOW_MODIFY;
            hasStateAction = true;
            if (wState != null) {
                try {
                    windowState = new WindowState(wState);
                } catch (Exception e) {
                    windowState = WindowState.NORMAL;
                }
            }
        }
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
        return portletMode;
    }

    /**
     * Returns the portlet title bar window state
     *
     * @return the portlet title bar window state
     */
    public WindowState getState() {
        return windowState;
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
