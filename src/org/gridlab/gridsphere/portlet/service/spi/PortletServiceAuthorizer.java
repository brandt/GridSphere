/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.service.spi;

import org.gridlab.gridsphere.portlet.service.PortletServiceAuthorizationException;
import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.portlet.User;

public interface PortletServiceAuthorizer {

    /**
     * Throws AuthorizationException if supplied user not a super user.
     */
    public void authorizeSuperUser() throws PortletServiceAuthorizationException;

    /**
     * Throws AuthorizationException if supplied user is not a super user
     * or an admin user within the specified group.
     *
     * @param PortletGroup The portlet group within which the user should
     *        be an admin if they are not a super user.
     */
    public void authorizeAdminUser(PortletGroup group) throws PortletServiceAuthorizationException;

}
