<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<gs:form action="doViewListUser">
<table class="portlet-pane" cellspacing="1">
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1">
        <tr>
          <td class="portlet-frame-actions">
            <gs:submit name="doViewListUser" value="List Users"/>
            &nbsp;&nbsp;<gs:submit name="doViewNewUser" value="New User"/>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td>
      <gs:table bean="userList"/>
    </td>
  </tr>
</table>
</gs:form>
