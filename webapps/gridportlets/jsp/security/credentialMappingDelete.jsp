<%@ page import="org.gridlab.gridsphere.services.grid.security.credential.CredentialMapping,
                 org.gridlab.gridsphere.portlets.grid.security.CredentialMappingAdminBean,
                 java.util.List" %>
<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<jsp:useBean id="credentialMappingAdminBean"
             class="org.gridlab.gridsphere.portlets.grid.security.CredentialMappingAdminBean"
             scope="request"/>
<form name="CredentialMappingPortlet" method="POST"
      action="<%=credentialMappingAdminBean.getPortletActionURI(CredentialMappingAdminBean.ACTION_CREDENTIAL_MAPPING_VIEW)%>">
  <input type="hidden" name="credentialMappingID" value="<%=credentialMappingAdminBean.getCredentialMappingID()%>"/>
  <script type="text/javascript">
    function CredentialMappingPortlet_confirmDeleteCredentialMapping_onClick() {
      document.CredentialMappingPortlet.action="<%=credentialMappingAdminBean.getPortletActionURI(CredentialMappingAdminBean.ACTION_CREDENTIAL_MAPPING_DELETE_CONFIRM)%>";
      document.CredentialMappingPortlet.submit();
    }

    function CredentialMappingPortlet_cancelDeleteCredentialMapping_onClick() {
      document.CredentialMappingPortlet.action="<%=credentialMappingAdminBean.getPortletActionURI(CredentialMappingAdminBean.ACTION_CREDENTIAL_MAPPING_DELETE_CANCEL)%>";
      document.CredentialMappingPortlet.submit();
    }
  </script>
<table class="portlet-pane" cellspacing="1">
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-actions">
            <input type="button"
                   name="<%=CredentialMappingAdminBean.ACTION_CREDENTIAL_MAPPING_DELETE_CONFIRM%>"
                   value="Confirm Delete"
                   onClick="javascript:CredentialMappingPortlet_confirmDeleteCredentialMapping_onClick()"/>
            &nbsp;&nbsp;<input type="button"
                   name="<%=CredentialMappingAdminBean.ACTION_CREDENTIAL_MAPPING_DELETE_CANCEL%>"
                   value="Cancel Delete"
                   onClick="javascript:CredentialMappingPortlet_cancelDeleteCredentialMapping_onClick()"/>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-message">
            Click <span style="portlet-text-alert">Confirm Delete</span> to delete this permission,
            <span style="portlet-text-alert">Cancel Delete</span> otherwise.
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
       <tr>
         <td class="portlet-frame-label">
           User Name
         </td>
         <td class="portlet-frame-text">
           <%=credentialMappingAdminBean.getCredentialUserName()%>
         </td>
       </tr>
       <tr>
         <td class="portlet-frame-label">
           Full Name
         </td>
         <td class="portlet-frame-text">
           <%=credentialMappingAdminBean.getCredentialUserFullName()%>
         </td>
       </tr>
       <tr>
         <td class="portlet-frame-label" width="200">
           Credential Subject
         </td>
         <td class="portlet-frame-text" width="250">
           <%=credentialMappingAdminBean.getCredentialSubject()%>
         </td>
       </tr>
       <tr>
         <td class="portlet-frame-label">
           Credential Label
         </td>
         <td class="portlet-frame-text">
           <%=credentialMappingAdminBean.getCredentialLabel()%>
         </td>
       </tr>
       <tr>
         <td class="portlet-frame-label">
           Credential Tag
         </td>
         <td class="portlet-frame-text">
           <%=credentialMappingAdminBean.getCredentialTag()%>
         </td>
       </tr>
      </table>
    </td>
  </tr>
</table>
</form>
