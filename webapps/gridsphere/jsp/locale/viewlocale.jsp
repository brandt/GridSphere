<%@ page import="java.util.Locale"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<jsp:useBean id="locale" class="java.util.Locale" scope="request"/>

<ui:form name="localeform" action="selectLang">
    <%
        String flag = "/gridsphere/html/flags/"+locale.getLanguage() +".gif";
    %>
    <table>
    <tr>
    <td>
    <ui:image src="<%= flag %>" alt="<%= locale.getDisplayLanguage() %>" title="<%= locale.getDisplayLanguage() %>"/>
    </td>
 <% String cs = new Locale("cs", "", "").getDisplayLanguage(locale); %>
    <% String hu = new Locale("hu", "", "").getDisplayLanguage(locale); %>
    <% String pl = new Locale("pl", "", "").getDisplayLanguage(locale); %>
    <td>
    <ui:listbox beanId="localeLB" size="1" onChange="GridSphere_SelectLocale(this)">
        <ui:listboxitem name="en" value="<%= Locale.US.getDisplayLanguage(locale) %>"/>
        <ui:listboxitem name="cs" value="<%= cs %>"/>
        <ui:listboxitem name="de" value="<%= Locale.GERMAN.getDisplayLanguage(locale) %>"/>
        <ui:listboxitem name="fr" value="<%= Locale.FRENCH.getDisplayLanguage(locale) %>"/>
        <ui:listboxitem name="hu" value="<%= hu %>"/>
        <ui:listboxitem name="it" value="<%= Locale.ITALIAN.getDisplayLanguage(locale) %>"/>
        <ui:listboxitem name="pl" value="<%= pl %>"/>
    </ui:listbox>
    </td>
    </tr>
    </table>
</ui:form>