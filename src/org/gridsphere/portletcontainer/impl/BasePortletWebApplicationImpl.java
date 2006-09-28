/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: BasePortletWebApplicationImpl.java 5032 2006-08-17 18:15:06Z novotny $
 */
package org.gridsphere.portletcontainer.impl;

import org.gridsphere.portlet.PortletException;
import org.gridsphere.portlet.PortletLog;
import org.gridsphere.portlet.impl.SportletLog;
import org.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridsphere.portlet.service.spi.impl.descriptor.SportletServiceCollection;
import org.gridsphere.portlet.service.PortletServiceException;
import org.gridsphere.portletcontainer.PortletWebApplication;
import org.gridsphere.portletcontainer.PortletStatus;

import javax.servlet.ServletContext;
import java.io.File;
import java.util.*;

/**
 * The <code>PortletWebApplicationImpl</code> is an implementation of a <code>PortletWebApplication</code> that
 * represents a collection of portlets contained in a packaged WAR file. Currently
 * under development is the notion of dynamically managing portlet web applications.
 */
public abstract class BasePortletWebApplicationImpl implements PortletWebApplication {

    private PortletLog log = SportletLog.getInstance(BasePortletWebApplicationImpl.class);

    protected Map appPortlets = new Hashtable();

    protected boolean isJSR = false;

    protected String webApplicationName = "Unknown portlet web application";
    protected String webAppDescription = "Unknown portlet web application description";

    protected PortletStatus status = PortletStatus.SUCCESS;
    protected String statusMessage = "Portlet web application loaded successfully";

    /**
     * Constructs an instance of a BasePortletWebApplicationImpl from a supplied
     * <code>ServletContext</code>
     *
     * @param context the <code>ServletContext</code>
     */
    public BasePortletWebApplicationImpl(ServletContext context) {
    }

    public abstract void init();

    /**
     * Under development. A portlet web application can unregister itself from the application server
     */
    public abstract void destroy();

    /**
     * Loads collection of portlets from portlet descriptor file using the associated <code>ServletContext</code>
     *
     * @param ctx the <code>ServletContext</code>
     */
    protected abstract void loadPortlets(ServletContext ctx, ClassLoader loader) throws PortletException;

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
