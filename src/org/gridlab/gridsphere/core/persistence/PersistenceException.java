/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.core.persistence;

public class PersistenceException extends Exception {

    private Throwable rootCause;

    /**
     * Constructs a new persistence exception with the given text.
     */
    public PersistenceException() {
        super();
    }

    /**
     * Constructs a new persistence exception with the given text.
     *
     * @param text the exception text
     */
    public PersistenceException(String msg) {
        super(msg);
    }

    /**
     * Constructs a new persistence exception with the given text.
     *
     * @param text the exception text
     * @param cause the root cause
     */
    public PersistenceException(String text, Throwable cause) {
        super(text);
        this.rootCause = cause;
    }

    /**
     * Constructs a new portlet exception when the portlet needs to throw an exception.
     * The exception's message is based on the localized message of the underlying exception.
     *
     * @param cause the root cause
     */
    public PersistenceException(Throwable cause) {
        this.rootCause = cause;
    }
}

