package org.gridlab.gridsphere.core.persistence.castor;

/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 *
 * Implementation for castor mysql
 *
 */

import org.exolab.castor.jdo.*;
import org.exolab.castor.jdo.PersistenceException;
import org.exolab.castor.mapping.MappingException;
import org.gridlab.gridsphere.core.persistence.*;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfig;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfigProperties;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.io.FileReader;
import java.io.FileInputStream;
import java.io.InputStream;


public class PersistenceManagerRdbms {

    protected transient static PortletLog log = SportletLog.getInstance(PersistenceManagerRdbms.class);

    private static PersistenceManagerRdbms instance = new PersistenceManagerRdbms();
    protected String Query = new String();
    protected boolean databaseCreated = false;
    protected JDO jdo = null;
    protected String Url = null;
    //protected PersistencePropertiesRdbms props = new PersistencePropertiesRdbms();

    // @todo need to check settings for rollback !!

    private PersistenceManagerRdbms() {
        super();
        log.info("Entering PM");
        String DatabaseName = GridSphereConfig.getInstance().getProperty(GridSphereConfigProperties.PERSISTENCE_DBNAME);
        String ConnectionURL = GridSphereConfig.getInstance().getProperty(GridSphereConfigProperties.PERSISTENCE_CONFIGFILE);
        log.info("Using '" + DatabaseName + "' as Databasename with the configfile '" + ConnectionURL + "'");
        this.Url = ConnectionURL;
        try {
            JDO.loadConfiguration(Url);
            jdo = new JDO(DatabaseName);
            jdo.setTransactionManager(null);
        } catch (MappingException e) {
            log.error("Unable to get JDO: ",e);
        }
    }

    public static PersistenceManagerRdbms getInstance() {
        return instance;
    }

    /**
     * Return the connectionURL
     *
     * @return filename
     */
    public String getConnectionURL() {
        return Url;
    }

    /**
     * Sets the connection URL
     *
     * @param url
     *
     */
    public void setConnectionURL(String url) {
        Url = url;
    }

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
     * checks if all variables have been set
     *
     */
    private void checkSettings() throws PersistenceManagerException {

        String msg = null;

        if (getConnectionURL().equals(null))
            throw new PersistenceManagerException("Connection URL not specified");
    }

    /**
     * Creates an object to the database
     *
     * @param object to be marshalled
     * @throws ConfigurationException if configuration is wrong
     * @throws CreateException is creation went wrong
     */
    public void create(Object object) throws PersistenceManagerException {
        checkSettings();
        Database db = null;

        //SportletGroup sa = (SportletGroup)object;
        //log.info("Name: "+sa.getName());

        try {
            db = jdo.getDatabase();
            db.begin();
            db.create(object);
            db.commit();
            db.close();
        } catch (PersistenceException e) {
            log.error("PersistenceException " + e);
            throw new PersistenceManagerException("Persistence Error " + e);
        }

    }


    /**
     * Updates a given object
     *
     * @param object to be updated
     * @throws ConfigurationException if configurations are not set
     * @throws UpdateException if updated failed
     */
    public void update(Object object) throws PersistenceManagerException {

        checkSettings();

        Database db = null;
        // get Database
        try {
            db = jdo.getDatabase();
            db.begin();
            db.update(object);
            db.commit();
            db.close();
        } catch (PersistenceException e) {
            log.error("PersistenceException " + e);
            throw new PersistenceManagerException("Persistence Error: " + e);
        }
    }


    /**
     * restores objects from storage
     *
     * @param query String object containing OQL query
     * @throws ConfigurationException if configurations are not set
     * @throws RestoreException if restore failes for some reason
     * @return list of objects from OQL query
     */
    public List restoreList(String query) throws PersistenceManagerException {
        setQuery(query);
        return restoreList();
    }

