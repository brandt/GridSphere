/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.impl;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portletcontainer.GridSphereProperties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.HashMap;

/**
 * The SportletRequestImpl encapsulates the request sent by the client to the portlet.
 *
 * The implementation class uses the facade pattern to represent a HttpServletRequest
 */
public class SportletRequestImpl implements SportletRequest {

    private HttpServletRequest req = null;
    private PortletSession portletSession = null;
    private PortletSettings portletSettings = null;
    private PortletData portletData = null;
    private PortletWindow portletWindow = null;
    private Portlet.ModeModifier modeModifier = null;
    private Portlet.Mode previousMode = null;
    private Portlet.Mode mode = null;
    private Client client = null;
    private User user = null;

    private static PortletLog log = SportletLog.getInstance(SportletRequestImpl.class);

    /**
     * Constructor creates a facade for a HttpServletRequest
     *
     * @param req the HttpServletRequest
     */
    public SportletRequestImpl(HttpServletRequest req) {
        this.req = req;
        HttpSession session = req.getSession(false);
        if (session == null) {
            portletSession = null;
            user = GuestUser.getInstance();
        } else {
            user = (User)session.getAttribute(GridSphereProperties.USER);
            portletSession = new SportletSession(session);
        }
        portletWindow = (PortletWindow)req.getAttribute(GridSphereProperties.PORTLETWINDOW);
        this.portletWindow = new SportletWindow();
        this.client = new ClientImpl(req);

    }

    /**
     * Returns the value of the attribute with the given name,
     * or null if no attribute with the given name exists.
     *
     * @param name the attribute name
     * @return the attribute value
     */
    public Object getAttribute(String name) {
        return req.getAttribute(name);
    }

    /**
     * Returns an enumeration of names of all attributes available to this request.
     * This method returns an empty enumeration if the request has no attributes available to it.
     *
     * @return the attribute names
     */
    public Enumeration getAttributeNames() {
        return req.getAttributeNames();
    }

    /**
     * Sets the attribute with the given name and value.
     *
     * @param name the attribute name
     * @param value the attribute value
     */
    public void setAttribute(String name, Object value) {
        req.setAttribute(name, value);
    }

    /**
     * Removes the attribute with the given name.
     *
     * @param name the attribute name to remove
     */
    public void removeAttribute(String name) {
        req.removeAttribute(name);
    }

    /**
     * Returns an object representing the client device that the user connects to the portal with.
     *
     * @return the client device
     */
    public Client getClient() {
        return client;
    }

    /**
     * Returns whether this request was made using a secure channel, such as HTTPS.
     *
     * @return true if channel is secure, false otherwise
     */
    public boolean isSecure() {
        return req.isSecure();
    }

    /**
     * Returns the current session or, if there is no current session, it creates one and returns it.
     *
     * @return the portlet session
     */
    public PortletSession getPortletSession() {
        if (portletSession == null) {
            HttpSession session = req.getSession();
            portletSession = new SportletSession(session);
        }
        return portletSession;
    }

    /**
     * Returns the current session or, if there is no current session and the given flag is true,
     * it creates one and returns it.
     *
     * If the given flag is false and there is no current portlet session, this method returns null.
     *
     * @param create true to create a news session, false to return null if there is no current session
     * @return the portlet session
     */
    public PortletSession getPortletSession(boolean create) {
        if (portletSession == null) {
            HttpSession session = req.getSession(create);
            if (create == true) {
                portletSession = new SportletSession(session);
            }
        }
        return portletSession;
    }

    /**
     * Returns an array containing all of the Cookie  objects the client sent with this request. This method returns null if no cookies were sent.
     *
     * @return an array of all the Cookies  included with this request, or null  if the request has no cookies
     */
    public Cookie[] getCookies() {
        return req.getCookies();
    }

    /**
     *
     * Returns the data of the concrete portlet instance
     * If the portlet is run in CONFIGURE mode, the portlet data is not accessible and this method will return null.
     *
     * @return the PortletData
     */
    public PortletData getData() {
        return portletData;
    }


