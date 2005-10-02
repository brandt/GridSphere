/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout.view.modern;

import org.gridlab.gridsphere.layout.PortletComponent;
import org.gridlab.gridsphere.layout.view.BaseRender;
import org.gridlab.gridsphere.layout.view.Render;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

public class RowLayout extends BaseRender implements Render {

    protected static final StringBuffer TOP_ROW =
            new StringBuffer("\n<!-- START MODERN ROW --><div class=\"row\" >");

    protected static final StringBuffer BOTTOM_ROW_BORDER = new StringBuffer("</div>\n");

    protected static final StringBuffer BOTTOM_ROW = new StringBuffer("</div><!-- END MODERN ROW -->\n");

    public StringBuffer doStart(GridSphereEvent event, PortletComponent comp) {
        return TOP_ROW;
    }

    public StringBuffer doStartBorder(GridSphereEvent event, PortletComponent p) {
        String temp = "\n<div class=\"column\"";
        if (!p.getWidth().equals("")) {
           temp += " style=\"width: " + p.getWidth() + "\""; 
        }    
        temp += ">";        
        return new StringBuffer(temp);       
    }

    public StringBuffer doEndBorder(GridSphereEvent event, PortletComponent comp) {
        return BOTTOM_ROW_BORDER;
    }

    public StringBuffer doEnd(GridSphereEvent event, PortletComponent comp) {
        return BOTTOM_ROW;
    }

}
 


