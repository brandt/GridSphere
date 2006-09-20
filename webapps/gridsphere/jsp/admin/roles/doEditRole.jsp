<%@ page import="org.gridsphere.portlet.User"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.List"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>
<% List userList = (List) request.getAttribute("userList"); %>


<ui:messagebox beanId="msg"/>


<ui:group key="ROLE_EDIT_MSG">
<ui:form>
    <ui:hiddenfield beanId="roleHF"/>
    <ui:hiddenfield beanId="isNewRoleHF"/>
    <ui:frame>

        <ui:tablerow>
            <ui:tablecell>
                <ui:text key="ROLENAME"/>
                <ui:textfield size="10" beanId="roleNameTF"/>
            </ui:tablecell>

            <ui:tablecell>
                <ui:text key="ROLEDESC"/>
                <ui:textfield beanId="roleDescTF"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:actionsubmit action="doSaveRole" key="ROLE_SAVE"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>

</ui:form>
</ui:group>

<ui:group key="ROLE_EDITUSER_MSG">
<ui:form>
   <ui:hiddenfield beanId="roleHF"/>
    <% if (request.getAttribute("nousers") == null) { %>
    <ui:frame>
        <ui:tablerow>
            <ui:tablecell>
    <ui:text key="ROLE_ADD_USER"/><ui:listbox beanId="addusersLB"/><ui:actionsubmit action="doAddUser" key="ROLE_ADD_USER_ACTION"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>
    <% } %>
    <p/>
    <% if (!userList.isEmpty()) { %>
    <ui:table beanId="userTable">
        <ui:tablerow header="true">
            <ui:tablecell><ui:text key="ROLE_REMOVE_USER"/></ui:tablecell>
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
                <ui:checkbox name="userCB" value="<%= user.getID() %>"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%= user.getFullName() %>"/>
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

    <ui:actionsubmit action="doRemoveUser" key="ROLE_REMOVE_USER"/>
    <% } %>
    <ui:actionsubmit action="doReturn" key="CANCEL"/>
</ui:form>

</ui:group>