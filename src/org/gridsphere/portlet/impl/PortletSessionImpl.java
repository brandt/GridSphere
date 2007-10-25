/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
package org.gridsphere.portlet.impl;

import javax.portlet.PortletContext;
import javax.portlet.PortletSession;
import javax.portlet.PortletSessionUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.Vector;


/**
 * The <CODE>PortletSession</CODE> interface provides a way to identify a user
 * across more than one request and to store transient information about that user.
 * <p/>
 * A <code>PortletSession</code> is created per user client per portlet application.
 * <p/>
 * A portlet can bind an object attribute into a <code>PortletSession</code> by name.
 * The <code>PortletSession</code> interface defines two scopes for storing objects:
 * <ul>
 * <li><code>APPLICATION_SCOPE</code>
 * <li><code>PORTLET_SCOPE</code>
 * </ul>
 * All objects stored in the session using the <code>APPLICATION_SCOPE</code>
 * must be available to all the portlets, servlets and
 * JSPs that belongs to the same portlet application and that handles a
 * request identified as being a part of the same session.
 * Objects stored in the session using the <code>PORTLET_SCOPE</code> must be
 * available to the portlet during requests for the same portlet window
 * that the objects where stored from. Attributes stored in the
 * <code>PORTLET_SCOPE</code> are not protected from other web components
 * of the portlet application. They are just conveniently namespaced.
 * <P>
 * The portlet session is based on the <code>HttpSession</code>. Therefore all
 * <code>HttpSession</code> listeners do apply to the portlet session and
 * attributes set in the portlet session are visible in the <code>HttpSession</code>
 * and vice versa.
 */
public class PortletSessionImpl implements PortletSession, HttpSession {

    private PortletContext ctx = null;
    private HttpServletRequest request = null;
    private HttpSession session = null;

    /**
     * Constructs an instance of SportletSession from a
     * <code>HttpSession</code>
     *
     * @param request the <code>PortletRequest</code> used to get ConcretePortletID
     * @param session the <code>HttpSession</code>
     */
    public PortletSessionImpl(HttpServletRequest request, HttpSession session, PortletContext ctx) {
        this.request = request;
        this.session = session;
        this.ctx = ctx;
    }


    /**
     * Returns the object bound with the specified name in this session
     * under the <code>PORTLET_SCOPE</code>, or <code>null</code> if no
     * object is bound under the name in that scope.
     *
     * @param name a string specifying the name of the object
     * @throws IllegalStateException    if this method is called on an
     *                                  invalidated session.
     * @throws IllegalArgumentException if name is <code>null</code>.
     * @return			the object with the specified name for
     * the <code>PORTLET_SCOPE</code>.
     */

    public Object getAttribute(String name) throws IllegalStateException, IllegalArgumentException {
        return getAttribute(name, PortletSession.PORTLET_SCOPE);
    }

    /**
     * Returns an <code>Enumeration</code> of String objects containing the names of
     * all the objects bound to this session under the <code>PORTLET_SCOPE</code>, or an
     * empty <code>Enumeration</code> if no attributes are available.
     *
     * @throws IllegalStateException if this method is called on an
     *                               invalidated session
     * @return			an <code>Enumeration</code> of
     * <code>String</code> objects specifying the
     * names of all the objects bound to
     * this session, or an empty <code>Enumeration</code>
     * if no attributes are available.
     */

    public java.util.Enumeration getAttributeNames() throws IllegalStateException {
        return getAttributeNames(PortletSession.PORTLET_SCOPE);
    }

    /**
     * Returns the time when this session was created, measured in
     * milliseconds since midnight January 1, 1970 GMT.
     *
     * @throws IllegalStateException if this method is called on an
     *                               invalidated session
     * @return				a <code>long</code> specifying
     * when this session was created,
     * expressed in
     * milliseconds since 1/1/1970 GMT
     */

    public long getCreationTime() throws IllegalStateException {
        return session.getCreationTime();
    }


    /**
     * Returns a string containing the unique identifier assigned to this session.
     *
     * @return				a string specifying the identifier
     * assigned to this session
     */

    public String getId() {
        return session.getId();
    }


    /**
     * Returns the last time the client sent a request associated with this session,
     * as the number of milliseconds since midnight January 1, 1970 GMT.
     * <p/>
     * <p>Actions that your portlet takes, such as getting or setting
     * a value associated with the session, do not affect the access
     * time.
     *
     * @return				a <code>long</code>
     * representing the last time
     * the client sent a request associated
     * with this session, expressed in
     * milliseconds since 1/1/1970 GMT
     */

    public long getLastAccessedTime() {
        return session.getLastAccessedTime();
    }


