/**
 * @author <a href="mailto:tkucz@icis.pcz.pl">Tomasz Kuczynski</a>
 * @version 0.4 2004/05/17
 */
package org.gridlab.gridsphere.services.core.secdir;

import org.gridlab.gridsphere.portlet.service.PortletService;

import java.io.File;
import java.io.InputStream;

/**
 * The <code>SecureDirectoryService</code> manages a temporary secure directory used for the storage of
 * user's persistent data. Resources are accessible by browser using URLs only when user is logged in.
 * Every user works using separate directory based on his userID.
 *
 * Parameter "appName" is root of filesystem f.e. "Commander" for CommanderPortlet.
 * More then one portlet can share one root. Number of roots is unlimited.
 * One portlet can use unlimited amount of roots.
 */

public interface SecureDirectoryService extends PortletService {

    /**
     * Returns URL that points at resource in SecureDirectory
     * @param userID described above
     * @param appName described above
     * @param resource is path, and filename of resource
     * @return URL that allows to access resource using Web Browser (f.e. include resource to the portlet as an image in <img> tag)
     */

    public String getFileUrl(String userID, String appName, String resource);

    /**
     * Returns URL that allows to download resource from SecureDirectory
     * @param userID described above
     * @param appName described above
     * @param resource is path, and filename of resource
     * @param saveAs filename for resource which will be sent to the browser
     * @return URL that allows to download resource using Web Browser (forces on browser save as dialog box)
     */

    public String getFileUrl(String userID, String appName, String resource, String saveAs);

    /**
     * Returns URL that allows to download resource from SecureDirectory (allows to force MIME type)
     * @param userID described above
     * @param appName described above
     * @param resource is path, and filename of resource
     * @param saveAs filename for resource which will be sent to the browser
     * @param contentType is MIME type of data accessible through URL
     * @return URL that allows to download resource using Web Browser (forces on browser save as dialog box)
     */

    public String getFileUrl(String userID, String appName, String resource, String saveAs, String contentType);

    /**
     * Returns array of descriptors of resources in some path in SecureDirectory (something like ls/dir command for SecureDirectoryService)
     * @param userID described above
     * @param appName described above
     * @param path directory which should be listed ("/" for main(root) directory)
     * @return array of descriptors of resources
     */

    public ResourceInfo[] getResourceList(String userID, String appName, String path);

    /**
     * Returns file object for the resource in SecureDirectory
     * @param userID described above
     * @param appName described above
     * @param resource is path, and filename of resource
     * @return file object for the resource in SecureDirectory or null if resource doesn't exist
     */

    public File getFile(String userID, String appName, String resource);

    /**
     * Rewrites data from stream to resource in SecureDirectory
     * @param userID described above
     * @param appName described above
     * @param resource is path, and filename of resource
     * @param input is input stream
     * @return success = true / fail = false
     */

    public boolean writeFromStream(String userID, String appName, String resource, InputStream input);

    /**
     * Rewrites data from file object to resource in SecureDirectory
     * @param userID described above
     * @param appName described above
     * @param resource is path, and filename of resource
     * @param inputFile is input file object
     * @return success = true / fail = false
     */

    public boolean writeFromFile(String userID, String appName, String resource, File inputFile);

    /**
     * Removes resource in SecureDirectory
     * @param userID described above
     * @param appName described above
     * @param resource is path, and filename of resource
     * @return success = true / fail = false
     */

    public boolean deleteResource(String userID, String appName, String resource);

    /**
     * Removes resource in SecureDirectory (allows to remove subdirectories recursively)
     * @param userID described above
     * @param appName described above
     * @param resource is path, and filename of resource
     * @param recursive set to true to delete directory and its subdirectories
     * @return success = true / fail = false
     */

    public boolean deleteResource(String userID, String appName, String resource, boolean recursive);

    /**
     * Removes resource in SecureDirectory (allows to remove subdirectories recursively/allows to remove parentdirectories recursively)
     * @param userID described above
     * @param appName described above
     * @param resource is path, and filename of resource
     * @param recursive set to true to delete directory and its subdirectories
     * @param delTree set to true to delete tree of all empty parentdirectores
     * @return success = true / fail = false
     */

    public boolean deleteResource(String userID, String appName, String resource, boolean recursive, boolean delTree);

    /**
     * Copies one resource to another one in SecureDirectory (checks if copying is save - if it is not copying directory to its subdirectory)
     * @param userID described above
     * @param appName described above
     * @param resourceSource is path, and filename of source resource
     * @param resourceDestination is path, and filename of destination resource
     * @return success = true / fail = false
     */

    public boolean saveResourceCopy(String userID, String appName, String resourceSource, String resourceDestination);

    /**
     * Moves one resource to another one in SecureDirectory (checks if moving is save - if it is not moving directory to its subdirectory)
     * @param userID described above
     * @param appName described above
     * @param resourceSource is path, and filename of source resource
     * @param resourceDestination is path, and filename of destination resource
     * @return success = true / fail = false
     */

    public boolean saveResourceMove(String userID, String appName, String resourceSource, String resourceDestination);

     /**
      * Checks if resource exists in SecureDirectory
      * @param userID described above
      * @param appName described above
      * @param resource is path, and filename of resource
      * @return true - exists / false doesn't exist
      */

    public boolean fileExists(String userID, String appName, String resource);

      /**
       * Checks if appName root exists in SecureDirectory (can create appName root)
       * @param userID described above
       * @param appName described above
       * @param create set to true to create if it doesn't exist
       * @return true - exists / false doesn't exist
       */

    public boolean appHasDirectory(String userID, String appName, boolean create);
}