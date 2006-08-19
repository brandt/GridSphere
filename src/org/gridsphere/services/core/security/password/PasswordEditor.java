/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Jan 8, 2003
 * Time: 11:30:04 AM
 * To change template for new interface use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridsphere.services.core.security.password;

import org.gridsphere.portlet.User;

import java.util.Date;

public interface PasswordEditor extends Password {

    public void setUser(User user);

    public void setHint(String hint);

    public void setValue(String passwordValue);

    public void setLifetime(long lifetime);

    public void setDateExpires(Date expires);

    public void setDateCreated(Date created);

    public void setDateLastModified(Date date);


}
