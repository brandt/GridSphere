<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<jsp:useBean id="userManagerBean"
             class="org.gridlab.gridsphere.portlets.core.user.UserManagerBean"
             scope="request"/>
<gs:form action="doConfigureEditUser">
<gs:hiddenfield bean="userID"/>
<table class="portlet-pane" cellspacing="1" width="100%">
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-actions">
            <gs:submit name="doConfigureConfirmEditUser" value="Save User"/>
            &nbsp;&nbsp;<gs:submit name="doConfigureCancelEditUser" value="Cancel Edit"/>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-message-alert">
            <gs:text bean="errorMessage"/>
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
             User Name:
          </td>
          <td class="portlet-frame-text">
             <gs:textfield bean="userName"/>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             Family Name:
          </td>
          <td class="portlet-frame-text">
             <gs:textfield bean="familyName"/>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             Given Name:
          </td>
          <td class="portlet-frame-text">
             <gs:textfield bean="givenName"/>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             Full Name:
          </td>
          <td class="portlet-frame-text">
             <gs:textfield bean="fullName"/>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             Email Address:
          </td>
          <td class="portlet-frame-text">
             <gs:textfield bean="emailAddress"/>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             Organization:
          </td>
          <td class="portlet-frame-text">
             <gs:textfield bean="organization"/>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             Role In GridSphere:
          </td>
          <td class="portlet-frame-input">
             <gs:dropdownlist bean="userRole"/>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             New Password:
          </td>
          <td class="portlet-frame-input">
             <gs:password bean="password"/>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             Confirm Password:
          </td>
          <td class="portlet-frame-input">
             <gs:password bean="confirmPassword"/>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</gs:form>
