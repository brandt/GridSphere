/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.impl.acl;

import java.util.Hashtable;
import java.util.Vector;
import java.security.acl.Group;

/**
 * AccessControlData maintains the access control information in the form of a generic nested hashtable of hashtables
 * The keys of the hashtable are the group names and the values are hashtables representing users as keys and the
 * values are hashtables respresenting roles. Only the outmost data structure needs to be synchronized.
 * Only one instance of the AccessControlData may be obtained
 */
public class AccessControlDataManager {

    private static AccessControlDataManager instance = null;
    private Hashtable hashtable = new Hashtable();
    private static AccessControlData acl = null;

    private AccessControlDataManager() {
        acl = new AccessControlData();
        load();
    }

    /**
     * doSync is used to ensure only one thread has created an instance
     */
    private synchronized static void doSync() {}

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
