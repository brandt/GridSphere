/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet;

import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

/**
 * The PortletRequest encapsulates the request sent by the client to the portlet.
 */
public interface PortletRequest extends HttpServletRequest {

    /**
     * Returns the value of the attribute with the given name,
     * or null if no attribute with the given name exists.
     *
     * @param name the attribute name
     * @return the attribute value
     */
    public Object getAttribute(String name);

    /**
     * Returns an enumeration of names of all attributes available to this request.
     * This method returns an empty enumeration if the request has no attributes available to it.
     *
     * @return the attribute names
     */
    public Enumeration getAttributeNames();

    /**
     * Removes the attribute with the given name.
     *
     * @param name the name of the attribute to be rermoved
     */
    public void removeAttribute(String name);

    /**
     * Sets the attribute with the given name and value.
     *
     * @param name the attribute name
     * @param value the attribute value
     */
    public void setAttribute(String name, Object value);

    /**
     * Returns whether this request was made using a secure channel, such as HTTPS.
     *
     * @return true if channel is secure, false otherwise
     */
    public boolean isSecure();

    /**
     * Returns the current session or, if there is no current session, it creates one and returns it.
     *
     * @return the portlet session
     */
    public PortletSession getPortletSession();

    /**
     * Returns the current session or, if there is no current session and the given flag is true,
     * it creates one and returns it.
     * If the given flag is false and there is no current portlet session, this method returns null.
     *
     * @param create true to create a news session, false to return null of there is no current session
     */
    public PortletSession getPortletSession(boolean create);

    /**
     * Returns an object representing the client device that the user connects to the portal with.
     *
     * @return the client device
     */
    public Client getClient();

    /**
     * Returns an array containing all of the Cookie  objects the client sent with this request. This method returns null if no cookies were sent.
     *
     * @return an array of all the Cookies  included with this request, or null  if the request has no cookies
     */
    public Cookie[] getCookies();

    /**
     *
     * Returns the data of the concrete portlet instance
     * If the portlet is run in CONFIGURE mode, the portlet data is not accessible and this method will return null.
     *
     * @return the PortletData
     */
    public PortletData getData();

    /**
     * Returns the user object. The user object contains useful information about the user and his or her preferences.
     * If the user has not logged in or does not grant access to the portlet, this method returns null
     *
     * @return the User object
     */
    public User getUser();

    /**
     * Returns the PortletSettings object of the concrete portlet.
     *
     * @return the portlet settings
     */
    public PortletSettings getPortletSettings();

    /**
     * Returns the value of the specified request header as a long value that represents a Date object.
     * Use this method with headers that contain dates, such as If-Modified-Since.
     *
     * @param name the date name
     * @return the date header
     */
    public long getDateHeader(String name);

    /**
     * Returns the value of the specified request header as a String.
     * If the request did not include a header of the specified name, this method returns null.
     * The header name is case insensitive. You can use this method with any request header.
     *
     * @param name a String specifying the header name
     * @return the value of the header name
     */
    public String getHeader(String name);

    /**
     * Returns all the values of the specified request header as an Enumeration of String objects.
     * Some headers, such as Accept-Language can be sent by clients as several headers each with a different
     * value rather than sending the header as a comma separated list.
     *
     * If the request did not include any headers of the specified name, this method returns an empty Enumeration.
     * The header name is case insensitive. You can use this method with any request header.
     *
     * @param name the String specifying the header name
     * @return a Enumeration containing the values of the requested header,
     * or null  if the request does not have any headers of that name
     */
    public Enumeration getHeaders(String name);

    /**
     * Returns an enumeration of all the header names this request contains.
     * If the request has no headers, this method returns an empty enumeration.
     * Some portlet containers do not allow do not allow portlets to access headers using this method,
     * in which case this method returns null
     *
     * @return an Enumeration of header names
     */
    public Enumeration getHeaderNames();

    /**
     * Retrieves the body of the request as binary data using an InputStream.
     *
     * @return an input stream containing the body of the request
     */
    public ServletInputStream getInputStream() throws IOException;

    /**
     * Returns the locale of the preferred language. The preference is based on the user's
     * choice of language(s) and/or the client's Accept-Language header.
     *
     * If more than one language is preferred, the locale returned by this
     * method is the one with the highest preference.
     *
     * @return the locale of the preferred language
     */
    public Locale getLocale();

    /**
     * Returns an Enumeration of Locale objects indicating, in decreasing order starting
     * with the preferred locale, the locales that are acceptable to the client based on
     * the Accept-Language header. If the client request doesn't provide an Accept-Language
     * header, this method returns an Enumeration containing one Locale, the default
     * locale for the server.
     *
     * @return an Enumeration of Locale objects
     */
    public Enumeration getLocales();

    /**
     * Returns the value of the specified request header as an int.
     * If the request does not have a header of the specified name, this method returns -1.
     * If the header cannot be converted to an integer, this method throws a NumberFormatException.
     *
     * The header name is case insensitive.
     *
     * @param name a String specifying the name of a request header
     * @return an integer expressing the value of the request header or -1
     * if the request doesn't have a header of this name
     */
    public int getIntHeader(String name);

    /**
     * Invalidates the cache for all window states, markups and locals
     */
    public void invalidateCache();

    /**
     * Returns the HTTP method of this request. The most commonly used request methods are GET and POST.
     *
     * @return the method
     */
    public String getMethod();

    /**
     * Returns the mode that the portlet is running in.
     *
     * @return the portlet mode
     */
    public Portlet.Mode getMode();

    /**
     * Returns the value of the parameter with the given name, or null if no such parameter exists.
     *
     * You should only use this method when you are sure the parameter has only one value.
     * If not, use getParameterValues(String)
     *
     * @param name the parameter name
     * @return the parameter value
     */
    public String getParameter(String name);

    /**
     * Returns a map of the parameters of this request.
     *
     * @return a map of parameters
     */
    public Map getParameterMap();

    /**
     * Returns an enumeration of all parameter names. If this request
     *
     * @return the enumeration of parameter names
     */
    public Enumeration getParameterNames();

    /**
     * Returns the values of all parameters with the given name.
     *
     * A request can carry more than one parameter with a certain name.
     * This method returns these parameters in the order of appearance.
     *
     * @param name the parameter name
     * @return the array of parameter values
     */
    public String[] getParameterValues(String name);

    /**
     * Returns the mode that the portlet was running at last, or null if no previous mode exists.
     *
     * @return the previous portlet mode
     */
    public Portlet.Mode getPreviousMode();

    /**
     * Returns the window that the portlet is running in.
     *
     * @return the portlet window
     */
    public PortletWindow getWindow();

    /**
     * Defines which portlet mode is shown next. Once the mode is changed it cannot be
     * changed back to Portlet.ModeModifier.REQUESTED.
     *
     * This function may only be used during event processing, in any other case the call has no effect.
     *
     * @param modeModifier the portlet mode modifier
     */
    public void setModeModifier(Portlet.ModeModifier modeModifier);

}
