/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: WindowListener.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.event;

import org.gridsphere.portlet.PortletException;

/**
 * The <code>WindowListener</code> interface is implemented by the
 * {@link org.gridsphere.portlet.AbstractPortlet} and must be
 * implemented by all portlets that wish to handle window events.
 */
public interface WindowListener {

    /**
     * Notifies this listener that a portlet window has been detached.
     *
     * @param event the window event
     */
    public void windowDetached(WindowEvent event) throws PortletException;

    /**
     * Notifies this listener that a portlet window has been maximized.
     *
     * @param event the window event
     */
    public void windowMaximized(WindowEvent event) throws PortletException;

    /**
     * Notifies this listener that a portlet window has been minimized.
     *
     * @param event the window event
     */
    public void windowMinimized(WindowEvent event) throws PortletException;

    /**
     * Notifies this listener that a portlet window is about to be closed.
     *
     * @param event the window event
     */
    public void windowClosing(WindowEvent event) throws PortletException;

    /**
     * Notifies this listener that a portlet window has been closed.
     *
     * @param event the window event
     */
    public void windowClosed(WindowEvent event) throws PortletException;

    /**
     * Notifies this listener that a portlet window has been restored from being minimized or maximized, respectively.
     *
     * @param event the window event
     */
    public void windowRestored(WindowEvent event) throws PortletException;

}
