<%@ page import="org.gridlab.gridsphere.portlet.User,
                 org.gridlab.gridsphere.portlet.PortletURI" %>
<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<jsp:useBean id="jobManagerBean"
             class="org.gridlab.gridsphere.portlets.grid.job.JobManagerBean"
             scope="request"/>
<gs:form action="doViewUserJob">
<input type="hidden" name="jobID" value="<%=jobManagerBean.getParameter("jobID")%>">
<table class="portlet-pane" cellspacing="1">
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-title">
            View Job
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-actions">
            <gs:submit name="doListJob" value="List Jobs"/>
            &nbsp;&nbsp;<gs:submit name="doNewUserJob" value="New Job"/>
<%--
            &nbsp;&nbsp;<gs:submit name="doStageUserJob" value="Stage Job"/>
            &nbsp;&nbsp;<gs:submit name="doMigrateUserJob" value="Migrate Job"/>
            &nbsp;&nbsp;<gs:submit name="doDeletUsereJob" value="Delete Job"/>
--%>
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
             Executable:
          </td>
          <td class="portlet-frame-value">
             <gs:text name="executable" value="executable">
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label" width="200">
             Stdout:
          </td>
          <td class="portlet-frame-value">
             <gs:text name="stdout" value="stdout">
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label" width="200">
             Stderr:
          </td>
          <td class="portlet-frame-value">
             <gs:text name="stderr" value="stderr">
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
             Arguments:
          </td>
          <td class="portlet-frame-value">
             <gs:text name="arguments" value="arguments">
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
             Environment:
          </td>
          <td class="portlet-frame-value">
             <gs:text name="environment" value="environment">
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
             Host Name:
          </td>
          <td class="portlet-frame-input">
             <gs:text name="hostName" value="hostName">
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             Job Scheduler:&nbsp;
          </td>
          <td class="portlet-frame-input">
             <gs:text name="hostName" value="jobScheduler">
          </td>
        </tr>
    </td>
  </tr>
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-label" width="200">
             Minimum Memory:
          </td>
          <td class="portlet-frame-input">
             <gs:text name="memory" value="memory">
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label" width="200">
             Number of Processors:
          </td>
          <td class="portlet-frame-input">
             <gs:text name="cpuCount" value="cpuCount">
          </td>
        </tr>
      </table>
    </td>
  </tr>
<%--
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-label" width="200">
             Operating System Type:
          </td>
          <td class="portlet-frame-input">
             <gs:text name="osType" value="osType">
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label" width="200">
             Operating System Name:
          </td>
          <td class="portlet-frame-input">
             <gs:text name="osName" value="osName">
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label" width="200">
             Operating System Version:
          </td>
          <td class="portlet-frame-input">
             <gs:text name="osVersion" value="osVersion">
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label" width="200">
             Operating System Release:
          </td>
          <td class="portlet-frame-input">
             <gs:text name="osRelease" value="osRelease">
          </td>
        </tr>
      </table>
    </td>
  </tr>
--%>
</table>
</gs:form>
