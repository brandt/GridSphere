/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.layout.impl;

import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.provider.portletui.beans.FileInputBean;
import org.gridlab.gridsphere.services.core.layout.LayoutManagerService;
import org.gridlab.gridsphere.layout.*;

import java.util.List;
import java.util.Iterator;
import java.util.Vector;


/**
 * The <code>LayoutManagerService</code> manages users layouts
 */
public class LayoutManagerServiceImpl implements PortletServiceProvider, LayoutManagerService {

    private PortletLog log = SportletLog.getInstance(LayoutManagerServiceImpl.class);
    private PortletLayoutEngine layoutEngine = PortletLayoutEngine.getInstance();
    private PortletPageFactory pageFactory = null;

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        try {
        pageFactory = PortletPageFactory.getInstance();
        } catch (Exception e) {
          //
        }
    }

    public void destroy() {}

    public void setTheme(PortletRequest req, String theme) {
        PortletPage page = pageFactory.createPortletPage(req);
        page.setTheme(theme);
        //page.init(new ArrayList());
    }

    public void reloadPage(PortletRequest req) {
        PortletPage page = pageFactory.createPortletPage(req);
        page.init(new Vector());
    }

    public String getTheme(PortletRequest req) {
        PortletPage page = pageFactory.createPortletPage(req);
        return page.getTheme();
    }

    public String[] getTabNames(PortletRequest req) {
        PortletPage page = pageFactory.createPortletPage(req);
        PortletTabbedPane pane = page.getPortletTabbedPane();
        List tabs = pane.getPortletTabs();
        String[] tabnames = new String[tabs.size()];
        int i;
        for (i = 0; i < tabs.size(); i++) {
            PortletTab tab = (PortletTab)tabs.get(i);
            tabnames[i] = tab.getTitle();
        }
        return tabnames;
    }

    public void setTabNames(PortletRequest req, String[] tabNames) {
        PortletPage page = pageFactory.createPortletPage(req);
        PortletTabbedPane pane = page.getPortletTabbedPane();
        List tabs = pane.getPortletTabs();
        if (tabNames.length == tabs.size()) {
            for (int i = 0; i < tabs.size(); i++) {
                PortletTab tab = (PortletTab)tabs.get(i);
                tab.setTitle(tabNames[i]);
            }
        } else {
            log.debug("Passed in tab names size does not match exist");
        }
    }

    public String[] getSubTabNames(PortletRequest req, String tabName) {
        PortletTab tab = findPortletTab(req, tabName);
        if (tab != null) {
            PortletComponent pc = tab.getPortletComponent();
            if (pc instanceof PortletTabbedPane) {
                PortletTabbedPane np = (PortletTabbedPane)pc;
                List subtabs = np.getPortletTabs();
                String[] newtabs = new String[subtabs.size()];
                for (int j = 0; j < subtabs.size(); j++) {
                    PortletTab t = (PortletTab)subtabs.get(j);
                    newtabs[j] = t.getTitle();
                }
                return newtabs;
            }
        }
        return new String[]{""};
    }

    public void setSubTabNames(PortletRequest req, String tabName, String[] subTabNames) {
        PortletTab tab = findPortletTab(req, tabName);
        if (tab != null) {
            PortletComponent pc = tab.getPortletComponent();
            if (pc instanceof PortletTabbedPane) {
                PortletTabbedPane np = (PortletTabbedPane)pc;
                List subtabs = np.getPortletTabs();
                if (subtabs.size() == subTabNames.length) {
                    for (int j = 0; j < subtabs.size(); j++) {
                        PortletTab t = (PortletTab)subtabs.get(j);
                        t.setTitle(subTabNames[j]);
                    }
                }
            }
        }
    }


    public String[] getFrameClassNames(PortletRequest req, String subTabName) {
        PortletPage page = pageFactory.createPortletPage(req);
        List cidList = page.getComponentIdentifierList();
        Iterator it = cidList.iterator();
        while (it.hasNext()) {
            ComponentIdentifier cid = (ComponentIdentifier)it.next();
            cid.getClassName().equals(PortletTab.class.getName());
        }
        return new String[]{""};
    }

    public void setFrameClassNames(PortletRequest req, String subTabName, String[] frameNames) {

    }

    public void removeTab(String tabName) {

    }

    public void removeFrame(String frameClassName) {

    }

    private PortletTab findPortletTab(PortletRequest req, String tabName) {
        PortletPage page = pageFactory.createPortletPage(req);
        PortletTabbedPane pane = page.getPortletTabbedPane();
        List tabs = pane.getPortletTabs();
        for (int i = 0; i < tabs.size(); i++) {
            PortletTab tab = (PortletTab)tabs.get(i);
            if (tab.getTitle().equals(tabName)) {
                return tab;
            } else {
                PortletComponent pc = tab.getPortletComponent();
                if (pc instanceof PortletTabbedPane) {
                    PortletTabbedPane np = (PortletTabbedPane)pc;
                    List subtabs = np.getPortletTabs();
                    for (int j = 0; j < subtabs.size(); j++) {
                        PortletTab t = (PortletTab)subtabs.get(i);
                        if (tab.getTitle().equals(tabName)) {
                            return tab;
                        }
                    }
                }
            }
        }
        return null;
    }

}
