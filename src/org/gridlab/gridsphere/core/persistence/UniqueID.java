/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 *
 * Generates a JavaVM wide unique ID.
 */


package org.gridlab.gridsphere.core.persistence;

public class UniqueID {

    static long current = System.currentTimeMillis();

    /**
     * Gets a unqiue ID
     * @return ID
     */
    public static synchronized String get() {
       return Long.toString(current++);
    }
}
