/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.impl;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portletcontainer.PortletMessageManager;
import org.gridlab.gridsphere.portletcontainer.impl.SportletMessageManager;


import javax.servlet.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * The PortletContext interface defines a portlet's view of the portlet container
 * within which each portlet is running. The PortletContext also allows a portlet
 * to access resources available to it. Using the context, a portlet can access
 * the portlet log, and obtain URL references to resources.
 */
public class SportletContext implements PortletContext {

    public final static String PORTLET_CONTAINER = "GridSphere Portal Server";
    public final static int MAJOR_VERSION = 0;
    public final static int MINOR_VERSION = 9;

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(SportletContext.class);
    private static SportletServiceFactory factory = SportletServiceFactory.getInstance();

    private ServletConfig config = null;
    private ServletContext context = null;

    public SportletContext(ServletConfig config) {
        this.config = config;
        this.context = config.getServletContext();
    }

    /**
     * Returns the attribute value with the given name, or null  if no such attribute exists.
     * The context attributes can be used to share information between the portlets of one portlet application.
     *
     * @param name the attribute name
     * @return the attribute value
     */
    public Object getAttribute(String name) {
        return context.getAttribute(name);
    }

    /**
     * Returns an enumeration of the attribute names that this portlet context is aware of.
     *
     * @return an enumeration of attribute names
     */
    public Enumeration getAttributeNames() {
        return context.getAttributeNames();
    }

    /**
     * Removes the attribute with the given name.
     *
     * @param name the name of attribute to be removed
     */
    public void removeAttribute(String name) {
        context.removeAttribute(name);
    }

    /**
     * Allows the portlet to delegate the rendering to another resource as specified by the given path.
     * The path has to be relative and will be resolved by this method, so that the portlet's resources are accessed.
     *
     * To access protected resources the path has to be prefixed with /WEB-INF/
     * (e.g. /WEB-INF/myportlet/myportlet.jsp). Otherwise, the direct path is used. (e.g. /myportlet/myportlet.jsp).

     * This method is enabled for multi-language and multi-device support.
     * For example, a jsp file "/myportlet/mytemplate.jsp" will be searched for in the following order,
     * when accessing via HTML-Browser:
     *
     * 1. /myportlet/html/en_US/mytemplate.jsp
     * 2. /myportlet/html/en/mytemplate.jsp
     * 3. /myportlet/html/mytemplate.jsp
     * 4. /myportlet/mytemplate.jsp
     *
     * @param path the path of the delegate resource
     * @param request the portlet request
     * @param response the portlet response
     *
     * @throws PortletException if the delegated resource has trouble fulfilling the rendering request
     * @throws IOException if the streaming causes an I/O problem
     */
    public void include(String path, PortletRequest request, PortletResponse response)
            throws PortletException, IOException {
        if (path == null) throw new PortletException("The provided resource path is null");
        RequestDispatcher rd = null;
        rd = context.getRequestDispatcher(path);
        try {
            if (rd != null) {
                rd.include(request, response);
            } else {
                throw new PortletException("Unable to include resource: RequestDispatcher is null");
            }
        } catch (Exception e) {
            throw new PortletException("Unable to include resource ", e);
        }
    }

    /**
     * Returns the resource located at the given path as an InputStream object.
     * The data in the InputStream can be of any type or length.
     * The method returns null if no resource exists at the given path.
     *
     * To access protected resources the path has to be prefixed with /WEB-INF/
     * (e.g. /WEB-INF/myportlet/myportlet.jsp). Otherwise, the direct path is used. (e.g. /myportlet/myportlet.jsp).
     *
     * @param path the path to the resource
     * @return the input stream
     */
    public InputStream getResourceAsStream(String path) {
        return context.getResourceAsStream(path);
    }

    /**
     * Returns the resource located at the given path as an InputStream object.
     * The data in the InputStream can be of any type or length.
     * The method returns null if no resource exists at the given path.
     *
     * To access protected resources the path has to be prefixed with /WEB-INF/
     * (e.g. /WEB-INF/myportlet/myportlet.jsp). Otherwise, the direct path is used. (e.g. /myportlet/myportlet.jsp).
     *
     * This method is enabled for multi-language and multi-device support.
     * For example, a jsp file "/myportlet/mytemplate.jsp" will be searched for in the following order,
     * when accessing via HTML-Browser:
     *
     * 1. /myportlet/html/en_US/mytemplate.jsp
     * 2. /myportlet/html/en/mytemplate.jsp
     * 3. /myportlet/html/mytemplate.jsp
     * 4. /myportlet/mytemplate.jsp
     *
     * @param path the path to the resource
     * @param client the client
     * @param locale the locale
     * @return the input stream
     */
    public InputStream getResourceAsStream(String path, Client client, Locale locale) {
        return context.getResourceAsStream(path);
    }

    /**
     * Returns the localized text resource with the given key and using the given locale.
     *
     * To use this feature, the portlet application's CLASSPATH has to contain a resource bundle
     * with the same name (including the package) as the portlet.
     *
     * @param bundle the name of the resource bundle
     * @param key the text key
     * @param locale the locale
     * @return the localized text resource
     */
    public String getText(String bundle, String key, Locale locale) {
        ResourceBundle resBundle = ResourceBundle.getBundle(bundle, locale);
        return resBundle.getString(key);
    }

