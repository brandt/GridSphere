<%@ page import="org.gridlab.gridsphere.portlet.User,
                 org.gridlab.gridsphere.portlet.PortletURI" %>
<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<jsp:useBean id="jobManagerBean"
             class="org.gridlab.gridsphere.portlets.grid.job.JobManagerBean"
             scope="request"/>
<gs:form action="doViewUserJob">
  <gs:hiddenfield name="jobID" value="jobID"/>
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
             Job ID:
          </td>
          <td class="portlet-frame-value">
             &nbsp;<%=jobManagerBean.getPortletRequestAttribute("jobID")%>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label" width="200">
             Job Status:
          </td>
          <td class="portlet-frame-value">
             &nbsp;<%=jobManagerBean.getPortletRequestAttribute("jobStatus")%>
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
             &nbsp;<%=jobManagerBean.getPortletRequestAttribute("executable")%>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label" width="200">
             Stdout:
          </td>
          <td class="portlet-frame-value">
             &nbsp;<%=jobManagerBean.getPortletRequestAttribute("stdout")%>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label" width="200">
             Stderr:
          </td>
          <td class="portlet-frame-value">
             &nbsp;<%=jobManagerBean.getPortletRequestAttribute("stderr")%>
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
             &nbsp;<%=jobManagerBean.getPortletRequestAttribute("arguments")%>
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
             &nbsp;<%=jobManagerBean.getPortletRequestAttribute("environment")%>
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
             &nbsp;<%=jobManagerBean.getPortletRequestAttribute("hostName")%>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             Job Scheduler:&nbsp;
          </td>
          <td class="portlet-frame-input">
             &nbsp;<%=jobManagerBean.getPortletRequestAttribute("jobScheduler")%>
          </td>
        </tr>
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
