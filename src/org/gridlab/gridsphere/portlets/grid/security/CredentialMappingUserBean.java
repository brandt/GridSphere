/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets.grid.security;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.services.grid.security.credential.*;
import org.gridlab.gridsphere.services.core.user.UserManagerService;
import org.gridlab.gridsphere.services.grid.security.credential.CredentialManagerService;
import org.gridlab.gridsphere.services.grid.security.credential.CredentialMapping;
import org.gridlab.gridsphere.portlets.PortletBean;

import java.util.List;

public class CredentialMappingUserBean extends PortletBean {

    // Credential mapping pages
    public static final String PAGE_USER_CREDENTIAL_MAPPING_LIST = "/jsp/security/userCredentialMappingList.jsp";
    public static final String PAGE_USER_CREDENTIAL_MAPPING_VIEW = "/jsp/security/userCredentialMappingView.jsp";
    public static final String PAGE_USER_CREDENTIAL_MAPPING_EDIT = "/jsp/security/userCredentialMappingEdit.jsp";
    public static final String PAGE_USER_CREDENTIAL_MAPPING_DELETE = "/jsp/security/userCredentialMappingDelete.jsp";
    public static final String PAGE_USER_CREDENTIAL_MAPPING_DELETE_CONFIRM = "/jsp/security/userCredentialMappingDeleteConfirm.jsp";

    // Credential mapping variables
    private List credentialMappingList = null;
    private CredentialMapping credentialMapping = null;
    private String credentialMappingID = null;
    private String credentialSubject = null;
    private String credentialLabel = null;
    private String credentialTag = null;

    // Credential manager service
    private CredentialManagerService credentialManagerService = null;
    // User manager service
    protected UserManagerService userManagerService = null;
    protected List userList = null;

    /******************************************
     * Portlet bean methods
     ******************************************/

    public CredentialMappingUserBean() {
        initView();
    }

    public CredentialMappingUserBean(PortletConfig config, PortletRequest request, PortletResponse response)
            throws PortletException {
        init(config, request, response);
        initServices();
        initView();
    }

    protected void initServices()
            throws PortletException {
        this.log.debug("Entering initServices()");
        this.credentialManagerService = (CredentialManagerService)getPortletService(CredentialManagerService.class);
        this.userManagerService = (UserManagerService)getPortletService(UserManagerService.class);
        this.log.debug("Exiting initServices()");
    }

    protected void initView() {
        setTitle("Credential Mapping");
    }

    public void doDefaultViewAction()
            throws PortletException {
        doListUserCredentialMapping();
    }

    /******************************************
     * User manager methods
     ******************************************/

    protected void loadUserList()
            throws PortletException {
        this.userList = this.userManagerService.getUsers();
    }

    public List getUserList() {
        return this.userList;
    }

    /******************************************
     * Credential mapping actions
     ******************************************/

    public void doListUserCredentialMapping()
            throws PortletException {
        this.log.debug("Entering doListUserCredentialMapping");
        loadUserCredentialMappingList();
        setPage(PAGE_USER_CREDENTIAL_MAPPING_LIST);
        this.log.debug("Exiting doListUserCredentialMapping");
    }

    public void doViewUserCredentialMapping()
            throws PortletException {
        this.log.debug("Entering doViewUserCredentialMapping");
        loadUserCredentialMapping();
        setPage(PAGE_USER_CREDENTIAL_MAPPING_VIEW);
        this.log.debug("Exiting doViewUserCredentialMapping");
    }

    public void doEditUserCredentialMapping()
            throws PortletException {
        this.log.debug("Entering doEditUserCredentialMapping");
        loadUserCredentialMapping();
        setPage(PAGE_USER_CREDENTIAL_MAPPING_EDIT);
        this.log.debug("Exiting doEditUserCredentialMapping");
    }

