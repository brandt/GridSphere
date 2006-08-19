/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 */
package org.gridsphere.portlet.impl;

import org.gridsphere.portlet.*;
import org.gridsphere.portlet.UserPrincipal;
import org.gridsphere.portletcontainer.PortletDataManager;
import org.gridsphere.services.core.persistence.PersistenceManagerException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.Locale;
import java.util.List;
import java.io.UnsupportedEncodingException;


/**
 * A <code>SportletRequest</code> provides an implementation of the
 * <code>PortletRequest</code> by following the decorator patterm.
 * A HttpServletRequest object is used in composition to perform most of
 * the required methods.
 */
public class SportletRequest extends HttpServletRequestWrapper implements PortletRequest {

    private static PortletLog log = SportletLog.getInstance(SportletRequest.class);

    private PortletSession portletSession = null;

    /**
     * Constructor creates a proxy for a HttpServletRequest
     * All PortletRequest objects come from request or session attributes
     *
     * @param req the HttpServletRequest
     */
    public SportletRequest(HttpServletRequest req) {
        super(req);
        //logRequest();
    }

    /**
     * Returns an object representing the client device that the user connects to the portal with.
     *
     * @return the client device
     */
    public Client getClient() {
        Client client = (Client) this.getHttpServletRequest().getSession().getAttribute(SportletProperties.CLIENT);
        if (client == null) {
            client = new ClientImpl(this.getHttpServletRequest());
            this.getHttpServletRequest().getSession().setAttribute(SportletProperties.CLIENT, client);
        }
        return client;
    }

    /**
     * Sets the client device that the user connects to the portal with.
     *
     * @param client the client device
     */
    public void setClient(Client client) {
        this.getHttpServletRequest().getSession().setAttribute(SportletProperties.CLIENT, client);
    }

    /**
     * Returns whether this this.getHttpServletRequest()uest was made using a secure channel, such as HTTPS.
     *
     * @return true if channel is secure, false otherwise
     */
    public boolean isSecure() {
        return this.getHttpServletRequest().isSecure();
    }

    /**
     * Returns the current session or, if there is no current session, it creates one and returns it.
     *
     * @return the portlet session
     */
    public PortletSession getPortletSession() {
        return getPortletSession(true);
    }

    /**
     * Returns the current session or, if there is no current session and the given flag is true,
     * it creates one and returns it.
     * <p/>
     * If the given flag is false and there is no current portlet session, this method returns null.
     *
     * @param create true to create a new session, false to return null if there is no current session
     * @return the portlet session
     */
    public PortletSession getPortletSession(boolean create) {

        HttpSession httpSession = this.getHttpServletRequest().getSession(false);

        // if no underlying httpsession exists but portletsession is not null, make it null
        if ((portletSession != null) && (httpSession == null)) {
            portletSession = null;
            // if httpsession exists, but portletsession is null, create new one
        } else if (httpSession != null) {
            create = true;
        }

        if (create && (portletSession == null)) {
            httpSession = this.getHttpServletRequest().getSession(create);
            if (httpSession != null) {
                portletSession = new SportletSession(httpSession);
            }
        }

        return portletSession;
    }

    /**
     * Returns an array containing all of the Cookie  objects the client sent with this this.getHttpServletRequest()uest. This method returns null if no cookies were sent.
     *
     * @return an array of all the Cookies  included with this this.getHttpServletRequest()uest, or null  if the this.getHttpServletRequest()uest has no cookies
     */
    public Cookie[] getCookies() {
        return this.getHttpServletRequest().getCookies();
    }

    /**
     * Returns the data of the concrete portlet instance
     * If the portlet is run in CONFIGURE mode, the portlet data is not accessible and this method will return null.
     *
     * @return the PortletData
     */
    public PortletData getData() {
        if (getMode() == Mode.CONFIGURE) {
            return null;
        }
        PortletDataManager dataManager = (PortletDataManager)this.getHttpServletRequest().getAttribute(SportletProperties.PORTLET_DATA_MANAGER);
        String portletID = (String) getAttribute(SportletProperties.PORTLETID);
        if (portletID == null) {
            // it may be in the request parameter
            portletID = getParameter(SportletProperties.PORTLETID);
        }
        try {
            return dataManager.getPortletData(getUser(), portletID);
        } catch (PersistenceManagerException e) {
            log.error("Unable to obtain PortletData for user", e);
        }
        return null;
    }

