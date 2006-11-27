<%@ page import="org.gridsphere.portlet.impl.SportletProperties" %>
<%@ page import="javax.portlet.RenderRequest" %>
<%@ page import="org.gridsphere.services.core.user.User" %>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<% RenderRequest req = (RenderRequest) pageContext.getAttribute("renderRequest");
    User user = (User) req.getAttribute(SportletProperties.PORTLET_USER);
    String username = user.getFirstName() + " " + user.getLastName(); %>

<div class="gridsphere-navbar">
    <ul>
        <li>
<ui:text style="nostyle" key="LOGIN_SUCCESS"/>, <%= username %>
        </li>
        <ui:hasrole role="ADMIN">
        <li>
            <ui:actionlink layout="AdminLayout" value="Administration"/>
        </li>
        <li>
            <ui:actionlink layout="LayoutManager" value="Layout"/>
        </li>
         </ui:hasrole>
        <li>
            <ui:actionlink layout="ProfileManager" value="Profile"/>
        </li>
        <li>
            <ui:actionlink layout="LoggedInUserLayout" value="Home"/>
        </li>
        <li>
            <ui:actionlink action="<%= SportletProperties.LOGOUT %>" key="LOGOUT"/>
        </li>
    </ul>
</div>