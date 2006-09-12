<%@ page import="org.gridsphere.services.core.portal.PortalConfigService"%>
<%@ page import="org.gridsphere.portlet.service.spi.PortletServiceFactory"%>

<%  PortalConfigService portalConfigService = (PortalConfigService)PortletServiceFactory.createPortletService(PortalConfigService.class, true);
    if  (Boolean.valueOf(portalConfigService.getProperty("CAN_USER_CREATE_ACCOUNT")).booleanValue()) {
 %>
<a style="font-size: 10px; text-decoration: underline;" href="<%= request.getContextPath() %><%= request.getServletPath()%>/LoginLayout/login/doNewUser">Register</a>

<% } %>