<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<gs:form action="doViewListGroup">
<table class="portlet-pane" cellspacing="1">
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1">
        <tr>
          <td class="portlet-frame-actions">
            <gs:submit name="doViewListGroup" value="List Groups"/>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td>
      <gs:table bean="groupList"/>
    </td>
  </tr>
</table>
</gs:form>
