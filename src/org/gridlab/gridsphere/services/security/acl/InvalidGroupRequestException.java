/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Feb 1, 2003
 * Time: 7:18:38 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.services.security.acl;

import org.gridlab.gridsphere.portlet.service.PortletServiceException;

public class InvalidGroupRequestException extends PortletServiceException {

    public InvalidGroupRequestException() {
        super();
    }

    public InvalidGroupRequestException(String msg) {
        super(msg);
    }
}
