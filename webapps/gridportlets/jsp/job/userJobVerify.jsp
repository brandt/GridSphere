<%@ page language="java"%>
<%-- Set error page --%>
<%@ page errorPage="/jsp/orbiter/pages/error.jsp"%>
<%-- Instantiate module bean --%>
<jsp:useBean id="module" scope="page"
  class="org.gridlab.portal.pages.GrmsJobManagerPageBean"/>
<% module.setPageContext(pageContext); %>
<!-- Begin presentation -->
<form method="POST" name="GrmsJobVerify" action="servlet/orbiter">
  <input type="hidden" name="moduleRequest" value="gridlab.resmgmt.GrmsJobVerify">
  <input type="hidden" name="executable" value="<%=module.getParameter("executable")%>">
  <input type="hidden" name="stdout" value="<%=module.getParameter("stdout")%>">
  <input type="hidden" name="stderr" value="<%=module.getParameter("stderr")%>">
  <input type="hidden" name="arguments" value="<%=module.getParameter("arguments")%>">
  <input type="hidden" name="environment" value="<%=module.getParameter("environment")%>">
  <input type="hidden" name="hostGroupName" value="<%=module.getParameter("hostGroupName")%>">
  <input type="hidden" name="hostName" value="<%=module.getParameter("hostName")%>">
  <input type="hidden" name="jobScheduler" value="<%=module.getParameter("jobScheduler")%>">
  <input type="hidden" name="memory" value="<%=module.getParameter("memory")%>">
  <input type="hidden" name="cpuCount" value="<%=module.getParameter("cpuCount")%>">
  <input type="hidden" name="osType" value="<%=module.getParameter("osType")%>">
  <input type="hidden" name="osName" value="<%=module.getParameter("osName")%>">
  <input type="hidden" name="osVersion" value="<%=module.getParameter("osVersion")%>">
  <input type="hidden" name="osRelease" value="<%=module.getParameter("osRelease")%>">
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
              &nbsp;Last Step: Verify the information below is correct.
              </font></td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td bgcolor="#999999" width="100%">
        <font size="-1" face="Arial, Helvetica, sans-serif">
          &nbsp;Application
        </font>
      </td>
    </tr>
    <tr>
      <td width="100%">
        <table width="100%" border="0" cellspacing="1" cellpadding="1" bgcolor="#CCCCCC">
<tr>
  <td width="200">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;Executable:&nbsp;
    </font>
  </td>
  <td bgcolor="#FFFFFF">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;<%=module.getParameter("executable")%>
    </font>
  </td>
</tr>
<tr>
  <td width="200">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;Stdout:&nbsp;
    </font>
  </td>
  <td bgcolor="#FFFFFF">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;<%=module.getParameter("stdout")%>
    </font>
  </td>
</tr>
<tr>
  <td width="200">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;Stderr:&nbsp;
    </font>
  </td>
  <td bgcolor="#FFFFFF">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;<%=module.getParameter("stderr")%>
    </font>
  </td>
</tr>
<tr>
  <td width="200">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;Arguments:&nbsp;
    </font>
  </td>
  <td bgcolor="#FFFFFF">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;<%=module.getParameter("arguments")%>
    </font>
  </td>
</tr>
<tr>
  <td width="200">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;Environment:&nbsp;
    </font>
  </td>
  <td bgcolor="#FFFFFF">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;<%=module.getParameter("environment")%>
    </font>
  </td>
</tr>
        </table>
      </td>
    </tr>
    <tr>
      <td bgcolor="#999999" width="100%">
        <font size="-1" face="Arial, Helvetica, sans-serif">
          &nbsp;Resource Requirements
        </font>
      </td>
    </tr>
    <tr>
      <td width="100%">
        <table width="100%" border="0" cellspacing="1" cellpadding="1" bgcolor="#CCCCCC">
<tr>
  <td width="200">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;Hostname:&nbsp;
    </font>
  </td>
  <td bgcolor="#FFFFFF">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;<%=module.getParameter("hostName")%>
    </font>
  </td>
</tr>
<tr>
  <td width="200">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;Job Scheduler:&nbsp;
    </font>
  </td>
  <td bgcolor="#FFFFFF">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;<%=module.getParameter("jobScheduler")%>
    </font>
  </td>
</tr>
<tr>
  <td width="200">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;Memory:&nbsp;
    </font>
  </td>
  <td bgcolor="#FFFFFF">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;<%=module.getParameter("memory")%>
    </font>
  </td>
</tr>
<tr>
  <td width="200">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;Processors:&nbsp;
    </font>
  </td>
  <td bgcolor="#FFFFFF">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;<%=module.getParameter("cpuCount")%>
    </font>
  </td>
</tr>
<tr>
  <td width="200">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;OS Type:&nbsp;
    </font>
  </td>
  <td bgcolor="#FFFFFF">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;<%=module.getParameter("osType")%>
    </font>
  </td>
</tr>
<tr>
  <td width="200">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;OS Name:&nbsp;
    </font>
  </td>
  <td bgcolor="#FFFFFF">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;<%=module.getParameter("osName")%>
    </font>
  </td>
</tr>
<tr>
  <td width="200">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;OS Version:&nbsp;
    </font>
  </td>
  <td bgcolor="#FFFFFF">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;<%=module.getParameter("osVersion")%>
    </font>
  </td>
</tr>
<tr>
  <td width="200">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;OS Release:&nbsp;
    </font>
  </td>
  <td bgcolor="#FFFFFF">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;<%=module.getParameter("osRelease")%>
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
              &nbsp;<input type="button" name="JobSubmit" value="Submit"
                onClick="javascript:GrmsJobSubmit_onClick()">&nbsp;
              &nbsp;<input type="button" name="JobCancel" value="Cancel"
                onClick="javascript:GrmsJobCancel_onClick()">&nbsp;
              </font></td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
  <script language="JavaScript">

    function GrmsJobVerify_onValidate() {
      var isValid = true;
      return isValid;
    }

    function GrmsJobBack_onClick() {
        document.GrmsJobVerify.moduleRequest.value="gridlab.resmgmt.GrmsJobResources";
        document.GrmsJobVerify.submit();
    }

    function GrmsJobSubmit_onClick() {
      if (GrmsJobVerify_onValidate()) {
        document.GrmsJobVerify.moduleRequest.value="gridlab.resmgmt.GrmsJobSubmit";
        document.GrmsJobVerify.submit();
      }
    }

    function GrmsJobCancel_onClick() {
      if (confirm("Are you sure you want to cancel this operation?")) {
        document.GrmsJobVerify.moduleRequest.value="gridlab.resmgmt.Jobs";
        document.GrmsJobVerify.submit();
      }
    }

  </script>
</form>
