<%@ page import="org.gridlab.gridsphere.portlet.impl.SportletProperties" %>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<jsp:useBean id="GRIDSPHERE_LOGOUT_LABEL" class="java.lang.String" scope="request"/>
<jsp:useBean id="username" class="java.lang.String" scope="request"/>

<ui:table>
    <ui:tablerow cssStyle="background-color:inherit;">
        <ui:tablecell align="center" valign="bottom">
            <ui:actionlink action="<%= SportletProperties.LOGOUT %>" value="<%= GRIDSPHERE_LOGOUT_LABEL %>"
                           style="bold"/>
        </ui:tablecell>
    </ui:tablerow>
    <ui:tablerow cssStyle="background-color:inherit;" align="center">
        <ui:tablecell align="center" valign="top">
            <ui:text key="LOGIN_SUCCESS"/>, <%= username %>
        </ui:tablecell>
    </ui:tablerow>
</ui:table>

<ui:hasrole role="ADMIN">
<a href="?gs_PageLayout=LayoutManager">Customize Layout</a>
</ui:hasrole>