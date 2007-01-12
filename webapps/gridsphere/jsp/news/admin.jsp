<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<portlet:defineObjects/>

<ui:messagebox beanId="msg"/>

<ui:form>
    <ui:text key="NEWS_DOCUMENT"/>
    <ui:listbox beanId="document"/>
    <ui:actionsubmit key="NEWS_SAVE" action="doSave"/>
</ui:form>