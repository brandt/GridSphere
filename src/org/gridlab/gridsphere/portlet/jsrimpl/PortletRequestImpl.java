
package org.gridlab.gridsphere.portlet.jsrimpl;

import org.gridlab.gridsphere.portlet.impl.SportletProperties;
import org.gridlab.gridsphere.portlet.Portlet;
import org.gridlab.gridsphere.portlet.PortletWindow;
import org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Supports;

import javax.portlet.ActionRequest;
import javax.portlet.RenderRequest;
import javax.portlet.WindowState;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletSession;
import javax.portlet.PortalContext;
import javax.portlet.PortletResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;


/**
 * The <CODE>PortletRequest</CODE> defines the base interface to provide client
 * request information to a portlet. The portlet container uses two specialized
 * versions of this interface when invoking a portlet, <CODE>ActionRequest</CODE>
 * and <CODE>RenderRequest</CODE>. The portlet container creates these objects and
 * passes them as  arguments to the portlet's <CODE>processAction</CODE> and
 * <CODE>render</CODE> methods.
 *
 * @see ActionRequest
 * @see RenderRequest
 */
public abstract class PortletRequestImpl extends HttpServletRequestWrapper implements PortletRequest
{

    protected PortletContext portletContext = null;
    protected PortalContext portalContext = null;
    protected Supports[] supports = null;
    protected String contextPath = "/";

    protected Map props = null;

    /**
     * Constructor creates a proxy for a HttpServletRequest
     * All PortletRequest objects come from request or session attributes
     *
     * @param req the HttpServletRequest
     */
    public PortletRequestImpl(HttpServletRequest req, PortalContext portalContext, PortletContext portletContext, Supports[] supports) {
        super(req);
        this.portalContext = portalContext;
        this.portletContext = portletContext;
        contextPath = this.portletContext.getRealPath("");
        int l = contextPath.lastIndexOf("/");
        contextPath = contextPath.substring(l);
        this.supports = supports;
        props = new HashMap();
    }

    /**
     * Returns true, if the given window state is valid
     * to be set for this portlet in the context
     * of the current request.
     *
     * @param  state    window state to checked
     *
     * @return    true, if it is valid for this portlet
     *             in this request to change to the
     *            given window state
     *
     */
    public boolean isWindowStateAllowed(WindowState state) {
        Enumeration statesEnum = portalContext.getSupportedWindowStates();
        while (statesEnum.hasMoreElements()) {
            WindowState s = (WindowState)statesEnum.nextElement();
            if (s.equals(state)) return true;
        }
        return false;
    }

    /**
     * Returns true, if the given portlet mode is a valid
     * one to set for this portlet  in the context
     * of the current request.
     *
     * @param  mode    portlet mode to check
     *
     * @return    true, if it is valid for this portlet
     *             in this request to change to the
     *            given portlet mode
     *
     */
    public boolean isPortletModeAllowed(PortletMode mode) {
        Enumeration modesEnum = portalContext.getSupportedPortletModes();
        while (modesEnum.hasMoreElements()) {
            PortletMode m = (PortletMode)modesEnum.nextElement();
            if (m.equals(mode)) return true;
        }
        return false;
    }

    /**
     * Returns the current portlet mode of the portlet.
     *
     * @return   the portlet mode
     */
    public PortletMode getPortletMode() {
       Portlet.Mode mode = (Portlet.Mode)getAttribute(SportletProperties.PORTLET_MODE);
       PortletMode m = PortletMode.VIEW;
        if (mode == Portlet.Mode.EDIT) {
            m = PortletMode.EDIT;
        } else if (mode == Portlet.Mode.HELP) {
            m = PortletMode.HELP;
        } else {
            m = new PortletMode(mode.toString());
        }
        return m;
    }

    /**
     * Returns the current window state of the portlet.
     *
     * @return   the window state
     */
    public WindowState getWindowState() {
        PortletWindow.State state = (PortletWindow.State)getAttribute(SportletProperties.PORTLET_WINDOW);
        WindowState ws = WindowState.NORMAL;
        if (state == PortletWindow.State.MAXIMIZED) {
            ws = WindowState.MAXIMIZED;
        } else if (state == PortletWindow.State.MINIMIZED) {
            ws = WindowState.MINIMIZED;
        } else if (state == PortletWindow.State.NORMAL) {
            ws = WindowState.NORMAL;
        }
        return ws;
    }

    /**
     * Returns the preferences object associated with the portlet.
     *
     * @return the portlet preferences
     */
    public PortletPreferences getPreferences() {
        return (PortletPreferences)getAttribute(SportletProperties.PORTLET_PREFERENCES);
    }

