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
            <gs:submit name="doViewListGroup" value="List Groups"/>
            &nbsp;&nbsp;<gs:submit name="doViewAddGroupEntry" value="Add Users"/>
            &nbsp;&nbsp;<gs:submit name="doViewRemoveGroupEntry" value="Remove Users"/>
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
