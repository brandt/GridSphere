/**
 * @author <a href="mailto:tkucz@icis.pcz.pl">Tomasz Kuczynski</a>
 * @version 0.7 2004/10/06
 */
package org.gridlab.gridsphere.services.core.secdir.impl;

import org.apache.oro.text.perl.Perl5Util;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.services.core.secdir.FileInfo;
import org.gridlab.gridsphere.services.core.secdir.SecureDirectoryService;
import org.gridlab.gridsphere.services.core.secdir.FileLocationID;

import java.io.*;
import java.net.URLEncoder;
import java.util.StringTokenizer;

public class SecureDirectoryServiceImpl implements SecureDirectoryService, PortletServiceProvider {
    private Perl5Util util = new Perl5Util();
    private static boolean inited = false;
    private final static int BUFFER_SIZE = 8 * 1024; //8 kB
    private static String secureDirPath;
    private static final String SECURE_SERVLET_MAPPING = "secure";
    protected PortletLog log = SportletLog.getInstance(SecureDirectoryServiceImpl.class);

    public void init(PortletServiceConfig config) {
        if (!inited) {
            secureDirPath = config.getServletContext().getRealPath("/WEB-INF/" + SECURE_SERVLET_MAPPING);
            File f = new File(secureDirPath);
            if (!f.exists()) {
                log.debug("Creating secure directory for users: " + secureDirPath);
                if (!f.mkdirs())
                    log.error("Unable to create directory" + secureDirPath);
            }
            inited = true;
        }
    }

    public void destroy() {
    }

    public String getFileUrl(FileLocationID fileLocationID) {
        return getDownloadFileUrl(fileLocationID, null, null);
    }

    public String getDownloadFileUrl(FileLocationID fileLocationID, String saveAs, String contentType) {
        String userID = fileLocationID.getUserID();
        String appName = fileLocationID.getCategory();
        String resource = fileLocationID.getFilePath();
        boolean shared = fileLocationID.isShared();
        if (userID == null || appName == null || resource == null || !inited)
            return null;
        String userDirectoryPath;
        resource = util.substitute("s!\\\\!/!g", resource);
        resource = util.substitute("s!^/!!", resource);
        if ((userDirectoryPath = getUserDirectoryPath(userID)) != null) {
            String filePath = userDirectoryPath + File.separator + appName + File.separator + resource;
            File file = new File(filePath);
            if (!file.exists() || file.isDirectory())
                return null;
            String queryString = "";
            if (saveAs != null && !saveAs.equals("")) {
                try {
                    queryString += "saveAs=" + URLEncoder.encode(saveAs, "UTF-8");
                } catch (Exception e) {
                }
            }
            if (contentType != null && !contentType.equals("")) {
                if (!queryString.equals(""))
                    queryString += "&";
                try {
                    queryString += "contentType=" + URLEncoder.encode(contentType, "UTF-8");
                } catch (Exception e) {
                }
            }
            if(shared){
                if (!queryString.equals(""))
                    queryString += "&";
                queryString += "shared=true";
            }
            resource = util.substitute("s!\\\\!/!g", resource);
            String url = SECURE_SERVLET_MAPPING + "/" + appName + "/" + resource + (queryString != null && !queryString.equals("") ? "?" + queryString : "");
            return url;
        } else {
            return null;
        }
    }

    public FileInfo[] getFileList(FileLocationID fileLocationID) {
        String userID = fileLocationID.getUserID();
        String appName = fileLocationID.getCategory();
        String path = fileLocationID.getFilePath();
        if (userID == null || appName == null || path == null || !inited)
            return null;
        String userDirectoryPath;
        //FOR SECURITY REASONS DO NOT CHANGE THE FOLLOWING REGEXPS (UNLESS YOU KNOW WHAT YOU ARE DOING)
        path = util.substitute("s!\\\\!/!g", path);
        do {
            path = util.substitute("s!^/!!", path);
            path = util.substitute("s!\\.\\.!.!g", path);
            path = util.substitute("s!^\\.[\\/]!!", path);
        } while (util.match("m!^/|\\.\\.|^\\.[\\/]!", path));
        if ((userDirectoryPath = getUserDirectoryPath(userID)) != null) {
            String dirPath = userDirectoryPath + File.separator + appName + File.separator + path;
            File directory = new File(dirPath);
            if (!directory.exists() || !directory.isDirectory())
                return null;
            String[] directoryList = directory.list();
            int start = 1; //(path.length() == 0 ? 0 : 1);
            FileInfo[] resourceList = new FileInfo[directoryList.length + start];
            if (start == 1) {
                resourceList[0] = new FileInfo("..", true, directory.lastModified(), 0);
            }
            for (int i = 0; i < directoryList.length; ++i) {
                String resourceName = directoryList[i];
                File file = new File(dirPath + resourceName);
                resourceList[i + start] = new FileInfo(resourceName, file.isDirectory(), file.lastModified(), file.length());
            }
            return resourceList;
        } else {
            return null;
        }
    }

