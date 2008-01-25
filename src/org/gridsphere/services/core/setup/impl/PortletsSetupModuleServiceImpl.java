package org.gridsphere.services.core.setup.impl;

import org.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridsphere.portlet.impl.SportletProperties;
import org.gridsphere.services.core.setup.PortletsSetupModuleService;
import org.gridsphere.services.core.setup.modules.PortletsSetupModule;
import org.gridsphere.services.core.setup.modules.impl.descriptor.PortletsSetupModulesDescriptor;
import org.gridsphere.services.core.setup.modules.impl.descriptor.PortletsSetupModuleCollection;
import org.gridsphere.services.core.setup.modules.impl.descriptor.PortletsSetupModuleDefinition;
import org.gridsphere.services.core.setup.modules.impl.descriptor.PortletsSetupModuleStateDescriptor;
import org.gridsphere.portletcontainer.impl.descriptor.PortletDefinition;
import org.gridsphere.portletcontainer.impl.PortletWebApplicationImpl;
import org.gridsphere.portletcontainer.PortletWebApplication;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.portlet.Portlet;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.net.URL;
import java.lang.reflect.Constructor;

/**
 * @author <a href="mailto:docentt@man.poznan.pl">Tomasz Kuczynski</a>, PSNC
 * @version $Id$
 */
public class PortletsSetupModuleServiceImpl implements PortletServiceProvider, PortletsSetupModuleService {
    private Log log = LogFactory.getLog(PortletsSetupModuleServiceImpl.class);

    private List<PortletsSetupModule> prePortletInitializationPortletsSetupModules = new ArrayList<PortletsSetupModule>();
    private List<PortletsSetupModule> postPortletInitializationPortletsSetupModules = new ArrayList<PortletsSetupModule>();
    private Map<String, Map<String, PortletDefinition>> portletsDefinitions = new HashMap<String, Map<String, PortletDefinition>>();
    private PortletsSetupModule processedModule = null;

    private boolean prePortletsInitializingSetupDone = false;

    private boolean postPortletsInitializingSetupDone = false;

    private URL setupMappingStream = getClass().getResource("/org/gridsphere/services/core/setup/modules/impl/descriptor/portlets-setup-modules-mapping.xml");

    public PortletsSetupModuleServiceImpl() {
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
    }

    public void loadPortletsSetupModules(String setupModsPath, PortletWebApplication portletWebApplication, Map<String, Portlet> portlets, ClassLoader classloader) {
        PortletsSetupModulesDescriptor desc;
        try {
            desc = new PortletsSetupModulesDescriptor(setupModsPath, portletWebApplication.getWebApplicationName(), setupMappingStream);

            List<PortletsSetupModule> tmpPrePortletInitializationPortletsSetupModules = new ArrayList<PortletsSetupModule>();
            List<PortletsSetupModule> tmpPostPortletInitializationPortletsSetupModules = new ArrayList<PortletsSetupModule>();
            Set<String> prePortletsInitializationPortlets = new HashSet<String>();
            PortletsSetupModuleCollection coll = desc.getCollection();
            List modList = coll.getPortletsSetupModulesList();
            Iterator it = modList.iterator();
            log.info("loading portlets setup modules:");
            while (it.hasNext()) {
                PortletsSetupModuleDefinition def = (PortletsSetupModuleDefinition) it.next();
                log.info(def.toString());
                String modClassName = def.getModuleImplementation();
                Class c = Class.forName(modClassName, true, classloader);
                Class[] parameterTypes = new Class[]{PortletsSetupModuleDefinition.class};
                Object[] obj = new Object[]{def};
                Constructor con = c.getConstructor(parameterTypes);
                PortletsSetupModule portletsSetupModule = (PortletsSetupModule) con.newInstance(obj);

                if(portletsSetupModule.isPrePortletsInitializationModule())
                    tmpPrePortletInitializationPortletsSetupModules.add(portletsSetupModule);
                if(portletsSetupModule.isPostPortletsInitializationModule())
                    tmpPostPortletInitializationPortletsSetupModules.add(portletsSetupModule);

                if (portletsSetupModule.isPrePortletsInitializationModule() && null != portletsSetupModule.getPortletName() && !"".equals(portletsSetupModule.getPortletName())) {
                    prePortletsInitializationPortlets.add(portletsSetupModule.getPortletName());
                }
            }

            Collections.sort(tmpPrePortletInitializationPortletsSetupModules);
            Collections.sort(tmpPostPortletInitializationPortletsSetupModules);
            prePortletInitializationPortletsSetupModules.addAll(tmpPrePortletInitializationPortletsSetupModules);
            postPortletInitializationPortletsSetupModules.addAll(tmpPostPortletInitializationPortletsSetupModules);

            if (!prePortletsInitializationPortlets.isEmpty()) {
                HashMap<String, PortletDefinition> contextPortletDefinitions = new HashMap<String, PortletDefinition>();
                Set set = portlets.keySet();
                it = set.iterator();
                while (it.hasNext()) {
                    String portletName = (String) it.next();
                    if (prePortletsInitializationPortlets.contains(portletName)) {
                        PortletDefinition portletDef = ((PortletWebApplicationImpl) portletWebApplication).getPortletDefinition(portletName);
                        contextPortletDefinitions.put(portletName, portletDef);
                    }
                }
                portletsDefinitions.put(portletWebApplication.getWebApplicationName(), contextPortletDefinitions);
            }
        } catch (Exception e) {
            log.error("Error loading portlets setup module!", e);
        }

    }

