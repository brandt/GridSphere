/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: FileManagerService.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.services.core.file;

import org.gridsphere.portlet.User;
import org.gridsphere.portlet.service.PortletService;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * The <code>UserStorageService</code> manages a temporary directory used for the storage of
 * user's persistent data.
 */
public interface FileManagerService extends PortletService {

    public String getLocationPath(User user, String fileName);

    //public void storeFile(User user, FileInputBean file, String fileName) throws IOException, Exception;

    public void deleteFile(User user, String fileName);

    public File getFile(User user, String fileName) throws FileNotFoundException;

    public String[] getUserFileList(User user);

}
