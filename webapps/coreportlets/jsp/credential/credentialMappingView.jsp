<%@ page import="org.gridlab.gridsphere.services.grid.security.credential.CredentialMapping,
                 org.gridlab.gridsphere.portlets.grid.security.CredentialMappingAdminBean,
                 java.util.List" %>
<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<jsp:useBean id="credentialMappingBean"
             class="org.gridlab.gridsphere.portlets.grid.security.CredentialMappingAdminBean"
             scope="request"/>
<form name="CredentialMappingPortlet" method="POST"
      action="<%=credentialMappingBean.getPortletActionURI(CredentialMappingAdminBean.ACTION_CREDENTIAL_MAPPING_VIEW)%>">
  <input type="hidden" name="credentialMappingID" value="<%=credentialMappingBean.getCredentialMappingID()%>"/>
  <script type="text/javascript">
    function CredentialMappingPortlet_listCredentialMapping_onClick() {
      document.CredentialMappingPortlet.action="<%=credentialMappingBean.getPortletActionURI(CredentialMappingAdminBean.ACTION_CREDENTIAL_MAPPING_LIST)%>";
      document.CredentialMappingPortlet.submit();
    }

    function CredentialMappingPortlet_newCredentialMapping_onClick(credentialMappingID) {
      document.CredentialMappingPortlet.credentialMappingID.value="";
      document.CredentialMappingPortlet.action="<%=credentialMappingBean.getPortletActionURI(CredentialMappingAdminBean.ACTION_CREDENTIAL_MAPPING_EDIT)%>";
      document.CredentialMappingPortlet.submit();
    }

    function CredentialMappingPortlet_editCredentialMapping_onClick() {
      document.CredentialMappingPortlet.action="<%=credentialMappingBean.getPortletActionURI(CredentialMappingAdminBean.ACTION_CREDENTIAL_MAPPING_EDIT)%>";
      document.CredentialMappingPortlet.submit();
    }

    function CredentialMappingPortlet_deleteCredentialMapping_onClick() {
      document.CredentialMappingPortlet.action="<%=credentialMappingBean.getPortletActionURI(CredentialMappingAdminBean.ACTION_CREDENTIAL_MAPPING_DELETE)%>";
      document.CredentialMappingPortlet.submit();
    }
  </script>
<table class="portlet-pane" cellspacing="1">
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-title">
              List Credential Mappings
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-actions">
            <input type="button"
                   name="<%=CredentialMappingAdminBean.ACTION_CREDENTIAL_MAPPING_LIST%>"
                   value="List Mappings"
                   onClick="javascript:CredentialMappingPortlet_listCredentialMapping_onClick()"/>
            &nbsp;&nbsp;<input type="button"
                   name="<%=CredentialMappingAdminBean.ACTION_CREDENTIAL_MAPPING_EDIT%>"
                   value="New Mapping"
                   onClick="javascript:CredentialMappingPortlet_newCredentialMapping_onClick()"/>
            &nbsp;&nbsp;<input type="button"
                   name="<%=CredentialMappingAdminBean.ACTION_CREDENTIAL_MAPPING_EDIT%>"
                   value="Edit Mapping"
                   onClick="javascript:CredentialMappingPortlet_editCredentialMapping_onClick()"/>
            &nbsp;&nbsp;<input type="button"
                   name="<%=CredentialMappingAdminBean.ACTION_CREDENTIAL_MAPPING_DELETE%>"
                   value="Delete Mapping"
                   onClick="javascript:CredentialMappingPortlet_deleteCredentialMapping_onClick()"/>
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
           Credential Subject
         </td>
         <td class="portlet-frame-text" width="250">
           <%=credentialMappingBean.getCredentialSubject()%>
         </td>
       </tr>
       <tr>
         <td class="portlet-frame-label">
           Credential Label
         </td>
         <td class="portlet-frame-text">
           <%=credentialMappingBean.getCredentialLabel()%>
         </td>
       </tr>
       <tr>
         <td class="portlet-frame-label">
           Credential Tag
         </td>
         <td class="portlet-frame-text">
           <%=credentialMappingBean.getCredentialTag()%>
         </td>
       </tr>
       <tr>
         <td class="portlet-frame-label">
           User Name
         </td>
         <td class="portlet-frame-text">
           <%=credentialMappingBean.getCredentialUserName()%>
         </td>
       </tr>
       <tr>
         <td class="portlet-frame-label">
           Full Name
         </td>
         <td class="portlet-frame-text">
           <%=credentialMappingBean.getCredentialUserFullName()%>
         </td>
       </tr>
      </table>
    </td>
  </tr>
</table>
</form>
