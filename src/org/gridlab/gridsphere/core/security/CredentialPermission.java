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
package org.gridlab.gridsphere.core.security;

import org.gridlab.gridsphere.core.persistence.BaseObject;
import org.gridlab.gridsphere.core.persistence.castor.PersistenceManagerRdbms;

import java.util.Date;

public final class CredentialPermission extends BaseObject {

    private String subjectPattern= null;
    private String description = null;

    /**
     * The default constructor is private so that it can never be called.
     */
    private CredentialPermission() {
    }

    /**
     * Constructs a <code>CredentialPermission</code> with the given subject pattern.
     */
    public CredentialPermission(String subjectPattern) {
        this.subjectPattern = subjectPattern;
        this.description = "";
    }

    /** 
     * Returns the subject pattern described by this credential permission.
     *
     * @return <code>String</code> The subject patternt.
     */
    public String getSubjectPattern() {
        return this.subjectPattern;
    }

    /** 
     * Returns the description of this credential permission.
     *
     * @return <code>String</code> The description.
     */
    public String getDescription() {
        return this.description;
    }

    /** 
     * Sets the description of this credential permission.
     *
     * @param <code>String</code> The description.
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /** 
     * Returns true if the given credential subject matches the subject pattern 
     * described by this credential permission, false otherwise.
     * <p>
     * <strong>NOTE: NOT YET IMPLEMENTED</strong>
     * <p>
     * @param <code>String</code> The credential subject in question.
     */
    public boolean isSubjectPermitted(String subject) {
        return false;
    }
}
