/*
 * @author <a href="mailto:russell@aei-potsdam.mpg.de">Michael Paul Russell</a>
 * @version $Id$
 * <p>
 * This class is used to describe a permission to use credentials with subjects
 * that match a given pattern. For example, given a permission to use credentials
 * with subjects matching the pattern<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;"/O=Grid/O=Globus/OU=gridsphere.org*"<br>
 * then a credential containing the subject<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;"/O=Grid/O=Globus/OU=gridsphere.org/CN=John Doe"<br>
 * would be permitted for use with GridSphere.
 */
package org.gridlab.gridsphere.core.security.impl;

import org.gridlab.gridsphere.core.persistence.BaseObject;
import org.gridlab.gridsphere.core.persistence.castor.PersistenceManagerRdbms;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.core.security.CredentialPermission;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * @table credentialpermission
 */
public class GlobusCredentialPermission extends BaseObject implements CredentialPermission {

    private transient static PersistenceManagerRdbms _pm = PersistenceManagerRdbms.getInstance();
    private transient static PortletLog _log = SportletLog.getInstance(GlobusCredentialPermission.class);
    private transient static String _jdoCredentialPermissionImpl = GlobusCredentialPermission.class.getName();

    /**
     * @sql-size 256
     * @sql-name pattern
     */
    private String pattern= null;
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
        return this.pattern;
    }

    /**
     */
    public void setPermittedSubjects(String pattern) {
        this.pattern = pattern;
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
     * <strong>NOT PROPERLY IMPLEMENTED! (MPR: 25/12/02)</strong>
     */
    public boolean isCredentialPermitted(String subject) {
        boolean answer = false;
        try {
            String query = "select cp.pattern from "
                         + _jdoCredentialPermissionImpl
                         + " cp where cp.oid=" + getOid()
                         +      " and " + subject + " like cp.pattern";
            _log.debug(query);
            String pattern = (String)_pm.restoreObject(query);
            answer = true;
        } catch (PersistenceManagerException e) {
            _log.error("This credential permission does not permit " + subject, e);
        }
        return answer;
    }

    /********
    public static List select(String query) {
        List permissions = null;
        try {
            permissions = _pm.restoreList(query);
        } catch (PersistenceManagerException e) {
            _log.error("Failed to select credential permissions with " + query, e);
            permissions = new Vector();
        }
        return permissions;
    }

    public static List getAll() {
        List permissions = null;
        try {
            String query = "select cp from "
                         + _jdoCredentialPermissionImpl
                         + " cp";
            _log.debug(query);
            permissions = _pm.restoreList(query);
        } catch (PersistenceManagerException e) {
            _log.error("Failed to load all credential permissions", e);
            permissions = new Vector();
        }
        return permissions;
    }

    public static CredentialPermission getByOid(String oid) {
        CredentialPermission permission = null;
        try {
            permission = (CredentialPermission)_pm.getObjectByOid(CredentialPermission.class, oid);
        } catch (PersistenceManagerException e) {
            _log.error("Failed to load credential permission " + oid, e);
        }
        return permission;
    }

    public static CredentialPermission getBySubjectPattern(String pattern) {
        CredentialPermission mapping = null;
        String query = "select cp from "
                     + _jdoCredentialPermissionImpl
                     + " cm where cp.pattern=" + pattern;
        _log.debug(query);
        try {
            mapping = (CredentialPermission)_pm.restoreObject(query);
        } catch (PersistenceManagerException e) {
            _log.error("Failed to load credential permission for " + pattern, e);
        }
        return mapping;
    }

    public void save() {
        String oid = getOid();
        if (oid == null) {
            try {
                _pm.create(this);
            } catch (PersistenceManagerException e) {
                _log.error("Failed to create credential permission for " + this.pattern, e);
            }
        } else {
            try {
                _pm.update(this);
            } catch (PersistenceManagerException e) {
                _log.error("Failed to update credential permission for " + this.pattern, e);
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
                _log.error("Failed to delete credential permission for " + this.pattern, e);
            }
        }
    }
    ********/
}
