/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer;

import org.gridlab.gridsphere.portletcontainer.ApplicationPortlet;

import java.util.*;

public class PortletRegistryManager {

    private static PortletRegistryManager instance = new PortletRegistryManager();

    private Hashtable allApplicationPortlets = new Hashtable();

    private PortletRegistryManager() {}

    public static PortletRegistryManager getInstance() {
        return instance;
    }

    public void addApplicationPortlet(ApplicationPortlet appPortlet) {
        allApplicationPortlets.put(appPortlet.getPortletAppID(), appPortlet);
    }

    public void removeApplicationPortlet(String applicationPortletID) {
        allApplicationPortlets.remove(applicationPortletID);
    }

    public void reloadApplicationPortlet(String applicationPortletID) {
        allApplicationPortlets.remove(applicationPortletID);
    }

    public ApplicationPortlet getApplicationPortlet(String applicationPortletID) {
        return (ApplicationPortlet)allApplicationPortlets.get(applicationPortletID);
    }

    public Collection getAllApplicationPortlets() {
        return allApplicationPortlets.values();
    }

    public Collection getApplicationPortlets(String webApplicationName) {
        List webappPortlets = new Vector();
        Enumeration enum = allApplicationPortlets.elements();
        ApplicationPortlet appPortlet;
        while (enum.hasMoreElements()) {
            appPortlet = (ApplicationPortlet)enum.nextElement();
            if (appPortlet.getWebApplication().equals((webApplicationName))) {
                webappPortlets.add(appPortlet);
            }
        }
        return webappPortlets;
    }

    public static final String getApplicationPortletID(String concretePortletID) {
        int i = concretePortletID.lastIndexOf(".");
        if (i < 0) return "";
        return concretePortletID.substring(0, i);
    }
}
