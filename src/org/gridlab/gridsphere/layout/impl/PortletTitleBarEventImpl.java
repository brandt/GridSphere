/*
 * Created by IntelliJ IDEA.
 * User: novotny
 * Date: Dec 10, 2002
 * Time: 2:17:14 AM
 * To change template for new interface use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.layout.impl;

import org.gridlab.gridsphere.layout.PortletTitleBarEvent;
import org.gridlab.gridsphere.portlet.Portlet;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletWindow;
import org.gridlab.gridsphere.portlet.impl.SportletWindow;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;
import org.gridlab.gridsphere.portletcontainer.GridSphereProperties;

public class PortletTitleBarEventImpl implements PortletTitleBarEvent {

    private PortletTitleBarEvent.Action action;
    private int id;
    private Portlet.Mode mode;
    private PortletWindow.State state;
    private PortletRequest req;

    public PortletTitleBarEventImpl(GridSphereEvent event, int id) {
        this.req = event.getPortletRequest();
        this.id = id;
        if (req.getParameter(GridSphereProperties.PORTLETMODE) != null) {
            action = PortletTitleBarEvent.Action.MODE_MODIFY;
        } else if (req.getParameter(GridSphereProperties.PORTLETWINDOW) != null) {
            action = PortletTitleBarEvent.Action.WINDOW_MODIFY;
        }
    }

    public PortletTitleBarEvent.Action getAction() {
        return action;
    }

    public Portlet.Mode getMode() {
        Portlet.Mode mode = null;
        String pMode = req.getParameter(GridSphereProperties.PORTLETMODE);
        if (pMode != null) {
            try {
                mode = Portlet.Mode.getInstance(pMode);
            } catch (Exception e) {
            }
            return mode;
        }
        return null;
    }

    public PortletWindow.State getState() {
        PortletWindow.State state = null;
        String s = req.getParameter(GridSphereProperties.PORTLETWINDOW);
        if (s != null) {
            try {
                state = SportletWindow.State.toPortletWindowState(s);
            } catch (Exception e) {
            }
            return state;
        }
        return null;
    }

    public int getID() {
        return id;
    }

}
