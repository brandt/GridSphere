/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.grid.job;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.net.MalformedURLException;

import org.gridlab.gridsphere.services.grid.data.file.FileHandle;
import org.gridlab.gridsphere.services.grid.job.Arguments;
import org.gridlab.gridsphere.portlet.User;

public interface Job {

    public String getID();

    public User getUser();

    public String getRuntimeHost();

    public String getRuntimeScheduler();

    public String getRuntimeQueue();

    public JobStatus getJobStatus();

    public Date getDateSubmitted();

    public Date getDateStarted();

    public Date getDateEnded();

    public Date getDateStatusChanged();

    public JobSpecification getJobSpecification();

    public void addJobStatusListener(JobStatusListener listener);

    public void removeJobStatusListener(JobStatusListener listener);

    public void stopJob() throws JobManagerException;

    public void killJob() throws JobManagerException;
}
