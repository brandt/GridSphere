/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.user;

import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.portlet.PortletRole;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletUser;

import java.util.List;

public interface AccountRequest extends SportletUser {

    /**
     * Adds the AccountRequest to the group with that role
     *
     * @param group
     * @param role
     */
    public void addToGroup(PortletGroup group, PortletRole role);

    /**
     * Prints the contents of the account request to a String
     *
     * @return the account request information
     */
    public String toString();

}
