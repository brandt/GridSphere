<%@ page import="org.gridlab.gridsphere.services.grid.security.credential.CredentialMapping,
                 org.gridlab.gridsphere.portlets.grid.security.CredentialMappingAdminBean,
                 java.util.List,
                 java.util.Iterator,
                 org.gridlab.gridsphere.portlet.User" %>
<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<jsp:useBean id="credentialMappingAdminBean"
             class="org.gridlab.gridsphere.portlets.grid.security.CredentialMappingAdminBean"
             scope="request"/>
<form name="CredentialMappingPortlet" method="POST"
      action="<%=credentialMappingAdminBean.getPortletActionURI(CredentialMappingAdminBean.ACTION_CREDENTIAL_MAPPING_EDIT)%>">
  <input type="hidden" name="credentialMappingID" value="<%=credentialMappingAdminBean.getCredentialMappingID()%>"/>
  <script type="text/javascript">
    function CredentialMappingPortlet_editConfirmCredentialMapping_onClick() {
      document.CredentialMappingPortlet.action="<%=credentialMappingAdminBean.getPortletActionURI(CredentialMappingAdminBean.ACTION_CREDENTIAL_MAPPING_EDIT_CONFIRM)%>";
      document.CredentialMappingPortlet.submit();
    }

    function CredentialMappingPortlet_editCancelCredentialMapping_onClick() {
      document.CredentialMappingPortlet.action="<%=credentialMappingAdminBean.getPortletActionURI(CredentialMappingAdminBean.ACTION_CREDENTIAL_MAPPING_EDIT_CANCEL)%>";
      document.CredentialMappingPortlet.submit();
    }
  </script>
<table class="portlet-pane" cellspacing="1">
<% if (credentialMappingAdminBean.isFormInvalid()) { %>
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-message-alert">
            <%=credentialMappingAdminBean.getFormInvalidMessage()%>
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
<% if (credentialMappingAdminBean.isNewCredentialMapping()) { %>
              New Credential Mapping
<% } else { %>
              Edit Credential Mapping [<%=credentialMappingAdminBean.getCredentialSubject()%>]
<% } %>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-actions">
            <input type="button"
                   name="<%=CredentialMappingAdminBean.ACTION_CREDENTIAL_MAPPING_EDIT_CONFIRM%>"
                   value="Save Mapping"
                   onClick="javascript:CredentialMappingPortlet_editConfirmCredentialMapping_onClick()"/>
            &nbsp;&nbsp;<input type="button"
                   name="<%=CredentialMappingAdminBean.ACTION_CREDENTIAL_MAPPING_EDIT_CANCEL%>"
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
<% if (credentialMappingAdminBean.isNewCredentialMapping()) { %>
       <tr>
         <td class="portlet-frame-label" width="200">
           Credential User
         </td>
         <td class="portlet-frame-input" width="250">
           <select name="credentialUserID" size="1">
<%     List userList = credentialMappingAdminBean.getUserList();
       Iterator userIterator = userList.iterator();
       while (userIterator.hasNext()) {
           User user = (User)userIterator.next(); %>
            <option value="<%=user.getID()%>"><%=user.getUserName()%>&nbsp;[<%=user.getFullName()%></option>
<%     } %>
           </select>
         </td>
       </tr>
       <tr>
         <td class="portlet-frame-label" width="200">
           Credential Subject
         </td>
         <td class="portlet-frame-input" width="250">
             <input type="text"
                    name="credentialSubject"
                    value="<%=credentialMappingAdminBean.getCredentialSubject()%>"/>
         </td>
       </tr>
<% } else { %>
       <tr>
         <td class="portlet-frame-label" width="200">
           Credential User
         </td>
         <td class="portlet-frame-text" width="250">
           <%=credentialMappingAdminBean.getCredentialUserName()%>
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
<% } %>
       <tr>
         <td class="portlet-frame-label">
           Credential Label
         </td>
         <td class="portlet-frame-input" width="250">
             <input type="text"
                    name="credentialLabel"
                    value="<%=credentialMappingAdminBean.getCredentialLabel()%>"/>
         </td>
       </tr>
       <tr>
         <td class="portlet-frame-label">
           Credential Tag
         </td>
         <td class="portlet-frame-input" width="250">
             <input type="text"
                    name="credentialTag"
                    value="<%=credentialMappingAdminBean.getCredentialTag()%>"/>
         </td>
       </tr>
      </table>
    </td>
  </tr>
</table>
</form>
