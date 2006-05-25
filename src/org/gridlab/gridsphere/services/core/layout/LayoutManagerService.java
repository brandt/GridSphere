/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.layout;

import org.gridlab.gridsphere.layout.PortletPage;
import org.gridlab.gridsphere.layout.PortletTabbedPane;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.portlet.service.PortletService;

/**
 * The <code>LayoutManagerService</code> manages users layouts
 */
public interface LayoutManagerService extends PortletService {

    public void reloadPage(PortletRequest req);

    public void refreshPage(PortletRequest req);

    public void addGroupTab(PortletRequest req, PortletGroup group);

    public void removeGroupTab(PortletRequest req, PortletGroup group);

    public PortletTabbedPane getUserTabbedPane(PortletRequest req);

    public PortletTabbedPane createUserTabbedPane(PortletRequest req, int cols, String tabName);

    public PortletPage getPortletPage(PortletRequest req);

}
