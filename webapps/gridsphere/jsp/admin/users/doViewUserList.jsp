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


<ui:actionlink cssStyle="text-decoration: underline; font-weight: bold;" action="doNewUser" key="USER_CREATE_USER"/>

<p/>
Displaying users 1 to 10 of 133

<ui:group>
<ui:form>

    <ui:table>
        <ui:tablerow>
            <ui:tablecell>
                <ui:text key="USER_PER_PAGE"/>
                <ui:listbox beanId="usersPageLB">
                    <ui:listboxitem name="10" value="10"/>
                    <ui:listboxitem name="20" value="20"/>
                    <ui:listboxitem name="50" value="50"/>
                    <ui:listboxitem name="100" value="100"/>
                </ui:listbox>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text key="USER_SEARCH_EMAIL"/>
                <ui:textfield size="15" beanId="userEmailTF"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text key="USER_SEARCH_ORGANIZATION"/>
                <ui:textfield size="10" beanId="userOrgTF"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:actionsubmit action="filterUserList" key="USER_VIEW"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:table>
</ui:form>
</ui:group>


<ui:form>

    <ui:table sortable="true" zebra="true" maxrows="20" numentries="<%= numUsers.intValue() %>" filter="true">
        <ui:tablerow header="true">
            <ui:tablecell><ui:text key="FULLNAME"/></ui:tablecell>
            <ui:tablecell><ui:text key="USERNAME"/></ui:tablecell>
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
                <ui:actionlink cssStyle="text-decoration: underline;" action="doViewUser" value="<%= user.getFullName() %>">
                    <ui:actionparam name="userID" value="<%= user.getID() %>"/>
                </ui:actionlink>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%= user.getUserName() %>"/>
            </ui:tablecell>
            <ui:tablecell>
                <a href="<%= "mailto:" + user.getEmailAddress() %>"><%= user.getEmailAddress() %></a>

            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%= user.getOrganization() %>"/>
            </ui:tablecell>
        </ui:tablerow>
        <%
            }
        %>
    </ui:table>


</ui:form>
