/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id: PortletRowLayout.java 4986 2006-08-04 09:54:38Z novotny $
 */
package org.gridsphere.layout;

import org.gridsphere.layout.view.Render;
import org.gridsphere.portlet.PortletRequest;
import org.gridsphere.portlet.PortletResponse;
import org.gridsphere.portlet.PortletURI;
import org.gridsphere.portlet.impl.SportletProperties;
import org.gridsphere.portletcontainer.GridSphereEvent;

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
 


