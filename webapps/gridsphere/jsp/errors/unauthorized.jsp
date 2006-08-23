<% response.addHeader("WWW-Authenticate", "BASIC realm=\"GridSphere Portal\""); %>
<% response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); %>

<%@ include file="/WEB-INF/CustomPortal/content/pagehead.html" %>

<h2>Unauthorized Access!</h2>

<a href="<%= request.getContextPath() %>">Return to Portal</a>
