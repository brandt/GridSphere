/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.beans;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.DiskFileUpload;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.globus.ftp.DataSourceStream;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

/**
 * A <code>FileInputBean</code> provides a file upload element
 */
public class FileInputBean extends InputBean implements TagBean {

    public static final int MAX_UPLOAD_SIZE = 1024 * 1024;
    public static final String TEMP_DIR = "/tmp";

    public static final String SUBMIT_STYLE = "portlet-form-button";

    public static String NAME = "fi";


    private FileItem savedFileItem = null;

    /**
     * Constructs a default file input bean
     */
    public FileInputBean() {
        super(NAME);
        this.cssStyle = SUBMIT_STYLE;
        this.inputtype = "file";
    }

    /**
     * Constructs a file input bean from a portlet request and bean identifier
     *
     * @param request the portlet request
     * @param beanId the bean identifier
     * @throws IOException if an I/O exception occurs
     */
    public FileInputBean(PortletRequest request, String beanId) throws IOException {
        super(NAME);
        this.cssStyle = SUBMIT_STYLE;
        this.inputtype = "file";
        this.request = request;
        this.beanId = beanId;
        createFileUpload();
    }

    /**
     * Constructs a file input bean from a portlet request and bean identifier
     *
     * @param request the portlet request
     * @param beanId the bean identifier
     * @throws IOException if an I/O exception occurs
     */
    public FileInputBean(PortletRequest request, String beanId,FileItem savedFileItem) throws IOException {
        super(NAME);
        this.cssStyle = SUBMIT_STYLE;
        this.inputtype = "file";
        this.request = request;
        this.beanId = beanId;
        this.savedFileItem=savedFileItem;
        createFileUpload();
    }

    /**
     * Creates a file upload handler
     *
     * @throws IOException
     */
    protected void createFileUpload() throws IOException {

        if (savedFileItem == null) {
            // Create a new file upload handler
            DiskFileUpload upload = new DiskFileUpload();

            // Set upload parameters
            upload.setSizeMax(MAX_UPLOAD_SIZE);
            upload.setRepositoryPath(TEMP_DIR);

            // Parse the request
            List items = null;
            try {
                items = upload.parseRequest(request);
            } catch (FileUploadException e) {
                name = "<b>Unable to parse uploaded file!</b>";
            }
            // Process the uploaded fields
            Iterator iter = items.iterator();

            try {
                while (iter.hasNext()) {
                    FileItem item = (FileItem) iter.next();
                    if (!item.isFormField()) {
                        savedFileItem = item;

                    }
                }
            } catch (Exception e) {
                throw new IOException("Unable to save file: " + e);
            }
        }

        if (savedFileItem == null) throw new IOException("No file has been saved!");

        value = savedFileItem.getName();
        //savedFileItem.getStoreLocation();

        System.err.println("saved file :" + value);
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

        if (!filePath.endsWith("/")) filePath += "/";

        File file = new File(filePath);

        if (savedFileItem != null) {
            try {
                if (!file.exists()) file.createNewFile();
                savedFileItem.write(file);
            } catch (Exception e) {
                throw new IOException("Unable to save file: " + e);
            }
        } else
            throw new IOException("Unable to save file: savedFileItem==null!");

    }
   /**
    * Returns with a DataSourceStream what can usefull at globus GSIFTPClient.put command
    * @return DataSourceStream
    * @throws IOException
    */
    public DataSourceStream getDataSourceStream() throws IOException{
        InputStream stream;
        stream= savedFileItem.getInputStream();
        DataSourceStream dataStream = new DataSourceStream(stream);
        return dataStream;
    }
}
