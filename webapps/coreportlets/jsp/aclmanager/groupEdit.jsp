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
<form name="AccessContollerPortlet" method="POST" action="<%=aclManagerBean.getGroupEditURI()%>">
  <input type="hidden" name="groupID" value="<%=aclManagerBean.getGroupID()%>"/>
  <script language="JAVASCRIPT">

    function AccessContollerPortlet_confirmEditGroup_onClick() {
      var action = "<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_EDIT_CONFIRM)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessContollerPortlet.submit();
    }

    function AccessContollerPortlet_cancelEditGroup_onClick() {
      var action = "<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_EDIT_CANCEL)%>";
      document.AccessControllerPortlet.action=action;
      document.AccessContollerPortlet.submit();
    }

  </script>
<% if (aclManagerBean.isFormInvalid()) { %>
  <tr>
    <td>
      <table bgcolor="BLACK" border="0" cellspacing="1" cellpadding="2" width="100%">
        <tr>
          <td bgcolor="WHITE">
            <font color="DARKRED"><bold>
              <%=aclManagerBean.getFormInvalidMessage()%>
            </bold></font>
          </td>
        </tr>
      </table>
    </td>
  </tr>
<% } %>
<table border="0" cellspacing="1" cellpadding="2" width="100%">
  <tr>
    <td>
      <table bgcolor="BLACK" border="0" cellspacing="1" cellpadding="2" width="100%">
        <tr>
          <td align="center" bgcolor="#6666FF">
            <font color="WHITE"><strong>
              Edit Group [<%=aclManagerBean.getGroupName()%>]
            </strong></font>
          </td>
        </tr>
        <tr>
          <td bgcolor="#CCCCCC">
            <input type="submit"
                   name="<%=AccessControllerBean.ACTION_GROUP_EDIT_CONFIRM%>"
                   value="Save Group"
                   onClick="javascript:AccessContollerPortlet_confirmEditGroup_onClick()"/>
            &nbsp;&nbsp;<input type="submit"
                   name="<%=AccessControllerBean.ACTION_GROUP_EDIT_CANCEL%>"
                   value="Cancel Edit"
                   onClick="javascript:AccessContollerPortlet_cancelEditGroup_onClick()"/>
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
               Group Name
          </td>
          <td bgcolor="WHITE">
             <input type="text"
                    name="groupName"
                    value="<%=aclManagerBean.getGroupName()%>"/>
          </td>
        </tr>
        <tr>
          <td bgcolor="#CCCCCC">
              Group Label:&nbsp;
          </td>
          <td bgcolor="WHITE">
             <input type="text"
                    name="groupLabel"
                    value=""/>
          </td>
        </tr>
        <tr>
          <td bgcolor="#CCCCCC">
              Group Description:&nbsp;
          </td>
          <td bgcolor="WHITE">
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
