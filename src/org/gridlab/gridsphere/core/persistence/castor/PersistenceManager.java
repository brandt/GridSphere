/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 */

package org.gridlab.gridsphere.core.persistence.castor;

import org.gridlab.gridsphere.core.persistence.*;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.jdo.Database;
import org.exolab.castor.jdo.JDO;
import org.exolab.castor.jdo.PersistenceException;
import org.exolab.castor.jdo.OQLQuery;

public class PersistenceManager implements PersistenceManagerInterface2 {
    static org.apache.log4j.Category cat = org.apache.log4j.Category.getInstance(PersistenceManager.class.getName());

    Transaction tx = null;
    Database db = null;

    /**
     * The great PersistenceManager
     * @param ConfigurationFile filename for the database.xml configurationfile for castor
     * @param Databasename which database to use in the database
     */
    public PersistenceManager(String ConfigurationFile, String Databasename) throws ConfigurationException {

        try {
            cat.info("Read from ConfigFile :"+ConfigurationFile+" and database "+Databasename);
            JDO.loadConfiguration(ConfigurationFile);
            JDO jdo;
            jdo = new JDO(Databasename);
            db = jdo.getDatabase();
        } catch (MappingException e) {
            throw new ConfigurationException("Error in configuration "+e);
        } catch (Exception e) {
            throw new ConfigurationException("Error in configuration "+e);
        }
        tx = new Transaction(db);

    }

    /**
     * returns the current Transaction
     *
     * @return Transaction the currrent transaction
     * @see Transaction
     */
    public Transaction getCurrentTransaction() {
        return tx;
    }


    /**
     * uupdates the object in the storage
     * @param object object to be updated
     * @throws UpdateException if something went wrong with the update
     */
    public void update(Object object) throws UpdateException {
        try {
            db.update(object);
        } catch (PersistenceException e) {
            throw new UpdateException("Problem with update "+e);
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
        } catch (PersistenceException e) {
            throw new CreateException("Problem with creation of the object "+e);
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
        } catch (PersistenceException e) {
            throw new DeleteException("Problem with deletion of the object "+e);
        }
    }

    /**
     * Gets a query object
     * @returns Query query object
     * @see Query
     */
    public Query getQuery() {

        Query query = new Query(db);
        return query;
    }

    /**
     * Creates an object to the database
     *
     * @param object to be marshalled
     * @throws CreateException is creation went wrong
     */
    public void tCreate(Object object) throws CreateException {
        Database db = null;
        try {
            db.begin();
            db.create(object);
            db.commit();
            db.close();
        } catch (PersistenceException e) {
            cat.error("PersistenceException " + e);
            throw new CreateException("Persistence Error "+e);
        }

    }

}

