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
            "The user calling this method must have super privileges";
    public static final String ADMIN_MESSAGE =
            "The user calling this method must have admin privileges within this group";
    public static final String SUPER_OR_ADMIN_MESSAGE =
            "The user calling this method must have super privileges or admin privileges within this group";
    public static final String SUPER_OR_SAME_MESSAGE =
            "The user calling this method must have super privileges or be the same user given to this method";
    public static final String SUPER_ADMIN_OR_SAME_MESSAGE =
            "The user calling this method must have super privileges, admin privileges within this group, or be the same user given to this method";

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
        if (!userManager.hasSuperRole(user)) {
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

    /**
     * Throws AuthorizationException if supplied user is not a super user
     * or an admin user within the specified group.
     *
     * @param PortletGroup The portlet group within which the user should
     *        be an admin if they are not a super user.
     */
    public void authorizeSuperOrAdminUser(PortletGroup group)
            throws AuthorizationException {
        if (!userManager.hasSuperRole(this.user) &&
            !userManager.hasAdminRoleInGroup(this.user, group)) {
            throw new PortletServiceAuthorizationException(SUPER_OR_ADMIN_MESSAGE);
        }
    }

    /**
     * Throws AuthorizationException if supplied user is not a super user
     * or an admin user within the specified group.
     *
     * @param PortletGroup The portlet group within which the user should
     *        be an admin if they are not a super user.
     */
    public void authorizeSuperOrSameUser(User user)
            throws AuthorizationException {
        if (!userManager.hasSuperRole(this.user) &&
            !this.user.equals(user)) {
            throw new PortletServiceAuthorizationException(SUPER_OR_SAME_MESSAGE);
        }
    }

    /**
     * Throws AuthorizationException if supplied user not a super user
     * or not the same user as specified in this method.
     *
     * @param User The user this supplied user should be equal to if
     *        if the supplied user is not a super user.
     */
    public void authorizeSuperAdminOrSameUser(User user, PortletGroup group)
             throws AuthorizationException {
        if (!userManager.hasSuperRole(this.user) &&
            !userManager.hasAdminRoleInGroup(this.user, group) &&
            !this.user.equals(user)) {
            throw new PortletServiceAuthorizationException(SUPER_ADMIN_OR_SAME_MESSAGE);
        }
    }

}
