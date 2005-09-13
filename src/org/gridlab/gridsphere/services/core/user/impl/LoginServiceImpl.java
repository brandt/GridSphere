/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.user.impl;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.core.security.auth.AuthorizationException;
import org.gridlab.gridsphere.services.core.security.auth.AuthenticationException;
import org.gridlab.gridsphere.services.core.security.auth.modules.LoginAuthModule;
import org.gridlab.gridsphere.services.core.security.auth.modules.impl.AuthModuleEntry;
import org.gridlab.gridsphere.services.core.security.auth.modules.impl.descriptor.AuthModulesDescriptor;
import org.gridlab.gridsphere.services.core.security.auth.modules.impl.descriptor.AuthModuleCollection;
import org.gridlab.gridsphere.services.core.security.auth.modules.impl.descriptor.AuthModuleDefinition;
import org.gridlab.gridsphere.services.core.user.LoginService;
import org.gridlab.gridsphere.services.core.user.UserSessionManager;
import org.gridlab.gridsphere.services.core.user.UserManagerService;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfig;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.lang.reflect.Constructor;
import java.util.*;

/**
 * The <code>LoginService</code> is the primary interface that defines the login method used to obtain a
 * <code>User</code> from a username and password. The <code>LoginService</code> is configured
 * dynamically at run-time with login authorization modules. By default the PASSWORD_AUTH_MODULE is
 * selected which uses the GridSphere database to store passwords. Other authorization modules
 * can use external directory servers such as LDAP, etc
 */
public class LoginServiceImpl extends HibernateDaoSupport implements LoginService {


    private PortletLog log = SportletLog.getInstance(LoginServiceImpl.class);
    private static Map authModules = new HashMap();
    private static Map activeAuthModules = new HashMap();
    private UserManagerService userManagerService = null;
    private UserSessionManager userSessionManager = null;

    private String authMappingPath = GridSphereConfig.getServletContext().getRealPath("/WEB-INF/mapping/auth-modules-mapping.xml");
    private String authModulesPath = GridSphereConfig.getServletContext().getRealPath("/WEB-INF/authmodules.xml");

    public LoginServiceImpl() {

    }

    public void init() {
        System.err.println("in init of loaginserviceimpl");
        loadAuthModules(authModulesPath, Thread.currentThread().getContextClassLoader());
    }

    public void setUserSessionManager(UserSessionManager userSessionManager) {
        this.userSessionManager = userSessionManager;
    }

    public void setUserManagerService(UserManagerService userManagerService) {
        this.userManagerService = userManagerService;
    }

    public void addActiveAuthModule(User user, LoginAuthModule authModule) {
        if (!hasActiveAuthModule(user, authModule.getClass().getName())) {
            AuthModuleEntry entry = new AuthModuleEntry();
            entry.setUserId(user.getID());
            entry.setModuleClassName(authModule.getClass().getName());
            entry.setAttributes(authModule.getAttributes());
            this.getHibernateTemplate().saveOrUpdate(entry);
        }
    }

    public LoginAuthModule getAuthModule(String moduleClassName) {
        return (LoginAuthModule)authModules.get(moduleClassName);
    }

    public List getAuthModules() {
        List vals = new ArrayList();
        Iterator it = authModules.values().iterator();
        while (it.hasNext()) {
            vals.add(it.next());
        }
        return vals;
    }

    public boolean hasActiveAuthModule(User user, String moduleClassName) {
        AuthModuleEntry authMod = null;
        String oql = "from " + AuthModuleEntry.class.getName() +
                " as authentry where authentry.UserId='" + user.getID() + "'" +
                " and authentry.ModuleClassName='" + moduleClassName + "'";
        log.debug("Query with " + oql);
        List mods = this.getHibernateTemplate().find(oql);
        if ((mods != null) && (!mods.isEmpty())) {
            authMod = (AuthModuleEntry)mods.get(0);
        }
        return (authMod != null);
    }

    public void removeActiveAuthModule(User user, String moduleClassName) {
        String oql = "from " + AuthModuleEntry.class.getName() +
                " as authentry where authentry.UserId='" + user.getID() + "'" +
                " and authentry.ModuleClassName='" + moduleClassName + "'";
        log.debug("Query with " + oql);
        this.getHibernateTemplate().delete(oql);
    }

