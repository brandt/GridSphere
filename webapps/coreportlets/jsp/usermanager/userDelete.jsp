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
<table class="portlet-pane" cellspacing="1">
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-message">
            Click "<span style="portlet-text-alert">Confirm Delete</span>" to delete this user,
            "<span style="portlet-text-alert">Cancel Delete</span>" otherwise.
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-title">
              Delete User [<%=userManagerBean.getUserName()%>]
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-actions">
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
      <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-label" width="200">
             User Name:&nbsp;
          </td>
          <td class="portlet-frame-text">
             <%=userManagerBean.getUserName()%>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             Family Name:&nbsp;
          </td>
          <td class="portlet-frame-text">
             <%=userManagerBean.getFamilyName()%>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             Given Name:&nbsp;
          </td>
          <td class="portlet-frame-text">
             <%=userManagerBean.getGivenName()%>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             Full Name:&nbsp;
          </td>
          <td class="portlet-frame-text">
             <%=userManagerBean.getFullName()%>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             Email Address:&nbsp;
          </td>
          <td class="portlet-frame-text">
             <%=userManagerBean.getEmailAddress()%>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             Organization:&nbsp;
          </td>
          <td class="portlet-frame-text">
             <%=userManagerBean.getOrganization()%>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</form>
