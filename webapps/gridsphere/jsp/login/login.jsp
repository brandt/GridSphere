<%@ page import="org.gridlab.gridsphere.portlet.GuestUser"%>

<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>
<jsp:useBean id="user" class="org.gridlab.gridsphere.portlet.User" scope="request"/>

<% if ((user == null) || (user instanceof GuestUser)) { %>

<%@ include file="showlogin.jsp" %>

<% } else { %>

<%@ include file="viewuser.jsp" %>

<% } %>
