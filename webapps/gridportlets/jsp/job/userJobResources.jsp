<%@ page import="java.util.Iterator,
                 org.ascportal.gridsphere.resources.Host,
                 org.ascportal.gridsphere.resources.HostGroup"%>
<%@ page language="java"%>
<%-- Set error page --%>
<%@ page errorPage="/jsp/orbiter/pages/error.jsp"%>
<%-- Instantiate module bean --%>
<jsp:useBean id="module" scope="page"
  class="org.gridlab.portal.pages.GrmsJobManagerPageBean"/>
<% module.setPageContext(pageContext); %>
<% String selectedHostName = module.getParameter("hostName");
   String selectedHostGroupName = module.getParameter("hostGroupName");
   HostGroup selectedHostGroup = null; %>
<!-- Begin presentation -->
<form method="POST" name="GrmsJobResources" action="servlet/orbiter">
  <input type="hidden" name="moduleRequest" value="gridlab.resmgmt.GrmsJobResources">
  <input type="hidden" name="executable" value="<%=module.getParameter("executable")%>">
  <input type="hidden" name="stdout" value="<%=module.getParameter("stdout")%>">
  <input type="hidden" name="stderr" value="<%=module.getParameter("stderr")%>">
  <input type="hidden" name="arguments" value="<%=module.getParameter("arguments")%>">
  <input type="hidden" name="environment" value="<%=module.getParameter("environment")%>">
  <table width="90%" border="0"
         cellspacing="2" cellpadding="0" bgcolor="#999999">
    <tr>
      <td width="100%">
        <table width="100%" border="0"
               cellspacing="1" cellpadding="1" bgcolor="#336699">
          <tr>
            <td height="20" align="left" valign="middle">
              <font color="#FFFFFF" face="Arial, Helvetica, sans-serif">
              &nbsp;New Job (GridLab Resource Management System)
              </font></td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td width="100%">
        <table width="100%" border="0"
               cellspacing="1" cellpadding="1" bgcolor="#FFFFFF">
          <tr>
            <td height="20" align="left" valign="middle">
              <font size="-1" color="#000000" face="Arial, Helvetica, sans-serif">
              &nbsp;Step 2: Specify resource requirements for your application
              </font></td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td bgcolor="#999999" width="100%">
        <font size="-1" face="Arial, Helvetica, sans-serif">
          &nbsp;Computational Host
        </font>
      </td>
    </tr>
    <tr>
      <td width="100%">
        <table width="100%" border="0" cellspacing="1" cellpadding="1" bgcolor="#CCCCCC">
<tr>
  <td width="200">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;Testbed:&nbsp;
    </font>
  </td>
  <td bgcolor="#FFFFFF">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;<select name="hostGroupName" onChange="javascript:GrmsJobHostGroup_onChange()">
                <option value="">&lt;All Resources&gt;</option>
<%  Iterator hostGroups = HostGroup.getAllHostGroups();
    while (hostGroups.hasNext()) {
        HostGroup hostGroup = (HostGroup)hostGroups.next();
        if (hostGroup.getName().equals(selectedHostGroupName)) {
            selectedHostGroup = hostGroup; %>
                <option value="<%=selectedHostGroupName%>" selected><%=selectedHostGroupName%></option>
<%      } else { %>
                <option value="<%=hostGroup.getName()%>"><%=hostGroup.getName()%></option>
<%      }
    } %>
            </select>
    </font>
  </td>
</tr>
<tr>
  <td width="200">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;Host:&nbsp;
    </font>
  </td>
  <td bgcolor="#FFFFFF">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;<select name="hostName">
                <option value="">&lt;Automatic&gt;</option>
<%  Iterator hosts = null;
    if (selectedHostGroup == null) {
        hosts = Host.getAllHosts();
    } else {
        hosts = selectedHostGroup.getHosts();
    }
    while (hosts.hasNext()) {
        Host host = (Host)hosts.next();
        if (host.getHostname().equals(selectedHostName)) { %>
                <option value="<%=selectedHostName%>" selected><%=selectedHostName%></option>
<%      } else { %>
                <option value="<%=host.getHostname()%>"><%=host.getHostname()%></option>
<%      }
    } %>
            </select>
<%--
      &nbsp;<input type="text" size="30" name="hostName"
               value="<%=module.getParameter("hostName")%>"></input>
