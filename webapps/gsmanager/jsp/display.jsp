
<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

The Portlet Application Manager allows you to view and manage portlet
web applications.


<gs:actionlink action="list" label="view portlet web applications"/>

<p>

<table border=0 cellpadding=1 cellspacing=0 width="180" bgcolor=6699CC><tr><td><font color=FFFFFF><b>&nbsp;Upload WAR File</b></font></td></tr>
</table>

<table border=0 cellpadding=0 cellspacing=0 width="178">
<tr>
<td width=1 bgcolor=cccccc>
<spacer type=block width=1></td>
<td width=1000>

<table border=0 cellpadding=3 cellspacing=0 width="178"><tr><td valign=top>

    <gs:fileform action="install">
    <table cellspacing=2 cellpadding=2 border=0>
    <tr>
    <td align="right">File: </td>
    <td align="left"><gs:fileinput name="filename" size="8" maxlength="20"/></td>
    </tr>
    <tr>
    <td colspan=5 align="center"><gs:submit name="option" value="Login"/></td>
    </tr>
    </table>
    </gs:fileform>

</td>
</tr>
</table>
