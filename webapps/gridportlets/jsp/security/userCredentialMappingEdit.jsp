<%@ page import="org.gridlab.gridsphere.services.grid.security.credential.CredentialMapping,
                 org.gridlab.gridsphere.portlets.grid.security.CredentialMappingUserBean,
                 java.util.List,
                 java.util.Iterator,
                 org.gridlab.gridsphere.portlet.User" %>
<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<jsp:useBean id="credentialMappingUserBean"
             class="org.gridlab.gridsphere.portlets.grid.security.CredentialMappingUserBean"
             scope="request"/>
<form name="CredentialMappingPortlet" method="POST"
      action="<%=credentialMappingUserBean.getPortletActionURI("doEditUserCredentialMapping")%>">
  <input type="hidden" name="credentialMappingID" value="<%=credentialMappingUserBean.getCredentialMappingID()%>"/>
  <script type="text/javascript">
    function CredentialMappingPortlet_editConfirmCredentialMapping_onClick() {
      document.CredentialMappingPortlet.action="<%=credentialMappingUserBean.getPortletActionURI("doConfirmEditUserCredentialMapping")%>";
      document.CredentialMappingPortlet.submit();
    }

    function CredentialMappingPortlet_editCancelCredentialMapping_onClick() {
      document.CredentialMappingPortlet.action="<%=credentialMappingUserBean.getPortletActionURI("doCancelEditUserCredentialMapping")%>";
      document.CredentialMappingPortlet.submit();
    }
  </script>
<table class="portlet-pane" cellspacing="1">
<% if (credentialMappingUserBean.isFormInvalid()) { %>
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-message-alert">
            <%=credentialMappingUserBean.getFormInvalidMessage()%>
          </td>
        </tr>
      </table>
    </td>
  </tr>
<% } %>
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-title">
<% if (credentialMappingUserBean.isNewCredentialMapping()) { %>
              New Credential Mapping
<% } else { %>
              Edit Credential Mapping [<%=credentialMappingUserBean.getCredentialSubject()%>]
<% } %>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-actions">
            <input type="button"
                   name="doConfirmEditUserCredentialMapping"
                   value="Save Mapping"
                   onClick="javascript:CredentialMappingPortlet_editConfirmCredentialMapping_onClick()"/>
            &nbsp;&nbsp;<input type="button"
                   name="doCancelEditUserCredentialMapping"
                   value="Cancel Edit"
                   onClick="javascript:CredentialMappingPortlet_editCancelCredentialMapping_onClick()"/>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
<% if (credentialMappingUserBean.isNewCredentialMapping()) { %>
       <tr>
         <td class="portlet-frame-label" width="200">
           Credential Subject
         </td>
         <td class="portlet-frame-input" width="250">
             <input type="text"
                    name="credentialSubject"
                    value="<%=credentialMappingUserBean.getCredentialSubject()%>"/>
         </td>
       </tr>
<% } else { %>
       <tr>
         <td class="portlet-frame-label" width="200">
           Credential Subject
         </td>
         <td class="portlet-frame-text" width="250">
           <%=credentialMappingUserBean.getCredentialSubject()%>
         </td>
       </tr>
<% } %>
       <tr>
         <td class="portlet-frame-label">
           Credential Label
         </td>
         <td class="portlet-frame-input" width="250">
             <input type="text"
                    name="credentialLabel"
                    value="<%=credentialMappingUserBean.getCredentialLabel()%>"/>
         </td>
       </tr>
       <tr>
         <td class="portlet-frame-label">
           Credential Tag
         </td>
         <td class="portlet-frame-input" width="250">
             <input type="text"
                    name="credentialTag"
                    value="<%=credentialMappingUserBean.getCredentialTag()%>"/>
         </td>
       </tr>
      </table>
    </td>
  </tr>
</table>
</form>
