<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<gs:form action="doViewViewGroup">
<gs:hiddenfield bean="groupID"/>
<gs:hiddenfield bean="groupEntryID"/>
<table class="portlet-pane" cellspacing="1" width="100%">
  <tr>
    <td>
       <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-actions">
            <gs:submit name="doViewViewGroup" value="Back To Group"/>
            <gs:submit name="doViewEditGroupEntry" value="Change User Role"/>
            <gs:submit name="doViewDeleteGroupEntry" value="Remove User From Group"/>
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
             Group Name:
          </td>
          <td class="portlet-frame-text">
             <gs:text bean="groupName"/>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             Group Label:
          </td>
          <td class="portlet-frame-text">
             <gs:text bean="groupLabel"/>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             User Name:
          </td>
          <td class="portlet-frame-text">
             <gs:text bean="groupEntryUserName"/>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             Full Name:
          </td>
          <td class="portlet-frame-text">
             <gs:text bean="groupEntryUserFullName"/>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             Role In Group:
          </td>
          <td class="portlet-frame-text">
             <gs:text bean="groupEntryRole"/>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</gs:form>