    /**
     * Returns the user object. The user object contains useful information about the user and his or her preferences.
     * If the user has not logged in or does not grant access to the portlet, this method returns null
     *
     * @return the User object
     */
    public User getUser() {
        return (User)this.getHttpServletRequest().getAttribute(SportletProperties.PORTLET_USER);
    }

    public List getRoles() {
        return (List)this.getHttpServletRequest().getAttribute(SportletProperties.PORTLET_ROLE);
    }

    /**
     * Returns the login of the user making this request, if the user
     * has been authenticated, or null if the user has not been authenticated.
     *
     * @return		a <code>String</code> specifying the login
     * of the user making this request, or <code>null</code>
     * if the user login is not known.
     */
    public String getRemoteUser() {
        UserPrincipal userPrincipal = (UserPrincipal)getAttribute(SportletProperties.PORTLET_USER_PRINCIPAL);
        return (userPrincipal != null) ? userPrincipal.getName() : this.getHttpServletRequest().getRemoteUser();
    }

    /**
     * Returns a java.security.Principal object containing the name of the
     * current authenticated user.
     *
     * @return		a <code>java.security.Principal</code> containing
     * the name of the user making this request, or
     * <code>null</code> if the user has not been
     * authenticated.
     */
    public java.security.Principal getUserPrincipal() {
        UserPrincipal userPrincipal = (UserPrincipal)getAttribute(SportletProperties.PORTLET_USER_PRINCIPAL);
        return (userPrincipal != null) ? userPrincipal : this.getHttpServletRequest().getUserPrincipal();
    }

    /**
     * Returns a boolean indicating whether the authenticated user is
     * included in the specified logical "role".  Roles and role membership can be
     * defined using deployment descriptors.  If the user has not been
     * authenticated, the method returns <code>false</code>.
     *
     * @param role a <code>String</code> specifying the name
     *             of the role
     * @return		a <code>boolean</code> indicating whether
     * the user making this request belongs to a given role;
     * <code>false</code> if the user has not been
     * authenticated.
     */
    public boolean isUserInRole(String role) {
        // TODO
        // As specified in PLT-20-3, the <security-role-ref> mapping in portlet.xml must be used.
        List roles = (List)getAttribute(SportletProperties.PORTLET_ROLE);
        if (role.equals("")) return true;
        if (getUserPrincipal() == null) return false;
        if (roles.contains(role)) return true;
        return this.getHttpServletRequest().isUserInRole(role);
    }
    
    public void setRoles(List roles) {
        this.getHttpServletRequest().setAttribute(SportletProperties.PORTLET_ROLE, roles);
    }

    /**
     * Returns the locale of the preferred language. The preference is based on the user's
     * choice of language(s) and/or the client's Accept-Language header.
     * <p/>
     * If more than one language is preferred, the locale returned by this
     * method is the one with the highest preference.
     *
     * @return the locale of the preferred language
     */
    public Locale getLocale() {
        Locale locale = null;
        User user = getUser();
        if (user != null) {
            String loc = (String) user.getAttribute(User.LOCALE);
            if (loc != null) {
                locale = new Locale(loc, "", "");
                this.getPortletSession(true).setAttribute(User.LOCALE, locale);
                return locale;
            }
        }
        locale = (Locale) this.getPortletSession(true).getAttribute(User.LOCALE);
        if (locale != null) return locale;
        locale = this.getHttpServletRequest().getLocale();
        if (locale != null) return locale;
        return Locale.ENGLISH;
    }

    /**
     * Returns an Enumeration of Locale objects indicating, in decreasing order starting
     * with the preferred locale, the locales that are acceptable to the client based on
     * the Accept-Language header. If the client this.getHttpServletRequest()uest doesn't provide an Accept-Language
     * header, this method returns an Enumeration containing one Locale, the default
     * locale for the server.
     *
     * @return an Enumeration of Locale objects
     */
    public Enumeration getLocales() {
        return this.getHttpServletRequest().getLocales();
    }

