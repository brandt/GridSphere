/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.grid.job;

public class JobStatus {

    public static final String NEW = "NEW";
    public static final String PENDING = "STATUS_PENDING";
    public static final String ACTIVE = "STATUS_ACTIVE";
    public static final String SUSPENDED = "STATUS_SUSPENDED";
    public static final String DONE = "DONE";
    public static final String FAILED = "FAILED";
    public static final String UNKNOWN = "UNKNOWN";

    public static final JobStatus STATUS_NEW = new JobStatus(NEW);
    public static final JobStatus STATUS_PENDING = new JobStatus(PENDING);
    public static final JobStatus STATUS_ACTIVE = new JobStatus(ACTIVE);
    public static final JobStatus STATUS_SUSPENDED = new JobStatus(SUSPENDED);
    public static final JobStatus STATUS_DONE = new JobStatus(DONE);
    public static final JobStatus STATUS_FAILED = new JobStatus(FAILED);
    public static final JobStatus STATUS_UNKNOWN = new JobStatus(UNKNOWN);

    private String value = NEW;

    public JobStatus() {
    }

    public JobStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean equals(JobStatus jobStatus) {
        return (this.value.equals(jobStatus.value));
    }

    public boolean equals(String jobStatusValue) {
        return (this.value.equals(jobStatusValue));
    }

    public String toString() {
        return this.value;
    }
}
