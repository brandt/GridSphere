<%@ page import="org.gridlab.gridsphere.portletcontainer.GridSphereProperties,
                 org.gridlab.gridsphere.portlet.*,
                 org.gridlab.gridsphere.portlets.core.LoginPortlet"%>

<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<jsp:useBean id="login" class="java.lang.String" scope="request"/>

<table border=0 cellpadding=1 cellspacing=0 width="181" bgcolor=6699CC><tr><td><font color=FFFFFF><b>&nbsp;Get Proxy from Server</b></font></td></tr>
</table>

<table border=0 cellpadding=0 cellspacing=0 width="178">
<tr>
<td width=1 bgcolor=cccccc>
<% if (request.getAttribute(LoginPortlet.LOGIN_ERROR_FLAG) != null) { %>
<tr>
  <td width=1000>
    <table cellspacing=0 cellpadding=0 border=0>
      <tr>
        <td>
          <font color="DARKRED"><bold>
            Your username and/or password is incorrect. Please try again.
          </bold></font>
        </td>
      </tr>
    </table>
  </td>
</tr>
<% } %>
<td width=1000>
    <table border=0 cellpadding=3 cellspacing=0 width="178"><tr><td valign=top>

    <gs:form action="login">

    <table cellspacing=2 cellpadding=2 border=0>
    <tr>
    <td align="right">Username: </td>
    <td align="left">
        <input type="text" name="username" size="8" maxlength="20"/>
    </td>
    </tr>
    <tr>
    <td align="right">Password: </td>
    <td align="left">
        <input type="password" name="password" size="8" maxlength="20"/>
    </td>
    </tr>
    <tr>
    <td colspan=5 align="center"><input type="submit" name="option" value="Login"/></td>
    </tr>
    </table>
    </gs:form>

</td>
</tr>
</table>
</td>
<table border=0 cellpadding=0 cellspacing=0 width="190">
<tr><td></td></tr>
</table>
<p>

<table border=0 cellpadding=0 cellspacing=0 width=190>
<tr><td></td></tr>
</table>
