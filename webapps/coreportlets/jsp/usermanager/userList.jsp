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
  <input type="hidden" name="userID" value=""/>
  <script language="JAVASCRIPT">
    function UserManagerPortlet_listUser_onClick() {
      document.UserManagerPortlet.action.value="<%=userManagerBean.getUserListURI()%>";
      document.UserManagerPortlet.submit();
    }

    function UserManagerPortlet_viewUser_onClick(userID) {
      document.UserManagerPortlet.userID.value=userID;
      document.UserManagerPortlet.action.value="<%=userManagerBean.getUserViewURI()%>";
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
                   value="Refresh List"
                   onClick="javascript:UserManagerPortlet_listUser_onClick()"/>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td>
      <table border="0" cellspacing="1" cellpadding="1">
        <tr>
          <td bgcolor="#999999">
            <font color="WHITE">
              ID
            </font>
          </td>
          <td bgcolor="GRAY">
            <font color="WHITE">
              User Name
            </font>
          </td>
          <td bgcolor="GRAY">
            <font color="WHITE">
              Full Name
            </font>
          </td>
          <td bgcolor="GRAY">
            <font color="WHITE">
              Organization
            </font>
          </td>
          <td bgcolor="GRAY">
            <font color="WHITE">
              Email Address
            </font>
          </td>
        </tr>
<% List userList = userManagerBean.getUserList();
   int numUsers = userList.size();
   if (numUsers == 0) { %>
        <tr>
          <td bgcolor="WHITE" colsize="5">
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
