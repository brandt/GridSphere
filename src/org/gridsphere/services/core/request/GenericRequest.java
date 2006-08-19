/*
 * @version: $Id: GenericRequest.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.services.core.request;

import java.util.*;

public class GenericRequest {

    private String oid = null;
    private String userID = "";
    private String label = "";
    private Date lifetime = null;
    private Map attributes = new HashMap();

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Date getLifetime() {
        return lifetime;
    }

    public void setLifetime(Date lifetime) {
        this.lifetime = lifetime;
    }

    public Map getAttributes() {
        return attributes;
    }

    public void setAttributes(Map attributes) {
        this.attributes = attributes;
    }

    /**
     * Returns the value of the attribute with the given name,s
     * or null if no attribute with the given name exists.
     *
     * @param name the attribute name
     * @return the attribute value
     */
    public String getAttribute(String name) {
        return (String)attributes.get(name);
    }

    /**
     * Sets the value of the attribute with the given name,
     *
     * @param name  the attribute name
     * @param value the attribute value
     */
    public void setAttribute(String name, String value) {
        attributes.put(name, value);
    }


}