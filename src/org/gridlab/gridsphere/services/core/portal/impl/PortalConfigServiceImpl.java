/**
 * @author <a href="mailto:kisg@mailbox.hu">Gergely Kis</a>
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.portal.impl;

import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerFactory;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerRdbms;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.services.core.portal.PortalConfigService;
import org.gridlab.gridsphere.services.core.portal.PortalConfigSettings;
import org.gridlab.gridsphere.services.core.security.group.GroupManagerService;

import java.util.HashSet;
import java.util.Set;

/**
 * Portal configuration service is used to manage portal administrative settings
 */
public class PortalConfigServiceImpl implements PortletServiceProvider, PortalConfigService {

    private PortletLog log = SportletLog.getInstance(PortalConfigServiceImpl.class);

    private PersistenceManagerRdbms pm = null;

    public synchronized void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        if (pm == null) pm = PersistenceManagerFactory.createGridSphereRdbms();
        PortalConfigSettings configSettings = this.getPortalConfigSettings();
        if (configSettings == null) {
            configSettings = new PortalConfigSettings();
            boolean canCreate = Boolean.getBoolean(config.getInitParameter("canUserCreateNewAccount"));
            configSettings.setCanUserCreateAccount(canCreate);
            // set gridsphere as a default group
            Set defaultGroups = new HashSet();

            PortletServiceFactory factory = SportletServiceFactory.getInstance();
            try {
                GroupManagerService aclService =
                        (GroupManagerService)factory.createPortletService(GroupManagerService.class, true);
                defaultGroups.add(aclService.getCoreGroup());
            } catch (PortletServiceNotFoundException e) {
                log.error("No core group found!!");
            }
            configSettings.setDefaultGroups(defaultGroups);
            // set default theme
            //configSettings.setDefaultTheme(config.getInitParameter("defaultTheme"));
            // set mailer
            savePortalConfigSettings(configSettings);
        }

    }

    public synchronized void destroy() {

    }

    public void savePortalConfigSettings(PortalConfigSettings configSettings) {
        try {
            if (configSettings.getOid() == null) {
                pm.create(configSettings);
            } else {
                pm.update(configSettings);
            }
        } catch (PersistenceManagerException e) {
            log.error("Unable to save or update config settings!", e);
        }
    }

    public PortalConfigSettings getPortalConfigSettings() {
        PortalConfigSettings settings = null;
        try {
            settings = (PortalConfigSettings) pm.restore("select c from " + PortalConfigSettings.class.getName() + " c");
        } catch (PersistenceManagerException e) {
            log.error("Unable to retrieve config settings!", e);
        }
        return settings;
    }

}
