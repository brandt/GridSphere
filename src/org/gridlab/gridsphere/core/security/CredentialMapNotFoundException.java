/*
 * @author <a href="mailto:russell@aei-potsdam.mpg.de">Michael Paul Russell</a>
 * @version $Id$
 *
 * This exception occurs when <code>CredentialManager</code> is unable to locate a 
 * <code>CredentialMap</code> for a given subject.
 */
package org.gridlab.gridsphere.core.security;

public class CredentialMapNotFoundException extends CredentialException {

    public CredentialMapNotFoundException() {
        super();
    }
    
    public CredentialMapNotFoundException(String message) {
        super(message);
    }
    
    public CredentialMapNotFoundException(String message, Throwable ex) {
        super(message, ex);
    }
}
