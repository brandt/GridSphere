/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer;

import org.gridlab.gridsphere.portletcontainer.descriptor.PortletDefinition;
import org.gridlab.gridsphere.portletcontainer.descriptor.PortletDeploymentDescriptor;
import org.gridlab.gridsphere.portletcontainer.impl.ApplicationPortletImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

public class PortletWebApplication {

    private String webApplicationName = null;
    private Map appPortlets = new Hashtable();
    private RequestDispatcher rd = null;


    public PortletWebApplication(String webApplicationName, ServletContext context) {
        this.webApplicationName = webApplicationName;
        // get the servlet context for the coreportlets webapp
        String contextURIPath = "/" +  webApplicationName;
        ServletContext ctx = context.getContext(contextURIPath);
        if (ctx == null) System.err.println("Unable to get ServletContext for: " + contextURIPath);
        rd = ctx.getNamedDispatcher(webApplicationName);

        // load in the portlet.xml file
        String portletXMLfile = ctx.getRealPath("") + "/WEB-INF/portlet.xml";
        GridSphereConfig gsConfig = GridSphereConfig.getInstance();
        String portletMappingfile = gsConfig.getProperty(GridSphereConfigProperties.PORTLET_MAPPING_XML);

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

    public String getWebApplicationName() {
        return webApplicationName;
    }

    public ApplicationPortlet getApplicationPortlet(String portletApplicationID) {
        return (ApplicationPortlet)appPortlets.get(portletApplicationID);
    }

    public Collection getAllApplicationPortlets() {
        return appPortlets.values();
    }

}
