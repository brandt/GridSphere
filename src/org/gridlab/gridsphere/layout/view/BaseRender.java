package org.gridlab.gridsphere.layout.view;

import org.gridlab.gridsphere.layout.PortletComponent;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

public class BaseRender implements Render {

    protected static StringBuffer BLANK = new StringBuffer();

    public StringBuffer doStart(GridSphereEvent event, PortletComponent comp) {
        return BLANK;
    }

    public StringBuffer doStartBorder(GridSphereEvent event, PortletComponent comp) {
        return BLANK;
    }

    public StringBuffer doEndBorder(GridSphereEvent event, PortletComponent comp) {
        return BLANK;
    }

    public StringBuffer doEnd(GridSphereEvent event, PortletComponent comp) {
        return BLANK;
    }

}
