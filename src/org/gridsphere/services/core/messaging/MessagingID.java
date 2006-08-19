package org.gridsphere.services.core.messaging;

/**
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id: MessagingID.java 4496 2006-02-08 20:27:04Z wehrens $
 */
public class MessagingID {

    private String oid = null;
    private String serviceid = "";
    private String serviceuserid = "";
    private String username = "";

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getServiceid() {
        return serviceid;
    }

    public void setServiceid(String serviceid) {
        this.serviceid = serviceid;
    }

    public String getServiceuserid() {
        return serviceuserid;
    }

    public void setServiceuserid(String serviceuserid) {
        this.serviceuserid = serviceuserid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
