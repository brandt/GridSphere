<%@ page import="org.gridlab.gridsphere.portlet.User,
                 org.gridlab.gridsphere.portlet.PortletURI" %>
<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<jsp:useBean id="jobManagerBean"
             class="org.gridlab.gridsphere.portlets.grid.job.JobManagerBean"
             scope="request"/>
<gs:form action="doVerifyUserJob">
<input type="hidden" name="executable" value="<%=jobManagerBean.getParameter("executable")%>">
<input type="hidden" name="stdout" value="<%=jobManagerBean.getParameter("stdout")%>">
<input type="hidden" name="stderr" value="<%=jobManagerBean.getParameter("stderr")%>">
<input type="hidden" name="arguments" value="<%=jobManagerBean.getParameter("arguments")%>">
<input type="hidden" name="environment" value="<%=jobManagerBean.getParameter("environment")%>">
<input type="hidden" name="hostName" value="<%=jobManagerBean.getParameter("hostName")%>">
<input type="hidden" name="jobScheduler" value="<%=jobManagerBean.getParameter("jobScheduler")%>">
<input type="hidden" name="memory" value="<%=jobManagerBean.getParameter("memory")%>">
<input type="hidden" name="cpuCount" value="<%=jobManagerBean.getParameter("cpuCount")%>">
<table class="portlet-pane" cellspacing="1">
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-title">
              New Job
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-message">
              Last Step: Verify Job
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-actions">
            <gs:submit name="doValidateEditUserJob" value="&lt;&lt; Back"/>
            &nbsp;&nbsp;<gs:submit name="doSubmitEditUserJob" value="Submit Job"/>
            &nbsp;&nbsp;<gs:submit name="doCancelEditUserJob" value="Cancel Edit"/>
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
