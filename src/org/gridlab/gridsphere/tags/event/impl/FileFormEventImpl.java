/*
 * @author <a href="wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.event.impl ;

import org.gridlab.gridsphere.tags.event.FormEvent;
import org.gridlab.gridsphere.tags.event.FileFormEvent;
import org.gridlab.gridsphere.event.ActionEvent;
import org.gridlab.gridsphere.tags.web.element.ElementBean;
import org.gridlab.gridsphere.tags.web.element.BaseNameValueBean;
import org.gridlab.gridsphere.tags.event.FileFormException;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletException;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.io.File;

public class FileFormEventImpl extends FormEventImpl implements FileFormEvent {

    private boolean haveFile = false;

    public FileFormEventImpl(ActionEvent evt) {
        super(evt);
    }

    public File saveFile(String filePath) throws FileFormException {

        if (!filePath.endsWith("/")) filePath += "/";

         // Create a new file upload handler
        FileUpload upload = new FileUpload();

        // Set upload parameters
        upload.setSizeMax(MAX_UPLOAD_SIZE);
        upload.setRepositoryPath(TEMP_DIR);

        // Parse the request
        List items = null;
        try {
            items = upload.parseRequest(event.getPortletRequest());
        } catch (FileUploadException e) {
            throw new FileFormException("Unable to parse uploaded file: ", e);
        }
        // Process the uploaded fields
        Iterator iter = items.iterator();
        File file = null;
        try {
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                if (item.isFormField()) {
                    item.write(filePath + item.getName());
                    file = item.getStoreLocation();
                }
            }
            haveFile = true;
        } catch (Exception e) {
            throw new FileFormException("Unable to save file", e);
        }
        return file;
    }

}
