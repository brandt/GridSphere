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
      action="<%=credentialMappingAdminBean.getPortletActionURI(CredentialMappingAdminBean.ACTION_CREDENTIAL_MAPPING_LIST)%>">
  <input type="hidden" name="credentialMappingID" value=""/>
  <script type="text/javascript">
    function CredentialMappingPortlet_listCredentialMapping_onClick() {
      document.CredentialMappingPortlet.action="<%=credentialMappingAdminBean.getPortletActionURI(CredentialMappingAdminBean.ACTION_CREDENTIAL_MAPPING_LIST)%>";
      document.CredentialMappingPortlet.submit();
    }

    function CredentialMappingPortlet_newCredentialMapping_onClick(credentialMappingID) {
      document.CredentialMappingPortlet.credentialMappingID.value="";
      document.CredentialMappingPortlet.action="<%=credentialMappingAdminBean.getPortletActionURI(CredentialMappingAdminBean.ACTION_CREDENTIAL_MAPPING_EDIT)%>";
      document.CredentialMappingPortlet.submit();
    }

    function CredentialMappingPortlet_viewCredentialMapping_onClick(credentialMappingID) {
      document.CredentialMappingPortlet.credentialMappingID.value=credentialMappingID;
      document.CredentialMappingPortlet.action="<%=credentialMappingAdminBean.getPortletActionURI(CredentialMappingAdminBean.ACTION_CREDENTIAL_MAPPING_VIEW)%>";
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
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="500">
<% List credentialMappingList = credentialMappingAdminBean.getCredentialMappingList();
   int numCredentialMappings = credentialMappingList.size();
   if (numCredentialMappings == 0) { %>
        <tr>
          <td class="portlet-frame-text-alert">
              No credentials have been mapped to any user.
          </td>
        </tr>
<% } else { %>
       <tr>
         <td class="portlet-frame-header" width="100">
           Credential Subject
         </td>
         <td class="portlet-frame-header" width="100">
           Credential Label
         </td>
         <td class="portlet-frame-header" width="100">
           Credential Tag
         </td>
         <td class="portlet-frame-header" width="100">
           User Name
         </td>
         <td class="portlet-frame-header" width="100">
           Full Name
         </td>
       </tr>
<%   for (int ii = 0; ii < numCredentialMappings; ++ii) {
       CredentialMapping credentialMapping = (CredentialMapping)credentialMappingList.get(ii); %>
        <tr>
          <td class="portlet-frame-text">
            <a href="javascript:CredentialMappingPortlet_viewCredentialMapping_onClick('<%=credentialMapping.getSubject()%>')">
              <%=credentialMapping.getSubject()%>
            </a>
          </td>
          <td class="portlet-frame-text">
            <%=credentialMapping.getLabel()%>
          </td>
          <td class="portlet-frame-text">
            <%=credentialMapping.getTag()%>
          </td>
          <td class="portlet-frame-text">
            <%=credentialMapping.getUser().getUserName()%>
          </td>
          <td class="portlet-frame-text">
            <%=credentialMapping.getUser().getFullName()%>
          </td>
        </tr>
<%   }
   } %>
      </table>
    </td>
  </tr>
</table>
</form>
