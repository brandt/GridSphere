/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.layout;

import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.layout.*;

import java.util.List;

/**
 * The <code>LayoutManagerService</code> manages users layouts
 */
public interface LayoutManagerService extends PortletService {

    public void reloadPage(PortletRequest req);

    public void refreshPage(PortletRequest req);

    public void addGroupTab(PortletRequest req, String groupName);

    public PortletTabbedPane getUserTabbedPane(PortletRequest req);

    public PortletTabbedPane createUserTabbedPane(PortletRequest req, int cols, String label);

    public PortletPage getPortletPage(PortletRequest req);
  
}
