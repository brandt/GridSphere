<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<gs:form action="doDeleteUser">
<gs:hiddenfield bean="userID"/>
<table class="portlet-pane" cellspacing="1" width="100%">
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-actions">
            <gs:submit name="doConfirmDeleteUser" value="Confirm Delete"/>
            &nbsp;&nbsp;<gs:submit name="doCancelDeleteUser" value="Cancel Delete"/>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-message-alert">
            Click "<span style="portlet-text-alert">Confirm Delete</span>" to delete this user,
            "<span style="portlet-text-alert">Cancel Delete</span>" otherwise.
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
             <gs:text text="Full Name:"/>
          </td>
          <td class="portlet-frame-text">
             <gs:text bean="fullName"/>
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
             <gs:text text="Role In GridSphere:"/>
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