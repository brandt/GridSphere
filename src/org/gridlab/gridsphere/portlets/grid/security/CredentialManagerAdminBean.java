/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets.grid.security;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.services.grid.security.credential.*;
import org.gridlab.gridsphere.portlets.PortletBean;

import java.util.List;

public class CredentialManagerAdminBean extends PortletBean {

    // Credential mapping actions
    public static final String ACTION_ACTIVE_CREDENTIAL_LIST = "doListActiveCredential";
    public static final String ACTION_ACTIVE_CREDENTIAL_VIEW = "doViewActiveCredential";

    // Credential mapping pages
    public static final String PAGE_ACTIVE_CREDENTIAL_LIST = "/jsp/security/activeCredentialList.jsp";
    public static final String PAGE_ACTIVE_CREDENTIAL_VIEW = "/jsp/security/activeCredentialView.jsp";

    // Credential mapping variables
    private List activeCredentialList = null;
    private Credential activeCredential = null;

    // Credential manager service
    private CredentialManagerService credentialManagerService = null;

    /******************************************
     * Portlet bean methods
     ******************************************/

    public CredentialManagerAdminBean() {
        initView();
    }

    public CredentialManagerAdminBean(PortletConfig config, PortletRequest request, PortletResponse response)
            throws PortletException {
        init(config, request, response);
        initServices();
        initView();
    }

    protected void initView() {
        setTitle("Active Credential Manager");
    }

    protected void initServices()
            throws PortletException {
        this.log.debug("Entering initServices()");
        this.credentialManagerService = (CredentialManagerService)getPortletService(CredentialManagerService.class);
        this.log.debug("Exiting initServices()");
    }

    public void doDefaultViewAction()
            throws PortletException {
        doListActiveCredential();
    }

    /******************************************
     * Credential mapping actions
     ******************************************/

    public void doListActiveCredential()
            throws PortletException {
        this.log.debug("Entering doListActiveCredential");
        loadActiveCredentialList();
        setPage(PAGE_ACTIVE_CREDENTIAL_LIST);
        this.log.debug("Exiting doListActiveCredential");
    }

    public void doViewActiveCredential()
            throws PortletException {
        this.log.debug("Entering doViewActiveCredential");
        loadActiveCredential();
        setPage(PAGE_ACTIVE_CREDENTIAL_VIEW);
        this.log.debug("Exiting doViewActiveCredential");
    }

    /******************************************
     * Credential mapping methods
     ******************************************/

    public List getActiveCredentialList() {
        return this.activeCredentialList;
    }

    public Credential getActiveCredential() {
        return this.activeCredential;
    }

    public void loadActiveCredentialList()
            throws PortletException {
        this.activeCredentialList = this.credentialManagerService.getActiveCredentials();
    }

    public void loadActiveCredential()
            throws PortletException {
        String credentialID = getParameter("credentialID");
        this.activeCredential = this.credentialManagerService.getActiveCredential(credentialID);
    }
}