    public File getFile(FileLocationID fileLocationID) {
        return getFile(fileLocationID, true);
    }

    public boolean deleteFile(FileLocationID fileLocationID) {
        return deleteFile(fileLocationID, false);
    }

    public boolean deleteFile(FileLocationID fileLocationID, boolean recursive) {
        return deleteFile(fileLocationID, recursive, false);
    }

    public boolean deleteFile(FileLocationID fileLocationID, boolean recursive, boolean delTree) {
        String userID = fileLocationID.getUserID();
        String appName = fileLocationID.getCategory();
        String resource = fileLocationID.getFilePath();
        //FOR SECURITY REASONS DO NOT CHANGE THE FOLLOWING REGEXPS (UNLESS YOU KNOW WHAT YOU ARE DOING)
        do {
            resource = util.substitute("s!\\.\\.!.!g", resource);
        } while (util.match("m!\\.\\.!", resource));
        if (deleteFile(userID, appName, resource, delTree)) {
            return true;
        } else
            return deleteDirectory(userID, appName, resource, recursive, delTree);
    }

    public boolean copyFile(FileLocationID srcFileLocationID, String fileDest) {
        String userID = srcFileLocationID.getUserID();
        String appName = srcFileLocationID.getCategory();
        String resourceSource = srcFileLocationID.getFilePath();
        if (!isInPath(resourceSource, fileDest))
            return copyResource(userID, appName, resourceSource, fileDest);
        return false;
    }

    public boolean moveFile(FileLocationID srcFileLocationID, String fileDest) {
        String userID = srcFileLocationID.getUserID();
        String appName = srcFileLocationID.getCategory();
        String resourceSource = srcFileLocationID.getFilePath();
        if (!isInPath(resourceSource, fileDest))
            if (copyResource(userID, appName, resourceSource, fileDest))
                return deleteFile(new FileLocationID(userID, appName, resourceSource), true);
        return false;
    }

    public void addFile(FileLocationID fileLocationID, InputStream inputStream) throws IOException {
        String userID = fileLocationID.getUserID();
        String appName = fileLocationID.getCategory();
        String resource = fileLocationID.getFilePath();
        File file = getFile(fileLocationID);
        if (file == null)
            throw new IOException("Unable to add file for (USERID=" + userID + ",CATEGORY=" + appName + ",FILENAME=" + resource + ") !");
        try {
            file.delete();
            if (!file.createNewFile())
                throw new IOException("Unable to add file for (USERID=" + userID + ",CATEGORY=" + appName + ",FILENAME=" + resource + ") ! Can't create file.");
            FileOutputStream output = new FileOutputStream(file);
            rewrite(inputStream, output);
            output.flush();
            output.close();
        } catch (Exception e) {
            throw new IOException("Unable to add file for (USERID=" + userID + ",CATEGORY=" + appName + ",FILENAME=" + resource + ") !" + e.getMessage());
        }
    }

    public void addFile(FileLocationID fileLocationID, File inputFile) throws IOException {
        FileInputStream input = new FileInputStream(inputFile);
        addFile(fileLocationID, input);
        input.close();
    }

