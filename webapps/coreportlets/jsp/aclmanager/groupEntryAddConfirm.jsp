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
<form name="AccessControllerPortlet" method="POST" action="<%=aclManagerBean.getGroupViewURI()%>">
  <input type="hidden" name="groupID" value="<%=aclManagerBean.getGroupID()%>"/>
  <input type="hidden" name="groupEntryID" value="<%=aclManagerBean.getGroupEntryID()%>"/>
<table border="0" cellspacing="1" cellpadding="2" width="100%">
  <tr>
    <td>
      <table bgcolor="BLACK" border="0" cellspacing="1" cellpadding="2" width="100%">
        <tr>
          <td bgcolor="#BLACK">
            <font color="WHITE" size="+1">
              Added Group Entries
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
                   value="Edit Entry="
                   onClick="javascript:AccessControllerPortlet_editGroupEntry_onClick()"/>
            &nbsp;&nbsp;<input type="button"
                   name="<%=AccessControllerBean.ACTION_GROUP_ENTRY_REMOVE%>"
                   value="Delete Entry"
                   onClick="javascript:AccessControllerPortlet_removeGroupEntry_onClick()"/>
            &nbsp;&nbsp;<input type="button"
                   name="<%=AccessControllerBean.ACTION_GROUP_LIST%>"
                   value="Back To Group"
                   onClick="javascript:AccessControllerPortlet_viewGroup_onClick('<%=aclManagerBean.getGroupID()%>')"/>
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
