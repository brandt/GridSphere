/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletResponse;

import java.util.List;


/**
 * The LayoutManager is responsible for constructing a layout appropriate
 * to the user's layout preferences.
 */
public interface LayoutManager extends PortletService {

    public void addLayoutComponent(String name, PortletComponent comp);

    public void addLayoutComponent(PortletComponent comp, Object constraints);

    public void layoutContainer(PortletContainer parent);

    public PortletDimension minimumLayoutSize(PortletContainer parent);

    public PortletDimension preferredLayoutSize(PortletContainer parent);

    public void removeLayoutComponent(PortletComponent comp);

    public PortletDimension maximumLayoutSize(PortletContainer target);
}


