<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<ui:form>
<ui:hiddenfield beanId="userID"/>

<%@ include file="/jsp/admin/users/doViewUser.jsp" %>

    <ui:frame>
        <ui:tablerow>
            <ui:tablecell>
                <ui:actionsubmit action="doConfirmDeleteUser" key="USER_CONFIRM_DELETE"/>
                <ui:actionsubmit action="doCancelDeleteUser" key="USER_CANCEL_DELETE"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>

</ui:form>