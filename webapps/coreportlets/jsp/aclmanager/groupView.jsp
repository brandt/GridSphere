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
  <input type="hidden" name="userID" value=""/>
  <script language="JAVASCRIPT">
    function AccessControllerPortlet_listGroup_onClick() {
      document.AccessControllerPortlet.action="<%=aclManagerBean.getGroupListURI()%>";
      document.AccessControllerPortlet.submit();
    }

    function AccessControllerPortlet_viewGroup_onClick() {
      document.AccessControllerPortlet.action="<%=aclManagerBean.getGroupViewURI()%>";
      document.AccessControllerPortlet.submit();
    }

    function AccessControllerPortlet_newGroup_onClick() {
      document.AccessControllerPortlet.groupID.value="";
      document.AccessControllerPortlet.action="<%=aclManagerBean.getGroupEditURI()%>";
      document.AccessControllerPortlet.submit();
    }

    function AccessControllerPortlet_editGroup_onClick() {
      document.AccessControllerPortlet.action="<%=aclManagerBean.getGroupEditURI()%>";
      document.AccessControllerPortlet.submit();
    }

    function AccessControllerPortlet_deleteGroup_onClick() {
      document.AccessControllerPortlet.action="<%=aclManagerBean.getGroupDeleteURI()%>";
      document.AccessControllerPortlet.submit();
    }

    function AccessControllerPortlet_viewAccessRight_onClick(userID) {
      document.AccessControllerPortlet.userID.value=userID;
      document.AccessControllerPortlet.action="<%=aclManagerBean.getGroupViewURI()%>";
      document.AccessControllerPortlet.submit();
    }

    function AccessControllerPortlet_addUsers_onClick(groupID) {
      document.AccessControllerPortlet.action="<%=aclManagerBean.getGroupViewURI()%>";
      document.AccessControllerPortlet.submit();
    }

    function AccessControllerPortlet_removeUsers_onClick(groupID) {
      document.AccessControllerPortlet.action="<%=aclManagerBean.getGroupViewURI()%>";
      document.AccessControllerPortlet.submit();
    }

  </script>
<table border="0" cellspacing="1" cellpadding="2" width="100%">
  <tr>
    <td>
      <table bgcolor="BLACK" border="0" cellspacing="1" cellpadding="2" width="100%">
        <tr>
          <td bgcolor="#CCCCCC">
            <input type="button"
                   name="<%=AccessControllerBean.ACTION_GROUP_VIEW%>"
                   value="Refresh View"
                   onClick="javascript:AccessControllerPortlet_viewGroup_onClick()"/>
            &nbsp;&nbsp;<input type="button"
                   name="<%=AccessControllerBean.ACTION_GROUP_LIST%>"
                   value="List Groups"
                   onClick="javascript:AccessControllerPortlet_listGroup_onClick()"/>
            &nbsp;&nbsp;<input type="button"
                   name="<%=AccessControllerBean.ACTION_GROUP_EDIT%>"
                   value="New Group"
                   onClick="javascript:AccessControllerPortlet_newGroup_onClick()"/>
            &nbsp;&nbsp;<input type="button"
                   name="<%=AccessControllerBean.ACTION_GROUP_EDIT%>"
                   value="Edit Group"
                   onClick="javascript:AccessControllerPortlet_editGroup_onClick()"/>
            &nbsp;&nbsp;<input type="button"
                   name="<%=AccessControllerBean.ACTION_GROUP_DELETE%>"
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
                   onClick="javascript:AccessControllerPortlet_addUsers_onClick()"/>
            &nbsp;&nbsp;<input type="button"
                   name="<%=AccessControllerBean.ACTION_GROUP_VIEW%>"
                   value="Remove Users"
                   onClick="javascript:AccessControllerPortlet_removeUsers_onClick()"/>
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
            User Name
          </td>
          <td bgcolor="#CCCCCC">
            Full Name
          </td>
          <td bgcolor="#CCCCCC">
            Group Role
          </td>
        </tr>
<% List groupEntryList = aclManagerBean.getGroupEntryList();
   int numEntries = groupEntryList.size();
   if (numEntries == 0) { %>
        <tr>
          <td bgcolor="WHITE" colspan="4">
            <font color="DARKRED">
              No users in portlet group.
            </font>
          </td>
        </tr>
<% } else {
      for (int ii = 0; ii < numEntries; ++ii) {
        GroupEntry groupEntry = (GroupEntry)groupEntryList.get(ii);
        User user = groupEntry.getUser();
        PortletRole role = groupEntry.getRole(); %>
        <tr>
          <td bgcolor="WHITE">
            <a href="javascript:AccessControllerPortlet_viewAccessRight_onClick('<%=user.getID()%>')">
            </a>
          </td>
          <td bgcolor="WHITE">
            <%=user.getUserID()%>
          </td>
          <td bgcolor="WHITE">
            <%=user.getFullName()%>
          </td>
          <td bgcolor="WHITE">
            <%=role%>
          </td>
        </tr>
<%   }
    } %>
      </table>
    </td>
  </tr>
</table>
</form>
