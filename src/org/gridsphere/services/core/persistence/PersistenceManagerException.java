/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 *
 * Is thrown if an RDBMS Exception occurs.
 */

package org.gridsphere.services.core.persistence;

public class PersistenceManagerException extends RuntimeException {

    /**
     * Constructs a new persistence exception with the given text.
     *
     * @param msg the exception text
     */
    public PersistenceManagerException(String msg) {
        super(msg);
    }

    /**
     * Constructs a new persistence exception with the given text.
     *
     * @param text  the exception text
     * @param cause the root cause
     */
    public PersistenceManagerException(String text, Throwable cause) {
        super(text, cause);
    }

    /**
     * Constructs a new portlet exception when the portlet needs to throw an exception.
     * The exception's message is based on the localized message of the underlying exception.
     *
     * @param cause the root cause
     */
    public PersistenceManagerException(Throwable cause) {
        super(cause);
    }

}

