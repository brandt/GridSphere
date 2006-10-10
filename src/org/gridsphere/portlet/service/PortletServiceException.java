/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: PortletServiceException.java 5032 2006-08-17 18:15:06Z novotny $
 */
package org.gridsphere.portlet.service;


/**
 * The <code>PortletServiceException</code> is the base class of all
 * exceptions thrown by portlet services.
 */
public class PortletServiceException extends RuntimeException {

    /**
     * Constructs a new portlet exception.
     */
    public PortletServiceException() {
        super();
    }

    /**
     * Creates a new exception with the sepcified detail message.
     *
     * @param message a string indicating why this exception is thrown.
     */
    public PortletServiceException(String message) {
        super(message);
    }

    /**
     * Constructs a new portlet exception with the given text.
     * The portlet container may use the text write it to a log.
     *
     * @param text  the exception text
     * @param cause the root cause
     */
    public PortletServiceException(String text, Throwable cause) {
        super(text, cause);
    }

    /**
     * Constructs a new portlet exception when the portlet needs to throw an exception.
     * The exception's message is based on the localized message of the underlying exception.
     *
     * @param cause the root cause
     */
    public PortletServiceException(Throwable cause) {
        super(cause);
    }

}
