/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: ActionListener.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.event;

import org.gridsphere.portlet.PortletException;

import java.util.EventListener;

/**
 * The <code>ActionListener</code> interface is implemented by the
 * {@link org.gridsphere.portlet.AbstractPortlet} and must be
 * implemented by all portlets that wish to handle action events.
 */
public interface ActionListener extends EventListener {

    /**
     * Gives notification that an action event has occured
     *
     * @param event the action event
     * @throws PortletException if the listener has trouble fulfilling the request
     */
    public void actionPerformed(ActionEvent event) throws PortletException;

}
