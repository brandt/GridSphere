/*
 * @author <a href="mailto:russell@aei-potsdam.mpg.de">Michael Paul Russell</a>
 * @version $Id$
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

    private transient static PortletLog _log = SportletLog.getInstance(CredentialMapping.class);

    /**
     * @sql-size 256
     * @sql-name subject
     * @required
     */
    private String subject= null;

    /**
     * @sql-size 15
     * @sql-name user
     * @required
     */
    private String user = null;

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
    public String getUser() {
        return this.user;
    }

    /**
     */
    public void setUser(String userId) {
        this.user = user;
    }

    /**
     */
    public String getTag() {
        String tag = null;
        if (this.tag == null) {
            return this.user;
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
}
