/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.event;

import org.gridlab.gridsphere.portlet.PortletException;

import java.util.EventListener;

/**
 * The ActionListener interface is an addition to the Portlet interface.
 * If an object wishes to receive action events in the portlet,
 * this interface has to be implemented additionally to the Portlet interface.
 */
public interface ActionListener extends EventListener {

    /**
     * Notifies this listener that the action which the listener is watching for has been performed.
     *
     * @param event the action event
     * @throws PortletException if the listener has trouble fulfilling the request
     */
    public void actionPerformed(ActionEvent event) throws PortletException;

}
