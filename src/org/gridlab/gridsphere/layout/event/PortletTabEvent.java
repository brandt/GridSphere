/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout.event;

import org.gridlab.gridsphere.layout.PortletTab;

/**
 * A <code>PortletTabEvent</code> is triggered by a <code>PortletTab</code>
 * when a tab has been selected.
 */
public interface PortletTabEvent {

    /**
     *  An Action defines a single tab event action:
     *
     * <ul><li>TAB_SELECTED</li></ul>
     *
     */
    public static final class Action {

        public static final Action TAB_SELECTED = new Action(1);

        private int action = 0;

        /**
         * Action is immutable and cannot be created outside of this class
         *
         * @param action a unique integer id
         */
        private Action(int action) {
            this.action = action;
        }
    }

    /**
     * Returns the portlet tab event action
     *
     * @return the portlet tab event action
     */
    public Action getAction();

    /**
     * Returns the PortletTab that was selected
     *
     * @return the PortletTab that was selcted
     */
    public PortletTab getPortletTab();

    /**
     * Returns the component id of the portlet tab
     *
     * @returns the component id of the portlet tab
     */
    public int getID();

}
