/*
 * @author <a href="mailto:russell@aei-potsdam.mpg.de">Michael Paul Russell</a>
 * @version $Id$
 *
 * This exception occurs when <code>CredentialManager</code> is unable to locate a
 * <code>CredentialMapping</code> for a given subject.
 */
package org.gridlab.gridsphere.services.security.credential;

public class MappingNotFoundException extends CredentialException {

    public MappingNotFoundException() {
        super();
    }
    
    public MappingNotFoundException(String message) {
        super(message);
    }
    
    public MappingNotFoundException(String message, Throwable ex) {
        super(message, ex);
    }
}
