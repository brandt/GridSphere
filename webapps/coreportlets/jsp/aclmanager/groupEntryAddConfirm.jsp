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
      action="<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_VIEW)%>">
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

  </script>
<table class="portlet-pane" cellspacing="1">
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-message">
            The following entries were <span style="portlet-text-bold">added</span> tp this group.
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
             Users Added To Group [<%=aclManagerBean.getGroupName()%>]
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
                   name="<%=AccessControllerBean.ACTION_GROUP_VIEW%>"
                   value="Back To Group"
                   onClick="javascript:AccessControllerPortlet_viewGroup_onClick()"/>
            &nbsp;&nbsp;<input type="button"
                   name="<%=AccessControllerBean.ACTION_GROUP_ENTRY_ADD%>"
                   value="List Groups"
                   onClick="javascript:AccessControllerPortlet_listGroup_onClick()"/>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
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
<% Iterator groupEntries = aclManagerBean.getGroupEntryList().iterator();
   while (groupEntries.hasNext()) {
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
<% }%>
      </table>
    </td>
  </tr>
</table>
</form>
