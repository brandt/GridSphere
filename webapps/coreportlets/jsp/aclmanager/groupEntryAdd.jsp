<jsp:include page="/jsp/aclmanager/aclManager.jsp" flush="true"/>
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
<form name="AccessControllerPortlet" method="POST" action="">
  <input type="hidden" name="groupID" value="<%=aclManagerBean.getGroupID()%>"/>
  <input type="hidden" name="groupEntryID" value="<%=aclManagerBean.getGroupEntryID()%>"/>
<table border="0" cellspacing="1" cellpadding="2" width="100%">
  <tr>
    <td>
      <table bgcolor="BLACK" border="0" cellspacing="1" cellpadding="2" width="100%">
        <tr>
          <td bgcolor="#BLACK">
            <font color="WHITE" size="+1">
              Add Group Entries
            </font>
          </td>
        </tr>
      </table>
    </td>
    <td>
      <table bgcolor="BLACK" border="0" cellspacing="1" cellpadding="2" width="100%">
        <tr>
          <td bgcolor="#CCCCCC">
            <input type="button"
                   name="<%=AccessControllerBean.ACTION_GROUP_ENTRY_VIEW%>"
                   value="Save"
                   onClick="javascript:AccessControllerPortlet_confirmAddGroupEntry_onClick()"/>
            &nbsp;&nbsp;<input type="button"
                   name="<%=AccessControllerBean.ACTION_GROUP_ENTRY_REMOVE%>"
                   value="Delete"
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
             Users not in group:
          </td>
          <td bgcolor="WHITE">
            <select size="8" name="groupEntryUserID">
<% Iterator usersNotInGroup = aclManagerBean.getUsersNotInGroup().iterator();
   while (usersNotInGroup.hasNext()) {
        User user = (User)usersNotInGroup.next(); %>
               <option label="<%=user.getUserName()%>"
                       value="<%=user.getID()%>"/>
<% } %>
            </select>
          </td>
        </tr>
        <tr>
          <td bgcolor="#CCCCCC">
             Role in group:&nbsp;
          </td>
          <td bgcolor="WHITE">
             <select name="groupEntryRoleName">
               <option label="USER"
                       value="user"/>
               <option label="ADMIN"
                       value="admin"=/>
               <option label="GUEST"
                       value="guest"=/>
             </select>
          </td>
        </tr>
        </tr>
      </table>
    </td>
  </tr>
</table>
</form>
