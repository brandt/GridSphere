/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 */

package org.gridlab.gridsphere.core.persistence.castor;

import org.exolab.castor.jdo.Database;
import org.exolab.castor.jdo.OQLQuery;
import org.exolab.castor.jdo.QueryResults;
import org.gridlab.gridsphere.core.persistence.QueryException;
import org.gridlab.gridsphere.core.persistence.QueryInterface;

public class Query implements QueryInterface {


    OQLQuery query = null;

    /**
     * creates the Query object
     *
     */
    public Query(Database db) {

        query = db.getOQLQuery();

    };

    /**
     * executes the query
     * @param command oqlquery string
     * @throws QueryException if something went wrong
     */
    public Result execute(String command) throws QueryException {
        Result res = new Result(query, command);
        return res;
    } ;


}

