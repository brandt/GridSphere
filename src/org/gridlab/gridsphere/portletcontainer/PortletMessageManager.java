/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer;

import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.AccessDeniedException;
import org.gridlab.gridsphere.portlet.PortletMessage;
import org.gridlab.gridsphere.layout.PortletLayoutException;
import org.gridlab.gridsphere.layout.PortletContainer;

import java.util.List;
import java.util.Map;

/**
 * The PortletMessageManager
 */
public interface PortletMessageManager {

    /**
     * Sends the given message to all portlets on the same page that have the given name regardless of the portlet application.
     * If the portlet name is null the message is broadcast to all portlets in the same portlet application.
     * If more than one instance of the portlet with the given name exists on the current page, the message is sent
     * to every single instance of that portlet. If the source portlet has the same name as the target portlet(s),
     * the message will not be sent to avoid possible cyclic calls.
     *
     * The portlet(s) with the given name will only receive the message event if it has/they have implemented
     * the appropriate listener.
     *
     * This function may only be used during event processing, in any other case the method throws an AccessDeniedException.
     *
     * @param portletName the name of the portlet(s) to send the message to
     * @param message the message to be sent
     */
    public void send(String portletName, PortletMessage message);

    /**
     * Retrieves the messages for the given portlet name and removes them from the queue
     *
     * @param portletName the name of the portlet(s) to send the message to
     * @return a list of PortletMessage objects
     */
    public List retrieveMessages(String portletName);

    /**
     * Retrieves all the messages  removes them from the queue
     *
     * @return a list of PortletMessage objects
     */
    public Map retrieveAllMessages();

    /**
     * Clears all the messages
     */
    public void clearAllMessages();

}
