/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.service;

/**
 * The <code>PortletServiceNotFoundException</code> is thrown if a portlet
 * attempts to access an service that cannot be found.
 */
public class PortletServiceNotFoundException extends PortletServiceException {

    /**
     * Constructs a new portlet service not found exception.
     */
    public PortletServiceNotFoundException() {
    }

    /**
     * Creates a new exception with the sepcified detail message.
     *
     * @param message a string indicating why this exception is thrown.
     */
    public PortletServiceNotFoundException(String message) {

    }

}
