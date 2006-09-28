/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: JSRPortletWebApplicationImpl.java 5032 2006-08-17 18:15:06Z novotny $
 */
package org.gridsphere.portletcontainer.jsrimpl;

import org.gridsphere.portlet.PortletException;
import org.gridsphere.portlet.PortletLog;
import org.gridsphere.portlet.impl.SportletLog;
import org.gridsphere.portletcontainer.ApplicationPortlet;
import org.gridsphere.portletcontainer.PortletWebApplication;
import org.gridsphere.portletcontainer.PortletStatus;
import org.gridsphere.portletcontainer.impl.BasePortletWebApplicationImpl;
import org.gridsphere.portletcontainer.jsrimpl.descriptor.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import java.io.File;
import java.util.Hashtable;
import java.util.Map;

/**
 * The <code>PortletWebApplicationImpl</code> is an implementation of a <code>PortletWebApplication</code> that
 * represents a collection of portlets contained in a packaged WAR file. Currently
 * under development is the notion of dynamically managing portlet web applications.
 */
public class JSRPortletWebApplicationImpl extends BasePortletWebApplicationImpl implements PortletWebApplication {

    private PortletLog log = SportletLog.getInstance(JSRPortletWebApplicationImpl.class);
    private PortletApp portletWebApp = null;

    protected Map portletDefinitions = new Hashtable();
    protected RequestDispatcher rd = null;

    public JSRPortletWebApplicationImpl(ServletContext context, ClassLoader loader) {
        super(context);

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

        PortletDeploymentDescriptor2 pdd = null;
        try {
            pdd = new PortletDeploymentDescriptor2(portletXMLfile);
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
            ApplicationPortlet portletApp = new JSRApplicationPortletImpl(loader, pdd, portletDefs[i], webApplicationName, ctx);

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
}
