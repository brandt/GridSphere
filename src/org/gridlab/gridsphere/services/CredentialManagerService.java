/*
 * @author <a href="mailto:russell@aei-potsdam.mpg.de">Michael Paul Russell</a>
 * @version $Id$
 * <p>
 * GridSphere requires users to authenticate with credentials. Once users have 
 * authenticated to GridSphere, they are maintained in memory (only) by the registered
 * <code>CredentialmanagerService</code>.
 * <p>
 * This interface describes methods for maintaining and accessing  credentials supplied 
 * by the user at login. Furthermore, it describes methods for restricting which credentials
 * are allowed for use with GridSphere.
 * <p>
 */
package org.gridlab.gridsphere.services;

import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.core.security.Credential;
import org.gridlab.gridsphere.core.security.CredentialExpiredException;
import org.gridlab.gridsphere.core.security.CredentialNotActiveException;
import org.gridlab.gridsphere.core.security.CredentialNotAllowedException;
import org.gridlab.gridsphere.core.security.CredentialNotAssignedException;

import java.util.List;

public interface CredentialManagerService extends PortletService {

    /**
     * Returns the list of credential subjects this service allows for use with GridSphere.
     * 
     * @return <code>List</code> of <code>String<code> The list of credential subjects.
     */
    public List getAllowedCredentialSubjects();

    /**
     * Adds a credential subject to the list of acceptable credential subjectss.
     *
     * @param <code>String</code> The credential subject to add.
     */
    public void addAllowedCredentialSubject(String subject);

    /**
     * Removes the given credential subject from the list of acceptable subjects.
     *
     * @param <code>String</code> The credential subject to remove.
     */
    public void removeAllowedCredentialSubject(String subject);

    /**
     * Returns the list of credential subjects this service accepts for a given user.
     *
     * @param <code>User</code> The user in question.
     * 
     * @return <code>List</code> of <code>String<code> The list of credential subjects.
     */
    public List getCredentialSubjects(User user);

    /**
     * Adds the given credential subject to the list of acceptable credential subjects
     * for given user.
     *
     * @param <code>User</code> The user.
     *
     * @param <code>String</code> The credential subject to add.
     */
    public void addCredentialSubject(User user, String subject)
        throws CredentialNotAllowedException;

    /**
     * Removes the given credential subject from the list of acceptable subjects for a given
     * user.
     * <p>
     * Note: This may or may not disallow credentials with the given subject pattern depending
     * on the remaining list of acceptable subject patterns and the implemenation of this service.
     * 
     * @param <code>String</code> The user's username.
     *
     * @param <code>String</code> The credential subject to remove.
     */
    public void removeCredentialSubject(User user, String subject);

    /**
     * Returns the list of credential subjects this service accepts for a user.
     *
     * @param <code>String</code> The user in question.
     * 
     * @return <code>boolean</code>
     */
    public boolean hasCredentialSubject(User user, String subject);

    /**
     * Returns the credential subjects for the credentials currently in service.
     * 
     * @return <code>List</code> of <code>String<code> The list of subjects for active credentials.
     */
    public List getActiveCredentialSubjects();

    /**
     * Returns the credential subjects for the credentials currently in service for
     * a user identified by the given username.
     * 
     * @param <code>User</code> The user in question.
     *
     * @return <code>List</code> of <code>String<code> The list of subjects for active credentials.
     */
    public List getActiveCredentialSubjects(User user);
    
    /**
     * Returns the credentials currently held by this service for a given user.
     *
     * @param <code>org.gridlab.gridsphere.portlet.User</code> The user.
     * 
     * @return <code>List</code> of <code>org.gridlab.gridsphere.core.security.Credential<code> 
     *         The active credentials.
     */
    public List getCredentials(User user);

    /**
     * Returns the credential identified by the subject and associated with the given user.
     * 
     * @param <code>org.gridlab.gridsphere.portlet.User</code> The user.
     * 
     * @param <code>String</code> the credential subject identifying the credential.
     * 
     * @return <code>org.gridlab.gridsphere.core.security.Credential<code> The credential.
     */
    public Credential getCredential(User user, String subject)
        throws CredentialNotActiveException;

