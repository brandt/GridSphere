/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id: RowLayout.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.layout.view.classic;

import org.gridsphere.layout.PortletComponent;
import org.gridsphere.layout.view.BaseRender;
import org.gridsphere.layout.view.Render;
import org.gridsphere.portletcontainer.GridSphereEvent;

public class RowLayout extends BaseRender implements Render {

    protected static final StringBuffer TOP_ROW =
            new StringBuffer("\n<!-- START ROW --><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");

    protected static final StringBuffer BOTTOM_ROW_BORDER = new StringBuffer("</td>\n");

    protected static final StringBuffer BOTTOM_ROW = new StringBuffer("</tr></tbody></table><!-- END ROW -->\n");

    public StringBuffer doStart(GridSphereEvent event, PortletComponent comp) {
        return TOP_ROW;
    }

    public StringBuffer doStartBorder(GridSphereEvent event, PortletComponent p) {
        return new StringBuffer("\n<td valign=\"top\" width=\"" + p.getWidth() + "\">");
    }

    public StringBuffer doEndBorder(GridSphereEvent event, PortletComponent comp) {
        return BOTTOM_ROW_BORDER;
    }

    public StringBuffer doEnd(GridSphereEvent event, PortletComponent comp) {
        return BOTTOM_ROW;
    }

}
 


