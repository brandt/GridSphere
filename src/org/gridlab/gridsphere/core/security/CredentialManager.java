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
import org.gridlab.gridsphere.core.security.Credential;
import org.gridlab.gridsphere.core.security.CredentialPermission;
import org.gridlab.gridsphere.core.security.CredentialPermissionNotFoundException;
import org.gridlab.gridsphere.core.security.CredentialMap;
import org.gridlab.gridsphere.core.security.CredentialMapNotFoundException;
import org.gridlab.gridsphere.core.security.CredentialExpiredException;
import org.gridlab.gridsphere.core.security.CredentialNotActiveException;

import java.util.List;

public interface CredentialManager {

    /****** CREDENTIAL PERMISSION PERSISTENCE METHODS *******/

    /**
     * Loads the credential permissions currently stored by this credential manager.
     * 
     * @return <code>List</code> of <code>CredentialPermission</code> 
     *         The list of credential permissions.
     */
    public List loadCredentialPermissions();

    /**
     * Loads a credential permission from storage based on its object id.
     * 
     * @param <code>String</code> The object id of the credential permission.
     *
     * @return <code>CredentialPermission</code> The credential permission of interest.
     */
    public CredentialPermission loadCredentialPermission(String oid)
        throws CredentialPermissionNotFoundException;

    /**
     * Loads a credential permission from storage based on its subject pattern.
     * 
     * @param <code>String</code> The subject pattern of the credential permission.
     *
     * @return <code>CredentialPermission</code> The credential permission of interest.
     */
    public CredentialPermission loadCredentialPermissionWithSubjectPattern(String pattern)
        throws CredentialPermissionNotFoundException;

    /**
     * Stores or updates a credential permission for this credential manager.
     *
     * @param <code>CredentialPermission</code> The credential permission to save.
     */
    public void saveCredentialPermission(CredentialPermission permission);

    /**
     * Delete a credential permission in storage.
     *
     * @param <code>String</code> The object id of the credential permission to delete.
     */
    public void deleteCredentialPermission(String oid)
        throws CredentialPermissionNotFoundException;

    /****** CREDENTIAL PERMISSION CONVENIENCE METHODS *******/

    /**
     * Returns true if the given credential subject is permitted for use by this 
     * credential manager, false otherwise. Permission is determined by testing
     * if the given subject matches the subject pattern of any one of the
     * stored credential permissions.
     *
     * @return <code>boolean</code>
     */
    public boolean isSubjectPermitted(String subject);

    /****** CREDENTIAL MAP PERSISTENCE METHODS *******/

    /**
     * Loads the credential maps currently stored by this credential manager.
     * 
     * @return <code>List</code> of <code>CredentialMap<code> 
     *         The list of credential maps.
     */
    public List loadCredentialMaps();

    /**
     * Loads a credential map from storage based on its object id.
     * 
     * @param <code>String</code> The object id of the credential map.
     *
     * @return <code>CredentialMap</code> The credential map of interest.
     */
    public CredentialMap loadCredentialMap(String oid)
        throws CredentialMapNotFoundException;

    /**
     * Loads a credential map from storage based on its credential subject.
     * 
     * @param <code>String</code> The object id of the credential map.
     *
     * @return <code>CredentialMap</code> The credential map of interest.
     */
    public CredentialMap loadCredentialMapForSubject(String subject)
        throws CredentialMapNotFoundException;

    /**
     * Loads the credential maps containing the id of the given porltet user.
     * 
     * @param <code>User<code> The portlet user.
     *
     * @return <code>List</code> of <code>CredentialMap</code> 
     *         The credential maps of interest.
     */
    public List loadCredentialMapsForUser(User user);

    /**
     * Stores or updates a credential map for this credential manager.
     *
     * @param <code>CredentialPermission</code> The credential map to save.
     */
    public void saveCredentialMap(CredentialMap map)
        throws CredentialPermissionNotFoundException;

    /**
     * Deletes a credential map from storage.
     *
     * @param <code>String</code> The object id of the credential map to delete.
     */
    public void deleteCredentialMap(String oid)
        throws CredentialMapNotFoundException;

    /**
     * Deletes the credential map with the given credential sbject from storage.
     *
     * @param <code>String</code> The credential subject of the credential map to delete.
     */
    public void deleteCredentialMapForSubject(String subject)
        throws CredentialMapNotFoundException;

    /**
     * Deletes the credential maps containing the id of the given portlet user.
     *
     * @param <code>String</code> The object id of the credential map to delete.
     */
    public void deleteCredentialMapsForUser(User user);

    /****** CREDENTIAL MAP CONVENIENCE METHODS *******/

    /**
     * Returns the portlet user mapped to the given credential subject.
     *
     * @param <code>String<code> The credential subject.
     *
     * @return <code>User</code> The portlet user to which the given subject maps.
     */
    public User getUserMappedToSubject(String subject)
        throws CredentialMapNotFoundException;

