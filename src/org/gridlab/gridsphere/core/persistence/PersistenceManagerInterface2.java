/* 
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 */
package org.gridlab.gridsphere.core.persistence;

import org.gridlab.gridsphere.core.persistence.castor.Transaction;
import org.gridlab.gridsphere.core.persistence.castor.Query;

public interface PersistenceManagerInterface2 {
    /**
     * returns the current Transaction
     *
     * @return Transaction the currrent transaction
     * @see Transaction
     */
    Transaction getCurrentTransaction();

    /**
     * uupdates the object in the storage
     * @param object object to be updated
     * @throws UpdateException if something went wrong with the update
     */
    void update(Object object) throws UpdateException;

    /**
     * creates an object in the persistence storage
     * @param object object to be created in the storage
     * @throws CreateException if something went wrong with the creation
     */
    void create(Object object) throws CreateException;

    /**
     * Deletes an object in the persistence storage
     * @param object object to be deleted
     * @throws DeleteException if something went wrong with the deletion of the object
     */
    void delete(Object object) throws DeleteException;

    /**
     * Gets a query object
     * @returns Query query object
     * @see Query
     */
    Query getQuery();
}
