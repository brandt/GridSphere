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
  <script type="text/javascript">

    function AccessControllerPortlet_viewGroup_onClick(groupID) {
      document.AccessControllerPortlet.groupID.value=groupID;
      var action = "<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_VIEW)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessContollerPortlet.submit();
    }

    function AccessControllerPortlet_editGroupEntry_onClick() {
      var action = "<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_ENTRY_EDIT)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessContollerPortlet.submit();
    }

    function AccessControllerPortlet_removeGroupEntry_onClick() {
      var action = "<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_ENTRY_REMOVE)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessContollerPortlet.submit();
    }

  </script>
<table class="portlet-pane" cellspacing="1">
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-title">
              Group Entry for User <%=aclManagerBean.getGroupEntryUser().getUserName()%>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-actions">
            <input type="button"
                   name="<%=AccessControllerBean.ACTION_GROUP_VIEW%>"
                   value="Back To Group"
                   onClick="javascript:AccessControllerPortlet_viewGroup_onClick('<%=aclManagerBean.getGroupID()%>')"/>
            &nbsp;&nbsp;<input type="button"
                   name="<%=AccessControllerBean.ACTION_GROUP_ENTRY_EDIT%>"
                   value="Edit Group Entry="
                   onClick="javascript:AccessControllerPortlet_editGroupEntry_onClick()"/>
            &nbsp;&nbsp;<input type="button"
                   name="<%=AccessControllerBean.ACTION_GROUP_ENTRY_REMOVE%>"
                   value="Delete Group Entry"
                   onClick="javascript:AccessControllerPortlet_removeGroupEntry_onClick()"/>
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
             Entry:&nbsp;
          </td>
          <td class="portlet-frame-text">
             <%=aclManagerBean.getGroupEntryID()%>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             Group:&nbsp;
          </td>
          <td class="portlet-frame-text">
             <%=aclManagerBean.getGroupEntry().getGroup().getName()%>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             User:&nbsp;
          </td>
          <td class="portlet-frame-text">
             <%=aclManagerBean.getGroupEntry().getUser().getUserName()%>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             Full Name:&nbsp;
          </td>
          <td class="portlet-frame-text">
             <%=aclManagerBean.getGroupEntry().getUser().getFullName()%>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             Role:&nbsp;
          </td>
          <td class="portlet-frame-text">
             <%=aclManagerBean.getGroupEntry().getRole().toString()%>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</form>
