<%@ page import="org.gridlab.gridsphere.services.grid.security.credential.CredentialPermission,
                 org.gridlab.gridsphere.portlets.grid.security.CredentialPermissionAdminBean,
                 java.util.List" %>
<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<jsp:useBean id="credentialPermissionAdminBean"
             class="org.gridlab.gridsphere.portlets.grid.security.CredentialPermissionAdminBean"
             scope="request"/>
<form name="CredentialPermissionPortlet" method="POST"
      action="<%=credentialPermissionAdminBean.getPortletActionURI(CredentialPermissionAdminBean.ACTION_CREDENTIAL_PERMISSION_VIEW)%>">
  <input type="hidden" name="credentialPermissionID" value="<%=credentialPermissionAdminBean.getCredentialPermissionID()%>"/>
  <script type="text/javascript">
    function CredentialPermissionPortlet_listCredentialPermission_onClick() {
      document.CredentialPermissionPortlet.action="<%=credentialPermissionAdminBean.getPortletActionURI(CredentialPermissionAdminBean.ACTION_CREDENTIAL_PERMISSION_LIST)%>";
      document.CredentialPermissionPortlet.submit();
    }

    function CredentialPermissionPortlet_newCredentialPermission_onClick(credentialPermissionID) {
      document.CredentialPermissionPortlet.credentialPermissionID.value="";
      document.CredentialPermissionPortlet.action="<%=credentialPermissionAdminBean.getPortletActionURI(CredentialPermissionAdminBean.ACTION_CREDENTIAL_PERMISSION_EDIT)%>";
      document.CredentialPermissionPortlet.submit();
    }
  </script>
<table class="portlet-pane" cellspacing="1">
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-message">
            This permission was <span style="portlet-text-bold">deleted</span>.
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-title">
              Deleted Credential Permission [<%=credentialPermissionAdminBean.getPermittedSubjects()%>]
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-actions">
            <input type="button"
                   name="<%=CredentialPermissionAdminBean.ACTION_CREDENTIAL_PERMISSION_LIST%>"
                   value="List Permissions"
                   onClick="javascript:CredentialPermissionPortlet_listCredentialPermission_onClick()"/>
            &nbsp;&nbsp;<input type="button"
                   name="<%=CredentialPermissionAdminBean.ACTION_CREDENTIAL_PERMISSION_DELETE_CANCEL%>"
                   value="New Permission"
                   onClick="javascript:CredentialPermissionPortlet_newCredentialPermission_onClick()"/>
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
           Permitted Subjects
         </td>
         <td class="portlet-frame-text" width="250">
           <%=credentialPermissionAdminBean.getPermittedSubjects()%>
         </td>
       </tr>
       <tr>
         <td class="portlet-frame-label">
           Description
         </td>
         <td class="portlet-frame-text">
           <%=credentialPermissionAdminBean.getDescription()%>
         </td>
       </tr>
      </table>
    </td>
  </tr>
</table>
</form>
