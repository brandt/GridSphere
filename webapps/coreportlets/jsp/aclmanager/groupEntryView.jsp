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
<table border="0" cellspacing="1" cellpadding="2" width="100%">
  <tr>
    <td>
      <table bgcolor="BLACK" border="0" cellspacing="1" cellpadding="2" width="100%">
        <tr>
          <td align="center" bgcolor="#6666FF">
            <font color="WHITE"><strong>
              Group Entry for User <%=aclManagerBean.getGroupEntryUser().getUserName()%>
            </strong></font>
          </td>
        </tr>
        <tr>
          <td bgcolor="#CCCCCC">
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
      <table bgcolor="BLACK" border="0" cellspacing="1" cellpadding="2" width="100%">
        <tr>
          <td width="200" bgcolor="#CCCCCC">
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
