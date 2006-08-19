/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: PortletContext.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.portlet;

import org.gridsphere.portlet.service.PortletService;
import org.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridsphere.portlet.service.PortletServiceUnavailableException;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Locale;

/**
 * The <code>PortletContext</code> interface defines a portlet's view of the
 * portlet container within which each portlet is running.
 * The <code>PortletContext</code> also allows a portlet to access resources
 * available to it. Using the context, a portlet can access the portlet log,
 * obtain portlet service instances and obtain URL references to resources.
 */
public interface PortletContext extends ServletContext {

    /**
     * Returns the attribute value with the given name, or null  if no such attribute exists.
     * The context attributes can be used to share information between the portlets of one portlet application.
     *
     * @param name the attribute name
     * @return the attribute value
     */
    public Object getAttribute(String name);

    /**
     * Returns an enumeration of the attribute names that this portlet context is aware of.
     *
     * @return an enumeration of attribute names
     */
    public Enumeration getAttributeNames();

    /**
     * Removes the attribute with the given name.
     *
     * @param name the name of attribute to be removed
     */
    public void removeAttribute(String name);

    /**
     * Allows the portlet to delegate the rendering to another resource as specified by the given path.
     * The path has to be relative and will be resolved by this method, so that the portlet's resources are accessed.
     * <p/>
     * To access protected resources the path has to be prefixed with /WEB-INF/
     * (e.g. /WEB-INF/myportlet/myportlet.jsp). Otherwise, the direct path is used. (e.g. /myportlet/myportlet.jsp).
     * <p/>
     * This method is enabled for multi-language and multi-device support.
     * For example, a jsp file "/myportlet/mytemplate.jsp" will be searched for in the following order,
     * when accessing via HTML-Browser:
     * <p/>
     * 1. /myportlet/html/en_US/mytemplate.jsp
     * 2. /myportlet/html/en/mytemplate.jsp
     * 3. /myportlet/html/mytemplate.jsp
     * 4. /myportlet/mytemplate.jsp
     *
     * @param path     the path of the delegate resource
     * @param request  the portlet request
     * @param response the portlet response
     * @throws PortletException if the delegated resource has trouble fulfilling the rendering request
     * @throws IOException      if the streaming causes an I/O problem
     */
    public void include(String path, PortletRequest request, PortletResponse response)
            throws PortletException, IOException;

    /**
     * Returns the resource located at the given path as an InputStream object.
     * The data in the InputStream can be of any type or length.
     * The method returns null if no resource exists at the given path.
     * <p/>
     * To access protected resources the path has to be prefixed with /WEB-INF/
     * (e.g. /WEB-INF/myportlet/myportlet.jsp). Otherwise, the direct path is used. (e.g. /myportlet/myportlet.jsp).
     *
     * @param path the path to the resource
     * @return the input stream
     */
    public InputStream getResourceAsStream(String path);

    /**
     * Returns the resource located at the given path as an InputStream object.
     * The data in the InputStream can be of any type or length.
     * The method returns null if no resource exists at the given path.
     * <p/>
     * To access protected resources the path has to be prefixed with /WEB-INF/
     * (e.g. /WEB-INF/myportlet/myportlet.jsp). Otherwise, the direct path is used. (e.g. /myportlet/myportlet.jsp).
     * <p/>
     * This method is enabled for multi-language and multi-device support.
     * For example, a jsp file "/myportlet/mytemplate.jsp" will be searched for in the following order,
     * when accessing via HTML-Browser:
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
    public InputStream getResourceAsStream(String path, Client client, Locale locale);

    /**
     * Returns the localized text resource with the given key and using the given locale.
     * <p/>
     * To use this feature, the portlet application's CLASSPATH has to contain a resource bundle
     * with the same name (including the package) as the portlet.
     *
     * @param bundle the name of the resource bundle
     * @param key    the text key
     * @param locale the locale
     * @return the localized text resource
     */
    public String getText(String bundle, String key, Locale locale);

    /**
     * Sends the given message to all portlets on the same page that have the given concrete portlet id regardless of the portlet application.
     * If the concrete portlet id is null the message is broadcast to all portlets in the same portlet application.
     * If more than one instance of the portlet with the given id exists on the current page, the message is sent
     * to every single instance of that portlet. If the source portlet has the same name as the target portlet(s),
     * the message will not be sent to avoid possible cyclic calls.
     * <p/>
     * The portlet(s) with the given name will only receive the message event if it has/they have implemented
     * the appropriate listener.
     *
     * @param concretePortletID the concrete portlet ID of the portlet(s) to send the message to
     * @param message           the message to be sent
     */
    public void send(String concretePortletID, PortletMessage message);

    /**
     * This function looks up a portlet service with the given classname.
     * Using this method a portlet is able to get additional functionality like a
     * service to get external content over a firewall or to include a servlet.
     *
     * @param service the classname of the service to load
     * @return the portlet service
     * @throws PortletServiceUnavailableException
     *          if an exception has occurrred that interferes with the portlet
     *          service's normal initialization
     * @throws PortletServiceNotFoundException
     *          if the PortletService is not found
     */
    public PortletService getService(Class service)
            throws PortletServiceUnavailableException, PortletServiceNotFoundException;

    /**
     * Returns the portlet container version information
     *
     * @return the portlet container version information
     */
    public String getVersionInfo();

    /**
     * Returns the portlet container release information
     *
     * @return the portlet container release information
     */
    public String getReleaseInfo();

    /**
     * Returns the name and version of the portlet container which the portlet is running in.
     * The form of the returned string is servername/versionnumber.
     *
     * @return the string containing at least name and version number
     */
    public String getContainerInfo();

    /**
     * Returns the portlet log which allows the portlet to write
     * debug, informational, warning, or error messages to a log.
     *
     * @return the portlet log
     */
    public PortletLog getLog();

}
