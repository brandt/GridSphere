/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet;

import java.io.Serializable;

/**
 * The <code>PortletWindow</code> represents the window that encloses a portlet.
 * The portlet window can send events on manipulation of its various window
 * controls, like the "minimize" or "maximize" buttons. But a portlet can also
 * interrogate the portlet window about its current visibility state.
 * For example, a portlet may render its content differently depending on
 * whether its window is maximized or not.
 */
public interface PortletWindow {

    /**
     * The <code>State</code> describes the <code>PortletWindow</code> state
     */
    public static class State implements Serializable {

        /**
         * The standard "one-of many" window state on a page.
         */
        public static final PortletWindow.State NORMAL = new PortletWindow.State(PortletWindow.State.NORMAL_STATE);

        /**
         * The standard "one-of many" window state on a page.
         */
        public static final PortletWindow.State MAXIMIZED = new PortletWindow.State(PortletWindow.State.MAXIMIZED_STATE);

        /**
         * In this window state the portlet is displayed minimzed which means that only the portlet title is showns
         */
        public static final PortletWindow.State MINIMIZED = new PortletWindow.State(PortletWindow.State.MINIMIZED_STATE);

        /**
         *  The window is or will be closed and thus is not shown on the portal page anymore.
         */
        public static final PortletWindow.State CLOSED = new PortletWindow.State(PortletWindow.State.CLOSED_STATE);

        /**
         * Allows the portlet window to be detached of the normal content view of the portal and thus
         * be shown in a separate window.
         */
        public static final PortletWindow.State DETACHED = new PortletWindow.State(PortletWindow.State.DETACHED_STATE);

        /**
         * Allows the portlet window to be resized
         */
        public static final PortletWindow.State RESIZING = new PortletWindow.State(PortletWindow.State.RESIZING_STATE);

        private static final int NORMAL_STATE = 0;
        private static final int MAXIMIZED_STATE = 1;
        private static final int MINIMIZED_STATE = 2;
        private static final int CLOSED_STATE = 3;
        private static final int DETACHED_STATE = 4;
        private static final int RESIZING_STATE = 5;

        private int state = NORMAL_STATE;

        /**
         * Constructs an instance of State
         */
        private State() {
        }

        /**
         * Constructs an instance of State with a provided state id
         *
         * @param state the state id
         */
        private State(int state) {
            this.state = state;
        }

        /**
         * Returns a State object from parsing the supplied state string
         *
         * @param windowState the window state expressed as a <code>String</code>
         * @return the portlet window state
         * @throws IllegalArgumentException if the passed in <code>String</code>
         * does not match any of the defined window states
         */
        public static final PortletWindow.State toState(String windowState)
                throws IllegalArgumentException {
            if ("NORMAL".equalsIgnoreCase(windowState)) {
                return PortletWindow.State.NORMAL;
            } else if ("MINIMIZED".equalsIgnoreCase(windowState)) {
                return PortletWindow.State.MINIMIZED;
            } else if ("MAXIMIZED".equalsIgnoreCase(windowState)) {
                return PortletWindow.State.MAXIMIZED;
            } else if ("CLOSED".equalsIgnoreCase(windowState)) {
                return PortletWindow.State.CLOSED;
            } else if ("DETACHED".equalsIgnoreCase(windowState)) {
                return PortletWindow.State.DETACHED;
            } else if ("RESIZING".equalsIgnoreCase(windowState)) {
                return PortletWindow.State.RESIZING;
            } else {
                throw new IllegalArgumentException("Unable to parse state: " + windowState);
            }
        }

        /**
         * Returns a <code>String</code> representation of the portlet window
         * state.
         *
         * @return the window state expressed as a <code>String</code>
         */
        public String toString() {
            if (state == NORMAL_STATE) {
                return "NORMAL";
            } else if (state == MAXIMIZED_STATE) {
                return "MAXIMIZED";
            } else if (state == MINIMIZED_STATE) {
                return "MINIMIZED";
            } else if (state == CLOSED_STATE) {
                return "CLOSED";
            } else if (state == DETACHED_STATE) {
                return "DETACHED";
            } else if (state == RESIZING_STATE) {
                return "RESIZING";
            }
            return "Unknown State!";
        }
    }

}