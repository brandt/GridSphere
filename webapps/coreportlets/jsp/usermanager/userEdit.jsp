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
  <script language="JAVASCRIPT">
    function UserManagerPortlet_confirmEditUser_onClick() {
      document.UserManagerPortlet.action="<%=userManagerBean.getUserEditConfirmURI()%>";
      document.UserManagerPortlet.submit();
    }

    function UserManagerPortlet_cancelEditUser_onClick() {
      document.UserManagerPortlet.action="<%=userManagerBean.getUserEditCancelURI()%>";
      document.UserManagerPortlet.submit();
    }
  </script>
<table border="0" cellspacing="1" cellpadding="2" width="100%">
<% if (userManagerBean.isFormInvalid()) { %>
  <tr>
    <td>
      <table bgcolor="BLACK" border="0" cellspacing="1" cellpadding="2" width="100%">
        <tr>
          <td bgcolor="WHITE">
            <font color="DARKRED"><bold>
              <%=userManagerBean.getFormInvalidMessage()%>
            </bold></font>
          </td>
        </tr>
      </table>
    </td>
  </tr>
<% } %>
  <tr>
    <td>
      <table bgcolor="BLACK" border="0" cellspacing="1" cellpadding="2" width="100%">
        <tr>
          <td bgcolor="#CCCCCC">
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
      <table bgcolor="BLACK" border="0" cellspacing="1" cellpadding="2" width="100%">
        <tr>
          <td bgcolor="#CCCCCC">
             User ID:&nbsp;
          </td>
          <td bgcolor="WHITE">
             <%=userManagerBean.getUserID()%>
          </td>
        </tr>
        <tr>
          <td bgcolor="#CCCCCC">
             User Name:&nbsp;
          </td>
          <td bgcolor="WHITE">
             <input type="text"
                    name="userName"
                    value="<%=userManagerBean.getUserName()%>"/>
          </td>
        </tr>
        <tr>
          <td bgcolor="#CCCCCC">
             Family Name:&nbsp;
          </td>
          <td bgcolor="WHITE">
             <input type="text"
                    name="familyName"
                    value="<%=userManagerBean.getFamilyName()%>"/>
          </td>
        </tr>
        <tr>
          <td bgcolor="#CCCCCC">
             Given Name:&nbsp;
          </td>
          <td bgcolor="WHITE">
             <input type="text"
                    name="givenName"
                    value="<%=userManagerBean.getGivenName()%>"/>
          </td>
        </tr>
        <tr>
          <td bgcolor="#CCCCCC">
             Email Address:&nbsp;
          </td>
          <td bgcolor="WHITE">
             <input type="text"
                    name="emailAddress"
                    value="<%=userManagerBean.getEmailAddress()%>"/>
          </td>
        </tr>
        <tr>
          <td bgcolor="#CCCCCC">
             Organization:&nbsp;
          </td>
          <td bgcolor="WHITE">
             <input type="text"
                    name="organization"
                    value="<%=userManagerBean.getOrganization()%>"/>
          </td>
        </tr>
        <tr>
          <td bgcolor="#CCCCCC">
             Base Role:&nbsp;
          </td>
          <td bgcolor="WHITE">
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
          <td bgcolor="#CCCCCC">
             New Password:&nbsp;
          </td>
          <td bgcolor="WHITE">
             <input type="password"
                    name="passwordValue"
                    value=""/>
          </td>
        </tr>
        <tr>
          <td bgcolor="#CCCCCC">
             Confirm Password:&nbsp;
          </td>
          <td bgcolor="WHITE">
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
