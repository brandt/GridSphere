<%@ page import="org.gridlab.gridsphere.portlet.User,
                 org.gridlab.gridsphere.portlet.PortletURI" %>
<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<jsp:useBean id="jobManagerBean"
             class="org.gridlab.gridsphere.portlets.grid.job.JobManagerBean"
             scope="request"/>
<gs:form action="doEditUserJobApplication">
<input type="hidden" name="hostName" value="<%=jobManagerBean.getParameter("hostName")%>">
<input type="hidden" name="jobScheduler" value="<%=jobManagerBean.getParameter("jobScheduler")%>">
<input type="hidden" name="memory" value="<%=jobManagerBean.getParameter("memory")%>">
<input type="hidden" name="cpuCount" value="<%=jobManagerBean.getParameter("cpuCount")%>">
<%--
<input type="hidden" name="osType" value="<%=jobManagerBean.getParameter("osType")%>">
<input type="hidden" name="osName" value="<%=jobManagerBean.getParameter("osName")%>">
<input type="hidden" name="osVersion" value="<%=jobManagerBean.getParameter("osVersion")%>">
<input type="hidden" name="osRelease" value="<%=jobManagerBean.getParameter("osRelease")%>">
--%>
<table class="portlet-pane" cellspacing="1" width="!00%">
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-message">
              Step 1: Edit Job Application
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-actions">
            <gs:submit name="doEditUserJobResources" value="Next &gt;&gt;"/>
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
          <td class="portlet-frame-input" width="*">
             <input type="text"
                    name="executable"
                    value="<%=jobManagerBean.getParameter("executable")%>"></input>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             Stdout:&nbsp;
          </td>
          <td class="portlet-frame-input">
            <select name="stdoutOptions"
                    onChange="javascript:GridJobStdoutOptions_onChange()">
             <option value="">&lt;Portal&gt;</option>
             <option value="file://">&lt;File&gt;</option>
            </select>
            &nbsp;<input type="text" size="25" name="stdout"
                         value="<%=jobManagerBean.getParameter("stdout")%>"
                         onKeyPress="javascript:GridJobStdout_onKeyPress()">
                  </input>
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-label">
             Stderr:&nbsp;
          </td>
          <td class="portlet-frame-input">
            <select name="stderrOptions"
                    onChange="javascript:GridJobStderrOptions_onChange()">
             <option value="">&lt;Portal&gt;</option>
             <option value="file://">&lt;File&gt;</option>
            </select>
            &nbsp;<input type="text" size="25" name="stdout"
                         value="<%=jobManagerBean.getParameter("stderr")%>"
                         onKeyPress="javascript:GridJobStderr_onKeyPress()">
                  </input>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-label">
             Arguments:&nbsp;
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-input">
             <textarea name="arguments" cols="50" rows="4">
               <%=jobManagerBean.getParameter("arguments")%>
             </textarea>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-label">
             Environment:&nbsp;
          </td>
        </tr>
        <tr>
          <td class="portlet-frame-input">
             <textarea name="environment" cols="50" rows="4">
               <%=jobManagerBean.getParameter("environment")%>
             </textarea>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</gs:form>
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

    function GridJobExecutableBrowse_onClick() {
      FileBrowseIntoTextInput(document.GridJobApplication.executable);
    }

    function GridJobExecutableUpload_onClick() {
      FileUploadIntoTextInput(document.GridJobApplication.executable);
    }

    function GridJobArgumentsFileBrowse_onClick() {
      FileBrowseIntoTextArea(document.GridJobApplication.executable);
    }

    function GridJobArgumentsFileUpload_onClick() {
      FileUploadIntoTextArea(document.GridJobApplication.executable);
    }

    function GridJobOutputOptions_onChange(options, field) {
      if (options.selectedIndex == 0) {
        field.value = "";
      }
    }

    function GridJobOutputField_onKeyPress(options, field) {
      if (options.selectedIndex == 0) {
        options.selectedIndex = 1;
      } else if (field.value.length == 0) {
        options.selectedIndex = 0;
      }
    }

    function GridJobStdoutOptions_onChange() {
      GridJobOutputOptions_onChange(document.GridJobApplication.stdoutOptions,
                                    document.GridJobApplication.stdout);
    }

    function GridJobStdout_onKeyPress() {
      GridJobOutputField_onKeyPress(document.GridJobApplication.stdoutOptions,
                                    document.GridJobApplication.stdout);
    }

    function GridJobStderrOptions_onChange() {
      GridJobOutputOptions_onChange(document.GridJobApplication.stderrOptions,
                                    document.GridJobApplication.stderr);
    }

    function GridJobStderr_onKeyPress() {
      GridJobOutputField_onKeyPress(document.GridJobApplication.stderrOptions,
                                    document.GridJobApplication.stderr);
    }
  </script>
