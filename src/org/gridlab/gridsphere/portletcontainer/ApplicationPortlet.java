/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer;

import org.gridlab.gridsphere.portletcontainer.descriptor.ApplicationPortletDescriptor;

import java.io.IOException;
import java.util.List;

/**
 * An application portlet represents the portlet application defined in the portlet.xml
 * ApplicationPortlet is mostly a proxy for the ApplicationPortletDescriptor class used by Castor
 *
 * @see <code>org.gridlab.gridsphere.portletcontainer.descriptor.ApplicationPortletDescriptor</code>
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
    public String getWebApplicationName();

    /**
     * Return the PortletApplication, the portlet descriptor class that defines the portlet application
     *
     * @return the PortletApplication
     */
    public ApplicationPortletDescriptor getApplicationPortletDescriptor();

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

    /**
     * Returns a PortletWrapper for this ApplicationPortlet
     *
     * @return PortletWrapper the proxy portlet for this ApplicationPortlet
     */
    public PortletWrapper getPortletWrapper();

    /**
     * Saves the supplied application portlet descriptor to serialize any changes that have been made
     *
     * @param appDescriptor the application portlet descriptor
     * @throws IOException if an I/O error ooccurs
     */
    public void saveDescriptor(ApplicationPortletDescriptor applicationDescriptor) throws IOException;

}
