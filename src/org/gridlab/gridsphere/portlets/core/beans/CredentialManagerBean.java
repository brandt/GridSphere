/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Feb 14, 2003
 * Time: 6:17:36 AM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.portlets.core.beans;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.services.security.credential.*;
import org.gridlab.gridsphere.services.user.UserManagerService;

import java.util.List;

public class CredentialManagerBean extends PortletBean {

    // Credential manager services
    protected CredentialManagerService credentialManagerService = null;
    protected UserManagerService userManagerService = null;

    protected List userList = null;

    /******************************************
     * Portlet bean methods
     ******************************************/

    public CredentialManagerBean() {
        super();
    }

    public CredentialManagerBean(PortletConfig config, PortletRequest request, PortletResponse response)
            throws PortletException {
        super(config, request, response);
        initServices();
    }

    private void initServices()
            throws PortletException {
        this.log.debug("Entering initServices()");
        this.credentialManagerService = (CredentialManagerService)getPortletService(CredentialManagerService.class);
        this.userManagerService = (UserManagerService)getPortletService(UserManagerService.class);
        this.log.debug("Exiting initServices()");
    }

    protected void loadUserList()
            throws PortletException {
        this.userList = this.userManagerService.getUsers();
    }

    public List getUserList() {
        return this.userList;
    }
}
