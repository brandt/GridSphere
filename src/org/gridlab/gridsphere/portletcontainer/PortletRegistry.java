/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer;

import java.util.*;

public class PortletRegistry {

    private static PortletRegistry instance = new PortletRegistry();

    private Map allApplicationPortlets = new Hashtable();

    private PortletRegistry() {
    }

    public static PortletRegistry getInstance() {
        return instance;
    }

    public void addApplicationPortlet(ApplicationPortlet appPortlet) {
        allApplicationPortlets.put(appPortlet.getPortletAppID(), appPortlet);
    }

    public void removeApplicationPortlet(String applicationPortletID) {
        allApplicationPortlets.remove(applicationPortletID);
    }

    public ApplicationPortlet getApplicationPortlet(String applicationPortletID) {
        return (ApplicationPortlet) allApplicationPortlets.get(applicationPortletID);
    }

    public Collection getAllApplicationPortlets() {
        return allApplicationPortlets.values();
    }

    public Collection getApplicationPortlets(String webApplicationName) {
        List webappPortlets = new Vector();

        Set set = allApplicationPortlets.keySet();
        ApplicationPortlet appPortlet;
        Iterator it = set.iterator();
        while (it.hasNext()) {
            appPortlet = (ApplicationPortlet) it.next();
            if (appPortlet.getWebApplicationName().equals((webApplicationName))) {
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
