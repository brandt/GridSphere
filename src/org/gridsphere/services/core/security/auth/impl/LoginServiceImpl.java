/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: LoginServiceImpl.java 5032 2006-08-17 18:15:06Z novotny $
 */
package org.gridsphere.services.core.security.auth.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gridsphere.portlet.User;
import org.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridsphere.services.core.persistence.PersistenceManagerException;
import org.gridsphere.services.core.persistence.PersistenceManagerRdbms;
import org.gridsphere.services.core.persistence.PersistenceManagerService;
import org.gridsphere.services.core.security.auth.AuthenticationException;
import org.gridsphere.services.core.security.auth.AuthorizationException;
import org.gridsphere.services.core.security.auth.LoginService;
import org.gridsphere.services.core.security.auth.LoginUserModule;
import org.gridsphere.services.core.security.auth.modules.LoginAuthModule;
import org.gridsphere.services.core.security.auth.modules.impl.descriptor.AuthModuleCollection;
import org.gridsphere.services.core.security.auth.modules.impl.descriptor.AuthModuleDefinition;
import org.gridsphere.services.core.security.auth.modules.impl.descriptor.AuthModulesDescriptor;
import org.gridsphere.services.core.user.UserManagerService;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * The <code>LoginService</code> is the primary interface that defines the login method used to obtain a
 * <code>User</code> from a username and password. The <code>LoginService</code> is configured
 * dynamically at run-time with login authorization modules. By default the PASSWORD_AUTH_MODULE is
 * selected which uses the GridSphere database to store passwords. Other authorization modules
 * can use external directory servers such as LDAP, etc
 */
public class LoginServiceImpl implements LoginService, PortletServiceProvider {

    private Log log = LogFactory.getLog(LoginServiceImpl.class);
    private UserManagerService userManagerService = null;
    private boolean inited = false;
    private List authModules = new ArrayList();
    private LoginUserModule activeLoginModule = null;

    private PersistenceManagerRdbms pm = null;

    private URL authMappingStream = getClass().getResource("/org/gridsphere/services/core/security/auth/modules/impl/descriptor/auth-modules-mapping.xml");


    public LoginServiceImpl() {
    }

    public List<LoginAuthModule> getAuthModules() {
        return authModules;
    }

    public List<LoginAuthModule> getActiveAuthModules() {
        List activeMods = new ArrayList();
        Iterator it = authModules.iterator();
        while (it.hasNext()) {
            LoginAuthModule authModule = (LoginAuthModule)it.next();
            if (authModule.isModuleActive()) activeMods.add(authModule);
        }
        return activeMods;
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
            PersistenceManagerService pmservice = (PersistenceManagerService)PortletServiceFactory.createPortletService(PersistenceManagerService.class, true);
            pm = pmservice.createGridSphereRdbms();
            String loginClassName = config.getInitParameter("LOGIN_MODULE");
            try {
                Class loginModClass = Class.forName(loginClassName);
                activeLoginModule = (LoginUserModule)PortletServiceFactory.createPortletService(loginModClass, true);
            } catch (ClassNotFoundException e) {
                log.error("Unable to create class from class name: " + loginClassName, e);
            } catch (PortletServiceNotFoundException e) {
                log.error("Unable to get service from portlet service factory: " + loginClassName, e);
            }
            log.debug("Created a login module service: " + loginClassName);
            //String authModulesPath = config.getServletContext().getRealPath("/WEB-INF/authmodules.xml");
            //loadAuthModules(authModulesPath, Thread.currentThread().getContextClassLoader());
            userManagerService = (UserManagerService)PortletServiceFactory.createPortletService(UserManagerService.class, true);
            inited = true;
        }
    }

    public void loadAuthModules(String authModsPath, ClassLoader classloader) {

        AuthModulesDescriptor desc;
        try {
            desc = new AuthModulesDescriptor(authModsPath, authMappingStream);

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

    public User login(HttpServletRequest req)
            throws AuthenticationException, AuthorizationException {
        String loginName = req.getParameter("username");
        String loginPassword = req.getParameter("password");
        String certificate = null;

        X509Certificate[] certs = (X509Certificate[]) req.getAttribute("javax.servlet.request.X509Certificate");
        if (certs != null && certs.length > 0) {
            certificate = certificateTransform(certs[0].getSubjectDN().toString());
        }

        User user = null;

        // if using client certificate, then don't use login modules
        if (certificate == null) {
            System.err.println("name=" + loginName + " pass=" + loginPassword);
            if ((loginName == null) || (loginPassword == null)) {
                throw new AuthorizationException(getLocalizedText(req, "LOGIN_AUTH_BLANK"));
            }
            // first get user
            user = activeLoginModule.getLoggedInUser(loginName);
        } else {

            log.debug("Using certificate for login :" + certificate);
            List userList = userManagerService.getUsersByAttribute("certificate", certificate, null);
            if (!userList.isEmpty()) {
                user = (User)userList.get(0);
            }
        }

        if (user == null) throw new AuthorizationException(getLocalizedText(req, "LOGIN_AUTH_NOUSER"));

        String accountStatus = (String)user.getAttribute(User.DISABLED);
        if ((accountStatus != null) && ("TRUE".equalsIgnoreCase(accountStatus)))
            throw new AuthorizationException(getLocalizedText(req, "LOGIN_AUTH_DISABLED"));

        // If authorized via certificates no other authorization needed
        if (certificate != null) return user;

        // second invoke the appropriate auth module
        List modules = getActiveAuthModules();

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
        if (!success) throw authEx;

        return user;
    }

    /**
     *  Transform certificate subject from :
     *  CN=Engbert Heupers, O=sara, O=users, O=dutchgrid
     *  to :
     *  /O=dutchgrid/O=users/O=sara/CN=Engbert Heupers
     * @param certificate string
     * @return certificate string
     */
    private String certificateTransform(String certificate) {
        String ls[] = certificate.split(", ");
        StringBuffer res = new StringBuffer();
        for(int i = ls.length - 1; i >= 0; i--) {
            res.append("/");
            res.append(ls[i]);
        }
        return res.toString();
    }

    protected String getLocalizedText(HttpServletRequest req, String key) {
        Locale locale = req.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("gridsphere.resources.Portlet", locale);
        return bundle.getString(key);
    }

}
