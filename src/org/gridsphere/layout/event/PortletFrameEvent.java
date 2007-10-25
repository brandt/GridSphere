/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
package org.gridsphere.layout.event;

/**
 * A <code>PortletFrameEvent</code> is created by a <code>PortletFrame</code>
 * when a window event has been triggered.
 */
public interface PortletFrameEvent extends PortletComponentEvent {

    /**
     * An Action defines an immutable set of known window events.
     * <p/>
     * <ul>
     * <li>FRAME_MAXIMIZED</li>
     * <li>FRAME_MINIMIZED</li>
     * <li>FRAME_RESTORED</li>
     * </ul>
     */
    public static final class FrameAction implements ComponentAction {

        public static final FrameAction FRAME_MAXIMIZED = new FrameAction(1);
        public static final FrameAction FRAME_RESTORED = new FrameAction(2);
        public static final FrameAction FRAME_MINIMIZED = new FrameAction(3);
        public static final FrameAction FRAME_CLOSED = new FrameAction(4);

        private int action = 0;

        /**
         * Constructs an instance of Action.
         *
         * @param action a unique integer id
         */
        private FrameAction(int action) {
            this.action = action;
        }

        public int getID() {
            return action;
        }
    }

    /**
     * Returns the type of PortletFrame action received
     *
     * @return the PortletFrameEvent.Action
     */
    public ComponentAction getAction();

    /**
     * Returns the component id of  the PortletFrame that triggered the event
     *
     * @return the component id of  the PortletFrame
     */
    public int getID();

    /**
     * Used in the case of portlet frame resize when it needs to know the original width of the frame
     *
     * @return the portlet frame original width
     */
    public String getOriginalWidth();

}
