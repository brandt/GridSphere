/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.event;

import org.gridlab.gridsphere.portlet.PortletException;

import java.util.EventListener;

/**
 * The MessageListener interface is an addition to the Portlet  interface.
 * If an object wishes to receive message events in the portlet, this interface has to be
 * implemented additionally to the Portlet interface.
 */
public interface MessageListener extends EventListener {

    /**
     * Notifies this listener that the message which the listener is watching for has been performed.
     *
     * @param event the message event
     *
     * @throws PortletException if the listener has trouble fulfilling the request
     */
    public void messageReceived(MessageEvent event) throws PortletException;

}
