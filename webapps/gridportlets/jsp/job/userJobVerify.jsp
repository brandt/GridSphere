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
<table class="portlet-pane" cellspacing="1" width="100%">
<% if (jobManagerBean.isFormInvalid()) { %>
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-message-alert">
            <%=jobManagerBean.getFormInvalidMessage()%>
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
          <td class="portlet-frame-message">
              <b>Last Step: Verify Job</b>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-actions">
            <gs:submit name="doEditUserJobResources" value="&lt;&lt; Back"/>
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
          <td class="portlet-frame-text" width="*">
            &nbsp;<%=jobManagerBean.getParameter("executable")%>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             Stdout:
          </td>
          <td class="portlet-frame-text">
            &nbsp;<%=jobManagerBean.getParameter("stdout")%>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             Stderr:
          </td>
          <td class="portlet-frame-text">
            &nbsp;<%=jobManagerBean.getParameter("stderr")%>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             Arguments:
          </td>
          <td class="portlet-frame-text">
            &nbsp;<%=jobManagerBean.getParameter("arguments")%>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             Environment:
          </td>
          <td class="portlet-frame-text">
            &nbsp;<%=jobManagerBean.getParameter("environment")%>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             Host Name:
          </td>
          <td class="portlet-frame-text">
            &nbsp;<%=jobManagerBean.getParameter("hostName")%>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             Job Scheduler:&nbsp;
          </td>
          <td class="portlet-frame-text">
            &nbsp;<%=jobManagerBean.getParameter("jobScheduler")%>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             Minimum Memory:
          </td>
          <td class="portlet-frame-text">
            &nbsp;<%=jobManagerBean.getParameter("memory")%>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             Number of Processors:
          </td>
          <td class="portlet-frame-text">
            &nbsp;<%=jobManagerBean.getParameter("cpuCount")%>
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
