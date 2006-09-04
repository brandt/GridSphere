/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: SportletContext.java 5032 2006-08-17 18:15:06Z novotny $
 */
package org.gridsphere.portlet.impl;

import org.gridsphere.portlet.*;
import org.gridsphere.portlet.service.PortletService;
import org.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridsphere.portletcontainer.PortletMessageManager;
import org.gridsphere.portletcontainer.impl.SportletMessageManager;

import javax.servlet.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * The <code>SportletContext</code> provides an implementation of the
 * <code>PortletContext</code> by following the decorator patterm.
 * A <code>ServletConfig</code> object is used in composition to perform most of
 * the required methods (from which a <code>ServletContext</code> is readily
 * obtained).
 */
public class SportletContext implements PortletContext {

    private ServletContext context = null;

    /**
     * Cannot instantiate uninitialized SportletContext
     */
    private SportletContext() {
    }

    /**
     * Constructs an instance of SportletContext from a <code>ServletConfig</code>
     */
    public SportletContext(ServletConfig config) {
        this.context = config.getServletContext();
    }

    /**
     * Returns the attribute value with the given name, or null  if no such
     * attribute exists. The context attributes can be used to share information
     * between the portlets of one portlet application.
     *
     * @param name the attribute name
     * @return the attribute value
     */
    public Object getAttribute(String name) {
        return context.getAttribute(name);
    }

    /**
     * Returns the name of this web application correponding to this ServletContext as
     * specified in the deployment descriptor for this web application by the display-name element.
     * Uses getServletContextName().
     *
     * @return Returns the context path of the web application.
     */
    public String getContextPath() {
        // todo fix me to confirm to servlet spec 2.5
        return context.getServletContextName();
    }

    /**
     * Returns an enumeration of the attribute names that this portlet context
     * is aware of.
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
     * Allows the portlet to delegate the rendering to another resource as
     * specified by the given . The path has to be relative and will be
     * resolved by this method, so that the portlet's resources are accessed.
     * <p/>
     * To access protected resources the path has to be prefixed with /WEB-INF/
     * (e.g. /WEB-INF/myportlet/myportlet.jsp). Otherwise, the direct path is
     * used. (e.g. /myportlet/myportlet.jsp).
     * <p/>
     * <p/>
     * <b>NONE OF THE DESCRIPTION BELOW IS IMPLEMENTED YET!</b>
     * This method is enabled for multi-language and multi-device support.
     * For example, a jsp file "/myportlet/mytemplate.jsp" will be searched for
     * in the following order, when accessing via HTML-Browser:
     * <p/>
     * 1. /myportlet/html/en_US/mytemplate.jsp
     * 2. /myportlet/html/en/mytemplate.jsp
     * 3. /myportlet/html/mytemplate.jsp
     * 4. /myportlet/mytemplate.jsp
     *
     * @param path     the path of the delegate resource
     * @param request  the portlet request
     * @param response the portlet response
     * @throws PortletException if the delegated resource has trouble fulfilling
     *                          the rendering request
     * @throws IOException      if the streaming causes an I/O problem
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
     * <p/>
     * To access protected resources the path has to be prefixed with /WEB-INF/
     * (e.g. /WEB-INF/myportlet/myportlet.jsp). Otherwise, the direct path is
     * used. (e.g. /myportlet/myportlet.jsp).
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
     * <p/>
     * To access protected resources the path has to be prefixed with /WEB-INF/
     * (e.g. /WEB-INF/myportlet/myportlet.jsp). Otherwise, the direct path is
     * used. (e.g. /myportlet/myportlet.jsp).
     * <p/>
     * This method is enabled for multi-language and multi-device support.
     * For example, a jsp file "/myportlet/mytemplate.jsp" will be searched for
     * in the following order, when accessing via HTML-Browser:
     * <p/>
     * 1. /myportlet/html/en_US/mytemplate.jsp
     * 2. /myportlet/html/en/mytemplate.jsp
     * 3. /myportlet/html/mytemplate.jsp
     * 4. /myportlet/mytemplate.jsp
     *
     * @param path   the path to the resource
     * @param client the client
     * @param locale the locale
     * @return the input stream
     */
    public InputStream getResourceAsStream(String path, Client client, Locale locale) {
        if (path == null) return null;

        int lastComponentIndex = path.lastIndexOf("/");
        String pathPrefix = path.substring(0, lastComponentIndex + 1);
        String lastComponent = path.substring(lastComponentIndex);
        InputStream resourceStream = null;
        StringBuffer pathBuffer = new StringBuffer();

        pathBuffer.append(pathPrefix);

        if (client != null) {
            String markupName = client.getMarkupName();
            pathBuffer.append(markupName);
            int clientIndex = pathBuffer.length();

            if (locale != null) {
                String language = locale.getLanguage();
                String country = locale.getCountry();
                pathBuffer.append("/");
                pathBuffer.append(language);

                int langIndex = pathBuffer.length();
                if (!country.equals("")) {
                    pathBuffer.append("_");
                    pathBuffer.append(country);
                    pathBuffer.append(lastComponent);
                    resourceStream = context.getResourceAsStream(pathBuffer.toString());
                    if (resourceStream != null) return resourceStream;
                }

                pathBuffer.replace(langIndex, pathBuffer.length(), lastComponent);
                resourceStream = context.getResourceAsStream(pathBuffer.toString());
                if (resourceStream != null) return resourceStream;
            }

            pathBuffer.replace(clientIndex, pathBuffer.length(), lastComponent);
            resourceStream = context.getResourceAsStream(pathBuffer.toString());
            if (resourceStream != null) return resourceStream;
        }

        return context.getResourceAsStream(path);
    }

