/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.layout.impl;

import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.PortletRole;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.services.core.layout.LayoutManagerService;
import org.gridlab.gridsphere.layout.*;
import org.gridlab.gridsphere.portletcontainer.PortletRegistry;

import java.util.*;
import java.io.IOException;

/**
 * The <code>LayoutManagerService</code> manages users layouts
 */
public class LayoutManagerServiceImpl implements PortletServiceProvider, LayoutManagerService {

    private PortletLog log = SportletLog.getInstance(LayoutManagerServiceImpl.class);

    private PortletPageFactory pageFactory = null;

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        try {
            pageFactory = PortletPageFactory.getInstance();
        } catch (Exception e) {
            log.error("Unable to get PortletPageFactory instance!", e);
        }
    }

    public void destroy() {}

    public void reloadPage(PortletRequest req) {
        PortletPage page = pageFactory.createPortletPage(req);
        
        page.init(req, new Vector());
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

    public PortletTabbedPane createUserTabbedPane(PortletRequest req, int cols, String label) {
        return pageFactory.createNewUserPane(req, cols, label);    
    }

    public PortletPage getPortletPage(PortletRequest req) {
        return pageFactory.createPortletPage(req);
    }


}
