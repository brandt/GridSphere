/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletSettings;
import org.gridlab.gridsphere.portletcontainer.descriptor.Owner;

import java.util.List;

/**
 * A RegisteredSportlet provides the portlet container with information used to create and manage the
 * portlet's lifecycle. A RegisteredPortlet is responsible for parsing the portlet.xml file for
 * portlet settings and portlet configuration information. The RegisteredPortlet also maintains an instantiated
 * portlet that is managed by the portlet container.
 */
public interface RegisteredPortlet {

    public String getPortletAppID();

    public String getConcretePortletAppID();

    /**
     * Returns the portlet config for this portlet
     *
     * @return the portlet config
     */
    public PortletConfig getPortletConfig();

    /**
     * Returns the portlet settings for this concrete portlet
     *
     * @return the portlet settings
     */
    public PortletSettings getPortletSettings(boolean enableConfig);

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
    public AbstractPortlet getActivePortlet();

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
}
