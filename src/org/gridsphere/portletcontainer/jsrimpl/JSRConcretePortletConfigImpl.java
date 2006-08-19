/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: JSRConcretePortletConfigImpl.java 5032 2006-08-17 18:15:06Z novotny $
 */
package org.gridsphere.portletcontainer.jsrimpl;

import org.gridsphere.portletcontainer.ConcretePortletConfig;
import org.gridsphere.portletcontainer.jsrimpl.descriptor.*;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class JSRConcretePortletConfigImpl implements ConcretePortletConfig {

    private Locale[] supportedLocales = null;
    private Map descsMap = null;
    private Map dispsMap = null;
    private String requiredRole = "";
    private String portletName = "Unknown portlet";

    public JSRConcretePortletConfigImpl(PortletDefinition portletDef) {

        // supported locales
        supportedLocales = new Locale[portletDef.getSupportedLocaleCount()];
        SupportedLocale[] suppLocs = portletDef.getSupportedLocale();
        for (int i = 0; i < suppLocs.length; i++) {
            supportedLocales[i] = new Locale(suppLocs[i].getContent(), "", "");
        }

        // portlet descriptions
        Description[] descs = portletDef.getDescription();
        descsMap = new HashMap();
        for (int i = 0; i < descs.length; i++) {
            descsMap.put(descs[i].getLang(), descs[i].getContent());
        }

        // portlet display names
        DisplayName[] disps = portletDef.getDisplayName();
        dispsMap = new HashMap();
        for (int i = 0; i < disps.length; i++) {
            dispsMap.put(disps[i].getLang(), disps[i].getContent());
        }

        // portlet name
        portletName = portletDef.getPortletName().getContent();

        // get required role
        SecurityRoleRef[] secRoleRef = portletDef.getSecurityRoleRef();
        for (int i = 0; i < secRoleRef.length; i++) {
            String roleStr = secRoleRef[i].getRoleName().getContent();
            requiredRole = roleStr;
        }


    }

    /**
     * Returns the default/supported locale of a portlet
     *
     * @return the default locale of the portlet
     */
    public String getDefaultLocale() {
        return supportedLocales[0].getLanguage();
    }

    public Locale[] getSupportedLocales() {
        return supportedLocales;
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

}