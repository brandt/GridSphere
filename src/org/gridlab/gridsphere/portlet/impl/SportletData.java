/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.impl;

import org.gridlab.gridsphere.portlet.AccessDeniedException;
import org.gridlab.gridsphere.portlet.Client;
import org.gridlab.gridsphere.portlet.PortletData;
import org.gridlab.gridsphere.portletcontainer.descriptor.ConcretePortletApplication;
import org.gridlab.gridsphere.core.persistence.BaseObject;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.core.persistence.castor.PersistenceManagerRdbms;
import org.exolab.castor.jdo.Database;
import org.exolab.castor.jdo.PersistenceException;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Vector;
import java.io.IOException;

/**
 * The PortletData contains information about the concrete portlet instance.
 * Also, it is through the data that the portlet has access to the personalized data.
 * The portlet can therefore only read the personalization data.
 * Only when the portlet is in EDIT mode, it has write access to the personalization data.
 */

/**
 * @table sportletdata
 */

public class SportletData extends BaseObject implements PortletData  {

    protected transient Hashtable store = new Hashtable();
    private transient PersistenceManagerRdbms pm = PersistenceManagerRdbms.getInstance();


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

    public SportletData() {};


    /**
     * Returns the value of the attribute with the given name, or null if no such attribute exists.
     *
     * @param name the name of the attribute
     * @return the value of the attribute
     */
    public String getAttribute(String name) {
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
     *
     * @throws AccessDeniedException if the caller isn't authorized to access this data object
     */
    public void removeAttribute(String name) {
        store.remove(name);
    }

    /**
     * Sets the attribute with the given name and value.
     *
     * @param name the attribute name
     * @param value the attribute value
     *
     * @throws AccessDeniedException if the caller isn't authorized to access this data object
     */
    public void setAttribute(String name, String value)  {
        store.put(name, value);
    }

    /**
     * Stores all attributes.
     *
     * @throws PersistenceManagerException store fails
     */
    public void store() throws PersistenceManagerException {
        pm.update(this);
    }


    private void convert2vector() {
        Enumeration allkeys = store.keys();
        SportletDataAttribute ha = null;
        while (allkeys.hasMoreElements()) {
            String key = (String) allkeys.nextElement();
            ha = new SportletDataAttribute(key, (String) store.get(key));
            ha.setSportletData(this);
            Attributes.add(ha);
        }
    }

    private void convert2hash() {
        for (int i = 0; i < Attributes.size(); i++) {
            SportletDataAttribute ha = (SportletDataAttribute) Attributes.get(i);
            store.put((String) ha.getKey(), (String) ha.getValue());
        }
    }

    public Vector getAttributes() {
        return Attributes;
    }

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

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getPortletID() {
        return PortletID;
    }

    public void setPortletID(String portletID) {
        PortletID = portletID;
    }
}
