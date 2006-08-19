/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id: SportletData.java 5032 2006-08-17 18:15:06Z novotny $
 */
package org.gridsphere.portlet.impl;

import org.gridsphere.portlet.PortletData;
import org.gridsphere.services.core.persistence.PersistenceManagerRdbms;
import org.gridsphere.services.core.persistence.PersistenceManagerException;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * The <code>SportletData</code> contains information about the concrete portlet instance for
 * a specific <code>User</code>. It is through the data that the portlet has
 * access to the personalized data. The portlet can therefore only read the
 * personalization data. Only when the portlet is in <code>EDIT</code> mode,
 * it has write access to the personalization data.
 * <p/>
 * This implementation of <code>PortletData</code> uses Castor for Java to SQL
 * bindings
 */
public class SportletData implements PortletData {

    private PersistenceManagerRdbms pm = null;
    private String oid = null;

    /**
     * Map containing the attributes for user/portlet
     */
    private Map attributes = new HashMap();

    /**
     * The unique userid this data belongs to
     */
    private String UserID = "";

    /**
     * The PortletId this data belongs to
     */
    private String PortletID = "";

    /**
     * Default constructor cannot be instantiated
     */
    public SportletData() {
    }

    public void setPersistenceManager(PersistenceManagerRdbms pm) {
        this.pm = pm;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    /**
     * Returns the value of the attribute with the given name, or
     * <code>null</code> if no such attribute exists.
     *
     * @param name the name of the attribute
     * @return the value of the attribute
     */
    public String getAttribute(String name) {
        return (String) attributes.get(name);
    }

    /**
     * Returns an enumeration of all available attributes names.
     *
     * @return an enumeration of all available attributes names
     */
    public Enumeration getAttributeNames() {
        return new Hashtable(attributes).keys();
    }

    /**
     * Removes the attribute with the given name.
     *
     * @param name the attribute name
     */
    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    /**
     * Sets the attribute with the given name and value.
     *
     * @param name  the attribute name
     * @param value the attribute value
     */
    public void setAttribute(String name, String value) {
        attributes.put(name, value);
    }

    /**
     * Stores all attributes.
     *
     * @throws IOException store fails
     */
    public void store() throws IOException {
        try {
            pm.update(this);
        } catch (PersistenceManagerException e) {
            throw new IOException(e.getMessage());
        }
    }

    public Map getAttributes() {
        return attributes;
    }

    public void setAttributes(Map attributes) {
        this.attributes = attributes;
    }


    /**
     * Returns the user id of this portlet data
     *
     * @return the user id
     */
    public String getUserID() {
        return UserID;
    }

    /**
     * Sets the user id of this portlet data
     *
     * @param userID the concrete portlet id
     */
    public void setUserID(String userID) {
        UserID = userID;
    }

    /**
     * Returns the concrete portlet id of this portlet data
     *
     * @return the concrete portlet id
     */
    public String getPortletID() {
        return PortletID;
    }

    /**
     * Sets the concrete portlet id of this portlet data
     *
     * @param portletID the concrete portlet id
     */
    public void setPortletID(String portletID) {
        PortletID = portletID;
    }
}
