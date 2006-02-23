/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id: ColumnLayout.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridlab.gridsphere.layout.view.standard;

import org.gridlab.gridsphere.layout.PortletComponent;
import org.gridlab.gridsphere.layout.view.BaseRender;
import org.gridlab.gridsphere.layout.view.Render;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;


public class ColumnLayout extends BaseRender implements Render {

    // unuseful start and end DIVs removed
    protected static final StringBuffer TOP_COLUMN = new StringBuffer("");
    protected static final StringBuffer TOP_COLUMN_BORDER = new StringBuffer("");
    protected static final StringBuffer BOTTOM_COLUMN_BORDER = new StringBuffer("");
    protected static final StringBuffer BOTTOM_COLUMN = new StringBuffer("");

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