    /**
     * Returns the maximum time interval, in seconds, for which the portlet container
     * keeps this session open between client accesses. After this interval,
     * the portlet container invalidates the session.  The maximum time
     * interval can be set
     * with the <code>setMaxInactiveInterval</code> method.
     * A negative time indicates the session should never timeout.
     *
     * @return		an integer specifying the number of
     * seconds this session remains open
     * between client requests
     * @see		#setMaxInactiveInterval
     */

    public int getMaxInactiveInterval() {
        return session.getMaxInactiveInterval();
    }


    /**
     * Invalidates this session (all scopes) and unbinds any objects bound to it.
     * <p/>
     * Invalidating the portlet session will result in invalidating the underlying
     * <code>HttpSession</code>
     *
     * @throws IllegalStateException if this method is called on a
     *                               session which has already been invalidated
     */
    public void invalidate() throws IllegalStateException {
        session.invalidate();
    }


    /**
     * Returns true if the client does not yet know about the session or
     * if the client chooses not to join the session.
     *
     * @return <code>true</code> if the
     *         server has created a session,
     *         but the client has not joined yet.
     * @throws IllegalStateException if this method is called on a
     *                               session which has already been invalidated
     */

    public boolean isNew() throws IllegalStateException {
        return session.isNew();
    }


    /**
     * Removes the object bound with the specified name under
     * the <code>PORTLET_SCOPE</code> from
     * this session. If the session does not have an object
     * bound with the specified name, this method does nothing.
     *
     * @param name the name of the object to be
     *             removed from this session in the
     *             <code> PORTLET_SCOPE</code>.
     * @throws IllegalStateException    if this method is called on a
     *                                  session which has been invalidated
     * @throws IllegalArgumentException if name is <code>null</code>.
     */

    public void removeAttribute(String name) throws IllegalStateException, IllegalArgumentException {
        removeAttribute(name, PortletSession.PORTLET_SCOPE);
    }

    /**
     * Binds an object to this session under the <code>PORTLET_SCOPE</code>, using the name specified.
     * If an object of the same name in this scope is already bound to the session,
     * that object is replaced.
     * <p/>
     * <p>After this method has been executed, and if the new object
     * implements <code>HttpSessionBindingListener</code>,
     * the container calls
     * <code>HttpSessionBindingListener.valueBound</code>. The container then
     * notifies any <code>HttpSessionAttributeListeners</code> in the web
     * application.
     * <p>If an object was already bound to this session
     * that implements <code>HttpSessionBindingListener</code>, its
     * <code>HttpSessionBindingListener.valueUnbound</code> method is called.
     * <p/>
     * <p>If the value is <code>null</code>, this has the same effect as calling
     * <code>removeAttribute()</code>.
     *
     * @param name  the name to which the object is bound under
     *              the <code>PORTLET_SCOPE</code>;
     *              this cannot be <code>null</code>.
     * @param value the object to be bound
     * @throws IllegalStateException    if this method is called on a
     *                                  session which has been invalidated
     * @throws IllegalArgumentException if name is <code>null</code>.
     */

    public void setAttribute(String name, Object value) throws IllegalStateException, IllegalArgumentException {
        setAttribute(name, value, PortletSession.PORTLET_SCOPE);
    }


    /**
     * Binds an object to this session in the given scope, using the name specified.
     * If an object of the same name in this scope is already bound to the session,
     * that object is replaced.
     * <p/>
     * <p>After this method has been executed, and if the new object
     * implements <code>HttpSessionBindingListener</code>,
     * the container calls
     * <code>HttpSessionBindingListener.valueBound</code>. The container then
     * notifies any <code>HttpSessionAttributeListeners</code> in the web
     * application.
     * <p>If an object was already bound to this session
     * that implements <code>HttpSessionBindingListener</code>, its
     * <code>HttpSessionBindingListener.valueUnbound</code> method is called.
     * <p/>
     * <p>If the value is <code>null</code>, this has the same effect as calling
     * <code>removeAttribute()</code>.
     *
     * @param name  the name to which the object is bound;
     *              this cannot be <code>null</code>.
     * @param value the object to be bound
     * @param scope session scope of this attribute
     * @throws IllegalStateException    if this method is called on a
     *                                  session which has been invalidated
     * @throws IllegalArgumentException if name is <code>null</code>.
     */
    public void setAttribute(java.lang.String name, java.lang.Object value, int scope) throws IllegalStateException {
        if (name == null) {
            throw new IllegalArgumentException("name must not be null");
        }
        if (scope == PortletSession.APPLICATION_SCOPE) {
            session.setAttribute(name, value);
        } else {
            session.setAttribute("javax.portlet.p." + request.getAttribute(SportletProperties.PORTLET_WINDOW_ID) + "?" + name, value);
        }
    }

