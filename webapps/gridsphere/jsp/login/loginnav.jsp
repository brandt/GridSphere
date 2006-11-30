<%@ page import="org.gridsphere.services.core.portal.PortalConfigService" %>
<%@ page import="org.gridsphere.portlet.service.spi.PortletServiceFactory" %>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<div class="gridsphere-navbar">
    <ul>
        <li>
      <%--     <ui:portletlink close="true" resizable="true" portletId="gridsphere#Login" titleColor="green" name="bar" value="Login" width="300"/> --%>

         <ui:actionlink layout="LoginLayout" value="Login"/>
        </li>
        <% PortalConfigService portalConfigService = (PortalConfigService) PortletServiceFactory.createPortletService(PortalConfigService.class, true);
    if (Boolean.valueOf(portalConfigService.getProperty(PortalConfigService.CAN_USER_CREATE_ACCOUNT)).booleanValue()) {
%>
        <li>
            <ui:actionlink layout="RegisterLayout" value="Register" label="login" action="doNewUser"/>
        </li>
       <% } %>
    </ul>
</div>