    /**
     * restores objects from storage
     *
     * @throws ConfigurationException if configurations are not set
     * @throws RestoreException if restore failes for some reason
     * @return list of objects from OQL query
     */
    public List restoreList() throws PersistenceManagerException {

        checkSettings();

        OQLQuery oql = null;
        Database db = null;
        QueryResults results = null;
        List list = new ArrayList();

        try {
            db = jdo.getDatabase();
            db.begin();
            oql = db.getOQLQuery(getQuery());
            results = oql.execute();
            while (results.hasMore()) {
                list.add(results.next());
            }
            db.commit();
            db.close();
        } catch (DatabaseNotFoundException e) {
            log.error("Exception! " + e);
            throw new PersistenceManagerException("Database not found: " + e);
        } catch (PersistenceException e) {
            log.error("PersistenceException!" + e);
            throw new PersistenceManagerException("Persistence Error: " + e);
        } catch (NoSuchElementException e) {
            log.error("NoSuchElementException!" + e);
            throw new PersistenceManagerException("No such element error: " + e);
        }

        return list;
    }

    /**
     * unmarshales the queried object
     *
     * @throws ConfigurationException if configuration failes
     * @throws RestoreException if restore failed
     * @return requested object defined by setQuery()
     */
    public Object restoreObject() throws PersistenceManagerException {
        List resultList = restoreList();
        if (resultList.size() > 0) {
            return resultList.get(0);
        } else {
            return null;
        }
    }

    public Object restoreObject(String oql) throws PersistenceManagerException {
        this.setQuery(oql);
        return restoreObject();

    }


    /**
     * deletes a the given object from storage
     *
     * @param object object to be deleted
     * @throws ConfigurationException if configurations are not set
     * @throws DeleteException if deletion failed
     */

    public void delete(Object object) throws PersistenceManagerException {

        checkSettings();

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

        } catch (NoSuchMethodException e) {
            log.info("Exception " + e);
            throw new PersistenceManagerException("Mapping Error :" + e);
        } catch (SecurityException e) {
            log.info("Exception " + e);
            throw new PersistenceManagerException("Mapping Error :" + e);
        } catch (IllegalAccessException e) {
            log.info("Exception " + e);
            throw new PersistenceManagerException("Mapping Error :" + e);
        } catch (IllegalArgumentException e) {
            log.info("Exception " + e);
            throw new PersistenceManagerException("Mapping Error :" + e);
        } catch (InvocationTargetException e) {
            log.info("Exception " + e);
            throw new PersistenceManagerException("Mapping Error :" + e);
        } catch (PersistenceException e) {
            log.info("Exception " + e);
            throw new PersistenceManagerException("Mapping Error :" + e);
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
        } catch (DatabaseNotFoundException e) {
            log.error("Exception! " + e);
            throw new PersistenceManagerException("Database not found: " + e);
        } catch (PersistenceException e) {
            log.error("Exception! " + e);
            throw new PersistenceManagerException("Persisetnce Exception: " + e);
        }

        return object;

    }

    /**
     * deletes objects which are matching against the query
     *
     * @param query oql query
     * @throws ConfigurationException
     * @throws DeleteException  if something went wrong during deletion
     */
    public void deleteList(String query) throws PersistenceManagerException {

        checkSettings();

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
        } catch (DatabaseNotFoundException e) {
            log.error("Exception! " + e);
            throw new PersistenceManagerException("Database not found: " + e);
        } catch (PersistenceException e) {
            log.error("PersistenceException!" + e);
            throw new PersistenceManagerException("Persistence Error: " + e);
        } catch (NoSuchElementException e) {
            log.error("NoSuchElementException!" + e);
            throw new PersistenceManagerException("No such element error: " + e);
        }
    }

    public void deleteList(ParameterList list) throws PersistenceManagerException {

        Database db = null;
        OQLQuery oql = null;
        QueryResults results = null;

        checkSettings();

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
        } catch (PersistenceException e) {
            //db.rollback();
            log.error("PersistenceException!" + e);
            throw new PersistenceManagerException("Persistence Error: " + e);
        } catch (NoSuchElementException e) {
            //db.rollback();
            log.error("NoSuchElementException!" + e);
            throw new PersistenceManagerException("NoSuchElement Error: " + e);
        }
    }

}
