package org.gridlab.gridsphere.core.persistence.castor;

/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 *
 * Implementation for castor mysql
 *
 */

import org.apache.log4j.Category;
import org.exolab.castor.jdo.*;
import org.exolab.castor.mapping.MappingException;
import org.gridlab.gridsphere.core.persistence.*;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


public class PersistenceManagerRdbms implements PersistenceManagerInterface  {

    static Category cat = Category.getInstance(PersistenceManagerRdbms.class.getName());


    String ConnectionURL = null;
    String DatabaseName = null;
    String Query = null;


    /**
     * gets the query for the database
     *
     * @return returns the query string or null if not set
     */
    public String getQuery() {
        return Query;
    }

    /**
     * sets the query for the database
     *
     * @param query sets the query for the database
     */
    public void setQuery(String query) {
        Query = query;
    }

    /**
     * Loads the configuration for the database
     *
     * @param url filename of the dbconfig file
     */
    public void setConnectionURL(String url) {
        ConnectionURL = url;
    }

    /**
     * returns the connectionURL
     * @return connectionurl or null if not set
     */
    public String getConnectionURL() {
        return ConnectionURL;
    }

    /**
     * return the databasename of the current connection
     *
     * @return databasenase or null if not set
     */
    public String getDatabaseName() {
        return DatabaseName;
    }

    /**
     * sets the database name for the current connection
     *
     * @param databaseName name of the database
     */
    public void setDatabaseName(String databaseName) {
        DatabaseName = databaseName;
    }

    /**
     * returns a Databasehandle to the database
     *
     * @return databasehandle
     */
    private Database getDatabase() throws
            DatabaseNotFoundException, PersistenceException, MappingException {

        JDO.loadConfiguration(this.getConnectionURL());

        JDO jdo;
        Database db;

        jdo = new JDO(this.getDatabaseName());
        db = jdo.getDatabase();
        return db;

    }

    /**
     * checks if all variables have been set
     *
     * @return true if everything is ok; else false
     */
    private void checkSettings() throws ConfigurationException {

        String msg = null;

        if (getConnectionURL().equals(null)) { msg= "Connection URL not specified"; }
        if (getDatabaseName().equals(null)) { msg = "Databasename not specified";}

        //if (getQuery().equals(null)) { msg ="Query not specified";}

        if (msg!=null) {
            throw new ConfigurationException(msg);
        }
    }

    /**
     * Creates an object to the database
     *
     * @param object to be marshalled
     * @throws ConfigurationException if configuration is wrong
     * @throws CreateException is creation went wrong
     */
    public void  create(Object object) throws ConfigurationException, CreateException {
        checkSettings();
        Database db = null;
        try {
            db = getDatabase();
            db.begin();
            db.create(object);
            db.commit();
            db.close();
        } catch (PersistenceException e) {
            cat.error("PersistenceException " + e);
            throw new CreateException("Persistence Error "+e);
        } catch (MappingException e) {
            cat.error("MappingException " + e);
            throw new CreateException("Mapping Error "+e);
        }

    }


    /**
     * Updates a given object
     *
     * @param object to be updated
     * @throws ConfigurationException if configurations are not set
     * @throws UpdateExcpetion if updated failed
     */
    public void update(Object object) throws ConfigurationException, UpdateException {

        checkSettings();

        Database db = null;
        // get Database
        try {
            db = getDatabase();

            db.begin();
            db.update(object);
            db.commit();
            db.close();
        } catch (PersistenceException e) {
            cat.error("PersistenceException " + e);
            throw new UpdateException("Persistence Error: "+e);
        } catch (MappingException e) {
            cat.error("MappingException " + e);
            throw new UpdateException("Mapping Error: "+e);
        }
    }

    /**
     * Dummy for restore;
     */
    public List restoreList(String query) throws ConfigurationException, RestoreException {
        setQuery(query);
        return restoreList();
    }

