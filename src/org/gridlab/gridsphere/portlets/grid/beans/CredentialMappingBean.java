/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Feb 14, 2003
 * Time: 6:17:36 AM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.portlets.grid.beans;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.services.grid.security.credential.*;
import org.gridlab.gridsphere.services.core.user.UserManagerService;
import org.gridlab.gridsphere.services.grid.security.credential.CredentialManagerService;
import org.gridlab.gridsphere.services.grid.security.credential.CredentialMapping;

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
    private String credentialMappingID = null;
    private String credentialSubject = null;
    private User credentialUser = null;
    private String credentialLabel = null;
    private String credentialTag = null;

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

    public boolean isNewCredentialMapping() {
        return (this.credentialMapping == null);
    }

    public String getCredentialMappingID() {
        return this.credentialMappingID;
    }

    public String getCredentialSubject() {
        return this.credentialSubject;
    }

    public String getCredentialLabel() {
        return this.credentialLabel;
    }

    public String getCredentialTag() {
        return this.credentialTag;
    }

    public User getCredentialUser() {
        return this.credentialUser;
    }

    public String getCredentialUserName() {
        if (this.credentialUser == null) {
            return "";
        }
        return this.credentialUser.getUserName();
    }

    public String getCredentialUserFullName() {
        if (this.credentialUser == null) {
            return "";
        }
        return this.credentialUser.getFullName();
    }

    public void loadCredentialMappingList()
            throws PortletException {
        this.credentialMappingList = this.credentialManagerService.getCredentialMappings();
    }

    public void loadCredentialMapping()
            throws PortletException {
        // Get id of credential permission
        this.credentialMappingID = getParameter("credentialMappingID");
        // If id blank, init credential permission
        if (credentialMappingID.equals("")) {
            initCredentialMapping();
            // And if new, load list of users
            loadUserList();
            return;
        }
        // Get credential permission with id
        this.credentialMapping = this.credentialManagerService.getCredentialMapping(credentialMappingID);
        // If no credential permission with id, init credential permission
        if (credentialMapping == null) {
            initCredentialMapping();
            // And if new, load list of users
            loadUserList();
            return;
        }
        // Otherwise set credential mapping properties
        this.credentialSubject = credentialMapping.getSubject();
        this.credentialLabel = credentialMapping.getLabel();
        this.credentialTag = credentialMapping.getTag();
        this.credentialUser = credentialMapping.getUser();
    }

    public void initCredentialMapping()
            throws PortletException {
        this.credentialSubject = new String();
        this.credentialLabel = new String();
        this.credentialTag = new String();
    }

    public void editCredentialMapping()
            throws PortletException {
        this.credentialSubject = getParameter("credentialSubject");
        if (credentialSubject.equals("")) {
            throw new PortletException("You must specify a subject for the given credential");
        }
        this.credentialLabel = getParameter("credentialLabel");
        if (credentialLabel.equals("")) {
            throw new PortletException("You must specify a label for the given credential");
        }
        this.credentialTag = getParameter("credentialTag");
        if (credentialTag.equals("")) {
            throw new PortletException("You must specify a tag for the given credential");
        }
        String credentialUserID = getParameter("credentialUserID");
        if (credentialUserID.equals("")) {
            throw new PortletException("You must select a user to map to the given credential");
        }
        this.credentialUser = this.userManagerService.getUser(credentialUserID);
        if (credentialUser == null) {
            throw new PortletException("The user you have selected does not exist");
        }
    }

    public void saveCredentialMapping()
            throws PortletException {
        CredentialMapping credentialMapping = null;
        try {
            // If new credential mapping, create it...
            if (credentialMappingID.equals("")) {
                credentialMapping
                    = this.credentialManagerService.createCredentialMapping(this.credentialSubject,
                                                                            this.credentialUser,
                                                                            this.credentialTag);
                this.credentialManagerService.setCredentialLabel(this.credentialSubject, this.credentialLabel);
                this.credentialMappingID = credentialSubject;
            // Otherwise retrieve and update mapping
            } else {
                credentialMapping
                    = this.credentialManagerService.getCredentialMapping(this.credentialSubject);
                this.credentialManagerService.setCredentialLabel(this.credentialSubject, this.credentialLabel);
                this.credentialManagerService.setCredentialTag(this.credentialSubject, this.credentialTag);
            }
        } catch (Exception e) {
            throw new PortletException(e.getMessage());
        }
    }

    public void deleteCredentialMapping()
            throws PortletException {
        this.credentialManagerService.deleteCredentialMapping(this.credentialSubject);
    }
}
