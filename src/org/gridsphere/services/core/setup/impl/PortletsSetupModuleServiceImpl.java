package org.gridsphere.services.core.setup.impl;

import org.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridsphere.portlet.service.PortletServiceUnavailableException;
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

    private List<PortletsSetupModule> preInitPortletsSetupModules = new ArrayList<PortletsSetupModule>();
    private List<PortletsSetupModule> postInitPortletsSetupModules = new ArrayList<PortletsSetupModule>();
    private Map<String, Map<String, PortletDefinition>> portletsDefinitions = new HashMap<String, Map<String, PortletDefinition>>();
    private Map<String, Map<String, Portlet>> portlets = new HashMap<String, Map<String, Portlet>>();
    private PortletsSetupModule processedModule = null;

    private boolean preInitSetupDone = true;

    private boolean postInitSetupDone = true;

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
            Set<String> preInitPortlets = new HashSet<String>();
            Set<String> postInitPortlets = new HashSet<String>();
            PortletsSetupModuleCollection coll = desc.getCollection();
            List modList = coll.getPortletsSetupModulesList();
            Iterator it = modList.iterator();
            log.info("loading portlets setup modules:");
            while (it.hasNext()) {
                PortletsSetupModuleDefinition def = (PortletsSetupModuleDefinition) it.next();
                log.info(def.toString());
                if(!def.getModuleActive())
                    continue;
                String modClassName = def.getModuleImplementation();
                Class c = Class.forName(modClassName, true, classloader);
                Class[] parameterTypes = new Class[]{PortletsSetupModuleDefinition.class};
                Object[] obj = new Object[]{def};
                Constructor con = c.getConstructor(parameterTypes);
                PortletsSetupModule portletsSetupModule = (PortletsSetupModule) con.newInstance(obj);

                if(portletsSetupModule.isPreInitModule()){
                    tmpPrePortletInitializationPortletsSetupModules.add(portletsSetupModule);
                    preInitSetupDone = false;
                }
                if(portletsSetupModule.isPostInitModule()){
                    tmpPostPortletInitializationPortletsSetupModules.add(portletsSetupModule);
                    postInitSetupDone = false;
                }
                if (portletsSetupModule.isPreInitModule() && null != portletsSetupModule.getPortletName() && !"".equals(portletsSetupModule.getPortletName())) {
                    preInitPortlets.add(portletsSetupModule.getPortletName());
                }
                if (portletsSetupModule.isPostInitModule() && null != portletsSetupModule.getPortletName() && !"".equals(portletsSetupModule.getPortletName())) {
                    postInitPortlets.add(portletsSetupModule.getPortletName());
                }
            }

            Collections.sort(tmpPrePortletInitializationPortletsSetupModules);
            Collections.sort(tmpPostPortletInitializationPortletsSetupModules);
            preInitPortletsSetupModules.addAll(tmpPrePortletInitializationPortletsSetupModules);
            postInitPortletsSetupModules.addAll(tmpPostPortletInitializationPortletsSetupModules);

            if (!preInitPortlets.isEmpty()) {
                HashMap<String, PortletDefinition> contextPortletDefinitions = new HashMap<String, PortletDefinition>();
                Set set = portlets.keySet();
                it = set.iterator();
                while (it.hasNext()) {
                    String portletName = (String) it.next();
                    if (preInitPortlets.contains(portletName)) {
                        PortletDefinition portletDef = ((PortletWebApplicationImpl) portletWebApplication).getPortletDefinition(portletName);
                        contextPortletDefinitions.put(portletName, portletDef);
                    }
                }
                this.portletsDefinitions.put(portletWebApplication.getWebApplicationName(), contextPortletDefinitions);
            }
            if (!postInitPortlets.isEmpty()) {
                HashMap<String, Portlet> contextPortlets = new HashMap<String, Portlet>();
                Set set = portlets.keySet();
                it = set.iterator();
                while (it.hasNext()) {
                    String portletName = (String) it.next();
                    if (postInitPortlets.contains(portletName)) {
                        Portlet portlet = portlets.get(portletName);
                        contextPortlets.put(portletName, portlet);
                    }
                }
                this.portlets.put(portletWebApplication.getWebApplicationName(), contextPortlets);
            }
        } catch (Exception e) {
            log.error("Error loading portlets setup module!", e);
        }

    }

    public PortletsSetupModuleStateDescriptor getModuleStateDescriptor(HttpServletRequest request) throws IllegalStateException {
        PortletsSetupModule portletsSetupModule = getProcessedPortletsSetupModule();
        PortletsSetupModuleStateDescriptor portletsSetupModuleStateDescriptor = new PortletsSetupModuleStateDescriptor(portletsSetupModule);
        portletsSetupModuleStateDescriptor.setTitle(portletsSetupModule.getModuleName());
        portletsSetupModuleStateDescriptor.setDescription(portletsSetupModule.getModuleDescription(request.getLocale()));
        portletsSetupModuleStateDescriptor.setJspFile(portletsSetupModule.getDefaultJSP());
        if(!preInitSetupDone){
            PortletDefinition portletDefinition = getPortletDefinitionForModule(portletsSetupModule);
            portletsSetupModule.fillPreInitStateDescriptor(portletsSetupModuleStateDescriptor, portletDefinition);
        }else{
            portletsSetupModule.fillPostInitStateDescriptor(portletsSetupModuleStateDescriptor);
        }
        return portletsSetupModuleStateDescriptor;
    }

    public void invokePreInit(HttpServletRequest request) throws IllegalArgumentException, IllegalStateException {
        PortletsSetupModule portletsSetupModule = getProcessedPortletsSetupModule();
        PortletDefinition portletDefinition = getPortletDefinitionForModule(portletsSetupModule);
        try {
            portletsSetupModule.invokePreInit(request, portletDefinition);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(portletsSetupModule.getModuleError(e.getMessage(),request.getLocale()));
        }
        if(portletsSetupModule.isPreInitPhaseProcessed() && preInitPortletsSetupModules.isEmpty()){
            setPreInitSetupDone();
        }
    }

    private PortletDefinition getPortletDefinitionForModule(PortletsSetupModule portletsSetupModule){
        PortletDefinition portletDefinition = null;
        String contextName = portletsSetupModule.getContextName();
        String portletName = portletsSetupModule.getPortletName();
        if(null != portletName && !"".equals(portletName))
            portletDefinition = portletsDefinitions.get(contextName).get(portletName);
        return portletDefinition;
    }

    public void invokePostInit(HttpServletRequest request) throws IllegalArgumentException, IllegalStateException {
        PortletsSetupModule portletsSetupModule = getProcessedPortletsSetupModule();
        Portlet portlet = getPortletForModule(portletsSetupModule);
        try {
            portletsSetupModule.invokePostInit(request, portlet);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(portletsSetupModule.getModuleError(e.getMessage(),request.getLocale()));
        }
        if(portletsSetupModule.isPostInitPhaseProcessed() && postInitPortletsSetupModules.isEmpty()){
            setPostInitSetupDone();
        }
    }

    private Portlet getPortletForModule(PortletsSetupModule portletsSetupModule){
        Portlet portlet = null;
        String contextName = portletsSetupModule.getContextName();
        String portletName = portletsSetupModule.getPortletName();
        if(null != portletName && !"".equals(portletName))
            portlet = portlets.get(contextName).get(portletName);
        return portlet;
    }

    private PortletsSetupModule getProcessedPortletsSetupModule() throws IllegalStateException{
        if((null == processedModule || (!preInitSetupDone && processedModule.isPreInitPhaseProcessed()) || (preInitSetupDone && processedModule.isPostInitPhaseProcessed())) && !pickNextModule())
            throw new IllegalStateException();
        return processedModule;
    }

    private boolean pickNextModule(){
        if(!preInitSetupDone){
            processedModule = preInitPortletsSetupModules.get(0);
            preInitPortletsSetupModules.remove(processedModule);
        }else if(!postInitSetupDone){
            processedModule = postInitPortletsSetupModules.get(0);
            postInitPortletsSetupModules.remove(processedModule);
        }else{
            return false;
        }
        return true;
    }

    public void destroy() {
        portletsDefinitions.clear();
    }

    public boolean isPreInitSetupDone() {
        return preInitSetupDone;
    }

    public void skipPreInitSetup() {
        setPreInitSetupDone();
    }

    private void setPreInitSetupDone() {
        if(isPreInitSetupDone())
            return;
        if(null != processedModule && !processedModule.isPreInitPhaseProcessed()){
            processedModule.setPreInitPhaseProcessed(true);
        }
        while(!preInitPortletsSetupModules.isEmpty()){
            pickNextModule();
            processedModule.setPreInitPhaseProcessed(true);
        }
        Iterator<String> webAppIterator = portletsDefinitions.keySet().iterator();
        while (webAppIterator.hasNext()) {
            String webApp = webAppIterator.next();
            portletsDefinitions.get(webApp).clear();
        }
        portletsDefinitions.clear();        
        this.preInitSetupDone = true;
    }

    public boolean isPostInitSetupDone() {
        return postInitSetupDone;
    }

    public void skipPostInitSetup() {
        setPostInitSetupDone();
    }

    public void skipModule() {
        PortletsSetupModule portletsSetupModule = getProcessedPortletsSetupModule();
        if(!preInitSetupDone){
            if(!portletsSetupModule.isPreInitPhaseProcessed()){
                portletsSetupModule.setPreInitPhaseProcessed(true);
                if(preInitPortletsSetupModules.isEmpty())
                    setPreInitSetupDone();
            }
        }else{
            if(!portletsSetupModule.isPostInitPhaseProcessed()){
                portletsSetupModule.setPostInitPhaseProcessed(true);
                if(postInitPortletsSetupModules.isEmpty())
                    setPostInitSetupDone();
            }
        }
    }

    private void setPostInitSetupDone() {
        if(isPostInitSetupDone())
            return;
        PortletsSetupModule tmpPortletsSetupModule = processedModule;
        if(null != processedModule && !processedModule.isPostInitPhaseProcessed()){
            processedModule.setPostInitPhaseProcessed(true);
        }
        while(!postInitPortletsSetupModules.isEmpty()){
            pickNextModule();
            processedModule.setPostInitPhaseProcessed(true);
        }
        Iterator<String> webAppIterator = portlets.keySet().iterator();
        while (webAppIterator.hasNext()) {
            String webApp = webAppIterator.next();
            portlets.get(webApp).clear();
        }
        portlets.clear();
        this.postInitSetupDone = true;
        if(null != tmpPortletsSetupModule && !this.preInitSetupDone && tmpPortletsSetupModule.isPreInitModule() && !tmpPortletsSetupModule.isPreInitPhaseProcessed())
            processedModule = tmpPortletsSetupModule;
    }
}
