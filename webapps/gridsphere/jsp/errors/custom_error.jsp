<%@ page import="org.gridsphere.provider.portletui.beans.MessageStyle"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>


<% Throwable error = (Throwable)request.getAttribute("error"); %>
<% String lastFrame = (String)request.getAttribute("lastFrame"); %>

<% if (error != null) { %>
<ui:messagebox style="<%= MessageStyle.MSG_ALERT %>" value="An error occurred!"/>

<% if (error.getMessage() != null) { %>
Error message:    <%= error.getMessage() %>
<% } %>

<p>
    <b>Stack Trace:</b><br/>
    <% error.printStackTrace(new java.io.PrintWriter(out)); %>
</p>

<% } else { %>

<ui:messagebox style="<%= MessageStyle.MSG_ALERT %>" key="PORTAL_ERROR_MSG"/>

<%= lastFrame %>
<% } %>