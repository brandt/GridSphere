/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.layout.impl;

import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.PortletRole;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.services.core.layout.LayoutManagerService;
import org.gridlab.gridsphere.layout.*;

import java.util.*;


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

    public void addPortletTab(PortletRequest req, PortletTab tab) {
        PortletPage page = pageFactory.createPortletPage(req);
        PortletTabbedPane pane = page.getPortletTabbedPane();
        pane.addTab(tab);
    }

    public void removePortlets(PortletRequest req, List portletClassNames) {

        Iterator cit = portletClassNames.iterator();
        while (cit.hasNext()) {
            System.err.println("removing " + (String)cit.next());
        }

        PortletPage page = pageFactory.createPortletPage(req);
        List cidList = page.getComponentIdentifierList();
        Iterator it = cidList.iterator();
        while (it.hasNext()) {
            ComponentIdentifier cid = (ComponentIdentifier)it.next();
            PortletComponent pc = cid.getPortletComponent();
            if (pc instanceof PortletFrame) {
                if (portletClassNames.contains(cid.getPortletClass())) {
                    System.err.println("component has portlet: " + cid.getPortletClass() + cid.getClassName());
                    removePortletComponent(pc);
                }
            }
        }

    }

    public PortletPage getPortletPage(PortletRequest req) {
        return pageFactory.createPortletPage(req);
    }

    private void removePortletComponent(PortletComponent pc) {
        PortletComponent parent = pc.getParentComponent();
        if (parent instanceof PortletFrameLayout) {
            PortletFrameLayout layout = (PortletFrameLayout)parent;
            layout.removePortletComponent(pc);
            if (layout.getPortletComponents().size() == 0) {
                removePortletComponent(parent);
            }
        }

    }

    public List getAllPortletNames(PortletRequest req) {
        List portlets = new Vector();
        PortletPage page = pageFactory.createPortletPage(req);
        List cids = page.getComponentIdentifierList();
        Iterator it = cids.iterator();
        while (it.hasNext()) {
            ComponentIdentifier cid = (ComponentIdentifier) it.next();
            PortletComponent pc = cid.getPortletComponent();
            if (pc instanceof PortletFrame) {
                portlets.add(cid.getPortletClass());

            }
        }
        return Collections.unmodifiableList(portlets);
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

    public PortletTableLayout getPortletLayout(PortletRequest req, String subtabName) {
        PortletTab tab = findPortletTab(req, subtabName);
        if (tab != null) {
            PortletComponent pc = tab.getPortletComponent();
            System.err.println("found tab with comp" + pc.getClass().getName() + " " + tab.getTitle());
            if (pc instanceof PortletTableLayout) {
                return (PortletTableLayout)pc;
            }
        }
        return null;
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

    public void removeTab(PortletRequest req, String tabName) {
        PortletPage page = pageFactory.createPortletPage(req);
        PortletTab tab = findPortletTab(req, tabName);
        if (tab != null) {
            System.err.println("found a tab!!!!");
            PortletTabbedPane pane = (PortletTabbedPane)tab.getParentComponent();
            System.err.println("trying to remoive tab from pane!!!!");
            pane.removeTab(tab);
        }

    }

    public void removeFrame(String frameClassName) {

    }

    public PortletTab getPortletTab(PortletRequest req, String tabName) {
        return  findPortletTab(req, tabName);
    }

    private PortletTab findPortletTab(PortletRequest req, String tabName) {
        PortletPage page = pageFactory.createPortletPage(req);
        PortletTabbedPane pane = page.getPortletTabbedPane();
        List tabs = pane.getPortletTabs();
        for (int i = 0; i < tabs.size(); i++) {
            PortletTab tab = (PortletTab)tabs.get(i);
            System.err.println(" tab title= " + tab.getTitle());
            if (tab.getTitle().equals(tabName)) {
                return tab;
            } else {
                PortletComponent pc = tab.getPortletComponent();
                if (pc instanceof PortletTabbedPane) {
                    PortletTabbedPane np = (PortletTabbedPane)pc;
                    List subtabs = np.getPortletTabs();
                    for (int j = 0; j < subtabs.size(); j++) {
                        tab = (PortletTab)subtabs.get(j);
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
