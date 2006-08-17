/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: PersistenceManagerRdbms.java 4793 2006-05-17 20:35:20Z novotny $
 */
package org.gridlab.gridsphere.services.core.persistence;

import java.util.List;

public interface PersistenceManagerRdbms {
    
    /**
     * Creates a session to conduct operations on database
     *
     * @return session  Session to conduction operations on database
     * @throws PersistenceManagerException
     */
    public Session getSession() throws PersistenceManagerException;

    /**
     * Closes a session
     *
     * @throws PersistenceManagerException
     */
    public void closeSession() throws PersistenceManagerException;

    /**
     * Begins a transaction
     *
     * @throws PersistenceManagerException
     */
    public void beginTransaction() throws PersistenceManagerException;

    /**
     * Commits a transaction
     *
     * @throws PersistenceManagerException
     */
    public void commitTransaction() throws PersistenceManagerException;

    /**
     * Rolls back a transaction
     *
     * @throws PersistenceManagerException
     */
    public void rollbackTransaction() throws PersistenceManagerException;

    /**
     * Creates the given object in the permanent storage.
     *
     * @param object object to be saved
     * @throws PersistenceManagerException if the object could not be created
     */
    public void create(Object object) throws PersistenceManagerException;

    /**
     * Updates the given (already existing) object in the permanent storage.
     *
     * @param object Object to be updated
     * @throws PersistenceManagerException If object could not be updated
     */
    public void update(Object object) throws PersistenceManagerException;

    /**
     * Saves or updates the given (already existing) object in the permanent storage.
     *
     * @param object Object to be updated
     * @throws PersistenceManagerException If object could not be updated
     */
    public void saveOrUpdate(Object object) throws PersistenceManagerException;

    /**
     * Restores an object matching the query. If multiple are found the first
     * is returned.
     *
     * @param query Query describing the object which should be restored
     * @return object Returns queried object or null if none was found
     * @throws PersistenceManagerException
     */
    public Object restore(String query) throws PersistenceManagerException;

    /**
     * Restores objects from storage matching the query.
     *
     * @param query Query describing the objects
     * @return list List of objects from OQL query
     * @throws PersistenceManagerException If a persistence error occurs
     */
    public List restoreList(String query) throws PersistenceManagerException;

    /**
     * Restores objects from storage matching the query and the supplied query filter
     *
     * @param query Query describing the objects
     * @param queryFilter a query filter specifying a subset of the list of objects to return
     * @return list List of objects from OQL query
     * @throws PersistenceManagerException
     */
    public List restoreList(String query, QueryFilter queryFilter) throws PersistenceManagerException;

    /**
     *
     * @param query Query describing the objects
     * @return the number of objects returned by the query
     * @throws PersistenceManagerException
     */
    public int count(String query) throws PersistenceManagerException;

    /**
     * Deletes the given object from the storage.
     *
     * @param object Object to be deleted.
     * @throws PersistenceManagerException If a persistence error occurs
     */
    public void delete(Object object) throws PersistenceManagerException;

    /**
     * Deletes objects from the storage matching the given query.
     *
     * @param query Query describing the objects to be deleted
     * @throws PersistenceManagerException If a persistence error occurs
     */
    public void deleteList(String query) throws PersistenceManagerException;

    /**
     * Shuts down the PersistenceManager. Used to free any ressources
     * still used by it.
     *
     * @throws PersistenceManagerException
     */
    public void destroy() throws PersistenceManagerException;
}
