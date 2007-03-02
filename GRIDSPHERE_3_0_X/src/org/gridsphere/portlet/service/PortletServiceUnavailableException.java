/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: PortletServiceUnavailableException.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.portlet.service;


/**
 * The <code>PortletServiceUnavailableException</code> is thrown if a portlet
 * attempts to access an service that cannot be loaded, because an error
 * occured during initialization.
 */
public class PortletServiceUnavailableException extends PortletServiceException {

    /**
     * Constructs a new portlet service unavailable exception.
     */
    public PortletServiceUnavailableException() {
        super();
    }

    /**
     * Creates a new exception with the sepcified detail message.
     *
     * @param message a string indicating why this exception is thrown.
     */
    public PortletServiceUnavailableException(String message) {
        super(message, null);
    }

    /**
     * Constructs a new portlet exception with the given text.
     * The portlet container may use the text write it to a log.
     *
     * @param text  the exception text
     * @param cause the root cause
     */
    public PortletServiceUnavailableException(String text, Throwable cause) {
        super(text, cause);
    }

    /**
     * Constructs a new portlet exception when the portlet needs to throw an exception.
     * The exception's message is based on the localized message of the underlying exception.
     *
     * @param cause the root cause
     */
    public PortletServiceUnavailableException(Throwable cause) {
        super(cause);
    }

}
