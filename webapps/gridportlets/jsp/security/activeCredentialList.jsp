<%@ page import="org.gridlab.gridsphere.services.grid.security.credential.Credential,
                 org.gridlab.gridsphere.portlets.grid.security.CredentialManagerAdminBean,
                 java.util.List" %>
<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<jsp:useBean id="credentialManagerAdminBean"
             class="org.gridlab.gridsphere.portlets.grid.security.CredentialManagerAdminBean"
             scope="request"/>
<form name="ActiveCredentialPortlet" method="POST"
      action="<%=credentialManagerAdminBean.getPortletActionURI(CredentialManagerAdminBean.ACTION_ACTIVE_CREDENTIAL_LIST)%>">
  <input type="hidden" name="credentialID" value=""/>
  <script type="text/javascript">
    function ActiveCredentialPortlet_listCredential_onClick() {
      document.ActiveCredentialPortlet.action="<%=credentialManagerAdminBean.getPortletActionURI(CredentialManagerAdminBean.ACTION_ACTIVE_CREDENTIAL_LIST)%>";
      document.ActiveCredentialPortlet.submit();
    }

    function ActiveCredentialPortlet_viewCredential_onClick(credentialID) {
      document.ActiveCredentialPortlet.credentialID.value=credentialID;
      document.ActiveCredentialPortlet.action="<%=credentialManagerAdminBean.getPortletActionURI(CredentialManagerAdminBean.ACTION_ACTIVE_CREDENTIAL_VIEW)%>";
      document.ActiveCredentialPortlet.submit();
    }
  </script>
<table class="portlet-pane" cellspacing="1">
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="500">
        <tr>
          <td class="portlet-frame-title">
              List Active Credentials
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-actions">
            <input type="button"
                   name="<%=CredentialManagerAdminBean.ACTION_ACTIVE_CREDENTIAL_LIST%>"
                   value="List Credentials"
                   onClick="javascript:ActiveCredentialPortlet_listCredential_onClick()"/>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
<% List activeCredentialList = credentialManagerAdminBean.getActiveCredentialList();
   int numCredentials = activeCredentialList.size();
   if (numCredentials == 0) { %>
        <tr>
          <td class="portlet-frame-text">
              There are currently no credentials in use.
          </td>
        </tr>
<% } else { %>
       <tr>
         <td class="portlet-frame-header" width="150">
           Credential Subject
         </td>
         <td class="portlet-frame-header" width="100">
           Credential Issuer
         </td>
         <td class="portlet-frame-header" width="150">
           Time Left
         </td>
       </tr>
<%   for (int ii = 0; ii < numCredentials; ++ii) {
       Credential activeCredential = (Credential)activeCredentialList.get(ii); %>
        <tr>
          <td class="portlet-frame-text">
            <a href="javascript:ActiveCredentialPortlet_viewCredential_onClick('<%=activeCredential.getSubject()%>')">
              <%=activeCredential.getSubject()%>
            </a>
          </td>
          <td class="portlet-frame-text">
            <%=activeCredential.getIssuer()%>
          </td>
          <td class="portlet-frame-text">
            <%=activeCredential.getTimeLeft()%>
          </td>
        </tr>
<%   }
   } %>
      </table>
    </td>
  </tr>
</table>
</form>
