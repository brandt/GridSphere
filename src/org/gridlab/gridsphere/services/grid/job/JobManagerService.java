/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.grid.job;

import org.gridlab.gridsphere.portlet.User;

import java.util.List;

public interface JobManagerService {

    public String getJobManagerDescription(User user)
            throws JobManagerException;

    public List getJobList(User user)
            throws JobManagerException;

    public Job getJob(User user, String jobId)
            throws JobNotFoundException, JobManagerException;

    public void clearJobList(User user)
            throws JobManagerException;

    public int getNumberOfJobs(User user)
            throws JobManagerException;

    public Job submitJob(User user, JobSpecification jobSpecification)
            throws JobManagerException;

    public void migrateJob(User user, Job job)
            throws JobManagerException;

    public void migrateJob(User user, Job job, JobSpecification jobSpecification)
            throws JobManagerException;

    public void stopJob(User user, String jobId)
            throws JobNotFoundException, JobManagerException;

    public void killJob(User user, String jobId)
            throws JobNotFoundException, JobManagerException;
}
