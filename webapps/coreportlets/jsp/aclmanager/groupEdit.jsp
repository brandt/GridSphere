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
      action="<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_EDIT)%>">
  <input type="hidden" name="groupID" value="<%=aclManagerBean.getGroupID()%>"/>
  <script language="JAVASCRIPT">

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

  </script>
<table class="portlet-pane" cellspacing="1">
<% if (aclManagerBean.isFormInvalid()) { %>
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-message-alert">
            <%=aclManagerBean.getFormInvalidMessage()%>
          </td>
        </tr>
      </table>
    </td>
  </tr>
<% } %>
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-title">
<% if (aclManagerBean.getGroupID().equals("")) { %>
              New Group
<% } else { %>
              Edit Group [<%=aclManagerBean.getGroupName()%>]
<% } %>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-actions">
            <input type="submit"
                   name="<%=AccessControllerBean.ACTION_GROUP_EDIT_CONFIRM%>"
                   value="Save Group"
                   onClick="javascript:AccessControllerPortlet_confirmEditGroup_onClick()"/>
            &nbsp;&nbsp;<input type="submit"
                   name="<%=AccessControllerBean.ACTION_GROUP_EDIT_CANCEL%>"
                   value="Cancel Edit"
                   onClick="javascript:AccessControllerPortlet_cancelEditGroup_onClick()"/>
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
               Group Name
          </td>
          <td class="portlet-frame-input">
             <input type="text"
                    name="groupName"
                    value="<%=aclManagerBean.getGroupName()%>"/>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
              Group Label:&nbsp;
          </td>
          <td class="portlet-frame-input">
             <input type="text"
                    name="groupLabel"
                    value=""/>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
              Group Description:&nbsp;
          </td>
          <td class="portlet-frame-input">
             <input type="text"
                    name="groupLabel"
                    value=""/>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</form>
