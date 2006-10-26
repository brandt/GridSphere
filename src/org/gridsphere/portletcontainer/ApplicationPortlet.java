package org.gridsphere.portletcontainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.portlet.PortalContext;
import javax.portlet.Portlet;
import javax.portlet.PortletMode;
import java.util.Locale;
import java.util.List;
import java.util.SortedSet;
import java.util.Set;
import java.io.IOException;

/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
public interface ApplicationPortlet {

    public String getWebApplicationName();

    public String getApplicationPortletID();

    public PortletDispatcher getPortletDispatcher(HttpServletRequest req, HttpServletResponse res);

    public String getApplicationPortletName();

    public String getApplicationPortletClassName();

    public PortalContext getPortalContext();

    public String getPortletDescription(Locale locale);

    public String getPortletDisplayName(Locale locale);

    public int getExpirationCache();

    public Locale[] getSupportedLocales();

    public PortletPreferencesManager getPortletPreferencesManager(String portletId, String userId, boolean isRender);

    public Portlet getPortletInstance();

    public void setApplicationPortletStatus(PortletStatus status);

    public void setApplicationPortletStatusMessage(String statusMessage);

    public PortletStatus getApplicationPortletStatus();

    public String getApplicationPortletStatusMessage();

    public String getPortletName();

    public List<javax.portlet.WindowState> getAllowedWindowStates();

    public Set<String> getSupportedModes(String markup);

    public SortedSet<String> getSupportedMimeTypes(PortletMode mode);

    public long getCacheExpires();

    public String getConcretePortletID();

    public String getDefaultLocale();

    public String getDescription(Locale loc);

    public String getDisplayName(Locale loc);

    public String getRequiredRole();

    public void setRequiredRole(String role);

    public void save() throws IOException;
}
