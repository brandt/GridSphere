<%@ page import="org.gridlab.gridsphere.portlet.User,
                 java.util.List,
                 org.gridlab.gridsphere.portlet.PortletURI,
                 org.gridlab.gridsphere.services.grid.job.Job,
                 org.gridlab.gridsphere.services.grid.job.JobSpecification,
                 org.gridlab.gridsphere.services.grid.data.file.FileHandle" %>
<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<jsp:useBean id="jobManagerBean"
             class="org.gridlab.gridsphere.portlets.grid.job.JobManagerBean"
             scope="request"/>
<gs:form action="doListUserJob">
<table class="portlet-pane" cellspacing="1" width="100%">
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-actions">
            <gs:submit name="doListUserJob" value="List Jobs"/>
            &nbsp;&nbsp;<gs:submit name="doNewUserJob" value="New Job"/>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
<% System.out.println("Getting jobs for user!!!!!!!!!!!");
   List userJobList = jobManagerBean.getUserJobList();
   int numUsers = userJobList.size();
   System.out.println("no jobs for user");
   if (numUsers == 0) { %>
        <tr>
          <td class="portlet-frame-text">
            There are no jobs associated with your account.
          </td>
        </tr>
<% } else { %>
       <tr>
         <td class="portlet-frame-header" width="100">
             Job
         </td>
         <td class="portlet-frame-header" width="150">
             Status
         </td>
         <td class="portlet-frame-header" width="150">
             Executable
         </td>
         <td class="portlet-frame-header" width="150">
             Host
         </td>
       </tr>
<%   for (int ii = 0; ii < numUsers; ++ii) {
      System.out.println("job " + ii + " for user");
       Job job = (Job)userJobList.get(ii);
       String jobID = job.getID();
       String jobStatus = job.getJobStatus().toString();
       String hostname = job.getRuntimeHost();
       JobSpecification jobSpecification = (JobSpecification)job.getJobSpecification();
       FileHandle executable = jobSpecification.getExecutable();
       String executableUrl = null;
       if (executable == null) {
           executableUrl = "";
       } else {
           executableUrl = executable.getFileUrl();
       }
%>
        <tr>
          <td class="portlet-frame-text">
            <gs:actionlink action="doViewUserJob" label="<%=jobID%>">
              <gs:actionparam name="jobID" value="<%=jobID%>"/>
            </gs:actionlink>
          </td>
          <td class="portlet-frame-text">
            <%=jobStatus%>
          </td>
          <td class="portlet-frame-text">
            <%=executableUrl%>
          </td>
          <td class="portlet-frame-text">
            <%=hostname%>
          </td>
        </tr>
<%   }
   } %>
      </table>
    </td>
  </tr>
</table>
</gs:form>
