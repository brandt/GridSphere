/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 *
 * Is thrown when the update of an object failed.
 */

package org.gridlab.gridsphere.core.persistence;

public class UpdateException extends PersistenceException {

    public UpdateException() {
        super();
    }

    public UpdateException (String msg) {
        super(msg);
    }

}

