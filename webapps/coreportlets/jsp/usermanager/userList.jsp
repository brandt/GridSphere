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
          <td align="center" bgcolor="#6666FF">
            <font color="WHITE"><strong>
              List Users
            </strong></font>
          </td>
        </tr>
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
          <td width="100" bgcolor="#6666FF">
            <font color="WHITE">
              User Name
            </font>
          </td>
          <td width="200" bgcolor="#6666FF">
            <font color="WHITE">
              Full Name
            </font>
          </td>
          <td width="200" bgcolor="#6666FF">
            <font color="WHITE">
              Email Address
            </font>
          </td>
          <td width="*" bgcolor="#6666FF">
            <font color="WHITE">
              Organization
            </font>
          </td>
        </tr>
<% List userList = userManagerBean.getUserList();
   int numUsers = userList.size();
   if (numUsers == 0) { %>
        <tr>
          <td bgcolor="WHITE" colspan="4">
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
              <%=user.getUserID()%>
            </a>
          </td>
          <td bgcolor="WHITE">
            <%=user.getFullName()%>
          </td>
          <td bgcolor="WHITE">
            <%=user.getEmailAddress()%>
          </td>
          <td bgcolor="WHITE">
            <%=user.getOrganization()%>
          </td>
        </tr>
<%   }
   } %>
      </table>
    </td>
  </tr>
</table>
</form>
