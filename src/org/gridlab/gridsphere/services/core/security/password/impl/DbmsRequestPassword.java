/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Jan 31, 2003
 * Time: 12:04:46 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.services.core.security.password.impl;

import org.gridlab.gridsphere.services.core.user.impl.AccountRequestImpl;
import org.gridlab.gridsphere.services.core.security.password.Password;
import org.gridlab.gridsphere.core.persistence.BaseObject;
import org.gridlab.gridsphere.portlet.User;

import java.util.Date;

/**
 * @table requestpassword
 */
public class DbmsRequestPassword extends BaseObject implements DbmsPassword {

    /**
     * @sql-name user
     * @get-method getAccountRequest
     * @set-method setAccountRequest
     * @required
     */
    private AccountRequestImpl user;
    /**
     * @sql-size 32
     * @sql-name value
     * @required
     */
    private String value = new String();
    /**
     * @sql-size 256
     * @sql-name hint
     */
    private String hint = new String();
    /**
     * @sql-name lifetime
     * @required
     */
    private long lifetime = -1;
    /**
     * @sql-name dateexpires
     */
    private Date dateExpires = null;
    /**
     * @sql-name datecreated
     * @required
     */
    private Date dateCreated = null;
    /**
     * @sql-name datelastmodified
     * @required
     */
    private Date dateLastModified = null;

    public String getHint() {
        return this.hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getLifetime() {
        return this.lifetime;
    }

    public void setLifetime(long lifetime) {
        this.lifetime = lifetime;
    }


    public Date getDateExpires() {
        return this.dateExpires;
    }

    public void setDateExpires(Date dateExpires) {
        this.dateExpires = dateExpires;
    }

    public Date getDateCreated() {
        return this.dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateLastModified() {
        return this.dateLastModified;
    }

    public void setDateLastModified(Date dateModified) {
        this.dateLastModified = dateModified;
    }

    public boolean equals(String value) {
        return this.value.equals(value);
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = (AccountRequestImpl)user;
    }

    /**
     * Castor method for getting user object.
     */
    public AccountRequestImpl getAccountRequest() {
        return this.user;
    }

    /**
     * Castor method for setting user object.
     */
    public void setAccountRequest(AccountRequestImpl user) {
        this.user = user;
    }
}

