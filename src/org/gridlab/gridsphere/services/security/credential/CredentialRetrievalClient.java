/*
 * @author <a href="mailto:russell@aei-potsdam.mpg.de">Michael Paul Russell</a>
 * @version $Id$
 * <p>
 * GridSphere requires users to authenticate with credentials. Credentials may be 
 * supplied by users at login from an accessible filesystem or they may be retrieved from
 * an <i>online certificate retrievel service</i> (OCR), such as MyProxy, as described in 
 * the  Online Credential Retrieval Requirements Memo published by the  Working
 * Group of the Global Grid Forum. An OCR service is required to allow users to authenticate 
 * with a username/passphrase pair in order to retrieve their credentials. Additionally, an OCR 
 * may allow users to retrieve multiple credentials or to retrieve the appropriate 
 * credential based on Grid resource requirements.  
 * <p>
 * This interface describes methods for retrieving one or more of a user's  credentials 
 * from an OCR based on a username/passphrase pair supplied by that user. Note: The user supplied
 * username/passphrase pair must not stored in memory nor in persistence storage. Furthermore, any
 * and all credentials retrieved with this service should be maintined in memory only, and destroyed
 * either when a user logs out or when GridSphere is shutdown.
 */
package org.gridlab.gridsphere.services.security.credential;

import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.services.security.credential.Credential;
import org.gridlab.gridsphere.services.security.credential.CredentialRetrievalException;

import java.util.List;

public interface CredentialRetrievalClient {

    /**
     * Returns the protocol for the credential retrieval service.
     * 
     * @return <code>String</code> describing protocol.
     */
    public String getProtocol();

    /**
     * Returns the hostname for the credential retrieval service.
     * 
     * @return <code>String</code> describing hostname.
     */
    public String getHost();

    /**
     * Returns the port for the credential retrieval service.
     * 
     * @return <code>int</code> describing port.
     */
    public int getPort();

    /**
     * Returns the default credential lifetime, in milliseconds, to set for retrieved credentials.
     * 
     * @return <code>long</code> describing lifetime in milliseconds.
     */
    public long getCredentialLifetime();

    /**
     * Set the default credential lifetime, in milliseconds, for the credential retrieval service.
     * 
     * @param <code>long</code> describing lifetime in milliseconds.
     */
    public void setCredentialLifetime(long lifetime);

    /**
     * Retrieve the list of credentials associated with the given username and passphrase from 
     * the credential retrieval service.
     * 
     * @return <code>List</code> of <code>org.gridlab.gridsphere.code.security.Credential</code>
     */
    public List retrieveCredentials(String username, String passphrase)
        throws CredentialRetrievalException;

    /**
     * Retrieve the list of credentials associated with the given username and passphrase from
     * the credential retrieval service. Set the liftime of the credential to the given value.
     * 
     * @return <code>List</code> of <code>org.gridlab.gridsphere.code.security.Credential</code>
     */
    public List retrieveCredentials(String username, String passphrase, long lifetime)
        throws CredentialRetrievalException;

    /**
     * Retrieve the credential identified by the given username, passphrase and subject.
     * from the credential retrieval service.
     * 
     * @return <code>org.gridlab.gridsphere.code.security.Credential</code>
     */
    public Credential retrieveCredential(String username, String passphrase, String subject)
        throws CredentialRetrievalException;

    /**
     * Retrieve the credential identified by the given username, passphrase and subject 
     * from the credential retrieval service. Set the lifetime of the credential to the
     * given value.
     * 
     * @return <code>org.gridlab.gridsphere.code.security.Credential</code>
     */
    public Credential retrieveCredential(String username, 
                                         String passphrase, 
                                         String subject,
                                         long lifetime)
        throws CredentialRetrievalException;
}
