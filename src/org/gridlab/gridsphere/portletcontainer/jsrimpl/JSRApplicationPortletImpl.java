/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.jsrimpl;

import org.gridlab.gridsphere.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.jsrimpl.PortalContextImpl;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import org.gridlab.gridsphere.portletcontainer.ApplicationPortlet;
import org.gridlab.gridsphere.portletcontainer.PortletDispatcher;
import org.gridlab.gridsphere.portletcontainer.ConcretePortlet;
import org.gridlab.gridsphere.portletcontainer.ApplicationPortletConfig;
import org.gridlab.gridsphere.portletcontainer.impl.SportletDispatcher;

import org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.portlet.Portlet;
import javax.portlet.PortalContext;
import java.util.Locale;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.Iterator;
import java.net.URLEncoder;

/**
 * The <code>ApplicationPortletImpl</code> is an implementation of the <code>ApplicationPortlet</code> interface
 * that uses Castor for XML to Java data bindings.
 * <p>
 * The <code>ApplicationPortlet</code> represents the portlet application instance
 * defined in the portlet descriptor file.
 */
public class JSRApplicationPortletImpl implements ApplicationPortlet {

    private PortletLog log = SportletLog.getInstance(JSRApplicationPortletImpl.class);
    private PortletDefinition portletDef = null;
    private String portletName = "";
    private String portletClassName = null;
    private String servletName = "";
    private String webAppName = null;
    private Locale[] supportedLocales = null;
    private List concPortlets = null;
    private PortletDispatcher portletDispatcher = null;
    private Portlet portletInstance = null;
    private JSRApplicationPortletConfigImpl appConfig = null;
    private ServletContext context = null;
    private PortalContext portalContext = null;

    /**
     * Default constructor is private
     */
    private JSRApplicationPortletImpl() {}

    /**
     * Constructs an instance of ApplicationPortletImpl
     *
     * @param pdd the <code>PortletDeploymentDescriptor</code>
     * @param portletDef the portlet definition
     * @param webApplication the ui application name for this application portlet
     * @param context the <code>ServletContext</code> containing this application portlet
     */
    public JSRApplicationPortletImpl(PortletDeploymentDescriptor2 pdd, PortletDefinition portletDef, String servletName, String webApplication, ServletContext context) throws PortletException {
        this.portletDef = portletDef;
        this.webAppName = webApplication;
        this.servletName = servletName;
        this.portletClassName = portletDef.getPortletClass().getContent();
        this.portletName = portletDef.getPortletName().getContent();
        this.context = context;
   
        SupportedLocale[] locales = portletDef.getSupportedLocale();
        supportedLocales = new Locale[locales.length];
        for (int i = 0; i < locales.length; i++) {
            supportedLocales[i] = new Locale(locales[i].getContent());
        }

        // create portal context
        portalContext = new PortalContextImpl(pdd.getPortletWebApplication());

        /*
        log.debug("Creating request dispatcher for " + servletName);

        RequestDispatcher rd = context.getNamedDispatcher(servletName);
        if (rd == null) {
            String msg = "Unable to create a dispatcher for portlet: " + portletName + "\n";
            msg += "Make sure the servletName: " + servletName + " is the servlet-name defined in web.xml";
            log.error(msg);
            throw new PortletException(msg);
        }
        portletDispatcher = new SportletDispatcher(rd, null);
        */

        concPortlets = new Vector();
        log.debug("creating JSRApplicationPortletConfigImpl");
        appConfig = new JSRApplicationPortletConfigImpl(pdd.getPortletWebApplication(), portletDef);

        log.debug("creating JSRConcretePortletConfigImpl");
        JSRConcretePortletConfigImpl concConfig = new JSRConcretePortletConfigImpl(portletDef);

        log.debug("creating JSRConcretePortletImpl");
        JSRConcretePortletImpl concPortlet = new JSRConcretePortletImpl(pdd, portletDef, appConfig, concConfig);
        concPortlets.add(concPortlet);
    }


    /**
     * Returns the concrete portlets associated with this application portlet
     *
     * @return the <code>ConcretePortlet</code>s
     */
    public List getConcretePortlets() {
        return concPortlets;
    }

    /**
     * Returns the <code>ConcretePortlet</code> associated with this application
     * portlet
     *
     * @param concretePortletID the concrete portlet ID associated with this
     * <code>ApplicationPortlet</code>
     * @return the <code>ConcretePortlet</code> associated with this
     * application portlet
     */
    public ConcretePortlet getConcretePortlet(String concretePortletID) {
        Iterator it = concPortlets.iterator();
        while (it.hasNext()) {
            ConcretePortlet conc = (ConcretePortlet)it.next();
            if (conc.getConcretePortletID().equals(concretePortletID)) return conc;
        }
        return null;
    }


    /**
     * Return the PortletApplication, the portlet descriptor class that defines the portlet application
     *
     * @return the PortletApplication
     */
    public ApplicationPortletConfig getApplicationPortletConfig() {
        return appConfig;
    }


