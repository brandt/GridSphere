package org.gridlab.gridsphere.utils.ejp;

/*
 * @author <a href="oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 */


import java.util.List;

public interface PersistenceManagerInterface {

    /**
     * set connection
     * @param url containing information
     */

    void setConnectionURL(String url) throws Exception;

    /**
     * creates the given object
     *
     * @param object object to be created
     */
    void create(Object object) throws Exception ;

    /**
     * updates the given object
     *
     * @param object object to be updated
     */
    void update(Object object) throws Exception ;

    /**
     * restores a list of objects from connection URL
     *
     * @returns a Listobject containing the resultobjects
     */
    List restoreList() throws Exception ;


    /**
     * restores the first object (matching a query) from storage
     *
     * @returns the queried object
     */

    Object restoreObject() throws Exception ;

    /**
     * deletes an object from storage
     *
     * @param object to be deleted
     */
    void delete(Object object) throws Exception ;
}
