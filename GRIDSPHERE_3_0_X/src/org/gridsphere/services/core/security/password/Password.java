/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Jan 8, 2003
 * Time: 11:30:04 AM
 * To change template for new interface use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridsphere.services.core.security.password;

import org.gridsphere.services.core.user.User;

import java.util.Date;

public interface Password {

    public User getUser();

    public String getHint();

    public String getValue();

    public long getLifetime();

    public Date getDateExpires();

    public Date getDateCreated();

    public Date getDateLastModified();

}
