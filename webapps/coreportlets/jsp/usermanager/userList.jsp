<%@ page import="org.gridlab.gridsphere.portlet.User,
                 org.gridlab.gridsphere.portlets.core.beans.UserManagerBean,
                 java.util.List" %>
<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<jsp:useBean id="userManagerBean"
             class="org.gridlab.gridsphere.portlets.core.beans.UserManagerBean"
             scope="request"/>
<form name="UserManagerPortlet" method="POST" action="<%=userManagerBean.getUserListURI()%>">
  <input type="hidden" name="userID" value=""/>
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

    function UserManagerPortlet_viewUser_onClick(userID) {
      document.UserManagerPortlet.userID.value=userID;
      document.UserManagerPortlet.action="<%=userManagerBean.getUserViewURI()%>";
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
                   value="Refresh List"
                   onClick="javascript:UserManagerPortlet_listUser_onClick()"/>
            &nbsp;&nbsp;<input type="button"
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
          <td bgcolor="#CCCCCC">
            ID
          </td>
          <td bgcolor="#CCCCCC">
            User Name
          </td>
          <td bgcolor="#CCCCCC">
            Full Name
          </td>
          <td bgcolor="#CCCCCC">
            Organization
          </td>
          <td bgcolor="#CCCCCC">
            Email Address
          </td>
        </tr>
<% List userList = userManagerBean.getUserList();
   int numUsers = userList.size();
   if (numUsers == 0) { %>
        <tr>
          <td bgcolor="WHITE" colspan="5">
            <font color="DARKRED">
              No user accounts in database.
            </font>
          </td>
        </tr>
<% } else {
     for (int ii = 0; ii < numUsers; ++ii) {
       User user = (User)userList.get(ii); %>
        <tr>
          <td bgcolor="WHITE">
            <a href="javascript:UserManagerPortlet_viewUser_onClick('<%=user.getID()%>')">
              <%=user.getID()%>
            </a>
          </td>
          <td bgcolor="WHITE">
            <%=user.getUserID()%>
          </td>
          <td bgcolor="WHITE">
            <%=user.getFullName()%>
          </td>
          <td bgcolor="WHITE">
            <%=user.getOrganization()%>
          </td>
          <td bgcolor="WHITE">
            <%=user.getEmailAddress()%>
          </td>
        </tr>
<%   }
   } %>
      </table>
    </td>
  </tr>
</table>
</form>
