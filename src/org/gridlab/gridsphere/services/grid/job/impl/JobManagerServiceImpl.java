/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.grid.job.impl;

import org.gridlab.gridsphere.portlet.service.spi.PortletServiceAuthorizer;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.services.grid.job.impl.grms.GrmsJobManager;
import org.gridlab.gridsphere.services.grid.job.*;

import java.util.List;

public class JobManagerServiceImpl implements JobManagerService, PortletServiceProvider {

    private GrmsJobManager jobManager = GrmsJobManager.getInstance();
    private PortletServiceAuthorizer authorizer = null;

    public JobManagerServiceImpl(PortletServiceAuthorizer authorizer) {
        this.authorizer = authorizer;
    }

    public void init(PortletServiceConfig config)
            throws PortletServiceUnavailableException {
        jobManager.init(config);
    }

    public void destroy() {
    }

    public String getJobManagerDescription(User user)
            throws JobManagerException {
        return jobManager.getJobManagerDescription(user);
    }

    public List getJobList(User user)
            throws JobManagerException {
        return jobManager.getJobList(user);
    }

    public Job getJob(User user, String jobId)
            throws JobNotFoundException, JobManagerException {
        return jobManager.getJob(user, jobId);
    }

    public void clearJobList(User user)
            throws JobManagerException {
        jobManager.clearJobList(user);
    }

    public int getNumberOfJobs(User user)
            throws JobManagerException {
        return jobManager.getNumberOfJobs(user);
    }

    public Job submitJob(User user, JobSpecification jobSpecification)
            throws JobManagerException {
        return jobManager.submitJob(user, jobSpecification);
    }

    public void migrateJob(User user, Job job)
            throws JobManagerException {
        jobManager.migrateJob(user, job);
    }

    public void migrateJob(User user, Job job, JobSpecification jobSpecification)
            throws JobManagerException {
        jobManager.migrateJob(user, job, jobSpecification);
    }

    public void stopJob(User user, String jobId)
            throws JobNotFoundException, JobManagerException {
        jobManager.stopJob(user, jobId);
    }

    public void killJob(User user, String jobId)
            throws JobNotFoundException, JobManagerException {
        jobManager.killJob(user, jobId);
    }
}
