<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<ui:form>
<ui:hiddenfield beanId="userID"/>

<%@ include file="/jsp/admin/users/doViewUser.jsp" %>

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