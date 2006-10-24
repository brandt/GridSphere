package org.gridsphere.layout.view;

import org.gridsphere.layout.PortletTab;
import org.gridsphere.layout.PortletTabbedPane;
import org.gridsphere.layout.PortletNavMenu;
import org.gridsphere.portletcontainer.GridSphereEvent;

public interface TabbedPaneView extends Render {

    public StringBuffer doRenderTab(GridSphereEvent event, PortletNavMenu navMenu, PortletTab tab);

    public StringBuffer doRenderEditTab(GridSphereEvent event, PortletNavMenu navMenu, boolean isSelected);

}
