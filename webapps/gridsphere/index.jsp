<%@ page import="javax.portlet.PortletURL" %>
<%@ page import="org.gridsphere.portlet.impl.PortletURLImpl" %>
<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8" %>


<% PortletURL purl = new PortletURLImpl(request, response, true); %>
<% System.err.println("url = " + purl); %>
<% response.sendRedirect(purl.toString()); %>
