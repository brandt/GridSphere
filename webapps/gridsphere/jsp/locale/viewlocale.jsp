<%@ page import="java.util.Locale" %>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<% Locale locale = (Locale) request.getAttribute("locale"); %>

<ui:form action="selectLang"> <!-- removed 'name="localeform"' for XHTML 1.0 Strict compliance -->

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
