/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Jan 18, 2003
 * Time: 7:12:18 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.services.core.security.password;

import org.gridlab.gridsphere.portlet.User;

import java.util.Date;

public class PasswordBean implements Password {

    private User user = null;
    private long lifetime = -1;
    private Date dateExpires = null;
    private Date dateCreated = null;
    private Date dateLastModified = null;

    private transient String hint = new String();
    private transient String value = new String();
    private transient boolean validation = true;

    private transient boolean isDirty = false;

    public PasswordBean() {
    }

    public PasswordBean(User user) {
        // Save user
        this.user = user;
    }

    public PasswordBean(Password password) {
        // Get password values
        this.user = password.getUser();
        this.hint = password.getHint();
        this.dateExpires = password.getDateExpires();
        this.dateCreated = password.getDateCreated();
        this.dateLastModified = password.getDateLastModified();
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getHint() {
        return this.hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public long getLifetime() {
        return this.lifetime;
    }

    public void setLifetime(long lifetime) {
        this.lifetime = lifetime;
        resetDateExpires();
    }

    public Date getDateExpires() {
        return this.dateExpires;
    }

    public void setDateExpires(Date dateExpires) {
        this.dateExpires = dateExpires;
        resetLifetime();
    }

    public Date getDateCreated() {
        return this.dateCreated;
    }

    public Date getDateLastModified() {
        return this.dateLastModified;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
        this.isDirty = true;
    }

    public boolean isDirty() {
        return this.isDirty;
    }

    public boolean equals(String value) {
        return this.value.equals(value);
    }

    public void resetDateExpires() {
        if (this.lifetime > -1) {
            long now = (new Date()).getTime();
            this.dateExpires = new Date(now + this.lifetime);
        } else {
            this.dateExpires = null;
        }
    }

    private void resetLifetime() {
        if (this.dateExpires == null) {
            this.lifetime = -1;
        } else {
            long now = (new Date()).getTime();
            this.lifetime = dateExpires.getTime() - now;
        }
    }

    public boolean getValidation() {
        return this.validation;
    }

    public void setValidation(boolean flag) {
        this.validation = flag;
    }
}
