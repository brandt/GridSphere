/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Feb 14, 2003
 * Time: 6:17:36 AM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.portlets.grid.security;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.services.grid.security.credential.*;
import org.gridlab.gridsphere.services.core.user.UserManagerService;
import org.gridlab.gridsphere.services.grid.security.credential.CredentialManagerService;
import org.gridlab.gridsphere.services.grid.security.credential.CredentialMapping;
import org.gridlab.gridsphere.portlets.PortletBean;

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
        setTitle("Credential Mapping Manager");
    }

    public void doDefaultViewAction()
            throws PortletException {
        doListUserActiveCredentials();
    }

    /******************************************
     * Credential retrieval actions
     ******************************************/

    public void doListUserActiveCredentials()
            throws PortletException {
        this.log.debug("Entering doListActiveCredentials");
        loadUserActiveCredentialMappingList();
        setPage(PAGE_USER_ACTIVE_CREDENTIAL_LIST);
        this.log.debug("Exiting doListActiveCredentials");
    }

    public void doViewUserActiveCredential()
           throws PortletException {
        this.log.debug("Entering doViewActiveCredential");
        loadActiveCredentialMapping();
        setPage(PAGE_USER_ACTIVE_CREDENTIAL_VIEW);
        this.log.debug("Exiting doViewActiveCredential");
    }

    public void doRetrieveUserCredentials()
            throws PortletException {
        this.log.debug("Entering doRetrieveCredentials");
        retrieveCredentials();
        setPage(PAGE_USER_ACTIVE_CREDENTIAL_LIST);
        this.log.debug("Exiting doRetrieveCredentials");
    }

    public void doRefreshUserCredentials()
            throws PortletException {
        this.log.debug("Entering doRefreshCredentials");
        refreshCredentials();
        setPage(PAGE_USER_ACTIVE_CREDENTIAL_LIST);
        this.log.debug("Exiting doRefreshCredentials");
    }

    public void doDestroyUserCredentials()
             throws PortletException {
         this.log.debug("Entering doDestroyCredentials");
         setPage(PAGE_USER_CREDENTIAL_DESTROY);
         this.log.debug("Exiting doDestroyCredentials");
     }

    public void doConfirmDestroyUserCredentials()
             throws PortletException {
         this.log.debug("Entering doConfirmDestroyCredentials");
         destroyCredentials();
         setPage(PAGE_USER_CREDENTIAL_DESTROY_CONFIRM);
         this.log.debug("Exiting doConfirmDestroyCredentials");
     }

     public void doCancelDestroyUserCredentials()
              throws PortletException {
          this.log.debug("Entering doCancelDestroyCredentials");
          doListUserActiveCredentials();
          this.log.debug("Exiting doCancelDestroyCredentials");
     }

    /******************************************
     * Credential retrieval methods
     ******************************************/

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

    public void loadUserActiveCredentialMappingList()
            throws PortletException {
        this.credentialMappingList = this.credentialManagerService.getActiveCredentialMappings(this.user);
    }

    public void loadActiveCredentialMapping()
            throws PortletException {
        // Get id of credential permission
        this.credentialMappingID = getParameter("credentialMappingID");
        // If id blank, init credential permission
        if (credentialMappingID.equals("")) {
            initCredentialMapping();
            return;
        }
        // Get credential permission with id
        this.credentialMapping = this.credentialManagerService.getCredentialMapping(credentialMappingID);
        // If no credential permission with id, init credential permission
        if (credentialMapping == null) {
            initCredentialMapping();
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

    public void retrieveCredentials()
            throws PortletException {
        String password = getParameter("password");
        this.credentialManagerService.retrieveCredentials(this.user, password);
    }

    public void refreshCredentials()
            throws PortletException {
    }

    public void destroyCredentials()
            throws PortletException {
        this.credentialManagerService.destroyCredentials(this.user);
    }
}
