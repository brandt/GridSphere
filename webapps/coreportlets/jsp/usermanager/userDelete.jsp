<%@ page import="org.gridlab.gridsphere.portlet.User,
                 org.gridlab.gridsphere.portlets.core.beans.UserManagerBean,
                 java.util.List" %>
<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<jsp:useBean id="userManagerBean"
             class="org.gridlab.gridsphere.portlets.core.beans.UserManagerBean"
             scope="request"/>
<form name="UserManagerPortlet" method="POST" action="<%=userManagerBean.getUserDeleteURI()%>">
  <input type="hidden" name="userID" value="<%=userManagerBean.getUserID()%>"/>
  <script language="JAVASCRIPT">
    function UserManagerPortlet_confirmDeleteUser_onClick() {
      document.UserManagerPortlet.action="<%=userManagerBean.getUserDeleteConfirmURI()%>";
      document.UserManagerPortlet.submit();
    }

    function UserManagerPortlet_cancelDeleteUser_onClick() {
      document.UserManagerPortlet.action="<%=userManagerBean.getUserDeleteCancelURI()%>";
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
                   name="<%=UserManagerBean.ACTION_USER_DELETE_CONFIRM%>"
                   value="Confirm Delete"
                   onClick="javascript:UserManagerPortlet_confirmDeleteUser_onClick()"/>
            &nbsp;&nbsp;<input type="button"
                   name="<%=UserManagerBean.ACTION_USER_DELETE_CANCEL%>"
                   value="Cancel Delete"
                   onClick="javascript:UserManagerPortlet_cancelDeleteUser_onClick()"/>
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
            Click "Confirm Delete" to delete the given user account.
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
             Organization:&nbsp;
          </td>
          <td bgcolor="WHITE">
             <%=userManagerBean.getOrganization()%>
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
      </table>
    </td>
  </tr>
</table>
</form>
