<%@ page import="org.gridlab.gridsphere.portlet.User,
                 org.gridlab.gridsphere.portlets.core.beans.UserManagerBean,
                 java.util.List" %>
<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<jsp:useBean id="userManagerBean"
             class="org.gridlab.gridsphere.portlets.core.beans.UserManagerBean"
             scope="request"/>
<gs:form action="doListUser">
  <input type="hidden" name="userID" value="<%=userManagerBean.getUserID()%>"/>
  </script>
<table class="portlet-pane" cellspacing="1">
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-title">
              Deleted User [<%=userManagerBean.getUserName()%>]
          </td>
        <tr>
          <td class="portlet-frame-message">
            The following user was deleted.
          </td>
        </tr>
      </table>
    </td>
  </tr>
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
             Full Name:&nbsp;
          </td>
          <td class="portlet-frame-text">
             <%=userManagerBean.getFullName()%>
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
      </table>
    </td>
  </tr>
</table>
</gs:form>
