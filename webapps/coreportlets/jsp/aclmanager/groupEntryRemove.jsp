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
<form name="AccessControllerPortlet" method="POST"
      action="<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_ENTRY_REMOVE)%>">
  <input type="hidden" name="groupID" value="<%=aclManagerBean.getGroupID()%>"/>
  <script type="text/javascript">

    function AccessControllerPortlet_confirmRemoveGroupEntry_onClick() {
      var isValid = GridSphere_CheckBoxList_validateCheckOneOrMore(document.AccessControllerPortlet.groupEntryID);
      // Validate remove action
      if (isValid == false) {
        alert("Please select the users you would like to remove from this group.");
      } else {
        var action = "<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_ENTRY_REMOVE_CONFIRM)%>";
        document.AccessControllerPortlet.action=action;
        document.AccessControllerPortlet.submit();
      }
    }

    function AccessControllerPortlet_cancelRemoveGroupEntry_onClick() {
      var action = "<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_ENTRY_REMOVE_CANCEL)%>";
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
            Click "<span style="portlet-text-alert">Confirm Delete</span>" to emove the selected users,
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
              Remove Users From Group <%=aclManagerBean.getGroupName()%>
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
                   name="<%=AccessControllerBean.ACTION_GROUP_ENTRY_REMOVE_CONFIRM%>"
                   value="Confirm Remove"
                   onClick="javascript:AccessControllerPortlet_confirmRemoveGroupEntry_onClick()"/>
            &nbsp;&nbsp;<input type="button"
                   name="<%=AccessControllerBean.ACTION_GROUP_ENTRY_REMOVE_CANCEL%>"
                   value="Cancel Remove"
                   onClick="javascript:AccessControllerPortlet_cancelRemoveGroupEntry_onClick()"/>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-header-checkbox">
              <input type="checkbox"
               name="groupEntryID"
               checked="false"
               value=""
               onClick="javascript:GridSphere_CheckBoxList_checkAll(document.AccessControllerPortlet.groupEntryID)"/>
          </td>
          <td class="portlet-frame-header" width="100">
              User
          </td>
          <td class="portlet-frame-header" width="150">
              Full Name
          </td>
          <td class="portlet-frame-header" width="100">
              Role
          </td>
        </tr>
<% Iterator groupEntries = aclManagerBean.getGroupEntryList().iterator();
   while (groupEntries.hasNext()) {
        GroupEntry groupEntry = (GroupEntry)groupEntries.next();
        User groupEntryUser = groupEntry.getUser();
        PortletRole groupEntryRole = groupEntry.getRole(); %>
        <tr>
          <td class="portlet-frame-entry-checkbox">
              <input type="checkbox"
               name="groupEntryID"
               checked="false"
               value="<%=groupEntry.getID()%>"
               onClick="javascript:GridSphere_CheckBoxList_onClick(document.AccessControllerPortlet.groupEntryID,
                                                                   this)"/>
          </td>
          <td class="portlet-frame-text">
            <a href="javascript:AccessControllerPortlet_viewGroupEntry_onClick('<%=groupEntryUser.getID()%>')">
              <%=groupEntryUser.getUserID()%>
            </a>
          </td>
          <td class="portlet-frame-text">
            <%=groupEntryUser.getFullName()%>
          </td>
          <td class="portlet-frame-text">
            <%=groupEntryRole%>
          </td>
        </tr>
<% }%>
      </table>
    </td>
  </tr>
</table>
</form>
