/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.user.impl;


/**
 * UserDataManager acts as a singleton for managing an instance of UserData
 */
public class UserDataManager {

    private static UserDataManager instance = null;
    private static UserData userData = null;

    private UserDataManager() {
        userData = new UserData();
        load();
    }

    /**
     * doSync is used to ensure only one thread has created an instance
     */
    private synchronized static void doSync() {
    }

    public static UserData getUserData() {
        if (instance == null) {
            doSync();
            instance = new UserDataManager();
        }
        return userData;
    }

    public static void load() {
        // load ACL from DB
    }

    public static void save() {
        // save ACL to DB
    }

}
