/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.layout;

import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.layout.PortletTab;
import org.gridlab.gridsphere.layout.PortletPage;
import org.gridlab.gridsphere.layout.PortletFrameLayout;
import org.gridlab.gridsphere.layout.PortletTableLayout;

import java.util.List;

/**
 * The <code>LayoutManagerService</code> manages users layouts
 */
public interface LayoutManagerService extends PortletService {

    //public String[] getPortletClassNames(PortletRequest req);

    public void setTheme(PortletRequest req, String theme);

    public String getTheme(PortletRequest req);

    public PortletTableLayout getPortletLayout(PortletRequest req, String subtabName);

    public PortletPage getPortletPage(PortletRequest req);

    public List getSubscribedPortlets(PortletRequest req);

    public void setSubscribedPortlets(PortletRequest req, List portletClassNames);

    public void addSubscribedPortlet(PortletRequest req, String portletClassName);

    public void addPortletTab(PortletRequest req, PortletTab tab);

    public void removeSubscribedPortlet(PortletRequest req, String portletClassName);

    public void removeSubscribedPortlets(PortletRequest req, List portletClassNames);

    public void removePortlets(PortletRequest req, List portletClassNames);

    public List getAllPortletNames(PortletRequest req);

    public void reloadPage(PortletRequest req);

    public String[] getTabNames(PortletRequest req);

    public void setTabNames(PortletRequest req, String[] tabNames);

    public String[] getSubTabNames(PortletRequest req, String tabName);

    public void setSubTabNames(PortletRequest req, String tabName, String[] subTabNames);

    public String[] getFrameClassNames(PortletRequest req, String subTabName);

    public void setFrameClassNames(PortletRequest req, String subTabName, String[] frameNames);

    public void removeTab(PortletRequest req, String tabName);

    public void removeFrame(String frameClassName);

}
