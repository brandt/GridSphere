package org.gridsphere.services.core.persistence.impl;

import org.gridsphere.portlet.PortletLog;
import org.gridsphere.portlet.impl.SportletLog;
import org.gridsphere.services.core.persistence.PersistenceManagerException;
import org.gridsphere.services.core.persistence.Session;
import org.gridsphere.services.core.persistence.QueryFilter;
import org.gridsphere.services.core.persistence.Transaction;

import java.util.List;

public class SessionImpl implements Session {

    private static PortletLog log = SportletLog.getInstance(SessionImpl.class);

    private net.sf.hibernate.Session hbSession = null;

    public SessionImpl(net.sf.hibernate.Session hbSession) {
        this.hbSession = hbSession;
    }

    /**
	 * Force the <tt>Session</tt> to flush. <tt>Transaction.commit()</tt> will calls
     * this method). <i>Flushing</i> is the process of synchronising the underlying
     *  persistent store with persistable state held in memory.
	 *
	 * @throws org.gridsphere.core.persistence.PersistenceManagerException
	 */
	public void flush() throws PersistenceManagerException {
        try {
            this.hbSession.flush();
        } catch (Exception e) {
            log.error("Unable to flush hibernate session", e);
            throw new PersistenceManagerException(e);
        }
    }

	/**
	 *  Create a new connection. This is used by applications which
	 * require long transactions.
	 *
	 * @throws org.gridsphere.core.persistence.PersistenceManagerException
	 */
	public void reconnect() throws PersistenceManagerException {
        try {
            this.hbSession.reconnect();
        } catch (Exception e) {
            log.error("Unable to reconnect session", e);
            throw new PersistenceManagerException(e);
        }
    }

	/**
	 * End the <tt>Session</tt> by disconnecting from the JDBC connection and
	 * cleaning up.
     *
	 * @throws org.gridsphere.core.persistence.PersistenceManagerException
	 */
	public void close() throws PersistenceManagerException {
        try {
            this.hbSession.close();
        } catch (Exception e) {
            log.error("Unable to close hibernate session", e);
            throw new PersistenceManagerException(e);
        }
    }

	/**
	 * Check if the <tt>Session</tt> is still open.
	 *
	 * @return boolean
	 */
	public boolean isOpen() {
        return this.hbSession.isOpen();
    }

	/**
	 * Check if the <tt>Session</tt> is currently connected.
	 *
	 * @return boolean
	 */
	public boolean isConnected() {
        return this.hbSession.isConnected();
    }

	/**
	 * Does this <tt>Session</tt> contain any changes which must be
	 * synchronized with the database? Would any SQL be executed if
	 * we flushed this session?
	 *
	 * @return boolean
	 */
	public boolean isDirty() throws PersistenceManagerException {
        try {
            return this.hbSession.isDirty();
        } catch (Exception e) {
            log.error("Unable to check if hibernate session isdirty", e);
            throw new PersistenceManagerException(e);
        }
    }

    /**
     * Creates the given object in the permanent storage.
     *
     * @param object object to be saved
     * @throws org.gridsphere.core.persistence.PersistenceManagerException if the object could not be created
     */
    public void create(Object object) throws PersistenceManagerException {
        try {
            this.hbSession.save(object);
        } catch (Exception e) {
            log.error("Unable to save object in hibernate session", e);
            throw new PersistenceManagerException(e);
        }
    }



    /**
	 * <tt>Create()</tt> or <tt>update()</tt> the given instance, depending upon the value of
	 * its identifier property. By default the instance is always saved. This behaviour may be
	 * adjusted by specifying an <tt>unsaved-value</tt> attribute of the identifier property
	 * mapping.
	 *
	 * @see org.gridsphere.core.persistence.Session#create(java.lang.Object)
	 * @param object a transient instance containing new or updated state
	 * @throws org.gridsphere.core.persistence.PersistenceManagerException
	 */
	public void createOrUpdate(Object object) throws PersistenceManagerException {
        try {
            this.hbSession.saveOrUpdate(object);
        } catch (Exception e) {
            log.error("Unable to save or update object in hibernate session", e);
            throw new PersistenceManagerException(e);
        }
    }

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
	 * @throws org.gridsphere.core.persistence.PersistenceManagerException
	 */
	public void update(Object object) throws PersistenceManagerException {
        try {
            this.hbSession.update(object);
        } catch (Exception e) {
            log.error("Unable to update object in hibernate session", e);
            throw new PersistenceManagerException(e);
        }
    }

