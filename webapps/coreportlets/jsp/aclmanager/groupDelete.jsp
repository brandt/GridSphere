<%@ page import="org.gridlab.gridsphere.portlet.User,
                 org.gridlab.gridsphere.portlet.PortletGroup,
                 org.gridlab.gridsphere.portlets.core.beans.AccessControllerBean,
                 java.util.List,
                 org.gridlab.gridsphere.portlet.PortletRole,
                 org.gridlab.gridsphere.portlet.PortletGroup,
                 java.util.Iterator,
                 org.gridlab.gridsphere.services.security.acl.GroupEntry" %>
<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<jsp:useBean id="aclManagerBean"
             class="org.gridlab.gridsphere.portlets.core.beans.AccessControllerBean"
             scope="request"/>
<form name="AccessContollerPortlet" method="POST" action="<%=aclManagerBean.getGroupViewURI()%>">
  <input type="hidden" name="groupID" value="<%=aclManagerBean.getGroupID()%>"/>
  <script language="JAVASCRIPT">
    function AccessContollerPortlet_confirmDeleteGroup_onClick() {
      document.AccessContollerPortlet.action="<%=aclManagerBean.getGroupDeleteConfirmURI()%>";
      document.AccessContollerPortlet.submit();
    }

    function AccessContollerPortlet_cancelDeleteGroup_onClick() {
      document.AccessContollerPortlet.action="<%=aclManagerBean.getGroupDeleteCancelURI()%>";
      document.AccessContollerPortlet.submit();
    }
  </script>
<table border="0" cellspacing="1" cellpadding="2" width="100%">
  <tr>
    <td>
      <table bgcolor="BLACK" border="0" cellspacing="1" cellpadding="2" width="100%">
        <tr>
          <td bgcolor="#CCCCCC">
            <input type="button"
                   name="<%=AccessControllerBean.ACTION_GROUP_DELETE_CONFIRM%>"
                   value="Confirm Delete"
                   onClick="javascript:AccessContollerPortlet_confirmDeleteGroup_onClick()"/>
            &nbsp;&nbsp;<input type="button"
                   name="<%=AccessControllerBean.ACTION_GROUP_DELETE_CANCEL%>"
                   value="Cancel Delete"
                   onClick="javascript:AccessContollerPortlet_cancelDeleteGroup_onClick()"/>
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
            Click "Confirm Delete" to delete the given portlet group.
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
             Group ID:&nbsp;
          </td>
          <td bgcolor="WHITE">
             <%=aclManagerBean.getGroupID()%>
          </td>
        </tr>
        <tr>
          <td bgcolor="#CCCCCC">
             Group Name:&nbsp;
          </td>
          <td bgcolor="WHITE">
             <%=aclManagerBean.getGroupName()%>
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
            <input type="button"
                   name="<%=AccessControllerBean.ACTION_GROUP_VIEW%>"
                   value="Add Users"
                   onClick="javascript:AccessContollerPortlet_addUsers_onClick()"/>
            &nbsp;&nbsp;<input type="button"
                   name="<%=AccessControllerBean.ACTION_GROUP_VIEW%>"
                   value="Remove Users"
                   onClick="javascript:AccessContollerPortlet_removeUsers_onClick()"/>
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
            Group Role
          </td>
        </tr>
<% List groupEntryList = aclManagerBean.getGroupEntryList();
   int numEntries = groupEntryList.size();
   if (numEntries == 0) { %>
        <tr>
          <td bgcolor="WHITE" colspan="4">
            <font color="DARKRED">
              No users in portlet group.
            </font>
          </td>
        </tr>
<% } else {
      for (int ii = 0; ii < numEntries; ++ii) {
        GroupEntry groupEntry = (GroupEntry)groupEntryList.get(ii);
        User user = groupEntry.getUser();
        PortletRole role = groupEntry.getRole(); %>
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
            <%=role%>
          </td>
        </tr>
<%   }
    } %>
      </table>
    </td>
  </tr>
</table>
</form>
