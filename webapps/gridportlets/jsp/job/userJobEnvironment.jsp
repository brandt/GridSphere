<%@ page language="java"%>
<%-- Set error page --%>
<%@ page errorPage="/jsp/orbiter/pages/error.jsp"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="org.gridlab.resmgmt.grms.GrmsJob"%>
<%@ page import="org.gridlab.resmgmt.grms.Argument"%>
<%@ page import="org.gridlab.resmgmt.grms.FileArgument"%>
<%@ page import="org.gridlab.resmgmt.grms.ValueArgument"%>
<%-- Instantiate module bean --%>
<jsp:useBean id="module" scope="page"
  class="org.gridlab.portal.pages.GrmsJobManagerPageBean"/>
<% module.setPageContext(pageContext); %>
<!-- Begin presentation -->
<form method="POST" name="GrmsJobEnvironment" action="servlet/orbiter">
  <input type="hidden" name="moduleRequest" value="gridlab.resmgmt.GrmsJobEnvironment">
  <br>
  <table width="90%" border="0"
         cellspacing="2" cellpadding="0" bgcolor="#999999">
    <tr>
      <td width="100%">
        <table width="100%" border="0"
               cellspacing="1" cellpadding="1" bgcolor="#336699">
          <tr>
            <td height="20" align="left" valign="middle">
              <font color="#FFFFFF" face="Arial, Helvetica, sans-serif">
              &nbsp;Environment Variables
              </font></td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td width="100%">
        <table width="100%" border="0" cellspacing="1" cellpadding="1" bgcolor="#CCCCCC">
<% int numberOfEnvVars = 10;
    for (int envVarIndex = 0; envVarIndex < numberOfEnvVars; ++envVarIndex) { %>
<tr>
  <td width="5">
    <font size="-2" face="Arial, Helvetica, sans-serif">
      <%=envVarIndex+1%>
    </font>
  </td>
  <td bgcolor="#FFFFFF">
    <font size="-2" face="Arial, Helvetica, sans-serif">
      &nbsp;Name:&nbsp;
    </font>
    <font size="-1" face="Arial, Helvetica, sans-serif">
      <input type="text" name="environmentVariableName<%=envVarIndex%>" size="15" value=""></input>
    </font>
    <font size="-2" face="Arial, Helvetica, sans-serif">
      &nbsp;Value:&nbsp;
    </font>
    <font size="-1" face="Arial, Helvetica, sans-serif">
      <input type="text" name="environmentVariableValue<%=envVarIndex%>" size="15" value=""></input>
    </font>
  </td>
</tr>
<% } %>
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
              &nbsp;<input type="button" name="JobResources" value="Next >>"
              &nbsp;<input type="button" name="JobResources" value="Next >>"
                onClick="javascript:GrmsJobResources_onClick()">&nbsp;
              &nbsp;<input type="button" name="JobCancel" value="Cancel"
                onClick="javascript:GrmsJobCancel_onClick()">&nbsp;
              </font></td>
          </tr>
        </table>
      </td>
    </tr>
<%-- List Box of Environment Variables
        </table>
      </td>
    </tr>
    <tr>
      <td width="100%">
        <table width="100%" border="0" cellspacing="1" cellpadding="1" bgcolor="#CCCCCC">
          <tr>
            <td>
              <font size="-1" face="Arial, Helvetica, sans-serif">
                <select name="environmentVariables" size="5">
                  <option value="">--------------------------------------------------------------------------</option>
                </select>
              </font>
            </td>
          </tr>
          <tr>
            <td align="left" valign="middle">
              <font size="-2" face="Arial, Helvetica, sans-serif">
                &nbsp;<input type="button" name="AddEnvVar" value="Add"
                  onClick="javascript:GridLabResmgmtJobAddEnvVar_onClick()">&nbsp;
                &nbsp;<input type="button" name="EditEnvVar" value="Edit"
                  onClick="javascript:GridLabResmgmtJobEditEnvVar_onClick()">&nbsp;
                &nbsp;<input type="button" name="RemoveAEnvVar" value="Remove"
                  onClick="javascript:GridLabResmgmtJobRemoveEnvVar_onClick()">&nbsp;
              </font>
            </td>
          </tr>
        </table>
      </td>
    </tr>
 --%>
<%-- Single entry
    <tr>
      <td width="100%">
        <table width="100%" border="0" cellspacing="1" cellpadding="1" bgcolor="#CCCCCC">
<tr>
  <td width="100">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;Variable Name:&nbsp;
    </font>
  </td>
  <td bgcolor="#FFFFFF">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      <input type="text" name="name" size="15"
        value="<%=name%>"></input>
    </font>
  </td>
</tr>
<tr>
  <td width="100">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;Variable Value:&nbsp;
    </font>
  </td>
  <td bgcolor="#FFFFFF">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      <input type="text" name="value" size="15"
        value="<%=value%>"></input>
    </font>
  </td>
</tr>
--%>
  </table>
  <script language="JavaScript">

    function GrmsJobEnvironment_onValidate() {
      var isValid = true;
      return isValid;
    }

    function GrmsJobResources_onClick() {
      if (GrmsJobEnvironment_onValidate()) {
        document.GridLabResmgmtJob.moduleRequest.value="gridlab.resmgmt.GrmsJobResources";
        document.GridLabResmgmtJob.submit();
      }
    }

    function GrmsJobCancel_onClick() {
      if (confirm("Are you sure you want to cancel this operation?")) {
        document.GrmsJobApplication.moduleRequest.value="gridlab.resmgmt.Jobs";
        document.GrmsJobApplication.submit();
      }
    }

  </script>
