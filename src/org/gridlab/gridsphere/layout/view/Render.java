package org.gridlab.gridsphere.layout.view;

import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;
import org.gridlab.gridsphere.layout.PortletComponent;

public interface Render {

    public StringBuffer doStart(GridSphereEvent event, PortletComponent comp);

    public StringBuffer doStartBorder(GridSphereEvent event, PortletComponent comp);

    public StringBuffer doEndBorder(GridSphereEvent event, PortletComponent comp);

    public StringBuffer doEnd(GridSphereEvent event, PortletComponent comp);

}
