<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>
<ui:form>
    <ui:hiddenfield beanId="userID"/>

    <h3><ui:text key="USER_VIEW_USER" style="nostyle"/></h3>

    <%@ include file="doViewUser.jsp" %>

    <ui:frame>
        <ui:tablerow>
            <ui:tablecell>
                <ui:actionsubmit action="doListUsers" key="USER_LIST_USERS"/>
                <ui:actionsubmit action="doEditUser" key="USER_EDIT_USER"/>
                <ui:actionsubmit action="doDeleteUser" key="USER_DELETE_USER"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>

</ui:form>