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
      action="<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_ENTRY_ADD)%>">
  <input type="hidden" name="groupID" value="<%=aclManagerBean.getGroupID()%>"/>
  <script type="text/javascript">

    function AccessControllerPortlet_listGroup_onClick() {
      document.AccessControllerPortlet.action="<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_LIST)%>";
      document.AccessControllerPortlet.submit();
    }

    function AccessControllerPortlet_viewGroup_onClick() {
      document.AccessControllerPortlet.action="<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_VIEW)%>";
      document.AccessControllerPortlet.submit();
    }

    function AccessControllerPortlet_confirmAddGroupEntry_onClick() {
      var validate = GridSphere_CheckBoxList_validateCheckOneOrMore(document.AccessControllerPortlet.groupEntryUserID);
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
      var action = "<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_ENTRY_ADD_CANCEL)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessControllerPortlet.submit();
    }

  </script>
<%  List usersNotInGroup = aclManagerBean.getUsersNotInGroup(); %>
<table class="portlet-pane" cellspacing="1">
<%  if (usersNotInGroup.size() == 0) { %>
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-actions">
            <input type="button"
                   name="<%=AccessControllerBean.ACTION_GROUP_VIEW%>"
                   value="Back To Group"
                   onClick="javascript:AccessControllerPortlet_viewGroup_onClick()"/>
            &nbsp;&nbsp;<input type="button"
                   name="<%=AccessControllerBean.ACTION_GROUP_LIST%>"
                   value="List Groups"
                   onClick="javascript:AccessControllerPortlet_listGroup_onClick()"/>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-message-alert">
            There are no users to add to this group.
          </td>
        </tr>
      </table>
    </td>
  </tr>
<%  } else { %>
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-actions">
            <input type="button"
                   name="<%=AccessControllerBean.ACTION_GROUP_ENTRY_ADD_CONFIRM%>"
                   value="Add Users"
                   onClick="javascript:AccessControllerPortlet_confirmAddGroupEntry_onClick()"/>
            &nbsp;&nbsp;<input type="button"
                   name="<%=AccessControllerBean.ACTION_GROUP_ENTRY_ADD_CANCEL%>"
                   value="Cancel Add"
                   onClick="javascript:AccessControllerPortlet_cancelAddGroupEntry_onClick()"/>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-message">
            Select the users you want to add to this group and specify the role each user plays within this group.
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
               name="groupEntryUserID"
               onClick="javascript:GridSphere_CheckBoxList_checkAll(document.AccessControllerPortlet.groupEntryUserID)"/>
          </td>
          <td class="portlet-frame-header">
              User Name
          </td>
          <td class="portlet-frame-header">
              Full Name
          </td>
          <td class="portlet-frame-header">
              Group Role
          </td>
        </tr>
<%      for (int ii = 0; ii < usersNotInGroup.size(); ++ii) {
            User user = (User)usersNotInGroup.get(ii); %>
        <tr>
          <td class="portlet-frame-entry-checkbox">
              <input type="checkbox"
               name="groupEntryUserID"
               value="<%=user.getID()%>"
               onClick="javascript:GridSphere_CheckBoxList_onClick(document.AccessControllerPortlet.groupEntryUserID,
                                                                   this)"/>
          </td>
          <td class="portlet-frame-text">
            <%=user.getUserName()%>
          </td>
          <td class="portlet-frame-text">
            <%=user.getFullName()%>
          </td>
          <td class="portlet-frame-text">
             <select name="groupEntryRoleName">
               <option label="USER"
                       value="<%=user.getID()%>:user"></option>
               <option label="ADMIN"
                       value="<%=user.getID()%>:admin"></option>
               <option label="GUEST"
                       value="<%=user.getID()%>:guest"></option>
             </select>
          </td>
        </tr>
<%      }
    }
%>
      </table>
    </td>
  </tr>
</table>
</form>
