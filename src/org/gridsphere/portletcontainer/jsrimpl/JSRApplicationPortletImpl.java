/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: JSRApplicationPortletImpl.java 4985 2006-08-04 09:54:28Z novotny $
 */
package org.gridsphere.portletcontainer.jsrimpl;

import org.gridsphere.portlet.PortletLog;
import org.gridsphere.portlet.impl.SportletLog;
import org.gridsphere.portlet.impl.SportletProperties;
import org.gridsphere.portlet.jsrimpl.PortalContextImpl;
import org.gridsphere.portletcontainer.*;
import org.gridsphere.portletcontainer.impl.SportletDispatcher;
import org.gridsphere.portletcontainer.jsrimpl.descriptor.*;

import javax.portlet.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import java.net.URLEncoder;
import java.util.*;
import java.io.UnsupportedEncodingException;

/**
 * The <code>ApplicationPortletImpl</code> is an implementation of the <code>ApplicationPortlet</code> interface
 * that uses Castor for XML to Java data bindings.
 * <p/>
 * The <code>ApplicationPortlet</code> represents the portlet application instance
 * defined in the portlet descriptor file.
 */
public class JSRApplicationPortletImpl implements ApplicationPortlet {

    private PortletLog log = SportletLog.getInstance(JSRApplicationPortletImpl.class);
    private PortletDefinition portletDef = null;
    private String portletName = "";
    private String portletClassName = null;
    private String webAppName = null;
    private Locale[] supportedLocales = null;
    private List concPortlets = null;
    private PortletDispatcher portletDispatcher = null;
    private Portlet portletInstance = null;
    private JSRApplicationPortletConfigImpl appConfig = null;
    private ServletContext context = null;
    private PortalContext portalContext = null;
    private javax.portlet.PreferencesValidator prefsValidator = null;

    protected PortletStatus status = PortletStatus.Success;
    protected String statusMessage = "JSR Portlet loaded successfully";

    /**
     * Default constructor is private
     */
    private JSRApplicationPortletImpl() {
    }

