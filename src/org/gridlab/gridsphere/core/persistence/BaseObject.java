/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 *
 * All objects which want to be persistent to a rdbms have to extend this
 * object.
 */

package org.gridlab.gridsphere.core.persistence;

import org.exolab.castor.jdo.Database;
import org.gridlab.gridsphere.core.persistence.castor.StringVector;

import java.util.Vector;
import java.util.List;

public class BaseObject implements org.exolab.castor.jdo.TimeStampable, org.exolab.castor.jdo.Persistent {

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

    public void jdoPersistent(Database database) {
    }

    public void jdoTransient() {
    }

    public Class jdoLoad(short i) throws Exception {
        return null;
    }

    public void jdoStore(boolean b) throws Exception {
    }

    public void jdoBeforeCreate(Database database) throws Exception {
    }

    public void jdoAfterCreate() throws Exception {
    }

    public void jdoBeforeRemove() throws Exception {
    }

    public void jdoAfterRemove() throws Exception {
    }

    public void jdoUpdate() throws Exception {
    }


    /**
     * converts a vector with jus strings to a castor-saveable
     * vector (containing stringvectors)
     *
     * @param object the reference object
     * @param vector the vector to be converted
     * @return a vector of stringobjects
     */
    public Vector convertToStringVector(Object object, List vector) {

        Vector newVector = new Vector();

        for (int i=0;i<vector.size();i++) {
            StringVector sv = new StringVector(object,(String)vector.get(i));
            newVector.add(sv);
        }
        return newVector;
    }

    /**
     * converts a vector of stringvectors to a normal vector of strings
     *
     * @param a vector of stringvector objects
     * @return a vector of strings
     */
    public Vector convertToVector(List vector) {
        Vector newVector = new Vector();

        for (int i=0;i<vector.size();i++) {
            StringVector sv = (StringVector)vector.get(i);
            newVector.add(sv.getValue());
        }
        return newVector;
    }
}


