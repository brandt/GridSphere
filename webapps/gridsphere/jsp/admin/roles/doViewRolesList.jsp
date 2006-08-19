<%@ page import="java.util.Iterator, java.util.List" %>
<%@ page import="org.gridsphere.portlet.PortletRequest" %>
<%@ page import="org.gridsphere.services.core.security.role.PortletRole"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>

<% PortletRequest pReq = (PortletRequest) pageContext.getAttribute("portletRequest"); %>

<ui:messagebox beanId="msg"/>

<% List coreRoles = (List) request.getAttribute("coreRoleList"); %>
<% List roleList = (List) request.getAttribute("roleList"); %>
<h3><ui:text key="ROLE_SHOW_ROLES" style="nostyle"/></h3>

<ui:form>

    <ui:table sortable="true" zebra="true" maxrows="25">
        <ui:tablerow header="true">
            <ui:tablecell><ui:text key="ROLENAME"/></ui:tablecell>
            <ui:tablecell><ui:text key="ROLEDESC"/></ui:tablecell>
            <ui:tablecell><ui:text key="ROLEDEL"/></ui:tablecell>
        </ui:tablerow>
        <%
            Iterator roleIterator = roleList.iterator();
            while (roleIterator.hasNext()) {
                // Get next user
                PortletRole role = (PortletRole) roleIterator.next();
        %>
        <ui:tablerow>
            <ui:tablecell>
                <% if (!coreRoles.contains(role.getName().toUpperCase())) { %>
                <ui:actionlink action="doEditRole" value="<%= role.getName() %>">
                    <ui:actionparam name="roleName" value="<%= role.getName() %>"/>
                </ui:actionlink>
                <% } else { %>
                <ui:text value="<%= role.getName() %>"/>
                <% } %>
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

    <h3><ui:actionlink action="doEditRole" key="ROLE_CREATE_ROLE"/></h3>


</ui:form>
