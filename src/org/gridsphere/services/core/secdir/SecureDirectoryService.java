/**
 * @author <a href="mailto:tkucz@icis.pcz.pl">Tomasz Kuczynski</a>
 * @version 1.0 2004/08/16
 */
package org.gridsphere.services.core.secdir;

import org.gridsphere.portlet.service.PortletService;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;

/**
 * The <code>SecureDirectoryService</code> manages a temporary secure directory used for the storage of
 * user's persistent data. Resources are accessible by browser using URLs only when user is logged in.
 * Every user works using separate directory based on his user ID.
 * <p/>
 * Parameter "category" is root of filesystem f.e. "Commander" for CommanderPortlet.
 * More then one portlet can share one root. Number of roots is unlimited.
 * One portlet can use unlimited amount of roots.
 * <p/>
 * Contributed by Tomasz Kucynski  tomasz.kuczynski@icis.pcz.pl
 */

public interface SecureDirectoryService extends PortletService {

    /**
     * Creates a file location path to store files using the following strategy:
     * <p/>
     * location path = "/GUEST/<category>/<fileName>"
     * <p/>
     * The fileName
     *
     * @param category a category name define root of share virtual filesystem
     * @param fileName the name of the file, this can be any file path e.g. "context.xml"
     *                 or "/newdir/files/context.xml"
     * @return a constructed file location path from the supplied parameters
     */
    public FileLocationID createFileLocationID(String category, String fileName);

    /**
     * Creates a file location path to store files using the following strategy:
     * <p/>
     * location path = "/<userID>/<category>/<fileName>"
     * <p/>
     * The fileName
     *
     * @param userID   the user id
     * @param category a category name define root of user's virtual filesystem
     * @param fileName the name of the file, this can be any file path e.g. "context.xml"
     *                 or "/newdir/files/context.xml"
     * @return a constructed file location path from the supplied parameters
     */
    public FileLocationID createFileLocationID(String userID, String category, String fileName);

    /**
     * Returns URL that points at resource in SecureDirectory
     *
     * @param fileLocationID the file location id
     * @return URL that represents the location of the file to be used in a hyperlink, etc.
     */
    public String getFileUrl(FileLocationID fileLocationID);

    /**
     * Returns URL that allows to download resource from SecureDirectory
     *
     * @param fileLocationID the file location id
     * @param saveAs         filename for resource which will be sent to the browser
     * @param contentType    if non-null forces the content type that should be set by HTTP server
     * @return URL that allows to download resource using Web Browser (forces on browser save as dialog box)
     */
    public String getDownloadFileUrl(FileLocationID fileLocationID, String saveAs, String contentType);

    /**
     * Returns array of descriptors of resources in some path in SecureDirectory (something like ls/dir command for SecureDirectoryService)
     *
     * @param fileLocationID a file location id that includes a path
     * @return array of file information
     */
    public FileInfo[] getFileList(FileLocationID fileLocationID);

    /**
     * Returns file object for the resource in SecureDirectory
     *
     * @param fileLocationID the file location id
     * @return file object for the resource in SecureDirectory or null if resource doesn't exist
     */
    public File getFile(FileLocationID fileLocationID);

    /**
     * Adds resource to SecureDirectory
     *
     * @param fileLocationID the file location id
     * @param inputFile      java.io.File object to add
     * @throws java.io.IOException if fails to add resource
     */
    public void addFile(FileLocationID fileLocationID, File inputFile) throws IOException;

    /**
     * Adds resource to SecureDirectory
     *
     * @param fileLocationID the file location id
     * @param inputStream    java.io.InputStream object to add
     * @throws java.io.IOException if fails to add resource
     */
    public void addFile(FileLocationID fileLocationID, InputStream inputStream) throws IOException;

    /**
     * Removes resource in SecureDirectory
     *
     * @param fileLocationID the handle to the file to be deleted
     * @return success = true / fail = false
     */
    public boolean deleteFile(FileLocationID fileLocationID);

    /**
     * Removes resource in SecureDirectory (allows to remove subdirectories recursively)
     *
     * @param fileLocationID the handle to the file to be deleted
     * @param recursive      set to true to delete directory and its subdirectories
     * @return success = true / fail = false
     */
    public boolean deleteFile(FileLocationID fileLocationID, boolean recursive);

    /**
     * Removes resource in SecureDirectory (allows to remove subdirectories recursively/allows to remove parentdirectories recursively)
     *
     * @param fileLocationID the handle to the file to be deleted
     * @param recursive      set to true to delete directory and its subdirectories
     * @param delTree        set to true to delete tree of all empty parentdirectores
     * @return success = true / fail = false
     */
    public boolean deleteFile(FileLocationID fileLocationID, boolean recursive, boolean delTree);

    /**
     * Copies one resource to another one in SecureDirectory (checks if copying is save - if it is not copying directory to its subdirectory)
     *
     * @param srcFileLocationID source file location id
     * @param fileDest          the name of the destination file, this can be any file path e.g. "context.xml"
     *                          or "/newdir/files/context.xml"
     * @return success = true / fail = false
     */
    public boolean copyFile(FileLocationID srcFileLocationID, String fileDest);

    /**
     * Moves one resource to another one in SecureDirectory (checks if moving is save - if it is not moving directory to its subdirectory)
     *
     * @param srcFileLocationID source file location id
     * @param fileDest          the name of the destination file, this can be any file path e.g. "context.xml"
     *                          or "/newdir/files/context.xml"
     * @return success = true / fail = false
     */
    public boolean moveFile(FileLocationID srcFileLocationID, String fileDest);

    /**
     * Checks if resource exists in SecureDirectory
     *
     * @param fileLocationID source file location id
     * @return exists = true / doesn't exist = false
     */
    public boolean fileExists(FileLocationID fileLocationID);

    /**
     * Checks if category exists for user
     *
     * @param userID   the user id
     * @param category a category name define root of user's virtual filesystem
     * @return true - category exists/false - category doesn't exist
     */
    public boolean categoryExistsForUser(String userID, String category);

    /**
     * Creates category for user
     *
     * @param userID   the user id
     * @param category a category name define root of user's virtual filesystem
     * @throws java.io.IOException - if method fails
     */
    public void createCategoryForUser(String userID, String category) throws IOException;
}