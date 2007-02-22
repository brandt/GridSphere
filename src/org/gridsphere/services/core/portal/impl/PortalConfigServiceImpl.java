/**
 * @author <a href="mailto:kisg@mailbox.hu">Gergely Kis</a>
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: PortalConfigServiceImpl.java 5032 2006-08-17 18:15:06Z novotny $
 */
package org.gridsphere.services.core.portal.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gridsphere.layout.PortletPageFactory;
import org.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridsphere.services.core.portal.PortalConfigService;

import javax.servlet.ServletContext;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Portal configuration service is used to manage portal administrative settings
 */
public class PortalConfigServiceImpl implements PortletServiceProvider, PortalConfigService {

    private Log log = LogFactory.getLog(PortalConfigServiceImpl.class);


    private String GRIDSPHERE_PROPERTIES = "/WEB-INF/CustomPortal/portal/gridsphere.properties";

    protected Properties props = null;
    protected ServletContext ctx = null;

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        ctx = config.getServletContext();
        InputStream propsStream = config.getServletContext().getResourceAsStream(GRIDSPHERE_PROPERTIES);
        try {
            FileOutputStream propertiesOutputStream = null;
            propertiesOutputStream = new FileOutputStream(ctx.getRealPath(GRIDSPHERE_PROPERTIES));
            props = new Properties();
            props.load(propsStream);
            // init config params here
            String theme = getProperty(PortalConfigService.DEFAULT_THEME);
            if (theme == null) setProperty(PortalConfigService.DEFAULT_THEME, PortletPageFactory.DEFAULT_THEME);
            props.store(propertiesOutputStream, "GridSphere Portal Properties");
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
        FileOutputStream propertiesOutputStream = null;
        propertiesOutputStream = new FileOutputStream(ctx.getRealPath(GRIDSPHERE_PROPERTIES));
        props.store(propertiesOutputStream, "GridSphere Portal Properties");
    }

}
