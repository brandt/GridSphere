/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

/**
 * A PortletFrameEvent is created by a PortletFrame when a window event has been
 * triggered.
 */
public interface PortletFrameEvent {

    /**
     * An Action defines an immutable set of known window events.
     *
     * <ul>
     * <li>FRAME_MAXIMIZED</li>
     * <li>FRAME_MINIMIZED</li>
     * <li>FRAME_RESTORED</li>
     * </ul>
     */
    public static final class Action {

        public static final PortletFrameEvent.Action FRAME_MAXIMIZED = new PortletFrameEvent.Action(1);
        public static final PortletFrameEvent.Action FRAME_RESTORED = new PortletFrameEvent.Action(2);
        public static final PortletFrameEvent.Action FRAME_MINIMIZED = new PortletFrameEvent.Action(3);

        private int action = 0;

        /**
         * Constructs an instance of Action.
         *
         * @param action a unique integer id
         */
        private Action(int action) {
            this.action = action;
        }
    }

    /**
     * Returns the type of PortletFrame action received
     *
     * @return the PortletFrameEvent.Action
     */
    public PortletFrameEvent.Action getAction();

    /**
     * Returns the component id of  the PortletFrame that triggered the event
     *
     * @return the component id of  the PortletFrame
     */
    public int getID();

}
