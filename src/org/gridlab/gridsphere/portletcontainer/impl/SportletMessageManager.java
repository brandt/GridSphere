/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.impl;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.PortletMessage;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portletcontainer.PortletMessageManager;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * The <code>SportletMessageManager</code> provides a singleton implementation of <code>PortletMessageManager</code> and
 * is responsible for maintaining <code>PortletMessage</code>s.
 */
public class SportletMessageManager implements PortletMessageManager {

    private static PortletLog log = SportletLog.getInstance(SportletMessageManager.class);
    private static PortletMessageManager instance = new SportletMessageManager();
    private Map messages = new Hashtable();

    /**
     * Returns a PortletMessageManager instance
     *
     * @return an instance of PortletMessageManager
     */
    public static PortletMessageManager getInstance() {
        return instance;
    }

    /**
     * Default constructor instantiation disallowed
     */
    private SportletMessageManager() {}

    /**
     * Sends the supplied message to the portlet specified by the concrete portlet id
     * <p>
     * The portlet(s) with the given name will only receive the message event if it has/they have implemented
     * the appropriate listener.
     *
     * @param concretePortletID the concrete portlet id
     * @param message the portlet message to be sent
     */
    public void send(String concretePortletID, PortletMessage message) {
        if (concretePortletID == null) {
                concretePortletID = "*"; 
        }
        List l = (List) messages.get(concretePortletID);
        if (l == null) l = new ArrayList();
        l.add(message);
        messages.put(concretePortletID, l);
    }

    /**
     * Retrieves the messages for the given portlet name and removes them from the queue
     *
     * @param concretePortletID the name of the portlet(s) to send the message to
     * @return a list of <code>PortletMessage</code> objects
     */
    public List retrieveMessages(String concretePortletID) {
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
     * @return a map of PortletMessage objects
     */
    public Map retrieveAllMessages() {
        return messages;
    }

    /**
     * Clears all the messages
     *
     */
    public void clearAllMessages() {
        messages.clear();
    }
}
