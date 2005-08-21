package org.gridlab.gridsphere.layout.view;

import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;
import org.gridlab.gridsphere.layout.PortletComponent;
import org.gridlab.gridsphere.layout.PortletFrame;
import org.gridlab.gridsphere.layout.PortletTableLayout;

public interface TableLayoutView extends Render {

    public StringBuffer doStartMaximizedComponent(GridSphereEvent event, PortletTableLayout tableLayout);

    public StringBuffer doEndMaximizedComponent(GridSphereEvent event, PortletTableLayout tableLayout);

    public StringBuffer doRenderUserSelects(GridSphereEvent event, PortletTableLayout tableLayout);
    
}
