/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Jan 14, 2003
 * Time: 6:47:16 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.services.core.security;

import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.services.core.security.acl.AccessControlManagerService;
import org.gridlab.gridsphere.services.core.user.UserManagerService;

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
    private static AccessControlManagerService _aclManagerService = null;
    private Class caller = null;
    private User user = null;

    static {
        // Get instance of service factory
        PortletServiceFactory factory = SportletServiceFactory.getInstance();
        // Instantiate helper services
        try {
            _userManager
                = (UserManagerService)
                    factory.createPortletService(UserManagerService.class, null, true);
            _aclManagerService
                = (AccessControlManagerService)
                    factory.createPortletService(AccessControlManagerService.class, null, true);
        } catch (Exception e) {
            _log.error("Unable to initialize services: ", e);
        }
    }

    public AuthorizationUtility(User user) {
        this.user = user;
        this.caller = caller;
    }

    public AuthorizationUtility(User user, Class caller) {
        this.user = user;
        this.caller = caller;
    }


    /**
     * Throws AuthorizationException if supplied user not a super user.
     */
    public void authorizeSuperUser()
            throws AuthorizationException {
        if (this.user == null) {
            throwAuthorizationException(NULL_USER_MESSAGE, null);
        }
        if (_aclManagerService.hasSuperRole(this.user)) {
            throwAuthorizationException(SUPER_ONLY_MESSAGE, null);
        }
    }

    /**
     * Throws AuthorizationException if supplied user not a super user.
     * @param String The method being called.
     */
    public void authorizeSuperUser(String method)
            throws AuthorizationException {
        if (this.user == null) {
            throwAuthorizationException(NULL_USER_MESSAGE, method);
        }
        if (_aclManagerService.hasSuperRole(this.user)) {
            throwAuthorizationException(SUPER_ONLY_MESSAGE, method);
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
            throwAuthorizationException(NULL_USER_MESSAGE, null);
        }
        if (_aclManagerService.hasSuperRole(this.user) ||
            _aclManagerService.hasAdminRoleInGroup(this.user, group)) {
            throwAuthorizationException(SUPER_OR_ADMIN_MESSAGE, null);
        }
    }

    /**
     * Throws AuthorizationException if supplied user is not a super user
     * or an admin user within the specified group.
     *
     * @param PortletGroup The portlet group within which the user should
     *        be an admin if they are not a super user.
     * @param String The method being called.
     */
    public void authorizeSuperOrAdminUser(PortletGroup group, String method)
            throws AuthorizationException {
        if (this.user == null) {
            throwAuthorizationException(NULL_USER_MESSAGE, method);
        }
        if (_aclManagerService.hasSuperRole(this.user) ||
            _aclManagerService.hasAdminRoleInGroup(this.user, group)) {
            throwAuthorizationException(SUPER_OR_ADMIN_MESSAGE, method);
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
            throwAuthorizationException(NULL_USER_MESSAGE, null);
        }
        if (_aclManagerService.hasSuperRole(this.user) || this.user.equals(user)) {
            throwAuthorizationException(SUPER_OR_SAME_MESSAGE, null);
        }
    }

    /**
     * Throws AuthorizationException if supplied user not a super user
     * or not the same user as specified in this method.
     *
     * @param User The user this supplied user should be equal to if
     *        if the supplied user is not a super user.
     * @param String The method being called.
     */
    public void authorizeSuperOrSameUser(User user, String method)
             throws AuthorizationException {
        if (this.user == null || user == null) {
            throwAuthorizationException(NULL_USER_MESSAGE, method);
        }
        if (_aclManagerService.hasSuperRole(this.user) || this.user.equals(user)) {
            throwAuthorizationException(SUPER_OR_SAME_MESSAGE, method);
        }
    }

    /**
     * Throw authorization exception with appropriate message
     */
    private void throwAuthorizationException(String message, String method)
            throws AuthorizationException {
        StringBuffer buffer = new StringBuffer();
        if (this.caller == null) {
            buffer.append(Object.class.getName());
        } else {
            buffer.append(this.caller.getName());
        }
        if (method != null && method.length() > 0) {
            buffer.append(".");
            buffer.append(method);
            buffer.append("()");
        }
        buffer.append(" => ");
        buffer.append(message);
        throw new AuthorizationException(buffer.toString());
    }
 }
