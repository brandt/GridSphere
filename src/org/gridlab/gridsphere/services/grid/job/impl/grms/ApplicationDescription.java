/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.grid.job.impl.grms;

import java.util.Iterator;
import java.util.Vector;
import java.net.MalformedURLException;

import org.gridlab.gridsphere.services.grid.data.file.FileHandle;
import org.gridlab.gridsphere.services.grid.job.Arguments;
import org.gridlab.gridsphere.services.grid.job.Environment;
import org.gridlab.gridsphere.services.grid.job.EnvironmentVariable;
import org.gridlab.gridsphere.services.grid.job.Argument;

public class ApplicationDescription {

    private FileHandle executable = null;
    private Arguments arguments = null;
    private Environment environment = null;
    private FileHandle stdin = null;
    private FileHandle stdout = null;
    private FileHandle stderr = null;

    public ApplicationDescription() {
        init();
    }

    private void init() {
        this.arguments = new Arguments();
        this.environment = new Environment();
    }

    public FileHandle getExecutable() {
        return this.executable;
    }

    public String getExecutableUrl() {
        return this.executable.getFileUrl();
    }

    public void setExecutable(FileHandle executable) {
        this.executable = executable;
    }

    public void setExecutable(String url)
                throws MalformedURLException {
        this.executable = new FileHandle(url);
    }

    public Arguments getArguments() {
        return this.arguments;
    }

    public void setArguments(Arguments arguments) {
        this.arguments = arguments;
    }

    public Iterator iterateArguments() {
        return this.arguments.iterateArguments();
    }

    public Argument getArgument(int position) {
        return this.arguments.getArgument(position);
    }

    public int getArgumentType(int position) {
        return this.arguments.getArgumentType(position);
    }

    public void addArgument(Argument argument) {
        this.arguments.addArgument(argument);
    }

    public void addValueArgument(String value) {
        this.arguments.addValueArgument(value);
    }

    public void addFileArgument(String fileType, String fileName, String fileUrl)
            throws MalformedURLException {
        this.arguments.addFileArgument(fileType, fileName, fileUrl);
    }

    public void addInputFileArgument(String fileName, String fileUrl)
            throws MalformedURLException {
        this.arguments.addInputFileArgument(fileName, fileUrl);
    }

    public void addOutputFileArgument(String fileName, String fileUrl)
            throws MalformedURLException {
        this.arguments.addOutputFileArgument(fileName, fileUrl);
    }

    public void addInOutFileArgument(String fileName, String fileUrl)
            throws MalformedURLException {
        this.arguments.addInOutFileArgument(fileName, fileUrl);
    }

    public void removeArgument(int position) {
        this.arguments.removeArgument(position);
    }

    public void clearArguments() {
        this.arguments.clearArguments();
    }

    public int getNumberOfArguments() {
        return this.arguments.getNumberOfArguments();
    }

    public FileHandle getStdin() {
        return this.stdin;
    }

    public String getStdinUrl() {
        return this.stdin.getFileUrl();
    }

    public void setStdin(FileHandle stdin) {
        this.stdin = stdin;
    }

    public void setStdin(String url)
            throws MalformedURLException {
        this.stdin = new FileHandle(url);
    }

    public FileHandle getStdout() {
        return this.stdout;
    }

    public String getStdoutUrl() {
        return this.stdout.getFileUrl();
    }

    public void setStdout(FileHandle stdout) {
        this.stdout = stdout;
    }

    public void setStdout(String url)
            throws MalformedURLException {
        this.stdout = new FileHandle(url);
    }

    public FileHandle getStderr() {
        return this.stderr;
    }

    public String getStderrUrl() {
        return this.stderr.getFileUrl();
    }

    public void setStderr(FileHandle stderr) {
        this.stderr = stderr;
    }

    public void setStderr(String url)
            throws MalformedURLException {
        this.stderr = new FileHandle(url);
    }

    public Environment getEnvironment() {
        return this.environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public Iterator iterateEnvironmentVariables() {
        return this.environment.iterateVariables();
    }

    public EnvironmentVariable getEnvironmentVariable(String name) {
        return this.environment.getVariable(name);
    }

    public void putEnvironmentVariable(EnvironmentVariable variable) {
        this.environment.putVariable(variable);
    }

    public void putEnvironmentVariable(String name, String value) {
        this.environment.putVariable(name, value);
    }

    public void removeEnvironmentVariable(String name) {
        this.environment.removeVariable(name);
    }

    public void clearEnvironmentVariables() {
        this.environment.clearVariables();
    }

    public int getNumberOfEnvironmentVariables() {
        return this.environment.getNumberOfVariables();
    }
}
