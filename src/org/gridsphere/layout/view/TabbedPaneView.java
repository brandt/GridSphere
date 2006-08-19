package org.gridsphere.layout.view;

import org.gridsphere.layout.PortletTab;
import org.gridsphere.layout.PortletTabbedPane;
import org.gridsphere.portletcontainer.GridSphereEvent;

public interface TabbedPaneView extends Render {

    public StringBuffer doRenderTab(GridSphereEvent event, PortletTabbedPane tabPane, PortletTab tab);

    public StringBuffer doRenderEditTab(GridSphereEvent event, PortletTabbedPane tabPane, boolean isSelected);

}
