<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<jsp:useBean id="webappname" class="java.lang.String"/>

<h2>
    <ui:text key="PORTLET_DISPLAY_PORTLETS"/>
    <%= request.getAttribute("webappname") %>
</h2>
<ui:form>
    <ui:group>
        <ui:panel beanId="panel"/>
    </ui:group>
</ui:form>

<h3>
    <ui:renderlink render="listPortlets" key="PORTLET_LIST_APPS"/>
</h3>
