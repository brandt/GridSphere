/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.core.persistence;

import java.util.List;

public interface PersistenceSession {

    /**
     * Begins a transaction.
     *
     * @throws PersistenceManagerException
     */
    void begin() throws PersistenceManagerException;

    /**
     * Commits the changes during the transaction.
     *
     * @throws PersistenceManagerException
     */
    void commit()  throws PersistenceManagerException;

    /**
     * Rolls back the operations.
     *
     * @throws PersistenceManagerException
     */
    void rollback()  throws PersistenceManagerException;

    /**
     * Creates the given object in the storage.
     *
     * @param object Obbject to be created.
     * @throws PersistenceManagerException
     */
    void create(Object object) throws PersistenceManagerException;

    /**
     * Updated the given (already exitsing) object in the storage.
     *
     * @param object Object to be updated.
     * @throws PersistenceManagerException
     */
    void update(Object object) throws PersistenceManagerException;

    /**
     * Deletes the given object from the storage.
     *
     * @param object Object to be deleted
     * @throws PersistenceManagerException
     */
    void delete(Object object) throws PersistenceManagerException;

    /**
     * Deletes a list of objects from the storage matching the given querystring.
     *
     * @param query Querystring describing the objects.
     * @throws PersistenceManagerException
     */
    void deleteList(String query) throws PersistenceManagerException;

    /**
     * Restore an object with the given query. If multiple are found the first one
     * is returned.
     *
     * @param query QueryString describing the object.
     * @return restored object
     * @throws PersistenceManagerException
     */
    Object restore(String query) throws PersistenceManagerException;

    /**
     * Returns a list of object which are described by the query.
     *
     * @param query QueryString describing the objects to be restored
     * @return List of objects matching the query.
     * @throws PersistenceManagerException
     */
    List restoreList(String query) throws PersistenceManagerException;

    /**
     * Closes the session.
     *
     * @throws PersistenceManagerException
     */
    void close() throws PersistenceManagerException;
}
