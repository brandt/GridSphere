/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets.grid.job;

import org.gridlab.gridsphere.provider.PortletBean;
import org.gridlab.gridsphere.provider.PortletActionHandler;
import org.gridlab.gridsphere.services.grid.job.*;
import org.gridlab.gridsphere.services.grid.job.impl.grms.*;
import org.gridlab.gridsphere.services.grid.security.credential.Credential;
import org.gridlab.gridsphere.portlet.*;

import java.net.MalformedURLException;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

public class JobManagerBean extends PortletActionHandler {

    // User job pages
    public static final String PAGE_USER_JOB_LIST = "/jsp/job/userJobList.jsp";
    public static final String PAGE_USER_JOB_VIEW = "/jsp/job/userJobView.jsp";
    public static final String PAGE_USER_JOB_EDIT = "/jsp/job/userJobEditApplication.jsp";
    public static final String PAGE_USER_JOB_APPLICATION_EDIT = "/jsp/job/userJobEditApplication.jsp";
    public static final String PAGE_USER_JOB_RESOURCES_EDIT = "/jsp/job/userJobEditResources.jsp";
    public static final String PAGE_USER_JOB_EDIT_VERIFY = "/jsp/job/userJobEditVerify.jsp";
    public static final String PAGE_USER_JOB_MIGRATE = "/jsp/job/userJobMigrate.jsp";
    public static final String PAGE_USER_JOB_SPEC_EDIT = "/jsp/job/userJobEditSpecification.jsp";
    public static final String PAGE_USER_JOB_SPEC_EDIT_VERIFY = "/jsp/job/userJobSpecEditVerify.jsp";
    public static final String PAGE_USER_JOB_SPEC_EDIT_SUBMIT = "/jsp/job/userJobSpecEditSubmit.jsp";
    public static final String PAGE_USER_JOB_SPEC_EDIT_CANCEL = "/jsp/job/userJobSpecEditCancel.jsp";

    // Job manager variables
    private JobManagerService jobManagerService = null;
    private String jobManagerDescription = null;

    // User job variables
    private List userJobList = null;
    private Job userJob = null;

    /******************************************
     * Portlet bean methods
     ******************************************/

    public JobManagerBean() {
    }

    public void init(PortletConfig config, PortletRequest request, PortletResponse response)
            throws PortletException {
        super.init(config, request, response);
        initPage();
        initServices();
    }

    private void initPage() {
        setTitle("Job Manager Portlet");
        setPage(PAGE_USER_JOB_LIST);
    }

    private void initServices()
            throws PortletException {
        this.log.debug("Entering initServices()");
        this.jobManagerService = (JobManagerService) getPortletService(JobManagerService.class);
        this.log.debug("Exiting initServices()");
    }

    public void doDefaultViewAction()
            throws PortletException {
        doListUserJob();
    }

    /******************************************
     * Job manager methods
     ******************************************/

    public void loadJobManagerDescription()
            throws PortletException {
        try {
            User user = getPortletUser();
            this.jobManagerDescription = this.jobManagerService.getJobManagerDescription(user);
        } catch (Exception e) {
            this.log.error("Error getting job manager description", e);
            this.jobManagerDescription = "";
            throw new PortletException(e.getMessage());
        }
    }

    public String getJobManagerDescription()
            throws JobManagerException {
        return this.jobManagerDescription;
    }

    /******************************************
     * User job methods
     ******************************************/

    public void doListUserJob()
            throws PortletException {
        loadUserJobList();
        setTitle("Job Manager: List Jobs");
        setPage(PAGE_USER_JOB_LIST);
    }

    public void doViewUserJob()
            throws PortletException {
        loadUserJob();
        setTitle("Job Manager: View Job");
        setPage(PAGE_USER_JOB_VIEW);
    }

    public void doNewUserJob()
            throws PortletException {
        setTitle("Job Manager: New Job");
        setPage(PAGE_USER_JOB_EDIT);
    }

    public void doStageUserJob()
            throws PortletException {
        loadUserJob();
        setTitle("Job Manager: Stage Job");
        setPage(PAGE_USER_JOB_EDIT);
    }

    public void doEditUserJobApplication()
            throws PortletException {
        loadUserJob();
        editUserJob();
        validateUserJobApplication();
        setTitle("Job Manager: Edit Job Application");
        setPage(PAGE_USER_JOB_APPLICATION_EDIT);
    }

