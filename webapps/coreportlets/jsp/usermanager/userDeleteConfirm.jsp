<%@ page import="org.gridlab.gridsphere.portlet.User,
                 org.gridlab.gridsphere.portlets.core.beans.UserManagerBean,
                 java.util.List" %>
<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<jsp:useBean id="userManagerBean"
             class="org.gridlab.gridsphere.portlets.core.beans.UserManagerBean"
             scope="request"/>
<form name="UserManagerPortlet" action="<%=userManagerBean.getUserListURI()%>">
  <input type="hidden" name="userID" value="<%=userManagerBean.getUserID()%>"/>
  <script language="JAVASCRIPT">
    function UserManagerPortlet_listUser_onClick() {
      document.UserManagerPortlet.action="<%=userManagerBean.getUserListURI()%>";
      document.UserManagerPortlet.submit();
    }

    function UserManagerPortlet_newUser_onClick() {
      document.UserManagerPortlet.userID.value="";
      document.UserManagerPortlet.action="<%=userManagerBean.getUserEditURI()%>";
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
            &&<input type="button"
                   name="<%=UserManagerBean.ACTION_USER_EDIT%>"
                   value="New User"
                   onClick="javascript:UserManagerPortlet_newUser_onClick()"/>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td>
      <table bgcolor="BLACK" border="0" cellspacing="1" cellpadding="2" width="100%">
        <tr>
          <td bgcolor="WHITE">
            The given user account was deleted.
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
             Full Name:&nbsp;
          </td>
          <td bgcolor="WHITE">
             <%=userManagerBean.getFullName()%>
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
      </table>
    </td>
  </tr>
</table>
</form>
