/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.grid.job.impl.grms;

public class ResourceDescription {

    private String osType = null;
    private String osName = null;
    private String osVersion = null;
    private String osRelease = null;
    private String hostName = null;
    private String jobScheduler = null;
    private long memory = 0;
    private long cpuCount = 0;
    private long cpuSpeed = 0;
    private String maxTime = null;
    private String maxWallTime = null;
    private String maxCpuTime = null;

    public ResourceDescription() {
    }

    public String getOsType() {
        return this.osType;
    }

    public void setOsType(String value) {
        if (value == null || value.length() == 0) {
            return;
        }
       this.osType = value;
    }

    public String getOsName() {
        return this.osName;
    }

    public void setOsName(String value) {
        if (value == null || value.length() == 0) {
            return;
        }
        this.osName = value;
    }

    public String getOsVersion() {
        return this.osVersion;
    }

    public void setOsVersion(String value) {
        if (value == null || value.length() == 0) {
            return;
        }
        this.osVersion = value;
    }

    public String getOsRelease() {
        return this.osRelease;
    }

    public void setOsRelease(String value) {
        this.osRelease = value;
        if (value == null || value.length() == 0) {
            return;
        }
    }

    public String getHostName() {
        return this.hostName;
    }

    public void setHostName(String value) {
        if (value == null || value.length() == 0) {
            return;
        }
        this.hostName = value;
    }

    public String getJobScheduler() {
        return this.jobScheduler;
    }

    public void setJobScheduler(String value) {
        if (value == null || value.length() == 0) {
            return;
        }
        this.jobScheduler = value;
    }

    public long getMemory() {
        return this.memory;
    }

    public void setMemory(long value) {
        if (value <= 0) {
            return;
        }
        this.memory = value;
    }

    public long getCpuCount() {
        return this.cpuCount;
    }

    public void setCpuCount(long value) {
        if (value <= 0) {
            return;
        }
        this.cpuCount = value;
    }

    public long getCpuSpeed() {
        return this.cpuSpeed;
    }

    public void setCpuSpeed(long value) {
        if (value <= 0) {
            return;
        }
        this.cpuSpeed = value;
    }

    public String getMaxTime() {
        return this.maxTime;
    }

    public void setMaxTime(String value) {
        if (value == null || value.length() == 0) {
            return;
        }
        this.maxTime = value;
    }

    public String getMaxWallTime() {
        return this.maxWallTime;
    }

    public void setMaxWallTime(String value) {
        if (value == null || value.length() == 0) {
            return;
        }
        this.maxWallTime = value;
    }

    public String getMaxCpuTime() {
        return this.maxCpuTime;
    }

    public void setMaxCpuTime(String value) {
        if (value == null || value.length() == 0) {
            return;
        }
        this.maxCpuTime = value;
    }
}
