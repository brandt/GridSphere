<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<gs:form action="doViewViewGroup">
<gs:hiddenfield bean="groupID"/>
<table class="portlet-pane" cellspacing="1" width="100%">
  <tr>
    <td>
       <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-actions">
            <gs:submit name="doViewConfirmRemoveGroupEntry" value="Remove Users"/>
            &nbsp;&nbsp;<gs:submit name="doViewCancelRemoveGroupEntry" value="Cancel Remove"/>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-message">
            Select the users you would like to remove from [<gs:text bean="groupLabel"/>]
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td>
      <gs:table bean="groupEntryList"/>
    </td>
  </tr>
</table>
</gs:form>
