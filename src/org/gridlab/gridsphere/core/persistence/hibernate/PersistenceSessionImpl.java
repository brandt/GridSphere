/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.core.persistence.hibernate;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.core.persistence.PersistenceSession;

import java.util.List;

/**
 *
 */
public class PersistenceSessionImpl implements PersistenceSession {

    private Session session = null;
    private Transaction tx = null;

    public PersistenceSessionImpl(Session session) {
        super();
        this.session = session;
    }

    public void begin() throws PersistenceManagerException {
        try {
            tx = session.beginTransaction();
        } catch (HibernateException e) {
            throw new PersistenceManagerException(e);
        }

    }

    public void commit() throws PersistenceManagerException {
        try {
            if (tx != null) {
                tx.commit();
            }
        } catch (HibernateException e) {
            throw new PersistenceManagerException(e);
        }
    }

    public void rollback() throws PersistenceManagerException {
        try {
            if (tx != null) {
                tx.rollback();
            }
        } catch (HibernateException e) {
            throw new PersistenceManagerException(e);
        }
    }

    public void create(Object o) throws PersistenceManagerException {
        try {
            if (session != null) {
                session.save(o);
            }
        } catch (HibernateException e) {
            throw new PersistenceManagerException(e);
        }
    }

    public void update(Object o) throws PersistenceManagerException {
        try {

            if (session != null) {
                session.update(o);
            }
        } catch (HibernateException e) {
            throw new PersistenceManagerException(e);
        }
    }

    public void delete(Object object) throws PersistenceManagerException {
        try {
            session.delete(object);
        } catch (HibernateException e) {
            throw new PersistenceManagerException(e);
        }
    }

    public void deleteList(String query) throws PersistenceManagerException {
        try {
            session.delete(query);
        } catch (HibernateException e) {
            throw new PersistenceManagerException(e);
        }
    }

    public Object restore(String query) throws PersistenceManagerException {
        List result = restoreList(query);
        if (result != null && result.size() >= 1) {
            return result.get(0);
        } else {
            return null;
        }
    }

    public List restoreList(String query) throws PersistenceManagerException {
        Query q = null;
        List result = null;
        try {
            q = session.createQuery(query);
            result = q.list();
        } catch (HibernateException e) {
            throw new PersistenceManagerException(e);
        }
        return result;
    }

    /**
     * Not usable anymore...setSession needed..
     * @throws PersistenceManagerException
     */

    public void close() throws PersistenceManagerException {
        try {
            if (session != null) {
                session.close();
            }
        } catch (HibernateException e) {
            throw new PersistenceManagerException(e);
        } finally {
            session = null;
            tx = null;
        }
    }
}