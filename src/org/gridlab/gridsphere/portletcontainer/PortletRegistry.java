/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer;

import java.util.*;

/**
 * The <code>PortletRegistry</code> is simply a static <code>Hashtable</code> that keeps track
 * of all the application portlets known to the container.
 */
public class PortletRegistry {

    private static PortletRegistry instance = new PortletRegistry();

    private static Map allApplicationPortlets = new Hashtable();

    /**
     * Default instantiation disallowed
     */
    private PortletRegistry() {
    }

    /**
     * Return an instance of PortletRegistry
     */
    public static PortletRegistry getInstance() {
        return instance;
    }

    /**
     * Adds an application portlet to the registry
     *
     * @param appPortlet an <code>ApplicationPortlet</code>
     */
    public void addApplicationPortlet(ApplicationPortlet appPortlet) {
        allApplicationPortlets.put(appPortlet.getApplicationPortletID(), appPortlet);
    }

    /**
     * Removes an application portlet from the registry
     *
     * @param applicationPortletID the application portlet id
     */
    public void removeApplicationPortlet(String applicationPortletID) {
        allApplicationPortlets.remove(applicationPortletID);
    }

    /**
     * Return the application portlet with the corresponding id
     *
     * @param applicationPortletID the application portlet id
     * @return an application portlet
     */
    public ApplicationPortlet getApplicationPortlet(String applicationPortletID) {
        return (ApplicationPortlet) allApplicationPortlets.get(applicationPortletID);
    }

    /**
     * Returns all application portlets from the registry
     *
     * @return the collection of application portlets
     */
    public Collection getAllApplicationPortlets() {
        return allApplicationPortlets.values();
    }

    /**
     * Returns all application portlets from the supplied portlet web application
     *
     * @param webApplicationName the portlet web application name
     * @return the collection of application portlets
     */
    public Collection getApplicationPortlets(String webApplicationName) {
        List webappPortlets = new Vector();
        Set set = allApplicationPortlets.keySet();
        ApplicationPortlet appPortlet;
        Iterator it = set.iterator();
        while (it.hasNext()) {
            String concretePortletID = (String)it.next();
            appPortlet = (ApplicationPortlet) allApplicationPortlets.get(concretePortletID);
            if (appPortlet.getWebApplicationName().equals((webApplicationName))) {
                webappPortlets.add(appPortlet);
            }
        }
        return webappPortlets;
    }

    /**
     * Returns the application portlet id given a concrete portlet id
     *
     * @param concretePortletID the concrete portlet id
     * @return the application portlet id
     */
    public static final String getApplicationPortletID(String concretePortletID) {
        int i = concretePortletID.lastIndexOf(".");
        if (i < 0) return "";
        return concretePortletID.substring(0, i);
    }
}
