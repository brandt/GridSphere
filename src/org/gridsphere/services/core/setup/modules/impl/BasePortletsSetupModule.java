package org.gridsphere.services.core.setup.modules.impl;

import org.gridsphere.services.core.setup.modules.PortletsSetupModule;
import org.gridsphere.services.core.setup.modules.impl.descriptor.PortletsSetupModuleDefinition;
import org.gridsphere.services.core.setup.modules.impl.descriptor.PortletsSetupModuleStateDescriptor;
import org.gridsphere.portlet.service.spi.impl.descriptor.ConfigParam;
import org.gridsphere.portlet.service.spi.impl.descriptor.Description;
import org.gridsphere.portletcontainer.impl.descriptor.PortletDefinition;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author <a href="mailto:docentt@man.poznan.pl">Tomasz Kuczynski</a>, PSNC
 * @version $Id$
 */
public abstract class BasePortletsSetupModule implements PortletsSetupModule, Comparable {

    protected Map attributes = new HashMap();
    protected PortletsSetupModuleDefinition moduleDef = null;
    protected boolean prePortletsInitializationPhaseProcessed = false;
    protected boolean postPortletsInitializationPhaseProcessed = false;

    public BasePortletsSetupModule(PortletsSetupModuleDefinition moduleDef) {
        this.moduleDef = moduleDef;
        List configList = moduleDef.getConfigParamList();
        Iterator it = configList.iterator();
        while (it.hasNext()) {
            ConfigParam param = (ConfigParam)it.next();
            attributes.put(param.getParamName(), param.getParamValue());
        }
        if(!moduleDef.getPrePortletInitializationModule())
            prePortletsInitializationPhaseProcessed = true;
        if(!moduleDef.getPostPortletInitializationModule())
            postPortletsInitializationPhaseProcessed = true;
    }

    public void invokePrePortletInitialization(HttpServletRequest request, PortletDefinition portletDefinition) throws IllegalArgumentException {
        if(moduleDef.getPrePortletInitializationModule())
            throw new IllegalArgumentException("Not implemented !!!");
        throw new IllegalArgumentException("Not supported");
    }

    public void invokePostPortletInitialization(HttpServletRequest request) throws IllegalArgumentException {
        if(moduleDef.getPostPortletInitializationModule())
            throw new IllegalArgumentException("Not implemented !!!");
        throw new IllegalArgumentException("Not supported");
    }

    public PortletsSetupModuleStateDescriptor getPrePortletInitializationModuleStateDescriptor(PortletDefinition portletDefinition) throws IllegalArgumentException {
        if(moduleDef.getPrePortletInitializationModule())
            throw new IllegalArgumentException("Not implemented !!!");
        throw new IllegalArgumentException("Not supported");
    }

    public PortletsSetupModuleStateDescriptor getPostPortletInitializationModuleStateDescriptor() throws IllegalArgumentException {
        if(moduleDef.getPostPortletInitializationModule())
            throw new IllegalArgumentException("Not implemented !!!");
        throw new IllegalArgumentException("Not supported");
    }

    public String getAttribute(String name) {
        return (String) attributes.get(name);
    }

    public Map getAttributes() {
        return attributes;
    }

    public void setAttributes(Map attributes) {
        this.attributes = attributes;
    }

    public String getModuleName() {
        return moduleDef.getModuleName();
    }

    public String getContextName() {
        return moduleDef.getContextName();
    }

    public String getPortletName() {
        return moduleDef.getPortletName();
    }

    public boolean isPrePortletsInitializationModule() {
        return moduleDef.getPrePortletInitializationModule();
    }

    public boolean isPostPortletsInitializationModule() {
        return moduleDef.getPostPortletInitializationModule();
    }

    public String getModuleError(String key, Locale locale) {
        List modErrs = moduleDef.getModuleErrors();
        if (locale == null) throw new IllegalArgumentException("locale is NULL");
        Iterator it = modErrs.iterator();
        String defTitle = null;
        while (it.hasNext()) {
            Description t = (Description) it.next();
            if (t.getKey().equals(key)) {
                if (t.getLang() == null) t.setLang(Locale.ENGLISH.getLanguage());
                if (locale.getLanguage().equals(new Locale(t.getLang(), "", "").getLanguage())) return t.getText();
                if (t.getLang().equals(Locale.ENGLISH.getLanguage())) defTitle = t.getText();
            }
        }
        return defTitle;
    }

    public String getModuleDescription(Locale locale) {
        List modDescs = moduleDef.getModuleDescriptions();
        if (locale == null) throw new IllegalArgumentException("locale is NULL");
        Iterator it = modDescs.iterator();
        String defTitle = "";
        while (it.hasNext()) {
            Description t = (Description) it.next();
            if (t.getLang() == null) t.setLang(Locale.ENGLISH.getLanguage());
            if (locale.getLanguage().equals(new Locale(t.getLang(), "", "").getLanguage())) return t.getText();
            if (t.getLang().equals(Locale.ENGLISH.getLanguage())) defTitle = t.getText();
        }
        return defTitle;
    }

    public int getModulePriority() {
        return moduleDef.getModulePriority();
    }

    public void setModulePriority(int priority) {
        moduleDef.setModulePriority(priority);
    }

    public boolean isModuleActive() {
        return moduleDef.getModuleActive();
    }

    public void setModuleActive(boolean active) {
        moduleDef.setModuleActive(active);
    }

    public PortletsSetupModuleDefinition getModuleDef() {
        return moduleDef;
    }

    public void setModuleDef(PortletsSetupModuleDefinition moduleDef) {
        this.moduleDef = moduleDef;
    }

    public boolean isPrePortletsInitializationPhaseProcessed() {
        return prePortletsInitializationPhaseProcessed;
    }

    public void setPrePortletsInitializationPhaseProcessed(boolean prePortletsInitializationPhaseProcessed) {
        this.prePortletsInitializationPhaseProcessed = prePortletsInitializationPhaseProcessed;
    }

    public boolean isPostPortletsInitializationPhaseProcessed() {
        return postPortletsInitializationPhaseProcessed;
    }

    public void setPostPortletsInitializationPhaseProcessed(boolean postPortletsInitializationPhaseProcessed) {
        this.postPortletsInitializationPhaseProcessed = postPortletsInitializationPhaseProcessed;
    }

    public int compareTo(Object obj) {
        if ((obj != null) && (obj instanceof PortletsSetupModule)) {
            PortletsSetupModule l = (PortletsSetupModule) obj;
            if (this.moduleDef.getModulePriority() < l.getModulePriority()) {
                return -1;
            } else if (this.moduleDef.getModulePriority() > l.getModulePriority()) {
                return 1;
            } else {
                return 0;
            }
        }
        throw new ClassCastException("Unable to compare supplied object to this module");
    }
}
