<%@ page import="org.gridlab.gridsphere.portlet.User,
                 org.gridlab.gridsphere.portlet.PortletURI" %>
<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<jsp:useBean id="jobManagerBean"
             class="org.gridlab.gridsphere.portlets.grid.job.JobManagerBean"
             scope="request"/>
<gs:form action="doEditUserJobResources">
<input type="hidden" name="executable" value="<%=jobManagerBean.getParameter("executable")%>">
<input type="hidden" name="stdout" value="<%=jobManagerBean.getParameter("stdout")%>">
<input type="hidden" name="stderr" value="<%=jobManagerBean.getParameter("stderr")%>">
<input type="hidden" name="arguments" value="<%=jobManagerBean.getParameter("arguments")%>">
<input type="hidden" name="environment" value="<%=jobManagerBean.getParameter("environment")%>">
<table class="portlet-pane" cellspacing="1" width="100%">
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
        </tr>
        <tr>
          <td class="portlet-frame-message">
              Step 2: Edit Job Resources
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-actions">
            <gs:submit name="doEditUserJobApplication" value="&lt;&lt; Back"/>
            &nbsp;&nbsp;<gs:submit name="doVerifyEditUserJob" value="Next &gt;&gt;"/>
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
             Computing Host:
          </td>
          <td class="portlet-frame-input" width="*">
             <input type="text"
                    name="hostName"
                    value="<%=jobManagerBean.getParameter("hostName")%>">
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             Job Scheduler:&nbsp;
          </td>
          <td class="portlet-frame-input">
            <select name="jobScheduler">
              <option value="jobmanager-fork">Fork</option>
              <option value="jobmanager-lsf">LSF</option>
              <option value="jobmanager-pbs">PBS</option>
            </select>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             Minimum Memory:
          </td>
          <td class="portlet-frame-input">
             <input type="text"
                    name="memory"
                    value="<%=jobManagerBean.getParameter("memory")%>">
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             Number of Processors:
          </td>
          <td class="portlet-frame-input">
             <input type="text"
                    name="cpuCount"
                    value="<%=jobManagerBean.getParameter("cpuCount")%>">
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
             <gs:textfield name="osType" value="osType">
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label" width="200">
             Operating System Name:
          </td>
          <td class="portlet-frame-input">
             <gs:textfield name="osName" value="osName">
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label" width="200">
             Operating System Version:
          </td>
          <td class="portlet-frame-input">
             <gs:textfield name="osVersion" value="osVersion">
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label" width="200">
             Operating System Release:
          </td>
          <td class="portlet-frame-input">
             <gs:textfield name="osRelease" value="osRelease">
          </td>
        </tr>
      </table>
    </td>
  </tr>
--%>
</table>
</gs:form>
