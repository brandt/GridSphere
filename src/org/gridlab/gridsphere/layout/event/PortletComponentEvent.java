/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout.event;

import org.gridlab.gridsphere.layout.PortletComponent;

/**
 * A <code>PortletComponentEvent</code> is a general portlet render event
 */
public interface PortletComponentEvent {

    /**
     * Returns the portlet title bar event action
     *
     * @return the portlet title bar event action
     */
    public ComponentAction getAction();

    /**
     * Returns true if this event actually triggered an action
     *
     * @return true if this event actually triggered an action
     */
    public boolean hasAction();

    /**
     * Returns the PortletComponent that was selected
     *
     * @return the PortletComponent that was selcted
     */
    public PortletComponent getPortletComponent();

    /**
     * Returns the component id of the portlet component
     *
     * @return the component id of the portlet component
     */
    public int getID();

}
