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
import org.gridlab.gridsphere.portlet.User;

import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 * @table credentialhostmapping
 */
public class GlobusHostMapping extends BaseObject {

    /**
     * @sql-name parent
     * @get-method getParent
     * @set-method setParent
     */
    private GlobusCredentialMapping parent = null;

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
    public GlobusCredentialMapping getParent() {
        return this.parent;
    }

    /**
     */
    public void setParent(GlobusCredentialMapping mapping) {
        this.parent = mapping;
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