    /**
     * Returns the current portlet session or, if there is no current session,
     * creates one and returns the new session.
     *  <p>
     * Creating a new portlet session will result in creating
     * a new <code>HttpSession</code> on which the portlet session is based on.
     *
     * @return the portlet session
     */
    public PortletSession getPortletSession() {
        return new PortletSessionImpl(this.getHttpServletRequest().getSession(true), portletContext);
    }

    /**
     * Returns the current portlet session or, if there is no current session
     * and the given flag is <CODE>true</CODE>, creates one and returns
     * the new session.
     * <P>
     * If the given flag is <CODE>false</CODE> and there is no current
     * portlet session, this method returns <CODE>null</CODE>.
     *  <p>
     * Creating a new portlet session will result in creating
     * a new <code>HttpSession</code> on which the portlet session is based on.
     *
     * @param create
     *               <CODE>true</CODE> to create a new session, <BR>
     *               <CODE>false</CODE> to return <CODE>null</CODE> if there
     *               is no current session
     * @return the portlet session
     */
    public PortletSession getPortletSession (boolean create) {
        if ((this.getHttpServletRequest().getSession() == null) && (create == false)) {
            return null;
        }
        return new PortletSessionImpl(this.getHttpServletRequest().getSession(true), portletContext);
    }


    /**
     * Returns the value of the specified request property
     * as a <code>String</code>. If the request did not include a property
     * of the specified name, this method returns <code>null</code>.
     * <p>
     * A portlet can access portal/portlet-container specific properties
     * through this method and, if available, the
     * headers of the HTTP client request.
     * <p>
     * This method should only be used if the
     * property has only one value. If the property might have
     * more than one value, use {@link #getProperties}.
     * <p>
     * If this method is used with a multivalued
     * parameter, the value returned is equal to the first value
     * in the Enumeration returned by <code>getProperties</code>.
     *
     * @param name		a <code>String</code> specifying the
     *				property name
     *
     * @return			a <code>String</code> containing the
     *				value of the requested
     *				property, or <code>null</code>
     *				if the request does not
     *				have a property of that name.
     *
     * @exception  IllegalArgumentException
     *                            if name is <code>null</code>.
     */
    public String getProperty(String name) {
        if (name == null) throw new IllegalArgumentException("name is NULL");
        Object o = props.get(name);
        if (o instanceof String) {
            return (String)o;
        } else if (o instanceof List) {
            List l = (List)o;
            if (!l.isEmpty()) {
                return (String)l.get(0);
            }
        }
        return null;
    }

    /**
     * Returns all the values of the specified request property
     * as a <code>Enumeration</code> of <code>String</code> objects.
     * <p>
     * If the request did not include any propertys
     * of the specified name, this method returns an empty
     * <code>Enumeration</code>.
     * The property name is case insensitive. You can use
     * this method with any request property.
     *
     * @param name		a <code>String</code> specifying the
     *				property name
     *
     * @return		a <code>Enumeration</code> containing
     *                  	the values of the requested property. If
     *                  	the request does not have any properties of
     *                  	that name return an empty <code>Enumeration</code>.
     *
     * @exception  IllegalArgumentException
     *                            if name is <code>null</code>.
     */
    public java.util.Enumeration getProperties(String name) {
        if (name == null) throw new IllegalArgumentException("name is NULL");
        Object o = props.get(name);
        if (o instanceof List) {
            List l = (List)o;
            return new Enumerator(l.iterator());
        }
        return null;
    }

    /**
     *
     * Returns a <code>Enumeration</code> of all the property names
     * this request contains. If the request has no
     * properties, this method returns an empty <code>Enumeration</code>.
     *
     *
     * @return			an <code>Enumeration</code> of all the
     *				property names sent with this
     *				request; if the request has
     *				no properties, an empty <code>Enumeration</code>.
     */
    public java.util.Enumeration getPropertyNames() {
        return new Enumerator(props.keySet().iterator());
    }

    /**
     * Returns the context of the calling portal.
     *
     * @return the context of the calling portal
     */
    public PortalContext getPortalContext() {
        return portalContext;
    }

    /**
     * Returns the name of the authentication scheme used for the
     * connection between client and portal,
     * for example, <code>BASIC_AUTH</code>, <code>CLIENT_CERT_AUTH</code>,
     * a custom one or <code>null</code> if there was no authentication.
     *
     * @return		one of the static members <code>BASIC_AUTH</code>,
     *			<code>FORM_AUTH</code>, <code>CLIENT_CERT_AUTH</code>,
     *                    <code>DIGEST_AUTH</code> (suitable for == comparison)
     *			indicating the authentication scheme,
     *                    a custom one, or
     *			<code>null</code> if the request was
     *			not authenticated.
     */
    public String getAuthType() {
        return this.getHttpServletRequest().getAuthType();
    }

