/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: SportletMessageManager.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.portletcontainer.impl;

import org.gridsphere.portlet.PortletLog;
import org.gridsphere.portlet.PortletMessage;
import org.gridsphere.portlet.impl.SportletLog;
import org.gridsphere.portletcontainer.PortletMessageManager;

import java.util.*;

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
    private SportletMessageManager() {
    }

    /**
     * Sends the supplied message to the portlet specified by the concrete portlet id
     * <p/>
     * The portlet(s) with the given name will only receive the message event if it has/they have implemented
     * the appropriate listener.
     *
     * @param concretePortletID the concrete portlet id
     * @param message           the portlet message to be sent
     */
    public synchronized void send(String concretePortletID, PortletMessage message) {
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
        if (!messages.containsKey(concretePortletID)) return null;
        List l = (List) messages.get(concretePortletID);
        return Collections.unmodifiableList(l);
    }

    public void removeMessages(String concretePortletID) {
        messages.remove(concretePortletID);
    }

    public synchronized void removeMessage(String concretePortletID, PortletMessage message) {
        if (messages.containsKey(concretePortletID)) {
            List l = (List) messages.get(concretePortletID);
            l.remove(message);
        }
    }

    public synchronized void removeAllMessages() {
        messages.clear();
    }

    /**
     * Retrieves all the messages  removes them from the queue
     *
     * @return a map of PortletMessage objects
     */
    public Map retrieveAllMessages() {
        return Collections.unmodifiableMap(messages);
    }

}
