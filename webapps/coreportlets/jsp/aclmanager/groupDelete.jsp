<%@ page import="org.gridlab.gridsphere.portlet.User,
                 org.gridlab.gridsphere.portlet.PortletGroup,
                 org.gridlab.gridsphere.portlets.core.beans.AccessControllerBean,
                 java.util.List,
                 org.gridlab.gridsphere.portlet.PortletRole,
                 org.gridlab.gridsphere.portlet.PortletGroup,
                 java.util.Iterator,
                 org.gridlab.gridsphere.services.core.security.acl.GroupEntry" %>
<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<jsp:useBean id="aclManagerBean"
             class="org.gridlab.gridsphere.portlets.core.beans.AccessControllerBean"
             scope="request"/>
<form name="AccessControllerPortlet" method="POST"
      action="<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_DELETE)%>">
  <input type="hidden" name="groupID" value="<%=aclManagerBean.getGroupID()%>"/>
  <script type="text/javascript">

    function AccessControllerPortlet_confirmDeleteGroup_onClick() {
      var action = "<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_DELETE_CONFIRM)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessControllerPortlet.submit();
    }

    function AccessControllerPortlet_cancelDeleteGroup_onClick() {
      var action = "<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_DELETE_CANCEL)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessControllerPortlet.submit();
    }

  </script>
<table class="portlet-pane" cellspacing="1">
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-message">
            Click <span style="portlet-text-alert">Confirm Delete</span> to delete this group,
            <span style="portlet-text-alert">Cancel Delete</span> otherwise.
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
              Delete Group [<%=aclManagerBean.getGroupName()%>]
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
                   name="<%=AccessControllerBean.ACTION_GROUP_DELETE_CONFIRM%>"
                   value="Confirm Delete"
                   onClick="javascript:AccessControllerPortlet_confirmDeleteGroup_onClick()"/>
            &nbsp;&nbsp;<input type="button"
                   name="<%=AccessControllerBean.ACTION_GROUP_DELETE_CANCEL%>"
                   value="Cancel Delete"
                   onClick="javascript:AccessControllerPortlet_cancelDeleteGroup_onClick()"/>
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
              Group Name:&nbsp;
          </td>
          <td class="portlet-frame-text">
             <%=aclManagerBean.getGroupName()%>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
              Group Label:&nbsp;
          </td>
          <td class="portlet-frame-text">
             <%=aclManagerBean.getGroupName()%>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
              Group Description:&nbsp;
          </td>
          <td class="portlet-frame-text">
             <%=aclManagerBean.getGroupName()%>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
<% Iterator groupEntries = aclManagerBean.getGroupEntryList().iterator();
    if (groupEntries.hasNext()) { %>
        <tr>
          <td class="portlet-frame-header">
            User
          </td>
          <td class="portlet-frame-header">
            Full Name
          </td>
          <td class="portlet-frame-header">
            Role
          </td>
        </tr>
<%      while (groupEntries.hasNext()) {
            GroupEntry groupEntry = (GroupEntry)groupEntries.next();
            User groupEntryUser = groupEntry.getUser();
            PortletRole groupEntryRole = groupEntry.getRole(); %>
        <tr>
          <td class="portlet-frame-text">
              <%=groupEntryUser.getUserID()%>
          </td>
          <td class="portlet-frame-text">
            <%=groupEntryUser.getFullName()%>
          </td>
          <td class="portlet-frame-text">
            <%=groupEntryRole%>
          </td>
        </tr>
<%     }
    } else { %>
        <tr>
          <td class="portlet-frame-message-alert">
              There are no users in this group.
          </td>
        </tr>
<%  } %>
      </table>
    </td>
  </tr>
</table>
</form>
