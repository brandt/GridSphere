/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.service.spi;

import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.service.PortletServiceAuthorizationException;

/**
 * The <code>PortletServiceAuthorizer</code> interface allows user services
 * to perform authorization checks on users.
 */
public interface PortletServiceAuthorizer {

    /**
     * Returns the internal user associated with this user
     *
     * @return the internal user
     */
    public User getInternalUser();

    /**
     * Authorizes operations that require super user access
     *
     * @throws PortletServiceAuthorizationException if supplied user is not a super user
     */
    public void authorizeSuperUser() throws PortletServiceAuthorizationException;

    /**
     *  Authorizes operations that require admin user access
     *
     * @param group the portlet group within which the
     * user should be an admin if they are not a super user.
     * @throws PortletServiceAuthorizationException if supplied user is not an admin user
     */
    public void authorizeAdminUser(PortletGroup group) throws PortletServiceAuthorizationException;

    /**
     * Authorizes operations that require super or admin user access
     *
     * @param group the portlet group within which the
     * user should be an admin if they are not a super user
     * @throws PortletServiceAuthorizationException if supplied user is not a super or admin user
     */
    public void authorizeSuperOrAdminUser(PortletGroup group) throws PortletServiceAuthorizationException;

    /**
     * Authorizes operations that require either a super user or can be invoked
     * only if the supplied user  matches the associated user
     *
     * @param user the portlet group within which the
     * user should be an admin if they are not a super user
     * @throws PortletServiceAuthorizationException if supplied user is
     * not a super or same user
     */
    public void authorizeSuperOrSameUser(User user) throws PortletServiceAuthorizationException;

    /**
     * Authorizes operations that require either a super user, or admin user, or
     * can be invoked only if the supplied user  matches the associated user
     *
     * @param user the portlet group within which the
     * user should be an admin if they are not a super user
     * @throws PortletServiceAuthorizationException if supplied user is
     * not a super, admin, or same  user
     */
    public void authorizeSuperAdminOrSameUser(User user, PortletGroup group) throws PortletServiceAuthorizationException;
}
