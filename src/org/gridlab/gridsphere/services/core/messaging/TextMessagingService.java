package org.gridlab.gridsphere.services.core.messaging;

import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.tmf.TmfMessage;
import org.gridlab.gridsphere.tmf.config.TmfUser;

import java.util.List;

/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

public interface TextMessagingService extends PortletService {

    /**
     * Returns an empty message Object.
     * @return  empty message
     */
    TmfMessage createNewMessage();

    /**
     * Sends given message object
     * @param message message to be sent
     */
    void send(TmfMessage message);

    /**
     * Returns a list of tmf users objects
     * @return list of tmf users
     */
    List getUsers();


    /**
     * Returns a list of the messaging services available.
     * @return  list of textmessaging services
     */
    List getServices();

    /**
     * Returns the textmessaging userobject for a given userid.
     * @param userid of the user
     * @return textmessaging userobject
     */
    TmfUser getUser(String userid);


    /**
     * Saves the userinfo for the user
     * @param user userobject to be saved
     */
    void saveUser(TmfUser user);

    /**
     * Checks if a userid is on a service
     * @param userid userid of the user
     * @param messagetype messagetype
     * @return true if the user has subscribed to the service, otherwise false
     */
    boolean isUserOnService(String userid, String messagetype);

    /**
     * Checks if a user is 'online' if the service supports this.
     * @param userid
     * @param messagetype messagetype
     * @return always false for now
     */
    boolean isUserOnline(String userid, String messagetype);

}
