<%@ page import="org.gridlab.gridsphere.portlet.User,
                 org.gridlab.gridsphere.portlets.core.beans.UserManagerBean,
                 java.util.List,
                 org.gridlab.gridsphere.portlet.PortletRole,
                 org.gridlab.gridsphere.portlet.PortletGroup,
                 java.util.Iterator" %>
<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<jsp:useBean id="userManagerBean"
             class="org.gridlab.gridsphere.portlets.core.beans.UserManagerBean"
             scope="request"/>
<form name="UserManagerPortlet" method="POST" action="<%=userManagerBean.getUserViewURI()%>">
  <input type="hidden" name="userID" value="<%=userManagerBean.getUserID()%>"/>
  <script language="JAVASCRIPT">
    function UserManagerPortlet_listUser_onClick() {
      document.UserManagerPortlet.action="<%=userManagerBean.getUserListURI()%>";
      document.UserManagerPortlet.submit();
    }

    function UserManagerPortlet_newUser_onClick(userID) {
      document.UserManagerPortlet.userID.value="";
      document.UserManagerPortlet.action="<%=userManagerBean.getUserEditURI()%>";
      document.UserManagerPortlet.submit();
    }

    function UserManagerPortlet_editUser_onClick() {
      document.UserManagerPortlet.action="<%=userManagerBean.getUserEditURI()%>";
      document.UserManagerPortlet.submit();
    }

    function UserManagerPortlet_deleteUser_onClick() {
      document.UserManagerPortlet.action="<%=userManagerBean.getUserDeleteURI()%>";
      document.UserManagerPortlet.submit();
    }
  </script>
<table border="0" cellspacing="1" cellpadding="2" width="100%">
  <tr>
    <td>
      <table bgcolor="BLACK" border="0" cellspacing="1" cellpadding="2" width="100%">
        <tr>
          <td bgcolor="#CCCCCC">
            <input type="button"
                   name="<%=UserManagerBean.ACTION_USER_LIST%>"
                   value="List Users"
                   onClick="javascript:UserManagerPortlet_listUser_onClick()"/>
            &nbsp;&nbsp;<input type="button"
                   name="<%=UserManagerBean.ACTION_USER_EDIT%>"
                   value="New User"
                   onClick="javascript:UserManagerPortlet_newUser_onClick()"/>
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
             <%=userManagerBean.getUserName()%>
          </td>
        </tr>
        <tr>
          <td bgcolor="#CCCCCC">
             Family Name:&nbsp;
          </td>
          <td bgcolor="WHITE">
             <%=userManagerBean.getFamilyName()%>
          </td>
        </tr>
        <tr>
          <td bgcolor="#CCCCCC">
             Given Name:&nbsp;
          </td>
          <td bgcolor="WHITE">
             <%=userManagerBean.getGivenName()%>
          </td>
        </tr>
        <tr>
          <td bgcolor="#CCCCCC">
             Email Address:&nbsp;
          </td>
          <td bgcolor="WHITE">
             <%=userManagerBean.getEmailAddress()%>
          </td>
        </tr>
        <tr>
          <td bgcolor="#CCCCCC">
             Organization:&nbsp;
          </td>
          <td bgcolor="WHITE">
             <%=userManagerBean.getOrganization()%>
          </td>
        </tr>
        <tr>
          <td bgcolor="#CCCCCC">
            Base Role:&nbsp;
          </td>
          <td bgcolor="WHITE">
             <%=userManagerBean.getRoleInBaseGroup()%>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</form>
