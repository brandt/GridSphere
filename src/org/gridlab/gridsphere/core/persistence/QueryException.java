/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 */

package org.gridlab.gridsphere.core.persistence;

public class QueryException extends Exception {
    static org.apache.log4j.Category cat = org.apache.log4j.Category.getInstance(QueryException.class.getName());

    public QueryException() {
        super();
    }

    public QueryException(String msg) {
        super(msg);
    }
}

