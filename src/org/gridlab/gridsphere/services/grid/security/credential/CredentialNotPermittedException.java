/*
 * @author <a href="mailto:russell@aei-potsdam.mpg.de">Michael Paul Russell</a>
 * @version $Id$
 *
 * This exception occurs when attempting to use a credential that is not permitted for use in
 * GridSphere. See <code>CredentialManager</code> for how this exception is applied.
 */
package org.gridlab.gridsphere.services.grid.security.credential;


public class CredentialNotPermittedException extends CredentialException {

    public CredentialNotPermittedException() {
        super();
    }
    
    public CredentialNotPermittedException(String message) {
        super(message);
    }
    
    public CredentialNotPermittedException(String message, Throwable ex) {
        super(message, ex);
    }
}
