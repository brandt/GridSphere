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
      action="<%=credentialPermissionBean.getPortletActionURI(CredentialPermissionBean.ACTION_CREDENTIAL_PERMISSION_EDIT)%>">
  <input type="hidden" name="credentialPermissionID" value="<%=credentialPermissionBean.getCredentialPermissionID()%>"/>
  <script type="text/javascript">
    function CredentialPermissionPortlet_editConfirmCredentialPermission_onClick() {
      document.CredentialPermissionPortlet.action="<%=credentialPermissionBean.getPortletActionURI(CredentialPermissionBean.ACTION_CREDENTIAL_PERMISSION_EDIT_CONFIRM)%>";
      document.CredentialPermissionPortlet.submit();
    }

    function CredentialPermissionPortlet_editCancelCredentialPermission_onClick() {
      document.CredentialPermissionPortlet.action="<%=credentialPermissionBean.getPortletActionURI(CredentialPermissionBean.ACTION_CREDENTIAL_PERMISSION_EDIT_CANCEL)%>";
      document.CredentialPermissionPortlet.submit();
    }
  </script>
<table class="portlet-pane" cellspacing="1">
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-title">
              Edit Credential Permission [<%=credentialPermissionBean.getPermittedSubjects()%>]
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-actions">
            <input type="button"
                   name="<%=CredentialPermissionBean.ACTION_CREDENTIAL_PERMISSION_EDIT_CONFIRM%>"
                   value="Save Permission"
                   onClick="javascript:CredentialPermissionPortlet_editConfirmCredentialPermission_onClick()"/>
            &nbsp;&nbsp;<input type="button"
                   name="<%=CredentialPermissionBean.ACTION_CREDENTIAL_PERMISSION_EDIT_CANCEL%>"
                   value="Cancel Edit"
                   onClick="javascript:CredentialPermissionPortlet_editCancelCredentialPermission_onClick()"/>
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
         <td class="portlet-frame-input" width="250">
             <input type="text"
                    name="permittedSubjects"
                    value="<%=credentialPermissionBean.getPermittedSubjects()%>"/>
         </td>
       </tr>
       <tr>
         <td class="portlet-frame-label">
           Description
         </td>
         <td class="portlet-frame-input">
             <input type="text"
                    name="description"
                    value="<%=credentialPermissionBean.getDescription()%>"/>
         </td>
       </tr>
      </table>
    </td>
  </tr>
</table>
</form>
