<%@ page import="org.gridsphere.portlet.service.spi.PortletServiceFactory" %>
<%@ page import="org.gridsphere.services.core.portal.PortalConfigService" %>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<div class="gridsphere-navbar">
    <ul>
        <li>
            <ui:renderlink layout="login" value="Login"/>
        </li>
        <% PortalConfigService portalConfigService = (PortalConfigService) PortletServiceFactory.createPortletService(PortalConfigService.class, true);
            if (Boolean.valueOf(portalConfigService.getProperty(PortalConfigService.CAN_USER_CREATE_ACCOUNT)).booleanValue()) {
        %>
        <li>
            <ui:renderlink layout="register" value="Register" label="register" render="doNewUser"/>
        </li>
        <% } %>
    </ul>
</div>

