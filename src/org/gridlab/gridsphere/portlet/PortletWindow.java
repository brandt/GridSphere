/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet;

/**
 * The PortletWindow represents the window that encloses a portlet.
 * The portlet window can send events on manipulation of its various window controls,
 * like the "minimize" or "close" buttons. But a portlet can also interrogate
 * the portlet window about its current visibility state. For example,
 * a portlet may render its content differently depending on whether its window is maximized or not.
 */
public interface PortletWindow {

    /**
     * State describes Window state
     */
    public static class State {
        private int state = NORMAL;
        public State(int state) {
            this.state = state;
        }
        public static final int NORMAL = 0;
        public static final int MAXIMIZED = 1;
        public static final int MINIMIZED = 2;
        public static final int CLOSED = 3;
        public static final int DETACHED = 4;
        public static final int RESIZING = 5;
    }

    /**
     * The standard "one-of many" window state on a page.
     */
    public static final PortletWindow.State NORMAL = new PortletWindow.State(PortletWindow.State.NORMAL);

    /**
     * The standard "one-of many" window state on a page.
     */
    public static final PortletWindow.State MAXIMIZED = new PortletWindow.State(PortletWindow.State.MAXIMIZED);

    /**
     * In this window state the portlet is displayed minimzed which means that only the portlet title is showns
     */
    public static final PortletWindow.State MINIMIZED = new PortletWindow.State(PortletWindow.State.MINIMIZED);

    /**
     *  The window is or will be closed and thus is not shown on the portal page anymore.
     */
    public static final PortletWindow.State CLOSED = new PortletWindow.State(PortletWindow.State.CLOSED);

    /**
     * Allows the portlet window to be detached of the normal content view of the portal and thus
     * be shown in a separate window.
     */
    public static final PortletWindow.State DETACHED = new PortletWindow.State(PortletWindow.State.DETACHED);

    /**
     * Allows the portlet window to be resized
     */
    public static final PortletWindow.State RESIZING = new PortletWindow.State(PortletWindow.State.RESIZING);

    /**
     * Return the portlet window state.
     *
     * @return the portlet window state
     */
    public PortletWindow.State getWindowState();

    /**
     * Defines which portlet window state is shown next.
     *
     * @param state the portlet window state
     */
    public void setWindowState(PortletWindow.State state);


}