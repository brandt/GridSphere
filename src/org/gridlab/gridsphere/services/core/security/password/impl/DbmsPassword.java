/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Jan 8, 2003
 * Time: 11:39:09 AM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.services.core.security.password.impl;

import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.services.core.security.password.Password;

import java.util.Date;

public interface DbmsPassword extends Password  {

    public void setUser(User user);

    public void setHint(String hint);

    public void setValue(String value);

    public void setLifetime(long lifetime);

    public void setDateExpires(Date dateExpires);

    public void setDateCreated(Date dateCreated);

    public void setDateLastModified(Date dateModified);
}
