package org.gridlab.gridsphere.services.core.messaging.impl;

import org.gridlab.gridsphere.services.core.messaging.TextMessagingService;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.tmf.Message;
import org.gridlab.gridsphere.tmf.TmfCore;

import java.util.List;

/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

public class TextMessagingServiceImpl  implements TextMessagingService, PortletServiceProvider {

    private static PortletLog log = SportletLog.getInstance(TextMessagingServiceImpl.class);

    private TmfCore core = null;

    public Message createNewMessage() {
        return core.getNewMessage();
    }

    public void send(Message message) {
        core.send(message);
    }

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        String configfile = config.getServletContext().getRealPath("WEB-INF/tmfconfig");

        log.info("Starting up TextMessagingService with config "+configfile);

        core = TmfCore.getInstance(configfile);
        core.startupServices();
    }

    public void destroy() {
        core.shutdown();
    }

    /**
     * Returns a list of tmf users objects
     * @return
     */
    public List getUsers() {
        return core.getUsers();
    }

    public List getServices() {
        return core.getServiceConfig();
    }


}
