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
                <ui:actionlink layout="admin" key="NAV_ADMIN"/>
            </li>
            <li>
                <ui:actionlink layout="content" key="NAV_CONTENT"/>
            </li>
            <li>
                <ui:actionlink layout="layout" key="NAV_LAYOUT"/>
            </li>
        </ui:hasrole>
        <li>
            <ui:actionlink layout="profile" key="NAV_PROFILE"/>
        </li>
        <li>
            <ui:actionlink layout="loggedin" key="NAV_HOME"/>
        </li>
        <li>
            <ui:actionlink action="<%= SportletProperties.LOGOUT %>" key="LOGOUT"/>
        </li>
    </ul>
</div>