/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Jan 8, 2003
 * Time: 11:39:09 AM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.services.security.password.impl;

import org.gridlab.gridsphere.services.security.password.Password;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletUserImpl;
import org.gridlab.gridsphere.core.persistence.BaseObject;

import java.util.Date;

/**
 * @table userpassword
 */
public class DbmsPassword extends BaseObject implements Password {

    /**
     * @sql-name user
     * @get-method getSportletUser
     * @set-method setSportletUser
     * @required
     */
    private SportletUserImpl user;
    /**
     * @sql-size 32
     * @sql-name value
     * @required
     */
    private String value;
    /**
     * @sql-size 256
     * @sql-name hint
     */
    private String hint = new String();
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
     * @sql-name datemodified
     * @required
     */
    private Date dateLastModified = null;

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = (SportletUserImpl)user;
    }

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

    /**
     * Castor method for getting user object.
     */
    public SportletUserImpl getSportletUser() {
        return this.user;
    }

    /**
     * Castor method for setting user object.
     */
    public void setSportletUser(SportletUserImpl user) {
        this.user = user;
    }
}
