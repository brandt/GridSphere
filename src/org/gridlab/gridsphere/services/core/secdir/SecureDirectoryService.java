/**
 * @author <a href="mailto:tkucz@icis.pcz.pl">Tomasz Kuczynski</a>
 * @version 0.4 2004/05/17
 */
package org.gridlab.gridsphere.services.core.secdir;

import org.gridlab.gridsphere.portlet.service.PortletService;

import java.io.File;
import java.io.InputStream;

public interface SecureDirectoryService extends PortletService {
    public String getFileUrl(String userID, String appName, String resource);

    public String getFileUrl(String userID, String appName, String resource, String saveAs);

    public String getFileUrl(String userID, String appName, String resource, String saveAs, String contentType);

    public ResourceInfo[] getResourceList(String userID, String appName, String path);

    public File getFile(String userID, String appName, String resource);

    public boolean writeFromStream(String userID, String appName, String resource, InputStream input);

    public boolean writeFromFile(String userID, String appName, String resource, File inputFile);

    public boolean deleteResource(String userID, String appName, String resource);

    public boolean deleteResource(String userID, String appName, String resource, boolean recursive);

    public boolean deleteResource(String userID, String appName, String resource, boolean recursive, boolean delTree);

    public boolean saveResourceCopy(String userID, String appName, String resourceSource, String resourceDestination);

    public boolean saveResourceMove(String userID, String appName, String resourceSource, String resourceDestination);

    public boolean fileExists(String userID, String appName, String resource);

    public boolean appHasDirectory(String userID, String appName, boolean create);
}