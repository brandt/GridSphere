/*
 * @author <a href="mailto:russell@aei-potsdam.mpg.de">Michael Paul Russell</a>
 * @version $Id$
 *
 * It is extended from <code>org.globus.common.ChainedException</code> so that we may 
 * more easily attach the specific exception generated in the underlying
 * implementation of <code>org.gridlab.gridsphere.core.security.Credential</code>. 
 */
package org.gridlab.gridsphere.services.grid.security.credential;

import org.gridlab.gridsphere.portlet.service.PortletServiceException;

public class CredentialException extends PortletServiceException {

    public CredentialException() {
        super();
    }
    
    public CredentialException(String message) {
        super(message);
    }

    public CredentialException(String message, Throwable ex) {
        super(message + ";" + ex.getMessage());
    }
}
