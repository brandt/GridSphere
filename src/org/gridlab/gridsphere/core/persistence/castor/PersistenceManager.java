/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 */

package org.gridlab.gridsphere.core.persistence.castor;

import org.exolab.castor.jdo.*;
import org.exolab.castor.jdo.PersistenceException;
import org.exolab.castor.mapping.MappingException;
import org.gridlab.gridsphere.core.persistence.*;

public class PersistenceManager implements PersistenceInterface {
    static org.apache.log4j.Category cat = org.apache.log4j.Category.getInstance(PersistenceInterface.class.getName());

    //Transaction tx = null;
    private Database db = null;
    private JDO jdo = null;


    /**
     * The great PersistenceManager
     * @param ConfigurationFile filename for the database.xml configurationfile for castor
     * @param Databasename which database to use in the database
     */
    public PersistenceManager(String Configfile, String Databasename) throws ConfigurationException {

        try {
            JDO.loadConfiguration(Configfile);

            jdo = new JDO(Databasename);
        } catch (MappingException e) {
            throw new ConfigurationException("Error in configuration " + e);

        } catch (Exception e) {
            throw new ConfigurationException("Error in configuration " + e);
        }

        cat.info("PM init done");
    }

    /**
     * begins a transaction
     *
     * @throws TransactionException of something went wrong
     */
    public void begin() throws TransactionException {
        try {
            db = jdo.getDatabase();
            db.begin();
        } catch (PersistenceException e) {
            throw new TransactionException("error in begin " + e);
        }
    }

    /**
     * commits changes to the storage
     *
     * @throws TransactionException of something went wrong
     */
    public void commit() throws TransactionException {
        try {
            db.commit();
        } catch (TransactionNotInProgressException e) {
            throw new TransactionException("error in commit " + e);
        } catch (TransactionAbortedException e) {
            throw new TransactionException("error in commit " + e);
        }
    }

    /**
     * rollback the changes since begin()
     *
     * @throws TransactionException of something went wrong
     */
    public void rollback() throws TransactionException {
        try {
            db.rollback();
        } catch (TransactionNotInProgressException e) {
            throw new TransactionException("error in rollback " + e);

        }
    }

    /**
     * closes the transaction
     *
     * @throws TransactionException of something went wrong
     */
    public void close() {
        try {
            db.close();
            db = null;
        } catch (PersistenceException e) {
            cat.error("Persistence Exception " + e);
        }
    }

    /**
     * uupdates the object in the storage
     * @param object object to be updated
     * @throws UpdateException if something went wrong with the update
     */
    public void update(Object object) throws UpdateException {
        try {
            db.update(object);
            cat.info("update success");
        } catch (PersistenceException e) {
            throw new UpdateException("Problem with update " + e);
        }
    }

    /**
     * creates an object in the persistence storage
     * @param object object to be created in the storage
     * @throws CreateException if something went wrong with the creation
     */
    public void create(Object object) throws CreateException {

        try {
            db.create(object);
            cat.info("create success");
        } catch (PersistenceException e) {
            throw new CreateException("Problem with creation of the object " + e);
        }
    }

    /**
     * Deletes an object in the persistence storage
     * @param object object to be deleted
     * @throws DeleteException if something went wrong with the deletion of the object
     */
    public void delete(Object object) throws DeleteException {
        try {
            db.remove(object);
            cat.info("delete success");
        } catch (PersistenceException e) {
            throw new DeleteException("Problem with deletion of the object " + e);
        }
    }

    /**
     * Gets a query object
     * @returns Query query object
     * @see Query
     */
    public Query getQuery() {
        if (db.equals(null)) {
            cat.error("ALERT!\n\n\n\n\n\n--------------");
        }
        Query query = new Query(db);
        return query;
    }

}

