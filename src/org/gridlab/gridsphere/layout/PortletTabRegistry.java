package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfig;

import java.io.IOException;
import java.io.File;
import java.util.Hashtable;
import java.util.Map;
import java.util.Collection;
import java.util.Collections;

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

    private PortletTabRegistry() {}

    public synchronized static void addApplicationTab(String webAppName, String tabXMLfile) throws IOException, PersistenceManagerException {
        PortletTabbedPane webAppTabs = PortletLayoutDescriptor.loadPortletTabs(tabXMLfile, layoutMappingFile);
        tabDescriptors.put(webAppName, tabXMLfile);
        applicationTabs.put(webAppName, webAppTabs);
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

    public synchronized static void removeApplicationTab(String webAppName) {
        applicationTabs.remove(webAppName);
    }

    public static PortletTabbedPane getApplicationTabs(String webAppName) {
        return (PortletTabbedPane)applicationTabs.get(webAppName);
    }

    public static PortletTabbedPane getGroupTabs(String groupName) {
        return (PortletTabbedPane)groupTabs.get(groupName);
    }

    public static void newGroupTab(String groupName) throws IOException, PersistenceManagerException {
        File f = new File(groupLayoutDir);
        if (!f.exists()) {
            f.mkdir();
        }
        String groupFile = groupLayoutDir + File.separator + groupName + ".xml";
        tabDescriptors.put(groupName, groupFile);
        PortletTabbedPane tabbedPane = new PortletTabbedPane();
        PortletLayoutDescriptor.savePortletTabbedPane(tabbedPane, groupFile, layoutMappingFile);
        groupTabs.put(groupName, tabbedPane);
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
        groupTabs.remove(groupName);
    }

    public static Map getApplicationTabs() {
        return Collections.unmodifiableMap(applicationTabs);
    }

    public static String getTabDescriptorPath(String webAppName) {
        return (String)tabDescriptors.get(webAppName);
    }


}
