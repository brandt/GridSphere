<%@ page import="org.gridlab.gridsphere.portlet.User,
                 org.gridlab.gridsphere.portlet.PortletGroup,
                 org.gridlab.gridsphere.portlets.core.beans.AccessControllerBean,
                 java.util.List,
                 org.gridlab.gridsphere.portlet.PortletRole,
                 org.gridlab.gridsphere.portlet.PortletGroup,
                 java.util.Iterator,
                 org.gridlab.gridsphere.services.core.security.acl.GroupEntry"%>
<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<jsp:useBean id="aclManagerBean"
             class="org.gridlab.gridsphere.portlets.core.beans.AccessControllerBean"
             scope="request"/>
<form name="AccessControllerPortlet" method="POST"
      action="<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_VIEW)%>">
  <input type="hidden" name="groupID" value="<%=aclManagerBean.getGroupID()%>"/>
  <script type="text/javascript">

    function AccessControllerPortlet_listGroup_onClick() {
      var action = "<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_LIST)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessControllerPortlet.submit();
    }

    function AccessControllerPortlet_viewGroupEntry_onClick(groupEntryID) {
      document.AccessControllerPortlet.groupEntryID.value=groupEntryID;
      var action = "<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_VIEW)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessControllerPortlet.submit();
    }

    function AccessControllerPortlet_newGroupEntry_onClick() {
      document.AccessControllerPortlet.groupEntryID.value="";
      var action = "<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_ENTRY_EDIT)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessControllerPortlet.submit();
    }

    function AccessControllerPortlet_editGroupEntry_onClick() {
      var action = "<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_ENTRY_EDIT)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessControllerPortlet.submit();
    }

    function AccessControllerPortlet_addGroupEntry_onClick() {
      // If we are showing list of users...
      if (GridSphere_Object_existsInForm(document.AccessControllerPortlet, "groupEntryID")) {
        // Clear any selections
        GridSphere_CheckBoxList_clear(document.AccessControllerPortlet.groupEntryID);
      }
      // Then submit action
      var action = "<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_ENTRY_ADD)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessControllerPortlet.submit();
    }

    function AccessControllerPortlet_removeGroupEntry_onClick() {
      var isValid = GridSphere_CheckBoxList_validateCheckOneOrMore(document.AccessControllerPortlet.groupEntryID);
      // Validate remove action
      if (isValid == false) {
        alert("Please select the users you would like to remove from this group.");
      } else {
        var action = "<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_ENTRY_REMOVE)%>";
        document.AccessControllerPortlet.action=action;
        document.AccessControllerPortlet.submit();
      }
    }

  </script>
<% Iterator groupEntries = aclManagerBean.getGroupEntryList().iterator(); %>
    <table class="portlet-pane" cellspacing="1">
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-actions">
            <input type="button"
                   name="<%=AccessControllerBean.ACTION_GROUP_LIST%>"
                   value="List Groups"
                   onClick="javascript:AccessControllerPortlet_listGroup_onClick()"/>
            &nbsp;&nbsp;<input type="button"
                   name="<%=AccessControllerBean.ACTION_GROUP_ENTRY_ADD%>"
                   value="Add Users"
                   onClick="javascript:AccessControllerPortlet_addGroupEntry_onClick()"/>
<% if (groupEntries.hasNext()) { %>
            &nbsp;&nbsp;<input type="button"
                   name="<%=AccessControllerBean.ACTION_GROUP_ENTRY_REMOVE%>"
                   value="Remove Users"
                   onClick="javascript:AccessControllerPortlet_removeGroupEntry_onClick()"/>
<% } %>
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
<% if (groupEntries.hasNext()) { %>
        <tr>
          <td class="portlet-frame-header-checkbox">
              <input type="checkbox"
               name="groupEntryID"
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
<%     while (groupEntries.hasNext()) {
           GroupEntry groupEntry = (GroupEntry)groupEntries.next();
           User groupEntryUser = groupEntry.getUser();
           PortletRole groupEntryRole = groupEntry.getRole(); %>
        <tr>
          <td class="portlet-frame-entry-checkbox">
            <font size="-1">
              <input type="checkbox"
               name="groupEntryID"
               value="<%=groupEntry.getID()%>"
               onClick="javascript:GridSphere_CheckBoxList_onClick(document.AccessControllerPortlet.groupEntryID,
                                                                   this)"/>
            </font>
          </td>
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
          <td class="portlet-frame-text">
            There are no users in this group.
          </td>
        </tr>
  </tr>
<%  } %>
      </table>
    </td>
  </tr>
</table>
</form>