    public void doConfirmEditUserCredentialMapping()
            throws PortletException {
        this.log.debug("Entering doConfirmEditUserCredentialMapping");
        loadUserCredentialMapping();
        try {
            editUserCredentialMapping();
            saveUserCredentialMapping();
            setPage(PAGE_USER_CREDENTIAL_MAPPING_VIEW);
        } catch (PortletException e) {
            setIsFormInvalid(true);
            setFormInvalidMessage(e.getMessage());
            setPage(PAGE_USER_CREDENTIAL_MAPPING_EDIT);
        }
        this.log.debug("Exiting doConfirmEditUserCredentialMapping");
    }

    public void doCancelEditUserCredentialMapping()
            throws PortletException {
        this.log.debug("Entering doCancelEditUserCredentialMapping");
        doListUserCredentialMapping();
        this.log.debug("Exiting doCancelEditUserCredentialMapping");
    }

    public void doDeleteUserCredentialMapping()
            throws PortletException {
        this.log.debug("Entering doDeleteUserCredentialMapping");
        loadUserCredentialMapping();
        setPage(PAGE_USER_CREDENTIAL_MAPPING_DELETE);
        this.log.debug("Exiting doDeleteUserCredentialMapping");
    }

    public void doConfirmDeleteUserCredentialMapping()
            throws PortletException {
        this.log.debug("Entering doConfirmDeleteUserCredentialMapping");
        loadUserCredentialMapping();
        deleteUserCredentialMapping();
        setPage(PAGE_USER_CREDENTIAL_MAPPING_DELETE_CONFIRM);
        this.log.debug("Exiting doConfirmDeleteUserCredentialMapping");
    }

    public void doCancelDeleteUserCredentialMapping()
            throws PortletException {
        this.log.debug("Entering doCancelDeleteUserCredentialMapping");
        doListUserCredentialMapping();
        this.log.debug("Exiting doCancelDeleteUserCredentialMapping");
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

    public void loadUserCredentialMappingList()
            throws PortletException {
        this.credentialMappingList = this.credentialManagerService.getCredentialMappings(this.user);
    }

    public void loadUserCredentialMapping()
            throws PortletException {
        // Get id of credential permission
        this.credentialMappingID = getParameter("credentialMappingID");
        // If id blank, init credential permission
        if (credentialMappingID.equals("")) {
            initUserCredentialMapping();
            // And if new, load list of users
            loadUserList();
            return;
        }
        // Get credential permission with id
        this.credentialMapping = this.credentialManagerService.getCredentialMapping(credentialMappingID);
        // If no credential permission with id, init credential permission
        if (credentialMapping == null) {
            initUserCredentialMapping();
            // And if new, load list of users
            loadUserList();
            return;
        }
        // Otherwise set credential mapping properties
        this.credentialSubject = credentialMapping.getSubject();
        this.credentialLabel = credentialMapping.getLabel();
        this.credentialTag = credentialMapping.getTag();
    }

    public void initUserCredentialMapping()
            throws PortletException {
        this.credentialSubject = new String();
        this.credentialLabel = new String();
        this.credentialTag = new String();
    }

    public void editUserCredentialMapping()
            throws PortletException {
        if (this.credentialMappingID.equals("")) {
            this.credentialSubject = getParameter("credentialSubject");
            if (credentialSubject.equals("")) {
                throw new PortletException("You must specify a subject for the given credential");
            }
        }
        this.credentialLabel = getParameter("credentialLabel");
        if (credentialLabel.equals("")) {
            throw new PortletException("You must specify a label for the given credential");
        }
        this.credentialTag = getParameter("credentialTag");
        if (credentialTag.equals("")) {
            throw new PortletException("You must specify a tag for the given credential");
        }
    }

    public void saveUserCredentialMapping()
            throws PortletException {
        CredentialMapping credentialMapping = null;
        try {
            // If new credential mapping, create it...
            if (credentialMappingID.equals("")) {
                credentialMapping
                    = this.credentialManagerService.createCredentialMapping(this.credentialSubject,
                                                                            this.user,
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

    public void deleteUserCredentialMapping()
            throws PortletException {
        this.credentialManagerService.deleteCredentialMapping(this.credentialSubject);
    }
}
