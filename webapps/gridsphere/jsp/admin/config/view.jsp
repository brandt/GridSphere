<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<ui:tabbedpane>
    <ui:tab label="loginconfig" title="Login Configuration" page="/jsp/admin/config/loginconfig.jsp"/>
    <ui:tab label="authmodules" title="Authentication Modules" page="/jsp/admin/config/authmodules.jsp"/>
    <ui:tab label="mailconfig" title="Mail Configuration" page="/jsp/admin/config/mailconfig.jsp"/>
    <ui:tab label="errconfig" title="Error Configuration" page="/jsp/admin/config/errorconfig.jsp"/>
</ui:tabbedpane>



