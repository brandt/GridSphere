/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 */

package org.gridlab.gridsphere.core.persistence;

public class TransactionException extends Exception {
    static org.apache.log4j.Category cat = org.apache.log4j.Category.getInstance(TransactionException.class.getName());

    public TransactionException () {
        super();
    }

    public TransactionException(String msg) {
        super(msg);
    }

}

