<%@ page import="org.gridsphere.portlet.impl.SportletProperties" %>
<%@ page import="org.gridsphere.portlet.service.spi.PortletServiceFactory" %>
<%@ page import="org.gridsphere.services.core.customization.SettingsService" %>
<%@ page import="org.gridsphere.services.core.user.User" %>
<%@ page import="javax.portlet.RenderRequest" %>
<%@ page import="java.io.File" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.util.Properties" %>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<% RenderRequest req = (RenderRequest) pageContext.getAttribute("renderRequest");
    User user = (User) req.getAttribute(SportletProperties.PORTLET_USER);
    String username = user.getFirstName() + " " + user.getLastName();

    SettingsService settingsService = (SettingsService) PortletServiceFactory.createPortletService(SettingsService.class, true);
    String path = settingsService.getRealSettingsPath("navigation.properties");

    String label = "";
    if (new File(path).exists()) {
        FileInputStream fis = new FileInputStream(path);
        Properties prop = new Properties();
        prop.load(fis);
        label = prop.getProperty("homelabel", "");
    }


%>

<div class="gridsphere-navbar">
    <ul>
        <li>
            <ui:text style="nostyle" key="LOGIN_SUCCESS"/>
            , <%= username %>
        </li>
        <ui:hasrole role="ADMIN">
            <li>
                <ui:actionlink layout="admin" key="NAV_ADMIN"/>
            </li>
            <li>
                <ui:actionlink layout="content" key="NAV_CONTENT"/>
            </li>
            <li>
                <ui:actionlink layout="layout" key="NAV_LAYOUT"/>
            </li>
        </ui:hasrole>
        <li>
            <ui:actionlink layout="profile" key="NAV_PROFILE"/>
        </li>
        <li>
            <ui:actionlink layout="loggedin" label="<%=label%>" key="NAV_HOME"/>
        </li>
        <li>
            <ui:actionlink action="<%= SportletProperties.LOGOUT %>" key="LOGOUT"/>
        </li>
    </ul>
</div>