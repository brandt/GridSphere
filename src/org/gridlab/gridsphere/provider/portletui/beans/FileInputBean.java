/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.portlet.PortletRequest;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.FileItem;

import java.util.List;
import java.util.Iterator;
import java.io.File;
import java.io.IOException;

public class FileInputBean extends InputBean implements TagBean {

    public static final int MAX_UPLOAD_SIZE = 1024 * 1024;
    public static final String TEMP_DIR = "/tmp";

    public static final String SUBMIT_STYLE = "portlet-frame-text";

    public static String NAME = "fi";

    private FileItem savedFileItem = null;

    public FileInputBean() {
        super(NAME);
        this.cssStyle = SUBMIT_STYLE;
        this.inputtype = "file";
    }

    public FileInputBean(PortletRequest request, String beanId) throws IOException {
        super(NAME);
        this.cssStyle = SUBMIT_STYLE;
        this.inputtype = "file";
        this.request = request;
        this.beanId = beanId;
        createFileUpload(request);
    }

    protected void createFileUpload(PortletRequest req) throws IOException {

        // Create a new file upload handler
        FileUpload upload = new FileUpload();

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

        if (savedFileItem == null) throw new IOException("No file has been saved!");

        value = savedFileItem.getName();
        savedFileItem.getStoreLocation();

        System.err.println("saved file :" + value);
    }

    public File getFile() {
        if (savedFileItem != null) {
            return savedFileItem.getStoreLocation();
        } else {
            return null;
        }
    }

    public String getFileName() {
        if (savedFileItem != null) {
            return savedFileItem.getName();
        } else {
            return "";
        }
    }

    public long getFileSize() {
        if (savedFileItem != null) {
            return savedFileItem.getSize();
        } else {
            return 0;
        }
    }

    public String saveFile(String filePath) throws IOException {

        if (!filePath.endsWith("/")) filePath += "/";

        File file = new File(filePath);

        try {
            if (!file.exists()) file.createNewFile();
            //savedFileItem.write(filePath);
        } catch (Exception e) {
            throw new IOException("Unable to save file: " + e);
        }
        return filePath;
    }


}
