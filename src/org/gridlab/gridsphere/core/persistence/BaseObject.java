/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 *
 * All objects which want to be persistent to a rdbms have to extend this
 * object.
 */

package org.gridlab.gridsphere.core.persistence;

public class BaseObject  {

    public BaseObject() {
        super();
        //UniqueID uid = UniqueID.getInstance();
        this.ObjectID = null;
    }


    private String ObjectID;

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

    /**
     * Returns true if the given user has the same object id as this object.
     *
     * @return User information represented as a String
     */
    public boolean equals(Object object) {
        return this.ObjectID.equals(((BaseObject) object).ObjectID);
    }
}

