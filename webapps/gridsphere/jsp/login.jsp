<%@ page import="org.gridlab.gridsphere.portletcontainer.GridSphereProperties,
                 org.gridlab.gridsphere.portlet.*,
                 org.gridlab.gridsphere.portlets.core.LoginPortlet"%>

<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>
<jsp:useBean id="login" class="java.lang.String" scope="request"/>
<gs:form action="login">
<% if (request.getAttribute(LoginPortlet.LOGIN_ERROR_FLAG) != null) { %>
<table class="portlet-pane" cellspacing="1" width="200">
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-message-alert">
            Your username and/or password is incorrect. Please try again.
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<% } %>
<table class="portlet-pane" cellspacing="1" width="200">
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-label" width="100">
             User Name:
          </td>
          <td class="portlet-frame-text">
            <input type="text" name="username" size="20" maxlength="20"/>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             Password:
          </td>
          <td class="portlet-frame-text">
             <input type="password" name="password" size="20" maxlength="20"/>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-text">
            <input type="submit" name="option" value="Login"/>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</gs:form>
