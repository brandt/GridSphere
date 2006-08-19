/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: ApplicationSportletConfig.java 5032 2006-08-17 18:15:06Z novotny $
 */

package org.gridsphere.portletcontainer.impl.descriptor;

import org.gridsphere.portlet.PortletWindow;
import org.gridsphere.portlet.Mode;
import org.gridsphere.portlet.service.spi.impl.descriptor.ConfigParamList;
import org.gridsphere.portletcontainer.ApplicationPortletConfig;

import java.util.*;

/**
 * The <code>ApplicationSportletConfig</code> is the implementation of
 * <code>ApplicationPortletConfig</code> using Castor for XML to Java
 * bindings.
 */
public class ApplicationSportletConfig implements ApplicationPortletConfig {

    private String id = "";
    private String portletName = "";
    private String servletName = "";

    // configuration hash exported to clients
    private ConfigParamList configParamList = new ConfigParamList();

    // cache info
    private CacheInfo cacheInfo = new CacheInfo();

    // window states used by Castor
    private AllowsWindowStates allowsWindowStates = new AllowsWindowStates();

    // portlet mode list used by Castor
    private Supports supports = new Supports();

    private Map markupModes = new HashMap();

    /**
     * Constructs an instance of ApplicationSportletConfig
     */
    public ApplicationSportletConfig() {
    }

    /**
     * Returns the portlet application id
     *
     * @return the portlet application id
     */
    public String getApplicationPortletID() {
        return id;
    }

    /**
     * Sets the portlet application id
     *
     * @param id the portlet application id
     */
    public void setApplicationPortletID(String id) {
        this.id = id;
    }

    /**
     * Returns the name of a servlet associated with this portlet defined
     * in ui.xml as <servlet-name>
     *
     * @return the servlet name
     */
    public String getServletName() {
        return servletName;
    }

    /**
     * Sets the servlet name associated with this portlet defined in ui.xml
     * as <servlet-name>
     *
     * @param servletName the servlet name
     */
    public void setServletName(String servletName) {
        this.servletName = servletName;
    }

    /**
     * Returns the portlet application name
     *
     * @return the portlet application name
     */
    public String getPortletName() {
        return portletName;
    }

    /**
     * Sets the name of a PortletApplication
     *
     * @param portletName name of a PortletApplication
     */
    public void setPortletName(String portletName) {
        this.portletName = portletName;
    }

    /**
     * Returns the configuration parameter <code>Hashtable</code>
     *
     * @return the configuration parameter <code>Hashtable</code>
     */
    public Hashtable getConfigParams() {
        return configParamList.getConfigParams();
    }

    /**
     * Sets the configuration parameter <code>Hashtable</code>
     *
     * @param configHash the configuration parameter <code>Hashtable</code>
     */
    public void setConfigParams(Hashtable configHash) {
        this.configParamList.setConfigParams(configHash);
    }

    /**
     * Used internally by Castor. Clients should use #getConfigParams
     * Returns the configuration paarameter map
     *
     * @return the <code>List</code> of <code>ConfigParam</code>s
     */
    public List getConfigParamList() {
        return configParamList.getConfigParamList();
    }

    /**
     * Used internally by Castor. Clients should use #setConfigParams
     * Sets the configuration paarameter map
     *
     * @param configList the <code>List</code> of <code>ConfigParam</code>s
     */
    public void setConfigParamList(ArrayList configList) {
        this.configParamList.setConfigParamList(configList);
    }

    /**
     * Used internally by Castor. Not fully implemented yet.
     * Returns the portlet caching information
     *
     * @return the cache info
     */
    public CacheInfo getCacheInfo() {
        return cacheInfo;
    }

    /**
     * Used internally by Castor. Not fully implemented yet.
     * Sets the portlet caching information
     *
     * @param cacheInfo the portlets caching information
     */
    public void setCacheInfo(CacheInfo cacheInfo) {
        this.cacheInfo = cacheInfo;
    }

    /**
     * Returns the allowed window states supported by this portlet
     *
     * @return the <code>List</code> of
     *         <code>PortletWindow.State</code> elements allowed for this portlet
     */
    public List getAllowedWindowStates() {
        List states = allowsWindowStates.getPortletWindowStates();
        if (states.isEmpty()) {
            states.add(PortletWindow.State.CLOSED);
            states.add(PortletWindow.State.MAXIMIZED);
            states.add(PortletWindow.State.MINIMIZED);
            states.add(PortletWindow.State.RESIZING);
        }
        return states;
    }

    /**
     * Internally used by Castor
     * Returns the allowed window states supported by this portlet
     *
     * @return the <code>List</code> of
     *         <code>PortletWindow.State</code> elements allowed for this portlet
     */
    public AllowsWindowStates getAllowsWindowStates() {
        return allowsWindowStates;
    }

    /**
     * Internal for use by Castor.
     * Sets the allowed window states supported by this portlet
     *
     * @param windowStates the <code>List</code> of
     *                     <code>PortletWindow.State</code> elements allowed for this portlet
     */
    public void setAllowsWindowStates(AllowsWindowStates windowStates) {
        this.allowsWindowStates = windowStates;
    }

    /**
     * Returns the supported modes for this portlet
     *
     * @return the supported modes for this portlet
     */
    public List getSupportedModes(String markup) {
        List markups = supports.getMarkups();
        List modes = new ArrayList();
        Iterator it = markups.iterator();
        while (it.hasNext()) {
            Markup m = (Markup) it.next();
            int idx1 = m.getName().indexOf(markup);
            int idx2 = markup.indexOf(m.getName());
            if ((idx1 > 0) || (idx2 > 0) || (markup.equalsIgnoreCase(m.getName()))) {
                modes = m.getPortletModes();
                if (!modes.contains(Mode.VIEW)) modes.add(Mode.VIEW);
                return modes;
            }
        }
        return modes;
    }

    /**
     * Used internally by Castor. Clients should use #getSupportedModes
     * instead.
     *
     * @return the supported modes for this portlet
     */
    public Supports getSupports() {
        return this.supports;
    }

    /**
     * Used internally by Castor. Clients should use #getSupportedModes
     * instead.
     *
     * @param supports the supported modes for this portlet
     */
    public void setSupports(Supports supports) {
        this.supports = supports;
    }

    /**
     * returns the amount of time in seconds that a portlet's content should be cached
     * 
     * @return the amount of time in seconds that a portlet's content should be cached
     */
    public long getCacheExpires() {
        return cacheInfo.getExpires();
    }
}

