package org.gridlab.gridsphere.layout;

/**
 * Created by IntelliJ IDEA.
 * User: novotny
 * Date: Jan 9, 2003
 * Time: 10:14:07 PM
 * To change this template use Options | File Templates.
 */
public interface PortletTabEvent {

    public static final class Action {

        public static final PortletTabEvent.Action TAB_SELECTED = new PortletTabEvent.Action(1);

        private int action = 0;

        private Action(int action) {
            this.action = action;
        }
    }

    public PortletTabEvent.Action getAction();

    public PortletTab getPortletTab();

    public int getID();

}
