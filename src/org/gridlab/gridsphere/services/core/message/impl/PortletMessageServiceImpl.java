/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.message.impl;

import org.gridlab.gridsphere.layout.*;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.services.layout.LayoutManagerService;

import java.io.IOException;
import java.util.*;

/**
 * The LayoutManagerService provies customization support for user layouts
 */
public class PortletMessageServiceImpl implements PortletMessageService, PortletServiceProvider {

    private static PortletLog log = SportletLog.getInstance(PortletMessageServiceImpl.class);

    /**
     * The init method is responsible for parsing portlet.xml and creating ConcretePortlet objects based on the
     * entries. The RegisteredPortlets are managed by the PortletRegistryService.
     */
    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        log.info("in init()");
    }

    public void destroy() {
        log.info("in destroy()");
    }

}
