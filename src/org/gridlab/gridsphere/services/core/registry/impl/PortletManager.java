/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.registry.impl;

import org.gridlab.gridsphere.layout.PortletLayoutEngine;
import org.gridlab.gridsphere.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portletcontainer.ApplicationPortlet;
import org.gridlab.gridsphere.portletcontainer.PortletInvoker;
import org.gridlab.gridsphere.portletcontainer.PortletRegistry;
import org.gridlab.gridsphere.portletcontainer.PortletWebApplication;
import org.gridlab.gridsphere.portletcontainer.impl.PortletWebApplicationImpl;
import org.gridlab.gridsphere.services.core.registry.PortletManagerService;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.*;

/**
 * The <code>PortletManager</code> is a singleton responsible for maintaining the registry of portlet
 * web applications known to the portlet container.
 */
public class PortletManager implements PortletManagerService {

    private final static String CORE_CONTEXT = "startup-portlet-webapps";
    private static PortletLog log = SportletLog.getInstance(PortletManager.class);
    private static PortletManager instance = new PortletManager();
    private ServletContext context = null;
    private boolean isInitialized = false;
    private PortletRegistry registry = PortletRegistry.getInstance();

    // A multi-valued hashtable with a webapp key and a List value containing portletAppID's
    private List webapps = new Vector();
    //private UserPortletManager userPortletManager = UserPortletManager.getDefault();

    /**
     * Default instantiation disallowed
     */
    private PortletManager() {}

    /**
     * Return an instance of PortletManager
     *
     * @return the PortletManager instance
     */
    public static PortletManager getInstance() {
        return instance;
    }

    /**
     * Initializes portlet web applications that are defined by the <code>PortletManagerService</code>
     *
     * @param config the <code>PortletServiceConfig</code>
     * @throws PortletServiceUnavailableException if initialization fails
     */
    public synchronized void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        log.debug("in init()");
        if (!isInitialized) {
            context = config.getServletConfig().getServletContext();
            String webapps = config.getInitParameter(CORE_CONTEXT);
            if (webapps != null) {
                try {
                String webapp;
                StringTokenizer st = new StringTokenizer(webapps, ",");
                if (st.countTokens() == 0) {
                    webapp = webapps.trim();
                    log.debug("adding webapp:" + webapp);
                    addWebApp(webapp);
                } else {
                    while (st.hasMoreTokens()) {
                        webapp = (String) st.nextToken().trim();
                        log.debug("adding webapp:" + webapp);
                        addWebApp(webapp);
                    }
                }
                } catch (PortletException e) {
                    //e.getMessage();
                    throw new PortletServiceUnavailableException("Unable to load core portlet web applications: " + e.getMessage(), e);
                }
            }
            isInitialized = true;
        }
    }

    /**
     * Adds a portlet web application to the registry
     *
     * @param webApplicationName the portlet web application name
     */
    protected void addWebApp(String webApplicationName) throws PortletException {
        PortletWebApplication portletWebApp = new PortletWebApplicationImpl(webApplicationName, context);
        Collection appPortlets = portletWebApp.getAllApplicationPortlets();
        Iterator it = appPortlets.iterator();
        while (it.hasNext()) {
            ApplicationPortlet appPortlet = (ApplicationPortlet) it.next();
            log.debug("Adding application portlet: " + appPortlet.getApplicationPortletID());
            registry.addApplicationPortlet(appPortlet);
        }
        webapps.add(portletWebApp);
    }

    public synchronized void removePortletWebApplication(String webApplicationName, PortletRequest req, PortletResponse res) {
        Iterator it = webapps.iterator();
        while (it.hasNext()) {
            PortletWebApplication webApp = (PortletWebApplication)it.next();

            if (webApp.getWebApplicationName().equalsIgnoreCase(webApplicationName)) {
                webApp.destroy();
                Collection appPortlets = webApp.getAllApplicationPortlets();
                Iterator appsit = appPortlets.iterator();
                while (appsit.hasNext()) {
                    ApplicationPortlet appPortlet = (ApplicationPortlet) it.next();
                    registry.removeApplicationPortlet(appPortlet.getApplicationPortletID());
                }
                webapps.remove(webApplicationName);
            }
        }
    }

    public synchronized void installPortletWebApplication(String webApplicationName, PortletRequest req, PortletResponse res) throws IOException, PortletException {
        addWebApp(webApplicationName);
        initPortletWebApplication(webApplicationName, req, res);
    }

    public synchronized void initAllPortletWebApplications(PortletRequest req, PortletResponse res) throws IOException, PortletException {
        PortletInvoker.initAllPortlets(req, res);
    }

    public synchronized void initPortletWebApplication(String webApplicationName, PortletRequest req, PortletResponse res) throws IOException, PortletException {
        PortletInvoker.initPortletWebApp(webApplicationName, req, res);
    }

    public synchronized void destroyAllPortletWebApplications(PortletRequest req, PortletResponse res) throws IOException, PortletException {
        PortletInvoker.destroyAllPortlets(req, res);
    }

    public synchronized void destroyPortletWebApplication(String webApplicationName, PortletRequest req, PortletResponse res) throws IOException, PortletException {
        PortletInvoker.destroyPortletWebApp(webApplicationName, req, res);
    }

    public List getPortletWebApplicationNames() {
        List l = new ArrayList();
        for (int i = 0; i < webapps.size(); i++) {
            PortletWebApplication webapp = (PortletWebApplication)webapps.get(i);
            l.add(webapp.getWebApplicationName());
        }
        return l;
    }

}
