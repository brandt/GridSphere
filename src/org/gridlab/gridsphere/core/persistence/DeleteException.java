/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 *
 * Is thrown when the deletion of an object failed.
 */

package org.gridlab.gridsphere.core.persistence;

public class DeleteException extends Exception {
    static org.apache.log4j.Category cat = org.apache.log4j.Category.getInstance(DeleteException.class.getName());

    public DeleteException () {
        super();
    }

    public DeleteException(String msg) {
        super(msg);
    }


}

