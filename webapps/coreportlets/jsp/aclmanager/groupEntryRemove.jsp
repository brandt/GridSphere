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
  <tr>
    <td>
      <table bgcolor="BLACK" border="0" cellspacing="1" cellpadding="2" width="100%">
        <tr>
          <td align="center" bgcolor="#6666FF">
            <font color="WHITE"><strong>
              Remove Users From Group <%=aclManagerBean.getGroupName()%>
            </strong></font>
          </td>
        </tr>
        <tr>
          <td bgcolor="WHITE">
            Click "<font color="DARKRED">Confirm Remove</font>" to remove the selected users,
            "<font color="DARKRED">Cancel Remove</font>" otherwise.
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
      <table bgcolor="BLACK" border="0" cellspacing="1" cellpadding="2" width="100%">
        <tr>
          <td bgcolor="#6666FF" align="center" valign="middle" width="12">
            <font size="-1">
              <input type="checkbox"
               name="groupEntryID"
               checked="false"
               value=""
               onClick="javascript:GridSphere_CheckBoxList_checkAll(document.AccessControllerPortlet.groupEntryID)"/>
            </font>
          </td>
          <td bgcolor="#6666FF">
            <font color="WHITE">
              User
            </font>
          </td>
          <td bgcolor="#6666FF">
            <font color="WHITE">
              Full Name
            </font>
          </td>
          <td bgcolor="#6666FF">
            <font color="WHITE">
              Role
            </font>
          </td>
        </tr>
<% Iterator groupEntries = aclManagerBean.getGroupEntryList().iterator();
   while (groupEntries.hasNext()) {
        GroupEntry groupEntry = (GroupEntry)groupEntries.next();
        User groupEntryUser = groupEntry.getUser();
        PortletRole groupEntryRole = groupEntry.getRole(); %>
        <tr>
          <td bgcolor="#CCCCCC" align="center" valign="middle" width="12">
            <font size="-1">
              <input type="checkbox"
               name="groupEntryID"
               checked="false"
               value="<%=groupEntry.getID()%>"
               onClick="javascript:GridSphere_CheckBoxList_onClick(document.AccessControllerPortlet.groupEntryID,
                                                                   this)"/>
            </font>
          </td>
          <td bgcolor="WHITE">
            <a href="javascript:AccessControllerPortlet_viewGroupEntry_onClick('<%=groupEntryUser.getID()%>')">
              <%=groupEntryUser.getUserID()%>
            </a>
          </td>
          <td bgcolor="WHITE">
            <%=groupEntryUser.getFullName()%>
          </td>
          <td bgcolor="WHITE">
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
