/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 */

package org.gridlab.gridsphere.core.persistence.castor;

import org.exolab.castor.jdo.QueryResults;
import org.exolab.castor.jdo.OQLQuery;
import org.exolab.castor.jdo.PersistenceException;
import org.gridlab.gridsphere.core.persistence.ResultInterface;
import org.gridlab.gridsphere.core.persistence.QueryException;

import java.util.NoSuchElementException;

public class Result implements ResultInterface {


    private  QueryResults qr = null;

    /**
     * Result will be created by PersistenceManager only
     * @param query the castor OQLQuery
     * @param command the actual oqlquery
     */
    public Result(OQLQuery query, String command) throws QueryException {

        try {
            query.create(command);
            qr = query.execute();
        } catch (PersistenceException e) {
            throw new QueryException("Cannot exceute query "+e);
        }

    };

    /**
     * Returns true if the Result has more Elements
     * @return true if the result has more elements otherwise false
     */
    public boolean hasMore() {

        try {
            return qr.hasMore();
        } catch (PersistenceException e) {
            return false;
        }
    };

    /**
     * Returns the size of the result
     * @return size number of results
     * @throws QueryException if something went wrong
     */
    public int size() throws QueryException {

        try {
            return qr.size();
        } catch (PersistenceException e) {
            throw new QueryException("cannot determine size "+e);
        }
    };

    /**
     * Gets the next ResultObject
     * @return Object the next resultobject
     * @throws QueryException if there is no element left or some other occurs
     * @todo NoSuchElementException
     */
    public Object next() throws QueryException {
        try {
            return qr.next();
        } catch (PersistenceException e) {
            throw new QueryException("Persistence exception "+e);
        } catch (NoSuchElementException e) {
            throw new QueryException("No such element "+e);
        }
    };

    /**
     * closes the connection to the database and frees all ressources
     */
    public void close() {
        qr.close();
    }

    /**
     *  positions the pointer to the n-th row in the result
     *
     * @param row where to poistion the pointer
     * @throws QueryException if some error occurs
     */
    public boolean absoult(int row) throws QueryException {
        try {
            qr.absolute(row);
            return true;
        } catch (PersistenceException e) {

            throw new QueryException("Absoult error "+e);

        }
    }
}

