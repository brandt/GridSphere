/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.service.spi.impl;

import org.gridlab.gridsphere.portlet.service.PortletServiceAuthorizationException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceAuthorizer;
import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.services.core.user.impl.GridSphereUserManager;
import org.gridlab.gridsphere.services.core.security.AuthorizationException;

public class SportletServiceAuthorizer implements PortletServiceAuthorizer {

    public static final String SUPER_MESSAGE =
            "User must have super user privileges to call this method";
    public static final String ADMIN_MESSAGE =
            "User must have admin user privileges to call this method";
    public static final String SUPER_OR_SAME_MESSAGE =
            "User must have super user privileges or be the same user to call this method";

    private User user = null;
    private GridSphereUserManager userManager = null;

    public SportletServiceAuthorizer(User user, GridSphereUserManager userManager) {
        this.user = user;
        this.userManager = userManager;
    }

    /**
     * Throws AuthorizationException if supplied user not a super user.
     */
    public void authorizeSuperUser() throws PortletServiceAuthorizationException {
        if (userManager.hasSuperRole(user)) {
            throw new PortletServiceAuthorizationException(SUPER_MESSAGE);
        }
    }

    /**
     * Throws AuthorizationException if supplied user is not a super user
     * or an admin user within the specified group.
     *
     * @param PortletGroup The portlet group within which the user should
     *        be an admin if they are not a super user.
     */
    public void authorizeAdminUser(PortletGroup group) throws PortletServiceAuthorizationException {
        if (!userManager.hasAdminRoleInGroup(user, group)) {
            throw new PortletServiceAuthorizationException(ADMIN_MESSAGE);
        }
    }



}
