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

public class CredentialManagerBean extends PortletBean {

    public static final String ACTION_CREDENTIAL_PERMISSION_LIST = "credentialPermissionList";
    public static final String ACTION_CREDENTIAL_PERMISSION_VIEW = "credentialPermissionView";
    public static final String ACTION_CREDENTIAL_PERMISSION_EDIT = "credentialPermissionEdit";
    public static final String ACTION_CREDENTIAL_PERMISSION_DELETE = "credentialPermissionDelete";
    public static final String ACTION_CREDENTIAL_PERMISSION_DELETE_CONFIRM = "credentialPermissionDeleteConfirm";

    public static final String ACTION_CREDENTIAL_MAPPING_LIST = "credentialMappingList";
    public static final String ACTION_CREDENTIAL_MAPPING_VIEW = "credentialMappingView";
    public static final String ACTION_CREDENTIAL_MAPPING_EDIT = "credentialMappingEdit";
    public static final String ACTION_CREDENTIAL_MAPPING_DELETE = "credentialMappingDelete";
    public static final String ACTION_CREDENTIAL_MAPPING_DELETE_CONFIRM = "credentialMappingDeleteConfirm";

    public static final String ACTION_ACTIVE_CREDENTIAL_LIST = "activeCredentialList";
    public static final String ACTION_ACTIVE_CREDENTIAL_VIEW = "activeCredentialView";

    // Credential permission pages
    public static final String PAGE_CREDENTIAL_PERMISSION_LIST = "/jsp/credential/credentialPermissionList.jsp";
    public static final String PAGE_CREDENTIAL_PERMISSION_VIEW = "/jsp/credential/credentialPermissionView.jsp";
    public static final String PAGE_CREDENTIAL_PERMISSION_EDIT = "/jsp/credential/credentialPermissionEdit.jsp";
    public static final String PAGE_CREDENTIAL_PERMISSION_DELETE = "/jsp/credential/credentialPermissionDelete.jsp";
    public static final String PAGE_CREDENTIAL_PERMISSION_DELETE_CONFIRM = "/jsp/credential/credentialPermissionDeleteConfirm.jsp";

    // Credential mapping pages
    public static final String PAGE_CREDENTIAL_MAPPING_LIST = "/jsp/credential/credentialMappingList.jsp";
    public static final String PAGE_CREDENTIAL_MAPPING_VIEW = "/jsp/credential/credentialMappingView.jsp";
    public static final String PAGE_CREDENTIAL_MAPPING_EDIT = "/jsp/credential/credentialMappingEdit.jsp";
    public static final String PAGE_CREDENTIAL_MAPPING_DELETE = "/jsp/credential/credentialMappingDelete.jsp";
    public static final String PAGE_CREDENTIAL_MAPPING_DELETE_CONFIRM = "/jsp/credential/credentialMappingDeleteConfirm.jsp";

    // Actived credential pages
    public static final String PAGE_ACTIVE_CREDENTIAL_LIST = "/jsp/credential/activeCredentialList.jsp";
    public static final String PAGE_ACTIVE_CREDENTIAL_VIEW = "/jsp/credential/activeCredentialView.jsp";

    // Credential manager services
    private CredentialManagerService credentialManagerService = null;

    // Credential permission variables
    private List credentialPermissionList = null;
    private CredentialPermission credentialPermission = null;

    // Credential mapping variables
    private List credentialMappingList = null;
    private CredentialMapping credentialMapping = null;

    // Active credential variables
    private List activeCredentialList = null;
    private Credential activeCredential = null;

    /******************************************
     * Portlet bean methods
     ******************************************/

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

    public void doAction(PortletAction action)
            throws PortletException {
        super.doAction(action);
        // Get name of action performed
        String actionName = getActionPerformedName();
        // Perform appropriate action
        if (actionName.equals(ACTION_CREDENTIAL_MAPPING_LIST)) {
            doListCredentialMapping();
        } else {
            doListCredentialMapping();
        }
    }

    public void doDefaultViewAction()
            throws PortletException {
        doListCredentialMapping();
    }

    /******************************************
     * Credential permission methods
     ******************************************/

    public void doListCredentialPermission()
            throws PortletException {
        loadCredentialPermissionList();
        setNextPage(PAGE_CREDENTIAL_PERMISSION_LIST);
    }

    public void doViewCredentialPermission()
            throws PortletException {
        loadCredentialPermission();
        setNextPage(PAGE_CREDENTIAL_PERMISSION_VIEW);
    }

    public void doEditCredentialPermission()
            throws PortletException {
        loadCredentialPermission();
        setNextPage(PAGE_CREDENTIAL_PERMISSION_EDIT);
    }

