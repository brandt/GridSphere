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
            <gs:submit name="doViewViewGroup" value="Back to Group"/>
            &nbsp;&nbsp;<gs:submit name="doViewListGroup" value="List Groups"/>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-message-alert">
            The following users were added to [<gs:text bean="groupLabel"/>].
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
