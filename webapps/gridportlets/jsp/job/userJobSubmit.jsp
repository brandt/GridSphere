<%@ page language="java"%>
<%-- Set error page --%>
<%@ page errorPage="/jsp/orbiter/pages/error.jsp"%>
<%@ page import="org.gridlab.gat.jobs.Job,
                 org.gridlab.gat.jobs.JobSpecification,
                 org.gridlab.gat.jobs.JobSpecificationException,
                 org.gridlab.gat.jobs.JobManagerException"%>
<%-- Instantiate module bean --%>
<jsp:useBean id="module" scope="page"
  class="org.gridlab.portal.pages.GrmsJobManagerPageBean"/>
<%  module.setPageContext(pageContext); %>
<%  System.out.println("GrmsJobSumbit: Entering");
    String nextPage = null;
    boolean errorFlag = false;
    String errorMessage = "";
    try {
        Job job = module.submitJob();
        nextPage = "GrmsJobView.jsp?id=" + job.getId();
    } catch (JobSpecificationException e) {
        System.err.println(e);
        nextPage = "GrmsJobVerify.jsp";
        errorFlag = true;
        errorMessage = e.getMessage();
    } catch (JobManagerException e) {
        System.err.println(e);
        nextPage = "GrmsJobVerify.jsp";
        errorFlag = true;
        errorMessage = e.getMessage();
    }
    if (errorFlag) { %>
<table width="90%" border="0" cellspacing="2" cellpadding="0" bgcolor="#FFFFFF">
    <tr>
      <td width="100%">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="20" align="left" valign="middle">
              <font face="Arial, Helvetica, sans-serif">
                &nbsp;An error occured while attempting to submit your job
              </font>
            </td>
          </tr>
          <tr>
            <td height="20" align="left" valign="middle">
              <font size="-1" color="DARKRED" face="Arial, Helvetica, sans-serif">
                &nbsp;<%=errorMessage%>
              </font>
            </td>
          </tr>
        </table>
      </td>
    </tr>
</table>
</form>
<%  } %>
<jsp:include page="<%=nextPage%>" flush="true"/>
