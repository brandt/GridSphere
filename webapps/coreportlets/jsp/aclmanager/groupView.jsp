<jsp:include page="/jsp/aclmanager/aclManager.jsp" flush="true"/>
<%@ page import="org.gridlab.gridsphere.portlet.User,
                 org.gridlab.gridsphere.portlet.PortletGroup,
                 org.gridlab.gridsphere.portlets.core.beans.AccessControllerBean,
                 org.gridlab.gridsphere.portlets.core.beans.AccessControllerAction,
                 java.util.List,
                 org.gridlab.gridsphere.portlet.PortletRole,
                 org.gridlab.gridsphere.portlet.PortletGroup,
                 java.util.Iterator,
                 org.gridlab.gridsphere.services.security.acl.GroupEntry"%>
<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<jsp:useBean id="aclManagerBean"
             class="org.gridlab.gridsphere.portlets.core.beans.AccessControllerBean"
             scope="request"/>
<form name="AccessControllerPortlet" method="POST" action="">
  <input type="hidden" name="groupID" value="<%=aclManagerBean.getGroupID()%>"/>
  <input type="hidden" name="groupEntryID" value="<%=aclManagerBean.getGroupEntryID()%>"/>
<table border="0" cellspacing="1" cellpadding="2" width="100%">
  <tr>
    <td>
      <table bgcolor="BLACK" border="0" cellspacing="1" cellpadding="2" width="100%">
        <tr>
          <td bgcolor="#CCCCCC">
            <input type="button"
                   name="<%=AccessControllerAction.ACTION_GROUP_VIEW%>"
                   value="Refresh View"
                   onClick="javascript:AccessControllerPortlet_viewGroup_onClick()"/>
            &nbsp;&nbsp;<input type="button"
                   name="<%=AccessControllerAction.ACTION_GROUP_LIST%>"
                   value="List Groups"
                   onClick="javascript:AccessControllerPortlet_listGroup_onClick()"/>
            &nbsp;&nbsp;<input type="button"
                   name="<%=AccessControllerAction.ACTION_GROUP_EDIT%>"
                   value="New Group"
                   onClick="javascript:AccessControllerPortlet_newGroup_onClick()"/>
            &nbsp;&nbsp;<input type="button"
                   name="<%=AccessControllerAction.ACTION_GROUP_EDIT%>"
                   value="Edit Group"
                   onClick="javascript:AccessControllerPortlet_editGroup_onClick()"/>
            &nbsp;&nbsp;<input type="button"
                   name="<%=AccessControllerAction.ACTION_GROUP_DELETE%>"
                   value="Delete Group"
                   onClick="javascript:AccessControllerPortlet_deleteGroup_onClick()"/>
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
             Group ID:&nbsp;
          </td>
          <td bgcolor="WHITE">
             <%=aclManagerBean.getGroupID()%>
          </td>
        </tr>
        <tr>
          <td bgcolor="#CCCCCC">
             Group Name:&nbsp;
          </td>
          <td bgcolor="WHITE">
             <%=aclManagerBean.getGroupName()%>
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
                   name="<%=AccessControllerBean.ACTION_GROUP_VIEW%>"
                   value="Add Users"
                   onClick="javascript:AccessControllerPortlet_addGroupEntry_onClick()"/>
            &nbsp;&nbsp;<input type="button"
                   name="<%=AccessControllerBean.ACTION_GROUP_VIEW%>"
                   value="Remove Users"
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
          <td bgcolor="#CCCCCC">
            ID
          </td>
          <td bgcolor="#CCCCCC">
            User
          </td>
          <td bgcolor="#CCCCCC">
            Full Name
          </td>
          <td bgcolor="#CCCCCC">
            Role
          </td>
        </tr>
<% Iterator groupEntries = aclManagerBean.getGroupEntryList().iterator();
   if (groupEntries.hasNext()) {
       while (groupEntries.hasNext()) {
           GroupEntry groupEntry = (GroupEntry)groupEntries.next();
           User groupEntryUser = groupEntry.getUser();
           PortletRole groupEntryRole = groupEntry.getRole(); %>
        <tr>
          <td bgcolor="WHITE">
            <a href="javascript:AccessControllerPortlet_viewGroupEntry_onClick('<%=groupEntryUser.getID()%>')">
            </a>
          </td>
          <td bgcolor="WHITE">
            <%=groupEntryUser.getUserID()%>
          </td>
          <td bgcolor="WHITE">
            <%=groupEntryUser.getFullName()%>
          </td>
          <td bgcolor="WHITE">
            <%=groupEntryRole%>
          </td>
        </tr>
<%     }
   } else { %>
        <tr>
          <td bgcolor="WHITE" colspan="4">
            <font color="DARKRED">
              No users in portlet group.
            </font>
          </td>
        </tr>
<%  } %>
      </table>
    </td>
  </tr>
</table>
</form>
