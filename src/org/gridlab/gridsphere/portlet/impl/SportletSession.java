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
        return null;
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

    public void setAttribute(String name, Object value) {
        session.setAttribute(name, value);
    }

    public Object getAttribute(String name) {
        return session.getAttribute(name);
    }

    public Enumeration getAttributeNames() {
        return session.getAttributeNames();
    }

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
