/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services;

import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletUser;
import org.gridlab.gridsphere.services.impl.AccountRequest;

import java.util.List;

public interface UserManagerService extends PortletService {

    /**
     * Returns the modifiable SportletUser object for a given user name.
     * If none exists, an empty SportletUser is returned.
     *
     * @param userName the user
     * @return the modifiable user object
     */
    public SportletUser getUser(String userName);

    /**
     *
     */
    public void createAccountRequest(AccountRequest accountRequest);

    public void loadUser(String userName);

    public void saveUser(String userName);

    public void deleteUser(String userName);

    public boolean existsUser(String userName);

    public List getAllUsers();

    public List getActiveUsers();

}
