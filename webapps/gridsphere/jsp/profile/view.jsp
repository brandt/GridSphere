<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<ui:tabbedpane>
    <ui:tab label="authmodules" key="PROFILE_SETTINGS" page="/jsp/profile/viewuser.jsp"/>
    <ui:tab label="loginconfig" key="PROFILE_CHANGE_PASSWORD" page="/jsp/profile/editpassword.jsp"/>
</ui:tabbedpane>


