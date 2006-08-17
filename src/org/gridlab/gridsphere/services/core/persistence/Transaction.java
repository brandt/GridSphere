package org.gridlab.gridsphere.services.core.persistence;

/**
 * Created by IntelliJ IDEA.
 * User: czhang
 * Date: Aug 26, 2004
 * Time: 5:02:56 PM
 * To change this template use Options | File Templates.
 */

public interface Transaction {
    /**
	 * Flush the associated <tt>Session</tt> and end the unit of work.
	 * This method will commit the underlying transaction if and only
	 * if the transaction was initiated by this object.
	 *
	 * @throws PersistenceManagerException
	 */
	public void commit() throws PersistenceManagerException;

	/**
	 * Force the underlying transaction to roll back.
	 *
	 * @throws PersistenceManagerException
	 */
	public void rollback() throws PersistenceManagerException;

	/**
	 * Was this transaction rolled back or set to rollback only?
	 *
	 * @return boolean
	 * @throws PersistenceManagerException
	 */
	public boolean wasRolledBack() throws PersistenceManagerException;

	/**
	 * Check if this transaction was successfully committed. This method
	 * could return <tt>false</tt> even after successful invocation
	 * of <tt>commit()</tt>.
	 *
	 * @return boolean
	 * @throws PersistenceManagerException
	 */
	public boolean wasCommitted() throws PersistenceManagerException;
}