    public boolean categoryExistsForUser(String userID, String category) {
        if (userID == null || category == null || !inited)
            return false;
        //FOR SECURITY REASONS DO NOT CHANGE THE FOLLOWING REGEXP (UNLESS YOU KNOW WHAT YOU ARE DOING)
        category = util.substitute("s![\\/.]!!g", category);
        String userDirectoryPath;
        if ((userDirectoryPath = getUserDirectoryPath(userID)) != null) {
            String filePath = userDirectoryPath + "/" + category;
            File file = new File(filePath);
            if (!file.exists()) {
                return false;
            } else if (!file.isDirectory()) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public void createCategoryForUser(String userID, String category) throws IOException {
        if (userID == null || category == null || !inited) {
            if (!inited)
                throw new IOException("Unable to create category for user ! SecureDirectory service is not initialized.");
            throw new IOException("Unable to create category for user (USERID=" + userID + ",CATEGORY=" + category + ")");
        }
        //FOR SECURITY REASONS DO NOT CHANGE THE FOLLOWING REGEXP (UNLESS YOU KNOW WHAT YOU ARE DOING)
        category = util.substitute("s![\\/.]!!g", category);
        String userDirectoryPath;
        if ((userDirectoryPath = getUserDirectoryPath(userID)) != null) {
            String filePath = userDirectoryPath + "/" + category;
            File file = new File(filePath);
            if (!file.exists()) {
                if (!file.mkdir()) {
                    log.error("Unable to create directory for application " + filePath);
                    throw new IOException("Unable to create category for user (USERID=" + userID + ",CATEGORY=" + category + ") ! Can't create directory.");
                }
            } else {
                return;
            }
        } else {
            throw new IOException("Unable to create category for user (USERID=" + userID + ",CATEGORY=" + category + ")! Can't get user directory.");
        }
    }

    public boolean fileExists(FileLocationID fileLocationID) {
        File file = getFile(fileLocationID, false);
        if (file == null || !file.exists())
            return false;
        return true;
    }

    public FileLocationID createFileLocationID(String userID, String category, String fileName) {
        return new FileLocationID(userID, category, fileName);
    }

    public FileLocationID createFileLocationID(String category, String fileName) {
        return new FileLocationID("GUEST", category, fileName, true);
    }

    private File getFile(FileLocationID fileLocationID, boolean createPath) {
        String userID = fileLocationID.getUserID();
        String appName = fileLocationID.getCategory();
        String resource = fileLocationID.getFilePath();
        if (userID == null || appName == null || resource == null || !inited)
            return null;
        //FOR SECURITY REASONS DO NOT CHANGE THE FOLLOWING REGEXPS (UNLESS YOU KNOW WHAT YOU ARE DOING)
        resource = util.substitute("s!\\\\!/!g", resource);
        do {
            resource = util.substitute("s!^/!!", resource);
            resource = util.substitute("s!\\.\\.!.!g", resource);
            resource = util.substitute("s!^\\.[\\/]!!", resource);
        } while (util.match("m!^/|\\.\\.|^\\.[\\/]!", resource));
        String userDirectoryPath;
        if ((userDirectoryPath = getUserDirectoryPath(userID)) != null) {
            String filePath = userDirectoryPath + File.separator + appName;
            File file = new File(filePath);
            if (!file.exists()) {
                if (!file.mkdir()) {
                    log.error("Unable to create directory for application " + filePath);
                    return null;
                }
            } else if (!file.isDirectory()) {
                return null;
            }
            boolean canCreate = true;
            if (util.match("m!/!", resource)) {
                String tmpPath = util.substitute("s!/[^/]*$!!", resource);
                File dirTree = new File(filePath + File.separator + tmpPath);
                if (!dirTree.exists()) {
                    if (createPath)
                        canCreate = dirTree.mkdirs();
                    else
                        return null;
                } else if (!dirTree.isDirectory())
                    canCreate = false;
            }
            if (canCreate) {
                filePath += File.separator + resource;
                file = new File(filePath);
                return file;
            } else {
                return null;
            }
        } else
            return null;
    }

    private boolean isInPath(String examineIsPath, String examineInPath) {
        examineIsPath = util.substitute("s! !!g", examineIsPath);
        examineInPath = util.substitute("s! !!g", examineInPath);
        examineIsPath = util.substitute("s!\\\\|/! !g", examineIsPath);
        examineInPath = util.substitute("s!\\\\|/! !g", examineInPath);
        StringTokenizer stringTokenizerIs = new StringTokenizer(examineIsPath);
        StringTokenizer stringTokenizerIn = new StringTokenizer(examineInPath);
        while (stringTokenizerIs.hasMoreTokens()) {
            String isToken = stringTokenizerIs.nextToken();
            if (stringTokenizerIn.hasMoreTokens()) {
                String inToken = stringTokenizerIn.nextToken();
                if (!isToken.equals(inToken))
                    return false;
            }
        }
        return true;
    }

    private void rewrite(InputStream input, OutputStream output) throws IOException {
        int numRead;
        byte[] buf = new byte[BUFFER_SIZE];
        while (!((numRead = input.read(buf)) < 0)) {
            output.write(buf, 0, numRead);
        }
    }

    private String getUserDirectoryPath(String userID) {
        String userDirectoryPath = secureDirPath + File.separator + userID;
        File userDirectory = new File(userDirectoryPath);
        if (!userDirectory.exists()) {
            log.debug("Creating directory for userID: " + userDirectoryPath);
            if (!userDirectory.mkdir()) {
                log.error("Unable to create directory" + userDirectoryPath);
                return null;
            }
        }
        return userDirectoryPath;
    }

    private boolean deleteDirectory(String userID, String appName, String resource, boolean recursive, boolean delTree) {
        if (userID == null || appName == null || resource == null || !inited)
            return false;
        resource = util.substitute("s!\\\\!/!g", resource);
        resource = util.substitute("s!^/!!", resource);
        String userDirectoryPath;
        if ((userDirectoryPath = getUserDirectoryPath(userID)) != null) {
            String filePath = userDirectoryPath + "/" + appName;
            File file = new File(filePath);
            if (!file.exists()) {
                if (!file.mkdir()) {
                    log.error("Unable to create directory for application " + filePath);
                    return false;
                }
            } else if (!file.isDirectory()) {
                return false;
            }
            file = new File(filePath + File.separator + resource);
            if (!file.isDirectory())
                return false;
            if (!recursive)
                return file.delete();
            if (!delTree)
                return deleteDirectory(file);
            boolean toRet = deleteDirectory(file);
            File secureDir = new File(secureDirPath);
            String parent = file.getParent();
            while (parent != null) {
                File dir = new File(parent);
                if (dir.isDirectory()) { //just to be sure ;-)
                    if (dir.compareTo(secureDir) == 0)
                        break;
                    if (!dir.delete())
                        break;
                } else
                    break;
                parent = dir.getParent();
            }
            return toRet;
        } else
            return false;
    }

    private boolean deleteDirectory(File f) {
        File[] files = f.listFiles();
        for (int i = 0; i < files.length; ++i) {
            if (files[i].isDirectory()) {
                deleteDirectory(files[i]);
            } else {
                files[i].delete();
            }
        }
        return f.delete();
    }

    private boolean deleteFile(String userID, String appName, String resource, boolean delTree) {
        File file = getFile(new FileLocationID(userID, appName, resource));
        if (file == null)
            return false;
        if (!file.delete())
            return false;
        if (delTree) {
            File secureDir = new File(secureDirPath);
            String parent = file.getParent();
            while (parent != null) {
                File dir = new File(parent);
                if (dir.isDirectory()) { //just to be sure ;-)
                    if (dir.compareTo(secureDir) == 0)
                        break;
                    if (!dir.delete())
                        break;
                } else
                    break;
                parent = dir.getParent();
            }
        }
        return true;
    }

    private boolean copyResource(String userID, String appName, String resourceSource, String resourceDestination) {
        if (userID == null || appName == null || resourceSource == null || resourceDestination == null || !inited)
            return false;
        resourceSource = util.substitute("s!\\\\!/!g", resourceSource);
        resourceSource = util.substitute("s!^/!!", resourceSource);
        String userDirectoryPath;
        if ((userDirectoryPath = getUserDirectoryPath(userID)) != null) {
            String filePath = userDirectoryPath + File.separator + appName;
            File file = new File(filePath);
            if (!file.exists()) {
                if (!file.mkdir())
                    log.error("Unable to create directory for application " + filePath);
                return false;
            } else if (!file.isDirectory()) {
                return false;
            }
            file = new File(filePath + File.separator + resourceSource);
            if (!file.isDirectory()) {
                try {
                    addFile(new FileLocationID(userID, appName, resourceDestination), file);
                    return true;
                } catch (IOException e) {
                    return false;
                }
            } else {
                resourceDestination += "\\\\";
                resourceDestination = util.substitute("s!\\\\!/!g", resourceDestination);
                resourceDestination = util.substitute("s!^/!!", resourceDestination);
                return copyDirectory(userID, appName, file, resourceDestination);
            }
        } else
            return false;
    }

    private boolean copyDirectory(String userID, String appName, File file, String destination) {
        File[] files = file.listFiles();
        boolean toRet = true;
        String directoryPath = getUserDirectoryPath(userID) + File.separator + appName + File.separator + destination;
        File dirTree = new File(directoryPath);
        if (!dirTree.exists())
            if (!dirTree.mkdirs())
                return false;
        for (int i = 0; i < files.length; ++i) {
            if (files[i].isDirectory()) {
                if (!copyDirectory(userID, appName, files[i], destination + files[i].getName() + File.separator))
                    toRet = false;
            } else {
                try {
                    addFile(new FileLocationID(userID, appName, destination + files[i].getName()), files[i]);
                } catch (IOException e) {
                    toRet = false;
                }
            }
        }
        return toRet;
    }
}