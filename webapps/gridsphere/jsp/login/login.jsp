<%@ page import="org.gridlab.gridsphere.portlet.User,
                 org.gridlab.gridsphere.portlet.impl.SportletProperties,
                 org.gridlab.gridsphere.portlet.GuestUser"%>

<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<% User user = (User)request.getAttribute("user"); %>

<% if ((user == null) || (user instanceof GuestUser)) { %>

<%@ include file="showlogin.jsp" %>

<% } else { %>

<%@ include file="viewuser.jsp" %>

<% } %>
