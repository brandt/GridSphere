<%@ page import="java.util.List,
                 java.util.Iterator,
                 org.gridlab.gridsphere.portlets.core.beans.TomcatWebAppResult"%>


<jsp:useBean id="result" class="org.gridlab.gridsphere.portlets.core.beans.TomcatWebAppResult" scope="request"/>

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
<% TomcatWebAppResult.TomcatWebAppDescription description = (TomcatWebAppResult.TomcatWebAppDescription)it.next(); %>
    <tr>
    <td><%= description.getContextPath() %></td>
    <td><%= description.getRunning() %></td>
    <td><%= description.getSessions() %></td>
    <td><%= description.getActions() %></td>
    </tr>

<% } %>

</table>