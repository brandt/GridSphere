<%@ page import="javax.portlet.RenderRequest"%>
<%@ page import="org.gridsphere.portlet.impl.SportletProperties" %>
<%@ page import="org.gridsphere.services.core.user.User" %>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<% RenderRequest req = (RenderRequest) pageContext.getAttribute("renderRequest");
    User user = (User) req.getAttribute(SportletProperties.PORTLET_USER);
    String username = user.getFirstName() + " " + user.getLastName(); %>

<span style="text-align: right; margin: 10px 10px 0px 0px; float:right; ">
<ui:text style="nostyle" key="LOGIN_SUCCESS"/>, <%= username %></span>

<div style="clear: both;"/>