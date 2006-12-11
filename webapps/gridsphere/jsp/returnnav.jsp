<%@ page import="org.gridsphere.portlet.impl.SportletProperties" %>
<%@ page import="org.gridsphere.services.core.user.User" %>
<%@ page import="javax.portlet.RenderRequest" %>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<% RenderRequest req = (RenderRequest) pageContext.getAttribute("renderRequest");
    User user = (User) req.getAttribute(SportletProperties.PORTLET_USER);
    String username = user.getFirstName() + " " + user.getLastName(); %>

<div class="gridsphere-navbar">
    <ul>
        <li>
            <ui:text style="nostyle" key="LOGIN_SUCCESS"/>
            , <%= username %>
        </li>
        <ui:hasrole role="ADMIN">
            <li>
                <ui:actionlink layout="admin" value="Administration"/>
            </li>
            <li>
                <ui:actionlink layout="content" value="Content"/>
            </li>
            <li>
                <ui:actionlink layout="layout" value="Layout"/>
            </li>
        </ui:hasrole>
        <li>
            <ui:actionlink layout="profile" value="Profile"/>
        </li>
        <li>
            <ui:actionlink layout="loggedin" value="Home"/>
        </li>
        <li>
            <ui:actionlink action="<%= SportletProperties.LOGOUT %>" key="LOGOUT"/>
        </li>
    </ul>
</div>