/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer;

import org.gridlab.gridsphere.portletcontainer.descriptor.PortletApp;

import java.util.List;

/**
 * An application portlet represents the portlet application defined in the portlet.xml
 * ApplicationPortlet is mostly a proxy for the PortletApp class used by Castor
 *
 * @see <code>org.gridlab.gridsphere.portletcontainer.descriptor.PortletApp</code>
 */
public interface ApplicationPortlet {

    /**
     * Return the ConcretePortlets associated with this ApplicationPortlet
     *
     * @return the ConcretePortlets associated with this ApplicationPortlet
     */
    public List getConcretePortlets();

    /**
     * Return the ConcretePortlet associated with this ApplicationPortlet
     *
     * @param concretePortletID the concrete portlet ID associated with this ApplicationPortlet
     * @return the ConcretePortlet associated with this ApplicationPortlet with the provided concretePortletID
     */
    public ConcretePortlet getConcretePortlet(String concretePortletID);

    /**
     * Return the web application name associated with this application portlet
     *
     * @return the web application name
     */
    public String getWebApplication();

    /**
     * Return the PortletApplication, the portlet descriptor class that defines the portlet application
     *
     * @return the PortletApplication
     */
    public PortletApp getPortletApplicationDescriptor();

    /**
     * Returns the id of a PortletApplication
     *
     * @returns the id of the PortletApplication
     */
    public String getPortletAppID();

    /**
     * Returns the name of a PortletApplication
     *
     * @returns name of the PortletApplication
     */
    public String getPortletName();

    /**
     * Returns the name of a servlet associated with this portlet defined in web.xml as <servlet-name>
     *
     * @returns the servlet name
     */
    public String getServletName();

    public PortletWrapper getPortletWrapper();

    /**
     * Returns the map of portlet configuration parameters that are used in the PortletConfig class
     *
     * @return the map of portlet config parameters keys are variable name and values are variable values
     */
    //public Map PortletConfig();

    /**
     * Returns the list of allowed portlet window states e.g. MINIMIZED, MAXIMIZED, RESIZING
     *
     * @return modes the list of allowed portlet window states
     * @see <code>PortletWindow.State</code>
     */
    //public List getAllowedPortletWindowStates();

    /**
     * Return the cacheable portlet info consisting of:
     * expires: -1 = never expires 0 = always expires # = number of seconds until expiration
     * shared: true if portlet output shared among all users or false if not
     */
    //public Cacheable getCacheablePortletInfo();

    /**
     * Returns the list of supported portlet modes e.g. EDIT, VIEW, HELP, CONFIGURE
     *
     * @return modes the list of allowed portlet modes
     * @see <code>Portlet.Mode</code>
     */
    //public List getSupportedPortletModes();
}
