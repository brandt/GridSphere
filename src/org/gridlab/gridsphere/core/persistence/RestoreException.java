/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 *
 * Is thrown when the restoration of an object failed.
 */

package org.gridlab.gridsphere.core.persistence;

public class RestoreException extends PersistenceManagerException {

    public RestoreException () {
        super();
    }

    public RestoreException (String msg) {
        super(msg);
    }
}

