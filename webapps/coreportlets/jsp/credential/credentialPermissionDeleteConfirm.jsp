<%@ page import="org.gridlab.gridsphere.services.security.credential.CredentialPermission,
                 org.gridlab.gridsphere.portlets.core.beans.CredentialPermissionBean,
                 java.util.List" %>
<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<jsp:useBean id="credentialPermissionBean"
             class="org.gridlab.gridsphere.portlets.core.beans.CredentialPermissionBean"
             scope="request"/>
<form name="CredentialPermissionPortlet" method="POST"
      action="<%=credentialPermissionBean.getPortletActionURI(CredentialPermissionBean.ACTION_CREDENTIAL_PERMISSION_VIEW)%>">
  <input type="hidden" name="credentialPermissionID" value="<%=credentialPermissionBean.getCredentialPermissionID()%>"/>
  <script type="text/javascript">
    function CredentialPermissionPortlet_listCredentialPermission_onClick() {
      document.CredentialPermissionPortlet.action="<%=credentialPermissionBean.getPortletActionURI(CredentialPermissionBean.ACTION_CREDENTIAL_PERMISSION_LIST)%>";
      document.CredentialPermissionPortlet.submit();
    }

    function CredentialPermissionPortlet_newCredentialPermission_onClick(credentialPermissionID) {
      document.CredentialPermissionPortlet.credentialPermissionID.value="";
      document.CredentialPermissionPortlet.action="<%=credentialPermissionBean.getPortletActionURI(CredentialPermissionBean.ACTION_CREDENTIAL_PERMISSION_EDIT)%>";
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
              Deleted Credential Permission [<%=credentialPermissionBean.getPermittedSubjects()%>]
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-actions">
            <input type="button"
                   name="<%=CredentialPermissionBean.ACTION_CREDENTIAL_PERMISSION_LIST%>"
                   value="List Permissions"
                   onClick="javascript:CredentialPermissionPortlet_listCredentialPermission_onClick()"/>
            &nbsp;&nbsp;<input type="button"
                   name="<%=CredentialPermissionBean.ACTION_CREDENTIAL_PERMISSION_DELETE_CANCEL%>"
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
           <%=credentialPermissionBean.getPermittedSubjects()%>
         </td>
       </tr>
       <tr>
         <td class="portlet-frame-label">
           Description
         </td>
         <td class="portlet-frame-text">
           <%=credentialPermissionBean.getDescription()%>
         </td>
       </tr>
      </table>
    </td>
  </tr>
</table>
</form>
