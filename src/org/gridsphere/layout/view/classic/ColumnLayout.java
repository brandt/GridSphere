/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id: ColumnLayout.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.layout.view.classic;

import org.gridsphere.layout.PortletComponent;
import org.gridsphere.layout.view.BaseRender;
import org.gridsphere.layout.view.Render;
import org.gridsphere.portletcontainer.GridSphereEvent;


public class ColumnLayout extends BaseRender implements Render {

    protected static final StringBuffer TOP_COLUMN = new StringBuffer("\n<!-- START COLUMN --><table width=\"100%\" cellspacing=\"2\" cellpadding=\"0\"><tbody>");
    protected static final StringBuffer TOP_COLUMN_BORDER = new StringBuffer("\n<tr><td valign=\"top\" width=\"100%\">");
    protected static final StringBuffer BOTTOM_COLUMN_BORDER = new StringBuffer("</td></tr>\n");
    protected static final StringBuffer BOTTOM_COLUMN = new StringBuffer("</tbody></table> <!-- END COLUMN -->\n");

    public ColumnLayout() {
    }

    public StringBuffer doStart(GridSphereEvent event, PortletComponent comp) {
        return TOP_COLUMN;
    }

    public StringBuffer doStartBorder(GridSphereEvent event, PortletComponent comp) {
        return TOP_COLUMN_BORDER;
    }

    public StringBuffer doEndBorder(GridSphereEvent event, PortletComponent comp) {
        return BOTTOM_COLUMN_BORDER;
    }

    public StringBuffer doEnd(GridSphereEvent event, PortletComponent comp) {
        return BOTTOM_COLUMN;
    }



}



