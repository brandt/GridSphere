/*
 * @author <a href="mailto:russell@aei-potsdam.mpg.de">Michael Paul Russell</a>
 * @version $Id$
 * <p>
 * This class describes the relationship in GridSphere between a credential subject, a
 * portlet user, and the hostnames for which the credential subject is known to
 * map the given portlet user to a local account. Note that in GridSphere a credential subject
 * can map to only one portlet user. See <code>CredentialManager</code> for more details on
 * the usage of this class.
 */
package org.gridlab.gridsphere.core.security.impl;

import org.gridlab.gridsphere.core.persistence.BaseObject;
import org.gridlab.gridsphere.core.persistence.castor.PersistenceManagerRdbms;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.core.security.CredentialMapping;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.Iterator;

/**
 * @table credentialmapping
 */
public class GlobusCredentialMapping extends BaseObject implements CredentialMapping {

    private transient static PersistenceManagerRdbms _pm = PersistenceManagerRdbms.getInstance();
    private transient static PortletLog _log = SportletLog.getInstance(CredentialMapping.class);
    private transient static String _jdoCredentialMappingImpl = GlobusCredentialMapping.class.getName();
    private transient static String _jdoHostMappingImpl = GlobusHostMapping.class.getName();

    /**
     * @sql-name user
     */
    private User user = null;
    /**
     * @sql-size 256
     * @sql-name subject
     */
    private String subject= null;
    /**
     * @sql-size 15
     * @sql-name tag
     */
    private String tag = null;
    /**
     * @sql-size 256
     * @sql-name description
     */
    private String description = null;

    /**
     * @field-type GlobusHostMapping
     * @many-key parent
     */
    private Vector hostMappings = new Vector();

    /**
     */
    public GlobusCredentialMapping() {
    }
    
    /**
     */
    public GlobusCredentialMapping(User user, String subject) {
        this.user = user;
        this.subject = subject;
        this.tag = user.getUserID();
        this.description = "";
    }

    /**
     */
    public User getUser() {
        return this.user;
    }

    /**
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     */
    public String getSubject() {
        return this.subject;
    }

    /**
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     */
    public String getTag() {
        String tag = null;
        if (this.tag == null) {
            return this.user.getUserID();
        } else {
            tag = this.tag;
        }
        return tag;
    }

    /**
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     */
    public String getDescription() {
        return this.description;
    }

    /**
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     */
    public List getHosts() {
        List hosts = new Vector();
        synchronized (this.hostMappings) {
            int length = this.hostMappings.size();
            for (int ii = 0; ii < length; ++ii) {
                GlobusHostMapping hostMapping = (GlobusHostMapping)this.hostMappings.get(ii);
                String hostAddress = hostMapping.getHostAddress();
                hosts.add(hostAddress);
            }
        }
        return hosts;
    }

    /**
     */
    public void addHost(String host) {
        GlobusHostMapping hostMapping = null;
        synchronized (this.hostMappings) {
            int length = this.hostMappings.size();
            for (int ii = 0; ii < length; ++ii) {
                hostMapping = (GlobusHostMapping)this.hostMappings.get(ii);
                if (hostMapping.getHostAddress().equals(host)) {
                    return;
                }
            }
            hostMapping = new GlobusHostMapping();
            hostMapping.setParent(this);
            hostMapping.setHostAddress(host);
            this.hostMappings.add(hostMapping);
        }
    }

    /** 
     */
    public void addHost(List hosts) {
        for (int ii = 0; ii < hosts.size(); ++ii) {
            String host = (String)hosts.get(ii);
            addHost(host);
        }
    }

    /** 
     */
    public void removeHost(String host) {
        GlobusHostMapping hostMapping = null;
        synchronized (this.hostMappings) {
            int length = this.hostMappings.size();
            for (int ii = 0; ii < length; ++ii) {
                hostMapping = (GlobusHostMapping)this.hostMappings.get(ii);
                if (hostMapping.getHostAddress().equals(host)) {
                    this.hostMappings.remove(ii);
                }
            }
        }
    }
    
    /** 
     */
    public boolean hasHost(String host) {
        GlobusHostMapping hostMapping = null;
        synchronized (this.hostMappings) {
            int length = this.hostMappings.size();
            for (int ii = 0; ii < length; ++ii) {
                hostMapping = (GlobusHostMapping)this.hostMappings.get(ii);
                if (hostMapping.getHostAddress().equals(host)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Vector getHostMappings() {
        return this.hostMappings;
    }

    public void setHostMappings(Vector hostMappings) {
        this.hostMappings = hostMappings;
    }

    /********
    public static List select(String query) {
        List mappings = null;
        try {
            mappings = _pm.restoreList(query);
        } catch (PersistenceManagerException e) {
            _log.error("Failed to retrieve credential mappings with " + query, e);
            mappings = new Vector();
        }
        return mappings;
    }

    public static List getAll() {
        List maps = null;
        String query = "select cm from "
                     + _jdoCredentialMappingImpl;
        _log.debug(query);
        try {
            maps = _pm.restoreList(query);
        } catch (PersistenceManagerException e) {
            _log.error("Failed to load all credential mappings", e);
            maps = new Vector();
        }
        return maps;
    }

    public static CredentialMapping getByOid(String oid) {
        CredentialMapping mapping = null;
        try {
            mapping = (CredentialMapping)_pm.getObjectByOid(CredentialMapping.class, oid);
        } catch (PersistenceManagerException e) {
            _log.error("Failed to load credential mapping " + oid, e);
        }
        return mapping;
    }

    public static CredentialMapping getBySubject(String subject) {
        CredentialMapping mapping = null;
        String query = "select cm from "
                     + _jdoCredentialMappingImpl
                     + " cm where cm.user=" + subject;
        _log.debug(query);
        try {
            mapping = (CredentialMapping)_pm.restoreObject(query);
        } catch (PersistenceManagerException e) {
            _log.error("Failed to load credential mapping for " + subject, e);
        }
        return mapping;
    }

    public static List getByUser(User user) {
        List maps = null;
        String query = "select cm from "
                     + _jdoCredentialMappingImpl
                     + " cm where cm.user=" + user;
        _log.debug(query);
        try {
            maps = _pm.restoreList(query);
        } catch (PersistenceManagerException e) {
            _log.error("Failed to load credential mappings for " + user, e);
            maps = new Vector();
        }
        return maps;
    }

    public void save() {
        String oid = getOid();
        if (oid == null) {
            try {
                _pm.create(this);
            } catch (PersistenceManagerException e) {
                _log.error("Failed to create credential mapping for " + this.subject, e);
            }
        } else {
            try {
                _pm.update(this);
            } catch (PersistenceManagerException e) {
                _log.error("Failed to update credential mapping for " + this.subject, e);
            }
        }
    }

    public void delete() {
        String oid = getOid();
        if (oid != null) {
            try {
                _pm.delete(this);
                setOid(null);
            } catch (PersistenceManagerException e) {
                _log.error("Failed to delete credential mapping for " + this.subject, e);
            }
        }
    }

    public static void deleteByUser(User user) {
        String query = "delete cm from "
                     + _jdoCredentialMappingImpl
                     + " cm where cm.user=" + user;
        _log.debug(query);
        try {
            _pm.deleteList(query);
        } catch (PersistenceManagerException e) {
            _log.error("Failed to delete credential mappings for " + user, e);
        }
    }
    ********/
}
