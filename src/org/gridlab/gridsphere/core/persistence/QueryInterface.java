/* 
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 */
package org.gridlab.gridsphere.core.persistence;

import org.gridlab.gridsphere.core.persistence.castor.Result;

public interface QueryInterface {
    /**
     * executes the query
     * @param command oqlquery string
     * @throws QueryException if something went wrong
     */
    Result execute(String command) throws QueryException;
}
