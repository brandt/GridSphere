/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.impl;

import org.exolab.castor.jdo.Database;
import org.gridlab.gridsphere.core.persistence.BaseObject;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerRdbms;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerFactory;
import org.gridlab.gridsphere.portlet.PortletData;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * The <code>SportletData</code> contains information about the concrete portlet instance for
 * a specific <code>User</code>. It is through the data that the portlet has
 * access to the personalized data. The portlet can therefore only read the
 * personalization data. Only when the portlet is in <code>EDIT</code> mode,
 * it has write access to the personalization data.
 * <p>
 * This implementation of <code>PortletData</code> uses Castor for Java to SQL
 * bindings
 *
 * @table sportletdata
 */
public class SportletData extends BaseObject implements PortletData {

    protected transient Hashtable store = new Hashtable();
    private transient PersistenceManagerRdbms pm = PersistenceManagerFactory.createGridSphereRdbms();

    /**
     * Saves the hashtable (for castor)
     *
     * @field-type SportletDataAttribute
     * @many-key sportletdata
     */
    private Vector Attributes = new Vector();

    /**
     * The unique userid this data belongs to
     *
     * @sql-size 255
     * @sql-name userid
     */
    private String UserID = new String();

    /**
     * The PortletId this data belongs to
     *
     * @sql-size 255
     * @sql-name portletid
     */
    private String PortletID = new String();

    /**
     * Constructs an instance of SportletData that uses the GridSphere database
     */
    public SportletData() {
        super();
    }

    /**
     * Constructs an instance of SportletData that uses the GridSphere database
     */
    public SportletData(PersistenceManagerRdbms pm) {
        super();
        this.pm = pm;
    }

    /**
     * Returns the value of the attribute with the given name, or
     * <code>null</code> if no such attribute exists.
     *
     * @param name the name of the attribute
     * @return the value of the attribute
     */
    public String getAttribute(String name) {
        System.out.println("========================== TRY TO GET ATTR: " + name);
        System.out.println("STORESIZE: " + store.size());
        return (String) store.get(name);
    }

    /**
     * Returns an enumeration of all available attributes names.
     *
     * @return an enumeration of all available attributes names
     */
    public Enumeration getAttributeNames() {
        return store.keys();
    }

    /**
     * Removes the attribute with the given name.
     *
     * @param name the attribute name
     */
    public void removeAttribute(String name) {
        store.remove(name);
    }

    /**
     * Sets the attribute with the given name and value.
     *
     * @param name the attribute name
     * @param value the attribute value
     */
    public void setAttribute(String name, String value) {
        System.out.println(" ========================== PUT ATTRR :" + name + " VALUE " + value);
        store.put(name, value);
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

    private void convert2vector() {
        Enumeration allkeys = store.keys();
        SportletDataAttribute ha = null;
        while (allkeys.hasMoreElements()) {
            String key = (String) allkeys.nextElement();
            ha = new SportletDataAttribute(key, (String) store.get(key));
            ha.setPortletData(this);
            Attributes.add(ha);
        }
    }

    private void convert2hash() {
        for (int i = 0; i < Attributes.size(); i++) {
            SportletDataAttribute ha = (SportletDataAttribute) Attributes.get(i);
            store.put(ha.getKey(), ha.getValue());
        }
    }

    /**
     * Returns the attributes
     *
     * @return the attributes
     */
    public Vector getAttributes() {
        return Attributes;
    }

    /**
     *
     */
    public void setAttributes(Vector attributes) {
        Attributes = attributes;
    }

    public void jdoBeforeCreate(Database database) throws Exception {
        //super.jdoBeforeCreate(database);
        convert2vector();
    }

    public Class jdoLoad(short i) throws Exception {
        convert2hash();
        return super.jdoLoad(i);
    }

    public void jdoUpdate() {
        convert2vector();
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
