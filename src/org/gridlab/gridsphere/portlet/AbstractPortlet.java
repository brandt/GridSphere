/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet;

import org.gridlab.gridsphere.event.WindowListener;
import org.gridlab.gridsphere.event.ActionListener;
import org.gridlab.gridsphere.event.ActionEvent;
import org.gridlab.gridsphere.event.WindowEvent;


/**
 *
 */
public abstract class AbstractPortlet extends PortletAdapter implements ActionListener, WindowListener {

    public AbstractPortlet() {
        super();
    }

    /**
     * Returns the portlet configuration.
     *
     * @return the portlet config
     */
    public PortletConfig getConfig() {
        return super.getPortletConfig();
    }

    /**
     * Called by the portlet container to ask this portlet to perform the required operational logic
     * using the given portlet request. This method is invoked before the service method.
     * Notifies this listener that the action which the listener is watching for has been performed.
     *
     * @param event the action event
     * @throws PortletException if the listener has trouble fulfilling the request
     */
    public abstract void actionPerformed(ActionEvent event) throws PortletException;

    /**
     * Notifies this listener that the action which the listener is watching for is about to be performed.
     *
     * @param event the action event
     * @throws PortletException if the listener has trouble fulfilling the request
     */
    public void actionNotYetPerformed(ActionEvent event) throws PortletException {}

    /**
     * Notifies this listener that a portlet window has been detached.
     *
     * @param event the window event
     */
    public void windowDetached(WindowEvent event) throws PortletException {}

    /**
     * Notifies this listener that a portlet window has been maximized.
     *
     * @param event the window event
     */
    public void windowMaximized(WindowEvent event) throws PortletException {}

    /**
     * Notifies this listener that a portlet window has been minimized.
     *
     * @param event the window event
     */
    public void windowMinimized(WindowEvent event) throws PortletException {}

    /**
     * Notifies this listener that a portlet window is about to be closed.
     *
     * @param event the window event
     */
    public void windowClosing(WindowEvent event) throws PortletException {}

    /**
     * Notifies this listener that a portlet window has been closed.
     *
     * @param event the window event
     */
    public void windowClosed(WindowEvent event) throws PortletException {}

    /**
     * Notifies this listener that a portlet window has been restored from being minimized or maximized, respectively.
     *
     * @param event the window event
     */
    public void windowRestored(WindowEvent event) throws PortletException {}

}










































