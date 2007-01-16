<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<br>

<ui:messagebox beanId="msg"/>

<ui:form>
    <ui:listbox beanId="feedsLB"/>
    <ui:actionsubmit action="removeFeed" key="RSS_DELETE_FEED"/>
    <br/>

    <ui:text key="RSS_ENTERFEEDURL"/>
    <ui:textfield beanId="newfeedurl"/>
    <ui:actionsubmit action="saveFeed" key="RSS_ADD_FEED"/>


</ui:form>