    /**
     * Return the web application name associated with this application portlet
     *
     * @return the web application name
     */
    public String getWebApplicationName() {
        return webAppName;
    }

     /**
     * Returns the id of a PortletApplication
     *
     * @return the id of the PortletApplication
     */
    public String getApplicationPortletID() {
        return portletClassName;
     }

    /**
     * Returns a PortletDispatcher for this ApplicationPortlet
     *
     * @return PortletDispatcher the proxy portlet for this ApplicationPortlet
     */
    public PortletDispatcher getPortletDispatcher(org.gridlab.gridsphere.portlet.PortletRequest req, org.gridlab.gridsphere.portlet.PortletResponse res) {

        // TODO fix my hack to get any render params and pass them as queryInfo to the portlet
        Map params = (Map)req.getAttribute("renderParams" + "_" + portletClassName);
        String extraInfo = "";

        if (params != null) {
            extraInfo = "?";
            boolean firstParam = true;
            Iterator it = params.keySet().iterator();
            while (it.hasNext()) {
                if (!firstParam) extraInfo += "&";
                String name = (String)it.next();
                String encname = URLEncoder.encode(name);
                String[] vals = (String[])params.get(name);
                if (vals != null) {
                    for (int i = 0; i < vals.length; i++) {
                        if (!firstParam) {
                            extraInfo += "&";
                        }
                        String encvalue = URLEncoder.encode(vals[i]);
                        extraInfo += encname + "=" + encvalue;
                        firstParam = false;
                    }
                } else {
                    extraInfo += encname;
                }
                firstParam = false;
            }
        }

        // before it adds ".1" to real webappName
        String realWebAppName = webAppName.substring(0,webAppName.length() - 2);

       
        System.err.println("in getPortletDispatcher of jsr query string " + extraInfo);
        // TODO change dangerously hardcoded value!!!
        RequestDispatcher rd = context.getRequestDispatcher("/jsr/" + realWebAppName  + extraInfo);
        //RequestDispatcher rd = context.getNamedDispatcher(servletName);

        if (rd == null) {
            String msg = "Unable to create a dispatcher for portlet: " + portletName + "\n";
            msg += "Make sure the servletName: " + servletName + " is the servlet-name defined in web.xml";
            log.error(msg);
        }
        portletDispatcher = new SportletDispatcher(rd, null);

        return portletDispatcher;
    }

    /**
     * Returns a PortletDispatcher for this ApplicationPortlet
     *
     * @return PortletDispatcher the proxy portlet for this ApplicationPortlet
     */
    public PortletDispatcher getPortletDispatcher() {
        return portletDispatcher;
    }

    /**
     * Returns the name of a PortletApplication
     *
     * @return name of the PortletApplication
     */
    public String getApplicationPortletName() {
        return portletName;
    }

    /**
     * Returns the name of a servlet associated with this portlet defined in ui.xml as <servlet-name>
     *
     * @return the servlet name
     */
    public String getServletName() {
        return servletName;
    }

    public String getPortletClassName() {
        return portletClassName;
    }

    public String getPortletName() {
        return portletDef.getPortletName().getContent();
    }

    public PortalContext getPortalContext() {
        return portalContext;
    }

    public String getPortletDescription(Locale locale) {
        Description[] descs = portletDef.getDescription();
        for (int i = 0; i < descs.length; i++) {
            if (descs[i].getLang().equals(locale.getLanguage())) {
                return descs[i].getContent();
            }
        }
        return "Unknown portlet description";
    }

    public String getPortletDisplayName(Locale locale) {
        DisplayName[] dispNames = portletDef.getDisplayName();
        for (int i = 0; i < dispNames.length; i++) {
            if (dispNames[i].getLang().equals(locale.getLanguage())) {
                return dispNames[i].getContent();
            }
        }
        return "Unknown portlet display name";
    }

    public int getExpirationCache() {
        return portletDef.getExpirationCache().getContent();
    }

    public Locale[] getSupportedLocales() {
        return supportedLocales;
    }

    public Supports[] getSupports() {
        return portletDef.getSupports();
    }

    public SecurityRoleRef[] getSecurityRoleRefs() {
        return portletDef.getSecurityRoleRef();
    }

    public org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletPreferences getPortletPreferences() {
        org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletPreferences prefDesc = portletDef.getPortletPreferences();
        return prefDesc;
    }

    public Portlet getPortletInstance() {
        return portletInstance;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("\t JSR Application Portlet:\n");
        sb.append("\t JSR Portlet Name: " + portletName + "\n");
        sb.append("\t Web app name: " + webAppName + "\n");
        sb.append("\t Servlet Name: " + servletName + "\n");

        if (portletDispatcher == null) {
            sb.append("\t Portlet dispatcher: NULL");
        } else {
            sb.append("\t Portlet dispatcher: OK");
        }
        return sb.toString();
    }
}
