/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 *
 * All objects which want to be persistent to a rdbms have to extend this
 * object.
 */

package org.gridlab.gridsphere.core.persistence;

public class BaseObject implements org.exolab.castor.jdo.TimeStampable {

    static org.apache.log4j.Category cat = org.apache.log4j.Category.getInstance(BaseObject.class.getName());

    // the needed Oid
    private String Oid;
    // timestamp
    private long timestamp;

    /**
     * init the object
     */
    public void init() {
        timestamp = 0;
    }

    /**
     * returns the timestamp
     */
    public long jdoGetTimeStamp() {
        return timestamp;
    }

    /**
     * sets the timestamp
     *
     * @param l timestamp
     */
    public void jdoSetTimeStamp(long l) {
        timestamp = l;
    }

    /**
     * gets the Object ID
     *
     * @return Object ID
     */
    public String getOid() {
        return Oid;
    }

    /**
     * sets the Object ID
     *
     * @param oid Object ID
     */
    public void setOid(String oid) {
        Oid = oid;
    }
}

