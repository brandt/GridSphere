/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.impl;

import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletSession;
import org.gridlab.gridsphere.portlet.PortletSettings;

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

    private PortletRequest request = null;
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

    /**
     * Constructs an instance of SportletSession from a
     * <code>HttpSession</code>
     *
     * @param request the <code>PortletRequest</code> used to get ConcretePortletID
     * @param session the <code>HttpSession</code>
     */
    public SportletSession(PortletRequest request, HttpSession session) {
	    this.request = request;
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

    /**
     * Tries to get ConcretePortletID. If request or PortletSettings
     * are not available, returns empty string.
     */
    private String getCpid() {
        if(request == null) return "";
        PortletSettings ps = request.getPortletSettings();
        if(ps == null) return "";
        return ps.getConcretePortletID()+":";
    }

    /**
     * Associates an attribute with the given name and value with this session.
     * All attribute names are prefixed with ConcretePortletID to prevent
     * namespace conflicts.
     */
    public void setAttribute(String name, Object value) {
        session.setAttribute(getCpid()+name, value);
    }

    /**
     * Returns the value of the attribute with the given name, or null if no attribute with the given name exists. Uses ConcretePortletID prefix.
     */
    public Object getAttribute(String name) {
        return session.getAttribute(getCpid()+name);
    }

    /**
     * Returns an enumeration of names of all attributes available to this session. Names are striped of ConcretePortletID prefix.
     */
    public Enumeration getAttributeNames() {
        if(request==null) return session.getAttributeNames();
        String cpid = getCpid();
        if("".equals(cpid))  return session.getAttributeNames();
        Vector s = new Vector();
        for(Enumeration en = session.getAttributeNames();en.hasMoreElements();) {
            String name = (String) en.nextElement();
            if(name.startsWith(cpid)) {
                s.add(name.substring(cpid.length()));
            }
        }
        return s.elements();
    }

    /**
     * Removes the attribute with the given name. Uses ConcretePortletID prefix.
     */
    public void removeAttribute(String name) {
        session.removeAttribute(getCpid()+name);
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
