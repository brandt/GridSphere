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
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.services.core.security.auth.AuthorizationException;
import org.gridlab.gridsphere.services.core.security.auth.AuthenticationException;
import org.gridlab.gridsphere.services.core.security.auth.modules.LoginAuthModule;
import org.gridlab.gridsphere.services.core.security.auth.modules.impl.AuthModuleEntry;
import org.gridlab.gridsphere.services.core.security.auth.modules.impl.descriptor.AuthModulesDescriptor;
import org.gridlab.gridsphere.services.core.security.auth.modules.impl.descriptor.AuthModuleCollection;
import org.gridlab.gridsphere.services.core.security.auth.modules.impl.descriptor.AuthModuleDefinition;
import org.gridlab.gridsphere.services.core.user.LoginService;
import org.gridlab.gridsphere.services.core.user.LoginUserModule;
import org.gridlab.gridsphere.services.core.user.UserSessionManager;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfig;

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

    private UserSessionManager userSessionManager = UserSessionManager.getInstance();
    private PortletLog log = SportletLog.getInstance(LoginServiceImpl.class);
    private static boolean inited = false;
    private static Map authModules = new HashMap();
    private static Map activeAuthModules = new HashMap();
    private static LoginUserModule activeLoginModule = null;

    private PersistenceManagerRdbms pm = null;

    private String authMappingPath = GridSphereConfig.getServletContext().getRealPath("/WEB-INF/mapping/auth-modules-mapping.xml");
    private String authModulesPath = GridSphereConfig.getServletContext().getRealPath("/WEB-INF/authmodules.xml");

    public LoginServiceImpl() {
    }

    public void addActiveAuthModule(User user, LoginAuthModule authModule) {
        if (!hasActiveAuthModule(user, authModule.getClass().getName())) {
            AuthModuleEntry entry = new AuthModuleEntry();
            entry.setUserId(user.getID());
            entry.setModuleClassName(authModule.getClass().getName());
            entry.setAttributes(authModule.getAttributes());
            try {
                pm.create(entry);
            } catch (PersistenceManagerException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean hasActiveAuthModule(User user, String moduleClassName) {
        AuthModuleEntry authMod = null;
        try {
            String oql = "from " + AuthModuleEntry.class.getName() +
                    " as authentry where authentry.UserId='" + user.getID() + "'" +
                    " and authentry.ModuleClassName='" + moduleClassName + "'";
            log.debug("Query with " + oql);
            authMod = (AuthModuleEntry) pm.restore(oql);
        } catch (PersistenceManagerException e) {
            e.printStackTrace();
        }
        return (authMod != null) ? true : false;
    }

    public void removeActiveAuthModule(User user, String moduleClassName) {
        try {
            String oql = "from " + AuthModuleEntry.class.getName() +
                    " as authentry where authentry.UserId='" + user.getID() + "'" +
                    " and authentry.ModuleClassName='" + moduleClassName + "'";
            log.debug("Query with " + oql);
            pm.deleteList(oql);
        } catch (PersistenceManagerException e) {
            e.printStackTrace();
        }
    }

    public List getActiveAuthModules(User user) {
        List result = null;
        try {
            String oql = "from " + AuthModuleEntry.class.getName() + " as authentry where authentry.UserId='" + user.getID() + "'";
            log.debug("Query with " + oql);
            result = pm.restoreList(oql);
        } catch (PersistenceManagerException e) {
            e.printStackTrace();
        }
        // now convert AuthModuleEntry into the LoginAuthModule concrete class
        List mods = new Vector();
        if (result != null) {
            Iterator it = result.iterator();
            while (it.hasNext()) {
                AuthModuleEntry entry = (AuthModuleEntry) it.next();
                String className = entry.getModuleClassName();
                System.err.println("found a class=" + className);
                if (activeAuthModules.containsKey(className)) mods.add(activeAuthModules.get(className));
            }
        }
        if (mods.isEmpty()) {
            Iterator it = activeAuthModules.values().iterator();
            while (it.hasNext()) {
                LoginAuthModule module = (LoginAuthModule)it.next();                
                System.err.println("found nothing-- adding " + module.getModuleName());
                this.addActiveAuthModule(user, module);
                mods.add(module);
            }
        }
        return mods;
    }

    public List getSupportedAuthModules() {
        List mods = new ArrayList(authModules.size());
        Iterator it = authModules.values().iterator();
        while (it.hasNext()) {
            mods.add(it.next());
        }
        return mods;
    }

    public LoginUserModule getActiveLoginModule() {
        return activeLoginModule;
    }

    public void setActiveLoginModule(LoginUserModule loginModule) {
        activeLoginModule = loginModule;
    }

    public List getActiveUserIds() {
        return userSessionManager.getUserIds();
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

            String loginClassName = config.getInitParameter("LOGIN_MODULE");
            try {
                PortletServiceFactory factory = SportletServiceFactory.getInstance();
                Class loginModClass = Class.forName(loginClassName);
                activeLoginModule = (LoginUserModule) factory.createPortletService(loginModClass, config.getServletContext(), true);
            } catch (ClassNotFoundException e) {
                log.error("Unable to create class from class name: " + loginClassName, e);
            } catch (PortletServiceNotFoundException e) {
                log.error("Unable to get service from portlet service factory: " + loginClassName, e);
            }
            log.debug("Created a login module service: " + loginClassName);

            loadAuthModules();

            /*
            passwdModule = new PasswordAuthModule("PASSWORD_AUTH_MODULE");
            authModules.put("PASSWORD_AUTH_MODULE", passwdModule);

            Enumeration enum = config.getInitParameterNames();
            while (enum.hasMoreElements()) {
                String moduleName = (String) enum.nextElement();
                if (moduleName.equals("LOGIN_MODULE")) {
                    String loginClassName = config.getInitParameter(moduleName);
                    try {
                        PortletServiceFactory factory = SportletServiceFactory.getInstance();
                        Class loginModClass = Class.forName(loginClassName);
                        activeLoginModule = (LoginUserModule) factory.createPortletService(loginModClass, config.getServletContext(), true);
                    } catch (ClassNotFoundException e) {
                        log.error("Unable to create class from class name: " + loginClassName, e);
                    } catch (PortletServiceNotFoundException e) {
                        log.error("Unable to get service from portlet service factory: " + loginClassName, e);
                    }
                    log.debug("Created a login module service: " + loginClassName);
                } else {
                    String authClassName = config.getInitParameter(moduleName);
                    LoginAuthModule authModule = createNewAuthModule(moduleName, authClassName);
                    if (authModule != null) authModules.put(moduleName, authModule);
                }
            }
            LoginAuthModule activeModule = (LoginAuthModule) authModules.get("PASSWORD_AUTH_MODULE");
            activeAuthModules.add(activeModule);
            */
            inited = true;
        }
    }

    private void loadAuthModules() {

        AuthModulesDescriptor desc = null;
        try {
            desc = new AuthModulesDescriptor(authModulesPath, authMappingPath);

            AuthModuleCollection coll = desc.getCollection();
            List modList = coll.getAuthModulesList();
            Iterator it = modList.iterator();
            log.info("loading auth modules:");
            while (it.hasNext()) {
                AuthModuleDefinition def = (AuthModuleDefinition)it.next();
                log.info(def.toString());
                String modClassName = def.getModuleImplementation();
                Class c = Class.forName(modClassName);
                Class[] parameterTypes = new Class[]{AuthModuleDefinition.class};
                Object[] obj = new Object[]{def};
                Constructor con = c.getConstructor(parameterTypes);
                LoginAuthModule authModule = (LoginAuthModule) con.newInstance(obj);
                authModules.put(def.getModuleName(), authModule);
                if (authModule.isModuleActive()) activeAuthModules.put(modClassName, authModule);
            }
        } catch (Exception e) {
            log.error("Error loading auth module!", e);
        }
    }

    /**
     * The destroy method is invoked by the portlet container to destroy a portlet service.
     * This method must free all resources allocated to the portlet service.
     */
    public void destroy() {
    }

    /**
     * Login a user with the given login name and password.
     * Returns the associated user if login succeeds.
     * Throws an AuthenticationException if user cannot authenticate fails.
     * Throws an AuthorizationException if user is not authroized, no account exists, etc.
     *
     * @param loginName     the login name
     * @param loginPassword The login password.
     * @return User The associated user.
     * @throws AuthorizationException if login unsuccessful
     */
    public User login(String loginName, String loginPassword)
            throws AuthenticationException, AuthorizationException {
        if ((loginName == null) || (loginPassword == null)) {
            throw new AuthorizationException("Username or password is blank");
        }

        // first get user
        User user = activeLoginModule.getLoggedInUser(loginName);

        if (user == null) throw new AuthorizationException("User " + loginName + " does not exist");

        // second invoke the appropriate auth module

        List modules = this.getActiveAuthModules(user);


        Collections.sort(modules);
        AuthorizationException authEx = null;

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
            } catch (AuthorizationException e) {
                authEx = e;
            }
            if (success) break;
        }
        if (!success) throw authEx;

        return user;
    }


}
