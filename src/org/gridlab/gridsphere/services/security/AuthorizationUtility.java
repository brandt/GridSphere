/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Jan 14, 2003
 * Time: 6:47:16 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.services.security;

import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.services.user.UserManagerService;

/**
 * This utility class provides useful methods for authorizing users to call methods.
 * It must be instantiated with a user object so that authorization can be performed.
 */
public class AuthorizationUtility {

    public static final String SUPER_ONLY_MESSAGE =
        "User must have super user privileges to call this method";
    public static final String SUPER_OR_ADMIN_MESSAGE =
        "User must have super user privileges or be the same user to call this method";
    public static final String SUPER_OR_SAME_MESSAGE =
        "User must have super user privileges or be the same user to call this method";
    public static final String NULL_USER_MESSAGE =
        "User is null! Cannot call this method.";

    private static PortletLog _log = SportletLog.getInstance(AuthorizationUtility.class);
    private static UserManagerService _userManager = null;
    private User user = null;

    static {
        // Get instance of service factory
        PortletServiceFactory factory = SportletServiceFactory.getInstance();
        // Instantiate helper services
        try {
            _userManager
                = (UserManagerService)
                    factory.createPortletService(UserManagerService.class, null, true);
        } catch (Exception e) {
            _log.error("Unable to initialize services: ", e);
        }
    }

    public AuthorizationUtility(User user) {
        this.user = user;
    }

    /**
     * Throws AuthorizationException if supplied user not a super user.
     */
    public void authorizeSuperUser()
            throws AuthorizationException {
        if (this.user == null) {
            throw new AuthorizationException(NULL_USER_MESSAGE);
        }
        if (_userManager.isSuperUser(this.user)) {
            throw new AuthorizationException(SUPER_ONLY_MESSAGE);
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
        if (this.user == null) {
            throw new AuthorizationException(NULL_USER_MESSAGE);
        }
        if (_userManager.isSuperUser(this.user) || _userManager.isAdminUser(this.user, group)) {
            throw new AuthorizationException(SUPER_OR_ADMIN_MESSAGE);
        }
    }

    /**
     * Throws AuthorizationException if supplied user not a super user
     * or not the same user as specified in this method.
     *
     * @param User The user this supplied user should be equal to if
     *        if the supplied user is not a super user.
     */
    public void authorizeSuperOrSameUser(User user)
             throws AuthorizationException {
        if (this.user == null || user == null) {
            throw new AuthorizationException(NULL_USER_MESSAGE);
        }
        if (_userManager.isSuperUser(this.user) || this.user.equals(user)) {
            throw new AuthorizationException(SUPER_OR_SAME_MESSAGE);
        }
    }
}