    public PortletsSetupModuleStateDescriptor getModuleStateDescriptor() throws IllegalAccessException{
        return getProcessedPortletsSetupModule().getModuleStateDescriptor(!prePortletsInitializingSetupDone? SportletProperties.PORTLET_SETUP_TYPE_PRE : SportletProperties.PORTLET_SETUP_TYPE_POST);
    }

    public void invokePrePortletInitialization(HttpServletRequest request) throws IllegalArgumentException, IllegalAccessException {
        PortletsSetupModule portletsSetupModule = getProcessedPortletsSetupModule();
        String contextName = portletsSetupModule.getContextName();
        String portletName = portletsSetupModule.getPortletName();
        PortletDefinition portletDefinition = null;
        if(null != portletName && !"".equals(portletName))
            portletDefinition = portletsDefinitions.get(contextName).get(portletName);
        portletsSetupModule.invokePrePortletInitialization(request, portletDefinition);
        if(portletsSetupModule.isPrePortletsInitializationPhaseProcessed() && prePortletInitializationPortletsSetupModules.isEmpty()){
            setPrePortletsInitializingSetupDone();
        }
    }

    public void invokePostPortletInitialization(HttpServletRequest request) throws IllegalArgumentException, IllegalAccessException {
        PortletsSetupModule portletsSetupModule = getProcessedPortletsSetupModule();
        portletsSetupModule.invokePostPortletInitialization(request);
        if(portletsSetupModule.isPostPortletsInitializationPhaseProcessed() && postPortletInitializationPortletsSetupModules.isEmpty()){
            setPostPortletsInitializingSetupDone();
        }
    }

    private PortletsSetupModule getProcessedPortletsSetupModule() throws IllegalAccessException{
        if((null == processedModule || (!prePortletsInitializingSetupDone && processedModule.isPrePortletsInitializationPhaseProcessed()) || (prePortletsInitializingSetupDone && processedModule.isPostPortletsInitializationPhaseProcessed())) && !pickNextModule())
            throw new IllegalAccessException();
        return processedModule;
    }

    private boolean pickNextModule(){
        if(!prePortletsInitializingSetupDone){
            processedModule = prePortletInitializationPortletsSetupModules.get(0);
            prePortletInitializationPortletsSetupModules.remove(processedModule);
        }else if(!postPortletsInitializingSetupDone){
            processedModule = postPortletInitializationPortletsSetupModules.get(0);
            postPortletInitializationPortletsSetupModules.remove(processedModule);
        }else{
            return false;
        }
        return true;
    }

    public void destroy() {
        portletsDefinitions.clear();
    }

    public boolean isPrePortletsInitializingSetupDone() {
        return prePortletsInitializingSetupDone;
    }

    public void skipPrePortletsInitializingSetup() {
        setPrePortletsInitializingSetupDone();
    }

    private void setPrePortletsInitializingSetupDone() {
        if(isPrePortletsInitializingSetupDone())
            return;
        if(null != processedModule && !processedModule.isPrePortletsInitializationPhaseProcessed()){
            processedModule.setPrePortletsInitializationPhaseProcessed(true);
        }
        while(!prePortletInitializationPortletsSetupModules.isEmpty()){
            pickNextModule();
            processedModule.setPrePortletsInitializationPhaseProcessed(true);
        }
        Iterator<String> webAppIterator = portletsDefinitions.keySet().iterator();
        while (webAppIterator.hasNext()) {
            String webApp = webAppIterator.next();
            portletsDefinitions.get(webApp).clear();
        }
        portletsDefinitions.clear();        
        this.prePortletsInitializingSetupDone = true;
    }

    public boolean isPostPortletsInitializingSetupDone() {
        return postPortletsInitializingSetupDone;
    }

    public void skipPostPortletsInitializingSetup() {
        setPostPortletsInitializingSetupDone();
    }

    public void setPostPortletsInitializingSetupDone() {
        if(isPostPortletsInitializingSetupDone())
            return;
        PortletsSetupModule tmpPortletsSetupModule = processedModule;
        if(null != processedModule && !processedModule.isPostPortletsInitializationPhaseProcessed()){
            processedModule.setPostPortletsInitializationPhaseProcessed(true);
        }
        while(!postPortletInitializationPortletsSetupModules.isEmpty()){
            pickNextModule();
            processedModule.setPostPortletsInitializationPhaseProcessed(true);
        }
        this.postPortletsInitializingSetupDone = true;
        if(null != tmpPortletsSetupModule && !this.prePortletsInitializingSetupDone && tmpPortletsSetupModule.isPrePortletsInitializationModule() && !tmpPortletsSetupModule.isPrePortletsInitializationPhaseProcessed())
            processedModule = tmpPortletsSetupModule;
    }
}
