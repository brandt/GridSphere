<%@ page import="java.util.List,
                 java.util.Iterator,
                 org.gridsphere.portlet.User" %>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<% List uids = (List) request.getAttribute("uids"); %>
<% String numSessions = (String) request.getAttribute("num_sessions"); %>
<p>
    <ui:text key="SESSION_ACTIVE"/> <%= numSessions %>


    <br/>

    <ui:text key="SESSION_USERS"/> <%= uids.size() %>
</p>
<ui:table sortable="true" zebra="true" maxrows="10">

    <ui:tablerow header="true">
        <ui:tablecell>
            <ui:text key="USERNAME"/>
        </ui:tablecell>
        <ui:tablecell>
            <ui:text key="FULLNAME"/>
        </ui:tablecell>
        <ui:tablecell>
            <ui:text key="EMAILADDRESS"/>
        </ui:tablecell>
    </ui:tablerow>

    <% Iterator it = uids.iterator();
        while (it.hasNext()) {

            User s = (User) it.next();  %>
    <ui:tablerow>
        <ui:tablecell>
            <%= s.getUserName() %>
        </ui:tablecell>
        <ui:tablecell>
            <%= s.getFullName() %>
        </ui:tablecell>
        <ui:tablecell>
            <%= s.getEmailAddress() %>
        </ui:tablecell>
    </ui:tablerow>


    <% } %>

</ui:table>