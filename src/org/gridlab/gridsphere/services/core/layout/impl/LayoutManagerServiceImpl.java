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

    private Map userPortlets = new Hashtable();

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
        page.init(req, new Vector());

        // save user tab
       /*
        try {
            page.save();
        } catch (IOException e) {
            log.error("Unable to save portlet page", e);
        }
        */
    }

    public void refreshPage(PortletRequest req) {
        pageFactory.removePortletPage(req);

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

    public void addApplicationTab(PortletRequest req, String webAppName) {
        pageFactory.addPortletApplicationTab(req, webAppName);
    }

    public void addGroupTab(PortletRequest req, String groupName) {
        pageFactory.addPortletGroupTab(req, groupName);
        /*
        PortletPage page = pageFactory.createPortletPage(user);
        try {
            System.err.println("Saving user layout!!!!");
            page.save();
        } catch (IOException e) {
            log.error("Unable to save layout!", e);
        }
        */
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
                    log.debug("component has portlet: " + cid.getPortletClass());
                    //removePortletComponent(pc, req);
                    pc.remove(null, req);
                    removeSubscribedPortlet(req, cid.getPortletClass());
                }
            }
        }

    }

    public void removePortlets(PortletRequest req, User user, List portletClassNames) {
        PortletPage page = pageFactory.createPortletPage(req);
        page.init(req, new ArrayList());
        List cidList = page.getComponentIdentifierList();
        Iterator it = cidList.iterator();
        while (it.hasNext()) {
            ComponentIdentifier cid = (ComponentIdentifier)it.next();
            log.debug("found cid: " + cid.getClassName());
            PortletComponent pc = cid.getPortletComponent();
            if (pc instanceof PortletFrame) {
                if (portletClassNames.contains(cid.getPortletClass())) {
                    log.debug("component has portlet: " + cid.getPortletClass());
                    //removePortletComponent(pc, req);
                    pc.remove(null, req);
                    removeSubscribedPortlet(req, cid.getPortletClass());
                }
            }
        }
        try {
            page.save();
        } catch (IOException e) {
            log.error("Unable to save layout", e);
        }
    }

    public void removePortlet(PortletRequest req, String portletClassName) {

        PortletPage page = pageFactory.createPortletPage(req);
        List cidList = page.getComponentIdentifierList();
        Iterator it = cidList.iterator();
        while (it.hasNext()) {
            ComponentIdentifier cid = (ComponentIdentifier)it.next();
            PortletComponent pc = cid.getPortletComponent();
            if (pc instanceof PortletFrame) {
                if (portletClassName.equals(cid.getPortletClass())) {
                    log.debug("component has portlet: " + cid.getPortletClass());
                    //removePortletComponent(pc, req);
                    pc.remove(null, req);
                    removeSubscribedPortlet(req, cid.getPortletClass());
                }
            }
        }
    }

    public PortletTabbedPane createUserTabbedPane(PortletRequest req, int cols, String label) {
        PortletTabbedPane pane = pageFactory.createNewUserPane(req, cols, label);
        return pane;
    }

    public PortletTabbedPane getUserTabbedPane(PortletRequest req) {
        PortletPage page = pageFactory.createPortletPage(req);
        
        PortletTabbedPane pane = pageFactory.getUserTabbedPane(req);
        return pane;
    }

    public PortletPage getPortletPage(PortletRequest req) {
        return pageFactory.createPortletPage(req);
    }


    public List getSubscribedPortlets(PortletRequest req) {
        User user = req.getUser();
        String uid = user.getID();
        return getAllPortletNames(req);
        /*
        if (!userPortlets.containsKey(uid)) {
            List names = getAllPortletNames(req);
            userPortlets.put(uid, names);
        }
        return (List)userPortlets.get(uid);
        */
    }

    public void setSubscribedPortlets(PortletRequest req, List portletClassNames) {
        userPortlets.put(req.getUser().getID(), portletClassNames);
    }

    public synchronized void addSubscribedPortlet(PortletRequest req, String portletClassName) {
        User user = req.getUser();
        String uid = user.getID();
        if (!userPortlets.containsKey(uid)) {
            List l = new Vector();
            l.add(portletClassName);
            userPortlets.put(uid, l);
        } else {
            List l = (List)userPortlets.get(uid);
            if (!l.contains(portletClassName)) l.add(portletClassName);
        }

    }

    public void removeSubscribedPortlet(PortletRequest req, String portletClassName) {
        User user = req.getUser();
        String uid = user.getID();
        if (userPortlets.containsKey(uid)) {
            List l = (List)userPortlets.get(uid);
            if (l != null) l.remove(portletClassName);
            removePortlet(req, portletClassName);
        }
    }

    public void removeSubscribedPortlets(PortletRequest req, List portletClassNames) {
        User user = req.getUser();
        String uid = user.getID();
        if (userPortlets.containsKey(uid)) {
            List l = (List)userPortlets.get(uid);
            Iterator it = portletClassNames.iterator();
            while (it.hasNext()) {
                String concId = (String)it.next();
                if (l.contains(concId)) {
                    l.remove(concId);
                }
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
                log.debug("adding subscribed portlet: " + cid.getPortletClass());
                portlets.add(cid.getPortletClass());

            }
        }
        return portlets;
        //return Collections.unmodifiableList(portlets);
    }

    public List getTabNames(PortletRequest req) {
        PortletPage page = pageFactory.createPortletPage(req);
        PortletTabbedPane pane = page.getPortletTabbedPane();
        PortletRole userRole = req.getRole();
        List tabs = pane.getPortletTabs();
        List tabnames = new ArrayList();
        int i;
        for (i = 0; i < tabs.size(); i++) {
            PortletTab tab = (PortletTab)tabs.get(i);
            String lang = req.getLocale().getLanguage();
            PortletRole tabRole = tab.getRequiredRole();
            if (userRole.compare(userRole, tabRole) >= 0) {
                tabnames.add(tab.getTitle(lang));
            }
        }
        return tabnames;
    }

    public void setTabNames(PortletRequest req, List tabNames) {
        PortletPage page = pageFactory.createPortletPage(req);
        PortletTabbedPane pane = page.getPortletTabbedPane();
        List tabs = pane.getPortletTabs();
        String lang = req.getLocale().getLanguage();
        if (tabNames.size() == tabs.size()) {
            for (int i = 0; i < tabs.size(); i++) {
                PortletTab tab = (PortletTab)tabs.get(i);
                tab.setTitle(lang, (String)tabNames.get(i));
            }
        } else {
            log.debug("Passed in tab names size does not match exist");
        }
    }

    public PortletTableLayout getPortletLayout(PortletRequest req, String subtabName) {
        PortletTab tab = findPortletTab(req, subtabName);
        if (tab != null) {
            PortletComponent pc = tab.getPortletComponent();
            System.err.println("found tab with comp" + pc.getClass().getName() + " " + tab.getLabel());
            if (pc instanceof PortletTableLayout) {
                return (PortletTableLayout)pc;
            }
        }
        return null;
    }


    public List getSubTabNames(PortletRequest req, String tabName) {
        PortletTab tab = findPortletTab(req, tabName);
        PortletRole userRole = req.getRole();
        if (tab != null) {
            PortletComponent pc = tab.getPortletComponent();
            if (pc instanceof PortletTabbedPane) {
                PortletTabbedPane np = (PortletTabbedPane)pc;
                List subtabs = np.getPortletTabs();
                List newtabs = new ArrayList();
                String lang = req.getLocale().getLanguage();
                for (int j = 0; j < subtabs.size(); j++) {
                    PortletTab t = (PortletTab)subtabs.get(j);
                    PortletRole tabRole = t.getRequiredRole();
                    if (userRole.compare(userRole, tabRole) >= 0) {
                        newtabs.add(t.getTitle(lang));
                    }
                }
                return newtabs;
            }
        }
        return new ArrayList();
    }

    public void setSubTabNames(PortletRequest req, String tabName, List subTabNames) {
        PortletTab tab = findPortletTab(req, tabName);
        if (tab != null) {
            String lang = req.getLocale().getLanguage();
            PortletComponent pc = tab.getPortletComponent();
            if (pc instanceof PortletTabbedPane) {
                PortletTabbedPane np = (PortletTabbedPane)pc;
                List subtabs = np.getPortletTabs();
                if (subtabs.size() == subTabNames.size()) {
                    for (int j = 0; j < subtabs.size(); j++) {
                        PortletTab t = (PortletTab)subtabs.get(j);
                        t.setTitle(lang, (String)subTabNames.get(j));
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
        PortletTab tab = findPortletTab(req, tabName);
        if (tab != null) {
            System.err.println("found a tab!!!!");
            PortletTabbedPane pane = (PortletTabbedPane)tab.getParentComponent();
            System.err.println("trying to remove tab from pane!!!!");
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
        String lang = req.getLocale().getLanguage();
        for (int i = 0; i < tabs.size(); i++) {
            PortletTab tab = (PortletTab)tabs.get(i);
            if (tab.getTitle(lang).equals(tabName)) {
                return tab;
            } else {
                PortletComponent pc = tab.getPortletComponent();
                if (pc instanceof PortletTabbedPane) {
                    PortletTabbedPane np = (PortletTabbedPane)pc;
                    List subtabs = np.getPortletTabs();
                    for (int j = 0; j < subtabs.size(); j++) {
                        tab = (PortletTab)subtabs.get(j);
                        if (tab.getTitle(lang).equals(tabName)) {
                            return tab;
                        }
                    }
                }
            }
        }
        return null;
    }

}
