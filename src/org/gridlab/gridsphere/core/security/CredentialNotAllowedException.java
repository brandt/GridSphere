/*
 * @author <a href="mailto:russell@aei-potsdam.mpg.de">Michael Paul Russell</a>
 * @version $Id$
 *
 * This exception occurs when attempting to use a credential that is not allowed for use in
 * GridSphere. See <code>org.gridlab.gridsphere.services.CredentialManagerService</code> 
 * for how this exception is applied.
 */
package org.gridlab.gridsphere.core.security;

public class CredentialNotAllowedException extends CredentialException {

    public CredentialNotAllowedException() {
        super();
    }
    
    public CredentialNotAllowedException(String message) {
        super(message);
    }
    
    public CredentialNotAllowedException(String message, Throwable ex) {
        super(message, ex);
    }
}
