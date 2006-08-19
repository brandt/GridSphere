/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: ApplicationPortletConfig.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.portletcontainer;

import java.util.List;

/**
 * The <code>ApplicationPortletConfig</code> represents the application portlet
 * configuration information.
 */
public interface ApplicationPortletConfig {

    /**
     * Returns the portlet application id
     *
     * @return the portlet application id
     */
    public String getApplicationPortletID();

    /**
     * Returns the portlet application name
     *
     * @return name portlet application name
     */
    public String getPortletName();

    /**
     * Sets the portlet application name
     *
     * @param portletName the portlet application name
     */
    public void setPortletName(String portletName);

    /**
     * Returns the allowed window states supported by this portlet
     *
     * @return the <code>List</code> of
     *         <code>PortletWindow.State</code> elements allowed for this portlet
     */
    public List getAllowedWindowStates();

    /**
     * Returns the supported modes for this portlet using the supplied markup
     *
     * @return the supported modes for this portlet
     */
    public List getSupportedModes(String markup);

    /**
     * returns the amount of time in seconds that a portlet's content should be cached
     *
     * @return the amount of time in seconds that a portlet's content should be cached
     */
    public long getCacheExpires();
}
