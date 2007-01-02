/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: FileInputBean.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.provider.portletui.beans;

import org.apache.commons.fileupload.FileItem;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * A <code>FileInputBean</code> provides a file upload element
 */
public class FileInputBean extends InputBean implements TagBean {

    public static final int MAX_UPLOAD_SIZE = 10 * 1024 * 1024;

    public static final String SUBMIT_STYLE = "portlet-form-button";

    private FileItem savedFileItem = null;
    private int maxUploadSize = MAX_UPLOAD_SIZE;

    /**
     * Constructs a default file input bean
     */
    public FileInputBean() {
        super(TagBean.FILEINPUT_NAME);
        this.cssClass = SUBMIT_STYLE;
        this.inputtype = "file";
    }

    /**
     * Constructs a file input bean from a portlet request and bean identifier
     *
     * @param beanId the bean identifier
     */
    public FileInputBean(String beanId) {
        this();
        this.beanId = beanId;
    }

    public FileInputBean(String beanId, FileItem fileItem) {
        this(beanId);
        savedFileItem = fileItem;
    }

    public FileItem getFileItem() {
        return savedFileItem;
    }

    public void setFileItem(FileItem item) {
        savedFileItem = item;
    }

    public int getMaxUploadSize() {
        return maxUploadSize;
    }

    public void setMaxUploadSize(int maxUploadSize) {
        this.maxUploadSize = maxUploadSize;
    }

    /**
     * Returns the uploaded file name
     *
     * @return the uploaded file name
     */
    public String getFileName() {
        if (savedFileItem != null) {
            return savedFileItem.getName();
        } else {
            return "";
        }
    }

    /**
     * Returns the uploaded file size
     *
     * @return the uploaded file size
     */
    public long getFileSize() {
        if (savedFileItem != null) {
            return savedFileItem.getSize();
        } else {
            return 0;
        }
    }

    /**
     * Saves the file to the supplied file location path
     *
     * @param filePath the path to save the file
     * @throws IOException if an I/O error occurs saving the file
     */
    public void saveFile(String filePath) throws IOException {

        String pathChar = File.separator;

        // Added by Chrono to check if directory needs creating
        int ddx = filePath.lastIndexOf(pathChar);
        String dirPath = filePath.substring(0, ddx);
        try {
            File dirCreate = new File(dirPath);
            dirCreate.mkdirs();
        } catch (Exception e) {
            System.err.println("Unable to create directory: " + dirPath);
        }

        // Chrono commented out necessary for windows usage (?)
        //if (!filePath.endsWith(pathChar)) filePath += pathChar;

        File file = new File(filePath);

        try {
            if (!file.exists()) file.createNewFile();
            if (savedFileItem != null) savedFileItem.write(file);
        } catch (Exception e) {
            throw new IOException("Unable to save file: " + e.getMessage());
        }
    }


    /**
     * Returns with the InputStream of savedFileItem
     *
     * @return InputStream
     * @throws IOException
     */
    public InputStream getInputStream() throws IOException {
        return (savedFileItem != null) ? savedFileItem.getInputStream() : null;
    }

    public String toEndString() {
        return "<input type=\"hidden\" name=\"maxfilesize\" value=\"" + maxUploadSize + "\"/>";
    }
}
