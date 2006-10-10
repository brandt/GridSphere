package org.gridsphere.services.core.tomcat;

import org.gridsphere.portlet.service.PortletService;
import org.gridsphere.portlet.service.spi.PortletServiceConfig;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
public interface TomcatManagerService extends PortletService {

    public void init(PortletServiceConfig config);

    public TomcatWebAppResult getWebAppList(PortletRequest req, PortletResponse res) throws TomcatManagerException;

    public TomcatWebAppResult reloadWebApp(PortletRequest req, PortletResponse res, String context) throws TomcatManagerException;

    public TomcatWebAppResult removeWebApp(PortletRequest req, PortletResponse res, String context) throws TomcatManagerException;

    public TomcatWebAppResult startWebApp(PortletRequest req, PortletResponse res, String context) throws TomcatManagerException;

    public TomcatWebAppResult stopWebApp(PortletRequest req, PortletResponse res, String context) throws TomcatManagerException;

    public TomcatWebAppResult deployWebApp(PortletRequest req, PortletResponse res, String context) throws TomcatManagerException;

    public TomcatWebAppResult undeployWebApp(PortletRequest req, PortletResponse res, String context) throws TomcatManagerException;

    public TomcatWebAppResult installWebApp(PortletRequest req, PortletResponse res, String context, String warFile) throws TomcatManagerException;

    public TomcatWebAppResult installWebApp(PortletRequest req, PortletResponse res, String warFile) throws TomcatManagerException;
}
