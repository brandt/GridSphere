package org.gridlab.gridsphere.layout.view;

import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;
import org.gridlab.gridsphere.layout.PortletComponent;
import org.gridlab.gridsphere.layout.PortletFrame;

public interface FrameView extends Render {

    public StringBuffer doRenderMinimizeFrame(GridSphereEvent event, PortletFrame frame);

    public StringBuffer doRenderCloseFrame(GridSphereEvent event, PortletFrame frame);

}
