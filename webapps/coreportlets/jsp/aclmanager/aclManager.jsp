<%@ page import="org.gridlab.gridsphere.portlet.PortletGroup,
                 org.gridlab.gridsphere.portlets.core.beans.AccessControllerBean,
                 java.util.List" %>
<jsp:useBean id="aclManagerBean"
             class="org.gridlab.gridsphere.portlets.core.beans.AccessControllerBean"
             scope="request"/>
<script language="JAVASCRIPT">

    function AccessControlManagerPortlet_listGroup_onClick() {
      var action = "<%=aclManagerBean.getActionURI(AccessControllerBean.ACTION_GROUP_LIST)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessContollerPortlet.submit();
    }

    function AccessControlManagerPortlet_viewGroup_onClick(groupID) {
      document.AccessControlManagerPortlet.groupID.value=groupID;
      var action = "<%=aclManagerBean.getActionURI(AccessControllerBean.ACTION_GROUP_VIEW)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessContollerPortlet.submit();
    }

    function AccessControlManagerPortlet_newGroup_onClick() {
      document.AccessControlManagerPortlet.groupID.value="";
      var action = "<%=aclManagerBean.getActionURI(AccessControllerBean.ACTION_GROUP_EDIT)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessContollerPortlet.submit();
    }

    function AccessControllerPortlet_editGroup_onClick() {
      var action = "<%=aclManagerBean.getActionURI(AccessControllerBean.ACTION_GROUP_EDIT)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessContollerPortlet.submit();
    }

    function AccessContollerPortlet_confirmEditGroup_onClick() {
      var action = "<%=aclManagerBean.getActionURI(AccessControllerBean.ACTION_GROUP_EDIT_CONFIRM)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessContollerPortlet.submit();
    }

    function AccessContollerPortlet_cancelEditGroup_onClick() {
      var action = "<%=aclManagerBean.getActionURI(AccessControllerBean.ACTION_GROUP_EDIT_CANCEL)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessContollerPortlet.submit();
    }

    function AccessControllerPortlet_deleteGroup_onClick() {
      var action = "<%=aclManagerBean.getActionURI(AccessControllerBean.ACTION_GROUP_DELETE)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessContollerPortlet.submit();
    }

    function AccessContollerPortlet_confirmDeleteGroup_onClick() {
      var action = "<%=aclManagerBean.getActionURI(AccessControllerBean.ACTION_GROUP_DELETE_CONFIRM)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessContollerPortlet.submit();
    }

    function AccessContollerPortlet_cancelDeleteGroup_onClick() {
      var action = "<%=aclManagerBean.getActionURI(AccessControllerBean.ACTION_GROUP_DELETE_CANCEL)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessContollerPortlet.submit();
    }
    function AccessControllerPortlet_viewGroupEntry_onClick(groupEntryID) {
      document.AccessControllerPortlet.groupEntryID.value=groupEntryID;
      var action = "<%=aclManagerBean.getActionURI(AccessControllerBean.ACTION_GROUP_VIEW)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessContollerPortlet.submit();
    }

    function AccessControllerPortlet_newGroupEntry_onClick() {
      document.AccessControllerPortlet.groupEntryID.value="";
      var action = "<%=aclManagerBean.getActionURI(AccessControllerBean.ACTION_GROUP_ENTRY_EDIT)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessContollerPortlet.submit();
    }

    function AccessControllerPortlet_editGroupEntry_onClick() {
      var action = "<%=aclManagerBean.getActionURI(AccessControllerBean.ACTION_GROUP_ENTRY_EDIT)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessContollerPortlet.submit();
    }

    function AccessContollerPortlet_confirmEditGroupEntry_onClick() {
      var action = "<%=aclManagerBean.getActionURI(AccessControllerBean.ACTION_GROUP_ENTRY_EDIT_CONFIRM)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessContollerPortlet.submit();
    }

    function AccessContollerPortlet_cancelEditGroupEntry_onClick() {
      var action = "<%=aclManagerBean.getActionURI(AccessControllerBean.ACTION_GROUP_ENTRY_EDIT_CANCEL)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessContollerPortlet.submit();
    }

    function AccessControllerPortlet_addGroupEntries_onClick() {
      var action = "<%=aclManagerBean.getActionURI(AccessControllerBean.ACTION_GROUP_ENTRY_ADD)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessControllerPortlet.submit();
    }

    function AccessControllerPortlet_confirmAddGroupEntries_onClick() {
      var action = "<%=aclManagerBean.getActionURI(AccessControllerBean.ACTION_GROUP_ENTRY_ADD_CONFIRM)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessControllerPortlet.submit();
    }

    function AccessControllerPortlet_cancelAddGroupEntries_onClick() {
      var action = "<%=aclManagerBean.getActionURI(AccessControllerBean.ACTION_GROUP_ENTRY_ADD_CONFIRM)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessControllerPortlet.submit();
    }

    function AccessControllerPortlet_removeGroupEntries_onClick(groupID) {
      var action = "<%=aclManagerBean.getActionURI(AccessControllerBean.ACTION_GROUP_ENTRY_REMOVE)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessControllerPortlet.submit();
    }

    function AccessControllerPortlet_confirmRemoveGroupEntries_onClick() {
      var action = "<%=aclManagerBean.getActionURI(AccessControllerBean.ACTION_GROUP_ENTRY_REMOVE_CONFIRM)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessControllerPortlet.submit();
    }

    function AccessControllerPortlet_cancelRemoveGroupEntries_onClick() {
      var action = "<%=aclManagerBean.getActionURI(AccessControllerBean.ACTION_GROUP_ENTRY_REMOVE_CONFIRM)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessControllerPortlet.submit();
    }

</script>
