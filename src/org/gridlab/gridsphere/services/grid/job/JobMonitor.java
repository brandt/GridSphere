/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.grid.job;

import java.util.Iterator;

public interface JobMonitor extends Runnable {

    public Iterator getJobs();

    public Job getJob(String jobId);

    public boolean hasJob(String jobId);

    public void addJob(Job job);

    public void removeJob(String jobId);

    public boolean canMonitorJob(Job job);

    public void updateJob(Job job) throws JobManagerException;

    public void run();

    public void cancel();
}
