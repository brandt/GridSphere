/*
 * Created by IntelliJ IDEA.
 * User: novotny
 * Date: Dec 10, 2002
 * Time: 2:17:14 AM
 * To change template for new interface use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.layout.impl;

import org.gridlab.gridsphere.layout.PortletTabEvent;
import org.gridlab.gridsphere.layout.PortletTab;

public class PortletTabEventImpl implements PortletTabEvent {

    private PortletTabEvent.Action action;
    private PortletTab portletTab;
    private int id;

    public PortletTabEventImpl(PortletTab portletTab, PortletTabEvent.Action action, int id) {
        this.action = action;
        this.portletTab = portletTab;
        this.id = id;
    }

    public PortletTabEvent.Action getAction() {
        return action;
    }

    public PortletTab getPortletTab() {
        return portletTab;
    }

    public int getID() {
        return id;
    }

}
