/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.file;

import org.gridlab.gridsphere.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfig;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfigProperties;

import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * The <code>UserStorageService</code> manages a temporary directory used for the storage of
 * user's persistent data.
 */
public interface FileManagerService extends PortletService {

    public String getLocationPath(User user, String fileName);

    public void storeFile(User user, File file, String fileName) throws IOException;

    public void deleteFile(User user, String fileName);

    public File getFile(User user, String fileName) throws FileNotFoundException;

    public String[] getUserFileList(User user);

}
