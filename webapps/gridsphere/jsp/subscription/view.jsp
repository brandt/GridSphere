<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<ui:form>

<ui:panel>
<ui:frame value="Select portlets to add or remove from your layout. New portlets will be added to the \"Untitled\" tab."/>
</ui:panel>

<ui:panel beanId="panel"/>

<ui:panel>
    <ui:actionsubmit action="applyChanges" value="Apply changes"/>
</ui:panel>
</ui:form>