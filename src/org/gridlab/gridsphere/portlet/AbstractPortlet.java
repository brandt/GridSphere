/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet;

import org.gridlab.gridsphere.event.ActionEvent;
import org.gridlab.gridsphere.event.ActionListener;
import org.gridlab.gridsphere.event.WindowEvent;
import org.gridlab.gridsphere.event.WindowListener;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import java.io.IOException;


/**
 * Additional extensions to a Portlet Adapter to provide action and window event handling
 */
public abstract class AbstractPortlet extends PortletAdapter implements ActionListener, WindowListener, PortletTitleListener {

    public static PortletLog log = SportletLog.getInstance(AbstractPortlet.class);

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
     * using the given portlet request.
     * Notifies this listener that the action which the listener is watching for has been performed.
     *
     * @param event the action event
     * @throws PortletException if the listener has trouble fulfilling the request
     */
    public abstract void actionPerformed(ActionEvent event) throws PortletException;

    /**
     * Called by the portlet container to render the portlet title.
     * The information in the portlet request (like locale, client, and session information) can
     * but doesn't have to be considered to render dynamic titles.. Examples are
     *
     * language-dependant titles for multi-lingual portals
     * shorter titles for WAP phones
     * the number of messages in a mailbox portlet
     * The session may be null, if the user is not logged in.
     *
     * @param request the portlet request
     * @param response the portlet response
     *
     * @throws <code>PortletException</code>if the portlet title has trouble fulfilling the rendering request
     * @throws java.io.IOException if the streaming causes an I/O problem
     */
    public abstract void doTitle(PortletRequest request, PortletResponse response) throws PortletException, IOException;

    /**
     * Notifies this listener that a portlet window has been detached.
     *
     * @param event the window event
     */
    public void windowDetached(WindowEvent event) throws PortletException {
    }

    /**
     * Notifies this listener that a portlet window has been maximized.
     *
     * @param event the window event
     */
    public void windowMaximized(WindowEvent event) throws PortletException {
    }

    /**
     * Notifies this listener that a portlet window has been minimized.
     *
     * @param event the window event
     */
    public void windowMinimized(WindowEvent event) throws PortletException {
    }

    /**
     * Notifies this listener that a portlet window is about to be closed.
     *
     * @param event the window event
     */
    public void windowClosing(WindowEvent event) throws PortletException {
    }

    /**
     * Notifies this listener that a portlet window has been closed.
     *
     * @param event the window event
     */
    public void windowClosed(WindowEvent event) throws PortletException {
    }

    /**
     * Notifies this listener that a portlet window has been restored from being minimized or maximized, respectively.
     *
     * @param event the window event
     */
    public void windowRestored(WindowEvent event) throws PortletException {
    }

}










































