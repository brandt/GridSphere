/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.user;

import org.gridlab.gridsphere.portlet.PortletException;

/**
 * The base class of all checked exceptions thrown by portlet services.
 */
public class PermissionDeniedException extends PortletException {

    /**
     * Constructs a new permission denied exception.
     */
    public PermissionDeniedException() {
        super();
    }

    /**
     * Creates a new exception with the sepcified detail message.
     *
     * @param message a string indicating why this exception is thrown.
     */
    public PermissionDeniedException(String message) {
        super(message);
    }

}
