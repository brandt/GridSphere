/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: PortletMessageManager.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.portletcontainer;

import org.gridsphere.portlet.PortletMessage;

import java.util.List;
import java.util.Map;

/**
 * The <code>PortletMessageManager</code> is responsible for maintaining <code>PortletMessage</code>s.
 */
public interface PortletMessageManager {

    /**
     * Sends the supplied message to the portlet specified by the concrete portlet id
     * <p/>
     * The portlet(s) with the given name will only receive the message event if it has/they have implemented
     * the appropriate listener.
     *
     * @param concretePortletID the concrete portlet id
     * @param message           the portlet message to be sent
     */
    public void send(String concretePortletID, PortletMessage message);

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
     * @return a map of PortletMessage objects
     */
    public Map retrieveAllMessages();

    public void removeAllMessages();

    public void removeMessages(String concretePortletID);

    public void removeMessage(String concretePortletID, PortletMessage message);
}
