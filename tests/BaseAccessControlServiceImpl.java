/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.impl;

import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.AccessDeniedException;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.AccessControlService;

import java.util.List;

public abstract class BaseAccessControlServiceImpl extends BaseSecureServiceImpl implements PortletServiceProvider {

    private static PortletLog log = SportletLog.getInstance(BaseAccessControlServiceImpl.class);

    public void init(PortletServiceConfig config) {
        log.info("in init()");
    }

    public void destroy() {
        log.info("in destroy()");
    }

}
