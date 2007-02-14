/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.user.impl;

import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerFactory;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerRdbms;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletUser;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.services.core.security.auth.AuthorizationException;
import org.gridlab.gridsphere.services.core.security.auth.AuthenticationException;
import org.gridlab.gridsphere.services.core.security.auth.modules.LoginAuthModule;
import org.gridlab.gridsphere.services.core.security.auth.modules.impl.descriptor.AuthModulesDescriptor;
import org.gridlab.gridsphere.services.core.security.auth.modules.impl.descriptor.AuthModuleCollection;
import org.gridlab.gridsphere.services.core.security.auth.modules.impl.descriptor.AuthModuleDefinition;
import org.gridlab.gridsphere.services.core.user.LoginService;
import org.gridlab.gridsphere.services.core.user.LoginUserModule;
import org.gridlab.gridsphere.services.core.user.UserManagerService;
import org.gridlab.gridsphere.services.core.messaging.TextMessagingService;
import org.gridlab.gridsphere.services.core.portal.PortalConfigService;
import org.gridlab.gridsphere.services.core.portal.PortalConfigSettings;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfig;
import org.gridsphere.tmf.TextMessagingException;

import java.lang.reflect.Constructor;
import java.util.*;

/**
 * The <code>LoginService</code> is the primary interface that defines the login method used to obtain a
 * <code>User</code> from a username and password. The <code>LoginService</code> is configured
 * dynamically at run-time with login authorization modules. By default the PASSWORD_AUTH_MODULE is
 * selected which uses the GridSphere database to store passwords. Other authorization modules
 * can use external directory servers such as LDAP, etc
 */
public class LoginServiceImpl implements LoginService, PortletServiceProvider {

    private PortletLog log = SportletLog.getInstance(LoginServiceImpl.class);
    private static boolean inited = false;
    private List authModules = new ArrayList();
    private static LoginUserModule activeLoginModule = null;

    private PersistenceManagerRdbms pm = null;

    private String authMappingPath = GridSphereConfig.getServletContext().getRealPath("/WEB-INF/mapping/auth-modules-mapping.xml");
    private String authModulesPath = GridSphereConfig.getServletContext().getRealPath("/WEB-INF/authmodules.xml");
    private UserManagerService userManagerService;
    private TextMessagingService tms = null;
    private PortalConfigService portalConfigService = null;

    public LoginServiceImpl() {

    }

    public List getAuthModules() {
        return authModules;
    }

    public List getActiveAuthModules() {
        List activeMods = new ArrayList();
        Iterator it = authModules.iterator();
        while (it.hasNext()) {
            LoginAuthModule authModule = (LoginAuthModule)it.next();
            if (authModule.isModuleActive()) activeMods.add(authModule);
        }
        return activeMods;
    }

    public List getSupportedAuthModules() {
        return authModules;
    }

    public LoginUserModule getActiveLoginModule() {
        return activeLoginModule;
    }

    public void setActiveLoginModule(LoginUserModule loginModule) {
        activeLoginModule = loginModule;
    }

