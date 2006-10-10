/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: PortletTabEvent.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.layout.event;


/**
 * A <code>PortletTabEvent</code> is triggered by a <code>PortletTab</code>
 * when a tab has been selected.
 */
public interface PortletTabEvent extends PortletComponentEvent {

    /**
     * An Action defines a single tab event action:
     * <p/>
     * <ul><li>TAB_SELECTED</li></ul>
     */
    public static final class TabAction implements ComponentAction {

        public static final TabAction TAB_SELECTED = new TabAction(1);

        private int action = 0;

        /**
         * Action is immutable and cannot be created outside of this class
         *
         * @param action a unique integer id
         */
        private TabAction(int action) {
            this.action = action;
        }

        public int getID() {
            return action;
        }
    }

    /**
     * Returns the component id of the portlet tab
     *
     * @return the component id of the portlet tab
     */
    public int getID();

}
