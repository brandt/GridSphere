/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.registry.impl;

import org.gridlab.gridsphere.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.services.core.registry.PortletManagerService;
import org.gridlab.gridsphere.services.core.registry.impl.tomcat.TomcatManagerWrapper;

import java.io.IOException;
import java.util.List;

/**
 * The <code>PortletManagerServiceImpl</code> is an implementation of the <code>PortletManagerService</code>
 * that is responsible for the initialization, installation, removal and overall
 * management of portlet web applications.
 */
public class PortletManagerServiceImpl implements PortletManagerService, PortletServiceProvider {

    private static PortletLog log = SportletLog.getInstance(PortletManagerServiceImpl.class);
    private static boolean isManagerInited = false;
    private PortletManager portletManager = PortletManager.getInstance();

    /**
     * Constructs an instance of PortletManagerServiceImpl
     */
    public PortletManagerServiceImpl() {
    }

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        log.debug("in init()");
        if (!isManagerInited) {
            portletManager.init(config);
            isManagerInited = true;
            String name = config.getInitParameter("username");
            String pass = config.getInitParameter("password");
            TomcatManagerWrapper.setCredentials(name, pass);
        }
    }

    public void destroy() {
        log.debug("in destroy()");
    }

    /**
     * Initializes a portlet web application
     *
     * @param webApplicationName the name of the portlet web application
     * @param req                the <code>PortletRequest</code>
     * @param res                the <code>Portletresponse</code>
     * @throws IOException      if an I/O error occurs
     * @throws PortletException if a portlet/servlet error occurs
     */
    public void initPortletWebApplication(String webApplicationName, PortletRequest req, PortletResponse res) throws IOException, PortletException {
        portletManager.initPortletWebApplication(webApplicationName, req, res);
    }

    /**
     * Shuts down a currently active portlet web application from the portlet container
     *
     * @param webApplicationName the name of the portlet web application
     * @param req                the <code>PortletRequest</code>
     * @param res                the <code>Portletresponse</code>
     * @throws IOException      if an I/O error occurs
     * @throws PortletException if a portlet/servlet error occurs
     */
    public void destroyPortletWebApplication(String webApplicationName, PortletRequest req, PortletResponse res) throws IOException, PortletException {
        log.debug("in PortletManagerServiceImpl: destroy webapp " + webApplicationName);
        portletManager.destroyPortletWebApplication(webApplicationName, req, res);
    }

    /**
     * Lists all the portlet web applications known to the portlet container
     *
     * @return the list of web application names as <code>String</code> elements
     */
    public List getPortletWebApplicationNames() {
        return portletManager.getPortletWebApplicationNames();
    }

    /**
     * Lists all the portlet web application descriptions known to the portlet container
     *
     * @return the list of web application names as <code>String</code> elements
     */
    public String getPortletWebApplicationDescription(String webApplicationName) {
        return portletManager.getPortletWebApplicationDescription(webApplicationName);
    }

}
