/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: JSRApplicationPortletImpl.java 4985 2006-08-04 09:54:28Z novotny $
 */
package org.gridsphere.portletcontainer.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gridsphere.portlet.impl.PortalContextImpl;
import org.gridsphere.portlet.impl.SportletProperties;
import org.gridsphere.portletcontainer.ApplicationPortlet;
import org.gridsphere.portletcontainer.PortletDispatcher;
import org.gridsphere.portletcontainer.PortletPreferencesManager;
import org.gridsphere.portletcontainer.PortletStatus;
import org.gridsphere.portletcontainer.impl.descriptor.*;
import org.gridsphere.services.core.persistence.PersistenceManagerException;

import javax.portlet.PortalContext;
import javax.portlet.Portlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * The <code>ApplicationPortletImpl</code> is an implementation of the <code>ApplicationPortlet</code> interface
 * that uses Castor for XML to Java data bindings.
 * <p/>
 * The <code>ApplicationPortlet</code> represents the portlet application instance
 * defined in the portlet descriptor file.
 */
public class ApplicationPortletImpl implements ApplicationPortlet, Comparable {

    private Log log = LogFactory.getLog(ApplicationPortletImpl.class);
    private PortletDefinition portletDef = null;

    private String portletClassName = null;
    private String webAppName = null;

    private Locale compareLocale = null;

    private Portlet portletInstance = null;

    private ServletContext context = null;
    private PortalContext portalContext = null;
    private javax.portlet.PreferencesValidator prefsValidator = null;

    private int expiration = 0;

    private Map<String, Set<String>> markupModes = new HashMap<String, Set<String>>();
    private List<javax.portlet.WindowState> states = new ArrayList<javax.portlet.WindowState>();

    private PortletDeploymentDescriptor portletDD = null;
    private String concreteID = null;
    private String portletName = null;

    private Locale[] supportedLocales = null;
    private Map<String, String> descsMap = null;
    private Map<String, String> dispsMap = null;
    private String requiredRole = "";

    protected PortletStatus status = PortletStatus.SUCCESS;
    protected String statusMessage = "JSR Portlet loaded successfully";

    protected PortletPreferencesManager prefsManager = null;

    /**
     * Default constructor is private
     */
    protected ApplicationPortletImpl() {
    }

