/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.grid.job.impl;

import org.gridlab.gridsphere.services.grid.job.Job;
import org.gridlab.gridsphere.services.grid.job.JobStatus;
import org.gridlab.gridsphere.services.grid.job.JobMonitor;
import org.gridlab.gridsphere.services.grid.job.JobManagerException;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

public abstract class AbstractJobMonitor extends Thread implements JobMonitor {

    private static AbstractJobMonitor _instance = null;
    private Long period = new Long(30);
    private boolean active = false;
    private boolean cancel = false;
    private Map jobMap = null;

    protected AbstractJobMonitor() {
        jobMap = Collections.synchronizedMap(new TreeMap());
    }

    public Iterator getJobs() {
        List jobList = new Vector();
        synchronized (this.jobMap) {
            Iterator jobIterator = this.jobMap.values().iterator();
            while (jobIterator.hasNext()) {
                Job job = (Job)jobIterator.next();
                jobList.add(job);
            }
        }
        return jobList.iterator();
    }

    public Job getJob(String jobId) {
        return (Job)this.jobMap.get(jobId);
    }

    public boolean hasJob(String jobId) {
        return this.jobMap.containsKey(jobId);
    }

    public void addJob(Job job) {
        String jobId = job.getID();
        System.out.println("JobMonitor: Adding job "
          + jobId + " with status " + job.getJobStatus());
        this.jobMap.put(jobId, job);
    }

    public void removeJob(String jobId) {
        this.jobMap.remove(jobId);
    }

    public boolean canMonitorJob(Job job) {
        String jobStatus = job.getJobStatus().getValue();
        // Cannot monitor if job is done or failed
        return (! (jobStatus.equals(JobStatus.DONE) ||
                   jobStatus.equals(JobStatus.FAILED)) );
    }

    public abstract void updateJob(Job job) throws JobManagerException;

    public void run() {
        while (true) {
            // Check if have been canceled
            synchronized (this.jobMap) {
                if (this.cancel) {
                    break;
                }
            }
            // Loop through job list and update job status
            Iterator jobIterator = getJobs();
            while (jobIterator.hasNext()) {
                Job job = (Job)jobIterator.next();
                String jobId = job.getID();
                // Check if we can monitor job
                if (canMonitorJob(job)) {
                    System.out.println("JobMonitor: Updating job "
                                      + jobId + " with status " + job.getJobStatus());
                    // If so, update job status
                    try {
                        updateJob(job);
                    } catch (JobManagerException e) {
                        // If we caught an exception, remove job from list
                        System.err.println(e);
                        removeJob(jobId);
                    }
                } else {
                    // Othwerwise, remove job from this monitor
                    removeJob(jobId);
                }
            }
            try {
                // Sleep for specified period
                sleep(this.period.longValue() * 1000);
            } catch (InterruptedException e) {
                System.err.println("JobStatusMonitor: Error putting thread to sleep!");
                break;
            }
        }
    }

    public void cancel() {
        System.out.println("JobStatusMonitor: Shutting down job status monitor");
        synchronized (this.jobMap) {
            this.cancel = true;
        }
    }
}
