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
  <script type="text/javascript">

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
<table class="portlet-pane" cellspacing="1">
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-actions">
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
      <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-header" width="150">
              Name
          </td>
          <td class="portlet-frame-header" width="200">
              Label
          </td>
          <td class="portlet-frame-header" width="250" >
              Description
          </td>
        </tr>
<% List groupList = aclManagerBean.getGroupList();
   int numGroups = groupList.size();
   if (numGroups == 0) { %>
        <tr>
          <td id="portlet-frame-text" colspan="3">
            No groups have been defined yet.
          </td>
        </tr>
<% } else {
     for (int ii = 0; ii < numGroups; ++ii) {
       PortletGroup group = (PortletGroup)groupList.get(ii); %>
        <tr>
          <td class="portlet-frame-text">
              <a href="javascript:AccessControllerPortlet_viewGroup_onClick('<%=group.getID()%>')">
                <%=group.getName()%>
              </a>
          </td>
          <td class="portlet-frame-text">
               <%=group.getName()%>
          </td>
          <td class="portlet-frame-text">
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
