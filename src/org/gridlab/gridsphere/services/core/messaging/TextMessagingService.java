package org.gridlab.gridsphere.services.core.messaging;

import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.tmf.Message;
import org.gridlab.gridsphere.tmf.config.User;

import java.util.List;

/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

public interface TextMessagingService  extends PortletService {

    Message createNewMessage();

    void send(Message message);

    /**
     * Returns a list of tmf users objects
     * @return
     */
    public List getUsers();

    public List getServices();

    public User getUser(String userid);

    public void setUser(User user);

}
