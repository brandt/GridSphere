/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.grid.job.impl.grms;

import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import org.gridlab.gridsphere.services.grid.job.Job;
import org.gridlab.gridsphere.services.grid.job.JobStatus;
import org.gridlab.gridsphere.services.grid.job.JobStatusListener;
import org.gridlab.gridsphere.services.grid.job.JobSpecification;
import org.gridlab.gridsphere.services.grid.job.JobManagerException;
import org.gridlab.gridsphere.portlet.User;

public class GrmsJob implements Job {

    private String jobId = "";
    private User user = null;
    private GrmsJobSpecification jobSpecification = null;
    private JobStatus jobStatus = JobStatus.STATUS_NEW;
    private String runtimeHost = "";
    private String runtimeScheduler = "";
    private String runtimeQueue = "";
    private Date dateSubmitted = null;
    private Date dateStarted = null;
    private Date dateEnded = null;
    private Date dateStatusChanged = null;
    private Vector jobStatusListeners = new Vector();

    public GrmsJob() {
        this.jobSpecification = new GrmsJobSpecification();
    }

    public GrmsJob(User user, GrmsJobSpecification jobSpecification) {
        this.user = user;
        this.jobSpecification = jobSpecification;
    }

    public String getID() {
        return this.jobId;
    }

    public void setId(String id) {
       this.jobId = id;
       this.jobStatus = JobStatus.STATUS_UNKNOWN;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public JobSpecification getJobSpecification() {
        return this.jobSpecification;
    }

    public void setJobSpecification(JobSpecification jobSpecification) {
        this.jobSpecification = (GrmsJobSpecification)jobSpecification;
    }
    public String getRuntimeHost() {
        return this.runtimeHost;
    }

    public void setRuntimeHost(String host) {
        this.runtimeHost = host;
    }

    public String getRuntimeScheduler() {
        return this.runtimeScheduler;
    }

    public void setRuntimeScheduler(String scheduler) {
        this.runtimeScheduler = scheduler;
    }

    public String getRuntimeQueue() {
        return this.runtimeQueue;
    }

    public void setRuntimeQueue(String queue) {
        this.runtimeQueue = queue;
    }

    public JobStatus getJobStatus() {
        return this.jobStatus;
    }

    public void setJobStatus(JobStatus jobStatus) {
       this.jobStatus = jobStatus;
    }

    public void setJobStatus(String jobStatus) {
       this.jobStatus = new JobStatus(jobStatus);
       this.dateStatusChanged = new Date();
       notifyJobStatusListeners();
    }

    public Date getDateSubmitted() {
        return this.dateSubmitted;
    }

    public void setDateSubmitted(Date date) {
        this.dateSubmitted = date;
    }

    public void setDateSubmitted(long date) {
        this.dateSubmitted = new Date(date);
    }

    public Date getDateStarted() {
        return this.dateStarted;
    }

    public void setDateStarted(Date date) {
        this.dateStarted = date;
    }

    public void setDateStarted(long date) {
        this.dateStarted = new Date(date);
    }

    public Date getDateEnded() {
        return this.dateEnded;
    }

    public void setDateEnded(Date date) {
        this.dateEnded = date;
    }

    public void setDateEnded(long date) {
        this.dateEnded = new Date(date);
    }

    public Date getDateStatusChanged() {
        return this.dateStatusChanged;
    }

    public void setDateStatusChanged(Date date) {
        this.dateStatusChanged = date;
    }

    public void addJobStatusListener(JobStatusListener listener) {
        this.jobStatusListeners.add(listener);
    }

    public void removeJobStatusListener(JobStatusListener listener) {
        if (this.jobStatusListeners.contains(listener)) {
            this.jobStatusListeners.remove(listener);
        }
    }

    private void notifyJobStatusListeners() {
        Iterator listeners = this.jobStatusListeners.iterator();
        while (listeners.hasNext()) {
            JobStatusListener listener = (JobStatusListener)listeners.next();
            listener.jobStatusChanged(this);
        }
    }

    public void stopJob()
            throws JobManagerException {
        GrmsJobManager.getInstance().stopJob(this.user, this);
    }

    public void killJob()
            throws JobManagerException {
        GrmsJobManager.getInstance().killJob(this.user, this);
    }
}
