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

    public boolean isPreInitSetupDone();

    public void skipPreInitSetup();

    public boolean isPostInitSetupDone();

    public void skipPostInitSetup();

    public void skipModule();

    public PortletsSetupModuleStateDescriptor getModuleStateDescriptor() throws IllegalStateException;

    public void invokePreInit(HttpServletRequest request) throws IllegalArgumentException, IllegalStateException;

    public void invokePostInit(HttpServletRequest request) throws IllegalArgumentException, IllegalStateException;
}
