<%@ page import="java.util.Locale"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<% Locale locale = (Locale)request.getAttribute("locale"); %>

<br>

<ui:form name="localeform" action="selectLang">
    <% String flag = "/gridsphere/images/flags/"+locale.getLanguage() +".gif"; %>
    <table>
    <tr>
    <td>
    <ui:image src="<%= flag %>" alt="<%= locale.getDisplayLanguage() %>" title="<%= locale.getDisplayLanguage() %>"/>
    </td>
    <td>
    <ui:listbox beanId="localeLB"/>
    </td>
<noscript>
    <td>
    <ui:actionsubmit action="selectLang" value="ok"/>
    </td>
</noscript>
    </tr>
    </table>
</ui:form>
