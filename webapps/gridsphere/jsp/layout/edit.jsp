<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>
<ui:form>

<ui:panel>
<ui:listbox beanId="themeLB"/>

</ui:panel>
<ui:panel>

Current top-level tabs:
<ui:frame>
    <ui:tablerow beanId="tabsRow"/>
</ui:frame>
</ui:panel>


<ui:panel>
<ui:actionsubmit action="saveChanges" value="Save changes"/>
</ui:panel>
</ui:form>
