/*
 * Created by IntelliJ IDEA.
 * User: novotny
 * Date: Dec 11, 2002
 * Time: 9:19:06 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.portletcontainer;

import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletContext;
import org.gridlab.gridsphere.portlet.PortletAction;
import org.gridlab.gridsphere.portlet.AbstractPortlet;
import org.gridlab.gridsphere.portlet.impl.SportletResponse;
import org.gridlab.gridsphere.portlet.impl.SportletURI;
import org.gridlab.gridsphere.portlet.impl.SportletRequest;

public interface GridSphereEvent {


    public static final class Action {

        public static final GridSphereEvent.Action PORTLET_ACTION = new GridSphereEvent.Action("p");
        public static final GridSphereEvent.Action LAYOUT_ACTION = new GridSphereEvent.Action("l");
        public static final GridSphereEvent.Action NO_ACTION = new GridSphereEvent.Action("");

        private String action = "";

        private Action(String action) {
            this.action = action;
        }

        public static GridSphereEvent.Action toAction(String action) {
            if (action == null) return NO_ACTION;

            if (action.equals(PORTLET_ACTION.toString())) {
                return PORTLET_ACTION;
            }
            if (action.equals(LAYOUT_ACTION.toString())) {
                return LAYOUT_ACTION;
            }
            return NO_ACTION;
        }

        public String toString() {
            return action;
        }
    }

    public PortletContext getPortletContext();

    public SportletRequest getSportletRequest();

    public SportletResponse getSportletResponse();

    public UserPortletManager getUserPortletManager();

    public GridSphereEvent.Action getAction();

    public boolean hasAction();

    public int getPortletComponentID();

    public SportletURI createNewAction(GridSphereEvent.Action action, int PortletComponentID, String ActivePortletID);

}
