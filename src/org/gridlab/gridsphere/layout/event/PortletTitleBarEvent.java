/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout.event;

import org.gridlab.gridsphere.portlet.Portlet;
import org.gridlab.gridsphere.portlet.PortletWindow;

/**
 * A PortletTitleBarEvent is created by a PortletTitleBar when a title
 * bar event has been triggered.
 */
public interface PortletTitleBarEvent {

    /**
     * Action is an immutable representing the window state and portlet mode
     * of the portlet title bar.
     */
    public static final class Action {

        public static final Action WINDOW_MODIFY = new Action(1);

        public static final Action MODE_MODIFY = new Action(5);

        private int action = 0;

        /**
         * Action cannot be instantiated outside of this class
         */
        private Action(int action) {
            this.action = action;
        }
    }

    /**
     * Returns the portlet title bar event action
     *
     * @return the portlet title bar event action
     */
    public Action getAction();

    /**
     * Returns the portlet title bar mode
     *
     * @return mode the portlet title bar mode
     */
    public Portlet.Mode getMode();

    /**
     * Returns the portlet title bar window state
     *
     * @return the portlet title bar window state
     */
    public PortletWindow.State getState();

    /**
     * Returns the portlet title bar component id
     *
     * @return the portlet title bar component id
     */
    public int getID();

}
