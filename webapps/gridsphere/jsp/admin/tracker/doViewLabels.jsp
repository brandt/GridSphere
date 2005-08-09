<%@ page import="java.util.Set"%>
<%@ page import="java.util.Iterator"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>


<% Set labelSet = (Set)request.getAttribute("labelSet"); %>

<h2><ui:text key="TRACKING_STATS" style="nostyle"/></h2>
<p>
<ui:text key="TRACKING_SELECT_MSG"/>
</p>
<p>

<ul>
<% Iterator it = labelSet.iterator(); %>
<% while (it.hasNext()) { %>
    <% String label = (String)it.next(); %>
<li><ui:actionlink action="showLabel" value="<%= label %>">
        <ui:actionparam name="label" value="<%= label %>"/>
    </ui:actionlink>
</li>
<% } %>
</ul>

</p>
