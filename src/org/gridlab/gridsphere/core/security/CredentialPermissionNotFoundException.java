/*
 * @author <a href="mailto:russell@aei-potsdam.mpg.de">Michael Paul Russell</a>
 * @version $Id$
 *
 * This exception occurs when attempting to use a credential that is not permitted for use in
 * GridSphere. See <code>CredentialManager</code> for how this exception is applied.
 */
package org.gridlab.gridsphere.core.security;

public class CredentialPermissionNotFoundException extends CredentialException {

    public CredentialPermissionNotFoundException() {
        super();
    }
    
    public CredentialPermissionNotFoundException(String message) {
        super(message);
    }
    
    public CredentialPermissionNotFoundException(String message, Throwable ex) {
        super(message, ex);
    }
}
