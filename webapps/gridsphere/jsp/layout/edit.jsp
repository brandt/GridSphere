<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>
<ui:form>

Select a theme: <ui:listbox beanId="themeLB"/> <ui:actionsubmit action="saveTheme" value="Save theme"/>

</ui:form>
<ui:form>
Select a tab: <ui:listbox beanId="tabsLB"/>  <ui:actionsubmit action="editTab" value="Edit tab"/>

</ui:form>
