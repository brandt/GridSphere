/*
 * @version: $Id$
 */
package org.gridlab.gridsphere.services.core.request;

public class GenericRequest {

    private String oid = null;
    private String userID = "";

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

}