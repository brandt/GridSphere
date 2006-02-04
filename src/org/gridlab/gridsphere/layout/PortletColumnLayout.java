/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.layout.view.Render;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.impl.SportletProperties;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * The <code>PortletColumnLayout</code> is a concrete implementation of the <code>PortletFrameLayout</code>
 * that organizes portlets into a column.
 */
public class PortletColumnLayout extends PortletFrameLayout implements Cloneable, Serializable {

    private transient Render colView = null;

    public PortletColumnLayout() {
    }

    public List init(PortletRequest req, List list) {
        list = super.init(req, list);
        colView = (Render)getRenderClass("ColumnLayout");
        return list;
    }

    public void remove(PortletComponent pc, PortletRequest req) {
        components.remove(pc);
        if (getPortletComponents().isEmpty() && (!canModify)) {
            parent.remove(this, req);
        }
    }

    public void doRender(GridSphereEvent event) {
        //System.err.println("\t\tin render ColumnLayout");
        StringBuffer col = new StringBuffer();
        // starting of the gridtable
        if (!components.isEmpty()) {
            col.append(colView.doStart(event, this));
            PortletComponent p;
            for (int i = 0; i < components.size(); i++) {
                p = (PortletComponent) components.get(i);
                col.append(colView.doStartBorder(event,this));
                if (p.getVisible()) {
                    p.doRender(event);
                    col.append(p.getBufferedOutput(event.getPortletRequest()));
                }
                col.append(colView.doEndBorder(event, this));
            }
            col.append(colView.doEnd(event, this));
        }
        event.getPortletRequest().setAttribute(SportletProperties.RENDER_OUTPUT + componentIDStr, col);
    }

    public Object clone() throws CloneNotSupportedException {
        PortletColumnLayout g = (PortletColumnLayout) super.clone();
        return g;
    }

}



