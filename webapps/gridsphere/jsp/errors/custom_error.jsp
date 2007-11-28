<%@ page import="org.gridsphere.provider.portletui.beans.MessageStyle"%>
<%@ page import="java.io.StringWriter" %>
<%@ taglib uri="/portletUI" prefix="ui" %>


<% Throwable error = (Throwable)request.getAttribute("error"); %>
<% String lastFrame = (String)request.getAttribute("lastFrame"); %>

<% if (error != null) { %>
<ui:messagebox style="<%= MessageStyle.MSG_ALERT %>" value="An error occurred!"/>

<% if (error.getMessage() != null) {
        String errorMsg = error.getMessage().replaceAll("<","&lt;").replaceAll(">","&gt;");
%>
Error message:    <%= errorMsg %>
<% } %>

<p>
    <b>Stack Trace:</b><br/>
    <%
        StringWriter stringWriter = new StringWriter();
        error.printStackTrace(new java.io.PrintWriter(stringWriter));
    %><%= stringWriter.toString().replaceAll("<","&lt;").replaceAll(">","&gt;") %>
</p>

<% } else { %>

<ui:messagebox style="<%= MessageStyle.MSG_ALERT %>" key="PORTAL_ERROR_MSG"/>

<%= lastFrame %>
<% } %>