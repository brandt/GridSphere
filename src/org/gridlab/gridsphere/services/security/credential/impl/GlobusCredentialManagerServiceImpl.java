/*
 * @author <a href="mailto:russell@aei-potsdam.mpg.de">Michael Paul Russell</a>
 * @version $Id$
 * <p>
 * This class is used for managing Globus credentials on behalf of portlet users.
 */
package org.gridlab.gridsphere.services.credential.impl;

/** GridSphere portlet imports **/
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

/** GridSphere services imports **/
import org.gridlab.gridsphere.services.security.credential.CredentialManagerService;

/** GridSphere security imports **/
import org.gridlab.gridsphere.core.security.Credential;
import org.gridlab.gridsphere.core.security.CredentialExpiredException;
import org.gridlab.gridsphere.core.security.CredentialNotActiveException;
import org.gridlab.gridsphere.core.security.CredentialPermission;
import org.gridlab.gridsphere.core.security.CredentialPermissionNotFoundException;
import org.gridlab.gridsphere.core.security.CredentialMap;
import org.gridlab.gridsphere.core.security.CredentialMapNotFoundException;
import org.gridlab.gridsphere.core.security.impl.GlobusCredential;

/** Globus imports **/
import org.globus.security.GlobusProxy;
import org.globus.security.GlobusProxyException;

/** JDK imports **/
import java.util.List;

public class GlobusCredentialManagerServiceImpl 
    implements PortletServiceProvider, CredentialManagerService {

    private static PortletLog _log = SportletLog.getInstance(GlobusCredentialManagerServiceImpl.class);

    /****** PORTLET SERVICE METHODS *******/

    public void init(PortletServiceConfig config) {
    }

    public void destroy() {
        destroyCredentials();
    }

    /****** CREDENTIAL PERMISSION PERSISTENCE METHODS *******/

    public List loadCredentialPermissions() {
        return null;
    }

    public CredentialPermission loadCredentialPermission(String oid) 
        throws CredentialPermissionNotFoundException {
        return null;
    }

    public CredentialPermission loadCredentialPermissionWithSubjectPattern(String pattern)
        throws CredentialPermissionNotFoundException {
        return null;
    }

    public void saveCredentialPermission(CredentialPermission permission) {
    }

    public void deleteCredentialPermission(String oid)
        throws CredentialPermissionNotFoundException {
    }

    /****** CREDENTIAL PERMISSION CONVENIENCE METHODS *******/

    public boolean isSubjectPermitted(String subject) {
        return false;
    }

    /****** CREDENTIAL MAP PERSISTENCE METHODS *******/

    public List loadCredentialMaps() {
        return null;
    }

    public CredentialMap loadCredentialMap(String oid) 
        throws CredentialMapNotFoundException {
        return null;
    }
    
    public CredentialMap loadCredentialMapForSubject(String subject)
        throws CredentialMapNotFoundException {
        return null;
    }
 
    public List loadCredentialMapsForUser(User user) {
        return null;
    }

    public void saveCredentialMap(CredentialMap map)
       throws CredentialPermissionNotFoundException {
    }

    public void deleteCredentialMap(String oid)
        throws CredentialMapNotFoundException {
    }
    
    public void deleteCredentialMapForSubject(String subject)
        throws CredentialMapNotFoundException {
    }

    public void deleteCredentialMapsForUser(User user) {
    }

    /****** CREDENTIAL MAP CONVENIENCE METHODS *******/

    public User getUserMappedToSubject(String subject)
        throws CredentialMapNotFoundException {
        return null;
    }

    public List getSubjectsMappedToUser(User user) {
        return null;
    }

    public boolean isSubjectMappedToUser(String subject, User user) {
        return false;
    }

    public List getHostNamesMappedToSubject(String subject)
        throws CredentialMapNotFoundException {
        return null;
    }

    public List getSubjectsMappedToHostName(String hostName) {
        return null;
    }

    public boolean isSubjectMappedToHostName(String subject, String hostName) {
        return false;
    }

    /****** CREDENTIAL "VAULT" METHODS *******/

    public List getCredentials(User user) {
        return null;
    }
    
    public Credential getCredential(String subject)
        throws CredentialMapNotFoundException,
               CredentialNotActiveException {
        return null;
    }

    public void putCredential(Credential credential)
        throws CredentialPermissionNotFoundException, 
               CredentialMapNotFoundException,
               CredentialExpiredException {
    }

    public void putCredentials(List credentials)
        throws CredentialPermissionNotFoundException, 
               CredentialMapNotFoundException,
               CredentialExpiredException {
    }

    public void destroyCredential(String subject)
        throws CredentialMapNotFoundException,
               CredentialNotActiveException {
    }

    public void destroyCredentials(User user) {
    }
    
    public boolean hasCredentials(User user) {
        return false;
    }

    public boolean hasCredential(String subject) {
        return false;
    }

    public List getCredentialsForHostName(User user, String hostName)
        throws CredentialMapNotFoundException,
               CredentialNotActiveException {
        return null;
    }

    public List getActiveCredentialSubjects() {
        return null;
    }

    public List getActiveCredentialSubjects(User user) {
        return null;
    }

    /****** PRIVATE METHODS *******/

    /**
     * Destroys all credentials in vault.
     */
    private void destroyCredentials() {
    }
}
