/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 */

package org.gridlab.gridsphere.core.persistence;

public class QueryException extends PersistenceException {

    public QueryException() {
        super();
    }

    public QueryException(String msg) {
        super(msg);
    }
}

