/*
 * @author <a href="mailto:russell@aei-potsdam.mpg.de">Michael Paul Russell</a>
 * @version $Id$
 * <p>
 * This class is used for managing Globus credentials on behalf of portlet users.
 */
package org.gridlab.gridsphere.services.security.credential.impl;

import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;

import org.gridlab.gridsphere.core.persistence.castor.PersistenceManagerRdbms;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.core.persistence.BaseObject;

import org.gridlab.gridsphere.services.security.credential.Credential;
import org.gridlab.gridsphere.services.security.credential.CredentialPermission;
import org.gridlab.gridsphere.services.security.credential.CredentialNotPermittedException;
import org.gridlab.gridsphere.services.security.credential.CredentialMapping;
import org.gridlab.gridsphere.services.security.credential.MappingNotFoundException;
import org.gridlab.gridsphere.services.security.credential.CredentialRetrievalClient;
import org.gridlab.gridsphere.services.security.credential.CredentialRetrievalException;
import org.gridlab.gridsphere.services.security.credential.CredentialManagerService;

import org.gridlab.gridsphere.services.security.credential.impl.GlobusCredential;
import org.gridlab.gridsphere.services.security.credential.impl.GlobusCredentialRetrievalClient;
import org.gridlab.gridsphere.services.security.credential.impl.GlobusCredentialMapping;
import org.gridlab.gridsphere.services.security.credential.impl.GlobusCredentialPermission;

import org.gridlab.gridsphere.services.user.UserManagerService;

import org.globus.security.GlobusProxy;
import org.globus.security.GlobusProxyException;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

