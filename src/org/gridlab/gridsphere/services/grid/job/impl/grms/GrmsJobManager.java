/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.grid.job.impl.grms;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.rmi.Remote;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.xml.rpc.holders.IntHolder;
import javax.xml.rpc.holders.StringHolder;

import org.apache.axis.SimpleTargetedChain;
import org.apache.axis.client.Service;
import org.apache.axis.client.Stub;
import org.apache.axis.configuration.SimpleProvider;

import org.globus.axis.transport.GSIHTTPSender;
import org.globus.axis.transport.GSIHTTPTransport;
import org.globus.axis.util.Util;
import org.globus.gsi.gssapi.auth.NoAuthorization;
import org.globus.gsi.gssapi.GlobusGSSCredentialImpl;

import org.gridlab.resmgmt.GsiScenarioBroker;
import org.gridlab.resmgmt.GsiScenarioBrokerServiceLocator;
import org.gridlab.resmgmt.JobInformation;
import org.gridlab.resmgmt.holders.ArrayOf_Xsd_StringHolder;
import org.gridlab.resmgmt.holders.JobInformationHolder;

import org.gridlab.gridsphere.services.grid.data.file.FileHandle;
import org.gridlab.gridsphere.services.grid.data.gass.GassManagerService;
import org.gridlab.gridsphere.services.grid.data.gass.impl.GridSphereGassManager;

import org.gridlab.gridsphere.services.grid.job.*;
import org.gridlab.gridsphere.services.grid.job.impl.grms.GrmsJobMonitor;
import org.gridlab.gridsphere.services.grid.system.Local;
import org.gridlab.gridsphere.services.grid.security.credential.impl.GlobusCredentialManager;
import org.gridlab.gridsphere.services.grid.security.credential.impl.GlobusCredential;
import org.gridlab.gridsphere.services.grid.security.credential.Credential;
import org.gridlab.gridsphere.services.grid.security.credential.CredentialException;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.PortletLog;

import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.ietf.jgss.GSSCredential;
import org.gridforum.jgss.ExtendedGSSManager;

public class GrmsJobManager implements JobManagerService {

    public static final String DEFAULT_RESMGMT_SERVICE_URL
            = "httpg://rage1.man.poznan.pl:8443/axis/services/gsiScenarioBroker";

    private static GrmsJobManager _instance = null;
    private static PortletLog _logger = SportletLog.getInstance(GrmsJobManager.class);
    private static GlobusCredentialManager credentialManager = GlobusCredentialManager.getInstance();
    private static JobStatusMessenger jobStatusMessenger = GrmsJobStatusMessenger.getInstance();
    private static GassManagerService gassManagerService = GridSphereGassManager.getInstance();
    private GsiScenarioBrokerServiceLocator serviceLocator = null;
    private URL serviceURL = null;
    private SortedMap allUserJobs = null;
    private GrmsJobMonitor jobMonitor = null;
    private String jobManagerDescription = null;

    public static GrmsJobManager getInstance() {
        if (_instance == null) {
            _instance = new GrmsJobManager();
        }
        return _instance;
    }

