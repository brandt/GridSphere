<%@ page import="org.gridlab.gridsphere.portlet.PortletGroup,
                 org.gridlab.gridsphere.portlets.core.beans.AccessControllerBean,
                 java.util.List" %>
<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<jsp:useBean id="aclManagerBean"
             class="org.gridlab.gridsphere.portlets.core.beans.AccessControllerBean"
             scope="request"/>
<form name="AccessControllerPortlet" method="POST"
      action="<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_LIST)%>">
  <input type="hidden" name="groupID" value=""/>
  <script language="JAVASCRIPT">

    function AccessControllerPortlet_listGroup_onClick() {
      document.AccessControllerPortlet.action="<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_LIST)%>";
      document.AccessControllerPortlet.submit();
    }

    function AccessControllerPortlet_newGroup_onClick() {
      document.AccessControllerPortlet.groupID.value="";
      document.AccessControllerPortlet.action="<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_EDIT)%>";
      document.AccessControllerPortlet.submit();
    }

    function AccessControllerPortlet_viewGroup_onClick(groupID) {
      document.AccessControllerPortlet.groupID.value=groupID;
      document.AccessControllerPortlet.action="<%=aclManagerBean.getPortletActionURI(AccessControllerBean.ACTION_GROUP_VIEW)%>";
      document.AccessControllerPortlet.submit();
    }
  </script>
<table border="0" cellspacing="1" cellpadding="2" width="100%">
  <tr>
    <td>
      <table bgcolor="BLACK" border="0" cellspacing="1" cellpadding="2" width="100%">
        <tr>
          <td align="center" bgcolor="#6666FF">
            <font color="WHITE"><strong>
              List Groups
            </strong></font>
          </td>
        </tr>
        <tr>
          <td bgcolor="#CCCCCC">
            <input type="button"
                   name="<%=AccessControllerBean.ACTION_GROUP_LIST%>"
                   value="List Groups"
                   onClick="javascript:AccessControllerPortlet_listGroup_onClick()"/>
            &nbsp;&nbsp;<input type="button"
                   name="<%=AccessControllerBean.ACTION_GROUP_EDIT%>"
                   value="New Group"
                   onClick="javascript:AccessControllerPortlet_newGroup_onClick()"/>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td>
      <table bgcolor="BLACK" border="0" cellspacing="1" cellpadding="2" width="100%">
        <tr>
          <td width="200" bgcolor="#6666FF">
            <font color="WHITE">
              Name
            </font>
          </td>
          <td width="200" bgcolor="#6666FF">
            <font color="WHITE">
              Label
            </font>
          </td>
          <td width="*" bgcolor="#6666FF">
            <font color="WHITE">
              Description
            </font>
          </td>
        </tr>
<% List groupList = aclManagerBean.getGroupList();
   int numGroups = groupList.size();
   if (numGroups == 0) { %>
        <tr>
          <td bgcolor="WHITE" colspan="3">
            <font color="DARKRED">
              No group accounts in database.
            </font>
          </td>
        </tr>
<% } else {
     for (int ii = 0; ii < numGroups; ++ii) {
       PortletGroup group = (PortletGroup)groupList.get(ii);
       if (group.getID().equals(PortletGroup.BASE.getID()) ||
           group.getID().equals(PortletGroup.SUPER.getID())) {
           continue;
       } %>
        <tr>
          <td bgcolor="WHITE">
            <a href="javascript:AccessControllerPortlet_viewGroup_onClick('<%=group.getID()%>')">
              <%=group.getName()%>
            </a>
          </td>
          <td bgcolor="WHITE">
            <%=group.getName()%>
          </td>
          <td bgcolor="WHITE">
            <%=group.getName()%>
          </td>
        </tr>
<%   }
   } %>
      </table>
    </td>
  </tr>
</table>
</form>
