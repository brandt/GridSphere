/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.event;

import org.gridlab.gridsphere.portlet.PortletException;

import java.util.EventListener;

/**
 * The <code>MessageListener</code> interface is implemented by the
 * {@link org.gridlab.gridsphere.portlet.AbstractPortlet} and must be
 * implemented by all portlets that wish to receive message events.
 */
public interface MessageListener extends EventListener {

    /**
     * Gives notification that a message has been received
     *
     * @param event the message event
     * @throws PortletException if the listener has trouble fulfilling the request
     */
    public void messageReceived(MessageEvent event) throws PortletException;

}
