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
<script type="text/javascript">

  /**************************************************************************
   * GridSphere Form Action Functions
   **************************************************************************/

  function GridSphere_Form_submitAction(form, action) {
    form.action=action;
    form.submit();
  }

  /**************************************************************************
   * GridSpehre Check Box List Functions
   **************************************************************************/

  function GridSphere_CheckBoxList_checkAll(list) {

    if (list[0].checked == true) {

      // alert("GridSphere CheckBoxList Check All True");

      for (i = 1; i < list.length; i++) {

        list[i].checked = true;
      }

      // Select first list value if none selected yet
      if (list[0].value == "") {

        if (list.length > 1) {

          list[0].value = list[1].value;
        }
      }

    } else {

      // alert("GridSphere CheckBoxList Check All False");

      GridSphere_CheckBoxList_clear(list);
    }
  }

  function GridSphere_CheckBoxList_clear(list) {

    // alert("GridSphere CheckBoxList Clear");

    for (i = 0; i < list.length; i++) {

      list[i].checked = false;
    }

    // Clear selected value
    list[0].value = "";
  }

  function GridSphere_CheckBoxList_checkOne(list)
  {
    // alert("GridSphere CheckBoxList Check One");

    // Uncheck "all" option
    list[0].checked = false;

    // Uncheck those that don't match selection
    for (i = 1; i < list.length; i++) {

      if (list[i].value != list[0].value) {

        list[i].checked = false;
      }
    }
  }

  function GridSphere_CheckBoxList_onClick(list, newSelection)
  {
    // alert("GridSphere CheckBoxList On Click");

    // alert("GridSphere CheckBoxList current selection: " + list[0].value);

    if (newSelection.checked == true) {

      // Save selection only if none made yet
      if (list[0].value == "") {

        list[0].value = newSelection.value;
      }

    } else {

      // If saved selection was this one
      if (list[0].value == newSelection.value) {

        var found = false;

        // Set selection to first checked item other than this
        for (i = 1; i < list.length && !found; i++) {

          if (list[i].checked == true) {

            if (list[i].value != item.value) {

              list[0].value = list[i].value;

              found = true;
            }
          }
        }

        // If we didn't find a checked value
        if (!found) {

          // Set selection to none
          list[0].value = "";
        }
      }
    }

    // alert("GridSphere CheckBoxList new selection: " + selection.value);
  }

  function GridSphere_CheckBoxList_validateCheckOneOrMore(list)
  {
    // alert("GridSphere CheckBoxList Validate Check One Or More");

    return (list[0].value != "");
  }

  /*******************************************************************************
    Call this to setup check box list
  *******************************************************************************/

  GridSphere_CheckBoxList_checkAll(document.AccessControllerPortlet.groupEntryID);

</script>
