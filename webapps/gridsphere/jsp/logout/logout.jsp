<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<jsp:useBean id="username" class="java.lang.String" scope="request"/>

<ui:text key="LOGIN_SUCCESS"/>, <%= username %>
     