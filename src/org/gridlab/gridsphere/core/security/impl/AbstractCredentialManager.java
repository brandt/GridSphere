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
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;

import org.gridlab.gridsphere.core.persistence.castor.PersistenceManagerRdbms;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.core.persistence.BaseObject;
import org.gridlab.gridsphere.core.security.Credential;
import org.gridlab.gridsphere.core.security.CredentialManager;
import org.gridlab.gridsphere.core.security.CredentialNotActiveException;
import org.gridlab.gridsphere.core.security.CredentialPermission;
import org.gridlab.gridsphere.core.security.CredentialPermissionNotFoundException;
import org.gridlab.gridsphere.core.security.CredentialNotPermittedException;
import org.gridlab.gridsphere.core.security.CredentialMapping;
import org.gridlab.gridsphere.core.security.MappingNotFoundException;
import org.gridlab.gridsphere.core.security.CredentialExpiredException;
import org.gridlab.gridsphere.core.security.CredentialRetrievalClient;
import org.gridlab.gridsphere.core.security.CredentialRetrievalException;
import org.gridlab.gridsphere.core.security.impl.GlobusCredentialMapping;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

public abstract class AbstractCredentialManager implements CredentialManager {

    private static PortletLog _log = SportletLog.getInstance(AbstractCredentialManager.class);
    private CredentialRetrievalClient retrievalClient = null;
    private PersistenceManagerRdbms pm = PersistenceManagerRdbms.getInstance();
    private Map credentials = Collections.synchronizedSortedMap(new TreeMap());
    private String jdoCredentialPermissionImpl = null;
    private String jdoCredentialMappingImpl = null;


    /****** CREDENTIAL PERMISSION PERSISTENCE METHODS *******/

    public List getCredentialPermissions() {
        try {
            String query = "select cp from "
                         + this.jdoCredentialPermissionImpl
                         + " cp";
            return this.pm.restoreList(query);
        } catch (PersistenceManagerException e) {
            _log.error("Error retrieving credential permissions", e);
            return new Vector();
        }
    }

    public CredentialPermission getCredentialPermission(String pattern) {
        try {
            String query = "select cp from "
                         + this.jdoCredentialPermissionImpl
                         + " cp where cp.pattern=" + pattern;
            return (CredentialPermission)this.pm.restoreObject(query);
        } catch (PersistenceManagerException e) {
            _log.error("Error retrieving credential permission", e);
            return null;
        }
    }

    public void createCredentialPermission(CredentialPermission permission) {
        try {
            this.pm.create(permission);
        } catch (PersistenceManagerException e) {
            _log.error("Error saving credential permission", e);
        }
    }

    public void updateCredentialPermission(CredentialPermission permission) {
        try {
            this.pm.update(permission);
        } catch (PersistenceManagerException e) {
            _log.error("Error saving credential permission", e);
        }
    }

    public void deleteCredentialPermission(String pattern) {
        try {
            CredentialPermission permission = getCredentialPermission(pattern);
            this.pm.delete(permission);
        } catch (PersistenceManagerException e) {
            _log.error("Error removing credential permission", e);
        }
    }

    /****** CREDENTIAL PERMISSION CONVENIENCE METHODS *******/

