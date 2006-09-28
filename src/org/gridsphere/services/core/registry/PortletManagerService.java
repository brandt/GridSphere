/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: PortletManagerService.java 5032 2006-08-17 18:15:06Z novotny $
 */
package org.gridsphere.services.core.registry;

import org.gridsphere.portlet.PortletException;
import org.gridsphere.portlet.service.PortletService;
import org.gridsphere.portletcontainer.PortletWebApplication;
import org.gridsphere.portletcontainer.PortletDispatcherException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * The <code>PortletManagerService</code> is responsible for the initialization, installation, removal and overall
 * management of portlet web applications.
 */
public interface PortletManagerService extends PortletService {

    /**
     * Initializes all known portlet web applications in order
     *
     * @param req                the <code>HttpServletRequest</code>
     * @param res                the <code>HttpServletResponse</code>
     * @throws PortletDispatcherException      if a dispatching error occurs
     * @throws PortletException if an exception occurs initializing the portlet web application
     */
    public void initAllPortletWebApplications(HttpServletRequest req, HttpServletResponse res) throws PortletDispatcherException, PortletException;

    /**
     * Initializes a portlet web application
     *
     * @param webApplicationName the name of the portlet web application
     * @param req                the <code>HttpServletRequest</code>
     * @param res                the <code>HttpServletresponse</code>
     * @throws PortletDispatcherException      if a dispatching error occurs
     * @throws PortletException if an exception occurs initializing the portlet web application
     */
    public void initPortletWebApplication(String webApplicationName, HttpServletRequest req, HttpServletResponse res) throws PortletDispatcherException, PortletException;

    /**
     * Shuts down a currently active portlet web application from the portlet container
     *
     * @param webApplicationName the name of the portlet web application
     * @param req                the <code>HttpServletRequest</code>
     * @param res                the <code>HttpServletResponse</code>
     * @throws PortletDispatcherException      if a dispatching error occurs
     */
    public void destroyPortletWebApplication(String webApplicationName, HttpServletRequest req, HttpServletResponse res) throws PortletDispatcherException;

    public void logoutPortletWebApplication(String webApplicationName, HttpServletRequest req, HttpServletResponse res) throws PortletDispatcherException;

    public void logoutAllPortletWebApplications(HttpServletRequest req, HttpServletResponse res) throws PortletDispatcherException;

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
