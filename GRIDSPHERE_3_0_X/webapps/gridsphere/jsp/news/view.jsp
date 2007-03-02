<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<portlet:defineObjects/>

<ui:messagebox beanId="msg"/>

<jsp:useBean id="document" class="java.lang.String" scope="request"/>

<%=document%>

 