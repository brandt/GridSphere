/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: PortletWindow.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.portlet;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Locale;
import java.util.ResourceBundle;

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
    public static class State implements Serializable, Comparator, Cloneable {

        /**
         * The standard "one-of many" window state on a page.
         */
        public static final PortletWindow.State NORMAL = new PortletWindow.State(PortletWindow.State.NORMAL_STATE, "NORMAL");

        /**
         * The standard "one-of many" window state on a page.
         */
        public static final PortletWindow.State MAXIMIZED = new PortletWindow.State(PortletWindow.State.MAXIMIZED_STATE, "MAXIMIZED");

        /**
         * In this window state the portlet is displayed minimzed which means that only the portlet title is showns
         */
        public static final PortletWindow.State MINIMIZED = new PortletWindow.State(PortletWindow.State.MINIMIZED_STATE, "MINIMIZED");

        /**
         * The closed window state
         */
        public static final PortletWindow.State CLOSED = new PortletWindow.State(PortletWindow.State.CLOSED_STATE, "CLOSED");

        /**
         * Allows the portlet window to be resized
         */
        public static final PortletWindow.State RESIZING = new PortletWindow.State(PortletWindow.State.RESIZING_STATE, "RESIZING");

        /**
         * Allows the portlet window to exist in its own browser frame
         */
        public static final PortletWindow.State FLOATING = new PortletWindow.State(PortletWindow.State.FLOATING_STATE, "FLOATING");

        /**
         * The ordering here is also used in the layout of icons
         */
        private static final int NORMAL_STATE = 0;
        private static final int MINIMIZED_STATE = 1;
        private static final int RESIZING_STATE = 2;
        private static final int MAXIMIZED_STATE = 3;
        private static final int CLOSED_STATE = 4;
        private static final int FLOATING_STATE = 5;

        private int state = NORMAL_STATE;
        private String stateString = "";

        private static int numStates = 6;

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
        private State(int state, String stateString) {
            this.state = state;
            this.stateString = stateString;
        }

        /**
         * Returns the portlet window state
         *
         * @return the portlet window state
         */
        public int getState() {
            return state;
        }

        /**
         * Returns a State object from parsing the supplied state string
         *
         * @param windowState the window state expressed as a <code>String</code>
         * @return the portlet window state
         * @throws IllegalArgumentException if the passed in <code>String</code>
         *                                  does not match any of the defined window states
         */
        public static final PortletWindow.State toState(String windowState)
                throws IllegalArgumentException {
            if ("NORMAL".equalsIgnoreCase(windowState)) {
                return PortletWindow.State.NORMAL;
            } else if ("MINIMIZED".equalsIgnoreCase(windowState)) {
                return PortletWindow.State.MINIMIZED;
            } else if ("MAXIMIZED".equalsIgnoreCase(windowState)) {
                return PortletWindow.State.MAXIMIZED;
            } else if ("RESIZING".equalsIgnoreCase(windowState)) {
                return PortletWindow.State.RESIZING;
            } else if ("CLOSED".equalsIgnoreCase(windowState)) {
                return PortletWindow.State.CLOSED;
            } else if ("FLOATING".equalsIgnoreCase(windowState)) {
                return PortletWindow.State.FLOATING;
            } else {
                PortletWindow.State newstate = new PortletWindow.State(numStates, windowState);
                numStates++;
                return newstate;
            }
        }

        /**
         * Returns a <code>String</code> representation of the portlet window
         * state.
         *
         * @return the window state expressed as a <code>String</code>
         */
        public String toString() {
            return stateString;
        }

        /**
         * Returns a locale-specific <code>String</code> representation of
         * the portlet window state.
         *
         * @return the locale-specific window state expressed as a <code>String</code>
         */
        public String getText(Locale locale) {
            ResourceBundle bundle = ResourceBundle.getBundle("gridsphere.resources.Portlet", locale);
            String key = toString();
            String value = bundle.getString(key);
            return value;
        }

        public Object clone() throws CloneNotSupportedException {
            PortletWindow.State w = (PortletWindow.State) super.clone();
            w.state = state;
            return w;
        }

        public int compare(Object left, Object right) {
            int leftID = ((PortletWindow.State) left).getState();
            int rightID = ((PortletWindow.State) right).getState();
            int result;
            if (leftID < rightID) {
                result = -1;
            } else if (leftID > rightID) {
                result = 1;
            } else {
                result = 0;
            }
            return result;
        }

        public boolean equals(Object o) {
            if ((o != null) && (o instanceof PortletWindow.State)) {
                return (this.state == ((PortletWindow.State) o).getState() ? true : false);
            }
            return false;
        }

        public int hashCode() {
            return state;
        }

        private Object readResolve() {
            PortletWindow.State s = PortletWindow.State.NORMAL;
            switch (state) {
                case NORMAL_STATE:
                    s = PortletWindow.State.NORMAL;
                    break;
                case MINIMIZED_STATE:
                    s = PortletWindow.State.MINIMIZED;
                    break;
                case MAXIMIZED_STATE:
                    s = PortletWindow.State.MAXIMIZED;
                    break;
                case CLOSED_STATE:
                    s = PortletWindow.State.CLOSED;
                    break;
                case RESIZING_STATE:
                    s = PortletWindow.State.RESIZING;
                    break;
                case FLOATING_STATE:
                    s = PortletWindow.State.FLOATING;
                    break;
            }
            return s;
        }

    }

}