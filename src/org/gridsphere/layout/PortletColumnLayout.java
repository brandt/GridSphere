/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridsphere.layout;

import org.gridsphere.layout.view.Render;
import org.gridsphere.portletcontainer.GridSphereEvent;

import javax.portlet.PortletRequest;
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

    public List<ComponentIdentifier> init(PortletRequest req, List<ComponentIdentifier> list) {
        list = super.init(req, list);
        colView = (Render) getRenderClass(req, "ColumnLayout");
        return list;
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
                col.append(colView.doStartBorder(event, this));
                if (p.getVisible()) {
                    p.doRender(event);
                    col.append(p.getBufferedOutput(event.getRenderRequest()));
                }
                col.append(colView.doEndBorder(event, this));
            }
            col.append(colView.doEnd(event, this));
        }
        setBufferedOutput(event.getRenderRequest(), col);
    }

    public Object clone() throws CloneNotSupportedException {
        return (PortletColumnLayout) super.clone();
    }

}



