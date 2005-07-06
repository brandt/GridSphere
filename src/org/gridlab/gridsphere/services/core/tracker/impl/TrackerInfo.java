package org.gridlab.gridsphere.services.core.tracker.impl;


public class TrackerInfo {

    private String oid;

    private String label;
    private String userAgent;
    private String userOid;
    private long date;

    public TrackerInfo() {}

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getUserOid() {
        return userOid;
    }

    public void setUserOid(String userOid) {
        this.userOid = userOid;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}