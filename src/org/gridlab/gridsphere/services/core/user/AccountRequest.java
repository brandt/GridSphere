/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.user;

import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.portlet.PortletRole;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletUser;
import org.gridlab.gridsphere.services.core.security.acl.GroupRequest;
import org.gridlab.gridsphere.services.security.password.PasswordBean;

import java.util.List;
import java.util.Date;

public interface AccountRequest extends SportletUser {

    /*** Password methods ***/

    public PasswordBean getPassword();

    public String getPasswordValue();

    public void setPasswordValue(String value);

    public String getPasswordHint();

    public void setPasswordHint(String value);

    public Date getPasswordDateExpires();

    public void setPasswordDateExpires(Date date);

    public long getPasswordLifetime();

    public void setPasswordLifetime(long lifetime);

    public boolean getPasswordValidation();

    public void setPasswordValidation(boolean flag);

    public boolean getPasswordHasChanged();

    /*** ACL methods ***/

    public void addToGroup(PortletGroup group, PortletRole role);

    /*** Other methods ***/

    public boolean isNewUser();

    public String toString();
}
