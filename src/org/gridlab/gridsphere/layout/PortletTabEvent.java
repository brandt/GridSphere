/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

/**
 * A PortletTabEvent is triggered by a PortletTab when a tab has been selected.
 */
public interface PortletTabEvent {

    /**
     *  An Action defines a single tab event action:
     *
     * <ul><li>TAB_SELECTED</li></ul>
     *
     */
    public static final class Action {

        public static final PortletTabEvent.Action TAB_SELECTED = new PortletTabEvent.Action(1);

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
    public PortletTabEvent.Action getAction();

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
