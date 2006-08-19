package org.gridsphere.services.core.persistence;


import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Chongjie Zhang
 * Date: Aug 26, 2004
 * Time: 4:08:52 PM
 * To change this template use Options | File Templates.
 */

public interface Session {
    /**
	 * Force the <tt>Session</tt> to flush. <tt>Transaction.commit()</tt> will calls
     * this method). <i>Flushing</i> is the process of synchronising the underlying
     *  persistent store with persistable state held in memory.
	 *
	 * @throws PersistenceManagerException
	 */
	public void flush() throws PersistenceManagerException;

	/**
	 *  Create a new connection. This is used by applications which
	 * require long transactions.
	 *
	 * @throws PersistenceManagerException
	 */
	public void reconnect() throws PersistenceManagerException;

	/**
	 * End the <tt>Session</tt> by disconnecting from the JDBC connection and
	 * cleaning up.
     *
	 * @throws PersistenceManagerException
	 */
	public void close() throws PersistenceManagerException;

	/**
	 * Check if the <tt>Session</tt> is still open.
	 *
	 * @return boolean
	 */
	public boolean isOpen();

	/**
	 * Check if the <tt>Session</tt> is currently connected.
	 *
	 * @return boolean
	 */
	public boolean isConnected();

	/**
	 * Does this <tt>Session</tt> contain any changes which must be
	 * synchronized with the database? Would any SQL be executed if
	 * we flushed this session?
	 *
	 * @return boolean
	 */
	public boolean isDirty() throws PersistenceManagerException;

    /**
     * Creates the given object in the permanent storage.
     *
     * @param object object to be saved
     * @throws PersistenceManagerException if the object could not be created
     */
    public void create(Object object) throws PersistenceManagerException;


	/**
	 * <tt>Create()</tt> or <tt>update()</tt> the given instance, depending upon the value of
	 * its identifier property. By default the instance is always saved. This behaviour may be
	 * adjusted by specifying an <tt>unsaved-value</tt> attribute of the identifier property
	 * mapping.
	 *
	 * @see Session#create(java.lang.Object)
	 * @param object a transient instance containing new or updated state
	 * @throws PersistenceManagerException
	 */
	public void createOrUpdate(Object object) throws PersistenceManagerException;

	/**
	 * Update the persistent instance with the identifier of the given transient
	 * instance. If there is a persistent instance with the same identifier,
	 * an exception is thrown.<br>
	 * <br>
	 * If the given transient instance has a <tt>null</tt> identifier, an exception
	 * will be thrown.<br>
	 * <br>
	 *
	 * @param object a transient instance containing updated state
	 * @throws PersistenceManagerException
	 */
	public void update(Object object) throws PersistenceManagerException;

	/**
	 * Remove a persistent instance from the datastore. The argument may be
	 * an instance associated with the receiving <tt>Session</tt> or a transient
	 * instance with an identifier associated with existing persistent state.
	 *
	 * @param object the instance to be removed
	 * @throws PersistenceManagerException
	 */
	public void delete(Object object) throws PersistenceManagerException;


	/**
	 * Delete all objects returned by the query.
	 *
	 * @param query the query string
	 * @throws PersistenceManagerException
	 */
	public void delete(String query) throws PersistenceManagerException;

	/**
	 * Synchronize the state of the given instance with the underlying database.
	 *
	 * @param object a persistent or transient instance
	 * @throws PersistenceManagerException
	 */
	public void refresh(Object object) throws PersistenceManagerException;

	/**
	 * Begin a unit of work and return the associated <tt>Transaction</tt> object.
	 * If a new underlying transaction is required, begin the transaction. Otherwise
	 * continue the new work in the context of the existing underlying transaction.
	 * The class of the returned <tt>Transaction</tt> object is determined by the
	 * property <tt>hibernate.transaction_factory</tt>.
	 *
	 * @return a Transaction instance
	 * @throws PersistenceManagerException
	 * @see Transaction
	 */
	public Transaction beginTransaction() throws PersistenceManagerException;

    /**
      * Restores an object matching the query. If multiple are found the first
      * is returned.

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
      * Restores objects from storage matching the query and the query filter.
      *
      * @param query Query describing the objects
     * * @param queryFilter a query filter describing which subset of the list to retrieve
      * @return list List of objects from OQL query
      * @throws PersistenceManagerException If a persistence error occurs
      */
    public List restoreList(String query, QueryFilter queryFilter) throws PersistenceManagerException;

    /**
	 * Completely clear the session. Evict all loaded instances and cancel all pending
	 * saves, updates and deletions. Do not close open iterators or instances of
	 * <tt>ScrollableResults</tt>.
	 */
	public void clear();

}