    /**
     * Returns the user object. The user object contains useful information about the user and his or her preferences.
     * If the user has not logged in or does not grant access to the portlet, this method creates a GuestUser
     *
     * @see GuestUser
     *
     * @return the Role object
     */
    public User getUser() {
        return user;
    }

    /**
     * Returns the locale of the preferred language. The preference is based on the user's
     * choice of language(s) and/or the client's Accept-Language header.
     *
     * If more than one language is preferred, the locale returned by this
     * method is the one with the highest preference.
     *
     * @return the locale of the preferred language
     */
    public Locale getLocale() {
        return req.getLocale();
    }

    /**
     * Returns an Enumeration of Locale objects indicating, in decreasing order starting
     * with the preferred locale, the locales that are acceptable to the client based on
     * the Accept-Language header. If the client request doesn't provide an Accept-Language
     * header, this method returns an Enumeration containing one Locale, the default
     * locale for the server.
     *
     * @return an Enumeration of Locale objects
     */
    public Enumeration getLocales() {
        return req.getLocales();
    }

    /**
     * Returns the PortletSettings object of the concrete portlet.
     *
     * @return portletSettings the portlet settings
     */
    public PortletSettings getPortletSettings() {
        return portletSettings;
    }

    /**
     * Sets the mode that the portlet was running at last, or null if no previous mode exists.
     *
     * @param the previous portlet mode
     */
    public void setPreviousMode(Portlet.Mode previousMode) {
        this.previousMode = previousMode;
    }

    /**
     * Returns the mode that the portlet was running at last, or null if no previous mode exists.
     *
     * @return the previous portlet mode
     */
    public Portlet.Mode getPreviousMode() {
        return previousMode;
    }

    /**
     * Sets the window that the portlet is running in.
     *
     * @param the portlet window
     */
    public void setWindow(PortletWindow portletWindow) {
        this.portletWindow = portletWindow;
    }


    /**
     * Returns the window that the portlet is running in.
     *
     * @return the portlet window
     */
    public PortletWindow getWindow() {
        return portletWindow;
    }

    /**
     * Defines which portlet mode is shown next. Once the mode is changed it cannot be
     * changed back to PortletInfo.ModeModifier.REQUESTED.
     *
     * This function may only be used during event processing, in any other case the call has no effect.
     *
     * @param modeModifier the portlet mode modifier
     */
    public void setModeModifier(Portlet.ModeModifier modeModifier) {
        this.modeModifier = modeModifier;
    }

    public Portlet.Mode getMode() {
        return mode;
    }

    /**
     * Returns the mode that the portlet is running in.
     *
     * @return the portlet mode
     */
    public void setMode(Portlet.Mode mode) {
        this.mode = mode;
    }


    public final String getParameter(java.lang.String name) {
        return req.getParameter(name);
    }

    /**
     * Returns a map of the parameters of this request.
     *
     * @return a map of parameters
     */
    public Map getParameterMap() {
        String name;
        Map paramMap = new HashMap();
        Enumeration enum = req.getParameterNames();
        while (enum.hasMoreElements()) {
            name = (String)enum.nextElement();
            String[] values = req.getParameterValues(name);
            paramMap.put(name, values);
        }
        return paramMap;
    }

    /**
     * Returns an enumeration of all parameter names. If this request
     *
     * @return the enumeration of parameter names
     */
    public Enumeration getParameterNames() {
        return req.getParameterNames();
    }

    /**
     * Returns the values of all parameters with the given name.
     *
     * A request can carry more than one parameter with a certain name.
     * This method returns these parameters in the order of appearance.
     *
     * @param name the parameter name
     * @return the array of parameter values
     */
    public String[] getParameterValues(String name) {
        return req.getParameterValues(name);
    }

    public long getDateHeader(String name) {
        return req.getDateHeader(name);
    }

    public String getHeader(String name) {
        return req.getHeader(name);
    }

    public Enumeration getHeaders(String name) {
        return req.getHeaders(name);
    }

    public Enumeration getHeaderNames() {
        return req.getHeaderNames();
    }

    public int getIntHeader(String name) {
        return req.getIntHeader(name);
    }

    public void invalidateCache() {
        // XXX: FILL ME IN
    }

    public final String getAuthType() {
        return req.getAuthType();
    }

