/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: PortletWebApplicationImpl.java 5032 2006-08-17 18:15:06Z novotny $
 */
package org.gridsphere.portletcontainer.impl;

import org.gridsphere.portlet.PortletException;
import org.gridsphere.portlet.PortletLog;
import org.gridsphere.portlet.impl.SportletLog;
import org.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridsphere.portletcontainer.ApplicationPortlet;
import org.gridsphere.portletcontainer.PortletWebApplication;
import org.gridsphere.portletcontainer.impl.descriptor.PortletDeploymentDescriptor;
import org.gridsphere.portletcontainer.impl.descriptor.SportletDefinition;
import org.gridsphere.services.core.persistence.PersistenceManagerService;

import javax.servlet.ServletContext;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * The <code>PortletWebApplicationImpl</code> is an implementation of a <code>PortletWebApplication</code> that
 * represents a collection of portlets contained in a packaged WAR file. Currently
 * under development is the notion of dynamically managing portlet web applications.
 */
public class PortletWebApplicationImpl extends BasePortletWebApplicationImpl implements PortletWebApplication {

    private PortletLog log = SportletLog.getInstance(PortletWebApplicationImpl.class);

    /**
     * Constructs an instance of a PortletWebApplicationImpl from a supplied ui application name and corresponding
     * <code>ServletContext</code>
     *
     * @param webApplicationName the the web application name
     * @param context            the <code>ServletContext</code>
     */
    public PortletWebApplicationImpl(String webApplicationName, ServletContext context) throws PortletException {
        super(context);
        this.webApplicationName = webApplicationName;
        // get the servlet context for the coreportlets webapp
        String contextURIPath;
        if (webApplicationName.startsWith("/")) {
            contextURIPath = webApplicationName;
            this.webApplicationName = webApplicationName.substring(1);
        } else {
            contextURIPath = "/" + webApplicationName;
        }

        // Get the cross context servlet context
        ServletContext ctx = context.getContext(contextURIPath);
        //System.err.println("contextURIPath: " + contextURIPath);
        //System.err.println("contextName: " + ctx.getServletContextName());
        //System.err.println("context path: " + ctx.getRealPath(""));


        //System.err.println("testing example portlets");
        //ServletContext testsc = context.getContext("/exampleportlets");
        //System.err.println("description: " + ctx.getServletContextName());
        //System.err.println("testing core portlets");
        //testsc = context.getContext("/coreportlets");
        //System.err.println("description: " + testsc.getServletContextName());
        //System.err.println("context path: " + te.getRealPath(""));

        if (ctx == null) {
            log.error(webApplicationName + ": Unable to get ServletContext for: " + contextURIPath);
            throw new PortletException(webApplicationName + ": Unable to get ServletContext for: " + contextURIPath);
        }
        log.debug("context path: " + ctx.getRealPath(""));
        this.webAppDescription = ctx.getServletContextName();

        // load portlet.xml
        loadPortlets(ctx, Thread.currentThread().getContextClassLoader());
        // load services xml
        if (!isJSR) loadServices(ctx, Thread.currentThread().getContextClassLoader());
        // load layout.xml
        if (!isJSR) loadLayout(ctx);
    }

    /**
     * Loads collection of portlets from portlet descriptor file using the associated <code>ServletContext</code>
     *
     * @param ctx the <code>ServletContext</code>
     */
    protected void loadPortlets(ServletContext ctx, ClassLoader loader) throws PortletException {

        // First we see if this is a gridsphere portlet descriptor and load in as gridsphere-portlet.xml
        log.info("Loading gridsphere-portlet.xml...");
        String gsportletXMLfile = ctx.getRealPath("/WEB-INF/gridsphere-portlet.xml");
        File gs = new File(gsportletXMLfile);

        String portletXMLfile = ctx.getRealPath("/WEB-INF/portlet.xml");
        File jsr = new File(portletXMLfile);

        if (gs.exists() && jsr.exists()) {
            isJSR = true;
        }

        if (!gs.exists()) {
            // trying
            log.info("Instead loading portlet.xml as a gridsphere portlet descriptor...");
            gsportletXMLfile = portletXMLfile;
        }

        PortletDeploymentDescriptor pdd = null;

        try {
            pdd = new PortletDeploymentDescriptor(gsportletXMLfile);
        } catch (Exception e) {
            log.error("Mapping Error! " + webApplicationName, e);
            throw new PortletException("Unable to load portlets from: " + webApplicationName + " + due to mapping error!");
        }
        // Every SportletDefinition has a PortletApplication and possibly multiple ConcretePortletConfig's
        Iterator portletDefs = pdd.getPortletDefinitionList().iterator();

        // Iterate thru portlet definitions for portlet applications
        while (portletDefs.hasNext()) {
            SportletDefinition portletDef = (SportletDefinition) portletDefs.next();
            ApplicationPortlet portletApp = new ApplicationPortletImpl(pdd, portletDef, webApplicationName, ctx);
            String portletAppID = portletApp.getApplicationPortletID();
            appPortlets.put(portletAppID, portletApp);
        }
    }

    public void init() {

    }

    /**
     * Under development. A portlet web application can unregister itself from the application server
     */
    public void destroy() {
        PersistenceManagerService pmservice = (PersistenceManagerService) PortletServiceFactory.createPortletService(PersistenceManagerService.class, true);
        pmservice.destroyPersistenceManagerRdbms(webApplicationName);
        PortletServiceFactory.shutdownServices(webApplicationName);
        appPortlets = null;
    }


    /**
     * Returns the portlet web application name
     *
     * @return the ui application name
     */
    public String getWebApplicationName() {
        return webApplicationName;
    }

    /**
     * Returns the portlet web application description
     *
     * @return the portlet web application description
     */
    public String getWebApplicationDescription() {
        return webAppDescription;
    }

    /**
     * Returns the collection of application portlets contained by this portlet web application
     *
     * @return the collection of application portlets
     */
    public Collection getAllApplicationPortlets() {
        return ((appPortlets != null ? appPortlets.values() : new ArrayList()));
    }

}
