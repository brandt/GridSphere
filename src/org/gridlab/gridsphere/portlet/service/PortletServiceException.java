/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.service;

import org.gridlab.gridsphere.portlet.PortletException;

/**
 * The <code>PortletServiceException</code> is the base class of all
 * checked exceptions thrown by portlet services.
 */
public class PortletServiceException extends PortletException {

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

}
