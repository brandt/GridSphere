/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.impl;

import org.gridlab.gridsphere.portlet.AccessDeniedException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.PortletMessage;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portletcontainer.PortletMessageManager;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * The PortletMessageService provides customization support for user layouts
 */
public class SportletMessageManager implements PortletMessageManager {

    private static PortletLog log = SportletLog.getInstance(SportletMessageManager.class);
    private static PortletMessageManager instance = new SportletMessageManager();
    private Map messages = new Hashtable();

    public static PortletMessageManager getInstance() {
        return instance;
    }

    private SportletMessageManager() {}

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
     *
     * @throws AccessDeniedException if the portlet tries to access this function outside of the event processing
     */
    public void send(String concretePortletID, PortletMessage message) throws AccessDeniedException {
        List l = (List) messages.get(concretePortletID);
        if (l == null) l = new ArrayList();
        l.add(message);
        messages.put(concretePortletID, l);
    }

    /**
     * Retrieves the messages for the given portlet name and removes them from the queue
     *
     * @para portletName the name of the portlet(s) to send the message to
     * @return a list of PortletMessage objects
     *
     * @throws AccessDeniedException if the portlet tries to access this function outside of the event processing
     */
    public List retrieveMessages(String concretePortletID) throws AccessDeniedException {
        List messageList = new ArrayList();
        List l = (List) messages.get(concretePortletID);
        if (l != null) {
            messageList.addAll(l);
            messages.remove(l);
        }
        return messageList;
    }

    /**
     * Retrieves all the messages  removes them from the queue
     *
     * @return a list of PortletMessage objects
     *
     * @throws AccessDeniedException if the portlet tries to access this function outside of the event processing
     */
    public Map retrieveAllMessages() throws AccessDeniedException {
        return messages;
    }

    /**
     * Clears all the messages
     *
     * @throws AccessDeniedException if the portlet tries to access this function outside of the event processing
     */
    public void clearAllMessages() throws AccessDeniedException {
        messages = null;
    }
}
