/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 */

package org.gridlab.gridsphere.core.persistence;

public class PersistenceException extends Exception {
    static org.apache.log4j.Category cat = org.apache.log4j.Category.getInstance(PersistenceException.class.getName());

    public PersistenceException() {
        super();
    }

    public PersistenceException(String msg) {
        super(msg);
    }

}

