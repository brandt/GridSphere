<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<gs:form action="doViewViewUser">
<gs:hiddenfield bean="userID"/>
<table class="portlet-pane" cellspacing="1" width="100%">
  <tr>
    <td>
       <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-actions">
            <gs:submit name="doViewListUser" value="List Users"/>
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
             <gs:text bean="userName"/>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             Full Name:
          </td>
          <td class="portlet-frame-text">
             <gs:text bean="fullName"/>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             Email Address:
          </td>
          <td class="portlet-frame-text">
             <gs:text bean="emailAddress"/>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             Organization:
          </td>
          <td class="portlet-frame-text">
             <gs:text bean="organization"/>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</gs:form>
