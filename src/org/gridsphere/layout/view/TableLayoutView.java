package org.gridsphere.layout.view;

import org.gridsphere.layout.PortletTableLayout;
import org.gridsphere.portletcontainer.GridSphereEvent;

public interface TableLayoutView extends Render {

    public StringBuffer doStartMaximizedComponent(GridSphereEvent event, PortletTableLayout tableLayout);

    public StringBuffer doEndMaximizedComponent(GridSphereEvent event, PortletTableLayout tableLayout);

    public StringBuffer doRenderUserSelects(GridSphereEvent event, PortletTableLayout tableLayout);
    
}
