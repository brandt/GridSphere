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

import java.util.List;

public class ActiveCredentialBean extends CredentialMappingBean {

    // Credential mapping actions
    public static final String ACTION_ACTIVE_CREDENTIAL_LIST = "doListActiveCredential";
    public static final String ACTION_ACTIVE_CREDENTIAL_VIEW = "doViewActiveCredential";

    // Credential mapping pages
    public static final String PAGE_ACTIVE_CREDENTIAL_LIST = "/jsp/credential/activeCredentialList.jsp";
    public static final String PAGE_ACTIVE_CREDENTIAL_VIEW = "/jsp/credential/activeCredentialView.jsp";

    // Credential mapping variables
    private List activeCredentialList = null;
    private Credential activeCredential = null;

    /******************************************
     * Portlet bean methods
     ******************************************/

    public ActiveCredentialBean() {
        super();
        initView();
    }

    public ActiveCredentialBean(PortletConfig config, PortletRequest request, PortletResponse response)
            throws PortletException {
        super(config, request, response);
        initView();
    }

    private void initView() {
        setTitle("Active Credential Manager");
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
