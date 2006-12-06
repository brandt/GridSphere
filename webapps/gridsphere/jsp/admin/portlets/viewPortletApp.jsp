
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<jsp:useBean id="webappname" class="java.lang.String"/>

<h2><ui:text key="PORTLET_DISPLAY_PORTLETS"/> <%= request.getAttribute("webappname") %></h2>
<ui:form>
    <ui:group>
        <ui:panel beanId="panel"/>
    </ui:group>
</ui:form>

<ui:form method="GET">
    <h3><ui:actionlink action="listPortlets" key="PORTLET_LIST_APPS"/></h3>
</ui:form>