    /**
     * Initializes the portlet service.
     * The init method is invoked by the portlet container immediately after a portlet service has
     * been instantiated and before it is passed to the requestor.
     *
     * @param config the service configuration
     * @throws PortletServiceUnavailableException
     *          if an error occurs during initialization
     */
    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        log.debug("in login service init");
        if (!inited) {
            pm = PersistenceManagerFactory.createGridSphereRdbms();
            PortletServiceFactory factory = SportletServiceFactory.getInstance();
            String loginClassName = config.getInitParameter("LOGIN_MODULE");
            try {
                Class loginModClass = Class.forName(loginClassName);
                activeLoginModule = (LoginUserModule) factory.createPortletService(loginModClass, config.getServletContext(), true);
            } catch (ClassNotFoundException e) {
                log.error("Unable to create class from class name: " + loginClassName, e);
            } catch (PortletServiceNotFoundException e) {
                log.error("Unable to get service from portlet service factory: " + loginClassName, e);
            }

            try {
                userManagerService = (UserManagerService)factory.createPortletService(UserManagerService.class, null, true);
                tms = (TextMessagingService)factory.createPortletService(TextMessagingService.class, null, true);
                portalConfigService = (PortalConfigService)factory.createPortletService(PortalConfigService.class, null, true);

                PortalConfigSettings settings = portalConfigService.getPortalConfigSettings();

                String numTries = settings.getAttribute("ACCOUNT_NUMTRIES");

                if (numTries == null) {
                    settings.setAttribute("ACCOUNT_NUMTRIES", "-1");
                }
                portalConfigService.savePortalConfigSettings(settings);
            } catch (PortletServiceException e) {
                log.error("Unable to init services! ", e);
            }


            log.debug("Created a login module service: " + loginClassName);

            loadAuthModules(authModulesPath, Thread.currentThread().getContextClassLoader());

            inited = true;
        }
    }

    public void loadAuthModules(String authModsPath, ClassLoader classloader) {

        AuthModulesDescriptor desc;
        try {
            desc = new AuthModulesDescriptor(authModsPath, authMappingPath);

            AuthModuleCollection coll = desc.getCollection();
            List modList = coll.getAuthModulesList();
            Iterator it = modList.iterator();
            log.info("loading auth modules:");
            while (it.hasNext()) {
                AuthModuleDefinition def = (AuthModuleDefinition)it.next();
                log.info(def.toString());
                String modClassName = def.getModuleImplementation();

                // before initializing check if we know about this mod in the db
                AuthModuleDefinition am = getAuthModuleDefinition(def.getModuleName());
                if (am != null) {
                    def.setModulePriority(am.getModulePriority());
                    def.setModuleActive(am.getModuleActive());
                } else {
                    pm.saveOrUpdate(def);
                }
                Class c = Class.forName(modClassName, true, classloader);
                Class[] parameterTypes = new Class[]{AuthModuleDefinition.class};
                Object[] obj = new Object[]{def};
                Constructor con = c.getConstructor(parameterTypes);
                LoginAuthModule authModule = (LoginAuthModule) con.newInstance(obj);
                authModules.add(authModule);
            }
        } catch (Exception e) {
            log.error("Error loading auth module!", e);
        }
    }

    public void saveAuthModule(LoginAuthModule authModule) {
        try {
            log.debug("saving auth module: " + authModule.getModuleName() + " " +
                    authModule.getModulePriority() + " " + authModule.isModuleActive());
            AuthModuleDefinition am = getAuthModuleDefinition(authModule.getModuleName());
            if (am != null) {
                am.setModulePriority(authModule.getModulePriority());
                am.setModuleActive(authModule.isModuleActive());
                pm.update(am);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private AuthModuleDefinition getAuthModuleDefinition(String moduleName) {
        AuthModuleDefinition am = null;
        try {
            am = (AuthModuleDefinition)pm.restore("select authmodule from " + AuthModuleDefinition.class.getName() +
                " authmodule where authmodule.ModuleName='" +
                moduleName + "'");
        } catch (PersistenceManagerException e) {
            e.printStackTrace();
        }
        return am;
    }

    public List getAuthModuleDefinitions() {
        List mods = null;
        try {
            mods = pm.restoreList("select authmod from "
                + AuthModuleDefinition.class.getName()
                + " authmod ");
        } catch (PersistenceManagerException e) {
            e.printStackTrace();
        }
        return mods;
    }


    /**
     * The destroy method is invoked by the portlet container to destroy a portlet service.
     * This method must free all resources allocated to the portlet service.
     */
    public void destroy() {
    }

    private void disableAccount(PortletRequest req) {

        String loginName = req.getParameter("username");

        User user = userManagerService.getUserByUserName(loginName);
        if (user != null) {
            System.err.println("user= " + user);
            SportletUser suser = userManagerService.editUser(user);
            suser.setAttribute(User.DISABLED, "true");
            userManagerService.saveUser(suser);

            org.gridsphere.tmf.message.MailMessage mailToUser = tms.getMailMessage();
            StringBuffer body = new StringBuffer();
            body.append(getLocalizedText(req, "LOGIN_DISABLED_MSG1") + " " + getLocalizedText(req, "LOGIN_DISABLED_MSG2") + "\n\n");
            mailToUser.setBody(body.toString());
            mailToUser.setSubject(getLocalizedText(req, "LOGIN_DISABLED_SUBJECT"));
            mailToUser.setTo(user.getEmailAddress());
            mailToUser.setServiceid("mail");

            org.gridsphere.tmf.message.MailMessage mailToAdmin = tms.getMailMessage();
            StringBuffer body2 = new StringBuffer();
            body2.append(getLocalizedText(req, "LOGIN_DISABLED_ADMIN_MSG") + " " + user.getUserName());
            mailToAdmin.setBody(body2.toString());
            mailToAdmin.setSubject(getLocalizedText(req, "LOGIN_DISABLED_SUBJECT") + " " + user.getUserName());
            mailToAdmin.setTo(tms.getServiceUserID("mail", "root"));
            mailToUser.setServiceid("mail");


            try {
                tms.send(mailToUser);
                tms.send(mailToAdmin);
            } catch (TextMessagingException e) {
                log.error("Unable to send mail message!", e);
            }
        }
    }
    public User login(PortletRequest req)
            throws AuthenticationException, AuthorizationException {
        String loginName = req.getParameter("username");
        String loginPassword = req.getParameter("password");

        if ((loginName == null) || (loginPassword == null)) {
            throw new AuthorizationException(getLocalizedText(req, "LOGIN_AUTH_BLANK"));
        }

        // first get user
        User user = activeLoginModule.getLoggedInUser(loginName);

        if (user == null) throw new AuthorizationException(getLocalizedText(req, "LOGIN_AUTH_NOUSER"));
        // tried one to many times using same name
        int numTriesInt;
        String numTries = (String) user.getAttribute("ACCOUNT_NUMTRIES");
        if (numTries == null) {
            numTriesInt = 1;
        } else {
            numTriesInt = Integer.valueOf(numTries).intValue();
        }
        System.err.println("num tries = " + numTriesInt);
        PortalConfigSettings settings = portalConfigService.getPortalConfigSettings();

        String defNumTries = settings.getAttribute("ACCOUNT_NUMTRIES");
        int defaultNumTries = Integer.valueOf(defNumTries).intValue();
        if ((defaultNumTries != -1) && (numTriesInt >= defaultNumTries)) {
            disableAccount(req);
            throw new AuthorizationException(getLocalizedText(req, "LOGIN_TOOMANY_ATTEMPTS"));
        }

        String accountStatus = (String)user.getAttribute(User.DISABLED);
        if ((accountStatus != null) && ("TRUE".equalsIgnoreCase(accountStatus)))
            throw new AuthorizationException(getLocalizedText(req, "LOGIN_AUTH_DISABLED"));

        // second invoke the appropriate auth module

        List modules = this.getActiveAuthModules();

        Collections.sort(modules);
        AuthenticationException authEx = null;

        Iterator it = modules.iterator();
        log.debug("in login: Active modules are: ");
        boolean success = false;
        while (it.hasNext()) {
            success = false;
            LoginAuthModule mod = (LoginAuthModule) it.next();
            log.debug(mod.getModuleName());
            try {
                mod.checkAuthentication(user, loginPassword);
                success = true;
            } catch (AuthenticationException e) {
                String errMsg = mod.getModuleError(e.getMessage(), req.getLocale());
                if (errMsg != null) {
                    authEx = new AuthenticationException(errMsg);
                } else {
                    authEx = e;
                }
            }
            if (success) break;
        }
        if (!success) {
            numTriesInt++;
            ((SportletUser)user).setAttribute("ACCOUNT_NUMTRIES", String.valueOf(numTriesInt));
            userManagerService.saveUser(user);
            throw authEx;
        }

        return user;
    }

    protected String getLocalizedText(PortletRequest req, String key) {
        Locale locale = req.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("gridsphere.resources.Portlet", locale);
        return bundle.getString(key);
    }

}
