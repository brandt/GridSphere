<%@ page import="org.gridsphere.services.core.security.role.PortletRole, java.util.List" %>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>


<ui:messagebox beanId="msg"/>

<% List coreRoles = (List) request.getAttribute("coreRoleList"); %>
<% List<PortletRole> roleList = (List<PortletRole>) request.getAttribute("roleList"); %>
<% List<PortletRole> defRoles = (List<PortletRole>) request.getAttribute("defRoles"); %>
<h3>
    <ui:text key="ROLE_SHOW_ROLES" style="nostyle"/>
</h3>

<ui:actionlink cssStyle="text-decoration: underline; font-weight: bold;" action="doEditRole" key="ROLE_CREATE_ROLE"/>

<p/>

<ui:form name="roleform">
    <ui:table zebra="true">
        <ui:tablerow header="true">
            <ui:tablecell>
                <ui:text style="bold" key="ROLE_EDIT_DEFAULT"/>
            </ui:tablecell>
            <% if (roleList.size() > 2) { %>
            <ui:tablecell>
                <% if (roleList.size() > 3) { %>
                <ui:checkbox name="all" onClick="GridSphere_CheckAll(document.roleform.rolesCB, this)"/>
                <% } %>
                <ui:text style="bold" key="DELETE"/>
            </ui:tablecell>
            <% } %>
            <ui:tablecell>
                <ui:text style="bold" key="ROLE_EDIT_USERS"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text style="bold" key="ROLEDESC"/>
            </ui:tablecell>
        </ui:tablerow>

        <% for (PortletRole role : roleList) { %>
        <ui:tablerow>
            <ui:tablecell>
                <% if (defRoles.contains(role)) { %>
                <ui:checkbox name="rolesDefCB" value="<%= role.getName() %>" selected="true"/>
                <% } else { %>
                <ui:checkbox name="rolesDefCB" value="<%= role.getName() %>"/>
                <% } %>
            </ui:tablecell>
            <% if (roleList.size() > 2) { %>
            <ui:tablecell>
                <% if (!coreRoles.contains(role.getName().toUpperCase())) { %>
                <ui:checkbox name="rolesCB" value="<%= role.getName() %>"/>
                <% } %>
            </ui:tablecell>
            <% } %>
            <ui:tablecell>
                <ui:actionlink action="doEditRole" value="<%= role.getName() %>">
                    <ui:actionparam name="roleName" value="<%= role.getName() %>"/>
                </ui:actionlink>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%= role.getDescription() %>"/>
            </ui:tablecell>

        </ui:tablerow>
        <% } %>
    </ui:table>
    <ui:actionsubmit action="doSaveDefaultRoles" key="ROLE_SAVE_DEFAULT"/>
    <% if (roleList.size() > 2) { %>
    <ui:actionsubmit action="doDeleteRole" key="DELETE"/>
    <% } %>

</ui:form>
