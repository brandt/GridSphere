/* 
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 */
package org.gridlab.gridsphere.core.persistence;

public interface ResultInterface {
    /**
     * Returns true if the Result has more Elements
     * @return true if the result has more elements otherwise false
     */
    boolean hasMore();

    /**
     * Returns the size of the result
     * @return size number of results
     * @throws QueryException if something went wrong
     */
    int size() throws QueryException;

    /**
     * Gets the next ResultObject
     * @return Object the next resultobject
     * @throws QueryException if there is no element left or some other occurs
     * @todo NoSuchElementException
     */
    Object next() throws QueryException;

    /**
     * closes the connection to the database and frees all ressources
     */
    void close();

    /**
     *  positions the pointer to the n-th row in the result
     *
     * @param row where to poistion the pointer
     * @throws QueryException if some error occurs
     */
    boolean absoult(int row) throws QueryException;
}
