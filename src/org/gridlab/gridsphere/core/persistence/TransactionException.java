/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 */

package org.gridlab.gridsphere.core.persistence;

public class TransactionException extends PersistenceException {

    public TransactionException () {
        super();
    }

    public TransactionException(String msg) {
        super(msg);
    }

}

