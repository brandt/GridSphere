<%@ page import="org.gridlab.gridsphere.portlet.User,
                 org.gridlab.gridsphere.portlets.core.user.UserManagerBean,
                 java.util.List,
                 org.gridlab.gridsphere.portlet.PortletURI" %>
<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<jsp:useBean id="userManagerBean"
             class="org.gridlab.gridsphere.portlets.core.user.UserManagerBean"
             scope="request"/>
<gs:form action="doListUser">
<table class="portlet-pane" cellspacing="1" width="100%">
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-actions">
            <gs:submit name="doListUser" value="List Users"/>
            &nbsp;&nbsp;<gs:submit name="doNewUser" value="New User"/>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
<% List userList = userManagerBean.getUserList();
   int numUsers = userList.size();
   if (numUsers == 0) { %>
        <tr>
          <td class="portlet-frame-text">
            There are no user accounts in the database.
          </td>
        </tr>
<% } else { %>
       <tr>
         <td class="portlet-frame-header" width="100">
             User Name
         </td>
         <td class="portlet-frame-header" width="150">
             Full Name
         </td>
         <td class="portlet-frame-header" width="200">
             Email Address
         </td>
         <td class="portlet-frame-header" width="150">
             Organization
         </td>
       </tr>
<%   for (int ii = 0; ii < numUsers; ++ii) {
       User user = (User)userList.get(ii);
       String userID = user.getID();
       String userName = user.getUserName();%>
        <tr>
          <td class="portlet-frame-text">
            <gs:actionlink action="doViewUser" label="<%=userName%>">
              <gs:actionparam name="userID" value="<%=userID%>"/>
            </gs:actionlink>
          </td>
          <td class="portlet-frame-text">
            <gs:label text="<%=user.getFullName()%>"/>
          </td>
          <td class="portlet-frame-text">
            <gs:label text="<%=user.getEmailAddress()%>"/>
          </td>
          <td class="portlet-frame-text">
            <gs:label text="<%=user.getOrganization()%>"/>
          </td>
        </tr>
<%   }
   } %>
      </table>
    </td>
  </tr>
</table>
</gs:form>
