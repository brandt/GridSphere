<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<ui:form>
<ui:panel>

    <ui:frame>
        <ui:tablerow>
            <ui:tablecell>
                <ui:actionsubmit action="doViewListUser" value="List Users"/>
                <ui:actionsubmit action="doViewNewUser" value="New User"/>
            </ui:tablecell>
        </ui:tablerow>
        <ui:tablerow>
            <ui:tablecell>
                <ui:text style="message" value="The following user was deleted."/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>

<%@ include file="/jsp/admin/users/doViewUser.jsp" %>

</ui:panel>
</ui:form>