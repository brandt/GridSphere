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
import org.gridlab.gridsphere.provider.PortletBean;

import java.util.List;

public class CredentialRetrievalUserBean extends PortletBean {

    // Credential retrieval pages
    public static final String PAGE_USER_ACTIVE_CREDENTIAL_LIST = "/jsp/security/userActiveCredentialList.jsp";
    public static final String PAGE_USER_ACTIVE_CREDENTIAL_VIEW = "/jsp/security/userActiveCredentialView.jsp";
    public static final String PAGE_USER_CREDENTIAL_RETRIEVE = "/jsp/security/userCredentialRetrieve.jsp";
    public static final String PAGE_USER_CREDENTIAL_REFRESH = "/jsp/security/userCredentialRefresh.jsp";
    public static final String PAGE_USER_CREDENTIAL_DESTROY = "/jsp/security/userCredentialDestroy.jsp";
    public static final String PAGE_USER_CREDENTIAL_DESTROY_CONFIRM = "/jsp/security/userCredentialDestroyConfirm.jsp";

    // Credential retrieval variables
    private List credentialMappingList = null;
    private CredentialMapping credentialMapping = null;
    private String credentialMappingID = null;
    private String credentialSubject = null;
    private User credentialUser = null;
    private String credentialLabel = null;
    private String credentialTag = null;

    // Credential manager service
    private CredentialManagerService credentialManagerService = null;

    /******************************************
     * Portlet bean methods
     ******************************************/

    public CredentialRetrievalUserBean() {
        initView();
    }

    public CredentialRetrievalUserBean(PortletConfig config, PortletRequest request, PortletResponse response)
            throws PortletException {
        init(config, request, response);
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
        String retrievalHost = this.credentialManagerService.getCredentialRetrievalHost();
        setTitle("Retrieve Your Credentials From [" + retrievalHost + "]");
        setPage(PAGE_USER_ACTIVE_CREDENTIAL_LIST);
    }

    public void doDefaultViewAction()
            throws PortletException {
        doListUserActiveCredential();
    }

    /******************************************
     * Credential retrieval actions
     ******************************************/

    public void doListUserActiveCredential()
            throws PortletException {
        this.log.debug("Entering doListUserActiveCredential");
        loadUserActiveCredentialMappingList();
        setPage(PAGE_USER_ACTIVE_CREDENTIAL_LIST);
        this.log.debug("Exiting doListUserActiveCredential");
    }

    public void doViewUserActiveCredential()
           throws PortletException {
        this.log.debug("Entering doViewUserActiveCredential");
        loadUserActiveCredentialMapping();
        setPage(PAGE_USER_ACTIVE_CREDENTIAL_VIEW);
        this.log.debug("Exiting doViewUserActiveCredential");
    }

    public void doRetrieveUserCredential()
            throws PortletException {
        this.log.debug("Entering doRetrieveUserCredential");
        System.out.println("Entering doRetrieveUserCredential");
        try {
            System.out.println("Retrieving credentials");
            retrieveUserCredentials();
        } catch (Exception e) {
            System.out.println("Error retrieving credentials");
            setIsFormInvalid(true);
            setFormInvalidMessage(e.getMessage());
        }
        System.out.println("Loading active credentials");
        loadUserActiveCredentialMappingList();
        System.out.println("Loaded active credentials");
        setPage(PAGE_USER_ACTIVE_CREDENTIAL_LIST);
        this.log.debug("Exiting doRetrieveUserCredential");
        System.out.println("Exiting doRetrieveUserCredential");
    }

    public void doRefreshUserCredential()
            throws PortletException {
        this.log.debug("Entering doRefreshUserCredential");
        try {
            refreshUserCredentials();
        } catch (PortletException e) {
            setIsFormInvalid(true);
            setFormInvalidMessage(e.getMessage());
        }
        loadUserActiveCredentialMappingList();
        setPage(PAGE_USER_ACTIVE_CREDENTIAL_LIST);
        this.log.debug("Exiting doRefreshUserCredential");
    }

    public void doDestroyUserCredential()
             throws PortletException {
         this.log.debug("Entering doDestroyUserCredential");
         setPage(PAGE_USER_CREDENTIAL_DESTROY);
         this.log.debug("Exiting doDestroyUserCredential");
     }

    public void doConfirmDestroyUserCredential()
             throws PortletException {
         this.log.debug("Entering doConfirmDestroyUserCredential");
         destroyUserCredentials();
         setPage(PAGE_USER_CREDENTIAL_DESTROY_CONFIRM);
         this.log.debug("Exiting doConfirmDestroyUserCredential");
     }

     public void doCancelDestroyUserCredential()
              throws PortletException {
          this.log.debug("Entering doCancelDestroyUserCredential");
          doListUserActiveCredential();
          this.log.debug("Exiting doCancelDestroyUserCredential");
     }

    /******************************************
     * Credential retrieval methods
     ******************************************/

    public String getCredentialRetrievalHost() {
        return this.credentialManagerService.getCredentialRetrievalHost();
    }

    public List getCredentialMappingList() {
        return this.credentialMappingList;
    }

    public CredentialMapping getCredentialMapping() {
        return this.credentialMapping;
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

    private void loadUserActiveCredentialMappingList()
            throws PortletException {
        this.log.debug("Entering doDestroyUserCredential");
        System.out.println("Entering loadUserActiveCredentialMappingList");
        User user = getPortletUser();
        this.credentialMappingList = this.credentialManagerService.getActiveCredentialMappings(user);
        this.log.debug("Exiting loadUserActiveCredentialMappingList");
        System.out.println("Exiting loadUserActiveCredentialMappingList");
    }

    private void loadUserActiveCredentialMapping()
            throws PortletException {
        // Get id of credential permission
        this.credentialMappingID = getParameter("credentialMappingID");
        // If id blank, init credential permission
        if (credentialMappingID.equals("")) {
            initUserCredentialMapping();
            return;
        }
        // Get credential permission with id
        this.credentialMapping = this.credentialManagerService.getCredentialMapping(credentialMappingID);
        // If no credential permission with id, init credential permission
        if (credentialMapping == null) {
            initUserCredentialMapping();
            return;
        }
        // Otherwise set credential mapping properties
        this.credentialSubject = credentialMapping.getSubject();
        this.credentialLabel = credentialMapping.getLabel();
        this.credentialTag = credentialMapping.getTag();
        this.credentialUser = credentialMapping.getUser();
    }

    private void initUserCredentialMapping()
            throws PortletException {
        this.credentialSubject = new String();
        this.credentialLabel = new String();
        this.credentialTag = new String();
    }

    private void retrieveUserCredentials()
            throws PortletException {
        User user = getPortletUser();
        if (this.credentialManagerService.hasCredentialMappings(user)) {
            String password = getParameter("password");
            this.credentialManagerService.retrieveCredentials(user, password);
        } else {
            String message = "There are no credentials mapped to your account. "
                           + "If you wish to use Grid credentials, please see "
                           + "the credential mapping portlet for more details.";
            throw new PortletException(message);
        }
    }

    private void refreshUserCredentials()
            throws PortletException {
    }

    private void destroyUserCredentials()
            throws PortletException {
        this.credentialManagerService.destroyCredentials(this.user);
    }
}
