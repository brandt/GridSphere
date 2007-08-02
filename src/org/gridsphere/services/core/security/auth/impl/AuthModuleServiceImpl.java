/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: LoginServiceImpl.java 5032 2006-08-17 18:15:06Z novotny $
 */
package org.gridsphere.services.core.security.auth.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridsphere.services.core.persistence.PersistenceManagerException;
import org.gridsphere.services.core.persistence.PersistenceManagerRdbms;
import org.gridsphere.services.core.persistence.PersistenceManagerService;
import org.gridsphere.services.core.security.auth.AuthModuleService;
import org.gridsphere.services.core.security.auth.modules.LoginAuthModule;
import org.gridsphere.services.core.security.auth.modules.impl.descriptor.AuthModuleCollection;
import org.gridsphere.services.core.security.auth.modules.impl.descriptor.AuthModuleDefinition;
import org.gridsphere.services.core.security.auth.modules.impl.descriptor.AuthModulesDescriptor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The <code>AuthModuleService</code> provides the portal with the available authentication
 * modules. By default the PasswordAuthModule is selected which uses the GridSphere database
 * to store passwords. Other authorization modules can use external directory servers such as LDAP, etc
 */
public class AuthModuleServiceImpl implements AuthModuleService, PortletServiceProvider {

    private Log log = LogFactory.getLog(AuthModuleServiceImpl.class);

    private List<LoginAuthModule> authModules = new ArrayList<LoginAuthModule>();

    private PersistenceManagerRdbms pm = null;

    private URL authMappingStream = getClass().getResource("/org/gridsphere/services/core/security/auth/modules/impl/descriptor/auth-modules-mapping.xml");

    public AuthModuleServiceImpl() {
    }

    public List<LoginAuthModule> getAuthModules() {
        return authModules;
    }

    public List<LoginAuthModule> getActiveAuthModules() {
        List<LoginAuthModule> activeMods = new ArrayList<LoginAuthModule>();
        for (LoginAuthModule authModule : authModules) {
            if (authModule.isModuleActive()) activeMods.add(authModule);
        }
        return activeMods;
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
        PersistenceManagerService pmservice = (PersistenceManagerService) PortletServiceFactory.createPortletService(PersistenceManagerService.class, true);
        pm = pmservice.createGridSphereRdbms();
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
                AuthModuleDefinition def = (AuthModuleDefinition) it.next();
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
                try {
                    Class[] parameterTypes = new Class[]{AuthModuleDefinition.class};
                    Object[] obj = new Object[]{def};
                    Constructor con = c.getConstructor(parameterTypes);
                    LoginAuthModule authModule = (LoginAuthModule) con.newInstance(obj);
                    authModules.add(authModule);
                } catch (NoSuchMethodException e) {
                    //for modules with Object (instead of AuthModuleDefinition) parametered constructor
                    Class[] parameterTypes = new Class[]{Object.class};
                    Object[] obj = new Object[]{def};
                    Constructor con = c.getConstructor(parameterTypes);
                    LoginAuthModule authModule = (LoginAuthModule) con.newInstance(obj);
                    authModules.add(authModule);
                }
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
            am = (AuthModuleDefinition) pm.restore("select authmodule from " + AuthModuleDefinition.class.getName() +
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
