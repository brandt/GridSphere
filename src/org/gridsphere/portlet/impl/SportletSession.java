/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: SportletSession.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.portlet.impl;

import org.gridsphere.portlet.PortletRequest;
import org.gridsphere.portlet.PortletSession;
import org.gridsphere.portlet.PortletSettings;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.Enumeration;
import java.util.Vector;


/**
 * A <code>SportletSession</code> provides an implementation of the
 * <code>PortletSession</code> by following the decorator patterm.
 * A HttpSesionobject is used in composition to perform all of
 * the required methods.
 */
public class SportletSession implements PortletSession {

    private HttpSession session = null;

    /**
     * Constructs an instance of SportletSession from a
     * <code>HttpSession</code>
     *
     * @param session the <code>HttpSession</code>
     */
    public SportletSession(HttpSession session) {
        this.session = session;
    }

    public long getCreationTime() {
        return session.getCreationTime();
    }

    public String getId() {
        return session.getId();
    }

    public final long getLastAccessedTime() {
        return session.getLastAccessedTime();
    }

    public final ServletContext getServletContext() {
        return session.getServletContext();
    }

    public final void setMaxInactiveInterval(int interval) {
        session.setMaxInactiveInterval(interval);
    }

    public final int getMaxInactiveInterval() {
        return session.getMaxInactiveInterval();
    }

    public HttpSessionContext getSessionContext() {
        return session.getSessionContext();
    }

    /**
     * Associates an attribute with the given name and value with this session.
     */
    public void setAttribute(String name, Object value) {
        session.setAttribute(name, value);
    }

    /**
     * Returns the value of the attribute with the given name, or null if no attribute with the given name exists.
     */
    public Object getAttribute(String name) {
        return session.getAttribute(name);
    }

    /**
     * Returns an enumeration of names of all attributes available to this session.
     */
    public Enumeration getAttributeNames() {
        return session.getAttributeNames();
    }

    /**
     * Removes the attribute with the given name.
     */
    public void removeAttribute(String name) {
        session.removeAttribute(name);
    }

    public final Object getValue(String name) {
        return session.getValue(name);
    }

    public final String[] getValueNames() {
        return session.getValueNames();
    }

    public final void putValue(String name, Object value) {
        session.putValue(name, value);
    }

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