    private GrmsJobManager() {
    }

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        _logger.info("Entering init()");
        try {
            _logger.info("GrmsJobManager: Getting service locator...");
            this.serviceLocator = getServiceLocator();
            _logger.info("GrmsJobManager: Got service locator");
            _logger.info("GrmsJobManager: Getting service url...");
            this.serviceURL = getServiceURL(config);
            _logger.info("GrmsJobManager: Got service url");
            this.allUserJobs = Collections.synchronizedSortedMap(new TreeMap());
            this.jobMonitor = GrmsJobMonitor.getInstance();
        } catch (Exception e) {
            _logger.error("GrmsJobManager failed to initialize!", e);
            throw new PortletServiceUnavailableException(e.getMessage());
        }
    }

    private GsiScenarioBrokerServiceLocator getServiceLocator() throws Exception {
        GsiScenarioBrokerServiceLocator serviceLocator = null;
        try {
            serviceLocator = new GsiScenarioBrokerServiceLocator();
            SimpleProvider serviceProvider = new SimpleProvider();
            serviceProvider.deployTransport("httpg", new SimpleTargetedChain(new GSIHTTPSender()));
            serviceLocator.setEngineConfiguration(serviceProvider);
            Util.registerTransport();
        } catch (Exception e) {
            _logger.error("getServiceLocator", e);
            System.err.println(e);
            throw e;
        }
        return serviceLocator;
    }

    private URL getServiceURL(PortletServiceConfig config) throws Exception {
        try {
            String serviceUrl = config.getInitParameter("resmgmtServiceUrl", DEFAULT_RESMGMT_SERVICE_URL);
            _logger.info("GrmsJobManager: " + serviceUrl);
            return new URL(serviceUrl);
        } catch (Exception e) {
            _logger.error("getServiceURL", e);
            System.err.println(e);
            throw e;
        }
    }

    public GsiScenarioBroker connect() throws Exception {
        GSSCredential gssProxy = getPortalGSSProxy();
        return connect(gssProxy);
    }

    public GsiScenarioBroker connect(User user) throws Exception {
        GSSCredential gssProxy = getUserDefaultGSSProxy(user);
        return connect(gssProxy);
    }

    public GsiScenarioBroker connect(GSSCredential gssProxy) throws Exception {
        GsiScenarioBroker grmsBroker = null;
        try {
            _logger.info("GrmsJobManager: Getting service handle...");
            grmsBroker = this.serviceLocator.getgsiScenarioBroker(this.serviceURL);
            _logger.info("GrmsJobManager: Got service handle");
        } catch (Exception e) {
            String message = "GrmsJobManager failed to get scenario broker service!";
            _logger.error(message);
            System.err.println(message);
            throw e;
        }
        setPortProperties(grmsBroker, gssProxy);
        return grmsBroker;
    }

    private void setPortProperties(Remote ws, GSSCredential gssCredential) {
        _logger.info("GrmsJobManager: Setting port properties");
        Stub stub = (Stub) ws;
        stub._setProperty(GSIHTTPTransport.GSI_CREDENTIALS, gssCredential);
        stub._setProperty( GSIHTTPTransport.GSI_MODE, GSIHTTPTransport.GSI_MODE_FULL_DELEG);
        stub._setProperty(GSIHTTPTransport.GSI_AUTHORIZATION, new NoAuthorization());
    }

    public String getJobManagerDescription(User user)
            throws JobManagerException {
        if (this.jobManagerDescription == null) {
            try {
                GsiScenarioBroker grmsBroker = connect(user);
                this.jobManagerDescription = grmsBroker.getServiceDescription();
            } catch (Exception e) {
                _logger.error("getJobManagerDescription", e);
                System.err.println(e.getMessage());
                throw new JobManagerException(e.getMessage());
            }
        }
        return this.jobManagerDescription;
    }

    public List getJobList(User user)
            throws JobManagerException {
        return getJobList(user, false);
    }

    public List getJobList(User user, boolean force)
            throws JobManagerException {
        List jobList = new Vector();
        Map jobMap = getJobMap(user, force);
        Iterator jobIterator = jobMap.values().iterator();
        while (jobIterator.hasNext()) {
            jobList.add(jobIterator.next());
        }
        return jobList;
    }

    private SortedMap getJobMap(User user, boolean force)
            throws JobManagerException {
        SortedMap jobMap = null;
        // Jobs are sorted by user id and job id
        String userId = user.getID();
        // Test if we've created a job map for this user
        if (this.allUserJobs.containsKey(userId)) {
            // If so retrieve job map
            jobMap = (SortedMap)allUserJobs.get(userId);
        } else {
            // Otherwise create job map
            jobMap = Collections.synchronizedSortedMap(new TreeMap());
            this.allUserJobs.put(userId, jobMap);
        }
        // If asked to requery grms or job map size is 0 then query grms
        if (force || jobMap.size() == 0) {
            List jobList = new Vector();
            ArrayOf_Xsd_StringHolder jobsArray = new ArrayOf_Xsd_StringHolder();
            int errorCode = -1;
            try {
                _logger.info("GrmsJobManager: Getting connection for " + userId);
                GsiScenarioBroker grmsBroker = connect(user);
                _logger.info("GrmsJobManager: Getting job list...");
                errorCode = grmsBroker.getMyJobsList(jobsArray);
                _logger.info("User has " + jobsArray.value.length + " jobs");
                for (int ii = 0; ii < jobsArray.value.length; ++ii) {
                    // Get job id from job array
                    String jobId = jobsArray.value[ii];
                    _logger.info("(" + ii + ") Job id = " + jobId);
                    _logger.info("GrmsJobManager: Instantiating job...");
                    // Instantiate job
                    GrmsJob job = new GrmsJob();
                    job.setId(jobId);
                    job.setUser(user);
                    job.setJobStatus(JobStatus.STATUS_UNKNOWN);
                    _logger.info("GrmsJobManager: Adding job to job map...");
                    // Store record of job
                    jobMap.put(jobId, job);
                    _logger.info("GrmsJobManager: Adding job to job monitor...");
                    // Add to job monitor
                    this.jobMonitor.addJob(job);
                }
            } catch (Exception e) {
                _logger.error("getJobList", e);
                System.err.println(e.getMessage());
                throw new JobManagerException(e.getMessage());
            } finally {
                if (errorCode != 0) {
                    String message =
                        Local.getProperty(
                            "org.gridlab.services.resmgmt.GsiScenarioBroker.errorMessage." + errorCode,
                            "GsiScenarioBroker getMyJobsList failed with error code " + errorCode);
                    _logger.error(message);
                    System.err.println(message);
                    throw new JobManagerException(message);
                }
            }
        }
        return jobMap;
    }

    public Job getJob(User user, String jobId)
            throws JobNotFoundException, JobManagerException {
        Map jobMap = getJobMap(user, false);
        GrmsJob job = (GrmsJob)jobMap.get(jobId);
        // If job not in job map
        if (job == null) {
            try {
                // Then retrieve job from grms
                job = retrieveJob(user, jobId);
            } catch (JobManagerException e) {
                // If exception, assume job is unknown to job manager
                String message = "No record of job " + jobId + " for user " + user;
                JobNotFoundException jme = new JobNotFoundException(message);
                _logger.error("getJob", jme);
                throw jme;
            }
            // Add our job listener
            job.addJobStatusListener(this.jobStatusMessenger);
            // Then add job to our job record for this user
            jobMap.put(jobId, job);
            // Add job to job monitor if necessary
            if (this.jobMonitor.canMonitorJob(job)) {
                this.jobMonitor.addJob(job);
            }
        // Otherwise, force an update of job if possible
        } else if (this.jobMonitor.canMonitorJob(job)) {
            updateJob(user, job);
        }
        return job;
    }

    GrmsJob retrieveJob(User user, String jobId)
            throws JobManagerException {
        // Retrieve job information for given job id
        JobInformation jobInformation = retrieveJobInformation(user, jobId);
        // Instantiate new job
        GrmsJob job = new GrmsJob();
        job.setId(jobId);
        job.setUser(user);
        // Update job information
        updateJobInformation(job, jobInformation);
        return job;
    }

    void updateJob(User user, GrmsJob job)
            throws JobManagerException {
        String jobId = job.getID();
        JobStatus jobStatus = job.getJobStatus();
        // If job status is unknown, we must query for job information
        if (jobStatus.equals(JobStatus.STATUS_UNKNOWN)) {
            // Retrieve job information for given job id
            JobInformation jobInformation = retrieveJobInformation(user, jobId);
            // Update job information
            updateJobInformation(job, jobInformation);
            // TEMPORARY HACK!!!
            jobStatus = getJobStatus(user, jobId);
            updateJobStatus(job, jobStatus);
            // Add our job listener
            job.addJobStatusListener(this.jobStatusMessenger);
        // Otherwise we only need the job status
        } else {
            jobStatus = getJobStatus(user, jobId);
            updateJobStatus(job, jobStatus);
        }
    }

    JobInformation retrieveJobInformation(User user, String jobId)
            throws JobManagerException {
        JobInformation jobInformation = null;
        int errorCode = -1;
        try {
            JobInformationHolder jobInformationHolder = new JobInformationHolder();
            GsiScenarioBroker grmsBroker = connect(user);
            errorCode = grmsBroker.getJobInfo(jobId, jobInformationHolder);
            jobInformation = jobInformationHolder.value;
        } catch (Exception e) {
            _logger.error("retrieveJob", e);
            System.err.println(e.getMessage());
            throw new JobManagerException(e.getMessage());
        } finally {
            if (errorCode != 0) {
                String message =
                    Local.getProperty(
                        "org.gridlab.services.resmgmt.GsiScenarioBroker.errorMessage." + errorCode,
                        "GsiScenarioBroker getJobInfo failed with error code " + errorCode);
                System.err.println(message);
                JobManagerException e = new JobManagerException(message);
                _logger.error("retrieveJob", e);
                throw e;
            }
        }
        return jobInformation;
    }

    void updateJobInformation(GrmsJob job, JobInformation jobInformation)
            throws JobManagerException {
        job.setRuntimeHost(jobInformation.getHostName());
        job.setJobStatus(jobInformation.getJobStatus());
        job.setDateSubmitted(jobInformation.getSubmissionTime());
        job.setDateStarted(jobInformation.getStartTime());
        job.setDateEnded(jobInformation.getFinishTime());
    }

    void updateJobStatus(GrmsJob job, JobStatus jobStatus)
            throws JobManagerException {
        job.setJobStatus(jobStatus);
        if (jobStatus.equals(JobStatus.DONE) ||
            jobStatus.equals(JobStatus.DONE)) {
            job.setDateEnded(new Date());
        }
    }

    JobStatus getJobStatus(User user, String jobId)
            throws JobManagerException {
        JobStatus jobStatus = null;
        int errorCode = -1;
        StringHolder status = new StringHolder();
        try {
            GsiScenarioBroker grmsBroker = connect(user);
            errorCode = grmsBroker.getJobStatus(jobId, status);
        } catch (Exception e) {
            _logger.error("getJobStatus", e);
            _logger.info(e.getMessage());
            throw new JobManagerException(e.getMessage());
        } finally {
            if (errorCode != 0) {
                String message =
                    Local.getProperty(
                        "org.gridlab.services.resmgmt.GsiScenarioBroker.errorMessage." + errorCode,
                        "GsiScenarioBroker getJobStatus failed with error code " + errorCode);
                _logger.error(message);
                System.err.println(message);
                throw new JobManagerException(message);
            }
        }
        jobStatus = new JobStatus(status.value);
        return jobStatus;
    }

    public void clearJobList(User user)
            throws JobManagerException {
        removeJobs(user);
    }

    public int getNumberOfJobs(User user)
            throws JobManagerException {
        Map jobMap = getJobMap(user, false);
        return jobMap.size();
    }

    public Job submitJob(User user, JobSpecification jobSpecification)
            throws JobManagerException {
        // Pre-process job specification
        preProcess(user, jobSpecification);
        // Instantiate new job
        GrmsJob job = new GrmsJob();
        job.setUser(user);
        job.setJobSpecification(jobSpecification);
        // Add our job listener
        job.addJobStatusListener(this.jobStatusMessenger);
        // Submit job specification to grms
        int errorCode = -1;
        StringHolder jobId = new StringHolder();
        try {
            GsiScenarioBroker grmsBroker = connect(user);
            _logger.info("GrmsJobManager: Submitting job for " + user);
            errorCode = grmsBroker.submitJob(jobSpecification.toString(), jobId);
            _logger.info("GrmsJobManager: Submitted job " + jobId.value);
        } catch (Exception e) {
            _logger.error("submitJob", e);
            System.err.println(e.getMessage());
            throw new JobManagerException(e.getMessage());
        } finally {
            if (errorCode != 0) {
                String message =
                    Local.getProperty(
                        "org.gridlab.services.resmgmt.GsiScenarioBroker.errorMessage." + errorCode,
                        "GsiScenarioBroker submitJob failed with error code " + errorCode);
                System.err.println(message);
                JobManagerException e = new JobManagerException(message);
                _logger.error("submitJob", e);
                throw e;
            }
        }
        // Save job id
        job.setId(jobId.value);
        // Store record of job
        putJob(job);
        // Add our job listener
        job.addJobStatusListener(this.jobStatusMessenger);
        // Add job to job monitor
        this.jobMonitor.addJob(job);
        // Return job
        return job;
    }

    public void migrateJob(User user, Job job)
            throws JobManagerException {
        // Retrieve job id
        String jobId = job.getID();
        // Retrieve job specification from original job
        JobSpecification oldSpecification = job.getJobSpecification();
        // Pre-process job specification
        preProcess(user, oldSpecification);
        // Submit same job specification to grms
        int errorCode = -1;
        try {
            GsiScenarioBroker grmsBroker = connect(user);
            errorCode = grmsBroker.migrateJob(jobId, oldSpecification.toString());
        } catch (Exception e) {
            _logger.error("migrateJob", e);
            System.err.println(e.getMessage());
            throw new JobManagerException(e.getMessage());
        } finally {
            if (errorCode != 0) {
                String message =
                    Local.getProperty(
                            "org.gridlab.services.resmgmt.GsiScenarioBroker.errorMessage." + errorCode,
                            "GsiScenarioBroker migrateJob failed with error code " + errorCode);
                System.err.println(message);
                JobManagerException e = new JobManagerException(message);
                _logger.error("migrateJob", e);
                throw e;
            }
        }
        // Add job to job monitor
        this.jobMonitor.addJob(job);
    }

    public void migrateJob(User user, Job job, JobSpecification newSpecification)
            throws JobManagerException {
        // Retrieve rjob id
        String jobId = job.getID();
        // Pre-process job specification
        preProcess(user, newSpecification);
        // Submit new job specification for job id
        int errorCode = -1;
        try {
            GsiScenarioBroker grmsBroker = connect(user);
            errorCode = grmsBroker.migrateJob(jobId, newSpecification.toString());
        } catch (Exception e) {
            _logger.error("migrateJob", e);
            System.err.println(e.getMessage());
            throw new JobManagerException(e.getMessage());
        } finally {
            if (errorCode != 0) {
                String message =
                    Local.getProperty(
                            "org.gridlab.services.resmgmt.GsiScenarioBroker.errorMessage." + errorCode,
                            "GsiScenarioBroker migrateJob failed with error code " + errorCode);
                System.err.println(message);
                JobManagerException e = new JobManagerException(message);
                _logger.error("migrateJob", e);
                throw e;
            }
        }
        // Update job specification
        ((GrmsJob)job).setJobSpecification(newSpecification);
        // Add job to job monitor
        this.jobMonitor.addJob(job);
    }

    public static void preProcess(User user, JobSpecification jobSpecification)
            throws JobManagerException {
        /**** JUST TESTING RIGHT NOW!!!!! ****/
        if (true) return;
        _logger.info("GrmsJobManager: Preprocessing executable");
        // Setup gass for executable if necessary
        FileHandle executable = jobSpecification.getExecutable();
        if (gassManagerService.isGassRequired(executable)) {
            try {
                String url = gassManagerService.getGassServerUrl(user)
                           + executable.getFilePath();
                executable.setFileUrl(url);
            } catch (Exception e) {
                _logger.error("preProcess", e);
                System.err.println(e);
                throw new JobManagerException(e.getMessage());
            }
        }
        _logger.info("GrmsJobManager: Preprocessing stdin");
        // Setup gass for stdout if necessary
        FileHandle stdout = jobSpecification.getStdout();
        if (stdout == null) {
            try {
                stdout = gassManagerService.createGassStdoutHandle(user);
            } catch (Exception e) {
                _logger.error("preProcess", e);
                System.err.println(e);
                throw new JobManagerException(e.getMessage());
            }
            jobSpecification.setStdout(stdout);
        } else if (gassManagerService.isGassRequired(stdout)) {
            try {
                String url = gassManagerService.getGassServerUrl(user)
                           + stdout.getFilePath();
                stdout.setFileUrl(url);
            } catch (Exception e) {
                _logger.error("preProcess", e);
                System.err.println(e);
                throw new JobManagerException(e.getMessage());
            }
        }
        _logger.info("GrmsJobManager: Preprocessing stderr");
        // Setup gass for stderr if necessary
        FileHandle stderr = jobSpecification.getStderr();
        if (stderr == null) {
            try {
                stderr = gassManagerService.createGassStderrHandle(user);
            } catch (Exception e) {
                _logger.error("preProcess", e);
                System.err.println(e);
                throw new JobManagerException(e.getMessage());
            }
            jobSpecification.setStderr(stderr);
        } else if (gassManagerService.isGassRequired(stderr)) {
            try {
                String url = gassManagerService.getGassServerUrl(user)
                           + stderr.getFilePath();
                stderr.setFileUrl(url);
            } catch (Exception e) {
                _logger.error("preProcess", e);
                System.err.println(e);
                throw new JobManagerException(e.getMessage());
            }
        }
        // Setup gass for any file arguments if necessary
        _logger.info("GrmsJobManager: Preprocessing arguments");
        Arguments arguments = jobSpecification.getArguments();
        Iterator iterator = arguments.iterateArguments();
        while (iterator.hasNext()) {
            Argument argument = (Argument)iterator.next();
            if (argument.getArgumentType() == Argument.FILE) {
                _logger.info("GrmsJobManager: Preprocessing file argument");
                FileHandle file = argument.getFile();
                if (gassManagerService.isGassRequired(file)) {
                    try {
                        String url = gassManagerService.getGassServerUrl(user)
                                   + file.getFilePath();
                        file.setFileUrl(url);
                    } catch (Exception e) {
                        _logger.error("preProcess", e);
                        throw new JobManagerException(e.getMessage());
                    }
                }
            }
        }
    }

    public void stopJob(User user, Job job)
            throws JobManagerException {
        // Stop job how?
    }

    public void stopJob(User user, String jobId)
            throws JobNotFoundException, JobManagerException {
        Job job = getJob(user, jobId);
        stopJob(user, job);
    }

    public void killJob(User user, Job job)
            throws JobManagerException {
        stopJob(user, job);
        removeJob(job);
    }

    public void killJob(User user, String jobId)
            throws JobNotFoundException, JobManagerException {
        Job job = getJob(user, jobId);
        stopJob(user, job);
        removeJob(job);
    }

    private void putJob(Job job)
        throws JobManagerException {
        User user = job.getUser();
        Map jobMap = getJobMap(user, false);
        String jobId = job.getID();
        jobMap.put(jobId, job);
   }

    private void removeJob(Job job)
            throws JobManagerException {
        User user = job.getUser();
        Map jobMap = getJobMap(user, false);
        String jobId = job.getID();
        if (jobMap.containsKey(jobId)) {
            jobMap.remove(jobId);
        }
    }

    private void removeJobs(User user)
            throws JobManagerException {
        Map jobMap = getJobMap(user, false);
        Iterator jobIterator = jobMap.values().iterator();
        while (jobIterator.hasNext()) {
            Job job = (Job)jobIterator.next();
            stopJob(user, job);
            removeJob(job);
        }
    }

    private GSSCredential getUserDefaultGSSProxy(User user) {
        if (user == null || !credentialManager.hasActiveCredentials(user)) {
            _logger.info("GrmsJobManager: User has no active proxies");
            GSSCredential portalProxy = getPortalGSSProxy();
            return portalProxy;
        } else {
            _logger.info("GrmsJobManager: Getting user proxy");
            List credentials = credentialManager.getActiveCredentials(user);
            GlobusCredential credential = (GlobusCredential)credentials.get(0);
            return credential.getGSSProxy();
        }
    }

    private GSSCredential getPortalGSSProxy() {
        _logger.debug("Portal credential has not been set yet");
        try {
            ExtendedGSSManager manager = (ExtendedGSSManager)ExtendedGSSManager.getInstance();
            return manager.createCredential(GSSCredential.INITIATE_AND_ACCEPT);
        } catch (Exception e) {
            String m = "Error getting portal credential";
            _logger.error(m, e);
            return null;
        }
    }

    private List toXsdStringList(ArrayOf_Xsd_StringHolder array) {
        List elements = new Vector();
        for (int ii = 0; ii < array.value.length; ii++) {
            elements.add(array.value[ii]);
        }
        return elements;
    }
}
