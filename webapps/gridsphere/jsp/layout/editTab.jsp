<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>
<ui:form>

<ui:hiddenfield beanId="tabNumHF"/>

Edit tab name: <ui:textfield beanId="tabTF"/> <ui:actionsubmit action="saveTabName" value="Apply"/>

</ui:form>
<ui:form>
<ui:hiddenfield beanId="tabNumHF"/>
Select a sub-tab: <ui:listbox beanId="subtabsLB"/>  <ui:actionsubmit action="editSubTab" value="Edit sub-tab"/>

</ui:form>

<ui:actionlink action="doEditLayout" value="go back"/>