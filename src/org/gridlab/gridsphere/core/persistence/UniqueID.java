/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 *
 * Generates a JavaVM wide unique ID.
 */


package org.gridlab.gridsphere.core.persistence;

import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.PortletLog;

import java.util.Random;

public class UniqueID {

    private static PortletLog log = SportletLog.getInstance(UniqueID.class);
    private static UniqueID instance = new UniqueID();

    private UniqueID() {
        super();
    }

    public static UniqueID getInstance() {
        return instance;
    }

    static long current = System.currentTimeMillis();
    private long counter = 0;

    /**
     * Gets a unqiue ID.
     * @return ID
     */
    public synchronized String get() {
        counter++;
        String random = Long.toString(current) + "0000" + counter;
        return random;
    }
}
