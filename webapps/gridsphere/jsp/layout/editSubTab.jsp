<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>
<ui:form>

<ui:hiddenfield beanId="tabNumHF"/>
<ui:hiddenfield beanId="subtabNumHF"/>

Edit sub tab name: <ui:textfield beanId="subtabTF"/> <ui:actionsubmit action="saveSubTabName" value="Apply"/>

</ui:form>
<ui:form>
<ui:panel>
<ui:frame beanId="portletFrames"/>
</ui:panel>
</ui:form>

<ui:actionlink action="editTab" value="go back"/>