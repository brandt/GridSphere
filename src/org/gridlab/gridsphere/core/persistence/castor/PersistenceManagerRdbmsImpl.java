package org.gridlab.gridsphere.core.persistence.castor;

/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 *
 * RDBMS Implementation for castor.
 *
 */

import org.exolab.castor.jdo.*;
import org.exolab.castor.jdo.PersistenceException;
import org.exolab.castor.mapping.MappingException;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerRdbms;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.core.persistence.BaseObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * This class provides methods for storing/getting data to/from rdbms storage.
 */
public class PersistenceManagerRdbmsImpl implements PersistenceManagerRdbms {

    protected transient static PortletLog log = SportletLog.getInstance(PersistenceManagerRdbmsImpl.class);
    protected JDO jdo = null;

    private PersistenceManagerRdbmsImpl() {}

    public PersistenceManagerRdbmsImpl(String databaseName, String databaseConfigFile) {
        log.debug("Creating PersistenceManagerRdbmsImpl");
        log.debug("Using '" + databaseName + "' as Databasename with the configfile '" + databaseConfigFile + "'");

        try {
            JDO.loadConfiguration(databaseConfigFile);
        } catch (MappingException e) {
            log.error("Unable to get JDO configuration: " + databaseConfigFile, e);
        }

        jdo = new JDO();
        jdo.setDatabaseName(databaseName);
        jdo.setTransactionManager(null);
    }

    /**
     * Creates an object to the database
     *
     * @param object to be marshalled
     * @throws PersistenceManagerException is a persistence error occurs
     */
    public void create(Object object) throws PersistenceManagerException {

        Database db = null;
        BaseObject b = (BaseObject)object;
        log.debug("Trying to save object with oid "+b.getOid()+" class: "+b.getClass().getName());

        try {
            db = jdo.getDatabase();
            db.begin();
            db.create(object);
            db.commit();
            db.close();
            db = null;
        } catch (PersistenceException e) {
            log.error("PersistenceException " + e);
            throw new PersistenceManagerException("Persistence Error " + e);
        }  finally {
            if (db != null) {
                try {
                    db.close();
                } catch (PersistenceException e) {
                    db = null;
                }
            }
        }

    }

    /**
     * Updates a given object
     *
     * @param object to be updated
     * @throws PersistenceManagerException is a persistence error occurs
     */
    public void update(Object object) throws PersistenceManagerException {
        Database db = null;
        BaseObject b = (BaseObject)object;
        log.debug("Trying to update object with oid "+b.getOid()+" class: "+b.getClass().getName());
        // get Database
        try {
            db = jdo.getDatabase();
            db.begin();
            db.update(object);
            db.commit();
            db.close();
            db = null;
        } catch (PersistenceException e) {
            log.error("PersistenceException " + e);
            throw new PersistenceManagerException("Persistence Error: " + e);
        }   finally {
            if (db != null) {
                try {
                    db.close();
                } catch (PersistenceException e) { }
                db = null;
            }
        }

    }

    /**
     * Restores objects from storage
     *
     * @return list of objects from OQL query
     * @throws PersistenceManagerException is a persistence error occurs
     */
    public List restoreList(String query) throws PersistenceManagerException {
        OQLQuery oql = null;
        Database db = null;
        QueryResults results = null;
        List list = new ArrayList();

        try {
            db = jdo.getDatabase();
            db.begin();
            oql = db.getOQLQuery(query);
            results = oql.execute();
            while (results.hasMore()) {
                list.add(results.next());
            }
            db.commit();
            db.close();
            db = null;
        } catch (DatabaseNotFoundException e) {
            log.error("Exception! " + e);
            throw new PersistenceManagerException("Database not found: " + e);
        } catch (PersistenceException e) {
            log.error("PersistenceException!" + e);
            throw new PersistenceManagerException("Persistence Error: " + e);
        } catch (NoSuchElementException e) {
            log.error("NoSuchElementException!" + e);
            throw new PersistenceManagerException("No such beans error: " + e);
        } finally {
            if (db != null) {
                try {
                    db.close();
                } catch (PersistenceException e) {}
                db = null;
            }
        }
        return list;
    }

