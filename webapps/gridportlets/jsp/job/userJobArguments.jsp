<%@ page language="java"%>
<%-- Set error page --%>
<%@ page errorPage="/jsp/orbiter/pages/error.jsp"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="org.gridlab.resmgmt.grms.GrmsJob"%>
<%@ page import="org.gridlab.resmgmt.grms.Argument"%>
<%@ page import="org.gridlab.resmgmt.grms.FileHandle"%>
<%@ page import="org.gridlab.resmgmt.grms.FileArgument"%>
<%@ page import="org.gridlab.resmgmt.grms.ValueArgument"%>
<%-- Instantiate module bean --%>
<jsp:useBean id="module" scope="page"
  class="org.gridlab.portal.pages.GrmsJobManagerPageBean"/>
<% module.setPageContext(pageContext); %>
<!-- Begin presentation -->
<form method="POST" name="GrmsJobArguments" action="servlet/orbiter">
  <input type="hidden" name="moduleRequest" value="gridlab.resmgmt.GrmsJobArguments">
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
              &nbsp;Arguments
              </font></td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td width="100%">
        <table width="100%" border="0" cellspacing="1" cellpadding="1" bgcolor="#CCCCCC">
<% int numberOfArguments = 5;
    for (int argumentIndex = 0; argumentIndex < numberOfArguments; ++argumentIndex) { %>
<tr>
  <td width="5">
    <font size="-2" face="Arial, Helvetica, sans-serif">
      <%=argumentIndex+1%>
    </font>
  </td>
  <td bgcolor="#FFFFFF">
    <font size="-2" face="Arial, Helvetica, sans-serif">
      <input type="radio" name="argumentType<%=argumentIndex%>" value="<%=Argument.VALUE%>">Value</input>
    </font>
    <font size="-1" face="Arial, Helvetica, sans-serif">
      <input type="text" name="valueArgument<%=argumentIndex%>" size="10" value=""></input>
    </font>
  </td>
  <td bgcolor="#FFFFFF">
    <font size="-2" face="Arial, Helvetica, sans-serif">
      <input type="radio" name="argumentType<%=argumentIndex%>" value="<%=Argument.FILE%>">File</input>&nbsp;&nbsp;
    </font>
    <font size="-1" face="Arial, Helvetica, sans-serif">
      <input type="text" name="fileArgument<%=argumentIndex%>" size="15" value=""></input>
    </font>
    <font size="-2" face="Arial, Helvetica, sans-serif">
      <input type="button" name="fileArgumentBrowse<%=argumentIndex%>" value="Browse"
        onClick="GridLabResmgmtJobFileBrowse_onClick(fileArgument<%=argumentIndex%>)">&nbsp;&nbsp;
      <select name="fileArgumentType<%=argumentIndex%>">
        <option value="<%=FileHandle.INPUT%>">Input</option>
        <option value="<%=FileHandle.OUTPUT%>">Output</option>
        <option value="<%=FileHandle.INOUT%>">In/Out</option>
      </select>
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
              &nbsp;<input type="button" name="JobEnvironment" value="Next >>"
                onClick="javascript:GrmsJobEnvironment_onClick()">&nbsp;
              &nbsp;<input type="button" name="JobCancel" value="Cancel"
                onClick="javascript:GrmsJobCancel_onClick()">&nbsp;
              </font></td>
          </tr>
        </table>
      </td>
    </tr>
<%-- List Box of Arguments
    <tr>
      <td width="100%">
        <table width="100%" border="0" cellspacing="1" cellpadding="1" bgcolor="#CCCCCC">
          <tr>
            <td>
              <font size="-1" face="Arial, Helvetica, sans-serif">
                <select name="arguments" size="5">
                  <option value="">--------------------------------------------------------------------------</option>
                </select>
              </font>
            </td>
          </tr>
          <tr>
            <td align="left" valign="middle">
              <font size="-2" face="Arial, Helvetica, sans-serif">
                &nbsp;<input type="button" name="AddArgument" value="Add"
                  onClick="javascript:GridLabResmgmtJobAddArgument_onClick()">&nbsp;
                &nbsp;<input type="button" name="EditArgument" value="Edit"
                  onClick="javascript:GridLabResmgmtJobEditArgument_onClick()">&nbsp;
                &nbsp;<input type="button" name="RemoveArgument" value="Remove"
                  onClick="javascript:GridLabResmgmtJobRemoveArgument_onClick()">&nbsp;
              </font>
            </td>
          </tr>
--%>
<%-- Single File Argument Entry
    <tr>
      <td width="100%">
        <table width="100%" border="0"
               cellspacing="1" cellpadding="1" bgcolor="#336699">
          <tr>
            <td height="20" align="left" valign="middle">
              <font color="#FFFFFF" face="Arial, Helvetica, sans-serif">
              &nbsp;File Argument
              </font></td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td width="100%">
        <table width="100%" border="0" cellspacing="1" cellpadding="1" bgcolor="#CCCCCC">
<tr>
  <td width="100">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;File Type:&nbsp;
    </font>
  </td>
  <td bgcolor="#FFFFFF">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      <select name="fileArgumentType">
        <option value="<%=FileArgument.INPUT%>">Input</option>
        <option value="<%=FileArgument.OUTPUT%>">Output</option>
        <option value="<%=FileArgument.INOUT%>">In/Out</option>
      </select>
    </font>
  </td>
</tr>
<tr>
  <td width="100">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;File Url:&nbsp;
    </font>
  </td>
  <td bgcolor="#FFFFFF">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;<input type="text" size="30" name="fileArgumentUrl" value=""></input>
      <input type="button" name="fileBrowse" value="Browse"
        onClick="GridLabDataFileBrowse_onClick(fileArgumentUrl)">&nbsp;&nbsp;
    </font>
  </td>
</tr>
--%>
  </table>
  <script language="JavaScript">

    function GrmsJobArguments_onValidate() {
      var isValid = true;
      return isValid;
    }

    function GrmsJobEnvironment_onClick() {
      if (GrmsJobArguments_onValidate()) {
        document.GridLabResmgmtJob.moduleRequest.value="gridlab.resmgmt.GrmsJobEnvironment";
        document.GridLabResmgmtJob.submit();
      }
    }

    function GrmsJobCancel_onClick() {
      if (confirm("Are you sure you want to cancel this operation?")) {
        document.GridLabResmgmtJob.moduleRequest.value="gridlab.resmgmt.Jobs";
        document.GridLabResmgmtJob.submit();
      }
    }

  </script>
</form>
