<%@ page import="java.util.List,
                 java.util.Iterator"%>

<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<jsp:useBean id="result" class="org.gridlab.gridsphere.portlets.manager.tomcat.TomcatWebAppResult" scope="request"/>

<% String error = (String)request.getAttribute("ERROR"); %>
<% if (error != null) { %>
<b><%= error %></b>
<% } %>
<table border="1" cellspacing="0" cellpadding="3">
<tr>
 <td colspan="10">Applications</td>
</tr>
<tr>
 <td>Path</td>
 <td>Sessions</td>
 <td>Running</td>
 <td>Actions</td>
</tr>

<% List descriptions = result.getWebAppDescriptions(); %>
<% Iterator it = descriptions.iterator(); %>
<% while (it.hasNext()) { %>
<% org.gridlab.gridsphere.portlets.manager.tomcat.TomcatWebAppResult.TomcatWebAppDescription description = (org.gridlab.gridsphere.portlets.manager.tomcat.TomcatWebAppResult.TomcatWebAppDescription)it.next(); %>
    <tr>
    <td><%= description.getContextPath() %></td>
    <td><%= description.getRunning() %></td>
    <td><%= description.getSessions() %></td>
    <td>
        <gs:actionlink action="manager" label="start">
            <gs:actionparam name="operation" value="start"/>
            <gs:actionparam name="context" value="<%= description.getContextPath() %>"/>
        </gs:actionlink>
        <gs:actionlink action="manager" label="stop">
            <gs:actionparam name="operation" value="stop"/>
            <gs:actionparam name="context" value="<%= description.getContextPath() %>"/>
        </gs:actionlink>
        <gs:actionlink action="manager" label="reload">
            <gs:actionparam name="operation" value="reload"/>
            <gs:actionparam name="context" value="<%= description.getContextPath() %>"/>
        </gs:actionlink>
        <gs:actionlink action="manager" label="remove">
            <gs:actionparam name="operation" value="remove"/>
            <gs:actionparam name="context" value="<%= description.getContextPath() %>"/>
        </gs:actionlink>
    </td>
    </tr>

<% } %>

</table>

<table border=0 cellpadding=1 cellspacing=0 width="180" bgcolor=6699CC><tr><td><font color=FFFFFF><b>&nbsp;Upload WAR File</b></font></td></tr>
</table>

<table border=0 cellpadding=0 cellspacing=0 width="178">
<tr>
<td width=1 bgcolor=cccccc>
<spacer type=block width=1></td>
<td width=1000>

<table border=0 cellpadding=3 cellspacing=0 width="178"><tr><td valign=top>

    <gs:fileform action="install">
    <table cellspacing=2 cellpadding=2 border=0>
    <tr>
    <td align="right">File: </td>
    <td align="left"><gs:fileinput name="filename" size="8" maxlength="20"/></td>
    </tr>
    <tr>
    <td colspan=5 align="center"><gs:submit name="option" value="Login"/></td>
    </tr>
    </table>
    </gs:fileform>

</td>
</tr>
</table>

</td>
<td width=1 bgcolor=cccccc>
<spacer type=block width=1>
</td>
</tr>
<tr>
<td height=1 colspan=3 bgcolor=cccccc>
<spacer type=block height=1></td></tr>
</table>
