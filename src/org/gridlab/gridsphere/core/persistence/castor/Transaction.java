/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 */

package org.gridlab.gridsphere.core.persistence.castor;

import org.exolab.castor.jdo.*;
import org.exolab.castor.mapping.MappingException;
import org.gridlab.gridsphere.core.persistence.ConfigurationException;
import org.gridlab.gridsphere.core.persistence.TransactionException;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.PortletLog;

public class Transaction {

    protected transient static PortletLog log = SportletLog.getInstance(Transaction.class);


    private Database db = null;

    /**
     * Transaction will be created from the persistenceManager only
     * @database the castor database object
     */
    public Transaction(Database database) {
        super();
        log.info("init transaction ");

        db = database;
    }

    /**
     * begins the transaction
     * @throws TransactionException if something failed
     */
    public void begin() throws TransactionException {
        try {
            db.begin();
            log.info(" Tx begin ");
        } catch (Exception e) {
            throw new TransactionException("Exception in begin "+e);
        }
    }

    /**
     * commits the changes since the transactin started
     * @throws TransactionException if something failed
     */
    public void commit() throws TransactionException {
        try {
            db.commit();
            log.info(" Tx commit");
            //db.close();
//        } catch (PersistenceException e) {
//            throw new TransactionException("Exception in commit (not in progress) "+e);
        } catch (TransactionNotInProgressException e) {
            throw new TransactionException("Exception in commit (not in progress) "+e);
        } catch (TransactionAbortedException e) {
            throw new TransactionException("Exception in commit (Abort) "+e);
        }
    }

    /**
     * Rolls back the action which where done since transaction started
     * @throws TransactionException if something failed
     */
    public void rollback() throws TransactionException {
        try {
            db.rollback();
        } catch (TransactionNotInProgressException e) {
            throw new TransactionException("Exception in rollback "+e);
        }
    }

    /**
     * closes a transaction
     * @throws TransactionException
     */
    public boolean close() {
        try {
            db.close();
            log.info( " Tx close ");
            return true;
        } catch (PersistenceException e) {
            log.error("Problem with Tx close "+e);
            return false;
        }
    }
}

