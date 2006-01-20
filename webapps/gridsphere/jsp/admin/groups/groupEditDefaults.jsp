<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>

<ui:form>
    <h3><ui:text key="GROUP_EDIT_DEFS" style="nostyle"/></h3>

    <p>
        <ui:text key="GROUP_EDIT_DEFS_MSG"/>
        <ui:frame zebra="true" beanId="defaultTable"/>

        <ui:actionsubmit action="doSaveDefaultGroups" key="SAVE"/>
        <ui:actionsubmit action="doViewListGroup" key="CANCEL"/>
    </p>
</ui:form>
