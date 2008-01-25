package org.gridsphere.services.core.setup.modules;

import org.gridsphere.services.core.setup.modules.impl.descriptor.PortletsSetupModuleStateDescriptor;
import org.gridsphere.portletcontainer.impl.descriptor.PortletDefinition;

import javax.servlet.http.HttpServletRequest;
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

    public String getModuleDescription(Locale locale);

    public boolean isPrePortletsInitializationModule();

    public boolean isPostPortletsInitializationModule();

    public int getModulePriority();

    public void setModulePriority(int priority);

    public boolean isModuleActive();

    public void setModuleActive(boolean isActive);

    public boolean isPrePortletsInitializationPhaseProcessed();

    public void setPrePortletsInitializationPhaseProcessed(boolean isModuleProcessed);

    public boolean isPostPortletsInitializationPhaseProcessed();

    public void setPostPortletsInitializationPhaseProcessed(boolean isModuleProcessed);

    public String getModuleError(String key, Locale locale);

    public PortletsSetupModuleStateDescriptor getModuleStateDescriptor(String phase);

    public void invokePrePortletInitialization(HttpServletRequest request, PortletDefinition portletDefinition) throws IllegalArgumentException;

    public void invokePostPortletInitialization(HttpServletRequest request) throws IllegalArgumentException;
}
