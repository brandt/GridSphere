package org.gridlab.gridsphere.services.core.secdir;

/**
 * @author <a href="mailto:tkucz@icis.pcz.pl">Tomasz Kuczynski</a>
 * @version $Id$
 */
public class FileLocationID {

    private String userID = null;
    private String category = null;
    private String filePath = null;

    public FileLocationID(String userID, String category, String filePath) {
        this.userID = userID;
        this.category = category;
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getUserID() {
        return userID;
    }

    public String getCategory() {
        return category;
    }
}
