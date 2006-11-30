<%@ page import="java.util.Iterator, java.util.List" %>
<%@ page import="org.gridsphere.services.core.security.role.PortletRole"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>


<ui:messagebox beanId="msg"/>

<% List coreRoles = (List) request.getAttribute("coreRoleList"); %>
<% List roleList = (List) request.getAttribute("roleList"); %>
<h3><ui:text key="ROLE_SHOW_ROLES" style="nostyle"/></h3>

<ui:actionlink cssStyle="text-decoration: underline; font-weight: bold;" action="doEditRole" key="ROLE_CREATE_ROLE"/>

<p/>

<ui:form>

    <ui:table sortable="true" zebra="true">
        <ui:tablerow header="true">
            <ui:tablecell><ui:text cssStyle="font-weight: bold;" key="ROLENAME"/></ui:tablecell>
            <ui:tablecell><ui:text cssStyle="font-weight: bold;" style="bold" key="ROLEDESC"/></ui:tablecell>
            <ui:tablecell><ui:text cssStyle="font-weight: bold;" style="bold" key="ROLEDEL"/></ui:tablecell>
        </ui:tablerow>
        <%
            Iterator roleIterator = roleList.iterator();
            while (roleIterator.hasNext()) {
                // Get next user
                PortletRole role = (PortletRole) roleIterator.next();
        %>
        <ui:tablerow>
            <ui:tablecell>
                <ui:actionlink action="doEditRole" value="<%= role.getName() %>">
                    <ui:actionparam name="roleName" value="<%= role.getName() %>"/>
                </ui:actionlink>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%= role.getDescription() %>"/>
            </ui:tablecell>
            <ui:tablecell>
                <% if (!coreRoles.contains(role.getName().toUpperCase())) { %>
                <ui:actionsubmit action="doDeleteRole" key="DELETE">
                    <ui:actionparam name="roleName" value="<%= role.getName() %>"/>
                </ui:actionsubmit>
                <% } %>
            </ui:tablecell>
        </ui:tablerow>
        <%
            }
        %>
    </ui:table>



</ui:form>