    /**
     * Returns the context path which is the path prefix associated with the deployed
     * portlet application. If the portlet application is rooted at the
     * base of the web server URL namespace (also known as "default" context),
     * this path must be an empty string. Otherwise, it must be the path the
     * portlet application is rooted to, the path must start with a '/' and
     * it must not end with a '/' character.
     * <p>
     * To encode a URL the {@link PortletResponse#encodeURL} method must be used.
     *
     * @return		a <code>String</code> specifying the
     *			portion of the request URL that indicates the context
     *			of the request
     *
     * @see PortletResponse#encodeURL
     */
    public String getContextPath() {
        return contextPath;
    }

    /**
     * Returns the login of the user making this request, if the user
     * has been authenticated, or null if the user has not been authenticated.
     *
     * @return		a <code>String</code> specifying the login
     *			of the user making this request, or <code>null</code>
     *			if the user login is not known.
     *
     */
    public String getRemoteUser() {
        return this.getHttpServletRequest().getRemoteUser();
    }

    /**
     * Returns a java.security.Principal object containing the name of the
     * current authenticated user.
     *
     * @return		a <code>java.security.Principal</code> containing
     *			the name of the user making this request, or
     *			<code>null</code> if the user has not been
     *			authenticated.
     */
    public java.security.Principal getUserPrincipal() {
        return this.getHttpServletRequest().getUserPrincipal();
    }

    /**
     * Returns a boolean indicating whether the authenticated user is
     * included in the specified logical "role".  Roles and role membership can be
     * defined using deployment descriptors.  If the user has not been
     * authenticated, the method returns <code>false</code>.
     *
     * @param role		a <code>String</code> specifying the name
     *				of the role
     *
     * @return		a <code>boolean</code> indicating whether
     *			the user making this request belongs to a given role;
     *			<code>false</code> if the user has not been
     *			authenticated.
     */
    public boolean isUserInRole(String role) {
        return this.getHttpServletRequest().isUserInRole(role);
    }

    /**
     *
     * Returns the value of the named attribute as an <code>Object</code>,
     * or <code>null</code> if no attribute of the given name exists.
     * <p>
     * Attribute names should follow the same conventions as package
     * names. This specification reserves names matching <code>java.*</code>,
     * and <code>javax.*</code>.
     * <p>
     * In a distributed portlet web application the <code>Object</code>
     * needs to be serializable.
     *
     * @param name	a <code>String</code> specifying the name of
     *			the attribute
     *
     * @return		an <code>Object</code> containing the value
     *			of the attribute, or <code>null</code> if
     *			the attribute does not exist.
     *
     * @exception  IllegalArgumentException
     *                            if name is <code>null</code>.
     *
     */
    public Object getAttribute(String name) {
        if (name == null) throw new IllegalArgumentException("name is NULL");
        return this.getHttpServletRequest().getAttribute(name);
    }

    /**
     * Returns an <code>Enumeration</code> containing the
     * names of the attributes available to this request.
     * This method returns an empty <code>Enumeration</code>
     * if the request has no attributes available to it.
     *
     *
     * @return		an <code>Enumeration</code> of strings
     *			containing the names
     * 			of the request attributes, or an empty
     *                    <code>Enumeration</code> if the request
     *                    has no attributes available to it.
     */
    public java.util.Enumeration getAttributeNames() {
        return this.getHttpServletRequest().getAttributeNames();
    }

    /**
     * Returns the value of a request parameter as a <code>String</code>,
     * or <code>null</code> if the parameter does not exist. Request parameters
     * are extra information sent with the request. The returned parameter
     * are "x-www-form-urlencoded" decoded.
     * <p>
     * Only parameters targeted to the current portlet are accessible.
     * <p>
     * This method should only be used if the
     * parameter has only one value. If the parameter might have
     * more than one value, use {@link #getParameterValues}.
     * <p>
     * If this method is used with a multivalued
     * parameter, the value returned is equal to the first value
     * in the array returned by <code>getParameterValues</code>.
     *
     *
     *
     * @param name 	a <code>String</code> specifying the
     *			name of the parameter
     *
     * @return		a <code>String</code> representing the
     *			single value of the parameter
     *
     * @see 		#getParameterValues
     *
     * @exception  IllegalArgumentException
     *                            if name is <code>null</code>.
     *
     */
    public String getParameter(String name) {
        if (name == null) throw new IllegalArgumentException("name is NULL");
        return this.getHttpServletRequest().getParameter(name);
    }

