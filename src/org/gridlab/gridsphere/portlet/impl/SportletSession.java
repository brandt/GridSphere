/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.impl;

import org.gridlab.gridsphere.portlet.PortletSession;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.Enumeration;


/**
 * The PortletSession holds the user-specific data that the portlet needs to
 * personalize the one global portlet instance. Together with the portlet,
 * the portlet session constitutes the concrete portlet instance.
 */
public class SportletSession implements PortletSession {

    private HttpSession session = null;

    public SportletSession(HttpSession session) {
        this.session = session;
    }

    /**
     * Returns the point of time that this session was created.
     * Essentially, this will also be the time when the user logged in.
     * The time is returned as the number of milliseconds since January 1, 1970 GMT.
     *
     * @return the time of creation
     */
    public long getCreationTime() {
        return session.getCreationTime();
    }

    public String getId() {
        return session.getId();
    }

    /**
     * Returns the point of time that this session was last accessed.
     * The time is returned as the number of milliseconds since January 1, 1970 GMT.
     *
     * @return the time of the last access
     */
    public final long getLastAccessedTime() {
        return session.getLastAccessedTime();
    }


    public final ServletContext getServletContext() {
        return null;
    }


    public final void setMaxInactiveInterval(int interval) {
        session.setMaxInactiveInterval(interval);
    }

    public final int getMaxInactiveInterval() {
        return session.getMaxInactiveInterval();
    }

    /**
     *
     * @deprecated       As of Version 2.1, this method is
     *                   deprecated and has no replacement.
     *                   It will be removed in a future
     *                   version of the Java Servlet API.
     *
     */
    public HttpSessionContext getSessionContext() {
        return session.getSessionContext();
    }

    /**
     * Associates an attribute with the given name and value with this session.
     * If a portlet needs to communicate information to embedded servlets or JSP,
     * this methods can used carry the information along.
     *
     * The portlet provider should take care that the the namespace of attribute
     * names is not unnecessarily polluted. It is recommended to prefix all attributes
     * the package and class name of the portlet that makes use of this method.
     *
     * @param name the attribute name
     * @param value the attribute value
     */
    public void setAttribute(String name, Object value) {
        session.setAttribute(name, value);
    }

    /**
     * Returns the value of the attribute with the given name,
     * or null if no attribute with the given name exists.
     *
     * @param name the attribute name
     * @return the attribute value
     */
    public Object getAttribute(String name) {
        return session.getAttribute(name);
    }

    /**
     * Returns an enumeration of names of all attributes available to this session.
     * This method returns an empty enumeration if the session has no attributes available to it.
     *
     * @return an enumeration of attribute names
     */
    public Enumeration getAttributeNames() {
        return session.getAttributeNames();
    }

    /**
     * Removes the attribute with the given name.
     *
     * @param name the name of attribute to be removed
     */
    public void removeAttribute(String name) {
        session.removeAttribute(name);
    }

    /**
     *
     * @deprecated      As of Version 2.2, this method is
     *                  replaced by {@link #getAttribute}.
     *
     *
     * @return                  the object with the specified name
     *
     * @exception IllegalStateException if this method is called on an
     *                                  invalidated session
     *
     */
    public final Object getValue(String name) {
        return (Object) session.getValue(name);
    }

    /**
     *
     * @deprecated      As of Version 2.2, this method is
     *                  replaced by {@link #getAttributeNames}
     *
     * @return                          an array of <code>String</code>
     *                                  objects specifying the
     *                                  names of all the objects bound to
     *                                  this session
     *
     * @exception IllegalStateException if this method is called on an
     *                                  invalidated session
     */
    public final String[] getValueNames() {
        return session.getValueNames();
    }

    /**
     *
     * @deprecated      As of Version 2.2, this method is
     *                  replaced by {@link #setAttribute}
     *
     * @param name                      the name to which the object is bound;
     *                                  cannot be null
     *
     * @param value                     the object to be bound; cannot be null
     *
     * @exception IllegalStateException if this method is called on an
     *                                  invalidated session
     *
     */
    public final void putValue(String name, Object value) {
        session.putValue(name, value);
    }

    /**
     *
     * @deprecated      As of Version 2.2, this method is
     *                  replaced by {@link #removeAttribute}
     *
     * @param name                              the name of the object to
     *                                          remove from this session
     *
     * @exception IllegalStateException if this method is called on an
     *                                  invalidated session
     */
    public final void removeValue(String name) {
        session.removeValue(name);
    }

    public void invalidate() {
        session.invalidate();
    }

    public boolean isNew() {
        return session.isNew();
    }

}
