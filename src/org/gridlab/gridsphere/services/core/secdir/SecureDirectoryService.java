/**
 * @author <a href="mailto:tkucz@icis.pcz.pl">Tomasz Kuczynski</a>
 * @version 0.3 2004/03/30
 */
package org.gridlab.gridsphere.services.core.secdir;

import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.service.PortletService;

import java.io.File;
import java.io.InputStream;

public interface SecureDirectoryService extends PortletService {
    public String getFileUrl(User user, String appName, String resource);

    public String getFileUrl(User user, String appName, String resource, String saveAs);

    public String getFileUrl(User user, String appName, String resource, String saveAs, String contentType);

    public ResourceInfo[] getResourceList(User user, String appName, String path);

    public File getFile(User user, String appName, String resource);

    public boolean writeFromStream(User user, String appName, String resource, InputStream input);

    public boolean writeFromFile(User user, String appName, String resource, File inputFile);

    public boolean deleteResource(User user, String appName, String resource);

    public boolean deleteResource(User user, String appName, String resource, boolean recursive);

    public boolean deleteResource(User user, String appName, String resource, boolean recursive, boolean delTree);

    public boolean saveResourceCopy(User user, String appName, String resourceSource, String resourceDestination);

    public boolean saveResourceMove(User user, String appName, String resourceSource, String resourceDestination);

    public boolean fileExists(User user, String appName, String resource);

    public boolean appHasDirectory(User user, String appName, boolean create);
}