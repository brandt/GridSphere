/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.service.spi.impl;

import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.service.PortletServiceAuthorizationException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceAuthorizer;
import org.gridlab.gridsphere.services.core.security.auth.AuthorizationException;
import org.gridlab.gridsphere.services.core.security.acl.impl.AccessControlManager;

/**
 * The <code>SportletServiceAuthorizer</code> provides an implementation of
 * the <code>PortletServiceAuthorizer</code> using the internal
 * <code>GridSphereUserManager</code> for access control logic.
 */
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
    private AccessControlManager aclManager = null;

    /**
     * Constructor disallows non-argument instantiation
     */
    private SportletServiceAuthorizer() {
    }

    /**
     * Constructs an instance of SportletServiceAuthorizer from a supplied User
     * and GridSphereUserManager
     *
     * @param user the supplied <code>User</code>
     * @param userManager an instance of <code>GridSphereUserManager</code>
     */
    public SportletServiceAuthorizer(User user, AccessControlManager userManager) {
        this.user = user;
        this.aclManager = userManager;
    }

    /**
     * Authorizes operations that require super user access
     *
     * @throws PortletServiceAuthorizationException if supplied user is not a super user
     */
    public void authorizeSuperUser() throws PortletServiceAuthorizationException {
        if (!aclManager.hasSuperRole(user)) {
            throw new PortletServiceAuthorizationException(SUPER_MESSAGE);
        }
    }

    /**
     *  Authorizes operations that require admin user access
     *
     * @param group the <code>PortletGroup</code> within which the
     * user should be an admin if they are not a super user.
     * @throws PortletServiceAuthorizationException if supplied user is not an admin user
     */
    public void authorizeAdminUser(PortletGroup group) throws PortletServiceAuthorizationException {
        if (!aclManager.hasAdminRoleInGroup(user, group)) {
            throw new PortletServiceAuthorizationException(ADMIN_MESSAGE);
        }
    }

    /**
     * Authorizes operations that require super or admin user access
     *
     * @param group the <code>PortletGroup</code> within which the
     * user should be an admin if they are not a super user
     * @throws PortletServiceAuthorizationException if supplied user is not a super or admin user
     */
    public void authorizeSuperOrAdminUser(PortletGroup group)
            throws AuthorizationException {
        if (!aclManager.hasSuperRole(this.user) &&
                !aclManager.hasAdminRoleInGroup(this.user, group)) {
            throw new PortletServiceAuthorizationException(SUPER_OR_ADMIN_MESSAGE);
        }
    }

    /**
     * Authorizes operations that require either a super user or can be invoked
     * only if the supplied user  matches the associated user
     *
     * @param user the <code>User</code> which should be an admin if they are not a super user
     * @throws PortletServiceAuthorizationException if supplied user is
     * not a super or same user
     */
    public void authorizeSuperOrSameUser(User user)
            throws AuthorizationException {
        if (!aclManager.hasSuperRole(this.user) &&
                !this.user.equals(user)) {
            throw new PortletServiceAuthorizationException(SUPER_OR_SAME_MESSAGE);
        }
    }

    /**
     * Authorizes operations that require either a super user, or admin user, or
     * can be invoked only if the supplied user  matches the associated user
     *
     * @param user this <code>User</code>
     * @param group the <code>PortletGroup</code> within which the
     * user should be an admin if they are not a super user
     * @throws PortletServiceAuthorizationException if supplied user is
     * not a super, admin, or same  user
     */
    public void authorizeSuperAdminOrSameUser(User user, PortletGroup group)
            throws AuthorizationException {
        if (!aclManager.hasSuperRole(this.user) &&
                !aclManager.hasAdminRoleInGroup(this.user, group) &&
                !this.user.equals(user)) {
            throw new PortletServiceAuthorizationException(SUPER_ADMIN_OR_SAME_MESSAGE);
        }
    }

}