    /**
     * Returns the object bound with the specified name in this session,
     * or <code>null</code> if no object is bound under the name in the given scope.
     *
     * @param name  a string specifying the name of the object
     * @param scope session scope of this attribute
     * @throws IllegalStateException    if this method is called on an
     *                                  invalidated session
     * @throws IllegalArgumentException if name is <code>null</code>.
     * @return			the object with the specified name
     */
    public java.lang.Object getAttribute(String name, int scope) throws java.lang.IllegalStateException {
        if (name == null) {
            throw new IllegalArgumentException("name must not be null");
        }
        if (scope == PortletSession.APPLICATION_SCOPE) {
            return session.getAttribute(name);
        } else {
            Object attribute = session.getAttribute("javax.portlet.p." + (String) request.getAttribute(SportletProperties.PORTLET_WINDOW_ID) + "?" + name);
            if (attribute == null) {
                // not sure, if this should be done for all attributes or only javax.servlet.
                attribute = session.getAttribute(name);
            }
            return attribute;
        }
    }

    /**
     * Returns an <code>Enumeration</code> of String objects containing the names of
     * all the objects bound to this session in the given scope, or an
     * empty <code>Enumeration</code> if no attributes are available in the
     * given scope.
     *
     * @param scope session scope of the attribute names
     * @throws IllegalStateException if this method is called on an
     *                               invalidated session
     * @return			an <code>Enumeration</code> of
     * <code>String</code> objects specifying the
     * names of all the objects bound to
     * this session, or an empty <code>Enumeration</code>
     * if no attributes are available in the given scope.
     */
    public java.util.Enumeration getAttributeNames(int scope) {
        if (scope == PortletSession.APPLICATION_SCOPE) {
            return session.getAttributeNames();
        } else {
            Enumeration attributes = session.getAttributeNames();

            Vector portletAttributes = new Vector();

            /* Fix that ONLY attributes of PORTLET_SCOPE are returned. */
            int prefix_length = "javax.portlet.p.".length();
            String wId = (String) request.getAttribute(SportletProperties.PORTLET_WINDOW_ID);

            while (attributes.hasMoreElements()) {
                String attribute = (String) attributes.nextElement();

                int attributeScope = PortletSessionUtil.decodeScope(attribute);

                if (attributeScope == PortletSession.PORTLET_SCOPE && attribute.startsWith(wId, prefix_length)) {
                    String portletAttribute = PortletSessionUtil.decodeAttributeName(attribute);

                    if (portletAttribute != null) { // it is in the portlet's namespace
                        portletAttributes.add(portletAttribute);
                    }
                }
            }

            return portletAttributes.elements();
        }
    }

    /**
     * Removes the object bound with the specified name and the given scope from
     * this session. If the session does not have an object
     * bound with the specified name, this method does nothing.
     *
     * @param name  the name of the object to be
     *              removed from this session
     * @param scope session scope of this attribute
     * @throws IllegalStateException    if this method is called on a
     *                                  session which has been invalidated
     * @throws IllegalArgumentException if name is <code>null</code>.
     */
    public void removeAttribute(String name, int scope) throws java.lang.IllegalStateException {
        if (name == null) {
            throw new IllegalArgumentException("name must not be null");
        }
        if (scope == PortletSession.APPLICATION_SCOPE) {
            session.removeAttribute(name);
        } else {
            session.removeAttribute("javax.portlet.p." + (String) request.getAttribute(SportletProperties.PORTLET_WINDOW_ID) + "?" + name);
        }
    }


    /**
     * Specifies the time, in seconds, between client requests, before the
     * portlet container invalidates this session. A negative time
     * indicates the session should never timeout.
     *
     * @param interval An integer specifying the number
     *                 of seconds
     */

    public void setMaxInactiveInterval(int interval) {
        session.setMaxInactiveInterval(interval);
    }


    /**
     * Returns the portlet application context associated with this session.
     *
     * @return the portlet application context
     */

    public PortletContext getPortletContext() {
        return ctx;
    }

    // javax.servlet.http.HttpSession implementation ----------------------------------------------
    public javax.servlet.ServletContext getServletContext() {
        // TBD, open issue. it would be good if we could also implement the ServletContext interface at the PortletContextImpl
        return session.getServletContext();
    }

    public javax.servlet.http.HttpSessionContext getSessionContext() {
        return session.getSessionContext();
    }

    public Object getValue(String name) {
        return this.getAttribute(name, PORTLET_SCOPE);
    }

    public String[] getValueNames() {
        // TBD
        return null;
    }

    public void putValue(String name, Object value) {
        this.setAttribute(name, value, PORTLET_SCOPE);
    }

    public void removeValue(String name) {
        this.removeAttribute(name, PORTLET_SCOPE);
    }
}


