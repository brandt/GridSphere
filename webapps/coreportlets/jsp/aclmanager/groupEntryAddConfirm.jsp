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
<form name="AccessControllerPortlet" method="POST" action="<%=aclManagerBean.getGroupViewURI()%>">
  <input type="hidden" name="groupID" value="<%=aclManagerBean.getGroupID()%>"/>
  <input type="hidden" name="groupEntryID" value="<%=aclManagerBean.getGroupEntryID()%>"/>
  <script language="JAVASCRIPT">

    function AccessControllerPortlet_listGroup_onClick() {
      var action = "<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_LIST)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessControllerPortlet.submit();
    }

    function AccessControllerPortlet_viewGroup_onClick(groupID) {
      document.AccessControllerPortlet.groupID.value=groupID;
      var action = "<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_VIEW)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessControllerPortlet.submit();
    }

    function AccessControllerPortlet_newGroup_onClick() {
      document.AccessControllerPortlet.groupID.value="";
      var action = "<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_EDIT)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessControllerPortlet.submit();
    }

    function AccessControllerPortlet_editGroup_onClick() {
      var action = "<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_EDIT)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessControllerPortlet.submit();
    }

    function AccessControllerPortlet_confirmEditGroup_onClick() {
      var action = "<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_EDIT_CONFIRM)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessControllerPortlet.submit();
    }

    function AccessControllerPortlet_cancelEditGroup_onClick() {
      var action = "<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_EDIT_CANCEL)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessControllerPortlet.submit();
    }

    function AccessControllerPortlet_deleteGroup_onClick() {
      var action = "<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_DELETE)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessControllerPortlet.submit();
    }

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

    function AccessControllerPortlet_confirmEditGroupEntry_onClick() {
      var action = "<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_ENTRY_EDIT_CONFIRM)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessControllerPortlet.submit();
    }

    function AccessControllerPortlet_cancelEditGroupEntry_onClick() {
      var action = "<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_ENTRY_EDIT_CANCEL)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessControllerPortlet.submit();
    }

    function AccessControllerPortlet_addGroupEntry_onClick() {
      var action = "<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_ENTRY_ADD)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessControllerPortlet.submit();
    }

    function AccessControllerPortlet_confirmAddGroupEntry_onClick() {
      var validate = GridSphere_CheckBoxList_validateCheckOneOrMore(document.AccessControllerPortlet.groupEntryUserIDs,
                                                                    document.AccessControllerPortlet.groupEntryUserID,
                                                                    document.AccessControllerPortlet.groupEntryUserIDCheckAll);
      // Validate remove action
      if (validate == false) {
        alert("Please select the users you would like to add to this group.");
      } else {
        var action = "<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_ENTRY_ADD_CONFIRM)%>";
        document.AccessControllerPortlet.action=action;
        document.AccessControllerPortlet.submit();
      }
    }

    function AccessControllerPortlet_cancelAddGroupEntry_onClick() {
      var action = "<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_ENTRY_ADD_CONFIRM)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessControllerPortlet.submit();
    }

    function AccessControllerPortlet_removeGroupEntry_onClick(groupID) {
      // Go to add confirmed page
      var action = "<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_ENTRY_REMOVE)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessControllerPortlet.submit();
    }

    function AccessControllerPortlet_confirmRemoveGroupEntry_onClick() {
      var validate = GridSphere_CheckBoxList_validateCheckOneOrMore(document.AccessControllerPortlet.groupEntryIDs,
                                                                    document.AccessControllerPortlet.groupEntryID,
                                                                    document.AccessControllerPortlet.groupEntryIDCheckAll);
      // Validate remove action
      if (validate == false) {
        alert("Please select the users you would like to remove from this group.");
      } else {
        validate = confirm("Are you sure wish to remove the selected entries from this group?");
      }
      // Validate user intention
      if (validate == true) {
        var action = "<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_ENTRY_REMOVE_CONFIRM)%>";
        document.AccessControllerPortlet.action=action;
        document.AccessControllerPortlet.submit();
      }
    }

    function AccessControllerPortlet_cancelRemoveGroupEntry_onClick() {
      var action = "<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_ENTRY_REMOVE_CONFIRM)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessControllerPortlet.submit();
    }

  </script>
<table border="0" cellspacing="1" cellpadding="2" width="100%">
  <tr>
    <td>
      <table bgcolor="BLACK" border="0" cellspacing="1" cellpadding="2" width="100%">
        <tr>
          <td bgcolor="#BLACK">
            <font color="WHITE" size="+1">
              Added Group Entries
            </font>
          </td>
        </tr>
      </table>
    </td>
    <td>
      <table bgcolor="BLACK" border="0" cellspacing="1" cellpadding="2" width="100%">
        <tr>
          <td bgcolor="#CCCCCC">
            <input type="button"
                   name="<%=AccessControllerBean.ACTION_GROUP_ENTRY_VIEW%>"
                   value="Edit Entry="
                   onClick="javascript:AccessControllerPortlet_editGroupEntry_onClick()"/>
            &nbsp;&nbsp;<input type="button"
                   name="<%=AccessControllerBean.ACTION_GROUP_ENTRY_REMOVE%>"
                   value="Delete Entry"
                   onClick="javascript:AccessControllerPortlet_removeGroupEntry_onClick()"/>
            &nbsp;&nbsp;<input type="button"
                   name="<%=AccessControllerBean.ACTION_GROUP_LIST%>"
                   value="Back To Group"
                   onClick="javascript:AccessControllerPortlet_viewGroup_onClick('<%=aclManagerBean.getGroupID()%>')"/>
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
             Entry:&nbsp;
          </td>
          <td bgcolor="WHITE">
             <%=aclManagerBean.getGroupEntryID()%>
          </td>
        </tr>
        <tr>
          <td bgcolor="#CCCCCC">
             Group:&nbsp;
          </td>
          <td bgcolor="WHITE">
             <%=aclManagerBean.getGroupEntry().getGroup().getName()%>
          </td>
        </tr>
        <tr>
          <td bgcolor="#CCCCCC">
             User:&nbsp;
          </td>
          <td bgcolor="WHITE">
             <%=aclManagerBean.getGroupEntry().getUser().getUserName()%>
          </td>
        </tr>
        <tr>
          <td bgcolor="#CCCCCC">
             Full Name:&nbsp;
          </td>
          <td bgcolor="WHITE">
             <%=aclManagerBean.getGroupEntry().getUser().getFullName()%>
          </td>
        </tr>
        <tr>
          <td bgcolor="#CCCCCC">
             Role:&nbsp;
          </td>
          <td bgcolor="WHITE">
             <%=aclManagerBean.getGroupEntry().getRole().toString()%>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</form>
