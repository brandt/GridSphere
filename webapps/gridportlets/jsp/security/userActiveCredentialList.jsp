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
<gs:form action="doListUserActiveCredential">
  <input type="hidden" name="credentialMappingID" value=""/>
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
            &nbsp;&nbsp;<gs:submit name="doRetrieveUserCredential" value="Submit"/>
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
      </table>
    </td>
  </tr>
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="500">
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
</gs:form>
