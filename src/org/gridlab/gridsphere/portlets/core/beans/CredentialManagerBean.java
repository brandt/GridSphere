/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Feb 14, 2003
 * Time: 6:17:36 AM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.portlets.core.beans;

import org.gridlab.gridsphere.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletConfig;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.services.security.credential.CredentialManagerService;

public class CredentialManagerBean extends PortletBean {

    // JSP pages used by this portlet
    public static final String PAGE_CREDENTIAL_MAPPING_LIST = "/jsp/credential/credentialMappingList.jsp";
    public static final String PAGE_CREDENTIAL_MAPPING_VIEW = "/jsp/credential/credentialMappingView.jsp";
    public static final String PAGE_CREDENTIAL_MAPPING_EDIT = "/jsp/credential/credentialMappingEdit.jsp";
    public static final String PAGE_CREDENTIAL_MAPPING_DELETE = "/jsp/credential/credentialMappingDelete.jsp";
    public static final String PAGE_CREDENTIAL_MAPPING_DELETE_CONFIRM = "/jsp/credential/credentialMappingDeleteConfirm.jsp";

    public static final String PAGE_CREDENTIAL_PERMISSION_LIST = "/jsp/credential/credentialPermissionList.jsp";
    public static final String PAGE_CREDENTIAL_PERMISSION_VIEW = "/jsp/credential/credentialPermissionView.jsp";
    public static final String PAGE_CREDENTIAL_PERMISSION_EDIT = "/jsp/credential/credentialPermissionEdit.jsp";
    public static final String PAGE_CREDENTIAL_PERMISSION_DELETE = "/jsp/credential/credentialPermissionDelete.jsp";
    public static final String PAGE_CREDENTIAL_PERMISSION_DELETE_CONFIRM = "/jsp/credential/credentialPermissionDeleteConfirm.jsp";

    public static final String PAGE_CREDENTIAL_LIST = "/jsp/credential/credentialList.jsp";
    public static final String PAGE_CREDENTIAL_VIEW = "/jsp/credential/credentialView.jsp";

    // Portlet services
    private CredentialManagerService credentialManagerService = null;

    public CredentialManagerBean() {
        super();
        initView();
    }

    public CredentialManagerBean(PortletConfig config, PortletRequest request, PortletResponse response)
            throws PortletException {
        super(config, request, response);
        initServices();
        initView();
    }

    private void initServices()
            throws PortletException {
        this.log.debug("Entering initServices()");
        this.credentialManagerService = (CredentialManagerService)getPortletService(CredentialManagerService.class);
        this.log.debug("Exiting initServices()");
    }

    private void initView() {
        setNextPage(PAGE_CREDENTIAL_MAPPING_LIST);
        setNextTitle("Access Control Manager");
    }

    public void doListCredentialMappings()
            throws PortletException {
    }
}
