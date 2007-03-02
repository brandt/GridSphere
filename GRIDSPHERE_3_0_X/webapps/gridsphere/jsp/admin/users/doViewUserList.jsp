<%@ page import="org.gridsphere.services.core.user.User,
                 java.text.SimpleDateFormat" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<ui:messagebox beanId="msg"/>

<% List<User> userList = (List<User>) request.getAttribute("userList"); %>

<h3>
    <ui:text key="USER_SHOW_USERS" style="nostyle"/>
</h3>

<ui:actionlink cssStyle="text-decoration: underline; font-weight: bold;" action="doNewUser" key="USER_CREATE_USER"/>
<p/>
<ui:group>
    <ui:form>
        <ui:table>
            <ui:tablerow>
                <ui:tablecell>
                    <ui:text key="USER_PER_PAGE"/>
                    <ui:listbox beanId="usersPageLB"/>
                </ui:tablecell>
                &nbsp;&nbsp;
                <ui:tablecell>
                    <ui:text key="USER_SEARCH_EMAIL"/>
                    <ui:textfield size="15" beanId="userEmailTF"/>
                </ui:tablecell>
                &nbsp;&nbsp;
                <ui:tablecell>
                    <ui:text key="USER_SEARCH_ORGANIZATION"/>
                    <ui:textfield size="10" beanId="userOrgTF"/>
                </ui:tablecell>
                &nbsp;&nbsp;
                <ui:tablecell>
                    <ui:actionsubmit action="filterUserList" key="USER_VIEW"/>
                </ui:tablecell>
            </ui:tablerow>
        </ui:table>
    </ui:form>
</ui:group>

<%if (!userList.isEmpty()) { %>

<ui:form name="myform">
    <ui:table beanId="userTable">
        <ui:tablerow header="true">
            <ui:tablecell>
                <ui:checkbox name="all" onClick="GridSphere_CheckAll(document.myform.usersCB, this)"/>
                <ui:text key="SELECT"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:image src="<%= request.getContextPath() + "/images/editor.png" %>" alt="Edit"/>
                <ui:text key="USER_EDIT_USER"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text key="USERNAME"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text key="EMAILADDRESS"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text key="ORGANIZATION"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text key="NUMLOGINS"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text key="LASTLOGINDATE"/>
            </ui:tablecell>
        </ui:tablerow>
        <% for (User user : userList) { %>
        <ui:tablerow>
            <ui:tablecell>
                <ui:checkbox name="usersCB" value="<%= user.getID() %>"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:actionlink cssStyle="text-decoration: underline;" action="doEditUser"
                               value="<%= user.getFullName() %>">
                    <ui:actionparam name="userID" value="<%= user.getID() %>"/>
                </ui:actionlink>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%= user.getUserName() %>"/>
            </ui:tablecell>
            <ui:tablecell>
                <a href="<%= "mailto:" + user.getEmailAddress() %>"><%= user.getEmailAddress() %>
                </a>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%= user.getOrganization() %>"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%= (user.getNumLogins()).toString() %>"/>
            </ui:tablecell>
            <ui:tablecell>
                <% if (user.getLastLoginTime() == null) { %>
                <ui:text value="--"/>
                <% } else { %>
                <ui:text
                        value="<%= new SimpleDateFormat("MMM d yyyy hh:mm a").format(user.getLastLoginTime()).toString() %>"/>
                <% } %>
            </ui:tablecell>
        </ui:tablerow>
        <% } %>
    </ui:table>
    <ui:actionsubmit action="doDeleteUser" key="USER_DELETE_USER"/>
    <ui:actionsubmit action="doComposeEmail" key="USER_SEND_EMAIL"/>
</ui:form>

<% } else { %>
<ui:text style="alert" key="USER_NO_RESULTS"/>
<% } %>
