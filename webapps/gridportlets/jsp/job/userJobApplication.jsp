<%@ page language="java"%>
<%-- Set error page --%>
<%@ page errorPage="/jsp/orbiter/pages/error.jsp"%>
<%-- Instantiate module bean --%>
<jsp:useBean id="module" scope="page"
  class="org.gridlab.portal.pages.GrmsJobManagerPageBean"/>
<% module.setPageContext(pageContext); %>
<!-- Begin presentation -->
<form method="POST" name="GrmsJobApplication" action="servlet/orbiter">
  <input type="hidden" name="moduleRequest" value="gridlab.resmgmt.GrmsJobApplication">
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
              &nbsp;Step 1: Specify your application
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
  <td width="100">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;Executable:&nbsp;
    </font>
  </td>
  <td bgcolor="#FFFFFF">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;<input type="text" size="40" name="executable"
                   value="<%=module.getParameter("executable")%>">
<!-- TEMPORARILY COMMENTED OUT
      <input type="button" name="executableBrowse" value="Browse"
        onClick="GrmsJobExecutableBrowse_onClick()">
      <input type="button" name="executableUpload" value="Upload"
        onClick="GrmsJobExecutableUpload_onClick()">
-->
    </font>
  </td>
</tr>
<tr>
  <td width="100">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;Stdout:&nbsp;
    </font>
  </td>
  <td bgcolor="#FFFFFF">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;<select name="stdoutOptions"
              onChange="javascript:GrmsJobStdoutOptions_onChange()">
        <option value="">&lt;Portal&gt;</option>
        <option value="file://">&lt;File&gt;</option>
      </select>
      &nbsp;<input type="text" size="25" name="stdout"
              value="<%=module.getParameter("stdout")%>"
              onKeyPress="javascript:GrmsJobStdout_onKeyPress()">
            </input>
    </font>
  </td>
</tr>
<tr>
  <td width="100">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;Stderr:&nbsp;
    </font>
  </td>
  <td bgcolor="#FFFFFF">
    <font size="-1" face="Arial, Helvetica, sans-serif">
      &nbsp;<select name="stderrOptions"
              onChange="javascript:GrmsJobStderrOptions_onChange()">
        <option value="">&lt;Portal&gt;</option>
        <option value="file://">&lt;File&gt;</option>
      </select>
      &nbsp;<input type="text" size="25" name="stderr"
               value="<%=module.getParameter("stderr")%>"
              onKeyPress="javascript:GrmsJobStderr_onKeyPress()">
            </input>
    </font>
  </td>
</tr>
        </table>
      </td>
    </tr>
    <tr>
      <td bgcolor="#999999" width="100%">
        <font size="-1" face="Arial, Helvetica, sans-serif">
          &nbsp;Arguments
        </font>
      </td>
    </tr>
    <tr>
      <td width="100%">
        <table width="100%" border="0" cellspacing="1" cellpadding="1" bgcolor="#FFFFFF">
          <tr>
            <td>
              <font size="-1" face="Arial, Helvetica, sans-serif">
<textarea name="arguments" cols="50" rows="4"><%=module.getParameter("arguments")%></textarea>
              </font>
            </td>
          </tr>
          <tr>
            <td bgcolor="#FFFFFF">
              <font size="-1" face="Arial, Helvetica, sans-serif">
<!-- TEMPORARILY COMMENTED OUT
      <input type="button" name="browseFileArgument" value="Browse File Argument"
        onClick="GrmsJobArgumentsFileBrowse_onClick()">&nbsp;&nbsp;
      <input type="button" name="uploadFileArugment" value="Upload File Argument"
        onClick="GrmsJobArgumentsFileUpload_onClick()">&nbsp;&nbsp;
-->
              </font>
            </td>
          </tr>
        </table>
      </td>
    </tr>

    <tr>
      <td bgcolor="#999999" width="100%">
        <font size="-1" face="Arial, Helvetica, sans-serif">
          &nbsp;Environment
        </font>
      </td>
    </tr>
    <tr>
      <td width="100%">
        <table width="100%" border="0" cellspacing="1" cellpadding="1" bgcolor="#FFFFFF">
          <tr>
            <td>
              <font size="-1" face="Arial, Helvetica, sans-serif">
<textarea name="environment" cols="50" rows="4"><%=module.getParameter("environment")%></textarea>
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

    function FileBrowseIntoTextArea(area) {
      area.text = "file://localhost/~russell/";
    }

    function FileBrowseIntoTextInput(input) {
      input.value = "file://localhost/~russell/";
    }

    function FileUploadIntoTextArea(area) {
      area.text = "file://localhost/~russell/";
    }

    function FileUploadIntoTextInput(input) {
      input.value = "file://localhost/~russell/";
    }

    function GrmsJobExecutableBrowse_onClick() {
      FileBrowseIntoTextInput(document.GrmsJobApplication.executable);
    }

    function GrmsJobExecutableUpload_onClick() {
      FileUploadIntoTextInput(document.GrmsJobApplication.executable);
    }

    function GrmsJobArgumentsFileBrowse_onClick() {
      FileBrowseIntoTextArea(document.GrmsJobApplication.executable);
    }

    function GrmsJobArgumentsFileUpload_onClick() {
      FileUploadIntoTextArea(document.GrmsJobApplication.executable);
    }

    function GrmsJobOutputOptions_onChange(options, field) {
      if (options.selectedIndex == 0) {
        field.value = "";
      }
    }

    function GrmsJobOutputField_onKeyPress(options, field) {
      if (options.selectedIndex == 0) {
        options.selectedIndex = 1;
      } else if (field.value.length == 0) {
        options.selectedIndex = 0;
      }
    }

    function GrmsJobStdoutOptions_onChange() {
      GrmsJobOutputOptions_onChange(document.GrmsJobApplication.stdoutOptions,
                                    document.GrmsJobApplication.stdout);
    }

    function GrmsJobStdout_onKeyPress() {
      GrmsJobOutputField_onKeyPress(document.GrmsJobApplication.stdoutOptions,
                                    document.GrmsJobApplication.stdout);
    }

    function GrmsJobStderrOptions_onChange() {
      GrmsJobOutputOptions_onChange(document.GrmsJobApplication.stderrOptions,
                                    document.GrmsJobApplication.stderr);
    }

    function GrmsJobStderr_onKeyPress() {
      GrmsJobOutputField_onKeyPress(document.GrmsJobApplication.stderrOptions,
                                    document.GrmsJobApplication.stderr);
    }

    function GrmsJobApplication_validate() {
      var isValid = true;
      if (document.GrmsJobApplication.executable.value == "") {
        isValid = false;
        alert("Please specify an executable file.");
        document.GrmsJobApplication.executable.focus();
      }

      return isValid;
    }

    function GrmsJobNext_onClick() {
      if (GrmsJobApplication_validate()) {
        document.GrmsJobApplication.moduleRequest.value="gridlab.resmgmt.GrmsJobResources";
        document.GrmsJobApplication.submit();
      }
    }

    function GrmsJobCancel_onClick() {
      if (confirm("Are you sure you want to cancel this operation?")) {
        document.GrmsJobApplication.moduleRequest.value="gridlab.resmgmt.Jobs";
        document.GrmsJobApplication.submit();
      }
    }

  </script>
</form>
