/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.grid.job.impl.grms;

import java.io.*;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.net.MalformedURLException;

import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.jdo.JDO;

import org.gridlab.gridsphere.services.grid.job.*;
import org.gridlab.gridsphere.services.grid.data.file.FileHandle;
import org.gridlab.gridsphere.services.grid.system.Local;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

public class GrmsJobSpecification implements JobSpecification {

    private static PortletLog portletLog = SportletLog.getInstance(GrmsJobSpecification.class);

    private String id = "appid";
    private JobDescription jobDescription = null;

    public GrmsJobSpecification() {
        this.jobDescription = new SingleJobDescription();
    }

    public GrmsJobSpecification(JobDescription jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
       this.id = id;
    }

    public JobDescription getJobDescription() {
        return this.jobDescription;
    }

    public void setJobDescription(JobDescription jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getHost() {
        SingleJobDescription singleJob =
                (SingleJobDescription)this.jobDescription;
        return singleJob.getResourceDescription().getHostName();
    }

    public void setHost(String host) {
        SingleJobDescription singleJob =
                (SingleJobDescription)this.jobDescription;
        singleJob.getResourceDescription().setHostName(host);
    }

    public String getJobScheduler() {
        SingleJobDescription singleJob =
                (SingleJobDescription)this.jobDescription;
        return singleJob.getResourceDescription().getJobScheduler();
    }

    public void setJobScheduler(String scheduler) {
        SingleJobDescription singleJob =
                (SingleJobDescription)this.jobDescription;
        singleJob.getResourceDescription().setJobScheduler(scheduler);
    }

    public String getJobQueue() {
        return "";
    }

    public void setJobQueue(String queue) {
        // do nothing
    }

    public FileHandle getExecutable() {
        SingleJobDescription singleJob =
                (SingleJobDescription)this.jobDescription;
        return singleJob.getApplicationDescription().getExecutable();
    }

    public void setExecutable(FileHandle file) {
        SingleJobDescription singleJob =
                (SingleJobDescription)this.jobDescription;
        singleJob.getApplicationDescription().setExecutable(file);
    }

    public void setExecutable(String file)
            throws MalformedURLException {
        SingleJobDescription singleJob =
                (SingleJobDescription)this.jobDescription;
        singleJob.getApplicationDescription().setExecutable(file);
    }

    public FileHandle getStdin() {
        SingleJobDescription singleJob =
                (SingleJobDescription)this.jobDescription;
        return singleJob.getApplicationDescription().getStdin();
    }

    public void setStdin(FileHandle file) {
        SingleJobDescription singleJob =
                (SingleJobDescription)this.jobDescription;
        singleJob.getApplicationDescription().setStdin(file);
    }

    public void setStdin(String file)
            throws MalformedURLException {
        SingleJobDescription singleJob =
                (SingleJobDescription)this.jobDescription;
        singleJob.getApplicationDescription().setStdin(file);
    }

    public FileHandle getStdout() {
        SingleJobDescription singleJob =
                (SingleJobDescription)this.jobDescription;
        return singleJob.getApplicationDescription().getStdout();
    }

    public void setStdout(FileHandle file) {
        SingleJobDescription singleJob =
                (SingleJobDescription)this.jobDescription;
        singleJob.getApplicationDescription().setStdout(file);
    }

    public void setStdout(String file)
            throws MalformedURLException {
        SingleJobDescription singleJob =
                (SingleJobDescription)this.jobDescription;
        singleJob.getApplicationDescription().setStdout(file);
    }

    public FileHandle getStderr() {
        SingleJobDescription singleJob =
                (SingleJobDescription)this.jobDescription;
        return singleJob.getApplicationDescription().getStderr();
    }

    public void setStderr(FileHandle file) {
        SingleJobDescription singleJob =
                (SingleJobDescription)this.jobDescription;
        singleJob.getApplicationDescription().setStderr(file);
    }

    public void setStderr(String file)
            throws MalformedURLException {
        SingleJobDescription singleJob =
                (SingleJobDescription)this.jobDescription;
        singleJob.getApplicationDescription().setStderr(file);
    }

    public Arguments getArguments() {
        SingleJobDescription singleJob =
                (SingleJobDescription)this.jobDescription;
        return singleJob.getApplicationDescription().getArguments();
    }

    public void setArguments(Arguments arguments) {
        SingleJobDescription singleJob =
                (SingleJobDescription)this.jobDescription;
        singleJob.getApplicationDescription().setArguments(arguments);
    }

    public void addArgument(String text) {
        Arguments arguments = getArguments();
        arguments.addArgument(text);
    }

    public void removeArgument(int position) {
        Arguments arguments = getArguments();
        arguments.removeArgument(position);
    }

    public int getNumberOfArguments() {
        Arguments arguments = getArguments();
        return arguments.getNumberOfArguments();
    }

    public Environment getEnvironment() {
        SingleJobDescription singleJob =
                (SingleJobDescription)this.jobDescription;
       return singleJob.getApplicationDescription().getEnvironment();
    }

    public void setEnvironment(Environment environment) {
        SingleJobDescription singleJob =
                (SingleJobDescription)this.jobDescription;
        singleJob.getApplicationDescription().setEnvironment(environment);
    }

    public void putEnvironmentVariable(String name, String value) {
        Environment environment = getEnvironment();
        environment.putVariable(name, value);
    }

    public void removeEnvironmentVariable(String name) {
        Environment environment = getEnvironment();
        environment.removeVariable(name);
    }

    public long getMinimumMemory() {
        SingleJobDescription singleJob =
                (SingleJobDescription)this.jobDescription;
        return singleJob.getResourceDescription().getMemory();
    }

    public void setMinimumMemory(long memory) {
        SingleJobDescription singleJob =
                (SingleJobDescription)this.jobDescription;
        singleJob.getResourceDescription().setMemory(memory);
    }

    public long getCpuCount() {
        SingleJobDescription singleJob =
                (SingleJobDescription)this.jobDescription;
        return singleJob.getResourceDescription().getCpuCount();
    }

    public void setCpuCount(long cpuCount) {
        SingleJobDescription singleJob =
                (SingleJobDescription)this.jobDescription;
        singleJob.getResourceDescription().setCpuCount(cpuCount);
    }

    public String toString() {
        portletLog.debug("Marshaling to a grms job");
        org.gridlab.resmgmt.Grmsjob grmsjob = buildGrmsjob();
        StringWriter writer = new StringWriter();
        portletLog.debug("Marshalling to a string writer");
        try {
            grmsjob.marshal(writer);
        } catch (Exception e) {
            portletLog.error("Error marhsaling job specification!", e);
        }
        return writer.toString();
        /***
        marshal(writer);
        String output = writer.toString();
        return postProcess(output);
        ***/
    }

    public void exportXml(String xmlFile)
            throws IOException {
        FileWriter writer = new FileWriter(xmlFile);
        //marshal(writer);
        String output = toString();
        System.out.println(output);
        writer.write(output, 0, output.length());
        writer.close();
    }

    private String postProcess(String xmlString) {
        StringBuffer buffer = new StringBuffer();
        Arguments arguments = getArguments();
        if (arguments.getNumberOfArguments() > 0) {
            int start = xmlString.indexOf("<arguments>") + 11;
            buffer.append(xmlString.substring(0, start));
            xmlString = xmlString.substring(start);
            start = xmlString.indexOf("<argument");
            System.out.println("GrmsJobSpecification: post process arguments()");
            for (int ii = 0; start > -1; ++ii) {
                int type = arguments.getArgument(ii).getArgumentType();
                if (type == Argument.VALUE) {
                    System.out.println("GrmsJobSpecification: value argument");
                    buffer.append("<value>");
                    start += 10;
                } else {
                    System.out.println("GrmsJobSpecification: file argument");
                    buffer.append("<file");
                    start += 9;
                }
                int end = xmlString.indexOf("</argument>");
                buffer.append(xmlString.substring(start, end));
                if (type == Argument.VALUE) {
                    buffer.append("</value>");
                } else {
                    buffer.append("</file>");
                }
                xmlString = xmlString.substring(end+11);
                start = xmlString.indexOf("<argument");
            }
        }
        buffer.append(xmlString);
        return buffer.toString();
    }

    public void marshal(Writer writer) {
        Mapping mapping = new Mapping();
        try {
            String xmlConfig = getJdoXmlConfiguration();
            mapping.loadMapping(xmlConfig);
            Marshaller marshaller = new Marshaller(writer);
            marshaller.setMapping(mapping);
            marshaller.marshal(this);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public static GrmsJobSpecification importXml(String xmlFile)
            throws FileNotFoundException {
        FileReader reader = new FileReader(xmlFile);
        return GrmsJobSpecification.unmarshal(reader);
    }

    public static GrmsJobSpecification unmarshal(String xmlString) {
        StringReader reader = new StringReader(xmlString);
        return unmarshal(reader);
    }

    public static GrmsJobSpecification unmarshal(Reader reader) {
        GrmsJobSpecification jobSpecification = null;
        Mapping mapping = new Mapping();
        PrintWriter printWriter = new PrintWriter(System.err);
        mapping.setLogWriter(printWriter);
        try {
            String xmlMappingFile = getJdoXmlConfiguration();
            System.err.println("Grms Job specification jdo castor mapping file = " + xmlMappingFile);
            mapping.loadMapping(xmlMappingFile);
            Unmarshaller unmarshaller = new Unmarshaller(mapping);
            unmarshaller.setMapping(mapping);
            jobSpecification = (GrmsJobSpecification)unmarshaller.unmarshal(reader);
        } catch (Exception e) {
            System.err.println(e);
            jobSpecification = new GrmsJobSpecification();
        }
        return jobSpecification;
    }

    private static String getJdoXmlConfiguration() {
        return "/Users/russell/gridsphere/webapps/gridportlets/WEB-INF/jdo/castor/GrmsJobSpecification.xml";
        /***
         return "~/gridsphere/webapps/gridportlets/WEB-INF/jdo/castor/GrmsJobSpecification.xml";
        return Local.getJdoCastorPath()
             + Local.FileSeparator
             + "GrmsJobSpecification.xml";
        ***/
    }

    private org.gridlab.resmgmt.Grmsjob buildGrmsjob() {
        // Grmsjob contains application id and simple job
        org.gridlab.resmgmt.Grmsjob grmsjob = new org.gridlab.resmgmt.Grmsjob();
        // Set pplication id
        String appid = this.getId();
        grmsjob.setAppid(this.getId());
        // Set simple job
        org.gridlab.resmgmt.Simplejob simplejob = buildSimplejob();
        grmsjob.setSimplejob(simplejob);
        return grmsjob;
    }

    private org.gridlab.resmgmt.Simplejob buildSimplejob() {
        // Simplejob contains application and resource
        org.gridlab.resmgmt.Simplejob simplejob = new org.gridlab.resmgmt.Simplejob();
        // Set executable
        org.gridlab.resmgmt.Executable application = buildExecutable();
        simplejob.setExecutable(application);
        // Set resource
        org.gridlab.resmgmt.Resource resource = buildResource();
        simplejob.setResource(resource);
        // All done
        return simplejob;
    }

    private org.gridlab.resmgmt.Executable buildExecutable() {
        // Executable contains
        org.gridlab.resmgmt.Executable executable = new org.gridlab.resmgmt.Executable();
        // Executable type
        org.gridlab.resmgmt.types.ExecutableTypeType type = buildExecutableType();
        executable.setType(type);
        // Executable count
        //executable.setCount(1);
        // Executable file
        org.gridlab.resmgmt.ExecutableChoice choice = buildExecutableChoice();
        executable.setExecutableChoice(choice);
        // Arguments
        org.gridlab.resmgmt.Arguments arguments = buildArguments();
        executable.setArguments(arguments);
        // Stdout
        org.gridlab.resmgmt.Stdout stdout = buildStdout();
        executable.setStdout(stdout);
        // Stderr
        org.gridlab.resmgmt.Stderr stderr = buildStderr();
        executable.setStderr(stderr);
        // Environment
        org.gridlab.resmgmt.Environment environment = buildEnvironment();
        executable.setEnvironment(environment);
        // All done
        return executable;
    }

    private org.gridlab.resmgmt.types.ExecutableTypeType buildExecutableType() {
        return org.gridlab.resmgmt.types.ExecutableTypeType.SINGLE;
    }

    private org.gridlab.resmgmt.ExecutableChoice buildExecutableChoice() {
        FileHandle executable = getExecutable();
        org.gridlab.resmgmt.ExecutableChoice choice = new org.gridlab.resmgmt.ExecutableChoice();
        org.gridlab.resmgmt.File file = new org.gridlab.resmgmt.File();
        file.setUrl(executable.getFileUrl());
        file.setName(executable.getFileName());
        file.setType(org.gridlab.resmgmt.types.FileTypeTypeType.IN);
        choice.setFile(file);
        return choice;
    }

/***
    private org.gridlab.resmgmt.Simplejob buildSimplejob() {
        // Simplejob contains application and resource
        org.gridlab.resmgmt.Simplejob simplejob = new org.gridlab.resmgmt.Simplejob();
        // Set application
        org.gridlab.resmgmt.Application application = buildApplication();
        simplejob.setApplication(application);
        // Set resource
        org.gridlab.resmgmt.Resource resource = buildResource();
        simplejob.setResource(resource);
        // All done
        return simplejob;
    }

    private org.gridlab.resmgmt.Application buildApplication() {
        // Application contains executable, stdout, stderr and arguments
        org.gridlab.resmgmt.Application application = new org.gridlab.resmgmt.Application();
        // Executable
        org.gridlab.resmgmt.Executable executable = buildExecutable();
        application.setExecutable(executable);
        // Arguments
        org.gridlab.resmgmt.Arguments arguments = buildArguments();
        application.setArguments(arguments);
        // Stdout
        org.gridlab.resmgmt.Stdout stdout = buildStdout();
        application.setStdout(stdout);
        // Stderr
        org.gridlab.resmgmt.Stderr stderr = buildStderr();
        application.setStderr(stderr);
        // All done
        return application;
    }

    private org.gridlab.resmgmt.Executable buildExecutable() {
        org.gridlab.resmgmt.Executable executable = new org.gridlab.resmgmt.Executable();
        // Executable url
        String executableUrl = getExecutable().getFileUrl();
        portletLog.debug("Executable url = " + executableUrl);
        executable.setUrl(executableUrl);
        // Executable type
        executable.setType(org.gridlab.resmgmt.types.ExecutableTypeType.SINGLE);
        // All done
        return executable;
    }
***/

    private org.gridlab.resmgmt.Stdout buildStdout() {
        FileHandle thisStdout = getStdout();
        // If no stdout, return null
        if (thisStdout == null) {
            return null;
        }
        org.gridlab.resmgmt.Stdout stdout = new org.gridlab.resmgmt.Stdout();
        String stdoutUrl = thisStdout.getFileUrl();
        portletLog.debug("Stdout url = " + stdoutUrl);
        stdout.setUrl(stdoutUrl);
        // All done
        return stdout;
    }

    private org.gridlab.resmgmt.Stderr buildStderr() {
        FileHandle thisStderr = getStderr();
        // If no stderr, return null
        if (thisStderr == null) {
            return null;
        }
        org.gridlab.resmgmt.Stderr stderr = new org.gridlab.resmgmt.Stderr();
        String stderrUrl = thisStderr.getFileUrl();
        portletLog.debug("Stderr url = " + stderrUrl);
        stderr.setUrl(stderrUrl);
        // All done
        return stderr;
    }

    private org.gridlab.resmgmt.Arguments buildArguments() {
        Arguments thisArguments = getArguments();
        // If no arguments, return null
        if (thisArguments.getNumberOfArguments() == 0) {
            return null;
        }
        org.gridlab.resmgmt.Arguments arguments = new org.gridlab.resmgmt.Arguments();
        List argumentList = thisArguments.getArgumentList();
        for (int ii = 0; ii < argumentList.size(); ++ii) {
            Argument argument = (Argument)argumentList.get(ii);
            if (argument.getArgumentType() == Argument.FILE) {
                org.gridlab.resmgmt.File file = new org.gridlab.resmgmt.File();
                String fileUrl = argument.getFileUrl();
                portletLog.debug("Argument [" + ii + "] = " + fileUrl);
                file.setName(argument.getFileName());
                file.setUrl(argument.getFileUrl());
                file.setType(convertFileType(argument.getFileType()));
                arguments.addFile(file);
            } else {
                String value = argument.getValue();
                portletLog.debug("Argument [" + ii + "] = " + value);
                arguments.addValue(value);
            }
        }
        // All done
        return arguments;
    }

    private org.gridlab.resmgmt.types.FileTypeTypeType convertFileType(String value) {
        org.gridlab.resmgmt.types.FileTypeTypeType fileType = null;
        try {
            return org.gridlab.resmgmt.types.FileTypeTypeType.valueOf(value);
        } catch (Exception e) {
            portletLog.error("Error converting file type!", e);
            return  org.gridlab.resmgmt.types.FileTypeTypeType.IN;
        }
    }

    private org.gridlab.resmgmt.Environment buildEnvironment() {
        Environment thisEnvironment = getEnvironment();
        // If no environment variables, return null
        if (thisEnvironment.getNumberOfVariables() == 0) {
            return null;
        }
        org.gridlab.resmgmt.Environment environment = new org.gridlab.resmgmt.Environment();
        List environmentVariables = thisEnvironment.getVariableList();
        for (int ii = 0; ii < environmentVariables.size(); ++ii) {
            EnvironmentVariable environmentVariable
                    = (EnvironmentVariable)environmentVariables.get(ii);
            org.gridlab.resmgmt.Variable variable = new org.gridlab.resmgmt.Variable();
            variable.setName(environmentVariable.getName());
            variable.setContent(environmentVariable.getValue());
            environment.addVariable(variable);
        }
        return environment;
    }

    private org.gridlab.resmgmt.Resource buildResource() {
        boolean hasValues = false;
        // Resource contains everything else
        org.gridlab.resmgmt.Resource resource = new org.gridlab.resmgmt.Resource();
        // Host name
        String host = getHost();
        if (host != null) {
            resource.setHostname(host);
            hasValues = true;
        }
        // Job scheduler
        String jobScheduler = getJobScheduler();
        if (jobScheduler != null) {
            resource.setLocalrmname(jobScheduler);
            hasValues = true;
        }
        // Cpu count=
        long cpuCount = getCpuCount();
        if (cpuCount > 0) {
            resource.setCpucount(String.valueOf(cpuCount));
            hasValues = true;
        }
        // Cpu count=
        long minimumMemory = getMinimumMemory();
        if (minimumMemory > 0) {
            resource.setMemory(String.valueOf(minimumMemory));
            hasValues = true;
        }
        // If values were set return the resource
        if (hasValues) {
            return resource;
        }
        // Return null otherwise
        return null;
    }

    public static void main(String[] argv) {
        GrmsJobSpecification jobSpec = new GrmsJobSpecification();
        try {
            jobSpec.setExecutable("/bin/sleep");
        } catch (Exception e) {
            System.err.println(e);
        }
        Arguments arguments = new Arguments();
        arguments.addValueArgument("2000");
        jobSpec.setArguments(arguments);
        System.out.println(jobSpec.toString());
    }
}
