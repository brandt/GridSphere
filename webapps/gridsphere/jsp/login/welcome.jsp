<%@ page import="org.gridsphere.portlet.PortletRequest"%>
<%@ page import="org.gridsphere.portlet.User"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<% PortletRequest req = (PortletRequest)pageContext.getAttribute("portletRequest");
   User user = req.getUser();
   String username = user.getFirstName() + " " + user.getLastName(); %>

<span style="text-align: right; margin: 10px 10px 0px 0px; float:right; ">
<ui:text style="nostyle" key="LOGIN_SUCCESS"/>, <%= username %></span>

<div style="clear: both;"/>