    /**
     * Returns the list of credential subjects mapped to the given portlet user.
     *
     * @param <code>User<code> The portlet user.
     *
     * @return <code>List</code> of <code>String</code> 
     *         The subjects mapped to the given portlet user.
     */
    public List getSubjectsMappedToUser(User user);

    /**
     * Returns true if the given credential subject is mapped to the given portlet user.
     *
     * @param <code>String<code> The credential subject.
     *
     * @param <code>User<code> The portlet user.
     *
     * @return <code>boolean</code>
     */
    public boolean isSubjectMappedToUser(String subject, User user);

    /**
     * Returns the list of hostnames mapped to the given credential subject.
     *
     * @param <code>String<code> The credential subject.
     *
     * @return <code>List</code> of <code>String</code> 
     *         The subjects mapped to the given portlet user.
     */
    public List getHostNamesMappedToSubject(String subject)
        throws CredentialMapNotFoundException;

    /**
     * Returns the list of credential subjects mapped to the given hostname.
     *
     * @param <code>String<code> The hostname.
     *
     * @return <code>List</code> of <code>String</code> 
     *         The subjects mapping to the given hostname.
     */
    public List getSubjectsMappedToHostName(String hostName);

    /**
     * Returns true if the given credential subject is mapped to the given hostname.
     *
     * @param <code>String<code> The credential subject.
     *
     * @param <code>String<code> The hostname.
     *
     * @return <code>boolean</code>
     */
    public boolean isSubjectMappedToHostName(String subject, String hostName);

    /****** CREDENTIAL "VAULT" METHODS *******/

    /**
     * Returns the credentials currently held in vault by this credential 
     * manager mapped to the given portlet user.
     *
     * @param <code>User<code> The portlet user.
     *
     * @return <code>List</code> of <code>Credential<code> 
     *        The portlet user's credentials.
     *
     * @return <code>boolean</code>
     */
    public List getCredentials(User user);
    
    /**
     * Retrieves the credential with the given subject from the vault.
     *
     * @param <code>String<code> The credential subject.
     *
     * @return <code>Credential<code> The credential with the given subject.
     */
    public Credential getCredential(String subject)
        throws CredentialMapNotFoundException,
               CredentialNotActiveException;

    /**
     * Stores the given credential in memory for use at a later time.
     *
     * @param <code>Credential<code> The credential.
     */
    public void putCredential(Credential credential)
        throws CredentialPermissionNotFoundException, 
               CredentialMapNotFoundException,
               CredentialExpiredException;

    /**
     * Stores the given credentials in the vault for use at a later time.
     *
     * @param <code>List</code> of <code>Credential<code> The credential.
     */
    public void putCredentials(List credentials)
        throws CredentialPermissionNotFoundException, 
               CredentialMapNotFoundException,
               CredentialExpiredException;

    /**
     * Removes the credential with the given subject from the vault and
     * calls the credential's destroy method so that it can no longer be used.
     *
     * @param <code>String<code> The credential subject.
     */
    public void destroyCredential(String subject)
        throws CredentialMapNotFoundException,
               CredentialNotActiveException;

    /**
     * Removes the credential with the given subject from the vault and
     * destroys the credentials mapped to the given portlet user.
     *
     * @param <code>User<code> The portlet user.
     */
    public void destroyCredentials(User user);
    
    /**
     * Returns true if one or more credentials in vault for the given portlet user.
     *
     * @param <code>User<code> The portlet user.
     *
     * @return <code>boolean</code>
     */
    public boolean hasCredentials(User user);

    /**
     * Returns true if a credential with the given subject is in the vault.
     *
     * @param <code>String<code> The credential subject.
     *
     * @return <code>boolean</code>
     */
    public boolean hasCredential(String subject);

    /**
     * Retrieves the credentials in vault mapped to the given portlet user and hostname.
     *
     * @param <code>User<code> The portlet user.
     *
     * @param <code>String<code> The hostname.
     *
     * @return <code>boolean</code>
     */
    public List getCredentialsForHostName(User user, String hostName)
        throws CredentialMapNotFoundException,
               CredentialNotActiveException;

    /**
     * Returns the credential subjects of the credentials currently in vault.
     * 
     * @return <code>List</code> of <code>String<code> 
     &         The subjects for all active credentials.
     */
    public List getActiveCredentialSubjects();

    /**
     * Returns the credential subjects of the credentials currently in vault for
     * the given portlet user.
     * 
     * @param <code>User</code> The user in question.
     *
     * @return <code>List</code> of <code>String<code> 
     &         The subjects for this user's active credentials.
     */
    public List getActiveCredentialSubjects(User user);
}
