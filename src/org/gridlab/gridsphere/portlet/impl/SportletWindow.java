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

    private SportletWindow() {}

    public SportletWindow(String windowState) throws Exception {
        if (windowState.equalsIgnoreCase(PortletWindow.State.NORMAL.toString())) {
            state = PortletWindow.State.NORMAL;
        } else if (windowState.equalsIgnoreCase(PortletWindow.State.MAXIMIZED.toString())) {
            state = PortletWindow.State.MAXIMIZED;
        } else if (windowState.equalsIgnoreCase(PortletWindow.State.MINIMIZED.toString())) {
            state = PortletWindow.State.MINIMIZED;
        } else if (windowState.equalsIgnoreCase(PortletWindow.State.CLOSED.toString())) {
            state = PortletWindow.State.CLOSED;
        } else if (windowState.equalsIgnoreCase(PortletWindow.State.DETACHED.toString())) {
            state = PortletWindow.State.DETACHED;
        } else if (windowState.equalsIgnoreCase(PortletWindow.State.RESIZING.toString())) {
            state = PortletWindow.State.RESIZING;
        } else {
            throw new Exception("Unable to create PortletWindow for state: "  + windowState);
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

}