	/**
	 * Remove a persistent instance from the datastore. The argument may be
	 * an instance associated with the receiving <tt>Session</tt> or a transient
	 * instance with an identifier associated with existing persistent state.
	 *
	 * @param object the instance to be removed
	 * @throws org.gridsphere.core.persistence.PersistenceManagerException
	 */
	public void delete(Object object) throws PersistenceManagerException {
        try {
            this.hbSession.delete(object);
        } catch (Exception e) {
            log.error("Unable to delete object in hibernate session", e);
            throw new PersistenceManagerException(e);
        }
    }


	/**
	 * Delete all objects returned by the query. 
	 *
	 * @param query the query string
	 * @throws org.gridsphere.core.persistence.PersistenceManagerException
	 */
	public void delete(String query) throws PersistenceManagerException {
        try {
            this.hbSession.delete(query);
        } catch (Exception e) {
            log.error("Unable to delete object in hibernate session", e);
            throw new PersistenceManagerException(e);
        }
    }

	/**
	 * Synchronize the state of the given instance with the underlying database.
	 *
	 * @param object a persistent or transient instance
	 * @throws org.gridsphere.core.persistence.PersistenceManagerException
	 */
	public void refresh(Object object) throws PersistenceManagerException {
        try {
            this.hbSession.refresh(object);
        } catch (Exception e) {
            log.error("Unable to refresh object in hibernate session", e);
            throw new PersistenceManagerException(e);
        }
    }

	/**
	 * Begin a unit of work and return the associated <tt>Transaction</tt> object.
	 * If a new underlying transaction is required, begin the transaction. Otherwise
	 * continue the new work in the context of the existing underlying transaction.
	 * The class of the returned <tt>Transaction</tt> object is determined by the
	 * property <tt>hibernate.transaction_factory</tt>.
	 *
	 * @return a Transaction instance
	 * @throws org.gridsphere.core.persistence.PersistenceManagerException
     * @see org.gridsphere.core.persistence.Transaction
     */
    public Transaction beginTransaction() throws PersistenceManagerException {
        try {
            Transaction tx =  new TransactionImpl(this.hbSession.beginTransaction());
            return tx;
        } catch (Exception e) {
            log.error("Unable to begin transaction in hibernate session", e);
            throw new PersistenceManagerException(e);
        }
    }

    /**
      * Restores an object matching the query. If multiple are found the first
      * is returned.

      * @param query Query describing the object which should be restored
      * @return object Returns queried object or null if none was found
      * @throws org.gridsphere.core.persistence.PersistenceManagerException
      */

    public Object restore(String query) throws PersistenceManagerException {
        try {
            net.sf.hibernate.Query q = hbSession.createQuery(query);
            List list = q.list();
            if (list.size() == 0) {
                return null;
            }
            return q.list().get(0);
        } catch (Exception e) {
            log.error("Unable to retrieve object in hibernate session with query " + query, e);
            throw new PersistenceManagerException(e);
        }
    }

    /**
      * Restores objects from storage matching the query.
      *
      * @param query Query describing the objects
      * @return list List of objects from OQL query
      * @throws org.gridsphere.core.persistence.PersistenceManagerException If a persistence error occurs
      */
    public List restoreList(String query) throws PersistenceManagerException {
        try {
            net.sf.hibernate.Query q = hbSession.createQuery(query);
            return q.list();
        } catch (Exception e) {
            log.error("Unable to retrieve list in hibernate session with query " + query, e);
            throw new PersistenceManagerException(e);
        }
    }

    /**
     * Restores objects from storage matching the query.
     *
     * @param query Query describing the objects
     * @return list List of objects from OQL query
     * @throws org.gridsphere.core.persistence.PersistenceManagerException If a persistence error occurs
     */
    public List restoreList(String query, QueryFilter queryFilter) throws PersistenceManagerException {
        try {
            net.sf.hibernate.Query q = hbSession.createQuery(query);
            if (queryFilter != null) {
                queryFilter.setFirstResult(queryFilter.getFirstResult());
                queryFilter.setMaxResults(queryFilter.getMaxResults());
            }
            return q.list();
        } catch (Exception e) {
            log.error("Unable to retrieve list in hibernate session with query " + query, e);
            throw new PersistenceManagerException(e);
        }
    }

    /**
	 * Completely clear the session. Evict all loaded instances and cancel all pending
	 * saves, updates and deletions. Do not close open iterators or instances of
	 * <tt>ScrollableResults</tt>.
	 */
	public void clear() {
        this.hbSession.clear();
    }
}
