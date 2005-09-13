/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.layout.impl;

import org.gridlab.gridsphere.layout.PortletPage;
import org.gridlab.gridsphere.layout.PortletPageFactory;
import org.gridlab.gridsphere.layout.PortletTabbedPane;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.core.layout.LayoutManagerService;

import java.util.ArrayList;

/**
 * The <code>LayoutManagerService</code> manages users layouts
 */
public class LayoutManagerServiceImpl implements LayoutManagerService {

    private PortletLog log = SportletLog.getInstance(LayoutManagerServiceImpl.class);

    private PortletPageFactory pageFactory = null;

    public PortletPageFactory getPortletPageFactory() {
        return pageFactory;
    }

    public void setPortletPageFactory(PortletPageFactory pageFactory) {
        this.pageFactory = pageFactory;
    }

    public void reloadPage(PortletRequest req) {
        PortletPage page = pageFactory.createPortletPage(req);
        page.init(req, new ArrayList());
    }

    public void refreshPage(PortletRequest req) {
        pageFactory.removePortletPage(req);
    }

    public void addGroupTab(PortletRequest req, String groupName) {
        pageFactory.addPortletGroupTab(req, groupName);
    }

    public void removeGroupTab(PortletRequest req, String groupName) {
        pageFactory.removePortletGroupTab(req, groupName);
    }

    public PortletTabbedPane getUserTabbedPane(PortletRequest req) {
        return pageFactory.getUserTabbedPane(req);
    }

    public PortletTabbedPane createUserTabbedPane(PortletRequest req, int cols, String tabName) {
        return pageFactory.createNewUserPane(req, cols, tabName);
    }

    public PortletPage getPortletPage(PortletRequest req) {
        return pageFactory.createPortletPage(req);
    }


}
