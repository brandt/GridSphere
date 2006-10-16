package org.gridsphere.portletcontainer.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridsphere.portletcontainer.PortletDispatcher;
import org.gridsphere.portletcontainer.PortletStatus;
import org.gridsphere.services.core.persistence.PersistenceManagerService;

import javax.portlet.PortletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

/**
 * The <code>PortletWebApplicationImpl</code> is an implementation of a <code>PortletWebApplication</code> that
 * represents a collection of portlets contained in a packaged WAR file. Currently
 * under development is the notion of dynamically managing portlet web applications.
 */
public class PortletWebApplicationLoader {

    private Log log = LogFactory.getLog(PortletWebApplicationLoader.class);

    protected String webApplicationName = "Unknown portlet web application";
    protected String webAppDescription = "Unknown portlet web application description";

    protected PortletStatus status = PortletStatus.SUCCESS;
    protected String statusMessage = "Portlet web application loaded successfully";

    protected PortletDispatcher portletDispatcher = null;

    /**
     * Constructs an instance of a PortletWebApplicationImpl from a supplied ui application name and corresponding
     * <code>ServletContext</code>
     *
     * @param webApplicationName the the web application name
     * @param context            the <code>ServletContext</code>
     * @throws PortletException if an initialization exception occurs
     */
    public PortletWebApplicationLoader(String webApplicationName, ServletContext context) throws PortletException {

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

        if (ctx == null) {
            log.error(webApplicationName + ": Unable to get ServletContext for: " + contextURIPath);
            throw new PortletException(webApplicationName + ": Unable to get ServletContext for: " + contextURIPath);
        }
        log.debug("context path: " + ctx.getRealPath(""));
        this.webAppDescription = ctx.getServletContextName();


        String servletName = "PortletServlet";
        RequestDispatcher rd = ctx.getNamedDispatcher(servletName);
        if (rd == null) {
            String msg = "Unable to create a dispatcher for portlet: JSR Portlet Servlet\n";
            msg += "Make sure the servletName: " + servletName + " is the servlet-name defined in web.xml";
            throw new PortletException(msg);
        }
        portletDispatcher = new PortletDispatcherImpl(rd);

    }

    /**
     * Under development. A portlet web application can unregister itself from the application server
     */
    public void destroy() {
        PortletServiceFactory.shutdownServices(webApplicationName);
        PersistenceManagerService pmservice = (PersistenceManagerService) PortletServiceFactory.createPortletService(PersistenceManagerService.class, true);
        pmservice.destroyPersistenceManagerRdbms(webApplicationName);
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

    public PortletDispatcher getPortletDispatcher() {
        return portletDispatcher;
    }
}
