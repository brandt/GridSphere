/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.portletcontainer.descriptor;

import java.util.ArrayList;
import java.util.List;

public class PortletApp {

    private String id = new String();
    private String portletName = new String();
    private String servletName = new String();
    private List portletConfig = new ArrayList();
    private CacheInfo cacheInfo = new CacheInfo();
    private AllowsWindowStates allowsWindowStates = new AllowsWindowStates();
    private SupportsModes supportsModes = new SupportsModes();


    public PortletApp() {}

    /**
     * Returns the id of a PortletApplication
     *
     * @returns id of the PortletApplication
     */
    public String getID() {
        return id;
    }

    /**
     * sets the id of a PortletApplication
     *
     * @param id the uid of the PortletApplication
     */
    public void setID(String id) {
        this.id = id;
    }

    /**
     * Returns the name of a servlet associated with this portlet defined in web.xml as <servlet-name>
     *
     * @returns the servlet name
     */
    public String getServletName() {
        return servletName;
    }

    /**
     * sets the servlet name associated with this portlet defined in web.xml as <servlet-name>
     *
     * @param name the servlet name
     */
    public void setServletName(String servletName) {
        this.servletName = servletName;
    }

    /**
     * gets the name of a PortletApplication
     *
     * @returns name of the PortletApplication
     */
    public String getPortletName() {
        return portletName;
    }

    /**
     * sets the name of a PortletApplication
     *
     * @param name name of a PortletApplication
     */
    public void setPortletName(String portletName) {
        this.portletName = portletName;
    }

    public List getConfigParamList() {
        return portletConfig;
    }

    public void setConfigParamList(ArrayList portletConfig) {
        this.portletConfig = portletConfig;
    }

    public CacheInfo getCacheInfo() {
        return cacheInfo;
    }

    public void setCacheInfo(CacheInfo cacheInfo) {
        this.cacheInfo = cacheInfo;
    }

    public void setAllowsWindowStates(AllowsWindowStates allowsWindowStates) {
        this.allowsWindowStates = allowsWindowStates;
    }

    public AllowsWindowStates getAllowsWindowStates() {
        return allowsWindowStates;
    }

    public void setSupportsModes(SupportsModes supportModes) {
        this.supportsModes = supportModes;
    }

    public SupportsModes getSupportsModes() {
        return supportsModes;
    }
}

