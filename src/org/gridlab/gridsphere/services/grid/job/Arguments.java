/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.grid.job;

import java.util.Iterator;
import java.util.Vector;
import java.net.MalformedURLException;

import org.gridlab.gridsphere.services.grid.data.file.FileHandle;

public class Arguments {

    private Vector argumentList = null;

    public Arguments() {
        init();
    }

    private void init() {
        argumentList = new Vector();
    }

    public Iterator iterateArguments() {
        return this.argumentList.iterator();
    }

    public Argument getArgument(int position) {
        return (Argument)this.argumentList.get(position);
    }

    public int getArgumentType(int position) {
        Argument argument = (Argument)this.argumentList.get(position);
        return argument.getArgumentType();
    }

    public void addArgument(Argument argument) {
        this.argumentList.add(argument);
    }

    public void addArgument(String text) {
        Argument argument = new Argument(text);
        this.argumentList.add(argument);
    }

    public void addValueArgument(String value) {
        ValueArgument argument = new ValueArgument();
        argument.setValue(value);
        this.argumentList.add(argument);
    }

    public void addFileArgument(String fileType, String fileName, String fileUrl)
            throws MalformedURLException {
        FileArgument argument = new FileArgument();
        argument.setFileType(fileType);
        argument.setFileName(fileName);
        argument.setFileUrl(fileUrl);
        this.argumentList.add(argument);
    }

    public void addInputFileArgument(String fileName, String fileUrl)
            throws MalformedURLException {
        FileArgument argument = new FileArgument();
        argument.setFileType(Argument.INPUT);
        argument.setFileName(fileName);
        argument.setFileUrl(fileUrl);
        this.argumentList.add(argument);
    }

    public void addOutputFileArgument(String fileName, String fileUrl)
            throws MalformedURLException {
        FileArgument argument = new FileArgument();
        argument.setFileType(Argument.OUTPUT);
        argument.setFileName(fileName);
        argument.setFileUrl(fileUrl);
        this.argumentList.add(argument);
    }

    public void addInOutFileArgument(String fileName, String fileUrl)
            throws MalformedURLException {
        FileArgument argument = new FileArgument();
        argument.setFileType(Argument.INOUT);
        argument.setFileName(fileName);
        argument.setFileUrl(fileUrl);
        this.argumentList.add(argument);
    }

    public void removeArgument(int position) {
        this.argumentList.remove(position);
    }

    public void clearArguments() {
        this.argumentList.clear();
    }

    public int getNumberOfArguments() {
        return this.argumentList.size();
    }

    public Vector getArgumentList() {
        return this.argumentList;
    }

    public void setArgumentList(Vector argumentList) {
        this.argumentList = argumentList;
    }
}
