<%@ page import="java.util.Locale"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<jsp:useBean id="curLoc" class="java.util.Locale" scope="request"/>

<% String disp = ""; %>
<table>
<tr>
<td>

<table>
<% disp = Locale.UK.getDisplayLanguage(curLoc); %>
<tr><td>
<ui:actionlink action="setLocale">
    <ui:image src="/gridsphere/html/flags/en_UK.gif" alt="<%= disp %>" title="<%= disp %>"/>
    <ui:actionparam name="locale" value="en_UK"/>
</ui:actionlink>
</td></tr>
<tr><td>
<%= disp %>
</td></tr>
</table>

</td>
<td>

<table>
<% disp = Locale.US.getDisplayLanguage(curLoc); %>
<tr><td>
<ui:actionlink action="setLocale">
    <ui:image src="/gridsphere/html/flags/en_US.gif" alt="<%= disp %>" title="<%= disp %>"/>
    <ui:actionparam name="locale" value="en_US"/>
</ui:actionlink>
</td></tr>
<tr><td>
<ui:text value="<%= disp %>"/>
</td></tr>
</table>

</td>
<td>

<table>
<% disp = new Locale("cs", "", "").getDisplayLanguage(curLoc); %>
<tr><td>
<ui:actionlink action="setLocale">
    <ui:image src="/gridsphere/html/flags/cs.gif" alt="<%= disp %>" title="<%= disp %>"/>
    <ui:actionparam name="locale" value="cs"/>
</ui:actionlink>
</td></tr>
<tr><td>
<ui:text value="<%= disp %>"/>
</td></tr>
</table>

</td>


<td>

<table>
<% disp = Locale.GERMAN.getDisplayLanguage(curLoc); %>
<tr><td>
<ui:actionlink action="setLocale">
    <ui:image src="/gridsphere/html/flags/de.gif" alt="<%= disp %>" title="<%= disp %>"/>
    <ui:actionparam name="locale" value="de"/>
</ui:actionlink>
</td></tr>
<tr><td>
<ui:text value="<%= disp %>"/>
</td></tr>
</table>

</td>
<td>

<table>
<% disp = Locale.FRENCH.getDisplayLanguage(curLoc); %>
<tr><td>
<ui:actionlink action="setLocale">
    <ui:image src="/gridsphere/html/flags/fr.gif" alt="<%= disp %>" title="<%= disp %>"/>
    <ui:actionparam name="locale" value="fr"/>
</ui:actionlink>
</td></tr>
<tr><td>
<ui:text value="<%= disp %>"/>
</td></tr>
</table>

</td>
<td>

<table>
<% disp = new Locale("hu", "", "").getDisplayLanguage(curLoc); %>
<tr><td>
<ui:actionlink action="setLocale">
    <ui:image src="/gridsphere/html/flags/hu.gif" alt="<%= disp %>" title="<%= disp %>"/>
    <ui:actionparam name="locale" value="hu"/>
</ui:actionlink>
</td></tr>
<tr><td>
<ui:text value="<%= disp %>"/>
</td></tr>
</table>

</td>
<td>

<table>
<% disp = new Locale("pl", "", "").getDisplayLanguage(curLoc); %>
<tr><td>
<ui:actionlink action="setLocale">
    <ui:image src="/gridsphere/html/flags/pl.gif" alt="<%= disp %>" title="<%= disp %>"/>
    <ui:actionparam name="locale" value="pl"/>
</ui:actionlink>
</td></tr>
<tr><td>
<ui:text value="<%= disp %>"/>
</td></tr>
</table>

</td>

<td>

<table>
<% disp = Locale.ITALIAN.getDisplayLanguage(curLoc); %>
<tr><td>
<ui:actionlink action="setLocale">
    <ui:image src="/gridsphere/html/flags/it.gif" alt="<%= disp %>" title="<%= disp %>"/>
    <ui:actionparam name="locale" value="it"/>
</ui:actionlink>
</td></tr>
<tr><td>
<%= disp %>
</td></tr>
</table>

</td>

</tr>

</table>
