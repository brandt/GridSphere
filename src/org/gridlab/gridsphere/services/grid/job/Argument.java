/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.grid.job;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;
import java.util.StringTokenizer;

import org.gridlab.gridsphere.services.grid.data.file.FileHandle;

public class Argument {

    public static final int VALUE = 0;
    public static final int FILE = 1;

    public static final String INPUT = "in";
    public static final String OUTPUT = "out";
    public static final String INOUT = "inout";

    private int argumentType = VALUE;
    private String value = null;
    private FileHandle file = null;
    private String fileType = null;
    private String fileName = null;

    public Argument() {
    }

    public Argument(String text) {
        try {
            setFileUrl(text);
            System.out.println("Argument: Created file argument " + text);
        } catch (MalformedURLException e) {
            setValue(text);
            System.out.println("Argument: Created value argument " + text);
        }
    }

    public int getArgumentType() {
        if (this.file == null) {
            return VALUE;
        }
        return FILE;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        if (!value.trim().equals("")) {
          this.value = value;
          this.argumentType = VALUE;
          // Clear file settings
          this.file = null;
          this.fileType = null;
          this.fileName = null;
        }
    }

    public FileHandle getFile() {
        return this.file;
    }

    public void setFile(FileHandle file) {
        this.file = file;
        System.out.println("Argument: Setting file type " + FILE);
        this.argumentType = FILE;
        // Clear value settings
        this.value = null;
    }

    public String getFileType() {
        return this.fileType;
    }

    public void setFileType(String type) {
        System.out.println("Argument: Setting file type " + type);
        this.fileType = type;
    }

    public String getFileName() {
        if (this.fileName == null) {
            if (this.file == null) {
                return null;
            }
            return getFile().getFileName();
        }
        return this.fileName;
    }

    public void setFileName(String name) {
        System.out.println("Argument: Setting file name " + name);
        this.fileName = name;
    }

    public String getFileUrl() {
        if (this.file == null) {
            return null;
        }
        return this.file.getFileUrl();
    }

    public void setFileUrl(String url)
            throws MalformedURLException {
        System.out.println("Argument: Setting file url " + url);
        FileHandle file = new FileHandle(url);
        String query = file.getFileQuery();
        if (query != null) {
            Hashtable variables = parseVariables(query);
            String type = (String)variables.get("type");
            //System.out.println("Argument: Setting file type = " + type);
            setFileType(type);
            String name = (String)variables.get("name");
            //System.out.println("Argument: Setting file name = " + name);
            setFileName(name);
        }
        setFile(file);
    }

    private Hashtable parseVariables(String query) {
        Hashtable assignments = new Hashtable();
        if ( ! query.equals("") ) {
            //System.out.println("Argument: Parsing query = " + query);
            StringTokenizer tokens = new StringTokenizer(query, "&");
            while (tokens.hasMoreTokens()) {
                String assignment = tokens.nextToken();
                //System.out.println("Argument: Query assignment " + assignment);
                int index = assignment.indexOf("=");
                if (index > -1) {
                    String name = assignment.substring(0, index);
                    //System.out.println("Argument: Variable name " + name);
                    String value = assignment.substring(index+1);
                    //System.out.println("Argument: Variable value " + value);
                    assignments.put(name, value);
                }
            }
        }
        return assignments;
    }
}