    /**
     *
     * Returns an <code>Enumeration</code> of <code>String</code>
     * objects containing the names of the parameters contained
     * in this request. If the request has
     * no parameters, the method returns an
     * empty <code>Enumeration</code>.
     * <p>
     * Only parameters targeted to the current portlet are returned.
     *
     *
     * @return		an <code>Enumeration</code> of <code>String</code>
     *			objects, each <code>String</code> containing
     * 			the name of a request parameter; or an
     *			empty <code>Enumeration</code> if the
     *			request has no parameters.
     */
    public java.util.Enumeration getParameterNames() {
        return this.getHttpServletRequest().getParameterNames();
    }

    /**
     * Returns an array of <code>String</code> objects containing
     * all of the values the given request parameter has, or
     * <code>null</code> if the parameter does not exist.
     * The returned parameters are "x-www-form-urlencoded" decoded.
     * <p>
     * If the parameter has a single value, the array has a length
     * of 1.
     *
     *
     * @param name	a <code>String</code> containing the name of
     *			the parameter the value of which is requested
     *
     * @return		an array of <code>String</code> objects
     *			containing the parameter values.
     *
     * @see		#getParameter
     *
     * @exception  IllegalArgumentException
     *                            if name is <code>null</code>.
     *
     */
    public String[] getParameterValues(String name) {
        if (name == null) throw new IllegalArgumentException("name is NULL");
        return this.getHttpServletRequest().getParameterValues(name);
    }

    /**
     * Returns a <code>Map</code> of the parameters of this request.
     * Request parameters are extra information sent with the request.
     * The returned parameters are "x-www-form-urlencoded" decoded.
     * <p>
     * The values in the returned <code>Map</code> are from type
     * String array (<code>String[]</code>).
     * <p>
     * If no parameters exist this method returns an empty <code>Map</code>.
     *
     * @return     an immutable <code>Map</code> containing parameter names as
     *             keys and parameter values as map values, or an empty <code>Map</code>
     *             if no parameters exist. The keys in the parameter
     *             map are of type String. The values in the parameter map are of type
     *             String array (<code>String[]</code>).
     */
    public java.util.Map getParameterMap() {
        return Collections.unmodifiableMap(this.getHttpServletRequest().getParameterMap());
    }

    /**
     * Returns a boolean indicating whether this request was made
     * using a secure channel between client and the portal, such as HTTPS.
     *
     * @return  true, if the request was made using a secure channel.
     */
    public boolean isSecure() {
        return this.getHttpServletRequest().isSecure();
    }

    /**
     * Stores an attribute in this request.
     *
     * <p>Attribute names should follow the same conventions as
     * package names. Names beginning with <code>java.*</code>,
     * <code>javax.*</code>, and <code>com.sun.*</code> are
     * reserved for use by Sun Microsystems.
     *<br> If the value passed into this method is <code>null</code>,
     * the effect is the same as calling {@link #removeAttribute}.
     *
     *
     * @param name			a <code>String</code> specifying
     *					the name of the attribute
     *
     * @param o				the <code>Object</code> to be stored
     *
     *
     * @exception  IllegalArgumentException
     *                            if name is <code>null</code>.
     */
    public void setAttribute(String name, Object o) {
        if (name == null) throw new IllegalArgumentException("name is NULL");
        if (o == null) this.removeAttribute(name);
        this.getHttpServletRequest().setAttribute(name, o);
    }

    /**
     *
     * Removes an attribute from this request.  This method is not
     * generally needed, as attributes only persist as long as the request
     * is being handled.
     *
     * <p>Attribute names should follow the same conventions as
     * package names. Names beginning with <code>java.*</code>,
     * <code>javax.*</code>, and <code>com.sun.*</code> are
     * reserved for use by Sun Microsystems.
     *
     * @param name			a <code>String</code> specifying
     *					the name of the attribute to be removed
     *
     *
     * @exception  IllegalArgumentException
     *                            if name is <code>null</code>.
     */
    public void removeAttribute(String name) {
        if (name == null) throw new IllegalArgumentException("name is NULL");
        this.getHttpServletRequest().removeAttribute(name);
    }

    /**
     *
     * Returns the session ID indicated in the client request.
     * This session ID may not be a valid one, it may be an old
     * one that has expired or has been invalidated.
     * If the client request
     * did not specify a session ID, this method returns
     * <code>null</code>.
     *
     * @return		a <code>String</code> specifying the session
     *			ID, or <code>null</code> if the request did
     *			not specify a session ID
     *
     * @see		#isRequestedSessionIdValid
     *
     */
    public String getRequestedSessionId() {
        return this.getHttpServletRequest().getRequestedSessionId();
    }

