/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Jan 14, 2003
 * Time: 3:00:22 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.services.security.credential;

import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.user.impl.UserManagerServiceImpl;
import org.gridlab.gridsphere.services.user.UserManagerService;
import org.gridlab.gridsphere.services.security.AuthorizationException;
import org.gridlab.gridsphere.services.security.AuthorizationUtility;
import org.gridlab.gridsphere.services.security.credential.impl.GlobusCredentialManagerService;

import java.util.List;

/**
 * This class provides a secure proxy to implementations of the CredentialManagerService
 * interface. It throws AuthorizationException for each method in which the supplied
 * user is not authorized to call that method.
 */
public class CredentialManagerProxy
        implements PortletServiceProvider, CredentialManagerService {

    private static PortletLog _log = SportletLog.getInstance(CredentialManagerProxy.class);
    private AuthorizationUtility authorizer = null;
    private User user = null;
    private UserManagerService userManager = null;
    private CredentialManagerService credentialManager = null;

    private CredentialManagerProxy() {
    }

    public CredentialManagerProxy(PortletServiceProvider psp, User user) throws Exception {
        this.user = user;
        if (psp instanceof UserManagerServiceImpl) {
            this.credentialManager = (GlobusCredentialManagerService) psp;
        } else {
            throw new Exception("Unable to create CredentialManagerProxy");
        }
        this.user = user;
        this.authorizer = new AuthorizationUtility(user);
    }

    public void init(PortletServiceConfig config) {
        // Get instance of service factory
        PortletServiceFactory factory = SportletServiceFactory.getInstance();
        // Instantiate helper services
        try {
            this.userManager
                    = (UserManagerService)factory.createPortletService(UserManagerService.class, null, true);
            this.credentialManager
                    = (CredentialManagerService)factory.createPortletService(CredentialManagerService.class, null, true);
        } catch (Exception e) {
            _log.error("Unable to initialize services: ", e);
        }
    }

    public void destroy() {
    }

    /****** CREDENTIAL PERMISSION PERSISTENCE METHODS *******/

    /*
     * Must be super user
     */
    public List getCredentialPermissions() {
        this.authorizer.authorizeSuperUser();
        return this.credentialManager.getCredentialPermissions();
    }

    /*
     * Must be super user
     */
    public CredentialPermission getCredentialPermission(String permission) {
        this.authorizer.authorizeSuperUser();
        return this.credentialManager.getCredentialPermission(permission);
    }

    /*
     * Must be super user
     */
    public CredentialPermission createCredentialPermission(String permission) {
        this.authorizer.authorizeSuperUser();
        return this.credentialManager.createCredentialPermission(permission);
    }

    /*
     * Must be super user
     */
    public CredentialPermission createCredentialPermission(String permission, String description) {
        this.authorizer.authorizeSuperUser();
        return this.credentialManager.createCredentialPermission(permission);
    }

    /*
     * Must be super user
     */
    public void deleteCredentialPermission(String permission) {
        this.authorizer.authorizeSuperUser();
        this.credentialManager.deleteCredentialPermission(permission);
    }

    /*
     * Must be super user
     */
    public boolean existsCredentialPermission(String permission) {
        this.authorizer.authorizeSuperUser();
        return this.credentialManager.existsCredentialPermission(permission);
    }

    /****** CREDENTIAL PERMISSION MANIPULATION METHODS *******/

    /*
     * Must be super user
     */
    public List getPermittedCredentialSubjects() {
        this.authorizer.authorizeSuperUser();
        return this.credentialManager.getPermittedCredentialSubjects();
    }

    /*
     * Must be super user
     */
    public boolean isCredentialPermitted(String subject) {
        this.authorizer.authorizeSuperUser();
        return this.credentialManager.isCredentialPermitted(subject);
    }

    /****** CREDENTIAL MAPPING PERSISTENCE METHODS *******/

    /*
     * Must be super user
     */
    public List getCredentialMappings() {
        this.authorizer.authorizeSuperUser();
        return this.credentialManager.getCredentialMappings();
    }

    /*
     * Must be super or same user
     */
    public CredentialMapping getCredentialMapping(String subject) {
        User user = this.credentialManager.getCredentialUser(subject);
        this.authorizer.authorizeSuperOrSameUser(user);
        return this.credentialManager.getCredentialMapping(subject);
    }

    /*
     * Must be super user
     */
    public CredentialMapping createCredentialMapping(String subject, User user)
            throws CredentialNotPermittedException {
        this.authorizer.authorizeSuperUser();
        return this.credentialManager.createCredentialMapping(subject, user);
    }

    /*
     * Must be super user
     */
    public CredentialMapping createCredentialMapping(String subject, User user, String tag)
            throws CredentialNotPermittedException {
        this.authorizer.authorizeSuperUser();
        return this.credentialManager.createCredentialMapping(subject, user, tag);
    }

    /*
     * Must be super user
     */
    public void deleteCredentialMapping(String subject) {
        this.authorizer.authorizeSuperUser();
        this.credentialManager.deleteCredentialMapping(subject);
    }

    /*
     * Must be super or same user
     */
    public List getCredentialMappings(User user) {
        this.authorizer.authorizeSuperOrSameUser(user);
        return this.credentialManager.getCredentialMappings(user);
    }

    /*
     * Must be super user
     */
    public void deleteCredentialMappings(User user) {
        this.authorizer.authorizeSuperUser();
        this.credentialManager.deleteCredentialMappings(user);
    }

    /****** CREDENTIAL MAPPING MANIPULATION METHODS *******/

    /*
     * Must be super user
     */
    public User getCredentialUser(String subject) {
        this.authorizer.authorizeSuperUser();
        return this.credentialManager.getCredentialUser(subject);
    }

    /*
     * Must be super or same user
     */
    public List getCredentialSubjects(User user) {
        this.authorizer.authorizeSuperOrSameUser(user);
        return this.credentialManager.getCredentialSubjects(user);
    }

    /*
     * Must be super or same user
     */
    public List getCredentialTags(User user) {
        this.authorizer.authorizeSuperOrSameUser(user);
        return this.credentialManager.getCredentialTags(user);
    }

    /*
     * Must be super or same user
     */
    public String getCredentialTag(String subject)
            throws CredentialMappingNotFoundException {
        User user = getCredentialUser(subject);
        this.authorizer.authorizeSuperOrSameUser(user);
        return this.credentialManager.getCredentialTag(subject);
    }

    /*
     * Must be super user
     */
    public void setCredentialTag(String subject, String tag)
            throws CredentialMappingNotFoundException {
        this.authorizer.authorizeSuperUser();
        this.credentialManager.setCredentialTag(subject, tag);
    }

    /*
     * Must be super or same user
     */
    public String getCredentialLabel(String subject)
            throws CredentialMappingNotFoundException {
        User user = getCredentialUser(subject);
        this.authorizer.authorizeSuperOrSameUser(user);
        return this.credentialManager.getCredentialLabel(subject);
    }

    /*
     * Must be super user
     */
    public void setCredentialLabel(String subject, String label)
            throws CredentialMappingNotFoundException {
        this.authorizer.authorizeSuperUser();
        this.credentialManager.setCredentialLabel(subject, label);
    }

    /*
     * Must be super or same user
     */
    public List getCredentialHosts(String subject)
            throws CredentialMappingNotFoundException {
        User user = getCredentialUser(subject);
        this.authorizer.authorizeSuperOrSameUser(user);
        return this.credentialManager.getCredentialHosts(subject);
    }

    /*
     * Must be super or same user
     */
    public void addCredentialHost(String subject, String host)
            throws CredentialMappingNotFoundException {
        User user = getCredentialUser(subject);
        this.authorizer.authorizeSuperOrSameUser(user);
        this.credentialManager.addCredentialHost(subject, host);
    }

    /*
     * Must be super or same user
     */
    public void addCredentialHosts(String subject, List hosts)
            throws CredentialMappingNotFoundException {
        User user = getCredentialUser(subject);
        this.authorizer.authorizeSuperOrSameUser(user);
        this.credentialManager.addCredentialHosts(subject, hosts);
    }

    /*
     * Must be super or same user
     */
    public void removeCredentialHost(String subject, String host)
            throws CredentialMappingNotFoundException {
        User user = getCredentialUser(subject);
        this.authorizer.authorizeSuperOrSameUser(user);
        this.credentialManager.removeCredentialHost(subject, host);
    }

    /*
     * Must be super user
     */
    public List getCredentialSubjectsForHost(String host) {
        this.authorizer.authorizeSuperUser();
        return this.credentialManager.getCredentialSubjectsForHost(host);
    }

    /*
     * Must be super or same user
     */
    public List getCredentialSubjectsForHost(User user, String host) {
        this.authorizer.authorizeSuperOrSameUser(user);
        return this.credentialManager.getCredentialSubjectsForHost(user, host);
    }

    /****** CREDENTIAL RETRIEVAL METHODS *******/

    /*
     * Anyone can use
     */
    public String getCredentialRetrievalProtocol() {
        return this.credentialManager.getCredentialRetrievalProtocol();
    }

    /*
     * Anyone can use
     */
    public String getCredentialRetrievalHost() {
        return this.credentialManager.getCredentialRetrievalHost();
    }

    /*
     * Anyone can use
     */
    public int getCredentialRetrievalPort() {
        return this.credentialManager.getCredentialRetrievalPort();
    }

    /*
     * Anyone can use
     */
    public long getCredentialRetrievalLifetime() {
        return this.credentialManager.getCredentialRetrievalLifetime();
    }

    /*
     * Anyone can use
     */
    public List retrieveCredentials(User user, String passphrase)
            throws CredentialRetrievalException {
        return this.credentialManager.retrieveCredentials(user, passphrase);
    }

    /****** CREDENTIAL STORAGE METHODS *******/

    /*
     * Anyone can use
     */
    public void storeCredential(Credential credential)
            throws CredentialNotPermittedException {
        this.credentialManager.storeCredential(credential);
    }

    /*
     * Anyone can use
     */
    public void storeCredentials(List credentials)
            throws CredentialNotPermittedException {
        this.credentialManager.storeCredentials(credentials);
    }

    /*
     * Must be super or same user
     */
    public void destroyCredential(String subject) {
        User user = getCredentialUser(subject);
        this.authorizer.authorizeSuperOrSameUser(user);
        this.credentialManager.destroyCredential(subject);
    }

    /*
     * Must be super or same user
     */
    public void destroyCredentials(User user) {
        this.authorizer.authorizeSuperOrSameUser(user);
        this.credentialManager.destroyCredentials(user);
    }

    /****** CREDENTIAL USEAGE METHODS *******/

    /*
     * Must be super or same user
     */
    public List getActiveCredentials(User user) {
        this.authorizer.authorizeSuperOrSameUser(user);
        return this.credentialManager.getActiveCredentials(user);
    }

    /*
     * Must be super or same user
     */
    public Credential getActiveCredential(String subject) {
        User user = getCredentialUser(subject);
        this.authorizer.authorizeSuperOrSameUser(user);
        return this.credentialManager.getActiveCredential(subject);
    }

    /*
     * Must be super or same user
     */
    public List getActiveCredentialsForHost(User user, String host) {
        this.authorizer.authorizeSuperOrSameUser(user);
        return this.credentialManager.getActiveCredentialsForHost(user, host);
    }

    /*
     * Must be super or same user
     */
    public boolean isActiveCredential(String subject) {
        User user = getCredentialUser(subject);
        this.authorizer.authorizeSuperOrSameUser(user);
        return this.credentialManager.isActiveCredential(subject);
    }

    /*
     * Must be super or same user
     */
    public boolean hasActiveCredentials(User user) {
        this.authorizer.authorizeSuperOrSameUser(user);
        return this.credentialManager.hasActiveCredentials(user);
    }

    /*
     * Must be super user
     */
    public List getActiveCredentialSubjects() {
        this.authorizer.authorizeSuperUser();
        return this.credentialManager.getActiveCredentialSubjects();
    }

    /*
     * Must be super or same user
     */
    public List getActiveCredentialSubjects(User user) {
        this.authorizer.authorizeSuperOrSameUser(user);
        return this.credentialManager.getActiveCredentialSubjects(user);
    }
}