public final class GlobusCredentialManagerService
    implements PortletServiceProvider, CredentialManagerService {

    private static PortletLog _log = SportletLog.getInstance(GlobusCredentialManagerService.class);
    private PersistenceManagerRdbms pm = PersistenceManagerRdbms.getInstance();
    private UserManagerService userManager = null;
    private CredentialRetrievalClient retrievalClient = null;
    private Map credentials = Collections.synchronizedSortedMap(new TreeMap());
    private String credentialPermissionImpl = GlobusCredentialPermission.class.getName();
    private String credentialMappingImpl = GlobusCredentialMapping.class.getName();

    /****** PORTLET SERVICE METHODS *******/

    public void init(PortletServiceConfig config) {
        _log.info("Entering init()");
        initServices();
        initCredentialRetrievalClient(config);
        _log.info("Exiting init()");
    }

    private void initServices() {
        // Get instance of service factory
        PortletServiceFactory factory = SportletServiceFactory.getInstance();
        // Get instance of helper services
        try {
            this.userManager
                    = (UserManagerService)factory.createPortletService(UserManagerService.class,
                                                                       null, true);
        } catch (Exception e) {
            _log.error("Unable to initialize services: ", e);
        }
    }

    private void initCredentialRetrievalClient(PortletServiceConfig config) {
        // Hostname init parameter
        String host = config.getInitParameter("retrievalHost");
        if (host == null) {
            host = "";
        }
        if (host.equals("")) {
            host = GlobusCredentialRetrievalClient.DEFAULT_HOST;
            _log.warn("Credential retrieval host not set. Using default value " + host);
        }
        // Port init parameter
        int port = GlobusCredentialRetrievalClient.DEFAULT_PORT;
        try {
            port = (new Integer(config.getInitParameter("retrievalPort"))).intValue();
        } catch (Exception e) {
            _log.warn("Credential retrieval port not valid. Using default value " + port);
        }
        // Lifetime init parameter
        long lifetime =  GlobusCredentialRetrievalClient.DEFAULT_LIFETIME;
        try {
            lifetime = (new Long(config.getInitParameter("retrievalLifetime"))).longValue();
        } catch (Exception e) {
          _log.warn("Credential retrieval lifetime not valid. Using default value " + lifetime);
        }
        _log.info("Credential retrieval hostname = " + host);
        _log.info("Credential retrieval port = " + port);
        _log.info("Credential default lifetime = " + lifetime);
        // Save credential retrieval client
        this.retrievalClient = new GlobusCredentialRetrievalClient(host, port, lifetime);
    }

    public void destroy() {
        destroyCredentials();
    }

    /****** CREDENTIAL PERMISSION PERSISTENCE METHODS *******/

    public List getCredentialPermissions() {
        try {
            String query = "select cp from "
                         + this.credentialPermissionImpl
                         + " cp";
            _log.debug(query);
            return this.pm.restoreList(query);
        } catch (PersistenceManagerException e) {
            _log.error("Error retrieving credential permissions", e);
            return new Vector();
        }
    }

    public CredentialPermission getCredentialPermission(String pattern) {
        try {
            String query = "select cp from "
                         + this.credentialPermissionImpl
                         + " cp where cp.permittedSubjects=\"" + pattern + "\"";
            _log.debug(query);
            return (CredentialPermission)this.pm.restoreObject(query);
        } catch (PersistenceManagerException e) {
            _log.error("Error retrieving credential permission", e);
            return null;
        }
    }

    public CredentialPermission createCredentialPermission(String pattern) {
        CredentialPermission permission = null;
        // Create new permission of proper type
        try {
            permission = (CredentialPermission)Class.forName(this.credentialPermissionImpl).newInstance();
        } catch (Exception e) {
            _log.error("Error creating instance of credential permission", e);
        }
        permission.setPermittedSubjects(pattern);
        createCredentialPermission(permission);
        return permission;
    }

    public CredentialPermission createCredentialPermission(String pattern, String description) {
        CredentialPermission permission = null;
        // Create new permission of proper type
        try {
            permission = (CredentialPermission)Class.forName(this.credentialPermissionImpl).newInstance();
        } catch (Exception e) {
            _log.error("Error creating instance of credential permission", e);
        }
        permission.setPermittedSubjects(pattern);
        permission.setDescription(description);
        createCredentialPermission(permission);
        return permission;
    }

    public void createCredentialPermission(CredentialPermission permission) {
        String pattern = permission.getPermittedSubjects();
        // Check that no permission (already) exists with given pattern
        if (!existsCredentialPermission(pattern)) {
            _log.debug("Creating credential permission " + pattern);
            try {
                this.pm.create(permission);
            } catch (PersistenceManagerException e) {
                _log.error("Error creating credential permission", e);
            }
        }
    }

    public void updateCredentialPermission(CredentialPermission permission) {
        String pattern = permission.getPermittedSubjects();
        _log.debug("Updating credential permission " + pattern);
        try {
            this.pm.update(permission);
        } catch (PersistenceManagerException e) {
            _log.error("Error updating credential permission", e);
        }
    }

    public void deleteCredentialPermission(String pattern) {
        _log.debug("Deleting credential permission " + pattern);
        try {
            CredentialPermission permission = getCredentialPermission(pattern);
            this.pm.delete(permission);
        } catch (PersistenceManagerException e) {
            _log.error("Error deleting credential permission", e);
        }
    }

    public boolean existsCredentialPermission(String pattern) {
        _log.debug("Testing if permission " + pattern + " exists");
        String value = null;
        try {
            String query = "select cp.permittedSubjects from "
                         + this.credentialPermissionImpl
                         + " cp where cp.permittedSubjects=\"" + pattern + "\"";
            _log.debug(query);
            value = (String)this.pm.restoreObject(query);
        } catch (PersistenceManagerException e) {
            _log.error("Error checking if credential permission exists", e);
            return false;
        }
        return (value != null);
    }

    /****** CREDENTIAL PERMISSION CONVENIENCE METHODS *******/

    public List getPermittedCredentialSubjects() {
        List permittedSubjects = null;
        try {
            String query = "select cp.permittedSubjects from "
                         + this.credentialPermissionImpl
                         + " cp";
            _log.debug(query);
            permittedSubjects = this.pm.restoreList(query);
        } catch (PersistenceManagerException e) {
            _log.error("Error retrieving permitted subjects!", e);
            permittedSubjects = new Vector();
        }
        return permittedSubjects;
    }

    public boolean isCredentialPermitted(String subject) {
        boolean answer = false;
        Iterator permissions = getCredentialPermissions().iterator();
        while (permissions.hasNext()) {
            CredentialPermission permission = (CredentialPermission)permissions.next();
            if (permission.isCredentialPermitted(subject)) {
                answer = true;
                break;
            }
        }
        return answer;
    }

    /****** CREDENTIAL MAPPING PERSISTENCE METHODS *******/

    public List getCredentialMappings() {
        try {
            String query = "select cm from "
                         + this.credentialMappingImpl
                         + " cm";
            _log.debug(query);
            return this.pm.restoreList(query);
        } catch (PersistenceManagerException e) {
            _log.error("Error retrieving credential mappings ", e);
            return new Vector();
        }
    }

    public CredentialMapping getCredentialMapping(String subject) {
        try {
            String query = "select cm from "
                         + this.credentialMappingImpl
                         + " cm where cm.subject=\"" + subject + "\"";
            _log.debug(query);
            return (CredentialMapping)this.pm.restoreObject(query);
        } catch (PersistenceManagerException e) {
            _log.error("Error retrieving credential mapping " + e);
            return null;
        }
    }

    public CredentialMapping createCredentialMapping(String subject, User user)
            throws CredentialNotPermittedException {
        GlobusCredentialMapping mapping = null;
        mapping = new GlobusCredentialMapping();
        mapping.setSubject(subject);
        mapping.setUser(user);
        createCredentialMapping(mapping);
        return mapping;
    }

    public void createCredentialMapping(CredentialMapping mapping)
            throws CredentialNotPermittedException {
        String subject = mapping.getSubject();
        // Check that no mapping already exists for given subject
        if (existsCredentialMapping(subject)) {
            throw new CredentialNotPermittedException("Mapping already exists for given subject");
        // Check that the subject is permitted for use by this manager
        } else if (!isCredentialPermitted(subject)) {
            throw new CredentialNotPermittedException("Credential subject not permitted");
        }
        if (_log.isDebugEnabled()) {
            _log.debug("Creating mapping " + mapping);
        }
        // Save a record of object to database
        try {
            this.pm.create(mapping);
        } catch (PersistenceManagerException e) {
            _log.error("Error creating credential mapping " + e);
        }
    }

    public void updateCredentialMapping(CredentialMapping mapping)
            throws CredentialNotPermittedException {
        if (_log.isDebugEnabled()) {
            _log.debug("Updating mapping " + mapping);
        }
        try {
            this.pm.update(mapping);
        } catch (PersistenceManagerException e) {
            _log.error("Error updating credential mapping " + e);
        }
    }

    public void deleteCredentialMapping(String subject) {
        CredentialMapping mapping = getCredentialMapping(subject);
        if (mapping != null) {
            deleteCredentialMapping(mapping);
        }
    }

    public void deleteCredentialMapping(CredentialMapping mapping) {
        try {
            this.pm.delete(mapping);
        } catch (PersistenceManagerException e) {
            _log.error("Error deleting credential mapping ", e);
        }
    }

    public List getCredentialMappings(User user) {
        try {
            String query = "select cm from "
                         + this.credentialMappingImpl
                         + " cm where cm.user=" + user.getID();
            _log.debug(query);
            return this.pm.restoreList(query);
        } catch (PersistenceManagerException e) {
            _log.error("Error retrieving credential mappings for user", e);
            return new Vector();
        }
    }

    public void deleteCredentialMappings(User user) {
        try {
            String query = "delete cm from "
                         + this.credentialMappingImpl
                         + " cm where cm.user=" + user.getID();
            _log.debug(query);
            this.pm.deleteList(query);
        } catch (PersistenceManagerException e) {
            _log.error("Error removing credential maps for user", e);
        }
    }

    public boolean existsCredentialMapping(String subject) {
        _log.debug("Testing if mapping for " + subject + " exists");
        String value = null;
        try {
            String query = "select cm.subject from "
                         + this.credentialMappingImpl
                         + " cm where cm.subject=\"" + subject + "\"";
            _log.debug(query);
            value = (String)this.pm.restoreObject(query);
        } catch (PersistenceManagerException e) {
            _log.error("Error checking if credential mapping exists", e);
            return false;
        }
        return (value != null);
    }

    /****** CREDENTIAL MAPPING CONVENIENCE METHODS *******/

    public User getCredentialUser(String subject) {
        User user = null;
        CredentialMapping mapping = getCredentialMapping(subject);
        if (mapping != null) {
            user = mapping.getUser();
        }
        return user;
    }

    public List getCredentialSubjects(User user) {
        List subjects = null;
        String query = "select cm.subject from "
                     + this.credentialMappingImpl
                     + " cm where cm.user=" + user.getID();
        _log.debug(query);
        try {
            subjects = this.pm.restoreList(query);
        } catch (PersistenceManagerException e) {
            _log.error("Error retrieving credential subjects for user", e);
            subjects = new Vector();
        }
        return subjects;
    }

    public List getCredentialTags(User user) {
        List tags = new Vector();
        String query = "select cm.tag from "
                     + this.credentialMappingImpl
                     + " cm where cm.user=" + user.getID();
        _log.debug(query);
        try {
            tags = this.pm.restoreList(query);
        } catch (PersistenceManagerException e) {
            _log.error("Error retrieving credential tags for user", e);
            tags = new Vector();
        }
        return tags;
    }

    public String getCredentialTag(String subject)
            throws MappingNotFoundException {
        String tag = null;
        String query = "select cm.tag from "
                     + this.credentialMappingImpl
                     + " cm where cm.subject=\"" + subject + "\"";
        _log.debug(query);
        try {
            tag = (String)this.pm.restoreObject(query);
        } catch (PersistenceManagerException e) {
            throw new MappingNotFoundException("No credential mapping exists for " + subject);
        }
        return tag;
    }

    public void setCredentialTag(String subject, String tag)
            throws MappingNotFoundException {
        CredentialMapping mapping = getCredentialMapping(subject);
        if (mapping == null) {
            throw new MappingNotFoundException("No credential mapping for " + subject);

        }
        mapping.setTag(tag);
        try {
            this.pm.update(mapping);
        } catch (PersistenceManagerException e) {
            _log.error("Error upating credential mapping for " + subject, e);
        }
    }

    public List getCredentialHosts(String subject)
            throws MappingNotFoundException {
        List hosts = null;
        String query = "select cm.hosts from "
                     + this.credentialMappingImpl
                     + " cm where cm.subject=\"" + subject + "\"";
        _log.debug(query);
        try {
            hosts = this.pm.restoreList(query);
        } catch (PersistenceManagerException e) {
            throw new MappingNotFoundException("No credential mapping exists for " + subject);
        }
        return hosts;
    }

    public void addCredentialHost(String subject, String host)
            throws MappingNotFoundException {
        CredentialMapping mapping = getCredentialMapping(subject);
        if (mapping == null) {
            throw new MappingNotFoundException("No credential mapping exists for " + subject);
        }
        mapping.addHost(host);
    }

    public void removeCredentialHost(String subject, String host)
            throws MappingNotFoundException {
        CredentialMapping mapping = getCredentialMapping(subject);
        if (mapping == null) {
            throw new MappingNotFoundException("No credential mapping exists for " + subject);
        }
        mapping.removeHost(host);
    }

    public List getCredentialSubjectsForHost(String host) {
        List subjects = null;
        Iterator maps = getCredentialMappings().iterator();
        while (maps.hasNext()) {
            CredentialMapping mapping = (CredentialMapping)maps.next();
            if (mapping.hasHost(host)) {
                subjects.add(mapping.getSubject());
            }
        }
        return subjects;
    }

    public List getCredentialSubjectsForHost(User user, String host) {
        List subjects = new Vector();
        Iterator maps = getCredentialMappings(user).iterator();
        while (maps.hasNext()) {
            CredentialMapping mapping = (CredentialMapping)maps.next();
            if (mapping.hasHost(host)) {
                subjects.add(mapping.getSubject());
            }
        }
        return subjects;
    }

    /****** CREDENTIAL RETRIEVAL METHODS *******/

    public String getCredentialRetrievalProtocol() {
        return this.retrievalClient.getProtocol();
    }

    public String getCredentialRetrievalHost() {
        return this.retrievalClient.getHost();
    }

    public int getCredentialRetrievalPort() {
        return this.retrievalClient.getPort();
    }

    public long getCredentialRetrievalLifetime() {
        return this.retrievalClient.getCredentialLifetime();
    }

    public List retrieveCredentials(User user, String password)
        throws CredentialRetrievalException {
        List credentials = new Vector();
        StringBuffer msgs = null;
        // Iterate through the credential mappings associated with user
        Iterator iterator = getCredentialMappings(user).iterator();
        while (iterator.hasNext()) {
            // For each mapping, check that a retrieval tag exists
            CredentialMapping mapping = (CredentialMapping)iterator.next();
            Credential credential = null;
            String tag = mapping.getTag();
            // If no retrieval tag, try next credential
            if (tag == null) {
                continue;
            }
            // Get subject for this credential
            String subject = mapping.getSubject();
            try {
                // Retrieve credential based on credential tag, subject, and given password
                credential = this.retrievalClient.retrieveCredential(tag, password, subject);
                // Store the retrieved credential
                storeCredential(credential);
                // Add credential to returned list
                credentials.add(credential);
            } catch (CredentialRetrievalException e) {
                // Record each error message we come across
                if (msgs == null) {
                    msgs = new StringBuffer();
                }
                String msg = e.getMessage();
                msgs.append(msg);
                msgs.append("\n");
                _log.debug(msg);
            } catch (CredentialNotPermittedException e) {
                // Record each error message we come across
                if (msgs == null) {
                    msgs = new StringBuffer();
                }
                String msg = e.getMessage();
                msgs.append(msg);
                msgs.append("\n");
                _log.debug(msg);
            }
        }
        // Throw exception if no credentials were
        // successfully retrieved and stored
        if (credentials.size() == 0) {
            // Provide the error messages from above
            throw new CredentialRetrievalException(msgs.toString());
        }
        return credentials;
   }

   public List refreshCredentials(User user)
        throws CredentialRetrievalException {
        throw new CredentialRetrievalException("Method not yet implemented!");
   }

    /****** CREDENTIAL STORAGE METHODS *******/

    public void storeCredential(Credential credential)
        throws CredentialNotPermittedException {
        String subject = credential.getSubject();
        // Check if credential is permitted
        if (isCredentialPermitted(subject)) {
            throw new CredentialNotPermittedException("Credential subject pattern not permitted!");
        }
        // Check if mapping exists
        User user = getCredentialUser(subject);
        if (user == null) {
            throw new CredentialNotPermittedException("Credential mapping not found for " + subject);
        }
        // Get user's credential collection
        Map userCredentials = getUserCredentialsMap(user);
        // Add this credential to that collection
        userCredentials.put(user.getID(), credential);
    }

    public void storeCredentials(List credentials)
        throws CredentialNotPermittedException {
        // Store each credential in list
        Iterator iterator = credentials.iterator();
        while (iterator.hasNext()) {
            Credential credential = (Credential)iterator.next();
            storeCredential(credential);
        }
    }

    public void destroyCredential(String subject) {
        // Get user mapped to subject
        User user = getCredentialUser(subject);
        // If user mapping exists
        if (user != null) {
            // Get user's credential collection
            Map userCredentials = (Map)this.credentials.get(user);
            // If user collection exists
            if (userCredentials != null) {
                // Remove the credential from the collection
                Credential credential = (Credential)userCredentials.remove(subject);
                // Destroy credential if not null
                if (credential != null) {
                    credential.destroy();
                }
            }
        }
    }

    private void destroyCredentials() {
        synchronized (this.credentials) {
            // Iterate through each user collection
            Iterator users = this.credentials.keySet().iterator();
            while (users.hasNext()) {
                User user = (User)users.next();
                // Get user's credential collection
                Map userCredentials = (Map)this.credentials.get(user);
                // Just being safe...
                if (userCredentials != null) {
                    synchronized (userCredentials) {
                        // Iterate through each credential and destroy it
                        Iterator iterator = userCredentials.values().iterator();
                        while (iterator.hasNext()) {
                            Credential credential = (Credential)iterator.next();
                            credential.destroy();
                        }
                    }
                }
            }
            // Now clear everything
            this.credentials.clear();
        }
    }

    public void destroyCredentials(User user) {
        // Remove mapping associated with user
        Map userCredentials = (Map)this.credentials.remove(user);
        // If not null, then destroy each credential in the mapping
        if (userCredentials != null) {
            synchronized (userCredentials) {
                // Destroy each credential in collection
                Iterator iterator = userCredentials.values().iterator();
                while (iterator.hasNext()) {
                    Credential credential = (Credential)iterator.next();
                    credential.destroy();
                }
            }
        }
    }

    /****** CREDENTIAL USEAGE METHODS *******/

    public List getActiveCredentials(User user) {
        List activeCredentials = new Vector();
        // Get user's credential collection
        Map userCredentials = (Map)this.credentials.get(user);
        // If not null...
        if (userCredentials != null) {
            synchronized (userCredentials) {
                // Iterate through the subjects in user's credential collection
                Iterator iterator = userCredentials.keySet().iterator();
                while (iterator.hasNext()) {
                    String subject = (String)iterator.next();
                    // Get the credential associated with subject
                    Credential credential = (Credential)userCredentials.get(subject);
                    // Just being safe...
                    if (credential == null) {
                        _log.debug("Credential not active " + subject);
                        continue;
                    }
                    // If expired then remove credential from collection
                    if (credential.isExpired()) {
                        _log.debug("Credential has expired " + credential.toString());
                        userCredentials.remove(subject);
                    // Otherwise, add to list of active credentials
                    } else {
                        activeCredentials.add(credential);
                    }
                }
            }
        }
        return activeCredentials;
    }

    public List getActiveCredentialsForHost(User user, String host) {
        List activeCredentials = new Vector();
        // Get subjects mapped to user and host
        List userSubjects = getCredentialSubjectsForHost(user, host);
        // Get user's credential collection
        Map userCredentials = (Map)this.credentials.get(user);
        if (userCredentials != null) {
            synchronized (userCredentials) {
                // Iterate through user's mapped subjects
                Iterator iterator = userSubjects.iterator();
                while (iterator.hasNext()) {
                    String subject = (String)iterator.next();
                    // Get credential with that subject
                    Credential credential = (Credential)userCredentials.get(subject);
                    // Just being safe...
                    if (credential == null) {
                        _log.debug("Credential not active " + subject);
                        continue;
                    }
                    // If expired, add to list of expired credentials
                    if (credential.isExpired()) {
                        _log.debug("Credential has expired " + credential.toString());
                        userCredentials.remove(subject);
                    // Otherwise, add to list of active credentials
                    } else {
                        activeCredentials.add(credential);
                    }
                }
            }
        }
        return activeCredentials;
    }

    public Credential getActiveCredential(String subject) {
        Credential credential = null;
        // Get user mapping for subject
        User user = getCredentialUser(subject);
        // If mapping exists
        if (user != null) {
            // Get user's credential collection
            Map userCredentials = (Map)this.credentials.get(user);
            // If collection exists
            if (userCredentials != null) {
                // Get credential from collection
                credential = (Credential)userCredentials.get(subject);
                // Just being safe...
                if (credential != null) {
                   // Check if it is expired
                   if (credential.isExpired()) {
                       // If so remove it and return null
                       _log.debug("Credential has expired " + credential.toString());
                        userCredentials.remove(credential.getSubject());
                       return null;
                   }
                }
            }
        }
        return credential;
    }

    public boolean isActiveCredential(String subject) {
        boolean answer = false;
        // Get user mapping for subject
        User user = getCredentialUser(subject);
        // If mapppng doesn't exist, return false
        if (user == null) {
            // Get user's credential collection
            Map userCredentials = (Map)this.credentials.get(user);
            // If empty, return false
            if (userCredentials != null) {
                // Otherwise, check if credential in collection
                answer = userCredentials.containsKey(subject);
            }
        }
        return answer;
    }

    public boolean hasActiveCredentials(User user) {
        Map userCredentials = getUserCredentialsMap(user);
        return (userCredentials.size() > 0);
    }

    public List getActiveCredentialSubjects() {
        List subjectList = new Vector();
        synchronized (this.credentials) {
            Iterator allCredentials = this.credentials.values().iterator();
            while (allCredentials.hasNext()) {
                Map userCredentials = (Map)allCredentials.next();
                synchronized (userCredentials) {
                    Iterator iterator = userCredentials.keySet().iterator();
                    while (iterator.hasNext()) {
                        String subject = (String)iterator.next();
                        subjectList.add(subject);
                    }
                }
            }
        }
        return subjectList;
    }

    public List getActiveCredentialSubjects(User user) {
        List subjectList = new Vector();
        // Get user's credential collection
        Map userCredentials = (Map)this.credentials.get(user);
        if (userCredentials != null) {
            synchronized (userCredentials) {
                Iterator iterator = userCredentials.keySet().iterator();
                while (iterator.hasNext()) {
                    String subject = (String)iterator.next();
                    subjectList.add(subject);
                }
            }
        }
        return subjectList;
    }

    private Map getUserCredentialsMap(User user) {
        String userID = user.getID();
        // Get user's credentials
        Map userCredentials = (Map)this.credentials.get(userID);
        // If mapping is empty, create new mapping
        if (userCredentials == null) {
            userCredentials = Collections.synchronizedSortedMap(new TreeMap());
            credentials.put(userID, userCredentials);
        }
        return userCredentials;
    }
}
