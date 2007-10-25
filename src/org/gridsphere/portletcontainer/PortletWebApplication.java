/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
package org.gridsphere.portletcontainer;

import java.util.Collection;

/**
 * A <code>PortletWebApplication</code> represents a collection of portlets contained in a packaged WAR file. Currently
 * under development is the notion of dynamically managing portlet web applications.
 */
public interface PortletWebApplication {

    /**
     * Under development. A portlet web application can unregister itself from the application server
     */
    public void destroy();

    /**
     * Returns the portlet web application name
     *
     * @return the portlet web application name
     */
    public String getWebApplicationName();

    /**
     * Returns the portlet web application description
     *
     * @return the portlet web application description
     */
    public String getWebApplicationDescription();

    /**
     * Returns the collection of application portlets contained by this portlet web application
     *
     * @return the collection of application portlets
     */
    public Collection<ApplicationPortlet> getAllApplicationPortlets();

    public PortletStatus getWebApplicationStatus();

    public String getWebApplicationStatusMessage();

}
