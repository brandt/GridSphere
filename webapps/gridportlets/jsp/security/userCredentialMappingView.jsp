<%@ page import="org.gridlab.gridsphere.services.grid.security.credential.CredentialMapping,
                 org.gridlab.gridsphere.portlets.grid.security.CredentialMappingUserBean,
                 java.util.List" %>
<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<jsp:useBean id="credentialMappingUserBean"
             class="org.gridlab.gridsphere.portlets.grid.security.CredentialMappingUserBean"
             scope="request"/>
<form name="CredentialMappingPortlet" method="POST"
      action="<%=credentialMappingUserBean.getPortletActionURI("doViewUserCredentialMapping")%>">
  <input type="hidden" name="credentialMappingID" value="<%=credentialMappingUserBean.getCredentialMappingID()%>"/>
  <script type="text/javascript">
    function CredentialMappingPortlet_listCredentialMapping_onClick() {
      document.CredentialMappingPortlet.action="<%=credentialMappingUserBean.getPortletActionURI("doListUserCredentialMapping")%>";
      document.CredentialMappingPortlet.submit();
    }

    function CredentialMappingPortlet_newCredentialMapping_onClick(credentialMappingID) {
      document.CredentialMappingPortlet.credentialMappingID.value="";
      document.CredentialMappingPortlet.action="<%=credentialMappingUserBean.getPortletActionURI("doViewUserCredentialMapping")%>";
      document.CredentialMappingPortlet.submit();
    }

    function CredentialMappingPortlet_editCredentialMapping_onClick() {
      document.CredentialMappingPortlet.action="<%=credentialMappingUserBean.getPortletActionURI("doEditUserCredentialMapping")%>";
      document.CredentialMappingPortlet.submit();
    }

    function CredentialMappingPortlet_deleteCredentialMapping_onClick() {
      document.CredentialMappingPortlet.action="<%=credentialMappingUserBean.getPortletActionURI("doDeleteUserCredentialMapping")%>";
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
                   name="doListUserCredentialMapping"
                   value="List Mappings"
                   onClick="javascript:CredentialMappingPortlet_listCredentialMapping_onClick()"/>
            &nbsp;&nbsp;<input type="button"
                   name="doEditUserCredentialMapping"
                   value="New Mapping"
                   onClick="javascript:CredentialMappingPortlet_newCredentialMapping_onClick()"/>
            &nbsp;&nbsp;<input type="button"
                   name="doEditUserCredentialMapping"
                   value="Edit Mapping"
                   onClick="javascript:CredentialMappingPortlet_editCredentialMapping_onClick()"/>
            &nbsp;&nbsp;<input type="button"
                   name="doDeleteUserCredentialMapping"
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
           <%=credentialMappingUserBean.getCredentialSubject()%>
         </td>
       </tr>
       <tr>
         <td class="portlet-frame-label">
           Credential Label
         </td>
         <td class="portlet-frame-text">
           <%=credentialMappingUserBean.getCredentialLabel()%>
         </td>
       </tr>
       <tr>
         <td class="portlet-frame-label">
           Credential Tag
         </td>
         <td class="portlet-frame-text">
           <%=credentialMappingUserBean.getCredentialTag()%>
         </td>
       </tr>
      </table>
    </td>
  </tr>
</table>
</form>
