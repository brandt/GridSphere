<%@ page import="java.util.Iterator,
                 java.util.List,
                 org.gridsphere.portlet.User" %>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<ui:messagebox beanId="msg"/>

<% List userList = (List) request.getAttribute("userList"); %>

<% Integer numUsers = (Integer)request.getAttribute("numUsers"); %>

<h3><ui:text key="USER_SHOW_USERS" style="nostyle"/></h3>

<ui:form>

    <ui:table sortable="true" zebra="true" maxrows="20" numentries="<%= numUsers.intValue() %>" filter="true">
        <ui:tablerow header="true">
            <ui:tablecell><ui:text key="USERNAME"/></ui:tablecell>
            <ui:tablecell><ui:text key="FULLNAME"/></ui:tablecell>
            <ui:tablecell><ui:text key="EMAILADDRESS"/></ui:tablecell>
            <ui:tablecell><ui:text key="ORGANIZATION"/></ui:tablecell>
        </ui:tablerow>
        <%
            Iterator userIterator = userList.iterator();
            while (userIterator.hasNext()) {
                // Get next user
                User user = (User) userIterator.next();
        %>
        <ui:tablerow>
            <ui:tablecell>
                <ui:actionlink action="doViewUser" value="<%= user.getUserName() %>">
                    <ui:actionparam name="userID" value="<%= user.getID() %>"/>
                </ui:actionlink>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%= user.getFullName() %>" style="plain"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%= user.getEmailAddress() %>" style="plain"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%= user.getOrganization() %>" style="plain"/>
            </ui:tablecell>
        </ui:tablerow>
        <%
            }
        %>
    </ui:table>

    <h3><ui:actionlink action="doNewUser" key="USER_CREATE_USER"/></h3>


</ui:form>