    public List getPermittedCredentialSubjects() {
        List permittedSubjects = null;
        try {
            String query = "select cp.pattern from "
                         + this.jdoCredentialPermissionImpl;
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
        try {
            String query = "select cp.pattern from "
                         + this.jdoCredentialPermissionImpl
                         + " cp where " + subject + " like cp.pattern";
            _log.debug(query);
            String pattern = (String)this.pm.restoreObject(query);
            if (_log.isDebugEnabled()) {
                _log.debug("Credential permission for subject "
                           + subject + " has pattern " + pattern);
            }
            answer = true;
        } catch (PersistenceManagerException e) {
            _log.error("No credential permission exists for subject " + subject, e);
        }
        return answer;
    }

    /****** CREDENTIAL MAPPING PERSISTENCE METHODS *******/

    public List getCredentialMappings() {
        try {
            String query = "select cm from "
                         + this.jdoCredentialMappingImpl
                         + " cm";
            return this.pm.restoreList(query);
        } catch (PersistenceManagerException e) {
            _log.error("Error retrieving credential maps ", e);
            return new Vector();
        }
    }

    public CredentialMapping getCredentialMapping(String subject) {
        try {
            String query = "select cm from "
                         + this.jdoCredentialMappingImpl
                         + " cm where cm.subject=" + subject;
            return (CredentialMapping)this.pm.restoreObject(query);
        } catch (PersistenceManagerException e) {
            _log.error("Error retrieving credential mapping " + e);
            return null;
        }
    }

    public void createCredentialMapping(CredentialMapping mapping)
            throws CredentialNotPermittedException {
        String subject = mapping.getSubject();
        if (isCredentialPermitted(subject)) {
            GlobusCredentialMapping mappingImpl = (GlobusCredentialMapping)mapping;
            try {
                this.pm.create(mappingImpl);
            } catch (PersistenceManagerException e) {
                _log.error("Error saving credential mapping " + e);
            }
        } else {
            throw new CredentialNotPermittedException("Credential subject not permitted");
        }
    }

    public void updateCredentialMapping(CredentialMapping mapping)
            throws CredentialNotPermittedException {
        String subject = mapping.getSubject();
        if (isCredentialPermitted(subject)) {
            GlobusCredentialMapping mappingImpl = (GlobusCredentialMapping)mapping;
            try {
                this.pm.update(mappingImpl);
            } catch (PersistenceManagerException e) {
                _log.error("Error saving credential mapping " + e);
            }
        } else {
            throw new CredentialNotPermittedException("Credential subject not permitted");
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
            _log.error("Error removing credential mapping ", e);
        }
    }

    public List getCredentialMappings(User user) {
        try {
            String query = "select cm from "
                         + this.jdoCredentialMappingImpl
                         + " cm where cm.user=" + user;
            return this.pm.restoreList(query);
        } catch (PersistenceManagerException e) {
            _log.error("Error retrieving credential maps for user", e);
            return new Vector();
        }
    }

    public void deleteCredentialMappings(User user) {
        try {
            String query = "delete cm from "
                         + this.jdoCredentialMappingImpl
                         + " cm where cm.user=" + user;
            this.pm.deleteList(query);
        } catch (PersistenceManagerException e) {
            _log.error("Error removing credential maps for user", e);
        }
    }

    /****** CREDENTIAL MAPPING CONVENIENCE METHODS *******/

    public User getCredentialUser(String subject) {
        User user = null;
        String query = "select cm.user from "
                     + this.jdoCredentialMappingImpl
                     + " cm where cm.subject=" + subject;
        try {
            user = (User)this.pm.restoreObject(query);
        } catch (PersistenceManagerException e) {
            _log.error("Error retrieving credential user", e);
        }
        return user;
    }

    public List getCredentialSubjects(User user) {
        List subjects = null;
        String query = "select cm.subject from "
                     + this.jdoCredentialMappingImpl
                     + " cm where cm.user=" + user;
        try {
            subjects = this.pm.restoreList(query);
        } catch (PersistenceManagerException e) {
            _log.error("Error retrieving credential subjects for user", e);
            subjects = new Vector();
        }
        return subjects;
    }

    public void addCredentialSubject(User user, String subject)
            throws CredentialNotPermittedException {
        CredentialMapping mapping = getCredentialMapping(subject);
        if (mapping == null) {
            mapping = new GlobusCredentialMapping();
            mapping.setSubject(subject);
            mapping.setUser(user);
            createCredentialMapping(mapping);
        }
    }

    public void removeCredentialSubject(User user, String subject) {
        CredentialMapping mapping = getCredentialMapping(subject);
        if (mapping != null) {
            deleteCredentialMapping(mapping);
        }
    }

    public List getCredentialTags(User user) {
        List tags = new Vector();
        String query = "select cm.tag from "
                     + this.jdoCredentialMappingImpl
                     + " cm where cm.user=" + user;
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
                     + this.jdoCredentialMappingImpl
                     + " cm where cm.subject=" + subject;
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
        String query = "select cm.subject from "
                     + this.jdoCredentialMappingImpl
                     + " cm where cm.subject=" + subject;
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

    public void retrieveCredentials(User user, String password)
        throws CredentialRetrievalException {
        StringBuffer msgs = null;
        int numReturned = 0;
        // Iterate through the credential mappings associated with user
        Iterator iterator = getCredentialMappings(user).iterator();
        while (iterator.hasNext()) {
            // For each mapping, get the credential tag and subject
            CredentialMapping mapping = (CredentialMapping)iterator.next();
            Credential credential = null;
            String tag = mapping.getTag();
            String subject = mapping.getSubject();
            try {
                // Retrieve credential based on credential tag, subject, and given password
                credential = this.retrievalClient.retrieveCredential(tag, password, subject);
                // Store the retrieved credential
                storeCredential(credential);
                // Update retrieved credentials count
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
        // Throw exception if no credentials were retrieved
        if (numReturned == 0) {
            // Provide the error messages from above
            throw new CredentialRetrievalException(msgs.toString());
        }
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
        Map userCredentials = getUserCredentials(user);
        // Add this credential to that collection
        userCredentials.put(user.getUserID(), credential);
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

    /****** CREDENTIAL USEAGE METHODS *******/

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
        Map userCredentials = getUserCredentials(user);
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

    /*** PROTECTED METHODS ***/

    protected String getCredentialPermissionImpl() {
        return this.jdoCredentialPermissionImpl;
    }

    protected void setCredentialPermissionImpl(String impl) {
        this.jdoCredentialPermissionImpl = impl;
    }

    protected String getCredentialMappingImpl() {
        return this.jdoCredentialMappingImpl;
    }

    protected void setCredentialMappingImpl(String impl) {
        this.jdoCredentialMappingImpl = impl;
    }

    protected CredentialRetrievalClient getCredentialRetrievalClient() {
        return this.retrievalClient;
    }

    protected void setCredentialRetrievalClient(CredentialRetrievalClient client) {
        this.retrievalClient = client;
    }

    protected Map getUserCredentials(User user) {
        String userID = user.getUserID();
        // Get user's credentials
        Map userCredentials = (Map)this.credentials.get(userID);
        // If mapping is empty, create new mapping
        if (userCredentials == null) {
            userCredentials = Collections.synchronizedSortedMap(new TreeMap());
            credentials.put(userID, userCredentials);
        }
        return userCredentials;
    }

    protected void destroyAllCredentials() {
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
}
