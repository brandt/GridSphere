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
      action="<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_ENTRY_ADD)%>">
  <input type="hidden" name="groupID" value="<%=aclManagerBean.getGroupID()%>"/>
  <input type="hidden" name="groupEntryUserID" value=""/>
  <input type="hidden" name="groupEntryUserIDCheckAll" value="false"/>
  <script language="JAVASCRIPT">

    function AccessControllerPortlet_listGroup_onClick() {
      var action = "<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_LIST)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessContollerPortlet.submit();
    }

    function AccessControllerPortlet_viewGroup_onClick(groupID) {
      document.AccessControllerPortlet.groupID.value=groupID;
      var action = "<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_VIEW)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessContollerPortlet.submit();
    }

    function AccessControllerPortlet_newGroup_onClick() {
      document.AccessControllerPortlet.groupID.value="";
      var action = "<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_EDIT)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessContollerPortlet.submit();
    }

    function AccessControllerPortlet_editGroup_onClick() {
      var action = "<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_EDIT)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessContollerPortlet.submit();
    }

    function AccessContollerPortlet_confirmEditGroup_onClick() {
      var action = "<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_EDIT_CONFIRM)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessContollerPortlet.submit();
    }

    function AccessContollerPortlet_cancelEditGroup_onClick() {
      var action = "<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_EDIT_CANCEL)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessContollerPortlet.submit();
    }

    function AccessControllerPortlet_deleteGroup_onClick() {
      var action = "<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_DELETE)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessContollerPortlet.submit();
    }

    function AccessContollerPortlet_confirmDeleteGroup_onClick() {
      var action = "<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_DELETE_CONFIRM)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessContollerPortlet.submit();
    }

    function AccessContollerPortlet_cancelDeleteGroup_onClick() {
      var action = "<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_DELETE_CANCEL)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessContollerPortlet.submit();
    }

    function AccessControllerPortlet_viewGroupEntry_onClick(groupEntryID) {
      document.AccessControllerPortlet.groupEntryID.value=groupEntryID;
      var action = "<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_VIEW)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessContollerPortlet.submit();
    }

    function AccessControllerPortlet_newGroupEntry_onClick() {
      document.AccessControllerPortlet.groupEntryID.value="";
      var action = "<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_ENTRY_EDIT)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessContollerPortlet.submit();
    }

    function AccessControllerPortlet_editGroupEntry_onClick() {
      var action = "<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_ENTRY_EDIT)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessContollerPortlet.submit();
    }

    function AccessContollerPortlet_confirmEditGroupEntry_onClick() {
      var action = "<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_ENTRY_EDIT_CONFIRM)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessContollerPortlet.submit();
    }

    function AccessContollerPortlet_cancelEditGroupEntry_onClick() {
      var action = "<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_ENTRY_EDIT_CANCEL)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessContollerPortlet.submit();
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
          <td align="center" bgcolor="#6666FF">
            <font color="WHITE"><strong>
              Group <%=aclManagerBean.getGroupName()%>
            </strong></font>
          </td>
        </tr>
        <tr>
          <td bgcolor="WHITE">
            Select the users you want to add and specify the role they play within this group.
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
                   name="<%=AccessControllerBean.ACTION_GROUP_ENTRY_ADD_CONFIRM%>"
                   value="Save"
                   onClick="javascript:AccessControllerPortlet_confirmAddGroupEntry_onClick()"/>
            &nbsp;&nbsp;<input type="button"
                   name="<%=AccessControllerBean.ACTION_GROUP_ENTRY_ADD_CANCEL%>"
                   value="Cancel"
                   onClick="javascript:AccessControllerPortlet_cancelAddGroupEntry_onClick()"/>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td>
      <table bgcolor="BLACK" border="0" cellspacing="1" cellpadding="2" width="100%">
        <tr>
          <td bgcolor="#6666FF" align="center" valign="middle" width="12">
            <font size="-1">
              <input type="checkbox"
               name="groupEntryUserIDs"
               value=""
               onClick="javascript:GridSphere_CheckBoxList_checkAll(document.AccessControllerPortlet.groupEntryUserIDs,
                                                                    document.AccessControllerPortlet.groupEntryUserID,
                                                                    document.AccessControllerPortlet.groupEntryUserIDCheckAll)"/>
            </font>
          </td>
          <td bgcolor="#6666FF">
            <font color="WHITE">
              User Name
            </font>
          </td>
          <td bgcolor="#6666FF">
            <font color="WHITE">
              Full Name
            </font>
          </td>
          <td bgcolor="#6666FF">
            <font color="WHITE">
              Group Role
            </font>
          </td>
        </tr>
