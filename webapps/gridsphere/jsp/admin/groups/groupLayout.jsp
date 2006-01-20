<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>

<jsp:useBean id="groupName" class="java.lang.String" scope="request"/>

<ui:messagebox beanId="msg"/>

<h2><ui:text key="GROUP_WIZARD_2"/></h2>

<p>

    <ui:text key="GROUP_LAYOUT_MSG"/>
    <ui:text key="GROUP_TEMPLATE_MSG"/>

    <ui:form>
        <ui:group>
            <p>
                <ui:text key="GROUP_LAYOUT_FILE"/>&nbsp;&nbsp;<ui:textfield beanId="layoutFileTF"/>
                <ui:actionsubmit action="doMakeTemplateLayout" key="GROUP_CREATE_TEMPLATE">
                    <ui:actionparam name="groupName" value="<%= groupName %>"/>
                </ui:actionsubmit>
            </p>
        </ui:group>
    </ui:form>

</p>