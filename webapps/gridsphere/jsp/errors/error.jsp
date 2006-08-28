<%@ page import="org.gridsphere.portlet.impl.SportletProperties"%>

<% String errorPage = request.getParameter("errorPage");

if (errorPage == null) {
    errorPage = (String)request.getAttribute("errorPage");
}

if (errorPage == null) { %>
    <h2>Some Unknown error occurred!</h2>
<%  } else { %>

    <jsp:include page="<%= errorPage %>"/>

<% } %>

<h2><a href="<%= request.getContextPath() %>">Return to Portal</a></h2>

<% request.getSession().removeAttribute(SportletProperties.LAYOUT_PAGE); %>