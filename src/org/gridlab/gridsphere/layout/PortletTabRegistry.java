package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfig;
import org.gridlab.gridsphere.portletcontainer.PortletRegistry;
import org.gridlab.gridsphere.portletcontainer.ApplicationPortlet;
import org.gridlab.gridsphere.portletcontainer.ConcretePortlet;
import org.gridlab.gridsphere.portlet.impl.SportletRoleInfo;

import java.io.IOException;
import java.io.File;
import java.util.*;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public class PortletTabRegistry {

    private static String layoutMappingFile = GridSphereConfig.getServletContext().getRealPath("/WEB-INF/mapping/layout-mapping.xml");

    private static String groupLayoutDir = GridSphereConfig.getServletContext().getRealPath("/WEB-INF/layouts/groups");

    private static String guestLayoutFile = GridSphereConfig.getServletContext().getRealPath("/WEB-INF/layouts/GuestUserLayout.xml");


    // Store application tabs in a hash
    private static Map applicationTabs = new Hashtable();
    private static Map tabDescriptors = new Hashtable();
    private static Map groupTabs = new Hashtable();
    private static PortletPage guestPage = null;


    static {

        try {
            File f = new File(groupLayoutDir);
            if (!f.exists()) {
                f.mkdir();
            } else {
                String[] files = f.list();
                for (int i = 0; i < files.length; i++) {
                    String group = files[i];
                    int idx = group.indexOf(".xml");
                    String groupName = group.substring(0, idx);
                    String groupFile = groupLayoutDir + File.separator + groupName + ".xml";
                    tabDescriptors.put(groupName, groupFile);

                    PortletTabbedPane tabbedPane = PortletLayoutDescriptor.loadPortletTabs(groupFile, layoutMappingFile);
                    groupTabs.put(groupName, tabbedPane);
                }
            }
        } catch (Exception e) {
            System.err.println("Unable to load group layout files!!");
            e.printStackTrace();
        }
    }

    private PortletTabRegistry() {}

    public synchronized static void addGroupTab(String groupName, String tabXMLfile) throws IOException, PersistenceManagerException {
        PortletTabbedPane webAppTabs = PortletLayoutDescriptor.loadPortletTabs(tabXMLfile, layoutMappingFile);
        tabDescriptors.put(groupName, tabXMLfile);
        groupTabs.put(groupName, webAppTabs);
    }

    public static void reloadGuestLayout() throws IOException, PersistenceManagerException {
        guestPage = PortletLayoutDescriptor.loadPortletPage(guestLayoutFile, layoutMappingFile);
        guestPage.setLayoutDescriptor(guestLayoutFile);
    }

    public static PortletPage getGuestLayoutPage() {
        if (guestPage == null) {
            try {
            reloadGuestLayout();
            } catch (Exception e) {

            }
        }
        return guestPage;
    }

    public static String getGuestLayoutFile() {
        return guestLayoutFile;
    }

    public static PortletTabbedPane getApplicationTabs(String webAppName) {
        return (PortletTabbedPane)applicationTabs.get(webAppName);
    }

    public static PortletTabbedPane getGroupTabs(String groupName) {
        return (PortletTabbedPane)groupTabs.get(groupName);
    }


    public static void newGroupTab(String groupName, Set portletRoleInfo) throws IOException, PersistenceManagerException {
        File f = new File(groupLayoutDir);
        if (!f.exists()) {
            f.mkdir();
        }
        String groupFile = groupLayoutDir + File.separator + groupName + ".xml";
        tabDescriptors.put(groupName, groupFile);
        PortletTabbedPane parentPane = new PortletTabbedPane();
        Iterator it = portletRoleInfo.iterator();
        String portletClass, reqRole;
        PortletTab parentTab = new PortletTab();
        parentTab.setTitle(groupName);
        parentTab.setLabel(groupName);

        PortletTabbedPane childPane = new PortletTabbedPane();
        childPane.setStyle("sub-menu");
        parentTab.setPortletComponent(childPane);
        PortletTab pTab = null;
        PortletFrame pFrame = null;
        while (it.hasNext()) {
            SportletRoleInfo info = (SportletRoleInfo)it.next();
            portletClass = info.getPortletClass();
            reqRole = info.getRole();
            pTab = new PortletTab();
            PortletRegistry registry = PortletRegistry.getInstance();
            String appID = PortletRegistry.getApplicationPortletID(portletClass);
            ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
            ConcretePortlet conc = appPortlet.getConcretePortlet(portletClass);
            String tabName = conc.getDisplayName(Locale.ENGLISH);
            pTab.setTitle("en", tabName);
            pTab.setRequiredRoleAsString(reqRole);
            PortletTableLayout table = new PortletTableLayout();
            PortletRowLayout row = new PortletRowLayout();
            PortletColumnLayout col = new PortletColumnLayout();

            pFrame = new PortletFrame();
            pFrame.setPortletClass(portletClass);

            col.addPortletComponent(pFrame);
            row.addPortletComponent(col);
            table.addPortletComponent(row);

            pTab.setPortletComponent(table);

            childPane.addTab(pTab);
        }
        parentPane.addTab(parentTab);
        PortletLayoutDescriptor.savePortletTabbedPane(parentPane, groupFile, layoutMappingFile);
        groupTabs.put(groupName, parentPane);
    }

    public static synchronized void reloadTab(String tab, String tabXMLfile) {
        try {
            PortletTabbedPane pane = PortletLayoutDescriptor.loadPortletTabs(tabXMLfile, layoutMappingFile);
            if (groupTabs.containsKey(tab)) {
                groupTabs.put(tab, pane);
            }
            if (applicationTabs.containsKey(tab)) {
                applicationTabs.put(tab, pane);
            }
        } catch (Exception e) {

        }
    }

    public static Map getGroupTabs() {
        return Collections.unmodifiableMap(groupTabs);
    }

    public synchronized static void removeGroupTab(String groupName) {
        String groupFile = (String)tabDescriptors.get(groupName);
        if (groupFile != null) {
            File f = new File(groupFile);
            if (f.exists()) f.delete();
            tabDescriptors.remove(groupName);
        }
        groupTabs.remove(groupName);
    }

    public static Map getApplicationTabs() {
        return Collections.unmodifiableMap(applicationTabs);
    }

    public static String getTabDescriptorPath(String webAppName) {
        return (String)tabDescriptors.get(webAppName);
    }


}
