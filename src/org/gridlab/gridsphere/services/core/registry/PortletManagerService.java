/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.registry;

import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfigProperties;
import org.gridlab.gridsphere.portletcontainer.GridSphereProperties;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfig;

import java.util.List;
import java.io.IOException;

/**
 * The <code>PortletManagerService</code> is responsible for the initialization, installation, removal and overall
 * management of portlet web applications.
 */
public interface PortletManagerService extends PortletService {

    /**
     * The location of deployed portlet web applications
     */
    public static final String WEB_APPLICATION_PATH =
            GridSphereConfig.getProperty(GridSphereConfigProperties.CATALINA_HOME) + "/webapps/";

    /**
     * Initializes all the portlet web applications
     *
     * @param req the <code>PortletRequest</code>
     * @param res the <code>Portletresponse</code>
     * @throws IOException if an I/O error occurs
     * @throws PortletException if a portlet/servlet error occurs
     */
    public void initAllPortletWebApplications(PortletRequest req, PortletResponse res) throws IOException, PortletException;

    /**
     * Initializes a portlet web application
     *
     * @param webApplicationName the name of the portlet web application
     * @param req the <code>PortletRequest</code>
     * @param res the <code>Portletresponse</code>
     * @throws IOException if an I/O error occurs
     * @throws PortletException if a portlet/servlet error occurs
     */
    public void initPortletWebApplication(String webApplicationName, PortletRequest req, PortletResponse res) throws IOException, PortletException;

    /**
     * Shuts down a currently active portlet web application from the portlet container
     *
     * @param webApplicationName the name of the portlet web application
     * @param req the <code>PortletRequest</code>
     * @param res the <code>Portletresponse</code>
     * @throws IOException if an I/O error occurs
     * @throws PortletException if a portlet/servlet error occurs
     */
    public void destroyPortletWebApplication(String webApplicationName, PortletRequest req, PortletResponse res) throws IOException, PortletException;

    /**
     * Shuts down all currently active portlet web applications from the portlet container
     *
     * @param webApplicationName the name of the portlet web application
     * @param req the <code>PortletRequest</code>
     * @param res the <code>Portletresponse</code>
     * @throws IOException if an I/O error occurs
     * @throws PortletException if a portlet/servlet error occurs
     */
    public void destroyAllPortletWebApplications(PortletRequest req, PortletResponse res) throws IOException, PortletException;

    /**
     * Removes a portlet web application from the portlet container. If the web application is active, then the portlets
     * are shutdown
     *
     * @param webApplicationName the name of the portlet web application
     * @param req the <code>PortletRequest</code>
     * @param res the <code>Portletresponse</code>
     * @throws IOException if an I/O error occurs
     * @throws PortletException if a portlet/servlet error occurs
     */
    public void removePortletWebApplication(String webApplicationName, PortletRequest req, PortletResponse res) throws IOException, PortletException;

    /**
     * Installs and initializes a portlet web application to the portlet container
     *
     * @param webApplicationName the name of the portlet web application
     * @param req the <code>PortletRequest</code>
     * @param res the <code>Portletresponse</code>
     * @throws IOException if an I/O error occurs
     * @throws PortletException if a portlet/servlet error occurs
     */
    public void installPortletWebApplication(String webApplicationName, PortletRequest req, PortletResponse res) throws IOException, PortletException;

    /**
     * Lists all the portlet web applications known to the portlet container
     *
     * @return the list of web application names as <code>String</code> elements
     */
    public List getPortletWebApplications();

}
