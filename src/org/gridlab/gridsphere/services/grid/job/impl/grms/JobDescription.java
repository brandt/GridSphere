/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.grid.job.impl.grms;

public abstract class JobDescription {

    public static final int SINGLE = 0;

    private int jobType = SINGLE;

    public int getJobType() {
        return this.jobType;
    }

    void setJobType(int type) {
        this.jobType = type;
    }
}
