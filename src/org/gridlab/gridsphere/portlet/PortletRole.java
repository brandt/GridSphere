/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet;

import java.util.ArrayList;
import java.util.List;

public interface PortletRole {

    public boolean isUserRole();

    public boolean isAdminRole();

    public boolean isSuperRole();

    public int getRole();

    public int getID();

    public String getRoleName();

}