    public List getActiveAuthModules(User user) {
        List result = null;

        String oql = "from " + AuthModuleEntry.class.getName() + " as authentry where authentry.UserId='" + user.getID() + "'";
        log.debug("Query with " + oql);
        result = this.getHibernateTemplate().find(oql);

        // now convert AuthModuleEntry into the LoginAuthModule concrete class
        List mods = new Vector();
        if (result != null) {
            Iterator it = result.iterator();
            while (it.hasNext()) {
                AuthModuleEntry entry = (AuthModuleEntry) it.next();
                String className = entry.getModuleClassName();
                //System.err.println("found a class=" + className);
                if (activeAuthModules.containsKey(className)) mods.add(activeAuthModules.get(className));
            }
        }
        if (mods.isEmpty()) {
            Iterator it = activeAuthModules.values().iterator();
            while (it.hasNext()) {
                LoginAuthModule module = (LoginAuthModule)it.next();
                //System.err.println("found nothing-- adding " + module.getModuleName());
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

    public List getActiveUserIds() {
        return userSessionManager.getUserIds();
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
                    this.getHibernateTemplate().saveOrUpdate(def);
                }
                Class c = Class.forName(modClassName, true, classloader);
                Class[] parameterTypes = new Class[]{AuthModuleDefinition.class};
                Object[] obj = new Object[]{def};
                Constructor con = c.getConstructor(parameterTypes);
                LoginAuthModule authModule = (LoginAuthModule) con.newInstance(obj);
                authModules.put(modClassName, authModule);
                if (authModule.isModuleActive()) activeAuthModules.put(modClassName, authModule);
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
                this.getHibernateTemplate().update(am);
                authModules.put(am.getModuleImplementation(), authModule);
                // in case old auth module was active and new one is not remove it first then reinsert if active
                activeAuthModules.remove(am.getModuleImplementation());
                if (authModule.isModuleActive()) activeAuthModules.put(am.getModuleImplementation(), authModule);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private AuthModuleDefinition getAuthModuleDefinition(String moduleName) {
        AuthModuleDefinition am = null;
        List mods = this.getHibernateTemplate().find("select authmodule from " + AuthModuleDefinition.class.getName() +
                " authmodule where authmodule.ModuleName='" +
                moduleName + "'");
        if ((mods != null) && (!mods.isEmpty())) {
            am = (AuthModuleDefinition)mods.get(0);
        }
        return am;
    }

    public List getAuthModuleDefinitions() {
        List mods = null;

        mods = this.getHibernateTemplate().find("select authmod from "
                + AuthModuleDefinition.class.getName()
                + " authmod ");

        return mods;
    }

    public User login(PortletRequest req)
            throws AuthenticationException, AuthorizationException {
        String loginName = req.getParameter("username");
        String loginPassword = req.getParameter("password");

        if ((loginName == null) || (loginPassword == null)) {
            throw new AuthorizationException(getLocalizedText(req, "LOGIN_AUTH_BLANK"));
        }

        // first get user
        User user = userManagerService.getLoggedInUser(loginName);

        if (user == null) throw new AuthorizationException(getLocalizedText(req, "LOGIN_AUTH_NOUSER"));

        String accountStatus = (String)user.getAttribute(User.DISABLED);
        if ((accountStatus != null) && ("TRUE".equalsIgnoreCase(accountStatus)))
            throw new AuthorizationException(getLocalizedText(req, "LOGIN_AUTH_DISABLED"));

        // second invoke the appropriate auth module

        List modules = this.getActiveAuthModules(user);

        Collections.sort(modules);
        AuthenticationException authEx = null;

        Iterator it = modules.iterator();
        log.debug("in login: Active modules are: ");

        // loop thru all modules
        // if auth module is required then it must be executed
        boolean success = false;
        while (it.hasNext()) {
            LoginAuthModule mod = (LoginAuthModule) it.next();
            log.debug(mod.getModuleName());
            try {
                if (success) {
                    if (mod.isModuleRequired()) {
                        success = false;
                        mod.checkAuthentication(user, loginPassword);
                    }
                } else {
                    mod.checkAuthentication(user, loginPassword);
                }
                success = true;
            } catch (AuthenticationException e) {
                String errMsg = mod.getModuleError(e.getMessage(), req.getLocale());
                if (errMsg != null) {
                    authEx = new AuthenticationException(errMsg);
                } else {
                    authEx = e;
                }
            }
        }
        if (!success) throw authEx;

        return user;
    }

    protected String getLocalizedText(PortletRequest req, String key) {
        Locale locale = req.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("gridsphere.resources.Portlet", locale);
        return bundle.getString(key);
    }

}