    /**
     * Unmarshals the queried object
     *
     * @return the requested object defined by setQuery()
     * @throws PersistenceManagerException is a persistence error occurs
     */
    public Object restore(String query) throws PersistenceManagerException {
        List resultList = restoreList(query);
        if (resultList.size() > 0) {
            return resultList.get(0);
        } else {
            return null;
        }
    }

    /**
     * deletes a the given object from storage
     *
     * @param object object to be deleted
     * @throws PersistenceManagerException is a persistence error occurs
     */
    public void delete(Object object) throws PersistenceManagerException {
        Database db = null;
        try {
            db = jdo.getDatabase();
            db.begin();

            Class cl = object.getClass();
            String Oid = null;

            Method m = cl.getMethod("getOid", null);

            Oid = (String) m.invoke(object, null);
            log.debug("got " + Oid + " from object via reflection");

            Object deleteObject = db.load(object.getClass(), Oid);

            db.remove(deleteObject);
            db.commit();
            db.close();
            db = null;
        } catch (Exception e) {
            log.info("Exception " + e);
            throw new PersistenceManagerException("Mapping Error :" + e);
        }   finally {
            if (db != null) {
                try {
                    db.close();
                } catch (PersistenceException e) {}
                db = null;
            }
        }
    }

    /**
     * returns an object identified by class and object id
     *
     * @param cl class of the object
     * @param Oid object id of the object
     * @return requested object or null if not found
     */
    public Object getObjectByOid(Class cl, String Oid) throws PersistenceManagerException {

        Database db = null;
        Object object = null;
        try {
            db = jdo.getDatabase();
            db.begin();
            object = db.load(cl, Oid);
            db.commit();
            db.close();
            db = null;
        } catch (DatabaseNotFoundException e) {
            log.error("Exception! " + e);
            throw new PersistenceManagerException("Database not found: " + e);
        } catch (PersistenceException e) {
            log.error("Exception! " + e);
            throw new PersistenceManagerException("Persisetnce Exception: " + e);
        } finally {
            if (db != null) {
                try {
                    db.close();
                } catch (PersistenceException e) {}
                db = null;
            }
        }
        return object;
    }

    /**
     * deletes objects which are matching against the query
     *
     * @param query oql query
     * @throws PersistenceManagerException is a persistence error occurs
     */
    public void deleteList(String query) throws PersistenceManagerException {
        Database db = null;
        OQLQuery oql = null;
        QueryResults results = null;

        try {
            db = jdo.getDatabase();

            db.begin();
            oql = db.getOQLQuery(query);
            results = oql.execute();
            while (results.hasMore()) {
                db.remove(results.next());
            }
            db.commit();
            db.close();
            db = null;
        } catch (DatabaseNotFoundException e) {
            log.error("Exception! " + e);
            throw new PersistenceManagerException("Database not found: " + e);
        } catch (PersistenceException e) {
            log.error("PersistenceException!" + e);
            throw new PersistenceManagerException("Persistence Error: " + e);
        } catch (NoSuchElementException e) {
            log.error("NoSuchElementException!" + e);
            throw new PersistenceManagerException("No such beans error: " + e);
        } finally {
            if (db != null) {
                try {
                    db.close();
                } catch (PersistenceException e) {}
                db = null;
            }
        }
    }

    public void deleteList(ParameterList list) throws PersistenceManagerException {

        Database db = null;
        OQLQuery oql = null;
        QueryResults results = null;
        try {
            db = jdo.getDatabase();
            db.begin();
            while (list.hasMore()) {
                Parameter p = list.getNextParameter();
                String query = "select " + p.getTable() + " from " + p.getTable() + " where " + p.getCondition();

                oql = db.getOQLQuery(query);
                results = oql.execute();
                while (results.hasMore()) {
                    db.remove(results.next());
                }
            }
            db.commit();
            db.close();
            db = null;
        } catch (PersistenceException e) {
            //db.rollback();
            log.error("PersistenceException!" + e);
            throw new PersistenceManagerException("Persistence Error: " + e);
        } catch (NoSuchElementException e) {
            //db.rollback();
            log.error("NoSuchElementException!" + e);
            throw new PersistenceManagerException("NoSuchElement Error: " + e);
        } finally {
            if (db != null) {
                try {
                    db.close();
                } catch (PersistenceException e) {}
                db = null;
            }
        }
    }

}
