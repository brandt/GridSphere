/*
 * @author <a href="mailto:russell@aei-potsdam.mpg.de">Michael Paul Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.grid.security.credential.impl;

import org.gridlab.gridsphere.core.persistence.BaseObject;
import org.gridlab.gridsphere.core.persistence.castor.PersistenceManagerRdbmsImpl;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import java.util.List;
import java.util.Vector;

/**
 * @table credentialhostmapping
 * @depends GlobusCredentialMapping
 */
public class GlobusHostMapping extends BaseObject {

    private transient static PortletLog _log = SportletLog.getInstance(GlobusHostMapping.class);

    /**
     * @sql-name credentialmapping
     * @required
     */
    private GlobusCredentialMapping credentialMapping = null;

    /**
     * @sql-size 256
     * @sql-name hostaddress
     * @required
     */
    private String hostAddress= null;

    public GlobusHostMapping() {
    }

    /**
     */
    public GlobusCredentialMapping getCredentialMapping() {
        return this.credentialMapping;
    }

    /**
     */
    public void setCredentialMapping(GlobusCredentialMapping mapping) {
        this.credentialMapping = mapping;
    }

    /**
     */
    public String getHostAddress() {
        return this.hostAddress;
    }

    /**
     */
    public void setHostAddress(String address) {
        this.hostAddress = address;
    }
}
