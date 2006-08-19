/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: PortletManagerService.java 5032 2006-08-17 18:15:06Z novotny $
 */
package org.gridsphere.services.core.registry;

import org.gridsphere.portlet.PortletException;
import org.gridsphere.portlet.PortletRequest;
import org.gridsphere.portlet.PortletResponse;
import org.gridsphere.portlet.service.PortletService;
import org.gridsphere.portletcontainer.PortletWebApplication;

import java.io.IOException;
import java.util.List;

/**
 * The <code>PortletManagerService</code> is responsible for the initialization, installation, removal and overall
 * management of portlet web applications.
 */
public interface PortletManagerService extends PortletService {

    /**
     * Initializes all known portlet web applications in order
     *
     * @param req                the portlet request
     * @param res                the portlet response
     * @throws IOException      if an I/O error occurs
     * @throws PortletException if a portlet exception occurs
     */
    public void initAllPortletWebApplications(PortletRequest req, PortletResponse res) throws IOException, PortletException;

    /**
     * Initializes a portlet web application
     *
     * @param webApplicationName the name of the portlet web application
     * @param req                the <code>PortletRequest</code>
     * @param res                the <code>Portletresponse</code>
     * @throws IOException      if an I/O error occurs
     * @throws PortletException if a portlet/servlet error occurs
     */
    public void initPortletWebApplication(String webApplicationName, PortletRequest req, PortletResponse res) throws IOException, PortletException;

    /**
     * Shuts down a currently active portlet web application from the portlet container
     *
     * @param webApplicationName the name of the portlet web application
     * @param req                the <code>PortletRequest</code>
     * @param res                the <code>Portletresponse</code>
     * @throws IOException      if an I/O error occurs
     * @throws PortletException if a portlet/servlet error occurs
     */
    public void destroyPortletWebApplication(String webApplicationName, PortletRequest req, PortletResponse res) throws IOException, PortletException;

    /**
     * Lists all the portlet web applications known to the portlet container
     *
     * @return the list of web application names as <code>String</code> elements
     */
    public List getPortletWebApplicationNames();

    /**
     * Returns the portlet web application description for the supplied web application name
     *
     * @return the portlet web application description
     */
    public String getPortletWebApplicationDescription(String webApplicationName);

    public void addPortletWebApplication(PortletWebApplication portletWebApp);
}
