/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Feb 1, 2003
 * Time: 7:18:38 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.services.user;

import org.gridlab.gridsphere.portlet.service.PortletServiceException;

public class InvalidAccountRequestException extends PortletServiceException {

    public InvalidAccountRequestException() {
        super();
    }

    public InvalidAccountRequestException(String msg) {
        super(msg);
    }
}
