/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.registry.impl;

import org.gridlab.gridsphere.layout.PortletLayoutEngine;
import org.gridlab.gridsphere.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceAuthorizer;
import org.gridlab.gridsphere.portletcontainer.*;
import org.gridlab.gridsphere.portletcontainer.impl.PortletWebApplicationImpl;
import org.gridlab.gridsphere.services.registry.PortletManagerService;

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

    public final static String CORE_CONTEXT = "coreContext";

    private static PortletLog log = SportletLog.getInstance(PortletManagerServiceImpl.class);
    private ServletContext context = null;

    private PortletLayoutEngine layoutEngine = PortletLayoutEngine.getInstance();
    private static PortletRegistry registry = PortletRegistry.getInstance();

    // A multi-valued hashtable with a webapp key and a List value containing portletAppID's
    private Map webapps = new Hashtable();
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
        this.context = config.getServletConfig().getServletContext();
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
    }

    public void destroy() {
        log.info("in destroy()");
    }

    protected void addWebApp(String webApplicationName) {
        PortletWebApplication portletWebApp = new PortletWebApplicationImpl(webApplicationName, context);
        Collection appPortlets = portletWebApp.getAllApplicationPortlets();
        Iterator it = appPortlets.iterator();
        List appPortletList = new ArrayList();
        while (it.hasNext()) {
            ApplicationPortlet appPortlet = (ApplicationPortlet) it.next();
            appPortletList.add(appPortlet);
            registry.addApplicationPortlet(appPortlet);
        }
        webapps.put(webApplicationName, appPortletList);
    }


    /**
     * Removes a portlet web application from the registry
     *
     * @param the web application name
     */
    public void removePortletWebApplication(String webApplicationName, PortletRequest req, PortletResponse res) {
        List webappsList = (List) webapps.get(webApplicationName);
        Iterator it = webappsList.iterator();
        while (it.hasNext()) {
            ApplicationPortlet appPortlet = (ApplicationPortlet) it.next();
            registry.removeApplicationPortlet((String) appPortlet.getPortletAppID());
        }
        webapps.remove(webApplicationName);

        // Remove default tab from layout engine
        layoutEngine.removeApplicationTab(webApplicationName);
    }

    public void installPortletWebApplication(String webApplicationName, PortletRequest req, PortletResponse res) throws PortletException {
        addWebApp(webApplicationName);
        initPortletWebApplication(webApplicationName, req, res);
    }

    public void initAllPortletWebApplications(PortletRequest req, PortletResponse res) throws PortletException {
        PortletDispatcher.initAllPortlets(req, res);
    }

    public void initPortletWebApplication(String webApplicationName, PortletRequest req, PortletResponse res) throws PortletException {
        PortletDispatcher.initPortlets(webApplicationName, req, res);
    }

    public void destroyAllPortletWebApplications(PortletRequest req, PortletResponse res) throws PortletException {
        PortletDispatcher.destroyAllPortlets(req, res);
    }

    public void destroyPortletWebApplication(String webApplicationName, PortletRequest req, PortletResponse res) throws PortletException {
        PortletDispatcher.destroyPortlets(webApplicationName, req, res);
    }

    /**
     * Lists all the portlet web applications in the registry
     *
     * @return the list of web application names
     */
    public List getPortletWebApplications() {
        List l = new ArrayList();
        Set set = webapps.keySet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            l.add((String) it.next());
        }
        return l;
    }



}
