/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Feb 18, 2003
 * Time: 3:23:21 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.services.grid.security.credential.impl;

import org.gridlab.gridsphere.services.grid.security.credential.CredentialMappingAction;
import org.gridlab.gridsphere.services.grid.security.credential.CredentialMappingRequest;
import org.gridlab.gridsphere.services.grid.security.credential.CredentialMapping;

/**
 * @table credentialmappingrequest
 */
public class GlobusCredentialMappingRequest
        extends GlobusCredentialMapping
        implements CredentialMappingRequest {

    /**
     * @sql-size 128
     * @sql-name action
     * @required
     */
    private String action = CredentialMappingAction.ADD.toString();

    public GlobusCredentialMappingRequest() {
        this.user = null;
        this.subject = new String();
        this.label = new String();
        this.tag = new String();
    }

    /**
     *
     */
    public CredentialMappingAction getCredentialMappingAction() {
        return CredentialMappingAction.toCredentialMappingAction(this.action);
    }

    /**
     *
     */
    public void setCredentialMappingAction(CredentialMappingAction mappingAction) {
        this.action = mappingAction.toString();
    }

    /**
     *
     */
    public String getAction() {
        return this.action;

    }

    /**
     *
     */
    public void setAction(String action) {
        this.action = action;
    }
}
