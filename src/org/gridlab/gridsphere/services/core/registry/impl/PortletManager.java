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
import org.gridlab.gridsphere.portletcontainer.*;
import org.gridlab.gridsphere.portletcontainer.impl.PortletWebApplicationImpl;
import org.gridlab.gridsphere.services.core.registry.PortletManagerService;

import javax.servlet.ServletContext;
import java.util.*;
import java.io.IOException;

/**
 * The <code>PortletManager</code> is a singleton responsible for maintaining the registry of portlet
 * web applications known to the portlet container.
 */
public class PortletManager implements PortletManagerService {

    public final static String CORE_CONTEXT = "startup-portlet-webapps";
    private static PortletLog log = SportletLog.getInstance(PortletManager.class);
    private static PortletManager instance = new PortletManager();
    private ServletContext context = null;
    private boolean isInitialized = false;
    private PortletLayoutEngine layoutEngine = PortletLayoutEngine.getInstance();
    private PortletRegistry registry = PortletRegistry.getInstance();

    // A multi-valued hashtable with a webapp key and a List value containing portletAppID's
    private Map webapps = new Hashtable();
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
    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        log.debug("in init()");
        if (!isInitialized) {
            context = config.getServletConfig().getServletContext();
            String webapps = config.getInitParameter(CORE_CONTEXT);
            if (webapps != null) {
                String webapp;
                StringTokenizer st = new StringTokenizer(webapps, ",");
                if (st.countTokens() == 0) {
                    webapp = webapps.trim();
                    System.err.println("adding webapp:" + webapp);
                    addWebApp(webapp);
                } else {
                    while (st.hasMoreTokens()) {
                        webapp = (String) st.nextToken().trim();
                        System.err.println("adding webapp:" + webapp);
                        addWebApp(webapp);
                    }
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
    protected void addWebApp(String webApplicationName) {
        PortletWebApplication portletWebApp = new PortletWebApplicationImpl(webApplicationName, context);
        Collection appPortlets = portletWebApp.getAllApplicationPortlets();
        Iterator it = appPortlets.iterator();
        while (it.hasNext()) {
            ApplicationPortlet appPortlet = (ApplicationPortlet) it.next();
            registry.addApplicationPortlet(appPortlet);
        }
        webapps.put(webApplicationName, portletWebApp);
    }

    public void removePortletWebApplication(String webApplicationName, PortletRequest req, PortletResponse res) {
        List webappsList = (List) webapps.get(webApplicationName);
        if (webappsList != null) {
            Iterator it = webappsList.iterator();
            while (it.hasNext()) {
                ApplicationPortlet appPortlet = (ApplicationPortlet) it.next();
                registry.removeApplicationPortlet((String) appPortlet.getApplicationPortletID());
            }
            webapps.remove(webApplicationName);

            // Remove default tab from layout engine
            layoutEngine.removeApplicationTab(webApplicationName);
        }
    }

    public void installPortletWebApplication(String webApplicationName, PortletRequest req, PortletResponse res) throws IOException, PortletException {
        addWebApp(webApplicationName);
        initPortletWebApplication(webApplicationName, req, res);
    }

    public void initAllPortletWebApplications(PortletRequest req, PortletResponse res) throws IOException, PortletException {
        PortletInvoker.initAllPortlets(req, res);
    }

    public void initPortletWebApplication(String webApplicationName, PortletRequest req, PortletResponse res) throws IOException, PortletException {
        PortletInvoker.initPortletWebApp(webApplicationName, req, res);
    }

    public void destroyAllPortletWebApplications(PortletRequest req, PortletResponse res) throws IOException, PortletException {
        PortletInvoker.destroyAllPortlets(req, res);
    }

    public void destroyPortletWebApplication(String webApplicationName, PortletRequest req, PortletResponse res) throws IOException, PortletException {
        PortletInvoker.destroyPortletWebApp(webApplicationName, req, res);
    }

    public List getPortletWebApplicationNames() {
        List l = new ArrayList();
        Set set = webapps.keySet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            l.add((String) it.next());
        }
        return l;
    }

}
