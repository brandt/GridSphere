/*
 * @version: $Id$
 */
package org.gridlab.gridsphere.services.core.user;

import org.gridlab.gridsphere.portlet.service.PortletServiceException;

public class InvalidAccountRequestException extends PortletServiceException {

    public InvalidAccountRequestException() {
        super();
    }

    public InvalidAccountRequestException(String msg) {
        super(msg);
    }
}
