<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<ui:form>
<ui:hiddenfield beanId="userID"/>
<ui:panel>
    <ui:frame>
        <ui:tablerow>
            <ui:tablecell>
                <ui:actionsubmit action="doViewListUser" value="List Users"/>
                <ui:actionsubmit action="doViewNewUser" value="New User"/>
                <ui:actionsubmit action="doViewEditUser" value="Edit User"/>
                <ui:actionsubmit action="doViewDeleteUser" value="Delete User"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>

<%@ include file="/jsp/admin/users/doViewUser.jsp" %>

</ui:panel>
</ui:form>