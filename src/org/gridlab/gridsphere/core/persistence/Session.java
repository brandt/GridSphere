package org.gridlab.gridsphere.core.persistence;


import java.sql.Connection;
import java.util.List;
import java.util.Iterator;
import java.io.Serializable;

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
	 * Copy the state of the given object onto the persistent object with the same
	 * identifier. If there is no persistent instance currently associated with
	 * the session, it will be loaded. Return the persistent instance. If the
	 * given instance is unsaved or does not exist in the database, save it and
	 * return it as a newly persistent instance. Otherwise, the given instance
	 * does not become associated with the session.
	 *
	 * @param object a transient instance with state to be copied
	 * @return an updated persistent instance
	 */
	public Object createOrUpdateCopy(Object object) throws PersistenceManagerException;

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
	 * Delete all objects returned by the query. Return the number of objects deleted.
	 *
	 * @param query the query string
	 * @return the number of instances deleted
	 * @throws PersistenceManagerException
	 */
	public int delete(String query) throws PersistenceManagerException;

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
	 * Execute a query and return the results in an iterator. If the query has multiple
	 * return values, values will be returned in an array of type <tt>Object[].</tt><br>
	 * <br>
	 * Entities returned as results are initialized on demand. The first SQL query returns
	 * identifiers only. So <tt>iterate()</tt> is usually a less efficient way to retrieve
	 * objects.
	 *
	 * @param query the query string
	 * @return an iterator
	 * @throws PersistenceManagerException
	 */
	public Iterator iterate(String query) throws PersistenceManagerException;

	/**
	 * Completely clear the session. Evict all loaded instances and cancel all pending
	 * saves, updates and deletions. Do not close open iterators or instances of
	 * <tt>ScrollableResults</tt>.
	 */
	public void clear();

}