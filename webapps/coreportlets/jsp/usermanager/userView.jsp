<%@ page import="org.gridlab.gridsphere.portlet.User,
                 org.gridlab.gridsphere.portlets.core.beans.UserManagerBean,
                 java.util.List" %>
<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<jsp:useBean id="userManagerBean"
             class="org.gridlab.gridsphere.portlets.core.beans.UserManagerBean"
             scope="request"/>
<form name="UserManagerPortlet" action="<%=userManagerBean.getUserViewURI()%>">
  <input type="hidden" name="userID" value="<%=userManagerBean.getUserID()%>"/>
  <script language="JAVASCRIPT">
    function UserManagerPortlet_listUser_onClick() {
      document.UserManagerPortlet.action.value="<%=userManagerBean.getUserListURI()%>";
      document.UserManagerPortlet.submit();
    }

    function UserManagerPortlet_editUser_onClick() {
      document.UserManagerPortlet.action.value="<%=userManagerBean.getUserEditURI()%>";
      document.UserManagerPortlet.submit();
    }

    function UserManagerPortlet_deleteUser_onClick() {
      document.UserManagerPortlet.action.value="<%=userManagerBean.getUserDeleteURI()%>";
      document.UserManagerPortlet.submit();
    }
  </script>
<% if (userManagerBean.isFormInvalid()) { %>
<table bgcolor="BLACK" border=0 cellpadding=0 cellspacing=0 width="*">
  <tr>
    <td width="*">
      <table cellspacing=0 cellpadding=0 border=0>
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
</table>
<% } %>
<table bgcolor="BLACK" border="0" cellpadding="1" cellspacing="1" width="*">
  <tr>
    <td width="*">
      <table border="0" cellspacing="1" cellpadding="1">
        <tr>
          <td bgcolor="GRAY">
            <input type="button"
                   name="<%=UserManagerBean.ACTION_USER_LIST%>"
                   value="List Users"
                   onClick="javascript:UserManagerPortlet_listUser_onClick()"/>
            &nbsp;&nbsp;<input type="button"
                   name="<%=UserManagerBean.ACTION_USER_EDIT%>"
                   value="Edit User"
                   onClick="javascript:UserManagerPortlet_editUser_onClick()"/>
            &nbsp;&nbsp;<input type="button"
                   name="<%=UserManagerBean.ACTION_USER_DELETE%>"
                   value="Delete User"
                   onClick="javascript:UserManagerPortlet_deleteUser_onClick()"/>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td>
      <table border="0" cellspacing="1" cellpadding="1">
        <tr>
          <td bgcolor="GRAY">
             User ID:&nbsp;
          </td>
          <td bcolor="WHITE">
             <%=userManagerBean.getUserID()%>
          </td>
        </tr>
        <tr>
          <td bgcolor="GRAY">
             User Name:&nbsp;
          </td>
          <td bcolor="WHITE">
             <%=userManagerBean.getUserName()%>
          </td>
        </tr>
        <tr>
          <td bgcolor="GRAY">
             Family Name:&nbsp;
          </td>
          <td bcolor="WHITE">
             <%=userManagerBean.getFamilyName()%>
          </td>
        </tr>
        <tr>
          <td bgcolor="GRAY">
             Given Name:&nbsp;
          </td>
          <td bcolor="WHITE">
             <%=userManagerBean.getGivenName()%>
          </td>
        </tr>
        <tr>
          <td bgcolor="GRAY">
             Full Name:&nbsp;
          </td>
          <td bcolor="WHITE">
             <%=userManagerBean.getFullName()%>
          </td>
        </tr>
        <tr>
          <td bgcolor="GRAY">
             Organization:&nbsp;
          </td>
          <td bcolor="WHITE">
             <%=userManagerBean.getOrganization()%>
          </td>
        </tr>
        <tr>
          <td bgcolor="GRAY">
             Email Address:&nbsp;
          </td>
          <td bcolor="WHITE">
             <%=userManagerBean.getEmailAddress()%>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</form>
