package org.gridsphere.services.core.tracker.impl;


public class TrackerAction {

    private String oid;
    private String action;
    private boolean isEnabled;

    public TrackerAction() {}

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}