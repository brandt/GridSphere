<%@ page import="java.util.Locale"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<jsp:useBean id="locale" class="java.util.Locale" scope="request"/>

<br><br>
<ui:form name="localeform" action="selectLang">
    <% String flag = "/gridsphere/html/flags/"+locale.getLanguage() +".gif"; %>
    <table>
    <tr>
    <td>
    <ui:image src="<%= flag %>" alt="<%= locale.getDisplayLanguage() %>" title="<%= locale.getDisplayLanguage() %>"/>
    </td>
    <td>
    <ui:listbox beanId="localeLB"/>
    </td>
    <%-- <ui:actionsubmit action="selectLang" value="ok" hideifjs="true"/> --%>
    <!-- the following three lines are the important bit, along with having the
functionality available on your server -->
<noscript>
    <td>
    <ui:actionsubmit action="selectLang" value="ok"/>
    </td>
</noscript>
    </tr>
    </table>
</ui:form>