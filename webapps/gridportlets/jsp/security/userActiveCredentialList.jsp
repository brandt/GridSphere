<%@ page import="org.gridlab.gridsphere.services.grid.security.credential.Credential,
                 org.gridlab.gridsphere.portlets.grid.security.CredentialRetrievalUserBean,
                 java.util.List,
                 org.gridlab.gridsphere.services.grid.security.credential.CredentialMapping" %>
<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<jsp:useBean id="credentialRetrievalUserBean"
             class="org.gridlab.gridsphere.portlets.grid.security.CredentialRetrievalUserBean"
             scope="request"/>
<form name="CredentialRetrievalUserPortlet" method="POST"
      action="<%=credentialRetrievalUserBean.getPortletActionURI("doListUserActiveCredential")%>">
  <input type="hidden" name="credentialMappingID" value=""/>
  <script type="text/javascript">
    function CredentialRetrievalUserPortlet_listUserActiveCredentialMapping_onClick() {
      document.CredentialRetrievalUserPortlet.action="doListUserActiveCredentialMapping";
      document.CredentialRetrievalUserPortlet.submit();
    }

    function CredentialRetrievalUserPortlet_viewUserActiveCredentialMapping_onClick(credentialMappingID) {
      document.CredentialRetrievalUserPortlet.credentialMappingID.value=credentialMappingID;
      document.CredentialRetrievalUserPortlet.action="doViewUserActiveCredentialMapping";
      document.CredentialRetrievalUserPortlet.submit();
    }

    function CredentialRetrievalUserPortlet_retrieveUserCredential_onClick(credentialMappingID) {
      document.CredentialRetrievalUserPortlet.action="doRetrieveUserCredential";
      document.CredentialRetrievalUserPortlet.submit();
    }

    function CredentialRetrievalUserPortlet_destroyUserCredential_onClick(credentialMappingID) {
      document.CredentialRetrievalUserPortlet.action="doDestroyUserCredential";
      document.CredentialRetrievalUserPortlet.submit();
    }
  </script>
<table class="portlet-pane" cellspacing="1">
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="500">
        <tr>
          <td class="portlet-frame-title">
              Retrieve Your Credentials
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-input">
            <input type="password"
                   name="passphrase"
                   value=""/>
            <input type="button"
                   name="doRetrieveUserCredential"
                   value="Submit"
                   onClick="javascript:CredentialRetrievalUserPortlet_retrieveUserCredential_onClick()"/>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="500">
        <tr>
          <td class="portlet-frame-title">
              Your Active Credentials
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-actions">
            <input type="button"
                   name="doListUserActiveCredential"
                   value="List Credentials"
                   onClick="javascript:CredentialRetrievalUserPortlet_listUserActiveCredentialMapping_onClick()"/>
            &nbsp;&nbsp;<input type="button"
                   name="doDestroyUserCredential"
                   value="Destroy Credentials"
                   onClick="javascript:CredentialRetrievalUserPortlet_destroyUserCredential_onClick()"/>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
<% List credentialMappingList = credentialRetrievalUserBean.getCredentialMappingList();
   int numCredentials = credentialMappingList.size();
   if (numCredentials == 0) { %>
        <tr>
          <td class="portlet-frame-text-alert">
              None of your credentials have been activated yet.
          </td>
        </tr>
<% } else { %>
       <tr>
         <td class="portlet-frame-header" width="150">
           Label
         </td>
         <td class="portlet-frame-header" width="150">
           Subject
         </td>
         <td class="portlet-frame-header" width="150">
           Time Left
         </td>
       </tr>
<%   for (int ii = 0; ii < numCredentials; ++ii) {
       CredentialMapping credentialMapping = (CredentialMapping)credentialMappingList.get(ii);
       Credential credential = (Credential)credentialMapping.getCredential(); %>
        <tr>
          <td class="portlet-frame-text">
            <a href="javascript:CredentialRetrievalUserPortlet_viewUserActiveCredentialMapping_onClick('<%=credentialMapping.getID()%>')">
              <%=credentialMapping.getLabel()%>
            </a>
          </td>
          <td class="portlet-frame-text">
            <%=credentialMapping.getSubject()%>
          </td>
          <td class="portlet-frame-text">
            <%=credential.getTimeLeft()%>
          </td>
        </tr>
<%   }
   } %>
      </table>
    </td>
  </tr>
</table>
</form>
