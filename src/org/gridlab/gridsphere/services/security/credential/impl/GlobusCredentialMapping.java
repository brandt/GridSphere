/*
 * @author <a href="mailto:russell@aei-potsdam.mpg.de">Michael Paul Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.security.credential.impl;

import org.gridlab.gridsphere.core.persistence.BaseObject;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletUserImpl;
import org.gridlab.gridsphere.services.security.credential.CredentialMapping;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * @table credentialmapping
 */
public class GlobusCredentialMapping extends BaseObject implements CredentialMapping {

    private transient static PortletLog _log = SportletLog.getInstance(GlobusCredentialMapping.class);
    private transient Integer lock = new Integer(0);

    /**
     * @sql-size 256
     * @sql-name subject
     * @required
     */
    protected String subject = null;
    /**
     * @sql-name user
     * @get-method getSportletUser
     * @set-method setSportletUser
     * @required
     */
    protected SportletUserImpl user = null;
    /**
     * @sql-size 15
     * @sql-name tag
     */
    protected String tag = null;
    /**
     * @sql-size 32
     * @sql-name label
     */
    protected String label = null;
    /**
     * @field-type GlobusHostMapping
     * @many-key credentialmapping
     */
    protected Vector hostMappings = new Vector();

    /**
     */
    public GlobusCredentialMapping() {
    }

    /**
     *
     */
    public String getID() {
        return getOid();
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
    public User getUser() {
        return this.user;
    }

    /**
     */
    public void setUser(User user) {
        this.user = (SportletUserImpl)user;
    }

    /**
     */
    public SportletUserImpl getSportletUser() {
        return this.user;
    }

    /**
     */
    public void setSportletUser(SportletUserImpl user) {
        this.user = user;
    }

    /**
     */
    public String getTag() {
        return tag;
    }

    /**
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     */
    public String getLabel() {
        return this.label;
    }

    /**
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     */
    public Vector getHostMappings() {
        return this.hostMappings;
    }

    /**
     */
    public void setHostMappings(Vector hostMappings) {
        this.hostMappings = hostMappings;
    }

    /**
     */
    public List getHosts() {
        List hosts = new Vector();
        Iterator hostMappings = this.hostMappings.iterator();
        while (hostMappings.hasNext()) {
            GlobusHostMapping hostMapping = (GlobusHostMapping)hostMappings.next();
            String hostAddress = hostMapping.getHostAddress();
            hosts.add(hostAddress);
        }
        return hosts;
    }

    /**
     */
    public void addHost(String host) {
        synchronized (this.lock) {
            GlobusHostMapping hostMapping = null;
            Iterator hostMappings = this.hostMappings.iterator();
            while (hostMappings.hasNext()) {
                hostMapping = (GlobusHostMapping)hostMappings.next();
                String hostAddress = hostMapping.getHostAddress();
                if (hostAddress.equals(host)) {
                    return;
                }
            }
            hostMapping = new GlobusHostMapping();
            hostMapping.setCredentialMapping(this);
            hostMapping.setHostAddress(host);
            this.hostMappings.add(hostMapping);
        }
    }

    /**
     */
    public void addHosts(List hosts) {
        for (int ii = 0; ii < hosts.size(); ++ii) {
            String host = (String)hosts.get(ii);
            addHost(host);
        }
    }

    /**
     */
    public void removeHost(String host) {
        synchronized (this.lock) {
            GlobusHostMapping hostMapping = null;
            Iterator hostMappings = this.hostMappings.iterator();
            while (hostMappings.hasNext()) {
                hostMapping = (GlobusHostMapping)hostMappings.next();
                String hostAddress = hostMapping.getHostAddress();
                if (hostAddress.equals(host)) {
                    break;
                }
            }
            this.hostMappings.remove(hostMapping);
        }
    }

    /**
     */
    public boolean hasHost(String host) {
        Iterator hostMappings = this.hostMappings.iterator();
        while (hostMappings.hasNext()) {
            GlobusHostMapping hostMapping = (GlobusHostMapping)hostMappings.next();
            String hostAddress = hostMapping.getHostAddress();
            if (hostAddress.equals(host)) {
                return true;
            }
        }
        return false;
    }
}
