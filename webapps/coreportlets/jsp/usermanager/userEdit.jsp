<%@ page import="org.gridlab.gridsphere.portlet.User,
                 org.gridlab.gridsphere.portlets.core.beans.UserManagerBean,
                 java.util.List,
                 org.gridlab.gridsphere.portlet.PortletRole" %>
<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<jsp:useBean id="userManagerBean"
             class="org.gridlab.gridsphere.portlets.core.beans.UserManagerBean"
             scope="request"/>
<form name="UserManagerPortlet" method="POST" action="<%=userManagerBean.getUserEditURI()%>">
  <input type="hidden" name="userID" value="<%=userManagerBean.getUserID()%>"/>
  <script type="text/javascript">
    function UserManagerPortlet_confirmEditUser_onClick() {
      document.UserManagerPortlet.action="<%=userManagerBean.getUserEditConfirmURI()%>";
      document.UserManagerPortlet.submit();
    }

    function UserManagerPortlet_cancelEditUser_onClick() {
      document.UserManagerPortlet.action="<%=userManagerBean.getUserEditCancelURI()%>";
      document.UserManagerPortlet.submit();
    }
  </script>
<table class="portlet-pane" cellspacing="1">
<% if (userManagerBean.isFormInvalid()) { %>
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-message-alert">
              <%=userManagerBean.getFormInvalidMessage()%>
          </td>
        </tr>
      </table>
    </td>
  </tr>
<% } %>
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-title">
<% if (userManagerBean.isNewUser()) { %>
              New User
<% } else { %>
              Edit User [<%=userManagerBean.getUserName()%>]
<% } %>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-actions">
            <input type="button"
                   name="<%=UserManagerBean.ACTION_USER_EDIT_CONFIRM%>"
                   value="Save User"
                   onClick="javascript:UserManagerPortlet_confirmEditUser_onClick()"/>
            &nbsp;&nbsp;<input type="button"
                   name="<%=UserManagerBean.ACTION_USER_EDIT_CANCEL%>"
                   value="Cancel Edit"
                   onClick="javascript:UserManagerPortlet_cancelEditUser_onClick()"/>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-label" width="200">
             User Name:&nbsp;
          </td>
          <td class="portlet-frame-input">
             <input type="text"
                    name="userName"
                    value="<%=userManagerBean.getUserName()%>"/>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             Family Name:&nbsp;
          </td>
          <td class="portlet-frame-input">
             <input type="text"
                    name="familyName"
                    value="<%=userManagerBean.getFamilyName()%>"/>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             Given Name:&nbsp;
          </td>
          <td class="portlet-frame-input">
             <input type="text"
                    name="givenName"
                    value="<%=userManagerBean.getGivenName()%>"/>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             Email Address:&nbsp;
          </td>
          <td class="portlet-frame-input">
             <input type="text"
                    name="emailAddress"
                    value="<%=userManagerBean.getEmailAddress()%>"/>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             Organization:&nbsp;
          </td>
          <td class="portlet-frame-input">
             <input type="text"
                    name="organization"
                    value="<%=userManagerBean.getOrganization()%>"/>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             Base Role:&nbsp;
          </td>
          <td class="portlet-frame-input">
             <select name="baseGroupRole">
               <% PortletRole thisRole = userManagerBean.getRoleInBaseGroup();
                   List allRoles = userManagerBean.getAllRolesInBaseGroup();
                   for (int ii = 0; ii < allRoles.size(); ++ii) {
                       PortletRole thatRole = (PortletRole)allRoles.get(ii);
                       if (thisRole.equals(thatRole)) { %>
               <option label="<%=thatRole.toString()%>"
                       value="<%=thatRole.toString()%>"
                       selected="true"/>
               <%     } else { %>
               <option label="<%=thatRole.toString()%>"
                       value="<%=thatRole.toString()%>"/>
               <%     }

                    } %>
             </select>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             New Password:&nbsp;
          </td>
          <td class="portlet-frame-input">
             <input type="password"
                    name="passwordValue"
                    value=""/>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             Confirm Password:&nbsp;
          </td>
          <td class="portlet-frame-input">
             <input type="password"
                    name="passwordConfirmation"
                    value=""/>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</form>
