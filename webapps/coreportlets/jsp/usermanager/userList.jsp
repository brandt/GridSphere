<%@ page import="org.gridlab.gridsphere.portlet.User,
                 org.gridlab.gridsphere.portlets.core.beans.UserManagerBean,
                 java.util.List" %>
<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<jsp:useBean id="userManagerBean"
             class="org.gridlab.gridsphere.portlets.core.beans.UserManagerBean"
             scope="request"/>
<form name="UserManagerPortlet" method="POST"
      action="<%=userManagerBean.getPortletActionURI(UserManagerBean.ACTION_USER_LIST)%>">
  <input type="hidden" name="userID" value=""/>
  <script type="text/javascript">
    function UserManagerPortlet_listUser_onClick() {
      document.UserManagerPortlet.action="<%=userManagerBean.getPortletActionURI(UserManagerBean.ACTION_USER_LIST)%>";
      document.UserManagerPortlet.submit();
    }

    function UserManagerPortlet_newUser_onClick(userID) {
      document.UserManagerPortlet.userID.value="";
      document.UserManagerPortlet.action="<%=userManagerBean.getPortletActionURI(UserManagerBean.ACTION_USER_EDIT)%>";
      document.UserManagerPortlet.submit();
    }

    function UserManagerPortlet_viewUser_onClick(userID) {
      document.UserManagerPortlet.userID.value=userID;
      document.UserManagerPortlet.action="<%=userManagerBean.getPortletActionURI(UserManagerBean.ACTION_USER_VIEW)%>";
      document.UserManagerPortlet.submit();
    }
  </script>
<table class="portlet-pane" cellspacing="1">
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-title">
              List Users
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-actions">
            <input type="button"
                   name="<%=UserManagerBean.ACTION_USER_LIST%>"
                   value="List Users"
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
      <table class="portlet-frame" cellspacing="1" width="100%">
<% List userList = userManagerBean.getUserList();
   int numUsers = userList.size();
   if (numUsers == 0) { %>
        <tr>
          <td class="portlet-frame-text-alert">
            There are no user accounts in the database.
          </td>
        </tr>
<% } else { %>
       <tr>
         <td class="portlet-frame-header" width="100">
             User Name
         </td>
         <td class="portlet-frame-header" width="150">
             Full Name
         </td>
         <td class="portlet-frame-header" width="200">
             Email Address
         </td>
         <td class="portlet-frame-header" width="150">
             Organization
         </td>
       </tr>
<%   for (int ii = 0; ii < numUsers; ++ii) {
       User user = (User)userList.get(ii); %>
        <tr>
          <td class="portlet-frame-text">
            <a href="javascript:UserManagerPortlet_viewUser_onClick('<%=user.getID()%>')">
              <%=user.getUserID()%>
            </a>
          </td>
          <td class="portlet-frame-text">
            <%=user.getFullName()%>
          </td>
          <td class="portlet-frame-text">
            <%=user.getEmailAddress()%>
          </td>
          <td class="portlet-frame-text">
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
