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

public class CredentialMappingBean extends CredentialManagerBean {

    // Credential mapping actions
    public static final String ACTION_CREDENTIAL_MAPPING_LIST = "doListCredentialMapping";
    public static final String ACTION_CREDENTIAL_MAPPING_VIEW = "doViewCredentialMapping";
    public static final String ACTION_CREDENTIAL_MAPPING_EDIT = "doEditCredentialMapping";
    public static final String ACTION_CREDENTIAL_MAPPING_EDIT_CONFIRM = "doConfirmEditCredentialMapping";
    public static final String ACTION_CREDENTIAL_MAPPING_EDIT_CANCEL = "doCancelEditCredentialMapping";
    public static final String ACTION_CREDENTIAL_MAPPING_DELETE = "doDeleteCredentialMapping";
    public static final String ACTION_CREDENTIAL_MAPPING_DELETE_CONFIRM = "doConfirmDeleteCredentialMapping";
    public static final String ACTION_CREDENTIAL_MAPPING_DELETE_CANCEL = "doCancelDeleteCredentialMapping";

    // Credential mapping pages
    public static final String PAGE_CREDENTIAL_MAPPING_LIST = "/jsp/credential/credentialMappingList.jsp";
    public static final String PAGE_CREDENTIAL_MAPPING_VIEW = "/jsp/credential/credentialMappingView.jsp";
    public static final String PAGE_CREDENTIAL_MAPPING_EDIT = "/jsp/credential/credentialMappingEdit.jsp";
    public static final String PAGE_CREDENTIAL_MAPPING_DELETE = "/jsp/credential/credentialMappingDelete.jsp";
    public static final String PAGE_CREDENTIAL_MAPPING_DELETE_CONFIRM = "/jsp/credential/credentialMappingDeleteConfirm.jsp";

    // Credential mapping variables
    private List credentialMappingList = null;
    private CredentialMapping credentialMapping = null;

    /******************************************
     * Portlet bean methods
     ******************************************/

    public CredentialMappingBean() {
        super();
        initView();
    }

    public CredentialMappingBean(PortletConfig config, PortletRequest request, PortletResponse response)
            throws PortletException {
        super(config, request, response);
        initView();
    }

    private void initView() {
        setTitle("Credential Mapping Manager");
    }

    public void doDefaultViewAction()
            throws PortletException {
        doListCredentialMapping();
    }

    /******************************************
     * Credential mapping actions
     ******************************************/

    public void doListCredentialMapping()
            throws PortletException {
        this.log.debug("Entering doListCredentialMapping");
        loadCredentialMappingList();
        setPage(PAGE_CREDENTIAL_MAPPING_LIST);
        this.log.debug("Exiting doListCredentialMapping");
    }

    public void doViewCredentialMapping()
            throws PortletException {
        this.log.debug("Entering doViewCredentialMapping");
        loadCredentialMapping();
        setPage(PAGE_CREDENTIAL_MAPPING_VIEW);
        this.log.debug("Exiting doViewCredentialMapping");
    }

    public void doEditCredentialMapping()
            throws PortletException {
        this.log.debug("Entering doEditCredentialMapping");
        loadCredentialMapping();
        setPage(PAGE_CREDENTIAL_MAPPING_EDIT);
        this.log.debug("Exiting doEditCredentialMapping");
    }

    public void doConfirmEditCredentialMapping()
            throws PortletException {
        this.log.debug("Entering doConfirmEditCredentialMapping");
        loadCredentialMapping();
        try {
            editCredentialMapping();
            saveCredentialMapping();
            setPage(PAGE_CREDENTIAL_MAPPING_VIEW);
        } catch (PortletException e) {
            setIsFormInvalid(true);
            setFormInvalidMessage(e.getMessage());
            setPage(PAGE_CREDENTIAL_MAPPING_EDIT);
        }
        this.log.debug("Exiting doConfirmEditCredentialMapping");
    }

    public void doCancelEditCredentialMapping()
            throws PortletException {
        this.log.debug("Entering doConfirmEditCredentialMapping");
        doListCredentialMapping();
        this.log.debug("Exiting doConfirmEditCredentialMapping");
    }

    public void doDeleteCredentialMapping()
            throws PortletException {
        this.log.debug("Entering doDeleteCredentialMapping");
        loadCredentialMapping();
        setPage(PAGE_CREDENTIAL_MAPPING_DELETE);
        this.log.debug("Exiting doDeleteCredentialMapping");
    }

    public void doConfirmDeleteCredentialMapping()
            throws PortletException {
        this.log.debug("Entering doConfirmDeleteCredentialMapping");
        loadCredentialMapping();
        deleteCredentialMapping();
        setPage(PAGE_CREDENTIAL_MAPPING_DELETE_CONFIRM);
        this.log.debug("Exiting doConfirmDeleteCredentialMapping");
    }

    public void doCancelDeleteCredentialMapping()
            throws PortletException {
        this.log.debug("Entering doCancelDeleteCredentialMapping");
        doListCredentialMapping();
        this.log.debug("Exiting doCancelDeleteCredentialMapping");
    }

    /******************************************
     * Credential mapping methods
     ******************************************/

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

    public void loadCredentialMapping()
            throws PortletException {
        String credentialMappingID = getParameter("credentialMappingID");
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
}
