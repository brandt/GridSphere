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

    public static String NAME = "fi";

    public FileInputBean() {
        super(NAME);
        this.inputtype = "file";
    }

    public FileInputBean(PortletRequest request, String beanId) {
        super(NAME);
        this.inputtype = "file";
        this.request = request;
        this.beanId = beanId;
    }

    public String saveFile(String filePath) throws IOException {

        if (!filePath.endsWith("/")) filePath += "/";

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
            throw new IOException("Unable to parse uploaded file: " + e);
        }
        // Process the uploaded fields
        Iterator iter = items.iterator();
        File file = new File(filePath);


        int i = 0;
        try {
            if (!file.exists()) file.createNewFile();
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();

                if (!item.isFormField()) {
                    System.err.println("fuxxy " + item.getFieldName() + "   " + item.getName() + " " + item.getStoreLocation().getAbsolutePath());
                    filePath = filePath + item.getName();
                    item.write(filePath);
                    //file = item.getStoreLocation();
                    System.err.println("Writing to location : " + filePath);
                }
            }
        } catch (Exception e) {
            throw new IOException("Unable to save file: " + e);
        }
        return filePath;
    }


}
