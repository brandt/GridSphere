/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.impl;

import org.gridlab.gridsphere.layout.PortletLayoutEngine;
import org.gridlab.gridsphere.portletcontainer.descriptor.PortletDefinition;
import org.gridlab.gridsphere.portletcontainer.descriptor.PortletDeploymentDescriptor;
import org.gridlab.gridsphere.portletcontainer.PortletWebApplication;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfig;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfigProperties;
import org.gridlab.gridsphere.portletcontainer.ApplicationPortlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import java.io.File;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

public class PortletWebApplicationImpl implements PortletWebApplication {

    private String webApplicationName = null;
    private Map appPortlets = new Hashtable();
    private RequestDispatcher rd = null;
    private String webAppDescription;

    // Layout engine handles layout.xml
    private PortletLayoutEngine layoutEngine = PortletLayoutEngine.getInstance();

    public PortletWebApplicationImpl(String webApplicationName, ServletContext context) {
        this.webApplicationName = webApplicationName;
        // get the servlet context for the coreportlets webapp
        String contextURIPath = "/" + webApplicationName;
        ServletContext ctx = context.getContext(contextURIPath);
        this.webAppDescription = ctx.getServletContextName();
        if (ctx == null) System.err.println("Unable to get ServletContext for: " + contextURIPath);
        rd = ctx.getNamedDispatcher(webApplicationName);
        // load portlet.xml
        loadPortlets(ctx);
        // load layout.xml
        loadLayout(ctx);
    }

    protected void loadPortlets(ServletContext ctx) {
        // load in the portlet.xml file
        String portletXMLfile = ctx.getRealPath("") + "/WEB-INF/portlet.xml";
        String portletMappingfile = GridSphereConfig.getProperty(GridSphereConfigProperties.PORTLET_MAPPING_XML);

        PortletDeploymentDescriptor pdd = null;
        try {
            pdd = new PortletDeploymentDescriptor(portletXMLfile, portletMappingfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Every PortletDefinition has a PortletApplication and possibly multiple ConcretePortletApplication's
        Iterator portletDefs = pdd.getPortletDef().iterator();

        // Iterate thru portlet definitions for portlet applications
        while (portletDefs.hasNext()) {
            PortletDefinition portletDef = (PortletDefinition) portletDefs.next();
            ApplicationPortlet portletApp = new ApplicationPortletImpl(pdd, portletDef, webApplicationName, ctx);
            String portletAppID = portletApp.getPortletAppID();
            appPortlets.put(portletAppID, portletApp);
        }
    }

    protected void loadLayout(ServletContext ctx) {
        // load in the portlet.xml file
        String layoutXMLfile = ctx.getRealPath("") + "/WEB-INF/layout.xml";
        File f = new File(layoutXMLfile);
        if (f.exists()) {
            String layoutMappingfile = GridSphereConfig.getProperty(GridSphereConfigProperties.LAYOUT_MAPPING_XML);
            layoutEngine.addApplicationTab(webApplicationName, layoutXMLfile);
        }
    }

    public void destroy() {
        layoutEngine.removeApplicationTab(webApplicationName);
    }

    public String getWebApplicationName() {
        return webApplicationName;
    }

    public String getWebApplicationDescription() {
        return webAppDescription;
    }

    public ApplicationPortlet getApplicationPortlet(String portletApplicationID) {
        return (ApplicationPortlet) appPortlets.get(portletApplicationID);
    }

    public Collection getAllApplicationPortlets() {
        return appPortlets.values();
    }

}
