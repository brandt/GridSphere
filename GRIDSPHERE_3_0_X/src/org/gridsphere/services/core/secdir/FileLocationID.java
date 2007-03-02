package org.gridsphere.services.core.secdir;

/**
 * @author <a href="mailto:tkucz@icis.pcz.pl">Tomasz Kuczynski</a>
 * @version $Id: FileLocationID.java 4496 2006-02-08 20:27:04Z wehrens $
 */
public class FileLocationID {

    private String userID = null;
    private String category = null;
    private String filePath = null;
    private boolean shared = false;

    public FileLocationID(String userID, String category, String filePath) {
        this.userID = userID;
        this.category = category;
        this.filePath = filePath;
    }

    public FileLocationID(String userID, String category, String filePath, boolean shared) {
        this.userID = userID;
        this.category = category;
        this.filePath = filePath;
        this.shared = shared;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getUserID() {
        return userID;
    }

    public String getCategory() {
        return category;
    }

    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }
}