--%>
    </font>
  </td>
</tr>
<tr>
  <td>
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;Job Scheduler:&nbsp;
    </font>
  </td>
  <td bgcolor="#FFFFFF">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;<input type="text" size="30" name="jobScheduler"
               value="<%=module.getParameter("jobScheduler")%>"></input>
    </font>
  </td>
</tr>
        </table>
      </td>
    </tr>
    <tr>
      <td bgcolor="#999999" width="100%">
        <font size="-1" face="Arial, Helvetica, sans-serif">
          &nbsp;Computational Requirements
        </font>
      </td>
    </tr>
    <tr>
      <td width="100%">
        <table width="100%" border="0" cellspacing="1" cellpadding="1" bgcolor="#CCCCCC">
<tr>
  <td width="200">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;Memory:&nbsp;
    </font>
  </td>
  <td bgcolor="#FFFFFF">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;<input type="text" size="30" name="memory"
               value="<%=module.getParameter("memory")%>"></input>
    </font>
  </td>
</tr>
<tr>
  <td>
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;Number of Processors:&nbsp;
    </font>
  </td>
  <td bgcolor="#FFFFFF">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;<input type="text" size="30" name="cpuCount"
               value="<%=module.getParameter("cpuCount")%>"></input>
    </font>
  </td>
</tr>
        </table>
      </td>
    </tr>
    <tr>
      <td bgcolor="#999999" width="100%">
        <font size="-1" face="Arial, Helvetica, sans-serif">
          &nbsp;Operating System Requirements
        </font>
      </td>
    </tr>
    <tr>
      <td width="100%">
        <table width="100%" border="0" cellspacing="1" cellpadding="1" bgcolor="#CCCCCC">
<tr>
  <td width="200">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;Operating System Type:&nbsp;
    </font>
  </td>
  <td bgcolor="#FFFFFF">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;<input type="text" size="30" name="osType"
               value="<%=module.getParameter("osType")%>"></input>
    </font>
  </td>
</tr>
<tr>
  <td>
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;Operating System Name:&nbsp;
    </font>
  </td>
  <td bgcolor="#FFFFFF">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;<input type="text" size="30" name="osName"
               value="<%=module.getParameter("osName")%>"></input>
    </font>
  </td>
</tr>
<tr>
  <td>
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;Operating System Version:&nbsp;
    </font>
  </td>
  <td bgcolor="#FFFFFF">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;<input type="text" size="30" name="osVersion"
               value="<%=module.getParameter("osVersion")%>"></input>
    </font>
  </td>
</tr>
<tr>
  <td>
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;Operating System Release:&nbsp;
    </font>
  </td>
  <td bgcolor="#FFFFFF">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;<input type="text" size="30" name="osRelease"
               value="<%=module.getParameter("osRelease")%>"></input>
    </font>
  </td>
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
              &nbsp;<input type="button" name="JobBack" value="<< Back"
                onClick="javascript:GrmsJobBack_onClick()">&nbsp;
              &nbsp;<input type="button" name="JobNext" value="Next >>"
                onClick="javascript:GrmsJobNext_onClick()">&nbsp;
              &nbsp;<input type="button" name="JobCancel" value="Cancel"
                onClick="javascript:GrmsJobCancel_onClick()">&nbsp;
              </font></td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
  <script language="JavaScript">

    function GrmsJobHostGroup_onChange() {
        document.GrmsJobResources.submit();
    }

    function GrmsJobResources_onValidate() {
      var isValid = true;
      return isValid;
    }

    function GrmsJobBack_onClick() {
        document.GrmsJobResources.moduleRequest.value="gridlab.resmgmt.GrmsJobApplication";
        document.GrmsJobResources.submit();
    }

    function GrmsJobNext_onClick() {
      if (GrmsJobResources_onValidate()) {
        document.GrmsJobResources.moduleRequest.value="gridlab.resmgmt.GrmsJobVerify";
        document.GrmsJobResources.submit();
      }
    }

    function GrmsJobCancel_onClick() {
      if (confirm("Are you sure you want to cancel this operation?")) {
        document.GrmsJobResources.moduleRequest.value="gridlab.resmgmt.Jobs";
        document.GrmsJobResources.submit();
      }
    }

  </script>
</form>