    /**
     * Constructs an instance of ApplicationPortletImpl
     *
     * @param pdd            the <code>PortletDeploymentDescriptor</code>
     * @param portletDef     the portlet definition
     * @param webApplication the ui application name for this application portlet
     * @param context        the <code>ServletContext</code> containing this application portlet
     */
    public JSRApplicationPortletImpl(ClassLoader loader, PortletDeploymentDescriptor2 pdd, PortletDefinition portletDef, String webApplication, ServletContext context)  {
        this.portletDef = portletDef;
        this.webAppName = webApplication;
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

        //portletDef.getSupports();

        log.debug("creating JSRConcretePortletConfigImpl");
        JSRConcretePortletConfigImpl concConfig = new JSRConcretePortletConfigImpl(portletDef);

        log.debug("creating JSRConcretePortletImpl");
        JSRConcretePortletImpl concPortlet = new JSRConcretePortletImpl(pdd, portletDef, concConfig, webAppName);
        concPortlets.add(concPortlet);

        org.gridsphere.portletcontainer.jsrimpl.descriptor.PortletPreferences prefDesc = portletDef.getPortletPreferences();
        if (prefDesc != null) {
            org.gridsphere.portletcontainer.jsrimpl.descriptor.PreferencesValidator validator = prefDesc.getPreferencesValidator();
            if (validator != null) {
                String validatorClass = validator.getContent();
                if (validatorClass != null) {
                    try {
                        prefsValidator = (javax.portlet.PreferencesValidator) Class.forName(validatorClass, true, loader).newInstance();
                    } catch (Exception e) {
                        log.error("Unable to create validator: " + validatorClass + "! ",  e);
                    }
                }
            }
        }
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
     *                          <code>ApplicationPortlet</code>
     * @return the <code>ConcretePortlet</code> associated with this
     *         application portlet
     */
    public ConcretePortlet getConcretePortlet(String concretePortletID) {
        return (ConcretePortlet)concPortlets.get(0);
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
        return webAppName + "#" + portletName;
    }

    /**
     * Returns a PortletDispatcher for this ApplicationPortlet
     *
     * @return PortletDispatcher the proxy portlet for this ApplicationPortlet
     */
    public PortletDispatcher getPortletDispatcher(org.gridsphere.portlet.PortletRequest req, org.gridsphere.portlet.PortletResponse res) {

        //System.err.println("in getPortletDispatcher: cid=" + req.getAttribute(SportletProperties.COMPONENT_ID));
        String compVar = (String)req.getAttribute(SportletProperties.COMPONENT_ID_VAR);
        if (compVar == null) compVar = SportletProperties.COMPONENT_ID;
        String cid = (String) req.getAttribute(compVar);
        String pid = (String) req.getAttribute(SportletProperties.PORTLETID);
        // TODO fix my hack to get any render params and pass them as queryInfo to the portlet
        Map params = (Map) req.getAttribute(SportletProperties.RENDER_PARAM_PREFIX + pid + "_" + cid);
        StringBuffer extraInfo = new StringBuffer();

        //System.err.println("Dispatching: Looking for render params for " + SportletProperties.RENDER_PARAM_PREFIX + pid + "_" + cid);
        if (params == null) {
            params = new HashMap();
        }

        //params.put(SportletProperties.COMPONENT_ID, cid);
        boolean firstParam = true;

        Iterator it = params.keySet().iterator();
        try {
        while (it.hasNext()) {
            if (!firstParam) {
                extraInfo.append("&");
            } else {
                extraInfo.append("?");
            }
            String name = (String) it.next();

            // Render parameters that are passed on from the portlet frame are persistent across client requests
            // They are render param names already prefixed. We prefix them again so are can be selectively retrieved
            // in the nasty request parameter filter GridSphereParameters

            String encname = URLEncoder.encode("pr_" + name, "UTF-8");
            //String encname = URLEncoder.encode(name, "UTF-8");

            Object val = params.get(name);
            if (val instanceof String[]) {
                String[] vals = (String[]) val;
                for (int j = 0; j < vals.length - 1; j++) {
                    String encvalue = URLEncoder.encode(vals[j], "UTF-8");
                    extraInfo.append(encname);
                    extraInfo.append("=");
                    extraInfo.append(encvalue);
                    extraInfo.append("&");
                }
                String encvalue = URLEncoder.encode(vals[vals.length - 1], "UTF-8");
                extraInfo.append(encname);
                extraInfo.append("=");
                extraInfo.append(encvalue);
            } else if (val instanceof String) {
                String aval = (String) params.get(name);
                if ((aval != null) && (!aval.equals(""))) {
                    String encvalue = URLEncoder.encode(aval, "UTF-8");
                    extraInfo.append(encname);
                    extraInfo.append("=");
                    extraInfo.append(encvalue);
                } else {
                    extraInfo.append(encname);
                }
            }
            firstParam = false;
        }
        } catch (UnsupportedEncodingException e) {
            log.error("Unsupported encoding!", e);
        }

        // before it adds ".1" to real webappName
        //String realWebAppName = webAppName.substring(0, webAppName.length() - 2);

       
        //System.err.println("in getPortletDispatcher of jsr query string " + "/jsr/" + webAppName  + extraInfo);
        // TODO change dangerously hardcoded value!!!
        RequestDispatcher rd = context.getRequestDispatcher("/jsr/" + webAppName + extraInfo.toString());
        //RequestDispatcher rd = context.getNamedDispatcher(servletName);

        if (rd == null) {
            String msg = "Unable to create a dispatcher for portlet: " + portletName + "\n";
            msg += "Make sure the servlet mapping: /jsr/" + webAppName + " is defined in web.xml";
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

    public String getApplicationPortletClassName() {
        return portletClassName;
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

    public javax.portlet.PreferencesValidator getPreferencesValidator() {
        return prefsValidator;
    }

    public org.gridsphere.portletcontainer.jsrimpl.descriptor.PortletPreferences getPreferencesDescriptor() {
        return portletDef.getPortletPreferences();
    }

    public Portlet getPortletInstance() {
        return portletInstance;
    }

    public void setApplicationPortletStatus(PortletStatus status) {
        this.status = status;
    }

    public void setApplicationPortletStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public PortletStatus getApplicationPortletStatus() {
        return status;
    }

    public String getApplicationPortletStatusMessage() {
        return statusMessage;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("\t JSR Application Portlet:\n");
        sb.append("\t JSR Portlet Name: " + portletName + "\n");
        sb.append("\t Web app name: " + webAppName + "\n");
        /*
        if (portletDispatcher == null) {
            sb.append("\t Portlet dispatcher: NULL");
        } else {
            sb.append("\t Portlet dispatcher: OK");
        }
        */
        return sb.toString();
    }
}
