/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.security.acl.impl;

import java.util.Hashtable;
import java.util.Vector;

/**
 * AccessControlData maintains the access control information in the form of a generic nested hashtable of hashtables
 * The keys of the hashtable are the group names and the values are hashtables representing users as keys and the
 * values are hashtables respresenting roles. Only the outmost data structure needs to be synchronized.
 * Only one instance of the AccessControlData may be obtained
 */
public class AccessControlDataManager {

    private static AccessControlDataManager instance = null;
    private static AccessControlData acl = null;

    private AccessControlDataManager() {

        // AccessControlData is instantiated with a list of portal super-users.
        // This list should probably be maintained as an encrypted file since it doesn't store this info in the DB
        // So if a new user should be super add them to superusers file.
        Vector superusers = new Vector();
        superusers.add("me");
        acl = new AccessControlData(superusers);
        load();
    }

    /**
     * doSync is used to ensure only one thread has created an instance
     */
    private synchronized static void doSync() {
    }

    public static AccessControlData getACL() {
        if (instance == null) {
            doSync();
            instance = new AccessControlDataManager();
        }
        return acl;
    }

    public static void load() {
        // load ACL from DB
    }

    public static void save() {
        // save ACL to DB
    }

}
