/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.layout.view.Render;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.PortletURI;
import org.gridlab.gridsphere.portlet.impl.SportletProperties;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public class PortletRowLayout extends PortletFrameLayout implements Cloneable, Serializable {

    private transient Render rowView = null;

    public List init(PortletRequest req, List list) {
        list = super.init(req, list);
        rowView = (Render)getRenderClass(req, "RowLayout");
        return list;
    }

    public void doRender(GridSphereEvent event) {
        StringBuffer row = new StringBuffer();
        PortletComponent p;
        row.append(rowView.doStart(event, this));
        for (int i = 0; i < components.size(); i++) {
            p = (PortletComponent) components.get(i);
            row.append(rowView.doStartBorder(event, p));
            if (p.getVisible()) {
                p.doRender(event);
                row.append(p.getBufferedOutput(event.getPortletRequest()));
            }
            row.append(rowView.doEndBorder(event, this));
        }

        row.append(rowView.doEnd(event, this));


        setBufferedOutput(event.getPortletRequest(), row);
    }


    public Object clone() throws CloneNotSupportedException {
        return (PortletRowLayout) super.clone();
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(super.toString());
        for (int i = 0; i < components.size(); i++) {
            sb.append(components.toString());
        }
        return sb.toString();
    }
}
 


