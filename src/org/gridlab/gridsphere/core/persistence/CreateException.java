/*
 * @author <a href="oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 *
 * Is thrown when the creation of an object failed.
 */

package org.gridlab.gridsphere.core.persistence;

public class CreateException extends Exception {
    static org.apache.log4j.Category cat = org.apache.log4j.Category.getInstance(CreateException.class.getName());

    public CreateException() {
        super();
    }

    public CreateException(String msg) {
        super(msg);
    }

}

