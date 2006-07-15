<%@ page import="org.gridlab.gridsphere.portlet.impl.SportletProperties"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<jsp:useBean id="GRIDSPHERE_LOGOUT_LABEL" class="java.lang.String" scope="request"/>
<jsp:useBean id="username" class="java.lang.String" scope="request"/>


<table height="72">
<tbody><tr>
<td align="center" valign="bottom">
<ui:actionlink action="<%= SportletProperties.LOGOUT %>" value="<%= GRIDSPHERE_LOGOUT_LABEL %>" style="bold"/>
</td></tr>
<tr><td align="center" valign="top">
<ui:text key="LOGIN_SUCCESS"/>, <%= username %>
</td></tr>
</tbody></table>
