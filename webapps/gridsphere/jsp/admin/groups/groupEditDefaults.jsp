<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>

<ui:form>
<h3>Edit default groups</h3>

<ui:text value="When users are added to the portal they can be added automatically to the following selection of groups"/>
<p>
<ui:frame zebra="true" beanId="defaultTable"/>
<p>

<ui:actionsubmit action="doSaveDefaultGroups" key="SAVE"/>
<ui:actionsubmit action="doViewListGroup" key="CANCEL"/>

</ui:form>
