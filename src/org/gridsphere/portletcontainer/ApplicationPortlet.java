package org.gridsphere.portletcontainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.portlet.PortalContext;
import javax.portlet.Portlet;
import java.util.Locale;
import java.util.List;
import java.io.IOException;

/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
public interface ApplicationPortlet {

    String getWebApplicationName();

    String getApplicationPortletID();

    PortletDispatcher getPortletDispatcher(HttpServletRequest req, HttpServletResponse res);

    String getApplicationPortletName();

    String getApplicationPortletClassName();

    PortalContext getPortalContext();

    String getPortletDescription(Locale locale);

    String getPortletDisplayName(Locale locale);

    int getExpirationCache();

    Locale[] getSupportedLocales();

    javax.portlet.PreferencesValidator getPreferencesValidator();

    org.gridsphere.portletcontainer.impl.descriptor.PortletPreferences getPreferencesDescriptor();

    Portlet getPortletInstance();

    void setApplicationPortletStatus(PortletStatus status);

    void setApplicationPortletStatusMessage(String statusMessage);

    PortletStatus getApplicationPortletStatus();

    String getApplicationPortletStatusMessage();

    String getPortletName();

    void setPortletName(String portletName);

    List getAllowedWindowStates();

    List getSupportedModes(String markup);

    long getCacheExpires();

    String getConcretePortletID();

    String getDefaultLocale();

    String getDescription(Locale loc);

    String getDisplayName(Locale loc);

    String getRequiredRole();

    void setRequiredRole(String role);

    void save() throws IOException;
}
