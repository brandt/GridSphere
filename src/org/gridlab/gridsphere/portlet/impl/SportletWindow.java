/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.impl;

import org.gridlab.gridsphere.portlet.PortletWindow;

/**
 * The PortletWindow represents the window that encloses a portlet.
 * The portlet window can send events on manipulation of its various window controls,
 * like the "minimize" or "close" buttons. But a portlet can also interrogate
 * the portlet window about its current visibility state. For example,
 * a portlet may render its content differently depending on whether its window is maximized or not.
 */
public class SportletWindow implements PortletWindow {

    private PortletWindow.State state = null;

    /**
     * The standard "one-of many" window state on a page.
     */
    public static final PortletWindow NORMAL = new SportletWindow(PortletWindow.State.NORMAL);

    /**
     * The standard "one-of many" window state on a page.
     */
    public static final PortletWindow MAXIMIZED = new SportletWindow(PortletWindow.State.MAXIMIZED);

    /**
     * In this window state the portlet is displayed minimzed which means that only the portlet title is showns
     */
    public static final PortletWindow MINIMIZED = new SportletWindow(PortletWindow.State.MINIMIZED);

    /**
     *  The window is or will be closed and thus is not shown on the portal page anymore.
     */
    public static final PortletWindow CLOSED = new SportletWindow(PortletWindow.State.CLOSED);

    /**
     * Allows the portlet window to be detached of the normal content view of the portal and thus
     * be shown in a separate window.
     */
    public static final PortletWindow DETACHED = new SportletWindow(PortletWindow.State.DETACHED);

    /**
     * Allows the portlet window to be resized
     */
    public static final PortletWindow RESIZING = new SportletWindow(PortletWindow.State.RESIZING);

    private SportletWindow() {
        this.state = PortletWindow.State.NORMAL;
    }

    private SportletWindow(PortletWindow.State state) {
        this.state = state;
    }

    public static PortletWindow getInstance(String windowState) {
        if (windowState.equals(PortletWindow.State.NORMAL.toString())) {
            return NORMAL;
        } else if (windowState.equals(PortletWindow.State.MAXIMIZED.toString())) {
            return MAXIMIZED;
        } else if (windowState.equals(PortletWindow.State.MINIMIZED.toString())) {
            return MINIMIZED;
        } else if (windowState.equals(PortletWindow.State.CLOSED.toString())) {
            return CLOSED;
        } else if (windowState.equals(PortletWindow.State.DETACHED.toString())) {
            return DETACHED;
        } else if (windowState.equals(PortletWindow.State.RESIZING.toString())) {
            return RESIZING;
        } else {
            return null;
        }
    }

    /**
     * Return the portlet window state.
     *
     * @return the portlet window state
     */
    public PortletWindow.State getWindowState() {
        return state;
    }

    /**
     * Defines which portlet window state is shown next.
     *
     * @param state the portlet window state
     */
    public void setWindowState(PortletWindow.State state) {
        this.state = state;
    }

    public String toString() {
        return state.toString();
    }

}