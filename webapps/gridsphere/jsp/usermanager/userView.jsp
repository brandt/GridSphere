<%@ page import="org.gridlab.gridsphere.portlet.User,
                 org.gridlab.gridsphere.portlets.core.user.UserManagerBean,
                 java.util.List,
                 org.gridlab.gridsphere.portlet.PortletRole,
                 org.gridlab.gridsphere.portlet.PortletGroup,
                 java.util.Iterator" %>
<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<jsp:useBean id="userManagerBean"
             class="org.gridlab.gridsphere.portlets.core.user.UserManagerBean"
             scope="request"/>
<portletAPI:init/>
<% System.out.println(userManagerBean.getActionPerformedParameter("userID")); %>
<% String userID = (String)userManagerBean.getPortletRequestAttribute("userID"); %>
<gs:form action="doViewUser">
<gs:hiddenfield name="userID" value="<%=userID%>"/>
<table class="portlet-pane" cellspacing="1" width="100%">
  <tr>
    <td>
       <table class="portlet-frame" cellspacing="1" width="100%">
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
             <gs:text text="User Name:"/>
          </td>
          <td class="portlet-frame-text">
             <gs:text bean="userName"/>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             <gs:text text="Family Name:"/>
          </td>
          <td class="portlet-frame-text">
             <gs:text bean="familyName"/>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             <gs:text text="Given Name:"/>
          </td>
          <td class="portlet-frame-text">
             <gs:text bean="givenName"/>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             <gs:text text="Email Address:"/>
          </td>
          <td class="portlet-frame-text">
             <gs:text bean="emailAddress"/>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             <gs:text text="Organization:"/>
          </td>
          <td class="portlet-frame-text">
             <gs:text bean="organization"/>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
            Base Role:
          </td>
          <td class="portlet-frame-text">
             <gs:text bean="userRole"/>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</gs:form>
