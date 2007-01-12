package org.gridsphere.services.core.jcr;

/*
* @author <a href="mailto:wehrens@gridsphere.org">Oliver Wehrens</a>
* @version $Id$
*/
public class ContentException extends Exception {

    /**
     * Constructs a new content exception.
     */
    public ContentException() {
        super();
    }

    /**
     * Creates a new content with the sepcified detail message.
     *
     * @param message a string indicating why this exception is thrown.
     */
    public ContentException(String message) {
        super(message);
    }

    /**
     * Constructs a new content exception with the given text.
     *
     * @param text  the exception text
     * @param cause the root cause
     */
    public ContentException(String text, Throwable cause) {
        super(text, cause);
    }

    /**
     * Constructs a new content exception when the portlet needs to throw an exception.
     *
     * @param cause the root cause
     */
    public ContentException(Throwable cause) {
        super(cause);
    }

}
