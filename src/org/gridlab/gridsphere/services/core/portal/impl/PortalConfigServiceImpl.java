/**
 * @author <a href="mailto:kisg@mailbox.hu">Gergely Kis</a>
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.portal.impl;

import org.gridlab.gridsphere.services.core.portal.PortalConfigService;
import org.gridlab.gridsphere.services.core.portal.PortalConfigSettings;
import org.gridlab.gridsphere.services.core.security.acl.AccessControlManagerService;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

/**
 * Portal configuration service is used to manage portal administrative settings
 */
public class PortalConfigServiceImpl extends HibernateDaoSupport implements PortalConfigService {

    private AccessControlManagerService accessControlManagerService = null;

    public void init() {
        PortalConfigSettings configSettings = this.getPortalConfigSettings();
        if (configSettings == null) {
            configSettings = new PortalConfigSettings();
            configSettings.setCanUserCreateAccount(false);
            // set gridsphere as a default group
            Set defaultGroups = new HashSet();

            defaultGroups.add(accessControlManagerService.getCoreGroup());

            configSettings.setDefaultGroups(defaultGroups);
            // set default theme
            //configSettings.setDefaultTheme(config.getInitParameter("defaultTheme"));
            // set mailer
            savePortalConfigSettings(configSettings);
        }

    }

    public void setAccessControlManagerService(AccessControlManagerService accessControlManagerService) {
        this.accessControlManagerService = accessControlManagerService;
    }

    public AccessControlManagerService getAccessControlManagerService() {
        return accessControlManagerService;
    }

    public void savePortalConfigSettings(PortalConfigSettings configSettings) {
        System.err.println("saving config settings!!");
        this.getHibernateTemplate().saveOrUpdate(configSettings);
    }

    public PortalConfigSettings getPortalConfigSettings() {
        List list = this.getHibernateTemplate().find("select c from " + PortalConfigSettings.class.getName() + " c");
        if ((list != null) && (!list.isEmpty())) {
            return (PortalConfigSettings)list.get(0);
        }
        return null;
    }

}
