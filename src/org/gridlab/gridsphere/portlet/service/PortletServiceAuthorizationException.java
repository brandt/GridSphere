/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.service;

/**
 * The <code>PortletServiceAuthorizationException</code> is a runtime exception
 * that signals if an unauthorized operation has been attempted on a user
 * service
 *
 * @see org.gridlab.gridsphere.portlet.service.spi.PortletServiceAuthorizer
 */
public class PortletServiceAuthorizationException extends RuntimeException {

    /**
     * Constructs an instance of PortletServiceAuthorizationException
     */
    public PortletServiceAuthorizationException() {
        super();
    }

    /**
     * Constructs an instance of PortletServiceAuthorizationException
     *
     * @param message a string indicating why this exception is thrown.
     */
    public PortletServiceAuthorizationException(String message) {
        super(message);
    }
}

