/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.jsrimpl;

import org.gridlab.gridsphere.layout.PortletTabRegistry;
import org.gridlab.gridsphere.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletGroup;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portletcontainer.ApplicationPortlet;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfig;
import org.gridlab.gridsphere.portletcontainer.PortletWebApplication;
import org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.*;
import org.gridlab.gridsphere.services.core.security.acl.impl.AccessControlManagerServiceImpl;
import org.gridlab.gridsphere.services.core.security.acl.impl.descriptor.PortletGroupDescriptor;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import java.io.File;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

/**
 * The <code>PortletWebApplicationImpl</code> is an implementation of a <code>PortletWebApplication</code> that
 * represents a collection of portlets contained in a packaged WAR file. Currently
 * under development is the notion of dynamically managing portlet web applications.
 */
public class JSRPortletWebApplicationImpl implements PortletWebApplication {

    private PortletLog log = SportletLog.getInstance(JSRPortletWebApplicationImpl.class);
    private PortletApp portletWebApp = null;
    private String servletName = null;
    protected Map appPortlets = new Hashtable();
    protected Map portletDefinitions = new Hashtable();
    protected RequestDispatcher rd = null;
    protected String webApplicationName = "Unknown portlet web application";
    protected String webAppDescription = "Unknown portlet web application description";

    // PortletLayout engine handles layout.xml
    //private PortletLayoutEngine layoutEngine = PortletLayoutEngine.getInstance();

    public JSRPortletWebApplicationImpl(ServletContext context, String servletName, ClassLoader loader) throws PortletException {
        String realPath = context.getRealPath("");
        int l = realPath.lastIndexOf(File.separator);
        String appName = realPath.substring(l + 1);

        // Make all jsr portlets have only one concrete instance
        webApplicationName = appName + ".1";
        this.webAppDescription = context.getServletContextName();
        this.servletName = servletName;

        //rd = context.getNamedDispatcher(webApplicationName);

        // load services xml
        loadServices(webApplicationName, context, loader);
        
        // load portlet.xml
        loadJSRPortlets(context);

        // load group.xml
        loadGroup(context);

    }


    /**
     * Loads collection of portlets from portlet descriptor file using the associated <code>ServletContext</code>
     *
     * @param ctx the <code>ServletContext</code>
     */
    protected void loadJSRPortlets(ServletContext ctx) throws PortletException {
        // load in the portlet.xml file
        String portletXMLfile = ctx.getRealPath("/WEB-INF/portlet.xml");
        //String portletMappingFile = GridSphereConfig.getProperty(GridSphereConfigProperties.PORTLET_MAPPING);

        String portletMappingFile = GridSphereConfig.getServletContext().getRealPath("/WEB-INF/mapping/portlet-jsr-mapping.xml");

        PortletDeploymentDescriptor2 pdd = null;
        try {
            pdd = new PortletDeploymentDescriptor2(portletXMLfile, portletMappingFile);
        } catch (Exception e) {
            log.error("Mapping Error! " + webApplicationName, e);
            throw new PortletException("Unable to load portlets from: " + webApplicationName + " + due to mapping error", e);
        }

        this.portletWebApp = pdd.getPortletWebApplication();
        // Every SportletDefinition has a PortletApplication and possibly multiple ConcretePortletConfig's
        PortletDefinition[] portletDefs = pdd.getPortletDefinitionList();


        // Iterate thru portlet definitions for portlet applications
        for (int i = 0; i < portletDefs.length; i++) {
            ApplicationPortlet portletApp = new JSRApplicationPortletImpl(pdd, portletDefs[i], servletName, webApplicationName, ctx);
            String portletClass = portletApp.getApplicationPortletID();
            portletDefinitions.put(portletClass, portletDefs[i]);
            appPortlets.put(portletClass, portletApp);
            log.debug("sticking " + portletClass + " in hash");
        }

    }

    /**
     * Called from loadGroups to load the layout associated with this group if it exits
     *
     * @param ctx the servlet context
     * @param groupName  the group name
     * @throws PortletException  if an error occurs
     */
    protected void loadLayout(ServletContext ctx, String groupName) throws PortletException {
        // load in the portlet.xml file
        String layoutXMLfile = ctx.getRealPath("/WEB-INF/layout.xml");
        File fin = new File(layoutXMLfile);
        if (fin.exists()) {
            try {
                PortletTabRegistry.copyFile(fin, groupName);
                log.info("Loaded a layout descriptor " + groupName);
            } catch (Exception e) {
                throw new PortletException("Unable to deserialize layout.xml for: " + groupName, e);
            }
        } else {
            log.debug("Did not find layout.xml for: " + ctx.getServletContextName());
        }
    }

    /**
     * Loads in a group descriptor file from the associated servlet context and then loads in a layout if one exists
     *
     * @param ctx the <code>ServletContext</code>
     */
   protected void loadGroup(ServletContext ctx) throws PortletException {
        // load in the portlet.xml file
        String groupXMLfile = ctx.getRealPath("/WEB-INF/group.xml");
        File f = new File(groupXMLfile);
        if (f.exists()) {
            try {
                PortletGroupDescriptor groupDescriptor = new PortletGroupDescriptor(groupXMLfile);
                SportletGroup group = groupDescriptor.getPortletGroup();
                AccessControlManagerServiceImpl aclManager = AccessControlManagerServiceImpl.getInstance();
                PortletGroup g = aclManager.getGroupByName(group.getName());
                if (g == null) {
                    aclManager.createGroup(group);
                }
                loadLayout(ctx, group.getName());
            } catch (Exception e) {
                throw new PortletException("Unable to deserialize group.xml for: " + webApplicationName, e);
            }
        } else {
            log.debug("Did not find group.xml for: " + ctx.getServletContextName());
        }
    }

    /**
     * Loads in a layout descriptor file from the associated servlet context
     *
     * @param ctx the <code>ServletContext</code>
     */
    protected void loadServices(String webappName, ServletContext ctx, ClassLoader loader) throws PortletException {
        // load in the portlet.xml file
        String descriptor = ctx.getRealPath("/WEB-INF/PortletServices.xml");
        File f = new File(descriptor);
        if (f.exists()) {
            SportletServiceFactory factory = SportletServiceFactory.getInstance();
            factory.addServices(webappName, ctx, descriptor, loader);
        } else {
            log.debug("Did not find PortletServices.xml for: " + ctx.getServletContextName());
        }
    }

    public PortletDefinition getPortletDefinition(String portletClassName) {
        return (PortletDefinition) portletDefinitions.get(portletClassName);
    }

    public void destroy() {
        //log.debug("removing application tab :" + webApplicationName);
        //PortletTabRegistry.removeGroupTab(webApplicationName);
        log.debug("unloading portlet services: ");
        SportletServiceFactory factory = SportletServiceFactory.getInstance();
        factory.shutdownServices(webApplicationName);
        PersistenceManagerFactory.destroyPersistenceManagerRdbms(webApplicationName);
        portletWebApp = null;
        servletName = null;
        appPortlets = null;
        portletDefinitions = null;
        rd = null;
    }

    public String getWebApplicationName() {
        return webApplicationName;
    }

    public Collection getAllApplicationPortlets() {
        return appPortlets.values();
    }

    public ApplicationPortlet getApplicationPortlet(String applicationPortletID) {
        return (ApplicationPortlet) appPortlets.get(applicationPortletID);
    }

    public String getWebApplicationDescription() {
        return webAppDescription;
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