    public void doEditUserJobResources()
            throws PortletException {
        loadUserJob();
        editUserJob();
        try {
            setTitle("Job Manager: Edit Job Resources");
            validateUserJobApplication();
            setPage(PAGE_USER_JOB_RESOURCES_EDIT);
        } catch (Exception e) {
            setTitle("Job Manager: Edit Job Application");
            setPage(PAGE_USER_JOB_APPLICATION_EDIT);
        }
    }

    public void doVerifyEditUserJob()
            throws PortletException {
        loadUserJob();
        editUserJob();
        try {
            validateUserJobResources();
            setTitle("Job Manager: Verify Job");
            setPage(PAGE_USER_JOB_EDIT_VERIFY);
        } catch (Exception e) {
            setTitle("Job Manager: Edit Job Resources");
            setPage(PAGE_USER_JOB_RESOURCES_EDIT);
        }
    }

    public void doSubmitEditUserJob()
            throws PortletException {
        loadUserJob();
        editUserJob();
        submitUserJob();
        setTitle("Job Manager: Submit Job");
        setPage(PAGE_USER_JOB_RESOURCES_EDIT);
    }

    public void doCancelEditUserJob()
            throws PortletException {
        loadUserJobList();
        setTitle("Job Manager: List Jobs");
        setPage(PAGE_USER_JOB_LIST);
    }

    public void doMigrateUserJob()
            throws PortletException {
        loadUserJob();
        setTitle("Job Manager: Migrate Job");
        setPage(PAGE_USER_JOB_MIGRATE);
    }

    public void doSubmitMigrateUserJob()
            throws PortletException {
        loadUserJob();
        migrateUserJob();
        setTitle("Job Manager: View Job");
        setPage(PAGE_USER_JOB_VIEW);
    }

    public void loadUserJobList() {
        try {
            User user = getPortletUser();
            this.userJobList = jobManagerService.getJobList(user);
        } catch (JobManagerException e) {
            this.log.error("Unable to load portlet user job list", e);
            this.userJobList = new Vector();
        }
    }

    public void validateUserJobApplication()
            throws PortletException {
    }

    public void validateUserJobResources()
            throws PortletException {
    }

    public void loadUserJob() {
        String jobID = getParameter("jobID");
        if (!jobID.equals("")) {
            User user = getPortletUser();
            try {
                this.userJob = jobManagerService.getJob(user, jobID);
            } catch (Exception e) {
                this.log.error("Unable to load portlet user job list", e);
                this.userJobList = new Vector();
            }
        }
    }

    public void editUserJob() {
    }

    public GrmsJob submitUserJob()
            throws PortletException {
        User user = getPortletUser();
        try {
            GrmsJobSpecification jobSpecification = getJobSpecification();
            return (GrmsJob) jobManagerService.submitJob(user, jobSpecification);
        } catch (Exception e) {
            this.log.error("Unable to submit user job", e);
            throw new PortletException(e.getMessage());
        }
    }

    public void migrateUserJob()
            throws PortletException {
        User user = getPortletUser();
        try {
            Job job = getUserJob();
            jobManagerService.migrateJob(user, job);
        } catch (Exception e) {
            this.log.error("Unable to migrate user job", e);
            throw new PortletException(e.getMessage());
        }
    }
    public List getUserJobList() {
        return this.userJobList;
    }

    public GrmsJob getUserJob()
            throws JobNotFoundException, JobManagerException {
        return (GrmsJob) userJob;
    }

    public GrmsJobSpecification getJobSpecification()
            throws JobSpecificationException, MalformedURLException {
        GrmsJobSpecification jobSpecification = null;
        String jobSpecificationText = getParameter("jobSpecification");
        if (jobSpecificationText == null) {
            jobSpecification = new GrmsJobSpecification();
            SingleJobDescription jobDescription = buildJobDescription();
            jobSpecification.setJobDescription(jobDescription);
        } else {
            jobSpecification = GrmsJobSpecification.unmarshal(jobSpecificationText);
        }
        return jobSpecification;
    }

    public SingleJobDescription getJobDescription()
            throws JobSpecificationException, MalformedURLException {
        GrmsJobSpecification jobSpecification = null;
        SingleJobDescription jobDescription = null;
        String jobSpecificationText = getParameter("jobSpecification");
        if (jobSpecificationText == null) {
            jobDescription = buildJobDescription();
        } else {
            jobSpecification = GrmsJobSpecification.unmarshal(jobSpecificationText);
            jobDescription = (SingleJobDescription) jobSpecification.getJobDescription();
        }
        return jobDescription;
    }

