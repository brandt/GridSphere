<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>
<ui:form>
<ui:panel>

Current top-level tabs:
<ui:frame>
    <ui:tablerow beanId="tabsRow"/>
</ui:frame>
</ui:panel>


<ui:panel>
<ui:actionsubmit action="saveTabs" value="Save titles"/>
</ui:panel>
</ui:form>
