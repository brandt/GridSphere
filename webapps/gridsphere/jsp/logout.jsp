<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<% String logout = (String)request.getAttribute("org.gridlab.gridsphere.portlets.core.LogoutPortlet.title"); %>
<gs:actionlink action="logout" label="<%= logout %>"/>


