package org.gridsphere.services.core.messaging;

import org.gridsphere.portlet.service.PortletService;
import org.gridsphere.tmf.message.GenericMessage;
import org.gridsphere.tmf.message.InstantMessage;
import org.gridsphere.tmf.message.MailMessage;
import org.gridsphere.tmf.TextMessagingException;
import org.gridsphere.tmf.TextMessagingSession;

import java.util.Set;

/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id: TextMessagingService.java 4496 2006-02-08 20:27:04Z wehrens $
 */

public interface TextMessagingService extends PortletService {

    final static String SERVICE_ID = "serviceid";
    final static String SERVICE_DESCRIPTION = "servicedescription";
    final static String SERVICE_NAME = "servicename";

    /**
     * Returns an object to be send through Instant Messaging
     * @return an instant message
     */
    InstantMessage getInstantMessage();

    /**
     * Returns a Mail Message
     * @return a mail message
     */
    MailMessage getMailMessage();

    /**
     * Sends a message to the service.
     * @param message
     * @throws TextMessagingException
     */
    void send(GenericMessage message) throws TextMessagingException;

    /**
     * Startup of all the configures services
     */
    void startup();

    /**
     * Shutdown of all the configured servies
     */
    void shutdown();

    /**
     * Returns a set of TextMessageService objects
     * @see org.gridsphere.tmf.services.TextMessageService
     * @return set of services
     */
    Set getServices();

    /**
     * Returns the userid for a servcice for a username
     * @param serviceid id of the service (aim, mail, irc...)
     * @param username name of the user in the portal
     * @return configured userid for the given service for the user
     */
    String getServiceUserID(String serviceid, String username);

    void setServiceUserID(String serviceid, String username, String serviceuserid);

    void addCommands(Set commands);

    public TextMessagingSession getSession(String serviceid, String userid);    

}
