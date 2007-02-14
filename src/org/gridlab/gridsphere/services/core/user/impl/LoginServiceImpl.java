/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.user.impl;

import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerFactory;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerRdbms;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfig;
import org.gridlab.gridsphere.services.core.portal.PortalConfigService;
import org.gridlab.gridsphere.services.core.portal.PortalConfigSettings;
import org.gridlab.gridsphere.services.core.security.auth.modules.LoginAuthModule;
import org.gridlab.gridsphere.services.core.security.auth.modules.impl.descriptor.AuthModuleCollection;
import org.gridlab.gridsphere.services.core.security.auth.modules.impl.descriptor.AuthModuleDefinition;
import org.gridlab.gridsphere.services.core.security.auth.modules.impl.descriptor.AuthModulesDescriptor;
import org.gridlab.gridsphere.services.core.user.LoginService;
import org.gridlab.gridsphere.services.core.user.LoginUserModule;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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



}
