/**
 * @author <a href="mailto:kisg@mailbox.hu">Gergely Kis</a>
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.portal;

import java.util.List;
import java.util.Set;

/**
 * A container for the GridSphere portal administrative settings
 */
public class PortalConfigSettings {

    private String oid = null;

    private boolean canCreateAccount = false;

    private Set defaultGroups = null;

    private String defaultTheme = "default";

    public PortalConfigSettings() {}

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public void setCanUserCreateAccount(boolean canCreateAccount) {
        this.canCreateAccount = canCreateAccount;
    }

    public boolean getCanUserCreateAccount() {
        return canCreateAccount;
    }

    public Set getDefaultGroups() {
        return defaultGroups;
    }

    public void setDefaultGroups(Set defaultGroups) {
        this.defaultGroups = defaultGroups;
    }

    public void setDefaultTheme(String theme) {
        this.defaultTheme = theme;
    }

    public String getDefaultTheme() {
        return defaultTheme;
    }
}
