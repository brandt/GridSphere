<%@ page import="org.gridlab.gridsphere.services.grid.security.credential.CredentialMapping,
                 org.gridlab.gridsphere.portlets.grid.security.CredentialMappingAdminBean,
                 java.util.List,
                 org.gridlab.gridsphere.portlets.grid.security.CredentialMappingUserBean" %>
<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<jsp:useBean id="credentialMappingUserBean"
             class="org.gridlab.gridsphere.portlets.grid.security.CredentialMappingUserBean"
             scope="request"/>
<form name="CredentialMappingPortlet" method="POST"
      action="<%=credentialMappingUserBean.getPortletActionURI("doListUserCredentialMapping")%>">
  <input type="hidden" name="credentialMappingID" value=""/>
<script type="text/javascript">
    function CredentialMappingPortlet_listCredentialMapping_onClick() {
      document.CredentialMappingPortlet.action="<%=credentialMappingUserBean.getPortletActionURI("doListUserCredentialMapping")%>";
      document.CredentialMappingPortlet.submit();
    }

    function CredentialMappingPortlet_newCredentialMapping_onClick(credentialMappingID) {
      document.CredentialMappingPortlet.credentialMappingID.value="";
      document.CredentialMappingPortlet.action="<%=credentialMappingUserBean.getPortletActionURI("doEditUserCredentialMapping")%>";
      document.CredentialMappingPortlet.submit();
    }

    function CredentialMappingPortlet_viewCredentialMapping_onClick(credentialMappingID) {
      document.CredentialMappingPortlet.credentialMappingID.value=credentialMappingID;
      document.CredentialMappingPortlet.action="<%=credentialMappingUserBean.getPortletActionURI("doViewUserCredentialMapping")%>";
      document.CredentialMappingPortlet.submit();
    }
</script>
<table class="portlet-pane" cellspacing="1">
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-title">
              Your Credential Mappings
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-actions">
            <input type="button"
                   name="doListUserCredentialMapping"
                   value="List Mappings"
                   onClick="javascript:CredentialMappingPortlet_listCredentialMapping_onClick()"/>
            &nbsp;&nbsp;<input type="button"
                   name="doEditUserCredentialMapping"
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
<% List credentialMappingList = credentialMappingUserBean.getCredentialMappingList();
   int numCredentialMappings = credentialMappingList.size();
   if (numCredentialMappings == 0) { %>
        <tr>
          <td class="portlet-frame-text">
              There are no credentials mapped to your account.
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
        </tr>
<%   }
   } %>
      </table>
    </td>
  </tr>
</table>
</form>
