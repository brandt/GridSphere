/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer;

import org.gridlab.gridsphere.portlet.AbstractPortlet;
import org.gridlab.gridsphere.portlet.PortletConfig;
import org.gridlab.gridsphere.portlet.PortletSettings;

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
     * Returns the portlet settings for this concrete portlet
     *
     * @return the portlet settings
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

    /**
     * Return the instantiated abstract portlet instance
     *
     * @return the instantiated abstract portlet instance
     */
    public AbstractPortlet getActivePortlet();

}
