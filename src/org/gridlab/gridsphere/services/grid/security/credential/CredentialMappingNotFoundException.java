/*
 * @author <a href="mailto:russell@aei-potsdam.mpg.de">Michael Paul Russell</a>
 * @version $Id$
 *
 * This exception occurs when <code>CredentialManager</code> is unable to locate a
 * <code>CredentialMapping</code> for a given subject.
 */
package org.gridlab.gridsphere.services.grid.security.credential;


public class CredentialMappingNotFoundException extends CredentialException {

    public CredentialMappingNotFoundException() {
        super();
    }
    
    public CredentialMappingNotFoundException(String message) {
        super(message);
    }
    
    public CredentialMappingNotFoundException(String message, Throwable ex) {
        super(message, ex);
    }
}
