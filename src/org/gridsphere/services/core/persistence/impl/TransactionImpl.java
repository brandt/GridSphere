package org.gridsphere.services.core.persistence.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gridsphere.services.core.persistence.PersistenceManagerException;
import org.gridsphere.services.core.persistence.Transaction;

public class TransactionImpl implements Transaction {

    private Log log = LogFactory.getLog(TransactionImpl.class);

    private org.hibernate.Transaction hbTransaction = null;

    public TransactionImpl(org.hibernate.Transaction hbTransaction) {
        this.hbTransaction = hbTransaction;
    }

    /**
     * Flush the associated <tt>Session</tt> and end the unit of work.
     * This method will commit the underlying transaction if and only
     * if the transaction was initiated by this object.
     *
     * @throws PersistenceManagerException
     */
    public void commit() throws PersistenceManagerException {
        try {
            this.hbTransaction.commit();
        } catch (Exception e) {
            log.error("Unable to perform commit hibernate transaction", e);
            throw new PersistenceManagerException(e);
        }
    }

    /**
     * Force the underlying transaction to roll back.
     *
     * @throws PersistenceManagerException
     */
    public void rollback() throws PersistenceManagerException {
        try {
            this.hbTransaction.rollback();
        } catch (Exception e) {
            log.error("Unable to perform rollback hibernate transaction", e);
            throw new PersistenceManagerException(e);
        }
    }

    /**
     * Was this transaction rolled back or set to rollback only?
     *
     * @return boolean
     * @throws PersistenceManagerException
     */
    public boolean wasRolledBack() throws PersistenceManagerException {
        try {
            return this.hbTransaction.wasRolledBack();
        } catch (Exception e) {
            log.error("Unable to check if transaction was rolled back", e);
            throw new PersistenceManagerException(e);
        }
    }

    /**
     * Check if this transaction was successfully committed. This method
     * could return <tt>false</tt> even after successful invocation
     * of <tt>commit()</tt>.
     *
     * @return boolean
     * @throws PersistenceManagerException
     */
    public boolean wasCommitted() throws PersistenceManagerException {
        try {
            return this.hbTransaction.wasCommitted();
        } catch (Exception e) {
            log.error("Unable to check if transaction was committed", e);
            throw new PersistenceManagerException(e);
        }
    }
}
