/*
 * @author <a href="oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 *
 * Is thrown when the restoration of an object failed.
 */

package org.gridlab.gridsphere.core.persistence;

public class RestoreException extends Exception {
    static org.apache.log4j.Category cat = org.apache.log4j.Category.getInstance(RestoreException.class.getName());

    public RestoreException () {
        super();
    }

    public RestoreException (String msg) {
        super(msg);
    }
}

