/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Feb 18, 2003
 * Time: 11:41:18 AM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.services.security.credential;

import org.gridlab.gridsphere.portlet.User;

import java.util.List;

public interface CredentialMappingRequest extends CredentialMapping {

    /**
     */
    public void setSubject(String subject);

    /**
     */
    public void setUser(User user);

    /**
     */
    public void setLabel(String label);

    /**
     */
    public void setTag(String tag);

    /**
     *
     */
    public CredentialMappingAction getCredentialMappingAction();

    /**
     *
     */
    public void setCredentialMappingAction(CredentialMappingAction action);
}
