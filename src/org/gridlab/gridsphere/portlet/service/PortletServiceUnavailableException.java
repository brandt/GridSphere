/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.service;

/**
 * The PortletServiceUnavailableException is thrown if a portlet attempts to access an
 * service that cannot be loaded, because an error occured during initialization.
 */
public class PortletServiceUnavailableException extends PortletServiceException {

    /**
     * Constructs a new portlet service unavailable exception.
     */
    public PortletServiceUnavailableException() {}

    /**
     * Creates a new exception with the sepcified detail message.
     *
     * @param message a string indicating why this exception is thrown.
     */
    public PortletServiceUnavailableException(String message) {

    }

}
