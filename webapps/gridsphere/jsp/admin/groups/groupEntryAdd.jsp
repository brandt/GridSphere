<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<gs:form action="doViewAddGroupEntry">
<gs:hiddenfield bean="groupID"/>
<table class="portlet-pane" cellspacing="1" width="100%">
  <tr>
    <td>
       <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-actions">
            <gs:submit name="doViewConfirmAddGroupEntry" value="Add User"/>
            &nbsp;&nbsp;<gs:submit name="doViewCancelAddGroupEntry" value="Cancel Add"/>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-message">
            <span style="portlet-text-alert"><gs:text bean="errorMessage"/></span>
            Select the user you would like to add and their role in <gs:text bean="groupLabel"/>.
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-label">
            Portlet Group:<br>
          </td>
          <td class="portlet-frame-text">
            <gs:text bean="groupLabel"/>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
            User To Add:<br>
          </td>
          <td class="portlet-frame-text">
            <gs:listbox bean="usersNotInGroupList"/>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
            Role In Group:<br>
          </td>
          <td class="portlet-frame-text">
            <gs:listbox bean="groupEntryRole"/>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</gs:form>