    public ApplicationDescription getApplicationDescription()
            throws JobSpecificationException, MalformedURLException {
        ApplicationDescription applicationDescription = null;
        String jobSpecificationText = getParameter("jobSpecification");
        if (jobSpecificationText == null) {
            applicationDescription = buildApplicationDescription();
        } else {
            GrmsJobSpecification jobSpecification =
                    GrmsJobSpecification.unmarshal(jobSpecificationText);
            SingleJobDescription jobDescription =
                    (SingleJobDescription) jobSpecification.getJobDescription();
            applicationDescription = jobDescription.getApplicationDescription();
        }
        return applicationDescription;
    }

    public ResourceDescription getResourceDescription()
            throws JobSpecificationException {
        ResourceDescription resourceDescription = null;
        String jobSpecificationText = getParameter("jobSpecification");
        if (jobSpecificationText == null) {
            resourceDescription = buildResourceDescription();
        } else {
            GrmsJobSpecification jobSpecification =
                    GrmsJobSpecification.unmarshal(jobSpecificationText);
            SingleJobDescription jobDescription =
                    (SingleJobDescription) jobSpecification.getJobDescription();
            resourceDescription = jobDescription.getResourceDescription();
        }
        return resourceDescription;
    }

    private SingleJobDescription buildJobDescription()
            throws JobSpecificationException, MalformedURLException {
        ResourceDescription resourceDescription =
                buildResourceDescription();
        ApplicationDescription applicationDescription =
                buildApplicationDescription();
        SingleJobDescription jobDescription = new SingleJobDescription();
        jobDescription.setResourceDescription(resourceDescription);
        jobDescription.setApplicationDescription(applicationDescription);
        return jobDescription;
    }

    private ResourceDescription buildResourceDescription()
            throws JobSpecificationException {
        ResourceDescription resourceDescription = new ResourceDescription();
        resourceDescription.setHostName(getParameter("hostName").trim());
        resourceDescription.setJobScheduler(getParameter("jobScheduler").trim());
        resourceDescription.setMemory(getParameterAsLng("memory"));
        resourceDescription.setCpuCount(getParameterAsLng("cpuCount"));
        resourceDescription.setCpuSpeed(getParameterAsLng("cpuSpeed"));
        resourceDescription.setOsType(getParameter("osType").trim());
        resourceDescription.setOsName(getParameter("osName").trim());
        resourceDescription.setOsVersion(getParameter("osVersion").trim());
        resourceDescription.setOsRelease(getParameter("osRelease").trim());
        return resourceDescription;
    }

    private ApplicationDescription buildApplicationDescription()
            throws JobSpecificationException, MalformedURLException {
        ApplicationDescription applicationDescription = new ApplicationDescription();
        applicationDescription = new ApplicationDescription();
        String executable = getParameter("executable").trim();
        if (executable.length() == 0) {
            String message = "No Executable specified for application!";
            throw new JobSpecificationException(message);
        }
        applicationDescription.setExecutable(executable);
        applicationDescription.setStdout(getParameter("stdout").trim());
        applicationDescription.setStderr(getParameter("stderr").trim());
        Arguments arguments = buildArguments();
        applicationDescription.setArguments(arguments);
        Environment environment = buildEnvironment();
        applicationDescription.setEnvironment(environment);
        return applicationDescription;
    }

    private Arguments buildArguments() {
        Arguments arguments = new Arguments();
        String argumentsText = getParameter("arguments");
        if (argumentsText != null) {
            StringTokenizer argumentTokens = new StringTokenizer(argumentsText);
            while (argumentTokens.hasMoreTokens()) {
                String value = argumentTokens.nextToken();
                Argument argument = new Argument();
                argument.setValue(value);
                arguments.addArgument(argument);
            }
        }
        return arguments;
    }

    private Environment buildEnvironment()
            throws JobSpecificationException {
        Environment environment = new Environment();
        String environmentText = getParameter("environment");
        if (environmentText != null) {
            StringTokenizer environmentTokens = new StringTokenizer(environmentText);
            while (environmentTokens.hasMoreTokens()) {
                String variableText = environmentTokens.nextToken();
                StringTokenizer variableTokens = new StringTokenizer(variableText, "=");
                if (variableTokens.countTokens() == 0) {
                    String message = "Environment variable ["
                            + variableText
                            + "] not properly assigned!";
                    throw new JobSpecificationException(message);
                } else {
                    String variableName = variableTokens.nextToken();
                    String variableValue = variableTokens.nextToken();
                    EnvironmentVariable variable = new EnvironmentVariable();
                    variable.setName(variableName);
                    variable.setValue(variableValue);
                    environment.putVariable(variable);
                }
            }
        }
        return environment;
    }
}