    /**
     * Sends the given message to all portlets on the same page that have the given name regardless of the portlet application.
     * If the portlet name is null the message is broadcast to all portlets in the same portlet application.
     * If more than one instance of the portlet with the given name exists on the current page, the message is sent
     * to every single instance of that portlet. If the source portlet has the same name as the target portlet(s),
     * the message will not be sent to avoid possible cyclic calls.
     *
     * The portlet(s) with the given name will only receive the message event if it has/they have implemented
     * the appropriate listener.
     *
     * This function may only be used during event processing, in any other case the method throws an AccessDeniedException.
     *
     * @param concretePortletID the concrete portlet to send the message to
     * @param message the message to be sent
     *
     * @throws AccessDeniedException if the portlet tries to access this function outside of the event processing
     */
    public void send(String concretePortletID, PortletMessage message) throws AccessDeniedException {
        PortletMessageManager messageManager = null;
        messageManager = SportletMessageManager.getInstance();
        messageManager.send(concretePortletID, message);
    }

    /**
     * This function looks up a portlet service with the given classname.
     * Using this method a portlet is able to get additional functionality like a
     * service to get external content over a firewall or to include a servlet.
     *
     * @param service the classname of the service to load
     * @return the portlet service
     *
     * @throws PortletServiceUnavailableException
     *      if an exception has occurrred that interferes with the portlet service's normal initialization
     * @throws PortletServiceNotFoundException if the PortletService is not found
     */
    public PortletService getService(Class service)
            throws PortletServiceUnavailableException, PortletServiceNotFoundException {
        return (PortletService) factory.createPortletService(service, config, true);
    }

    /**
     * This function looks up a portlet service with the given classname.
     * The returned service is a service wrapper that contains the User object
     * which can be used for method level access control. An implementation of the
     * service is provided with the appropiae access control restrictions on the supplied
     *
     * @param service the classname of the service to load
     * @param User the user requesting a service instance
     * @return the portlet service
     *
     * @throws PortletServiceUnavailableException
     *      if an exception has occurrred that interferes with the portlet service's normal initialization
     * @throws PortletServiceNotFoundException if the PortletService is not found
     */
    public PortletService getService(Class service, User user)
            throws PortletServiceUnavailableException, PortletServiceNotFoundException {
        return (PortletService) factory.createPortletUserService(service, user, config, true);
    }

    /**
     * Returns the major version of the PortletInfo API that this portlet container supports.
     *
     * @return the major version
     */
    public int getMajorVersion() {
        return MAJOR_VERSION;
    }

    /**
     * Returns the minor version of the PortletInfo API that this portlet container supports.
     *
     * @return the minor version
     */
    public int getMinorVersion() {
        return MINOR_VERSION;
    }

    /**
     * Returns the name and version of the portlet container which the portlet is running in.
     * The form of the returned string is servername/versionnumber.
     * For the GridSphere Portal Server this method may return the string GridSphere Portal Server/0.9.
     *
     * @return the string containing at least name and version number
     */
    public String getContainerInfo() {
        return PORTLET_CONTAINER + "/" + MAJOR_VERSION + "." + MINOR_VERSION;
    }

    /**
     * Returns the portlet log which allows the portlet to write
     * debug, informational, warning, or error messages to a log.
     *
     * @return the portlet log
     */
    public PortletLog getLog() {
        return log;
    }

    // ServletContext methods
    public final ServletContext getContext(String uripath) {
        return context.getContext(uripath);
    }

    public final String getMimeType(String file) {
        return context.getMimeType(file);
    }

    public final URL getResource(String path) throws MalformedURLException {
        return context.getResource(path);
    }

    public final Set getResourcePaths(String path) {
        return null;
    }

    public final RequestDispatcher getRequestDispatcher(String path) {
        return context.getRequestDispatcher(path);
    }

    public final RequestDispatcher getNamedDispatcher(String name) {
        return context.getNamedDispatcher(name);
    }

    public final Servlet getServlet(String name) throws ServletException {
        return context.getServlet(name);
    }

    public final Enumeration getServlets() {
        return context.getServlets();
    }

    public final Enumeration getServletNames() {
        return context.getServletNames();
    }

    public final void log(String msg) {
        context.log(msg);
    }

    public final void log(Exception exception, String msg) {
        context.log(exception, msg);
    }

    public final void log(String message, Throwable throwable) {
        context.log(message, throwable);
    }

    public final String getRealPath(String path) {
        return context.getRealPath(path);
    }

    public final String getServerInfo() {
        return context.getServerInfo();
    }

    public final String getInitParameter(String name) {
        return context.getInitParameter(name);
    }

    public final Enumeration getInitParameterNames() {
        return context.getInitParameterNames();
    }

    public final void setAttribute(String name, Object object) {
        context.setAttribute(name, object);
    }

    /**
     * Returns the <display-name> of the web application
     */
    public final String getServletContextName() {
        return context.getServletContextName();
    }

}
