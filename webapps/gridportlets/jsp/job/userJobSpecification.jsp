<%@ page language="java"%>
<%-- Set error page --%>
<%@ page errorPage="/jsp/orbiter/pages/error.jsp"%>
<%@ page import="java.util.Iterator"%>
<%-- Instantiate module bean --%>
<jsp:useBean id="module" scope="page"
  class="org.gridlab.portal.pages.GrmsJobManagerPageBean">
<%  module.setPageContext(pageContext); %>
</jsp:useBean>
<!-- Begin presentation -->
<form method="POST" name="GrmsJobSpecification" action="servlet/orbiter">
  <input type="hidden" name="moduleRequest" value="gridlab.resmgmt.GrmsJobSpecification">
  <table width="90%" border="0"
         cellspacing="2" cellpadding="0" bgcolor="#999999">
    <tr>
      <td width="100%">
        <table width="100%" border="0"
               cellspacing="1" cellpadding="1" bgcolor="#336699">
          <tr>
            <td height="20" align="left" valign="middle">
              <font color="#FFFFFF" face="Arial, Helvetica, sans-serif">
              &nbsp;New Job Specification (GridLab Resource Management System)
              </font></td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td width="100%">
        <table width="100%" border="0" cellspacing="1" cellpadding="1" bgcolor="#CCCCCC">
          <tr>
<td bgcolor="#FFFFFF"><font size="-1" face="Arial, Helvetica, sans-serif">&nbsp;<textarea
        name="jobSpecification" rows="30" cols="80"><%=module.getParameter("jobSpecification")%></textarea></font></td>
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
              &nbsp;<input type="button" name="JobSubmit" value="Submit Job"
                onClick="javascript:GrmsJobSubmit_onClick()">&nbsp;
              &nbsp;<input type="button" name="JobCancel" value="Cancel Job"
                onClick="javascript:GrmsJobCancel_onClick()">&nbsp;
              </font></td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
  <script language="JavaScript">

    function GrmsJobSpecification_validate() {
      var isValid = true;
      if (document.GrmsJobSpecification.jobSpecification.value == "") {
        isValid = false;
        alert("Please fill out a job request.");
        document.GrmsJobSpecification.jobSpecification.focus();
      }
      return isValid;
    }

    function GrmsJobSubmit_onClick() {
      if (GridLabResmgmtJobSpecification_validate()) {
        document.GrmsJobSpecification.moduleRequest.value="gridlab.resmgmt.GrmsJobSubmit";
        document.GrmsJobSpecification.submit();
      }
    }

    function GrmsJobCancel_onClick() {
      if (confirm("Are you sure you want to cancel this operation?")) {
        document.GrmsJobSpecification.moduleRequest.value="gridlab.resmgmt.GrmsJobList";
        document.GrmsJobSpecification.submit();
      }
    }

  </script>
</form>
