<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<ui:form>
<ui:hiddenfield beanId="userID"/>
<ui:panel>
    <ui:frame>
        <ui:tablerow>
            <ui:tablecell>
                <ui:actionsubmit action="doConfirmDeleteUser" value="Confirm Delete"/>
                <ui:actionsubmit action="doCancelDeleteUser" value="Cancel Delete"/>
            </ui:tablecell>
        </ui:tablerow>
        <ui:tablerow>
            <ui:tablecell>
                <ui:text value="Click \"<span style=\"portlet-text-alert\">Confirm Delete</span>\" to delete this user,
                \"<span style=\"portlet-text-alert\">Cancel Delete</span>\" otherwise."/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>

<%@ include file="/jsp/admin/users/doViewUser.jsp" %>

</ui:panel>
</ui:form>