    public final String getMethod() {
        return req.getMethod();
    }

    public final String getPathInfo() {
        return req.getPathInfo();
    }

    public final String getPathTranslated() {
        return req.getPathTranslated();
    }

    public final String getContextPath() {
        return req.getContextPath();
    }

    public final String getQueryString() {
        return req.getQueryString();
    }

    public final String getRemoteUser() {
        return req.getRemoteUser();
    }

    public final boolean isUserInRole(String role) {
        return req.isUserInRole(role);
    }

    public final java.security.Principal getUserPrincipal() {
        return req.getUserPrincipal();
    }

    public final String getRequestedSessionId() {
        return req.getRequestedSessionId();
    }

    public final boolean isRequestedSessionIdValid() {
        return req.isRequestedSessionIdValid();
    }

    public final boolean isRequestedSessionIdFromCookie() {
        return req.isRequestedSessionIdFromCookie();
    }

    public final boolean isRequestedSessionIdFromURL() {
        return req.isRequestedSessionIdFromURL();
    }

    public boolean isRequestedSessionIdFromUrl() {
        return req.isRequestedSessionIdFromUrl();
    }

    public final String getRequestURI() {
        return req.getRequestURI();
    }

    public final StringBuffer getRequestURL() {
        return req.getRequestURL();
    }

    public final String getServletPath() {
        return req.getServletPath();
    }

    public final HttpSession getSession() {
        return req.getSession();
    }

    public final HttpSession getSession(boolean create) {
        return req.getSession(create);
    }


    // The ServletRequest methods
    public final ServletInputStream getInputStream() throws IOException {
        return req.getInputStream();
    }

    public final String getCharacterEncoding() {
        return req.getCharacterEncoding();
    }

    public final void setCharacterEncoding(String enc) throws UnsupportedEncodingException {
    }

    public final int getContentLength() {
        return req.getContentLength();
    }

    public final String getContentType() {
        return req.getContentType();
    }

    public final String getProtocol() {
        return req.getProtocol();
    }

    public final String getScheme() {
        return req.getScheme();
    }

    public final String getServerName() {
        return req.getServerName();
    }

    public final int getServerPort() {
        return req.getServerPort();
    }

    public final BufferedReader getReader() throws IOException {
        return req.getReader();
    }

    public final String getRemoteAddr() {
        return req.getRemoteAddr();
    }

    public final String getRemoteHost() {
        return req.getRemoteHost();
    }

    public final RequestDispatcher getRequestDispatcher(String path) {
        return req.getRequestDispatcher(path);
    }

    public final String getRealPath(String path) {
        return req.getRealPath(path);
    }


    // Primarily used for debugging
    public void logRequest() {
        String name, paramvalue, headervals;
        Object attrvalue;
        Enumeration enum, eenum;

        log.debug("HttpRequest Information");
        log.debug("\trequest headers: ");

        enum = getHeaderNames();
        while (enum.hasMoreElements()) {
            name = (String) enum.nextElement();
            eenum = (Enumeration) getHeaders(name);
            headervals = "";
            while (eenum.hasMoreElements()) {
                headervals += " " + (String) eenum.nextElement();
            }
            log.debug("\t\tname=" + name + " values=" + headervals);
        }
        log.debug("\tcontent type: " + getContentType());
        if (getCookies() != null)
            log.debug("\tcookies are present");
        log.debug("\trequest method: " + getMethod());
        log.debug("\tremote host: " + getRemoteHost() + " " + getRemoteAddr());
        log.debug("\trequest scheme: " + getScheme());
        log.debug("\trequest attribute names: ");
        enum = getAttributeNames();
        while (enum.hasMoreElements()) {
            name = (String) enum.nextElement();
            attrvalue = (Object) getAttribute(name);
            log.debug("\t\tname=" + name + " object type=" + attrvalue.getClass
                    ().getName());
        }
        log.debug("\trequest parameter names: note if a parameter has multiple values, only the first element is displayed ");
        enum = getParameterNames();
        while (enum.hasMoreElements()) {
            name = (String) enum.nextElement();
            paramvalue = getParameter(name);
            log.debug("\t\tname=" + name + " value=" + paramvalue);
        }
    }

}