    /**
     * Returns the PortletSettings object of the concrete portlet.
     *
     * @return the portlet settings
     */
    public PortletSettings getPortletSettings() {
        return (PortletSettings) this.getHttpServletRequest().getAttribute(SportletProperties.PORTLET_SETTINGS);
    }

    /**
     * Sets the PortletSettings object of the concrete portlet.
     *
     * @param settings the portlet settings
     */
    public void setPortletSettings(PortletSettings settings) {
        this.getHttpServletRequest().setAttribute(SportletProperties.PORTLET_SETTINGS, settings);
    }


    /**
     * Returns the mode that the portlet was running at last, or Portlet.Mode.VIEW if no previous mode exists.
     *
     * @return the previous portlet mode
     */
    public Mode getPreviousMode() {
        Mode prev = (Mode) this.getHttpServletRequest().getAttribute(SportletProperties.PREVIOUS_MODE);
        if (prev == null) prev = Mode.VIEW;
        return prev;
    }

    /**
     * Sets the previous portlet mode.
     *
     * @param previousMode the previous portlet mode
     */
    public void setPreviousMode(Mode previousMode) {
        this.getHttpServletRequest().setAttribute(SportletProperties.PREVIOUS_MODE, previousMode);
    }

    /**
     * Returns the window that the portlet is running in.
     *
     * @return the portlet window
     */
    public PortletWindow.State getWindowState() {
        return (PortletWindow.State) this.getHttpServletRequest().getAttribute(SportletProperties.PORTLET_WINDOW);
    }

    /**
     * Returns the window state that the portlet is running in.
     *
     * @param state the portlet window state
     */
    public void setWindowState(PortletWindow.State state) {
        this.getHttpServletRequest().setAttribute(SportletProperties.PORTLET_WINDOW, state);
    }

    /**
     * Returns the mode that the portlet is running in.
     *
     * @return the portlet mode
     */
    public Mode getMode() {
        String mode = (String) this.getHttpServletRequest().getAttribute(SportletProperties.PORTLET_MODE);
        return Mode.toMode(mode);
    }

    /**
     * Sets the mode that the portlet is running in.
     *
     * @param mode the portlet mode
     */
    public void setMode(Mode mode) {
        this.getHttpServletRequest().setAttribute(SportletProperties.PORTLET_MODE, mode.toString());
    }

    public void invalidateCache() {
        // XXX: FILL ME IN
    }

    public void setCharacterEncoding(String encoding) throws UnsupportedEncodingException {
        this.getHttpServletRequest().setCharacterEncoding(encoding);
    }
    
    // Primarily used for debugging
    public void logRequest() {
        String name, paramvalue, headervals;
        Object attrvalue;
        Enumeration e1, eenum;

        log.debug("PortletRequest Information");
        log.debug("\tthis.getHttpServletRequest()uest headers: ");

        e1 = getHeaderNames();
        while (e1.hasMoreElements()) {
            name = (String) e1.nextElement();
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
        log.debug("\tthis.getHttpServletRequest()uest method: " + getMethod());
        log.debug("\tremote host: " + getRemoteHost() + " " + getRemoteAddr());
        log.debug("\tthis.getHttpServletRequest()uest scheme: " + getScheme());
        log.debug("\tthis.getHttpServletRequest()uest attribute names: ");
        e1 = getAttributeNames();
        while (e1.hasMoreElements()) {
            name = (String)e1.nextElement();
            attrvalue = (Object) getAttribute(name);
            log.debug("\t\tname=" + name + " object type=" + attrvalue.getClass().getName());
        }
        log.debug("\tthis.getHttpServletRequest()uest parameter names: note if a parameter has multiple values, only the first beans is displayed ");
        e1 = getParameterNames();
        while (e1.hasMoreElements()) {
            name = (String) e1.nextElement();
            paramvalue = getParameter(name);
            log.debug("\t\tname=" + name + " value=" + paramvalue);
        }
        log.debug("\tthis.getHttpServletRequest()uest parameter info: ");
        log.debug("\tthis.getHttpServletRequest()uest path info: " + this.getHttpServletRequest().getPathInfo());
    }

    private HttpServletRequest getHttpServletRequest() {
        return (HttpServletRequest) super.getRequest();
    }
}
