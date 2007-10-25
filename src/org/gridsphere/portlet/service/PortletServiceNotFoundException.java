/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
package org.gridsphere.portlet.service;

/**
 * The <code>PortletServiceNotFoundException</code> is thrown if a portlet
 * attempts to access an service that cannot be found.
 */
public class PortletServiceNotFoundException extends PortletServiceException {

    /**
     * Constructs a new portlet service not found exception.
     */
    public PortletServiceNotFoundException() {
        super();
    }

    /**
     * Creates a new exception with the sepcified detail message.
     *
     * @param message a string indicating why this exception is thrown.
     */
    public PortletServiceNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new portlet exception with the given text.
     * The portlet container may use the text write it to a log.
     *
     * @param text  the exception text
     * @param cause the root cause
     */
    public PortletServiceNotFoundException(String text, Throwable cause) {
        super(text, cause);
    }

    /**
     * Constructs a new portlet exception when the portlet needs to throw an exception.
     * The exception's message is based on the localized message of the underlying exception.
     *
     * @param cause the root cause
     */
    public PortletServiceNotFoundException(Throwable cause) {
        super(cause);
    }
}
