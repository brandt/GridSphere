/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 *
 * Is thrown when the creation of an object failed.
 */

package org.gridlab.gridsphere.core.persistence;

public class CreateException extends PersistenceManagerException {

    public CreateException() {
        super();
    }

    public CreateException(String msg) {
        super(msg);
    }

}

