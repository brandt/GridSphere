/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: Event.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.event;

import org.gridsphere.portlet.PortletRequest;

/**
 * The <code>Event</code> is the base interface for all events that can occur whithin the portlet container.
 * To be informed of events, a listener has to be registered with the respective event source.
 * The portlet container delivers all events to the respective event listeners (and thereby the portlets)
 * before the content generation is started. Should a listener, while processing the event, find that another
 * event needs to be generated, that event will be queued by the portlet container and delivered at a point
 * of time that is at the discretion of the portlet container. It is only guarantued that it will be deliverd
 * and that it will happen before the content generation phase. This also means, that no further events
 * will be delivered once the content generation phase has started. For example, message cannot be sent
 * from within the service() methods. The resulting message event will not be delivered and essentially discarded.
 * <p/>
 * Intelligent portlet containers should provide a cyclic event detection mechanism, so that the portlet
 * container does not come to a screaming halt, if two or more portlets happen to send each other events
 * that are triggered by each other.
 */
public interface Event {

    /**
     * Returns the portlet request that has caused this event
     *
     * @return the <code>PortletRequest</code>
     */
    public PortletRequest getPortletRequest();

}
