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

    private PortletWindow.State state = new PortletWindow.State(PortletWindow.State.NORMAL);

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