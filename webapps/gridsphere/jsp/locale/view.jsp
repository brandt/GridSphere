<%@ page import="java.util.Locale"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<jsp:useBean id="curLoc" class="java.util.Locale" scope="request"/>
<% String disp = ""; %>
<% disp = Locale.UK.getDisplayLanguage(curLoc); %>
<ui:actionlink action="setLocale">
    <ui:image src="/gridsphere/html/flags/en_UK.gif" alt="<%= disp %>" title="<%= disp %>"/>
    <ui:actionparam name="locale" value="en_UK"/>
</ui:actionlink>
<% disp = Locale.US.getDisplayLanguage(curLoc); %>
<ui:actionlink action="setLocale">
    <ui:image src="/gridsphere/html/flags/en_US.gif" alt="<%= disp %>" title="<%= disp %>"/>
    <ui:actionparam name="locale" value="en_US"/>
</ui:actionlink>
<% disp = new Locale("cs", "", "").getDisplayLanguage(curLoc); %>
<ui:actionlink action="setLocale">
    <ui:image src="/gridsphere/html/flags/cs.gif" alt="<%= disp %>" title="<%= disp %>"/>
    <ui:actionparam name="locale" value="cs"/>
</ui:actionlink>
<% disp = Locale.GERMAN.getDisplayLanguage(curLoc); %>
<ui:actionlink action="setLocale">
    <ui:image src="/gridsphere/html/flags/de.gif" alt="<%= disp %>" title="<%= disp %>"/>
    <ui:actionparam name="locale" value="de"/>
</ui:actionlink>
<% disp = Locale.FRENCH.getDisplayLanguage(curLoc); %>
<ui:actionlink action="setLocale">
    <ui:image src="/gridsphere/html/flags/fr.gif" alt="<%= disp %>" title="<%= disp %>"/>
    <ui:actionparam name="locale" value="fr"/>
</ui:actionlink>
<% disp = new Locale("hu", "", "").getDisplayLanguage(curLoc); %>
<ui:actionlink action="setLocale">
    <ui:image src="/gridsphere/html/flags/hu.gif" alt="<%= disp %>" title="<%= disp %>"/>
    <ui:actionparam name="locale" value="hu"/>
</ui:actionlink>
<% disp = new Locale("pl", "", "").getDisplayLanguage(curLoc); %>
<ui:actionlink action="setLocale">
    <ui:image src="/gridsphere/html/flags/pl.gif" alt="<%= disp %>" title="<%= disp %>"/>
    <ui:actionparam name="locale" value="pl"/>
</ui:actionlink>
<% disp = Locale.ITALIAN.getDisplayLanguage(curLoc); %>
<ui:actionlink action="setLocale">
    <ui:image src="/gridsphere/html/flags/it.gif" alt="<%= disp %>" title="<%= disp %>"/>
    <ui:actionparam name="locale" value="it"/>
</ui:actionlink>
