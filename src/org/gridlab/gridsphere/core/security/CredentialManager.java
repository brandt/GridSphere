/*
 * @author <a href="mailto:russell@aei-potsdam.mpg.de">Michael Paul Russell</a>
 * @version $Id$
 * <p>
 * GridSphere requires portlet users to authenticate with credentials at login. The
 * credentials that portlet users authenticate with are then kept in memory and used
 * as required to authenticate on behalf of portlet users to other Grid services.
 * <p>
 * This interface describes methods for permitting credentials for use within GridSphere,
 * mapping credential subjects to portlet users and hostnames, and finally methods for
 * managing credentials on the behalf of portlet users.
 */
package org.gridlab.gridsphere.core.security;

import org.gridlab.gridsphere.portlet.User;

import java.util.List;

public interface CredentialManager {

    /****** CREDENTIAL PERMISSION PERSISTENCE METHODS *******/

    public List getCredentialPermissions();

    public CredentialPermission getCredentialPermission(String permission);

    public CredentialPermission createCredentialPermission(String permission);

    public CredentialPermission createCredentialPermission(String permission, String description);

    public void updateCredentialPermission(CredentialPermission permission);

    public void deleteCredentialPermission(String permission);

    /****** CREDENTIAL PERMISSION CONVENIENCE METHODS *******/

    public List getPermittedCredentialSubjects();

    public boolean isCredentialPermitted(String subject);

    /****** CREDENTIAL MAPPING PERSISTENCE METHODS *******/

    public List getCredentialMappings();

    public CredentialMapping getCredentialMapping(String subject);

    public CredentialMapping createCredentialMapping(String subject, User user)
            throws CredentialNotPermittedException;

    public void updateCredentialMapping(CredentialMapping mapping)
            throws CredentialNotPermittedException;

    public void deleteCredentialMapping(String subject);

    public List getCredentialMappings(User user);

    public void deleteCredentialMappings(User user);

    /****** CREDENTIAL MAPPING CONVENIENCE METHODS *******/

    public User getCredentialUser(String subject);

    public List getCredentialSubjects(User user);

    public List getCredentialTags(User user);

    public String getCredentialTag(String subject)
            throws MappingNotFoundException;

    public void setCredentialTag(String subject, String tag)
            throws MappingNotFoundException;

    public List getCredentialHosts(String subject)
            throws MappingNotFoundException;

    public void addCredentialHost(String subject, String host)
            throws MappingNotFoundException;

    public void removeCredentialHost(String subject, String host)
            throws MappingNotFoundException;

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

    public List getActiveCredentials(User user);

    public Credential getActiveCredential(String subject);

    public List getActiveCredentialsForHost(User user, String host);

    public boolean isActiveCredential(String subject);

    public boolean hasActiveCredentials(User user);

    public List getActiveCredentialSubjects();

    public List getActiveCredentialSubjects(User user);
}
