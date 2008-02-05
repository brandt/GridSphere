package org.gridsphere.services.core.setup.modules;

import org.gridsphere.services.core.setup.modules.impl.descriptor.PortletsSetupModuleStateDescriptor;
import org.gridsphere.portletcontainer.impl.descriptor.PortletDefinition;

import javax.servlet.http.HttpServletRequest;
import javax.portlet.Portlet;
import java.util.Map;
import java.util.Locale;

/**
 * @author <a href="mailto:docentt@man.poznan.pl">Tomasz Kuczynski</a>, PSNC
 * @version $Id$
 */
public interface PortletsSetupModule extends Comparable {
    public void setAttributes(Map attributes);

    public String getAttribute(String name);

    public Map getAttributes();

    public String getModuleName();

    public String getContextName();

    public String getPortletName();

    public String getModuleDefaultJSP(Locale locale);
    
    public String getModuleDescription(Locale locale);

    public String getModuleTitle(Locale locale);

    public boolean isPreInitModule();

    public boolean isPostInitModule();

    public int getModulePriority();

    public void setModulePriority(int priority);

    public boolean isModuleActive();

    public void setModuleActive(boolean isActive);

    public boolean isPreInitPhaseProcessed();

    public void setPreInitPhaseProcessed(boolean isModuleProcessed);

    public boolean isPostInitPhaseProcessed();

    public void setPostInitPhaseProcessed(boolean isModuleProcessed);

    public String getModuleError(String key, Locale locale);

    public void fillPreInitStateDescriptor(PortletsSetupModuleStateDescriptor portletsSetupModuleStateDescriptor, PortletDefinition portletDefinition);
    
    public void fillPostInitStateDescriptor(PortletsSetupModuleStateDescriptor portletsSetupModuleStateDescriptor);

    public void invokePreInit(HttpServletRequest request, PortletDefinition portletDefinition) throws IllegalArgumentException;

    public void invokePostInit(HttpServletRequest request, Portlet portlet) throws IllegalArgumentException;
}
