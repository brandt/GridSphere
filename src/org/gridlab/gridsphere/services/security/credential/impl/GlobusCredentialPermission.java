/*
 * @author <a href="mailto:russell@aei-potsdam.mpg.de">Michael Paul Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.security.credential.impl;

import org.gridlab.gridsphere.core.persistence.BaseObject;
import org.gridlab.gridsphere.core.persistence.castor.PersistenceManagerRdbms;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.services.security.credential.CredentialPermission;

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
        /*** GHETTO STYLE
        boolean answer = false;
        subject = subject.trim();
        String pattern = this.pattern.trim();
        // Search for wild character
        int index = pattern.indexOf("*");
        // If pattern matches "...*" check if subject starts with pattern
        if (index > 0) {
            pattern = pattern.substring(0, index);
            answer = (subject.indexOf(pattern) == 0);
        // If pattern matches "*..." then..
        } else if (index == 0) {
             int length = pattern.length();
            // If pattern matches "*" then return true
             if (length == 1) {
                answer = true;
             // Otherwise compare rest of string to subject
             } else {
                 pattern = pattern.substring(index+1);
                 answer = (subject.endsWith(pattern));
             }
        // If pattern does not contain "*" then compare entire strings
        } else {
                answer = (this.pattern.equals(subject));
        }
        return answer;
        ***/
        RE ex = null;
        try {
            ex = new RE(this.permittedSubjects);
        } catch (RESyntaxException e) {
            return false;
        }
        return ex.match(subject);
    }
}
