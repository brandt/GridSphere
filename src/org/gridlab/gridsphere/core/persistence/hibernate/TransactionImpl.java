package org.gridlab.gridsphere.core.persistence.hibernate;

import org.gridlab.gridsphere.core.persistence.Transaction;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;

/**
 * Created by IntelliJ IDEA.
 * User: czhang
 * Date: Aug 26, 2004
 * Time: 6:21:06 PM
 * To change this template use Options | File Templates.
 */
public class TransactionImpl implements Transaction {
    private net.sf.hibernate.Transaction hbTransaction = null;

    TransactionImpl(net.sf.hibernate.Transaction hbTransaction) {
        this.hbTransaction = hbTransaction;
    }
    /**
	 * Flush the associated <tt>Session</tt> and end the unit of work.
	 * This method will commit the underlying transaction if and only
	 * if the transaction was initiated by this object.
	 *
	 * @throws org.gridlab.gridsphere.core.persistence.PersistenceManagerException
	 */
	public void commit() throws PersistenceManagerException {
        try {
            this.hbTransaction.commit();
        } catch (Exception e) {
            throw new PersistenceManagerException(e);
        }
    }

	/**
	 * Force the underlying transaction to roll back.
	 *
	 * @throws org.gridlab.gridsphere.core.persistence.PersistenceManagerException
	 */
	public void rollback() throws PersistenceManagerException {
        try {
            this.hbTransaction.rollback();
        } catch (Exception e) {
            throw new PersistenceManagerException(e);
        }
    }

	/**
	 * Was this transaction rolled back or set to rollback only?
	 *
	 * @return boolean
	 * @throws org.gridlab.gridsphere.core.persistence.PersistenceManagerException
	 */
	public boolean wasRolledBack() throws PersistenceManagerException {
        try {
            return this.hbTransaction.wasRolledBack();
        } catch (Exception e) {
            throw new PersistenceManagerException(e);
        }
    }

	/**
	 * Check if this transaction was successfully committed. This method
	 * could return <tt>false</tt> even after successful invocation
	 * of <tt>commit()</tt>.
	 *
	 * @return boolean
	 * @throws org.gridlab.gridsphere.core.persistence.PersistenceManagerException
	 */
	public boolean wasCommitted() throws PersistenceManagerException {
        try {
            return this.hbTransaction.wasCommitted();
        } catch (Exception e) {
            throw new PersistenceManagerException(e);
        }
    }
}
