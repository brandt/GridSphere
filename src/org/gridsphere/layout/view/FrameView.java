package org.gridsphere.layout.view;

import org.gridsphere.layout.PortletFrame;
import org.gridsphere.portletcontainer.GridSphereEvent;

public interface FrameView extends Render {

    public StringBuffer doRenderMinimizeFrame(GridSphereEvent event, PortletFrame frame);

    public StringBuffer doRenderCloseFrame(GridSphereEvent event, PortletFrame frame);

}
