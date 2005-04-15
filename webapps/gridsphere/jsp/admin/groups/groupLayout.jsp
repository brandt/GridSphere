<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>

<jsp:useBean id="groupId" class="java.lang.String" scope="request"/>

<ui:messagebox beanId="msg"/>

<h2><ui:text key="GROUP_WIZARD_2"/></h2>
<ui:group>

<ui:text key="GROUP_LAYOUT_MSG"/>
<ui:text key="GROUP_TEMPLATE_MSG"/>

<p>
   <ui:form>
   <ui:text key="GROUP_LAYOUT_FILE"/>&nbsp;&nbsp;<ui:textfield beanId="layoutFileTF"/>
    <ui:actionsubmit action="doMakeTemplateLayout" key="GROUP_CREATE_TEMPLATE">
        <ui:actionparam name="groupId" value="<%= groupId %>"/>
    </ui:actionsubmit>
    </ui:form>

</p>

</ui:group>