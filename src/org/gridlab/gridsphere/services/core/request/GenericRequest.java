/*
 * @version: $Id$
 */
package org.gridlab.gridsphere.services.core.request;

import java.util.Date;

public class GenericRequest {

    private String oid = null;
    private String userID = "";
    private String label = "";
    private Date lifetime = null;

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
}