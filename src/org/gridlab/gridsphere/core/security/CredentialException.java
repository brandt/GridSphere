/*
 * @author <a href="mailto:russell@aei-potsdam.mpg.de">Michael Paul Russell</a>
 * @version $Id$
 *
 * It is extended from <code>org.globus.common.ChainedException</code> so that we may 
 * more easily attach the specific exception generated in the underlying
 * implementation of <code>org.gridlab.gridsphere.core.security.Credential</code>. 
 */
package org.gridlab.gridsphere.core.security;

import org.globus.common.ChainedException;

public class CredentialException extends ChainedException {

    public CredentialException() {
        super();
    }
    
    public CredentialException(String message) {
        super(message);
    }
    
    public CredentialException(String message, Throwable ex) {
        super(message, ex);
    }
}
