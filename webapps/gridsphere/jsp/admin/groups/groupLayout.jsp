<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>

<jsp:useBean id="groupId" class="java.lang.String" scope="request"/>

<ui:messagebox beanId="msg"/>

<h3><ui:text key="GROUP_LAYOUT_HEADER" style="nostyle"/></h3>

<ui:text key="GROUP_LAYOUT_MSG"/>

<p>

<h3>
    <ui:actionlink action="doMakeTemplateLayout" key="GROUP_CREATE_TEMPLATE">
        <ui:actionparam name="groupId" value="<%= groupId %>"/>
    </ui:actionlink>
</h3>

<ui:text key="GROUP_TEMPLATE_MSG"/>

<h3><ui:actionlink label="adminlayout" key="GROUP_CUSTOMIZE_LAYOUT"/></h3>

<ui:text key="GROUP_LAYOUT_CUSTOM"/>

<p>

<ui:form>
<ui:actionsubmit action="doViewListGroup" key="CANCEL"/>
</ui:form>

</p>

