/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.grid.job.impl.grms;

import org.gridlab.gridsphere.services.grid.job.*;
import org.gridlab.gridsphere.services.grid.job.impl.AbstractJobMonitor;
import org.gridlab.gridsphere.services.grid.job.impl.grms.GrmsJob;
import org.gridlab.gridsphere.services.grid.job.impl.grms.GrmsJobManager;

public class GrmsJobMonitor extends AbstractJobMonitor {

    private static GrmsJobMonitor _instance = null;

    public static GrmsJobMonitor getInstance() {
        if (_instance == null) {
            System.out.println("Creating new grms job monitor instance.");
            _instance = new GrmsJobMonitor();
            System.out.println("Starting instance.");
            _instance.start();
            System.out.println("Ready to monitor...");
        }
        return _instance;
    }

    protected GrmsJobMonitor() {
        super();
    }


    public void updateJob(Job job)
            throws JobManagerException {
        GrmsJobManager.getInstance().updateJob(job.getUser(), (GrmsJob)job);
    }
}
