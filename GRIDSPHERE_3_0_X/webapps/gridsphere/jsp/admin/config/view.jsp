<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<ui:tabbedpane>
    <ui:tab label="loginconfig" key="CONFIG_LOGIN_TAB" page="/jsp/admin/config/loginconfig.jsp"/>
    <ui:tab label="authmodules" key="CONFIG_AUTH_TAB" page="/jsp/admin/config/authmodules.jsp"/>
    <ui:tab label="mailconfig" key="CONFIG_MAIL_TAB" page="/jsp/admin/config/mailconfig.jsp"/>
    <ui:tab label="msgconfig" key="CONFIG_MSG_TAB" page="/jsp/admin/config/msgconfig.jsp"/>
    <ui:tab label="errconfig" key="CONFIG_ERR_TAB" page="/jsp/admin/config/errorconfig.jsp"/>
</ui:tabbedpane>


