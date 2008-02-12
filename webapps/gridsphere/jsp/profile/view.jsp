<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<ui:tabbedpane>
    <ui:tab label="authmodules" key="PROFILE_SETTINGS" page="/jsp/profile/viewuser.jsp"/>
    <ui:tab label="loginconfig" key="CONFIG_LOGIN_TAB" page="/jsp/profile/editpassword.jsp"/>
    <ui:tab label="loginconfig2" key="NAV_LAYOUT" page="/jsp/profile/editpassword.jsp"/>
</ui:tabbedpane>


