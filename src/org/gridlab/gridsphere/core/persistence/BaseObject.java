/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 *
 * All objects which want to be persistent to a rdbms have to extend this
 * object.
 */

package org.gridlab.gridsphere.core.persistence;

import org.exolab.castor.jdo.Database;
import org.exolab.castor.jdo.TimeStampable;
import org.exolab.castor.jdo.Persistent;
import org.gridlab.gridsphere.core.persistence.castor.StringVector;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.PortletLog;

import java.util.Vector;
import java.util.List;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

public class BaseObject implements TimeStampable, Persistent {

    protected transient static PortletLog log = SportletLog.getInstance(BaseObject.class);

    /**
     * ObjectID an object is refrenced by.
     *
     * @primary-key
     * @sql-size 128
     * @get-method getOid
     * @set-method setOid
     * @sql-name objectid
     */
    private String ObjectID;

    /**
     * Timestamp for long transaction as we have it in castor.
     *
     * @get-method jdoGetTimeStamp
     * @set-method jdoSetTimeStamp
     * @sql-name timestamp
     */
    private transient long timestamp;

    public BaseObject() {
        super();
        UniqueID uid = UniqueID.getInstance();
        this.ObjectID = uid.get();
        timestamp = 0;
        //log.debug("constructor oid:" + ObjectID);
    }

    /**
     * Init the object.
     */
/*    public void init() {
        timestamp = 0;
        UniqueID uid = UniqueID.getInstance();
        this.ObjectID = uid.get();
        log.debug("Init ");
    }                           */

    /**
     * Returns the timestamp
     * @return the timestamp
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
        return ObjectID;
    }

    /**
     * sets the Object ID
     *
     * @param oid Object ID
     */
    public void setOid(String oid) {
        ObjectID = oid;
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
    public Vector convertToStringVector(Object object, List vector, Class cl) {
        log.info("converting from Vector to StringVector");

        Vector newVector = new Vector();

        for (int i = 0; i < vector.size(); i++) {

            Object v = new Object();

            try {
                v = cl.newInstance();

                Method m1 = cl.getMethod("setValue", new Class[]{String.class});
                Method m2 = cl.getMethod("setReference", new Class[]{Object.class});
                m1.invoke(v, new Object[]{vector.get(i)});
                m2.invoke(v, new Object[]{object});
            } catch (InstantiationException e) {
            } catch (IllegalAccessException e) {
            } catch (NoSuchMethodException e) {
            } catch (SecurityException e) {
            } catch (IllegalArgumentException e) {
            } catch (InvocationTargetException e) {
            }

            newVector.add(v);

            //log.info("converted to type :"+cl.getName());

        }
        return newVector;
    }

    /**
     * converts a vector of stringvectors to a normal vector of strings
     *
     * @param vector vector of stringvector objects
     * @return a vector of strings
     */
    public Vector convertToVector(List vector) {

        //log.info("converting from StringVector to Vector");

        Vector newVector = new Vector();

        for (int i = 0; i < vector.size(); i++) {
            StringVector sv = (StringVector) vector.get(i);
            newVector.add(sv.getValue());
        }

        return newVector;
    }

    /**
     * Returns true if the given user has the same object id as this object.
     *
     * @return User information represented as a String
     */
    public boolean equals(Object object) {
        return this.ObjectID.equals(((BaseObject) object).ObjectID);
    }
}

