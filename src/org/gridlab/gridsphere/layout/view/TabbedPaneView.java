package org.gridlab.gridsphere.layout.view;

import org.gridlab.gridsphere.layout.PortletTab;
import org.gridlab.gridsphere.layout.PortletTabbedPane;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

public interface TabbedPaneView extends Render {

    public StringBuffer doRenderTab(GridSphereEvent event, PortletTabbedPane tabPane, PortletTab tab);

}
