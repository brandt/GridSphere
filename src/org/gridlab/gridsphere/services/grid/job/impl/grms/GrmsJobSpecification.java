/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.grid.job.impl.grms;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.net.MalformedURLException;

import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.Marshaller;

import org.gridlab.gridsphere.services.grid.job.JobSpecification;
import org.gridlab.gridsphere.services.grid.job.Environment;
import org.gridlab.gridsphere.services.grid.job.Arguments;
import org.gridlab.gridsphere.services.grid.job.Argument;
import org.gridlab.gridsphere.services.grid.data.file.FileHandle;
import org.gridlab.gridsphere.services.grid.system.Local;

public class GrmsJobSpecification implements JobSpecification {

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
        StringWriter writer = new StringWriter();
        marshal(writer);
        //return writer.toString();
        String output = writer.toString();
        return postProcess(output);
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
        try {
            mapping.loadMapping(getJdoXmlConfiguration());
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
        return Local.getJdoCastorPath()
             + Local.FileSeparator
             + "GrmsJobSpecification.xml";
    }

}