    /**
     * Inserts the given credential into the list of credentials currently in service for
     * the given user.
     * 
     * @param <code>org.gridlab.gridsphere.portlet.User</code> The user.
     * 
     * @param <code>org.gridlab.gridsphere.core.security.Credential</code> The credential 
     *        to put in service.
     */
    public void putCredential(User user, Credential credential)
       throws CredentialNotAllowedException, 
              CredentialExpiredException;

    /**
     * Inserts the given list of credentials into the list of credentials currently in service
     * for the given user.
     *
     * @param <code>org.gridlab.gridsphere.portlet.User</code> The user.
     * 
     * @param <code>List</code> of <code>org.gridlab.gridsphere.core.security.Credential</code> 
     *        The credentials to put in service.
     */
    public void putCredentials(User user, List credentials)
       throws CredentialNotAllowedException, 
              CredentialExpiredException;

    /**
     * Removes and destroys the credential identified by the given subject from the list of 
     * credentials currently in service for the given user.
     *
     * @param <code>org.gridlab.gridsphere.portlet.User</code> The user.
     * 
     * @param <code>org.gridlab.gridsphere.core.security.Credential</code> The subject for
     *        the credential to destroy.
     */
    public void destroyCredential(User user, String subject);

    /**
     * Removes and destroys the list of credentials currently in service for the given user.
     *
     * @param <code>org.gridlab.gridsphere.portlet.User</code> The user.
     */
    public void destroyCredentials(User user);

    /**
     * Returns true if there are credentials currently held by this service for a given user
     * and false otherwise.
     *
     * @param <code>org.gridlab.gridsphere.portlet.User</code> The user.
     * 
     * @return <code>boolean<code>
     */
    public boolean hasCredentials(User user);

    /**
     * Returns true if the credential identified by the given subject is held by this service
     * for a given user and false otherwise.
     *
     * @param <code>org.gridlab.gridsphere.portlet.User</code> The user.
     *
     * @param <code>String</code> The credential subject in question.
     * 
     * @return <code>boolean<code>
     */
    public boolean hasCredential(User user, String subject);

    /**
     * Returns the credential associated with a particular host for the given user. If no
     * credential has been associated and/or the credential is not currently held by the
     * this service, an exception is thrown.
     *
     * @param <code>org.gridlab.gridsphere.portlet.User</code> The user.
     *
     * @param <code>String</code> The assigned hostname.
     * 
     * @return <code>org.gridlab.gridsphere.core.security.Credential<code> The credential.
     */
    public Credential getAssignedCredential(User user, String hostname) 
        throws CredentialNotAssignedException, CredentialNotActiveException;

    /**
     * Assigns credentials identified by the given subject for use with hosts 
     * identified by the given hostname for the given user.
     * <p>
     * This allows all users to specify that credentials with the given subject 
     * should be used for authenticating and authorizing the given user to resources
     * on a particular host system
     *
     * @param <code>org.gridlab.gridsphere.portlet.User</code> The user.
     *
     * @param <code>String</code> The credential subject.
     *
     * @param <code>String</code> The hostname to which to assign the credential.
     */
    public void assignCredential(User user, String subject, String hostname);

    /**
     * Unassigns credentials identified by the given subject for use for hosts 
     * identified by the given hostname for the given user.
     *
     * @param <code>org.gridlab.gridsphere.portlet.User</code> The user.
     *
     * @param <code>String</code> The credential subject.
     *
     * @param <code>String</code> The hostname from which to unassign the credential.
     */
    public void unassignCredential(User user, String subject, String hostname)
        throws CredentialNotAssignedException;

    /**
     * Returns true if a credential has been assigned to the host identified by the
     * given hostname for the given user, and false otherwise.
     * <p>
     * This allows all services to test whether a particular credential is should
     * be used for authenticating and authorizing the given user to resources on a
     * particular host system
     *
     * @param <code>org.gridlab.gridsphere.portlet.User</code> The user.
     *
     * @param <code>String</code> The hostname in question.
     */
    public boolean hasAssignedCredential(User user, String hostname);

    /**
     * Returns the list of hosts for which credentials identified by the given 
     * subject have been assigned for the given user.
     *
     * @param <code>org.gridlab.gridsphere.portlet.User</code> The user.
     *
     * @param <code>String</code> The credential subject.
     * 
     * @return <code>List<code> of <code>String</code> The credential subjects.
     */
    public List getAssignedHosts(User user, String subject);
}
