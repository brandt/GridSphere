package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfig;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public class PortletTabRegistry {

    private static String layoutMappingFile = GridSphereConfig.getServletContext().getRealPath("/WEB-INF/mapping/layout-mapping.xml");

    // Store application tabs in a hash
    private static Map applicationTabs = new Hashtable();
    private static Map tabDescriptors = new Hashtable();

    private PortletTabRegistry() {}

    public synchronized static void addApplicationTab(String webAppName, String tabXMLfile) throws IOException, PersistenceManagerException {
        PortletTabbedPane webAppTabs = PortletLayoutDescriptor.loadPortletTabs(tabXMLfile, layoutMappingFile);
        tabDescriptors.put(webAppName, tabXMLfile);
        applicationTabs.put(webAppName, webAppTabs);
    }

    public synchronized static void removeApplicationTab(String webAppName) {
        applicationTabs.remove(webAppName);
    }

    public static PortletTabbedPane getApplicationTabs(String webAppName) {
        return (PortletTabbedPane)applicationTabs.get(webAppName);
    }

    public static String getTabDescriptorPath(String webAppName) {
        return (String)tabDescriptors.get(webAppName);
    }
}
