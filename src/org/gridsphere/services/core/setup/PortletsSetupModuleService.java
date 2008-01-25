package org.gridsphere.services.core.setup;

import org.gridsphere.portlet.service.PortletService;
import org.gridsphere.portletcontainer.PortletWebApplication;
import org.gridsphere.services.core.setup.modules.impl.descriptor.PortletsSetupModuleStateDescriptor;

import javax.portlet.Portlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author <a href="mailto:docentt@man.poznan.pl">Tomasz Kuczynski</a>, PSNC
 * @version $Id$
 */
public interface PortletsSetupModuleService extends PortletService {

    public void loadPortletsSetupModules(String setupModsPath, PortletWebApplication portletWebApplication, Map<String, Portlet> portlets, ClassLoader classloader);

    public boolean isPrePortletsInitializingSetupDone();

    public void skipPrePortletsInitializingSetup();

    public boolean isPostPortletsInitializingSetupDone();

    public void skipPostPortletsInitializingSetup();

    public PortletsSetupModuleStateDescriptor getModuleStateDescriptor() throws IllegalAccessException;

    public void invokePrePortletInitialization(HttpServletRequest request) throws IllegalArgumentException, IllegalAccessException;

    public void invokePostPortletInitialization(HttpServletRequest request) throws IllegalArgumentException, IllegalAccessException;
}
