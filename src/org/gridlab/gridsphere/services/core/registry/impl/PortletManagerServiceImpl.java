/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.registry.impl;

import org.gridlab.gridsphere.layout.PortletLayoutEngine;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceAuthorizer;
import org.gridlab.gridsphere.portletcontainer.*;
import org.gridlab.gridsphere.portletcontainer.impl.PortletWebApplicationImpl;
import org.gridlab.gridsphere.services.core.registry.PortletManagerService;

import javax.servlet.ServletContext;
import java.util.*;

/**
 * The PortletRegistryService acts as a repository for portlets and makes them available to the portlet
 * container, layout manager and any other services that require an active portlet.
 * The PortletInfo base class is responsible for reading in the associated portlet.xml file and
 * creating a ConcretePortlet object which represents the portlet. The PortletRegistryService maintains
 * a Set of RegisteredPortlets and provides operations for the registration, unregistration and querying
 * of ConcretePortlet objects.
 */
public class PortletManagerServiceImpl implements PortletManagerService, PortletServiceProvider {


    private static PortletLog log = SportletLog.getInstance(PortletManagerServiceImpl.class);
    private PortletManager portletManager = PortletManager.getInstance();

    // A multi-valued hashtable with a webapp key and a List value containing portletAppID's
    //private Map webapps = new Hashtable();
    //private UserPortletManager userPortletManager = UserPortletManager.getInstance();

    private PortletServiceAuthorizer authorizer = null;

    public PortletManagerServiceImpl(PortletServiceAuthorizer authorizer) {
        this.authorizer = authorizer;
    }

    /**
     * The init method is responsible for parsing portlet.xml and creating ConcretePortlet objects based on the
     * entries. The RegisteredPortlets are managed by the PortletRegistryService.
     */
    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        log.info("in init()");
        portletManager.init(config);
    }

    public void destroy() {
        log.info("in destroy()");
    }

    /**
     * Removes a portlet web application from the registry
     *
     * @param the web application name
     */
    public void removePortletWebApplication(String webApplicationName, PortletRequest req, PortletResponse res) {
        portletManager.removePortletWebApplication(webApplicationName, req, res);
    }

    public void installPortletWebApplication(String webApplicationName, PortletRequest req, PortletResponse res) throws PortletException {
        portletManager.installPortletWebApplication(webApplicationName, req, res);
    }

    public void initAllPortletWebApplications(PortletRequest req, PortletResponse res) throws PortletException {
        portletManager.initAllPortletWebApplications(req, res);
    }

    public void initPortletWebApplication(String webApplicationName, PortletRequest req, PortletResponse res) throws PortletException {
        portletManager.initPortletWebApplication(webApplicationName, req, res);
    }

    public void destroyAllPortletWebApplications(PortletRequest req, PortletResponse res) throws PortletException {
        portletManager.destroyAllPortletWebApplications(req, res);
    }

    public void destroyPortletWebApplication(String webApplicationName, PortletRequest req, PortletResponse res) throws PortletException {
        portletManager.destroyPortletWebApplication(webApplicationName, req, res);
    }

    /**
     * Lists all the portlet web applications in the registry
     *
     * @return the list of web application names
     */
    public List getPortletWebApplications() {
        return portletManager.getPortletWebApplications();
    }



}
