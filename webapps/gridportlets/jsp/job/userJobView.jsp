<%@ page language="java"%>
<%-- Set error page --%>
<%@ page errorPage="/jsp/orbiter/pages/error.jsp"%>
<%-- Instantiate module bean --%>
<jsp:useBean id="module" scope="page"
  class="org.gridlab.portal.pages.GrmsJobManagerPageBean"/>
<% module.setPageContext(pageContext); %>
<!-- Begin presentation -->
<form method="POST" name="GrmsJobView" action="servlet/orbiter">
  <input type="hidden" name="moduleRequest" value="gridlab.resmgmt.GrmsJobView">
  <input type="hidden" name="id" value="<%=module.getParameter("id")%>">
  <table width="90%" border="0"
         cellspacing="2" cellpadding="0" bgcolor="#999999">
    <tr>
      <td width="100%">
        <table width="100%" border="0"
               cellspacing="1" cellpadding="1" bgcolor="#336699">
          <tr>
            <td height="20" align="left" valign="middle">
              <font color="#FFFFFF" face="Arial, Helvetica, sans-serif">
              &nbsp;Job View
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
              &nbsp;<input type="button" name="refresh" value="Refresh View"
                onClick="javascript:GrmsJobView_onClick()">&nbsp;
              &nbsp;<input type="button" name="migrate" value="Migrate Job"
                onClick="javascript:GrmsJobMigrate_onClick()">&nbsp;
              &nbsp;<input type="button" name="list" value="List Jobs"
                onClick="javascript:GrmsJobList_onClick()">&nbsp;
              <!-- &nbsp;<input type="button" name="stage" value="Stage"
                onClick="javascript:GrmsJobStage_onClick()">&nbsp; -->
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
  </table>
  <script language="JavaScript">

    function GrmsJobList_onClick() {
      document.GrmsJobView.moduleRequest.value="gridlab.resmgmt.GrmsJobList";
      document.GrmsJobView.submit();
    }

    function GrmsJobView_onClick() {
      document.GrmsJobVerify.moduleRequest.value="gridlab.resmgmt.GrmsJobView";
      document.GrmsJobVerify.submit();
    }

    function GrmsJobStage_onClick() {
      document.GrmsJobVerify.moduleRequest.value="gridlab.resmgmt.GrmsJobStage";
      document.GrmsJobVerify.submit();
    }

    function GrmsJobMigrate_onClick() {
      document.GrmsJobView.moduleRequest.value="gridlab.resmgmt.GrmsJobMigrate";
      document.GrmsJobView.submit();
    }

  </script>
</form>
