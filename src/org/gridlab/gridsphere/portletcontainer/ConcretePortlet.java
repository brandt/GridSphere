/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletSettings;
import org.gridlab.gridsphere.portletcontainer.descriptor.Owner;
import org.gridlab.gridsphere.portletcontainer.descriptor.ConcretePortletApplication;
import org.gridlab.gridsphere.portletcontainer.impl.Cacheable;

import java.util.List;
import java.util.Map;

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
    public ConcretePortletApplication getConcretePortletApplication();

    /**
     * Returns the map of portlet context parameters that are used in the PortletConfig class
     *
     * @return the map of portlet context parameters keys are variable name and values are variable values
     */
    public Map getPortletContext();


    /**
     * Returns the map of portlet configuration parameters that are used in the PortletSettings class
     *
     * @return the map of portlet config parameters keys are variable name and values are variable values
     */
    public Map getPortletConfig();

    /**
     * Returns the sportlet settings for this concrete portlet
     *
     * @return the sportlet settings
     */
    public SportletSettings getSportletSettings();

    /**
     * Returns the portlet application settings for this concrete portlet
     *
     * @return the portlet application settings
     */
    //public PortletApplicationSettings getPortletApplicationSettings(boolean enableConfig);

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

    /**
     * Return the instantiated abstract portlet instance
     *
     * @return the instantiated abstract portlet instance
     */
    public AbstractPortlet getAbstractPortlet();

    /**
     * Return the Owner of the concrete portlet that can reconfigure the settings
     *
     * @return the concrete portlet owner
     */
    public Owner getPortletOwner();

    /**
     * Returns the list of supported groups
     * NOTE: THIS IS NOT PART OF THE WPS PORTLET API 4.1
     *
     * @return the list of supported PortletGroup objects
     */
    public List getSupportedGroups();

    /**
     * Returns the list of supported roles
     * NOTE: THIS IS NOT PART OF THE WPS PORTLET API 4.1
     *
     * @return the list of supported PortletRole objects
     */
    public List getSupportedRoles();

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

}
