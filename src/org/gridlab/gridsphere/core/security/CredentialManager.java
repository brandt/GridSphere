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
import org.gridlab.gridsphere.core.security.Credential;
import org.gridlab.gridsphere.core.security.CredentialNotActiveException;
import org.gridlab.gridsphere.core.security.CredentialPermission;
import org.gridlab.gridsphere.core.security.CredentialPermissionNotFoundException;
import org.gridlab.gridsphere.core.security.CredentialNotPermittedException;
import org.gridlab.gridsphere.core.security.CredentialMap;
import org.gridlab.gridsphere.core.security.CredentialMapNotFoundException;
import org.gridlab.gridsphere.core.security.CredentialExpiredException;
import org.gridlab.gridsphere.core.security.CredentialRetrievalClient;
import org.gridlab.gridsphere.core.security.CredentialRetrievalException;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

public interface CredentialManager {

    /****** CREDENTIAL PERMISSION METHODS *******/

    public List getCredentialPermissions();

    public CredentialPermission getCredentialPermission(String oid);

    public CredentialPermission getCredentialPermissionWithSubjectPattern(String pattern);

    public void saveCredentialPermission(CredentialPermission permission);

    public void removeCredentialPermission(String oid);

    /****** CREDENTIAL PERMISSION CONVENIENCE METHODS *******/

    public boolean isCredentialPermitted(String subject);

    /****** CREDENTIAL MAP PERSISTENCE METHODS *******/

    public List getCredentialMaps();

    public CredentialMap getCredentialMap(String oid);

    public void saveCredentialMap(CredentialMap map)
            throws CredentialNotPermittedException;

    public void removeCredentialMap(String oid);

    public List getCredentialMaps(User user);

    public void removeCredentialMaps(User user);

    public CredentialMap getCredentialMapWithSubject(String subject);

    public void removeCredentialMapWithSubject(String subject);

    /****** CREDENTIAL MAP CONVENIENCE METHODS *******/

    public User getCredentialUser(String subject);

    public List getCredentialSubjects(User user);

    public void addCredentialSubject(User user, String subject)
            throws CredentialNotPermittedException;

    public void removeCredentialSubject(User user, String subject);

    public List getCredentialTags(User user);

    public String getCredentialTag(String subject)
            throws CredentialMapNotFoundException;

    public void setCredentialTag(String subject, String tag)
            throws CredentialMapNotFoundException;

    public List getCredentialHosts(String subject)
            throws CredentialMapNotFoundException;

    public void addCredentialHost(String subject, String host)
            throws CredentialMapNotFoundException;

    public void removeCredentialHost(String subject, String host)
            throws CredentialMapNotFoundException;

    public List getCredentialSubjectsForHost(String host);

    public List getCredentialSubjectsForHost(User user, String host);

    /****** CREDENTIAL RETRIEVAL METHODS *******/

    public String getCredentialRetrievalProtocol();

    public String getCredentialRetrievalHost();

    public int getCredentialRetrievalPort();

    public long getCredentialRetrievalLifetime();

    public void retrieveCredentials(User user, String passphrase)
            throws CredentialRetrievalException;

    /****** CREDENTIAL STORAGE METHODS *******/

    public void storeCredential(Credential credential)
            throws CredentialNotPermittedException;

    public void storeCredentials(List credentials)
            throws CredentialNotPermittedException;

    public void destroyCredential(String subject);

    public void destroyCredentials(User user);

    public List getActiveCredentials(User user);

    public Credential getActiveCredential(String subject);

    public List getActiveCredentialsForHost(User user, String host);

    public boolean isActiveCredential(String subject);

    public boolean hasActiveCredentials(User user);

    public List getActiveCredentialSubjects();

    public List getActiveCredentialSubjects(User user);
}
