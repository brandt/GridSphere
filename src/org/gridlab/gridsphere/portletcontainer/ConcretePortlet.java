/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer;

import org.gridlab.gridsphere.portlet.PortletSettings;
import org.gridlab.gridsphere.portletcontainer.descriptor.ConcretePortletDescriptor;
import org.gridlab.gridsphere.portletcontainer.descriptor.AllowedAccess;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

/**
 * A ConcreteSportlet provides the portlet container with information used to create and manage the
 * portlet's lifecycle. A ConcretePortlet is responsible for parsing the portlet.xml file for
 * portlet settings and portlet configuration information. The ConcretePortlet also maintains an instantiated
 * portlet that is managed by the portlet container.
 */
public interface ConcretePortlet {

    /**
     * Return the concrete portlet application ID
     *
     * @return the concrete portlet application ID
     */
    public String getConcretePortletAppID();

    /**
     * Return the concrete portlet application that defines this concrete portlet in the portlet.xml
     * descriptor.
     *
     * @return the concrete portlet application
     */
    public ConcretePortletDescriptor getConcretePortletDescriptor();

    /**
     * Returns the map of portlet context parameters that are used in the PortletConfig class
     *
     * @return the map of portlet context parameters keys are variable name and values are variable values
     */
    public Hashtable getPortletContextHash();


    /**
     * Returns the map of portlet configuration parameters that are used in the PortletSettings class
     *
     * @return the map of portlet config parameters keys are variable name and values are variable values
     */
    public Hashtable getPortletConfigHash();

    /**
     * Returns the sportlet settings for this concrete portlet
     *
     * @return the sportlet settings
     */
    public PortletSettings getPortletSettings();

    /**
     * Return the name of this portlet
     *
     * @return the portlet name
     */
    public String getPortletName();

    /**
     * Return the classname of this portlet
     *
     * @return the portlet classname
     */
    public String getPortletClass();


    public AllowedAccess getAllowedAccess();

    /**
     * gets the default locale of a portlet
     *
     * @return the default locale of the portlet
     */
    public String getDefaultLocale();

    /**
     * Returns the language info of a portlet
     *
     * @return language info of the portlet
     */
    public List getLanguageList();

    /**
     * Saves the supplied concrete portlet descriptor to serialize any changes that have been made
     *
     * @param concreterDescriptor the concrete portlet descriptor
     * @throws IOException if an I/O error ooccurs
     */
    public void saveDescriptor(ConcretePortletDescriptor concreteDescriptor) throws IOException;
}
