/*
 * @author <a href="mailto:russell@aei-potsdam.mpg.de">Michael Paul Russell</a>
 * @version $Id$
 *
 * This exception occurs when attempting to retrieve one or more credentials from an
 * online credential retrieval service.
 */
 
package org.gridlab.gridsphere.core.security;

import org.gridlab.gridsphere.core.security.CredentialException;

public class CredentialRetrievalException extends CredentialException {

    public CredentialRetrievalException() {
        super();
    }
    
    public CredentialRetrievalException(String message) {
        super(message);
    }
    
    public CredentialRetrievalException(String message, Throwable ex) {
        super(message, ex);
    }
}
