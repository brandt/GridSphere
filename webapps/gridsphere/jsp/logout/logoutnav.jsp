<%@ page import="org.gridsphere.portlet.impl.SportletProperties" %>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<ui:actionlink cssStyle="font-size: 10px; text-decoration: underline;" action="<%= SportletProperties.LOGOUT %>" key="LOGOUT"/>


