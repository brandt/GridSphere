/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer;

import org.gridlab.gridsphere.portletcontainer.impl.descriptor.ApplicationSportletConfig;

import java.io.IOException;
import java.util.List;

/**
 * An <code>ApplicationPortlet</code> represents the portlet application instance
 * defined in the portlet descriptor file.

 * @see <code>org.gridlab.gridsphere.portletcontainer.impl.descriptor.ApplicationSportletConfig</code>
 */
public interface ApplicationPortlet {

    /**
     * Returns the concrete portlets associated with this application portlet
     *
     * @return the <code>ConcretePortlet</code>s
     */
    public List getConcretePortlets();

    /**
     * Returns the <code>ConcretePortlet</code> associated with this application
     * portlet
     *
     * @param concretePortletID the concrete portlet ID associated with this
     * <code>ApplicationPortlet</code>
     * @return the <code>ConcretePortlet</code> associated with this
     * application portlet
     */
    public ConcretePortlet getConcretePortlet(String concretePortletID);

    /**
     * Returns the web application name associated with this application portlet
     *
     * @return the web application name
     */
    public String getWebApplicationName();

    /**
     * Returns the id of a PortletApplication
     *
     * @return the id of the PortletApplication
     */
    public String getApplicationPortletID();

    /**
     * Returns the name of a PortletApplication
     *
     * @return name of the PortletApplication
     */
    public String getApplicationPortletName();

    /**
     * Returns the name of a servlet associated with this portlet defined in web.xml as <servlet-name>
     *
     * @return the servlet name
     */
    public String getServletName();

    /**
     * Returns a PortletDispatcher for this ApplicationPortlet
     *
     * @return PortletDispatcher the proxy portlet for this ApplicationPortlet
     */
    public PortletDispatcher getPortletDispatcher();

    /**
     * Return the PortletApplication, the portlet descriptor class that defines the portlet application
     *
     * @return the PortletApplication
     */
    public ApplicationPortletConfig getApplicationPortletConfig();

}
