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
      action="<%=credentialMappingUserBean.getPortletActionURI("doDeleteUserCredentialMapping")%>">
  <input type="hidden" name="credentialMappingID" value="<%=credentialMappingUserBean.getCredentialMappingID()%>"/>
  <script type="text/javascript">
    function CredentialMappingPortlet_confirmDeleteCredentialMapping_onClick() {
      document.CredentialMappingPortlet.action="<%=credentialMappingUserBean.getPortletActionURI("doConfirmDeleteUserCredentialMapping")%>";
      document.CredentialMappingPortlet.submit();
    }

    function CredentialMappingPortlet_cancelDeleteCredentialMapping_onClick() {
      document.CredentialMappingPortlet.action="<%=credentialMappingUserBean.getPortletActionURI("doCancelDeleteUserCredentialMapping")%>";
      document.CredentialMappingPortlet.submit();
    }
  </script>
<table class="portlet-pane" cellspacing="1">
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
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
          <td class="portlet-frame-title">
              Delete Credential Mapping [<%=credentialMappingUserBean.getCredentialSubject()%>]
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-actions">
            <input type="button"
                   name="doConfirmDeleteUserCredentialMapping"
                   value="Confirm Delete"
                   onClick="javascript:CredentialMappingPortlet_confirmDeleteCredentialMapping_onClick()"/>
            &nbsp;&nbsp;<input type="button"
                   name="doCancelDeleteUserCredentialMapping"
                   value="Cancel Delete"
                   onClick="javascript:CredentialMappingPortlet_cancelDeleteCredentialMapping_onClick()"/>
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
