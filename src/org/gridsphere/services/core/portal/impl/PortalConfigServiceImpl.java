/**
 * @author <a href="mailto:kisg@mailbox.hu">Gergely Kis</a>
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
package org.gridsphere.services.core.portal.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gridsphere.layout.PortletPageFactory;
import org.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridsphere.services.core.customization.SettingsService;
import org.gridsphere.services.core.portal.PortalConfigService;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.Properties;

/**
 * Portal configuration service is used to manage portal administrative settings
 */
public class PortalConfigServiceImpl implements PortletServiceProvider, PortalConfigService {

    private Log log = LogFactory.getLog(PortalConfigServiceImpl.class);

    protected Properties props = new Properties();
    protected ServletContext ctx = null;
    private SettingsService settingsService = null;

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        ctx = config.getServletContext();
        settingsService = (SettingsService) PortletServiceFactory.createPortletService(SettingsService.class, true);
        try {
            File propFile = new File(settingsService.getRealSettingsPath("portal/gridsphere.properties"));
            props.load(new FileInputStream(propFile));

            // init config params here, just make sure we do have a theme set
            String theme = getProperty(PortalConfigService.DEFAULT_THEME);
            if (theme == null) setProperty(PortalConfigService.DEFAULT_THEME, PortletPageFactory.DEFAULT_THEME);
            props.store(new FileOutputStream(propFile), "GridSphere Portal Properties");
        } catch (FileNotFoundException e) {
            log.error("Unable to find gridsphere.properties", e);
        } catch (IOException e) {
            log.error("Unable to load gridsphere.properties", e);
        }
    }

    public void destroy() {

    }

    public String getProperty(String key) {
        if (key == null) throw new IllegalArgumentException("property key cannot be null!");
        return props.getProperty(key);
    }

    public void setProperty(String key, String value) {
        if (key == null) throw new IllegalArgumentException("property key cannot be null!");
        if (value == null) throw new IllegalArgumentException("property value cannot be null!");
        props.setProperty(key, value);
    }

    public void storeProperties() throws IOException {
        FileOutputStream propertiesOutputStream = new FileOutputStream(settingsService.getRealSettingsPath("portal/gridsphere.properties"));
        props.store(propertiesOutputStream, "GridSphere Portal Properties");
    }

}
