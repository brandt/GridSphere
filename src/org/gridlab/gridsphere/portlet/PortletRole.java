/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet;

import org.gridlab.gridsphere.portlet.impl.SportletRole;

public interface PortletRole {

    public static final PortletRole GUEST = SportletRole.GUEST;
    public static final PortletRole USER = SportletRole.USER;
    public static final PortletRole ADMIN = SportletRole.ADMIN;
    public static final PortletRole SUPER = SportletRole.SUPER;

    public int getRole();

    public int getID();

    public String toString();

}
