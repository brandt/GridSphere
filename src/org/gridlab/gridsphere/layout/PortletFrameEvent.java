/*
 * Created by IntelliJ IDEA.
 * User: novotny
 * Date: Dec 10, 2002
 * Time: 2:17:14 AM
 * To change template for new interface use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.layout;

public interface PortletFrameEvent {

    public static final class Action {

        public static final PortletFrameEvent.Action FRAME_MAXIMIZED = new PortletFrameEvent.Action(1);
        public static final PortletFrameEvent.Action FRAME_RESIZED = new PortletFrameEvent.Action(2);
        public static final PortletFrameEvent.Action FRAME_MINIMIZED = new PortletFrameEvent.Action(3);

        private int action = 0;

        private Action(int action) {
            this.action = action;
        }
    }

    public PortletFrameEvent.Action getAction();

    public int getID();

}
