<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<ui:form>
<ui:hiddenfield beanId="userID"/>
<ui:panel>
    <ui:frame>
        <ui:tablerow>
            <ui:tablecell>
                <ui:actionsubmit action="doListUsers" value="List Users"/>
                <ui:actionsubmit action="doEditUser" value="Edit User"/>
                <ui:actionsubmit action="doDeleteUser" value="Delete User"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>

<%@ include file="/jsp/admin/users/doViewUser.jsp" %>

</ui:panel>
</ui:form>