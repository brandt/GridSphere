<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<jsp:useBean id="GRIDSPHERE_LOGOUT_LABEL" class="java.lang.String" scope="request"/>

<ui:actionlink action="gs_logout" value="<%= GRIDSPHERE_LOGOUT_LABEL %>"/>