    /**
     * Returns the localized text resource with the given key and using the
     * given locale.
     * <p/>
     * To use this feature, the portlet application's CLASSPATH has to contain
     * a resource bundle with the same name (including the package) as the portlet.
     *
     * @param bundle the name of the resource bundle
     * @param key    the text key
     * @param locale the locale
     * @return the localized text resource
     */
    public String getText(String bundle, String key, Locale locale) {
        ResourceBundle resBundle = ResourceBundle.getBundle(bundle, locale);
        return resBundle.getString(key);
    }

    /**
     * Sends the given message to the concrete portlet specified.
     * The portlet(s) with the given name will only receive the message event
     * if it has implemented the appropriate listener.
     *
     * @param concretePortletID the concrete portlet to send the message to
     * @param message           the message to be sent
     */
    public void send(String concretePortletID, PortletMessage message) {
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
     * @throws PortletServiceUnavailableException
     *          if an exception has occurrred
     *          that interferes with the portlet service's normal initialization
     * @throws PortletServiceNotFoundException
     *          if the PortletService is not found
     */
    public PortletService getService(Class service)
            throws PortletServiceUnavailableException, PortletServiceNotFoundException {
        return PortletServiceFactory.createPortletService(service, true);
    }

    /**
     * Returns the major version of the API that this portlet
     * container supports.
     *
     * @return the major version
     */
    public String getVersionInfo() {
        return SportletProperties.getInstance().getProperty("gridsphere.release");
    }

    public int getMajorVersion() {
        return Integer.valueOf(SportletProperties.getInstance().getProperty("gridsphere.portletapi.majorversion")).intValue();
    }

    public int getMinorVersion() {
        return Integer.valueOf(SportletProperties.getInstance().getProperty("gridsphere.portletapi.minorversion")).intValue();
    }

    /**
     * Returns the release information for this portlet container
     *
     * @return the minor version
     */
    public String getReleaseInfo() {
        return getContainerInfo();
    }

    /**
     * Returns the name and version of the portlet container which the portlet
     * is running in. The form of the returned string is
     * servername/versionnumber-versionrelease.
     *
     * @return the string containing at least name and version number
     */
    public String getContainerInfo() {
        return SportletProperties.getInstance().getProperty("gridsphere.release");
    }

    /**
     * Returns the portlet log which allows the portlet to write
     * debug, informational, warning, or error messages to a log.
     *
     * @return the portlet log
     */
    public PortletLog getLog() {
        return SportletLog.getInstance(SportletContext.class);
    }

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

    public final String getServletContextName() {
        return context.getServletContextName();
    }

}
