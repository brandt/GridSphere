<%@ page import="java.util.List,
                 java.util.Iterator"%>

<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<jsp:useBean id="result" class="org.gridlab.gridsphere.portlets.manager.tomcat.TomcatWebAppResult" scope="request"/>

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