/*
 * @author <a href="mailto:russell@aei-potsdam.mpg.de">Michael Paul Russell</a>
 * @version $Id$
 *
 * This exception occurs when attempting to use a credential that has expired. 
 */
package org.gridlab.gridsphere.core.security;

public class CredentialExpiredException extends CredentialException {

    public CredentialExpiredException() {
        super();
    }
    
    public CredentialExpiredException(String message) {
        super(message);
    }
    
    public CredentialExpiredException(String message, Throwable ex) {
        super(message, ex);
    }
}
