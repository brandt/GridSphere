/*
 * @author <a href="mailto:russell@aei-potsdam.mpg.de">Michael Paul Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.grid.security.credential.impl;

import org.gridlab.gridsphere.core.persistence.BaseObject;
import org.gridlab.gridsphere.core.persistence.castor.PersistenceManagerRdbms;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.services.grid.security.credential.CredentialPermission;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.regexp.RE;
import org.apache.regexp.RESyntaxException;

/**
 * @table credentialpermission
 */
public class GlobusCredentialPermission extends BaseObject implements CredentialPermission {

    private transient static PersistenceManagerRdbms _pm = PersistenceManagerRdbms.getInstance();
    private transient static PortletLog _log = SportletLog.getInstance(GlobusCredentialPermission.class);
    private transient static String _jdoCredentialPermissionImpl = GlobusCredentialPermission.class.getName();

    /**
     * @sql-size 256
     * @sql-name permittedsubjects
     * @required
     */
    private String permittedSubjects = null;

    /**
     * @sql-size 256
     * @sql-name description
     */
    private String description = null;

    /**
     */
    public GlobusCredentialPermission() {
    }

    /**
     */
    public String getPermittedSubjects() {
        return this.permittedSubjects;
    }

    /**
     */
    public void setPermittedSubjects(String pattern) {
        this.permittedSubjects = pattern;
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
    public boolean isCredentialPermitted(String subject) {
        RE ex = null;
        try {
            ex = new RE(this.permittedSubjects);
        } catch (RESyntaxException e) {
            return false;
        }
        return ex.match(subject);
    }
}
