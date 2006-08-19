/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id: PortletColumnLayout.java 4986 2006-08-04 09:54:38Z novotny $
 */
package org.gridsphere.layout;

import org.gridsphere.layout.view.Render;
import org.gridsphere.portlet.PortletRequest;
import org.gridsphere.portletcontainer.GridSphereEvent;

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
        colView = (Render)getRenderClass(req, "ColumnLayout");
        return list;
    }

    public void remove(PortletComponent pc, PortletRequest req) {
        components.remove(pc);
        if (getPortletComponents().isEmpty()) {
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
        setBufferedOutput(event.getPortletRequest(), col);
    }

    public Object clone() throws CloneNotSupportedException {
        PortletColumnLayout g = (PortletColumnLayout) super.clone();
        return g;
    }

}