<%  List usersNotInGroup = aclManagerBean.getUsersNotInGroup();
    for (int ii = 0; ii < usersNotInGroup.size(); ++ii) {
         User user = (User)usersNotInGroup.get(ii); %>
        <tr>
          <td bgcolor="#CCCCCC" align="center" valign="middle" width="12">
            <font size="-1">
              <input type="checkbox"
               name="groupEntryUserIDs"
               value="<%=user.getID()%>"
               onClick="javascript:GridSphere_CheckBoxList_onClick(this,
                                                                   document.AccessControllerPortlet.groupEntryUserIDs,
                                                                   document.AccessControllerPortlet.groupEntryUserID,
                                                                   document.AccessControllerPortlet.groupEntryUserIDCheckAll)"/>
            </font>
          </td>
          <td bgcolor="WHITE">
            <%=user.getUserName()%>
          </td>
          <td bgcolor="WHITE">
            <%=user.getFullName()%>
          </td>
          <td bgcolor="WHITE">
             <select name="groupEntryRoleNames<%=ii%>">
               <option label="USER"
                       value="user"/>
               <option label="ADMIN"
                       value="admin"=/>
               <option label="GUEST"
                       value="guest"=/>
             </select>
          </td>
        </tr>
<% } %>
      </table>
    </td>
  </tr>
</table>
<script>

  function GridSphere_CheckBoxList_checkAll(list, selection, checkAllFlag) {

    // alert("GridSphere CheckBoxList Check All Current: " + checkAllFlag.value);

    if (checkAllFlag.value == "false") {

      // alert("GridSphere CheckBoxList Check All True");

      for (i = 0; i < list.length; i++) {

        list[i].checked = true;
      }

      checkAllFlag.value = "true";

      // Select first list value if none selected yet
      if (selection.value == "") {

        if (list.length > 0) {

          selection.value = list[1].value;
        }
      }

    } else {

      // alert("GridSphere CheckBoxList Check All False");

      GridSphere_CheckBoxList_clear(list, selection, checkAllFlag);
    }
  }

  function GridSphere_CheckBoxList_clear(list, selection, checkAllFlag) {

    // alert("GridSphere CheckBoxList Clear");

    for (i = 0; i < list.length; i++) {

      list[i].checked = false;
    }

    // Clear selected value
    selection.value = "0";

    checkAllFlag.value = "false";
  }

  function GridSphere_CheckBoxList_checkOne(list, selection, checkAllFlag)
  {
    // alert("GridSphere CheckBoxList Check One");

    checkAllFlag.value = "false";

    // Uncheck "all" option
    list[0].checked = false;

    // Uncheck those that don't match selection
    for (i = 1; i < list.length; i++) {

      if (list[i].value != selection.value) {

        list[i].checked = false;
      }
    }
  }

  function GridSphere_CheckBoxList_onClick(newSelection, list, selection, checkAllFlag)
  {
    // alert("GridSphere CheckBoxList On Click");

    // alert("GridSphere CheckBoxList current selection: " + selection.value);

    if (newSelection.checked == true) {

      // Save selection only if none made yet
      if (selection.value == "") {

        selection.value = newSelection.value;
      }

    } else {

      // If saved selection was this one
      if (selection.value == newSelection.value) {

        var found = false;

        // Set selection to first checked item other than this
        for (i = 1; i < list.length && !found; i++) {

          if (list[i].checked == true) {

            if (list[i].value != item.value) {

              selection.value = list[i].value;

              found = true;
            }
          }
        }

        // If we didn't find a checked value
        if (!found) {

          // Set selection to none
          selection.value = "";
        }
      }
    }

    // alert("GridSphere CheckBoxList new selection: " + selection.value);
  }

  function GridSphere_CheckBoxList_validateCheckOneOrMore(list, selection, checkAllFlag)
  {
    // alert("GridSphere CheckBoxList Validate Check One Or More");

    return (selection.value != "");
  }

</script>
</form>
