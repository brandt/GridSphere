package org.gridlab.gridsphere.core.persistence;

/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
import java.util.List;
import java.io.IOException;

public interface PersistenceManagerInterface {

    /**
     * Set the URL connection
     *
     * @param url containing information
     */
    void setConnectionURL(String url) throws PersistenceException;

    /**
     * Creates the given object
     *
     * @param object object to be created
     */
    void create(Object object) throws IOException, PersistenceException;

    /**
     * Updates the given object
     *
     * @param object object to be updated
     */
    void update(Object object) throws IOException, PersistenceException;

    /**
     * Restores a list of objects from connection URL
     *
     * @returns a Listobject containing the resultobjects
     */
    List restoreList() throws IOException, PersistenceException;


    /**
     * Restores the first object (matching a query) from storage
     *
     * @returns the queried object
     */
    Object restoreObject() throws IOException, PersistenceException;

    /**
     * Deletes an object from storage
     *
     * @param object to be deleted
     */
    void delete(Object object) throws IOException, PersistenceException;
}