    /**
     *
     * Checks whether the requested session ID is still valid.
     *
     * @return			<code>true</code> if this
     *				request has an id for a valid session
     *				in the current session context;
     *				<code>false</code> otherwise
     *
     * @see			#getRequestedSessionId
     * @see			#getPortletSession
     */
    public boolean isRequestedSessionIdValid() {
        return this.getHttpServletRequest().isRequestedSessionIdValid();
    }

    /**
     * Returns the portal preferred content type for the response.
     * <p>
     * The content type only includes the MIME type, not the
     * character set.
     * <p>
     * Only content types that the portlet has defined in its
     * deployment descriptor are valid return values for
     * this method call. If the portlet has defined
     * <code>'*'</code> or <code>'* / *'</code> as supported content
     * types, these may also be valid return values.
     *
     * @return preferred MIME type of the response
     */
    public String getResponseContentType() {
        Portlet.Mode mode = (Portlet.Mode)getAttribute(SportletProperties.PORTLET_MODE);
        if (supports != null) {
            for (int i = 0; i < supports.length; i++) {
                Supports s = supports[i];
                org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletMode[] modes = s.getPortletMode();
                for (int j = 0; j < modes.length; j++) {
                    org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletMode m = modes[j];
                    if (m.getContent().equalsIgnoreCase(mode.toString())) {
                        return s.getMimeType().getContent();
                    }
                }
            }
        }
        return null;
    }

    /**
     * Gets a list of content types which the portal accepts for the response.
     * This list is ordered with the most preferable types listed first.
     * <p>
     * The content type only includes the MIME type, not the
     * character set.
     * <p>
     * Only content types that the portlet has defined in its
     * deployment descriptor are valid return values for
     * this method call. If the portlet has defined
     * <code>'*'</code> or <code>'* / *'</code> as supported content
     * types, these may also be valid return values.
     *
     * @return ordered list of MIME types for the response
     */
    public java.util.Enumeration getResponseContentTypes() {
        List types = new ArrayList();
        Portlet.Mode mode = (Portlet.Mode)getAttribute(SportletProperties.PORTLET_MODE);
        if (supports != null) {
            for (int i = 0; i < supports.length; i++) {
                Supports s = supports[i];
                org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletMode[] modes = s.getPortletMode();
                for (int j = 0; j < modes.length; j++) {
                    org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletMode m = modes[j];
                    if (m.getContent().equalsIgnoreCase(mode.toString())) {
                        types.add(s.getMimeType().getContent());
                    }
                }
            }
        }
        return new Enumerator(types);
    }

    /**
     * Returns the preferred Locale in which the portal will accept content.
     * The Locale may be based on the Accept-Language header of the client.
     *
     * @return  the prefered Locale in which the portal will accept content.
     */
    public java.util.Locale getLocale() {
        Locale locale = (Locale)this.getPortletSession(true).getAttribute(SportletProperties.LOCALE);
        if (locale != null) return locale;
        locale = this.getHttpServletRequest().getLocale();
        if (locale != null) return locale;
        return Locale.ENGLISH;
    }


    /**
     * Returns an Enumeration of Locale objects indicating, in decreasing
     * order starting with the preferred locale in which the portal will
     * accept content for this request.
     * The Locales may be based on the Accept-Language header of the client.
     *
     * @return  an Enumeration of Locales, in decreasing order, in which
     *           the portal will accept content for this request
     */
    public java.util.Enumeration getLocales() {
        return this.getHttpServletRequest().getLocales();
    }

    /**
     * Returns the name of the scheme used to make this request.
     * For example, <code>http</code>, <code>https</code>, or <code>ftp</code>.
     * Different schemes have different rules for constructing URLs,
     * as noted in RFC 1738.
     *
     * @return		a <code>String</code> containing the name
     *			of the scheme used to make this request
     */
    public String getScheme() {
        return this.getHttpServletRequest().getScheme();
    }

    /**
     * Returns the host name of the server that received the request.
     *
     * @return		a <code>String</code> containing the name
     *			of the server to which the request was sent
     */
    public String getServerName() {
        return this.getHttpServletRequest().getServerName();
    }

    /**
     * Returns the port number on which this request was received.
     *
     * @return		an integer specifying the port number
     */
    public int getServerPort() {
        return this.getHttpServletRequest().getServerPort();
    }

    private HttpServletRequest getHttpServletRequest() {
        return (HttpServletRequest)super.getRequest();
    }

}
