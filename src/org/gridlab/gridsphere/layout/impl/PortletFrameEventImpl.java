/*
 * Created by IntelliJ IDEA.
 * User: novotny
 * Date: Dec 10, 2002
 * Time: 2:17:14 AM
 * To change template for new interface use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.layout.impl;

import org.gridlab.gridsphere.layout.PortletFrameEvent;

public class PortletFrameEventImpl implements PortletFrameEvent {

    private PortletFrameEvent.Action action;
    private int id;

    public PortletFrameEventImpl(PortletFrameEvent.Action action, int id) {
        this.action = action;
        this.id = id;
    }

    public PortletFrameEvent.Action getAction() {
        return action;
    }

    public int getID() {
        return id;
    }

}
