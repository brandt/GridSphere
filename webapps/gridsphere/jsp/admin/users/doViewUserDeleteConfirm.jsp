<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<ui:form>

<%@ include file="/jsp/admin/users/doViewUser.jsp" %>

<ui:frame>
        <ui:tablerow>
            <ui:tablecell>
                <ui:actionsubmit action="doListUsers" value="List Users"/>
                <ui:actionsubmit action="doNewUser" value="New User"/>
            </ui:tablecell>
        </ui:tablerow>
        <ui:tablerow>
            <ui:tablecell>
                <ui:text style="message" key="USER_DELETE_MSG"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>
    
</ui:form>