    public void doConfirmEditCredentialPermission()
            throws PortletException {
        loadCredentialPermission();
        try {
            editCredentialPermission();
            saveCredentialPermission();
            setNextPage(PAGE_CREDENTIAL_PERMISSION_VIEW);
        } catch (PortletException e) {
            setIsFormInvalid(true);
            setFormInvalidMessage(e.getMessage());
            setNextPage(PAGE_CREDENTIAL_PERMISSION_EDIT);
        }
    }

    public void doDeleteCredentialPermission()
            throws PortletException {
        loadCredentialPermission();
        setNextPage(PAGE_CREDENTIAL_PERMISSION_DELETE);
    }

    public void doConfirmDeleteCredentialPermission()
            throws PortletException {
        loadCredentialPermission();
        deleteCredentialPermission();
        setNextPage(PAGE_CREDENTIAL_PERMISSION_DELETE_CONFIRM);
    }

    public List getCredentialPermissionList() {
        return this.credentialPermissionList;
    }

    public CredentialPermission getCredentialPermission() {
        return this.credentialPermission;
    }

    public void loadCredentialPermissionList()
            throws PortletException {
        this.credentialPermissionList = this.credentialManagerService.getCredentialPermissions();
    }

    public void loadCredentialPermission()
            throws PortletException {
        String credentialPermissionID = getParameter("credentialPermissionID");
        this.credentialPermission = this.credentialManagerService.getCredentialPermission(credentialPermissionID);
    }

    public void editCredentialPermission()
            throws PortletException {
    }

    public void saveCredentialPermission()
            throws PortletException {
    }

    public void deleteCredentialPermission()
            throws PortletException {
    }

    /******************************************
     * Credential mapping methods
     ******************************************/

    public void doListCredentialMapping()
            throws PortletException {
        loadCredentialMappingList();
        setNextPage(PAGE_CREDENTIAL_MAPPING_LIST);
    }

    public void doViewCredentialMapping()
            throws PortletException {
        loadCredentialMapping();
        setNextPage(PAGE_CREDENTIAL_MAPPING_VIEW);
    }

    public void doEditCredentialMapping()
            throws PortletException {
        loadCredentialMapping();
        setNextPage(PAGE_CREDENTIAL_MAPPING_EDIT);
    }

    public void doConfirmEditCredentialMapping()
            throws PortletException {
        loadCredentialMapping();
        try {
            editCredentialMapping();
            saveCredentialMapping();
            setNextPage(PAGE_CREDENTIAL_MAPPING_VIEW);
        } catch (PortletException e) {
            setIsFormInvalid(true);
            setNextPage(PAGE_CREDENTIAL_MAPPING_EDIT);
        }
    }

    public void doDeleteCredentialMapping()
            throws PortletException {
        loadCredentialMapping();
        setNextPage(PAGE_CREDENTIAL_MAPPING_DELETE);
    }

    public void doConfirmDeleteCredentialMapping()
            throws PortletException {
        loadCredentialMapping();
        deleteCredentialMapping();
        setNextPage(PAGE_CREDENTIAL_MAPPING_DELETE_CONFIRM);
    }

    public List getCredentialMappingList() {
        return this.credentialMappingList;
    }

    public CredentialMapping getCredentialMapping() {
        return this.credentialMapping;
    }

    public void loadCredentialMappingList()
            throws PortletException {
        this.credentialMappingList = this.credentialManagerService.getCredentialMappings();
    }

    public void loadUserCredentialMappingList()
            throws PortletException {
        User user = getPortletUser();
        loadUserCredentialMappingList(user);
    }

    public void loadUserCredentialMappingList(User user)
            throws PortletException {
        this.credentialMappingList = this.credentialManagerService.getCredentialMappings(user);
    }

    public void loadCredentialMapping()
            throws PortletException {
        String credentialMappingID = getParameter("credentialSubject");
        this.credentialMapping = this.credentialManagerService.getCredentialMapping(credentialMappingID);
    }

    public void editCredentialMapping()
            throws PortletException {
    }

    public void saveCredentialMapping()
            throws PortletException {
    }

    public void deleteCredentialMapping()
            throws PortletException {
    }

    /******************************************
     * Active credential methods
     ******************************************/

    public void doListActiveCredential()
            throws PortletException {
        loadActiveCredentialList();
        setNextPage(PAGE_ACTIVE_CREDENTIAL_LIST);
    }

    public void doViewActiveCredential()
            throws PortletException {
        loadActiveCredential();
        setNextPage(PAGE_ACTIVE_CREDENTIAL_VIEW);
    }

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

    public void loadUserActiveCredentialList()
            throws PortletException {
        User user = getPortletUser();
        loadUserActiveCredentialList(user);
    }

    public void loadUserActiveCredentialList(User user)
            throws PortletException {
        this.activeCredentialList = this.credentialManagerService.getActiveCredentials(user);
    }

    public void loadActiveCredential()
            throws PortletException {
        String credentialMappingID = getParameter("credentialSubject");
        this.credentialMapping = this.credentialManagerService.getCredentialMapping(credentialMappingID);
    }
}
