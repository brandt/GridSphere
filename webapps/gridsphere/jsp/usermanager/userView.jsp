<%@ page import="org.gridlab.gridsphere.portlet.User,
                 org.gridlab.gridsphere.portlets.core.beans.UserManagerBean,
                 java.util.List,
                 org.gridlab.gridsphere.portlet.PortletRole,
                 org.gridlab.gridsphere.portlet.PortletGroup,
                 java.util.Iterator" %>
<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<jsp:useBean id="userManagerBean"
             class="org.gridlab.gridsphere.portlets.core.beans.UserManagerBean"
             scope="request"/>
<gs:form action="doViewUser">
  <input type="hidden" name="userID" value="<%=userManagerBean.getUserID()%>"/>
<table class="portlet-pane" cellspacing="1">
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-title">
              View User [<%=userManagerBean.getUserName()%>]
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-actions">
            <gs:submit name="doListUser" value="List Users"/>
            &nbsp;&nbsp;<gs:submit name="doNewUser" value="New User"/>
            &nbsp;&nbsp;<gs:submit name="doEditUser" value="Edit User"/>
            &nbsp;&nbsp;<gs:submit name="doDeleteUser" value="Delete User"/>
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
             User Name:&nbsp;
          </td>
          <td class="portlet-frame-text">
             <%=userManagerBean.getUserName()%>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             Family Name:&nbsp;
          </td>
          <td class="portlet-frame-text">
             <%=userManagerBean.getFamilyName()%>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             Given Name:&nbsp;
          </td>
          <td class="portlet-frame-text">
             <%=userManagerBean.getGivenName()%>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             Email Address:&nbsp;
          </td>
          <td class="portlet-frame-text">
             <%=userManagerBean.getEmailAddress()%>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             Organization:&nbsp;
          </td>
          <td class="portlet-frame-text">
             <%=userManagerBean.getOrganization()%>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
            Base Role:&nbsp;
          </td>
          <td class="portlet-frame-text">
             <%=userManagerBean.getRoleInBaseGroup()%>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</gs:form>