    /**
     * restores objects from storage
     *
     * @param query String object containing OQL query
     * @throws ConfigurationException if configurations are not set
     * @throws RestoreExcpetion if restore failes for some reason
     * @returns list of objects from OQL query
     */
    public List restoreList() throws ConfigurationException, RestoreException {

        checkSettings();

        OQLQuery oql = null;
        Database db = null;
        QueryResults results = null;
        List list = new ArrayList();

        try {
            db = getDatabase();

            db.begin();
            oql = db.getOQLQuery(getQuery());
            results = oql.execute();
            while (results.hasMore()) {
                list.add(results.next());
            }
            db.commit();
            db.close();
        } catch (DatabaseNotFoundException e) {
            cat.error("Exception! " + e);
            throw new RestoreException("Database not found: "+e);
        } catch (PersistenceException e) {
            cat.error("PersistenceException!" + e);
            throw new RestoreException("Persistence Error: "+e);
        } catch (NoSuchElementException e) {
            cat.error("NoSuchElementException!" + e);
            throw new RestoreException("No such element error: "+e);
        } catch (MappingException e) {
            cat.error("MappingException!" + e);
            throw new RestoreException("Mapping Error: "+e);

        }

        return list;
    }

    /**
     * unmarshales the queried object
     *
     * @throws ConfigurationExcpetion if configuration failes
     * @throws RestoreException if restore failed
     * @return requested object defined by setQuery()
     */
    public Object restoreObject() throws ConfigurationException, RestoreException {

        return restoreList().get(0);

    }

    /**
     * deletes a the given object from storage
     *
     * @param object object to be deleted
     * @throws ConfigurationException if configurations are not set
     * @throws DeleteException if deletion failed
     */

    public void delete(Object object) throws ConfigurationException, DeleteException {

        checkSettings();

        Database db = null;
        try {
            db = getDatabase();
            db.begin();

            Class cl = object.getClass();
            String Oid = null;

            try {
                Method m = cl.getMethod("getOid", null);

                Oid = (String) m.invoke(object, null);
                cat.debug("got " + Oid + " from object via reflection");
            } catch (NoSuchMethodException e) {
                cat.info("Exception " + e);
            } catch (SecurityException e) {
                cat.info("Exception " + e);
            } catch (IllegalAccessException e) {
                cat.info("Exception " + e);
            } catch (IllegalArgumentException e) {
                cat.info("Exception " + e);
            } catch (InvocationTargetException e) {
                cat.info("Exception " + e);
            }
            Object deleteObject = db.load(object.getClass(), Oid);

            db.remove(deleteObject);
            db.commit();
            db.close();

        } catch (DatabaseNotFoundException e) {
            cat.error("Database not found " + e);
            throw new DeleteException("Database not found :"+e);
        } catch (ObjectNotPersistentException e) {
            cat.error("object not persistent " + e);
            throw new DeleteException("Object not persistent Error :"+e);
        } catch (TransactionNotInProgressException e) {
            cat.error("Transation not in progress " + e);
            throw new DeleteException("Transaction not in progress Error :"+e);
        } catch (PersistenceException e) {
            cat.error("Persistent error " + e);
            throw new DeleteException("Persistence Error :"+e);
        } catch (MappingException e) {
            cat.error("Mapping Exception " + e);
            throw new DeleteException("Mapping Error :"+e);
        }

    }

    /**
     * returns an object identified by class and object id
     *
     * @param cl class of the object
     * @param Oid object id of the object
     * @returns requested object or null if not found
     */
    public Object getObjectByOid(Class cl, String Oid) {

        Database db = null;
        Object object = null;
        try {
            db = getDatabase();
            db.begin();
            object = db.load(cl, Oid);
            db.commit();
        } catch (PersistenceException e) {
            cat.error("Exception " + e);
        } catch (MappingException e) {
            cat.error("Exception " + e);
        }

        return object;

    }
}
