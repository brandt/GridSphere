/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout.event.impl;

import org.gridlab.gridsphere.layout.PortletTab;
import org.gridlab.gridsphere.layout.event.PortletTabEvent;

/**
 * A <code>PortletTabEventImpl</code> is the concrete implementation of
 * <code>PortletTabEvent</code>
 */
public class PortletTabEventImpl implements PortletTabEvent {

    private PortletTabEvent.Action action;
    private PortletTab portletTab;
    private int id;

    private PortletTabEventImpl() {}

    /**
     * Constructs an instance of PortletTabEventImpl from a portlet tab, a
     * tab event action and the portlet tab component id
     *
     * @param portletTab the portlet tab
     * @param action the portlet tab event action
     * @param id the portlet component id
     * @see PortletTab
     */
    public PortletTabEventImpl(PortletTab portletTab, PortletTabEvent.Action action, int id) {
        this.action = action;
        this.portletTab = portletTab;
        this.id = id;
    }

    /**
     * Returns the portlet tab event action
     *
     * @return the portlet tab event action
     */
    public PortletTabEvent.Action getAction() {
        return action;
    }

    /**
     * Returns the PortletTab that was selected
     *
     * @return the PortletTab that was selcted
     */
    public PortletTab getPortletTab() {
        return portletTab;
    }

    /**
     * Returns the component id of the portlet tab
     *
     * @return the component id of the portlet tab
     */
    public int getID() {
        return id;
    }

}
