/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.grid.job;

import org.gridlab.gridsphere.services.grid.data.file.FileHandle;

import java.net.MalformedURLException;
import java.util.List;

public interface JobSpecification {

    public FileHandle getExecutable();

    public void setExecutable(FileHandle executable);

    public void setExecutable(String executable)
            throws MalformedURLException;

    public FileHandle getStdin();

    public void setStdin(FileHandle stdin);

    public void setStdin(String stdin)
           throws MalformedURLException;

    public FileHandle getStdout();

    public void setStdout(FileHandle stdout);

    public void setStdout(String stdout)
            throws MalformedURLException;

    public FileHandle getStderr();

    public void setStderr(FileHandle stderr);

    public void setStderr(String stderr)
            throws MalformedURLException;

    public Arguments getArguments();

    public void setArguments(Arguments arguments);

    public void addArgument(String argument);

    public void removeArgument(int position);

    public Environment getEnvironment();

    public void setEnvironment(Environment environment);

    public void putEnvironmentVariable(String name, String value);

    public void removeEnvironmentVariable(String name);

    public String getHost();

    public void setHost(String host);

    public String getJobScheduler();

    public void setJobScheduler(String scheduler);

    public String getJobQueue();

    public void setJobQueue(String queue);

    public long getMinimumMemory();

    public void setMinimumMemory(long memory);

    public long getCpuCount();

    public void setCpuCount(long cpucount);

    /***
    public List getAttributes();

    public Object getAttribute(String name);

    public void setAttribute(String name, Object attribute);

    public void clearAttribute(String name);

    public void clearAttributes();
    ***/

    public String toString();
}
