/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: JSRPortletWebApplicationImpl.java 5032 2006-08-17 18:15:06Z novotny $
 */
package org.gridsphere.portletcontainer.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gridsphere.portletcontainer.ApplicationPortlet;
import org.gridsphere.portletcontainer.PortletStatus;
import org.gridsphere.portletcontainer.PortletWebApplication;
import org.gridsphere.portletcontainer.impl.descriptor.*;
import org.gridsphere.portlet.service.PortletServiceException;
import org.gridsphere.portlet.service.spi.impl.descriptor.SportletServiceCollection;
import org.gridsphere.portlet.service.spi.PortletServiceFactory;

import javax.portlet.PortletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import java.io.File;
import java.util.Hashtable;
import java.util.Map;
import java.util.Collection;
import java.util.ArrayList;

/**
 * The <code>PortletWebApplicationImpl</code> is an implementation of a <code>PortletWebApplication</code> that
 * represents a collection of portlets contained in a packaged WAR file. Currently
 * under development is the notion of dynamically managing portlet web applications.
 */
public class PortletWebApplicationImpl implements PortletWebApplication {

    private Log log = LogFactory.getLog(PortletWebApplicationImpl.class);
    private PortletApp portletWebApp = null;

    protected Map portletDefinitions = new Hashtable();
    protected RequestDispatcher rd = null;

    protected Map appPortlets = new Hashtable();

    protected boolean isJSR = false;

    protected String webApplicationName = "Unknown portlet web application";
    protected String webAppDescription = "Unknown portlet web application description";

    protected PortletStatus status = PortletStatus.SUCCESS;
    protected String statusMessage = "Portlet web application loaded successfully";

    public PortletWebApplicationImpl(ServletContext context, ClassLoader loader) {

        // Make all jsr portlets have only one concrete instance
        String realPath = context.getRealPath("");
        int l = realPath.lastIndexOf(File.separator);
        webApplicationName = realPath.substring(l + 1);

        this.webAppDescription = context.getServletContextName();
        try {
            // load portlet.xml
            loadPortlets(context, loader);

            // load services.xml
            loadServices(context, loader);
        } catch (PortletException e) {
            status = PortletStatus.FAILURE;
            statusMessage = e.getMessage();
        }
    }


    /**
     * Loads collection of portlets from portlet descriptor file using the associated <code>ServletContext</code>
     *
     * @param ctx the <code>ServletContext</code>
     */
    protected void loadPortlets(ServletContext ctx, ClassLoader loader) throws PortletException {
        // load in the portlet.xml file
        String portletXMLfile = ctx.getRealPath("/WEB-INF/portlet.xml");

        PortletDeploymentDescriptor pdd = null;
        try {
            pdd = new PortletDeploymentDescriptor(portletXMLfile);
        } catch (Exception e) {
            status = PortletStatus.FAILURE;
            statusMessage = "Unable to load portlets from: " + webApplicationName + " due to mapping error";
            throw new PortletException(statusMessage, e);
        }

        this.portletWebApp = pdd.getPortletWebApplication();
        // Every SportletDefinition has a PortletApplication and possibly multiple ConcretePortletConfig's
        PortletDefinition[] portletDefs = pdd.getPortletDefinitionList();


        // Iterate thru portlet definitions for portlet applications
        for (int i = 0; i < portletDefs.length; i++) {
            ApplicationPortlet portletApp = new ApplicationPortletImpl(loader, pdd, portletDefs[i], webApplicationName, ctx);

            String portletClass = portletApp.getApplicationPortletID();
            String portletName = portletApp.getApplicationPortletName();
            portletDefinitions.put(portletName, portletDefs[i]);
            appPortlets.put(portletName, portletApp);

            log.debug("sticking " + portletName + " class: " + portletClass + " in hash");
        }

    }

    public PortletDefinition getPortletDefinition(String portletName) {
        return (PortletDefinition) portletDefinitions.get(portletName);
    }

    public void init() {}

    public void destroy() {
        portletWebApp = null;
        appPortlets = null;
        portletDefinitions = null;
        rd = null;
    }


    public CustomPortletMode[] getCustomPortletModes() {
        return portletWebApp.getCustomPortletMode();
    }

    public CustomWindowState[] getCustomWindowStates() {
        return portletWebApp.getCustomWindowState();
    }

    public UserAttribute[] getUserAttributes() {
        return portletWebApp.getUserAttribute();
    }

    public SecurityConstraint[] getSecurityConstraints() {
        return portletWebApp.getSecurityConstraint();
    }

    /**
     * Loads in a service descriptor file from the associated servlet context
     *
     * @param ctx the <code>ServletContext</code>
     */
    protected void loadServices(ServletContext ctx, ClassLoader loader) throws PortletException {
        // load in the portlet-services.xml file
        String descriptorPath = ctx.getRealPath("/WEB-INF/PortletServices.xml");
        File f = new File(descriptorPath);
        if (f.exists()) {
            PortletServiceDescriptor descriptor = null;
            try {
                System.err.println("loading from: " + descriptorPath);
                descriptor = new PortletServiceDescriptor(descriptorPath);
            } catch (Exception e) {
                //log.error("error unmarshalling " + servicesPath + " using " + servicesMappingPath + " : " + e.getMessage());
                status = PortletStatus.FAILURE;
                statusMessage = "Error unmarshalling " + descriptorPath;
                throw new PortletServiceException(statusMessage, e);
            }
            SportletServiceCollection serviceCollection = descriptor.getServiceCollection();
            PortletServiceFactory.addServices(webApplicationName, ctx, serviceCollection, loader);
        } else {
            descriptorPath = ctx.getRealPath("/WEB-INF/portlet-services");
            f = new File(descriptorPath);
            if (f.exists()) {
                String[] servicePaths = f.list();
                for (int i = 0; i < servicePaths.length; i++) {
                    servicePaths[i] = descriptorPath + File.separator + servicePaths[i];
                }
                for (int i = 0; i < servicePaths.length; i++) {
                    PortletServiceDescriptor descriptor = null;
                    try {
                        System.err.println("loading from: " + servicePaths[i]);
                        descriptor = new PortletServiceDescriptor(servicePaths[i]);
                    } catch (Exception e) {
                        status = PortletStatus.FAILURE;
                        statusMessage = "Error unmarshalling " + servicePaths[i];
                        throw new PortletServiceException("error unmarshalling " + servicePaths[i], e);
                    }
                    SportletServiceCollection serviceCollection = descriptor.getServiceCollection();
                    PortletServiceFactory.addServices(webApplicationName, ctx, serviceCollection, loader);
                }
            } else {
                log.debug("Did not find PortletServices.xml or portlet-services directory for: " + ctx.getServletContextName());
            }
        }
    }

    /**
     * Returns the portlet web application name
     *
     * @return the portlet web application name
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

    public void setWebApplicationStatus(PortletStatus status) {
        this.status = status;
    }

    public void setWebApplicationStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public PortletStatus getWebApplicationStatus() {
        return status;
    }

    public String getWebApplicationStatusMessage() {
        return statusMessage;
    }
}
