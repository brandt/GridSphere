/*
 * Created by IntelliJ IDEA.
 * User: novotny
 * Date: Dec 10, 2002
 * Time: 2:17:14 AM
 * To change template for new interface use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.Portlet;
import org.gridlab.gridsphere.portlet.PortletWindow;

public interface PortletTitleBarEvent {

    public static final class Action {

        public static final Action WINDOW_MODIFY = new Action(1);

        public static final Action MODE_MODIFY = new Action(5);

        private int action = 0;

        private Action(int action) {
            this.action = action;
        }
    }

    public PortletTitleBarEvent.Action getAction();

    public Portlet.Mode getMode();

    public PortletWindow.State getState();

    public int getID();

}
