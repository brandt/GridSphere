<%@ page import="org.gridlab.gridsphere.services.grid.security.credential.Credential,
                 org.gridlab.gridsphere.portlets.grid.security.CredentialManagerAdminBean,
                 java.util.List" %>
<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<jsp:useBean id="jobManagerBean"
             class="org.gridlab.gridsphere.portlets.grid.job.JobManagerBean"
             scope="request"/>
<form name="JobManagerPortlet" method="POST"
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

<!-- Begin presentation -->
<form method="POST" name="GrmsJobList" action="servlet/orbiter">
  <input type="hidden" name="moduleRequest" value="gridlab.resmgmt.GrmsJobList">
  <input type="hidden" name="id" value="0">
  <br>
  <table width="90%" border="0"
         cellspacing="2" cellpadding="0" bgcolor="#999999">
    <tr>
      <td width="100%">
        <table width="100%" border="0"
               cellspacing="1" cellpadding="1" bgcolor="#336699">
          <tr>
            <td height="20" align="left" valign="middle">
              <font color="#FFFFFF" face="Arial, Helvetica, sans-serif">
              &nbsp;My Jobs
              </font></td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td width="100%">
        <table width="100%" border="0"
               cellspacing="1" cellpadding="1" bgcolor="#CCCCCC">
          <tr>
            <td align="left" valign="middle">
              <font size="-1" face="Arial, Helvetica, sans-serif">
              &nbsp;<input type="button" name="Refresh" value="Refresh List"
                onClick="javascript:GrmsJobList_onClick()">&nbsp;
              &nbsp;<input type="button" name="JobNew" value="New Job"
                onClick="javascript:GrmsJobCreate_onClick()">&nbsp;
              </font></td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td width="100%">
        <table width="100%" border="0" cellspacing="1" cellpadding="1" bgcolor="#FFFFFF">
          <tr>
<td bgcolor="#000000">
  <font color="#FFFFFF" size="-1" face="Arial, Helvetica, sans-serif">
    &nbsp;Job Id
  </font>
</td>
<td bgcolor="#000000">
  <font color="#FFFFFF" size="-1" face="Arial, Helvetica, sans-serif">
    &nbsp;Resource
  </font>
</td>
<td bgcolor="#000000">
  <font color="#FFFFFF" size="-1" face="Arial, Helvetica, sans-serif">
    &nbsp;Job Status
  </font>
</td>
<td bgcolor="#000000">
  <font color="#FFFFFF" size="-1" face="Arial, Helvetica, sans-serif">
    &nbsp;Date Submitted
  </font>
</td>
          </tr>
<%  List jobList = module.getJobList();
    if (jobList.size() == 0) { %>
          <tr>
<td colspan="4" bgcolor="#FFFFFF">
    <font size="-1" face="Arial, Helvetica, sans-serif">
        &nbsp;No active jobs
    </font>
</td>
          </tr>
<% } else  {
        Iterator jobIterator = jobList.iterator();
        while (jobIterator.hasNext()) {
            Job job = (Job)jobIterator.next();
            String jobId = job.getId();
            String jobHost = job.getRuntimeHost();
            JobStatus jobStatus = job.getJobStatus();
            Date jobDateStarted = job.getDateStarted(); %>
          <tr>
<td bgcolor="#FFFFFF">
  <font size="-1" face="Arial, Helvetica, sans-serif">
    &nbsp;<a href="javascript:GrmsJobView_onClick('<%=jobId%>')">
      <%=jobId%>
    </a>
  </font>
</td>
<td bgcolor="#FFFFFF">
  <font size="-1" face="Arial, Helvetica, sans-serif">
    &nbsp;<%=jobHost%>
  </font>
</td>
<td bgcolor="#FFFFFF">
  <font size="-1" face="Arial, Helvetica, sans-serif">
    &nbsp;<%=jobStatus%>
  </font>
</td>
<td bgcolor="#FFFFFF">
  <font size="-1" face="Arial, Helvetica, sans-serif">
    &nbsp;<%=DateUtil.formatDateLong(jobDateStarted)%>
  </font>
</td>
          </tr>
<%      }
    } %>
        </table>
      </td>
    </tr>
  </table>
  <script language="JavaScript">

    function GrmsJobList_onClick() {
      document.GrmsJobList.moduleRequest.value="gridlab.resmgmt.GrmsJobList";
      document.GrmsJobList.submit();
    }

    function GrmsJobView_onClick(jobId) {
      document.GrmsJobList.id.value=jobId;
      document.GrmsJobList.moduleRequest.value="gridlab.resmgmt.GrmsJobView";
      document.GrmsJobList.submit();
    }

    function GrmsJobCreate_onClick() {
      document.GrmsJobList.moduleRequest.value="gridlab.resmgmt.GrmsJobCreate";
      document.GrmsJobList.submit();
    }

  </script>
</form>