    /**
     * Constructs an instance of ApplicationPortletImpl
     *
     * @param pdd            the <code>PortletDeploymentDescriptor</code>
     * @param portletDef     the portlet definition
     * @param webApplication the ui application name for this application portlet
     * @param context        the <code>ServletContext</code> containing this application portlet
     */
    public ApplicationPortletImpl(PortletDeploymentDescriptor pdd, PortletDefinition portletDef, String webApplication, ServletContext context) {
        this.portletDef = portletDef;
        this.webAppName = webApplication;
        this.portletClassName = portletDef.getPortletClass().getContent();
        this.portletName = portletDef.getPortletName().getContent();
        this.context = context;
        PortletApp portletApp = pdd.getPortletWebApplication();

        SupportedLocale[] locales = portletDef.getSupportedLocale();
        supportedLocales = new Locale[locales.length];
        for (int i = 0; i < locales.length; i++) {
            supportedLocales[i] = new Locale(locales[i].getContent());
        }

        // create portal context
        portalContext = new PortalContextImpl(pdd.getPortletWebApplication());

        portletName = portletDef.getPortletName().getContent();
        concreteID = webAppName + "#" + portletName;

        // supported locales
        supportedLocales = new Locale[portletDef.getSupportedLocaleCount()];
        SupportedLocale[] suppLocs = portletDef.getSupportedLocale();
        for (int i = 0; i < suppLocs.length; i++) {
            supportedLocales[i] = new Locale(suppLocs[i].getContent(), "", "");
        }

        // portlet descriptions
        Description[] descs = portletDef.getDescription();
        descsMap = new HashMap<String, String>();
        for (int i = 0; i < descs.length; i++) {
            descsMap.put(descs[i].getLang(), descs[i].getContent());
        }

        // portlet display names
        DisplayName[] disps = portletDef.getDisplayName();
        dispsMap = new HashMap<String, String>();
        for (int i = 0; i < disps.length; i++) {
            dispsMap.put(disps[i].getLang(), disps[i].getContent());
        }

        // get required role
        SecurityRoleRef[] secRoleRef = portletDef.getSecurityRoleRef();
        for (int i = 0; i < secRoleRef.length; i++) {
            requiredRole = secRoleRef[i].getRoleName().getContent();
        }

        //String id = portletDef.getPortletClass().getContent();
        this.portletName = portletDef.getPortletName().getContent();
        if (portletDef.getExpirationCache() != null) {
            expiration = portletDef.getExpirationCache().getContent();
        }

        Supports[] supports = portletDef.getSupports();
        // defined portlet modes
        for (int i = 0; i < supports.length; i++) {
            Set<String> modesAllowed = new HashSet<String>();
            Supports s = (Supports) supports[i];
            org.gridsphere.portletcontainer.impl.descriptor.PortletMode[] modes = (org.gridsphere.portletcontainer.impl.descriptor.PortletMode[]) s.getPortletMode();
            for (int j = 0; j < modes.length; j++) {
                org.gridsphere.portletcontainer.impl.descriptor.PortletMode m = modes[j];
                modesAllowed.add(m.getContent());
            }
            modesAllowed.add(javax.portlet.PortletMode.VIEW.toString());
            String mimeType = (String) s.getMimeType().getContent();

            markupModes.put(mimeType, modesAllowed);

        }

        List<String> customStatesList = new ArrayList<String>();
        CustomWindowState[] customStates = portletApp.getCustomWindowState();
        if (customStates != null) {
            for (int i = 0; i < customStates.length; i++) {
                customStatesList.add(customStates[i].getWindowState().getContent());
            }
        }

        // defined window states
        if (!customStatesList.isEmpty()) {
            for (int i = 0; i < supports.length; i++) {
                Supports s = (Supports) supports[i];
                org.gridsphere.portletcontainer.impl.descriptor.WindowState[] statesAllowed = (org.gridsphere.portletcontainer.impl.descriptor.WindowState[]) s.getWindowState();
                if (statesAllowed != null) {
                    for (int j = 0; j < statesAllowed.length; j++) {
                        org.gridsphere.portletcontainer.impl.descriptor.WindowState w = statesAllowed[j];
                        if (customStatesList.contains(w.getContent()))
                            states.add(new javax.portlet.WindowState(w.getContent()));
                    }
                }
            }
        }
        states.add(javax.portlet.WindowState.MAXIMIZED);
        states.add(javax.portlet.WindowState.MINIMIZED);
        states.add(javax.portlet.WindowState.NORMAL);


        org.gridsphere.portletcontainer.impl.descriptor.PortletPreferences prefDesc = portletDef.getPortletPreferences();
        if (prefDesc != null) {
            org.gridsphere.portletcontainer.impl.descriptor.PreferencesValidator validator = prefDesc.getPreferencesValidator();
            if (validator != null) {
                String validatorClass = validator.getContent();
                if (validatorClass != null) {
                    try {
                        prefsValidator = (javax.portlet.PreferencesValidator) Class.forName(validatorClass).newInstance();
                    } catch (Exception e) {
                        log.error("Unable to create validator: " + validatorClass + "! ", e);
                    }
                }
            }
        }

        prefsManager = new PortletPreferencesManagerImpl(prefDesc, prefsValidator);

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
    public PortletDispatcher getPortletDispatcher(HttpServletRequest req, HttpServletResponse res) {

        //System.err.println("in getPortletDispatcher: cid=" + req.getAttribute(SportletProperties.COMPONENT_ID));
        String cid = (String) req.getAttribute(SportletProperties.COMPONENT_ID);
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
        return new PortletDispatcherImpl(rd);
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
        if (locale == null) throw new IllegalArgumentException("supplied locale cannot be null!");
        Description[] descs = portletDef.getDescription();
        for (int i = 0; i < descs.length; i++) {
            if (descs[i].getLang().equals(locale.getLanguage())) {
                return descs[i].getContent();
            }
        }
        return "Unknown portlet description";
    }

    public String getPortletDisplayName(Locale locale) {
        if (locale == null) throw new IllegalArgumentException("supplied locale cannot be null!");
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

    public SecurityRoleRef[] getSecurityRoleRefs() {
        return portletDef.getSecurityRoleRef();
    }

    public PortletPreferencesManager getPortletPreferencesManager(String portletId, String userId, boolean isRender) {
        prefsManager.setPortletId(portletId);
        prefsManager.setUserId(userId);
        prefsManager.setRender(isRender);
        return prefsManager;
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

    /**
     * Returns the portlet application name
     *
     * @return the portlet application name
     */
    public String getPortletName() {
        return portletName;
    }

    /**
     * Sets the name of a PortletApplication
     *
     * @param portletName name of a PortletApplication
     */
    public void setPortletName(String portletName) {
        this.portletName = portletName;
    }

    /**
     * Returns the allowed window states supported by this portlet
     *
     * @return the <code>List</code> of
     *         <code>WindowState</code> elements allowed for this portlet
     */
    public List<javax.portlet.WindowState> getAllowedWindowStates() {
        return Collections.unmodifiableList(states);
    }

    /**
     * Returns the supported modes for this portlet
     *
     * @return the supported modes for this portlet
     */
    public Set<String> getSupportedModes(String markup) {
        if (markup == null) throw new IllegalArgumentException("Supplied markup cannot be null!");
        for (String mimeType : markupModes.keySet()) {
            int idx1 = mimeType.indexOf(markup);
            int idx2 = markup.indexOf(mimeType);
            if ((idx1 > 0) || (idx2 > 0) || (mimeType.equalsIgnoreCase(markup))) {
                return markupModes.get(mimeType);
            }
        }
        return new TreeSet<String>();
    }

    public SortedSet<String> getSupportedMimeTypes(javax.portlet.PortletMode mode) {
        if (mode == null) throw new IllegalArgumentException("Supplied mode cannot be null!");
        SortedSet<String> mimeTypes = new TreeSet<String>();
        for (String mimeType : markupModes.keySet()) {
            Set modes = markupModes.get(mimeType);
            if (modes.contains(mode.toString())) {
                mimeTypes.add(mimeType);
            }
        }
        return mimeTypes;
    }

    /**
     * returns the amount of time in seconds that a portlet's content should be cached
     *
     * @return the amount of time in seconds that a portlet's content should be cached
     */
    public long getCacheExpires() {
        return expiration;
    }

    /**
     * Returns the concrete portlet id
     *
     * @return the concrete portlet id
     */
    public String getConcretePortletID() {
        return concreteID;
    }

    /**
     * Returns the default/supported locale of a portlet
     *
     * @return the default locale of the portlet
     */
    public String getDefaultLocale() {
        return supportedLocales[0].getLanguage();
    }

    public String getDescription(Locale loc) {
        String desc = (String) descsMap.get(loc.getLanguage());
        if (desc == null) {
            desc = portletName;
        }
        return desc;
    }

    public String getDisplayName(Locale loc) {
        String disp = (String) dispsMap.get(loc.getLanguage());
        if (disp == null) {
            disp = portletName;
        }
        return disp;
    }

    /**
     * Returns the required portlet role necessary to access this portlet
     *
     * @return the required portlet role necessary to access this portlet
     */
    public String getRequiredRole() {
        return requiredRole;
    }

    /**
     * Sets the required portlet role necessary to access this portlet
     *
     * @param role the required portlet role necessary to access this portlet
     */
    public void setRequiredRole(String role) {
        this.requiredRole = role;
    }


    /**
     * Saves any concrete portlet changes to the descriptor
     *
     * @throws java.io.IOException if an I/O error occurs
     */
    public void save() throws IOException {
        try {
            portletDD.save();
        } catch (PersistenceManagerException e) {
            throw new IOException("Unable to save JSR concrete portlet: " + e.getMessage());
        }
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("\t JSR Application Portlet:\n");
        sb.append("\t JSR Portlet Name: ").append(portletName).append("\n");
        sb.append("\t Web app name: ").append(webAppName).append("\n");
        sb.append("\t concrete ID: ").append(concreteID).append("\n");
        sb.append("\t Status: ").append(status).append("\n");
        sb.append("\t Status message: ").append(statusMessage).append("\n");
        /*
        if (portletDispatcher == null) {
            sb.append("\t Portlet dispatcher: NULL");
        } else {
            sb.append("\t Portlet dispatcher: OK");
        }
        */
        return sb.toString();
    }

    public void setCompareLocale(Locale loc) {
        compareLocale = loc;
    }

    public int compareTo(Object o) {
        ApplicationPortletImpl otherApplicationPortlet = (ApplicationPortletImpl) o;
        if (compareLocale == null)
            return getDisplayName(new Locale(getDefaultLocale())).
                    compareToIgnoreCase(otherApplicationPortlet.getDisplayName(new Locale(getDefaultLocale())));
        else
            return getDisplayName(compareLocale).compareToIgnoreCase(otherApplicationPortlet.getDisplayName(compareLocale));
    }
}
