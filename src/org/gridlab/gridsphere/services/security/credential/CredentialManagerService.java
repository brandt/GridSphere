/*
 * @author <a href="mailto:russell@aei-potsdam.mpg.de">Michael Paul Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.security.credential;

import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.service.PortletService;

import java.util.List;

public interface CredentialManagerService extends PortletService {

    /****** CREDENTIAL PERMISSION PERSISTENCE METHODS *******/

    public List getCredentialPermissions();

    public CredentialPermission getCredentialPermission(String permission);

    public CredentialPermission createCredentialPermission(String permission);

    public CredentialPermission createCredentialPermission(String permission, String description);

    public void deleteCredentialPermission(String permission);

    public boolean existsCredentialPermission(String permission);

    /****** CREDENTIAL PERMISSION MANIPULATION METHODS *******/

    public List getPermittedCredentialSubjects();

    public boolean isCredentialPermitted(String subject);

    /****** CREDENTIAL MAPPING PERSISTENCE METHODS *******/

    public List getCredentialMappings();

    public CredentialMapping getCredentialMapping(String subject);

    public CredentialMapping createCredentialMapping(String subject, User user)
            throws CredentialNotPermittedException;

    public CredentialMapping createCredentialMapping(String subject, User user, String tag)
            throws CredentialNotPermittedException;

    public void deleteCredentialMapping(String subject);

    public List getCredentialMappings(User user);

    public void deleteCredentialMappings(User user);

    /****** CREDENTIAL MAPPING MANIPULATION METHODS *******/

    public User getCredentialUser(String subject);

    public List getCredentialSubjects(User user);

    public List getCredentialTags(User user);

    public String getCredentialTag(String subject)
            throws CredentialMappingNotFoundException;

    public void setCredentialTag(String subject, String tag)
            throws CredentialMappingNotFoundException;

    public String getCredentialLabel(String subject)
            throws CredentialMappingNotFoundException;

    public void setCredentialLabel(String subject, String label)
            throws CredentialMappingNotFoundException;

    public List getCredentialHosts(String subject)
            throws CredentialMappingNotFoundException;

    public void addCredentialHost(String subject, String host)
            throws CredentialMappingNotFoundException;

    public void addCredentialHosts(String subject, List hosts)
            throws CredentialMappingNotFoundException;

    public void removeCredentialHost(String subject, String host)
            throws CredentialMappingNotFoundException;

    public List getCredentialSubjectsForHost(String host);

    public List getCredentialSubjectsForHost(User user, String host);

    /****** CREDENTIAL RETRIEVAL METHODS *******/

    public String getCredentialRetrievalProtocol();

    public String getCredentialRetrievalHost();

    public int getCredentialRetrievalPort();

    public long getCredentialRetrievalLifetime();

    public List retrieveCredentials(User user, String passphrase)
            throws CredentialRetrievalException;

    /****** CREDENTIAL STORAGE METHODS *******/

    public void storeCredential(Credential credential)
            throws CredentialNotPermittedException;

    public void storeCredentials(List credentials)
            throws CredentialNotPermittedException;

    public void destroyCredential(String subject);

    public void destroyCredentials(User user);

    /****** CREDENTIAL USEAGE METHODS *******/

    public List getActiveCredentials();

    public List getActiveCredentials(User user);

    public Credential getActiveCredential(String subject);

    public List getActiveCredentialsForHost(User user, String host);

    public boolean isActiveCredential(String subject);

    public boolean hasActiveCredentials(User user);

    public List getActiveCredentialSubjects();

    public List getActiveCredentialSubjects(User user);
}
