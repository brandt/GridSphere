<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<jsp:useBean id="locale" class="java.lang.String" scope="request"/>



<ui:form>
    <%
        String flag = "/gridsphere/html/flags/"+locale+".gif";
    %>
    <ui:image src="<%= flag %>" alt="<%= locale%>" title="<%= locale %>"/>
    <ui:listbox beanId="localeSelector"/>
    <ui:actionsubmit action="selectLang" value="Ok"/>
</ui:form>