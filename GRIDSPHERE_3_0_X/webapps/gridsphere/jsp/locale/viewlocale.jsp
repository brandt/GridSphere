<%@ page import="java.util.Locale" %>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<% Locale locale = (Locale) request.getAttribute("locale"); %>

<ui:form action="selectLang">

    <% String flag =  request.getContextPath() + "/images/flags/" + locale.getLanguage() + ".gif"; %>
    <table>
        <tr>
            <td>
                <ui:image src="<%= flag %>" alt="<%= locale.getDisplayLanguage() %>"
                          title="<%= locale.getDisplayLanguage() %>"/>
            </td>
            <td>
                <ui:listbox beanId="localeLB"/>
            </td>
            <td>
                <noscript>
                    <p>
                        <ui:actionsubmit action="selectLang" value="ok"/>
                    </p>
                </noscript>
            </td>
        </tr>
    </table>
</ui:form>
