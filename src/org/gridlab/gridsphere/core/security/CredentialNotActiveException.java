/*
 * @author <a href="mailto:russell@aei-potsdam.mpg.de">Michael Paul Russell</a>
 * @version $Id$
 *
 * This exception occurs when specifying a credential in method calls to 
 * <code>Credentialmanager</code> that is not currently in service for a given user.
 */
package org.gridlab.gridsphere.core.security;

public class CredentialNotActiveException extends CredentialException {

    public CredentialNotActiveException() {
        super();
    }
    
    public CredentialNotActiveException(String message) {
        super(message);
    }
    
    public CredentialNotActiveException(String message, Throwable ex) {
        super(message, ex);
    }
}
