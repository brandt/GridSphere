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
    function CredentialRetrievalUserPortlet_listUserActiveCredential_onClick() {
      document.CredentialRetrievalUserPortlet.action="<%=credentialRetrievalUserBean.getPortletActionURI("doListUserActiveCredential")%>";
      document.CredentialRetrievalUserPortlet.submit();
    }

    function CredentialRetrievalUserPortlet_viewUserActiveCredential_onClick(credentialMappingID) {
      document.CredentialRetrievalUserPortlet.credentialMappingID.value=credentialMappingID;
      document.CredentialRetrievalUserPortlet.action="<%=credentialRetrievalUserBean.getPortletActionURI("doViewUserActiveCredential")%>";
      document.CredentialRetrievalUserPortlet.submit();
    }

    function CredentialRetrievalUserPortlet_retrieveUserCredential_onClick() {
      document.CredentialRetrievalUserPortlet.action="<%=credentialRetrievalUserBean.getPortletActionURI("doRetrieveUserCredential")%>";
      document.CredentialRetrievalUserPortlet.submit();
    }

    function CredentialRetrievalUserPortlet_refreshUserCredential_onClick() {
      var validate = GridSphere_CheckBoxList_validateCheckOneOrMore(document.CredentialRetrievalUserPortlet.credentialMappingID);
      // Validate remove action
      if (validate == false) {
        alert("Please select the credentials you would like to refresh.");
      } else {
        document.CredentialRetrievalUserPortlet.action="<%=credentialRetrievalUserBean.getPortletActionURI("doRefreshUserCredential")%>";
        document.CredentialRetrievalUserPortlet.submit();
      }
    }

    function CredentialRetrievalUserPortlet_destroyUserCredential_onClick() {
      var validate = GridSphere_CheckBoxList_validateCheckOneOrMore(document.CredentialRetrievalUserPortlet.credentialMappingID);
      // Validate remove action
      if (validate == false) {
        alert("Please select the credentials you would like to destroy.");
      } else {
        document.CredentialRetrievalUserPortlet.action="<%=credentialRetrievalUserBean.getPortletActionURI("doDestroyUserCredential")%>";
        document.CredentialRetrievalUserPortlet.submit();
      }
    }
  </script>
<table class="portlet-pane" cellspacing="1">
<% if (credentialRetrievalUserBean.isFormInvalid()) { %>
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="500">
        <tr>
          <td class="portlet-frame-message-alert">
            <%=credentialRetrievalUserBean.getFormInvalidMessage()%>
          </td>
        </tr>
      </table>
    </td>
  </tr>
<% } %>
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="500">
        <tr>
          <td class="portlet-frame-title">
              Retrieve Your Credentials From [<%=credentialRetrievalUserBean.getCredentialRetrievalHost()%>]
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-actions">
            Password:
            &nbsp;&nbsp;<input type="password"
                   name="password"
                   value=""/>
            &nbsp;&nbsp;<input type="button"
                   name="doRetrieveUserCredential"
                   value="Submit"
                   onClick="javascript:CredentialRetrievalUserPortlet_retrieveUserCredential_onClick()"/>
          </td>
        </tr>
      </table>
    </td>
  </tr>
<% List credentialMappingList = credentialRetrievalUserBean.getCredentialMappingList();
   int numCredentialMappings = credentialMappingList.size();
   if (numCredentialMappings == 0) { %>
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-text">
              None of your credentials have been activated yet.
          </td>
        </tr>
          </td>
        </tr>
      </table>
    </td>
  </tr>
<% } else { %>
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
                   onClick="javascript:CredentialRetrievalUserPortlet_listUserActiveCredential_onClick()"/>
<%-- TEMPORARILY COMMENTED OUT (MPR 25/02/03)
            &nbsp;&nbsp;<input type="button"
                   name="doRefreshUserCredential"
                   value="Refresh Credentials"
                   onClick="javascript:CredentialRetrievalUserPortlet_refreshUserCredential_onClick()"/>
            &nbsp;&nbsp;<input type="button"
                   name="doDestroyUserCredential"
                   value="Destroy Credentials"
                   onClick="javascript:CredentialRetrievalUserPortlet_destroyUserCredential_onClick()"/>
--%>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="500">
        <tr>
<%-- TEMPORARILY COMMENTED OUT (MPR 25/02/03)
          <td class="portlet-frame-header-checkbox">
              <input type="checkbox"
               name="credentialMappingID"
               value=""
               onClick="javascript:GridSphere_CheckBoxList_checkAll(document.AccessControllerPortlet.groupEntryUserID)"/>
          </td>
--%>
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
<%   for (int ii = 0; ii < numCredentialMappings; ++ii) {
       CredentialMapping credentialMapping = (CredentialMapping)credentialMappingList.get(ii);
       Credential credential = credentialMapping.getCredential(); %>
        <tr>
<%-- TEMPORARILY COMMENTED OUT (MPR 25/02/03)
          <td class="portlet-frame-entry-checkbox">
              <input type="checkbox"
               name="credentialMappingID"
               value="<%=credentialMapping.getID()%>"
               onClick="javascript:GridSphere_CheckBoxList_onClick(document.CredentialRetrievalUserPortlet.credentialMappingID,
                                                                   this)"/>
--%>
          <td class="portlet-frame-text">
            <a href="javascript:CredentialRetrievalUserPortlet_viewUserActiveCredential_onClick('<%=credentialMapping.getID()%>')">
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
