<%@ page import="java.util.Set" %>
<%@ page import="java.util.Iterator" %>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>


<% Set labelSet = (Set) request.getAttribute("labelSet"); %>

<h3><ui:actionlink action="doEditActions" key="TRACKING_ADD_ACTION"/></h3>

<ui:group key="TRACKING_PORTLET_LABEL">

    <ui:form>
        <p>
        <ui:checkbox beanId="trackPortletsCB"/>
        <ui:text key="TRACKING_PORTLET_COUNTER" style="plain"/>
         <p>
        <ui:actionsubmit action="savePortletCounter" key="SAVE"/>
        </p>
    </ui:form>

</ui:group>


<h3><ui:text key="TRACKING_STATS" style="nostyle"/></h3>

<p>
    <ui:text key="TRACKING_SELECT_MSG"/>
</p>
<% Iterator it = labelSet.iterator(); %>
<% if (it.hasNext()) { %>
<ul>
    <% while (it.hasNext()) { %>
    <% String label = (String) it.next(); %>
    <li><ui:actionlink action="showLabel" value="<%= label %>">
        <ui:actionparam name="label" value="<%= label %>"/>
    </ui:actionlink>
    </li>
    <% } %>
</ul>
<% } %>