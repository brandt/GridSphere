<!-- $Id$ -->

<!-- Demonstration of Simple HelloWorld Portlet JSP -->
<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>
Hello, World !

<% out.println("hello from java-jsp"); %>

<gs:actionlink action="sendmessage" label="send message to HalloWelt"/>


