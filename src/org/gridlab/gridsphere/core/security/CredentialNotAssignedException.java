/*
 * @author <a href="mailto:russell@aei-potsdam.mpg.de">Michael Paul Russell</a>
 * @version $Id$
 *
 * This exception occurs when specifying a credential as if it were assigned to a host
 * when it has not in fact been assigned to that host. See 
 * <code>org.gridlab.gridsphere.services.CredentialManagerService</code> for
 * how this exception is applied.
 */
package org.gridlab.gridsphere.core.security;

public class CredentialNotAssignedException extends CredentialException {

    public CredentialNotAssignedException() {
        super();
    }
    
    public CredentialNotAssignedException(String message) {
        super(message);
    }
    
    public CredentialNotAssignedException(String message, Throwable ex) {
        super(message, ex);
    }
}
