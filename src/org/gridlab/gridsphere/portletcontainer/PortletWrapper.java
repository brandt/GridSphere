/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer;

import org.gridlab.gridsphere.event.MessageEvent;
import org.gridlab.gridsphere.event.WindowEvent;
import org.gridlab.gridsphere.portlet.PortletAction;
import org.gridlab.gridsphere.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.PortletSettings;
import org.gridlab.gridsphere.portlet.impl.PortletProperties;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portletcontainer.descriptor.PortletApp;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PortletWrapper {

    public static PortletLog log = SportletLog.getInstance(PortletWrapper.class);

    private RequestDispatcher rd;
    private PortletApp portletApp = null;

    public PortletWrapper(RequestDispatcher rd, PortletApp portletApp) {
        this.rd = rd;
        this.portletApp = portletApp;
    }

    /**
     * Called by the portlet container to indicate to this portlet that it is put into service.
     *
     * The portlet container calls the init() method for the whole life-cycle of the portlet.
     * The init() method must complete successfully before concrete portlets are created through
     * the initConcrete() method.
     *
     * The portlet container cannot place the portlet into service if the init() method
     *
     * 1. throws UnavailableException
     * 2. does not return within a time period defined by the portlet container.
     *
     * @param config the portlet configuration
     * @throws UnavailableException if an exception has occurrred that interferes with the portlet's
     * normal initialization
     */
    public void init(HttpServletRequest req, HttpServletResponse res) throws PortletException {
        log.info("in PortletWrapper: init");
        req.setAttribute(PortletProperties.PORTLET_APPLICATION, portletApp);
        req.setAttribute(PortletProperties.PORTLET_LIFECYCLE_METHOD, PortletProperties.INIT);
        include(req, res);
    }

    /**
     * Called by the portlet container to indicate to this portlet that it is taken out of service.
     * This method is only called once all threads within the portlet's service() method have exited
     * or after a timeout period has passed. After the portlet container calls this method,
     * it will not call the service() method again on this portlet.
     *
     * This method gives the portlet an opportunity to clean up any resources that are
     * being held (for example, memory, file handles, threads).
     *
     * @param config the portlet configuration
     */
    public void destroy(HttpServletRequest req, HttpServletResponse res) throws PortletException {
        log.info("in PortletWrapper: destroy");
        req.setAttribute(PortletProperties.PORTLET_LIFECYCLE_METHOD, PortletProperties.DESTROY);
        include(req, res);
    }

    /**
     * Called by the portlet container to indicate that the concrete portlet is put into service.
     * The portlet container calls the initConcrete() method for the whole life-cycle of the portlet.
     * The initConcrete() method must complete successfully before concrete portlet instances can be
     * created through the login() method.
     *
     * The portlet container cannot place the portlet into service if the initConcrete() method
     *
     * 1. throws UnavailableException
     * 2. does not return within a time period defined by the portlet container.
     *
     * @param settings the portlet settings
     */
    public void initConcrete(PortletSettings settings, HttpServletRequest req, HttpServletResponse res) throws PortletException {
        log.info("in PortletWrapper: initConcrete");
        req.setAttribute(PortletProperties.PORTLET_SETTINGS, settings);
        req.setAttribute(PortletProperties.PORTLET_LIFECYCLE_METHOD, PortletProperties.INIT_CONCRETE);
        include(req, res);
    }

    /**
     * Called by the portlet container to indicate that the concrete portlet is taken out of service.
     * This method is only called once all threads within the portlet's service() method have exited
     * or after a timeout period has passed. After the portlet container calls this method,
     * it will not call the service() method again on this portlet.
     *
     * This method gives the portlet an opportunity to clean up any resources that are being
     * held (for example, memory, file handles, threads).
     *
     * @param settings the portlet settings
     */
    public void destroyConcrete(PortletSettings settings, HttpServletRequest req, HttpServletResponse res) throws PortletException {
        log.info("in PortletWrapper: destroyConcrete");
        req.setAttribute(PortletProperties.PORTLET_SETTINGS, settings);
        req.setAttribute(PortletProperties.PORTLET_LIFECYCLE_METHOD, PortletProperties.DESTROY_CONCRETE);
        include(req, res);
    }

    /**
     * Called by the portlet container to ask this portlet to generate its markup using the given
     * request/response pair. Depending on the mode of the portlet and the requesting client device,
     * the markup will be different. Also, the portlet can take language preferences and/or
     * personalized settings into account.
     *
     * @param request the portlet request
     * @param response the portlet response
     *
     * @throws PortletException if the portlet has trouble fulfilling the rendering request
     * @throws IOException if the streaming causes an I/O problem
     */
    public void service(String concretePortletID, HttpServletRequest req, HttpServletResponse res) throws PortletException {
        log.info("in PortletWrapper: service");
        req.setAttribute(GridSphereProperties.PORTLETID, concretePortletID);
        req.setAttribute(PortletProperties.PORTLET_LIFECYCLE_METHOD, PortletProperties.SERVICE);
        include(req, res);
    }


    /**
     * Description copied from interface: PortletSessionListener
     * Called by the portlet container to ask the portlet to initialize a personalized user experience.
     * In addition to initializing the session this method allows the portlet to initialize the
     * concrete portlet instance, for example, to store attributes in the session.
     *
     * @param request the portlet request
     */
    public void login(HttpServletRequest req, HttpServletResponse res) throws PortletException {
        log.info("in PortletWrapper: login");
        req.setAttribute(PortletProperties.PORTLET_LIFECYCLE_METHOD, PortletProperties.LOGIN);
        include(req, res);
    }

    /**
     * Description copied from interface: PortletSessionListener
     * Called by the portlet container to indicate that a concrete portlet instance is being removed.
     * This method gives the concrete portlet instance an opportunity to clean up any resources
     * (for example, memory, file handles, threads), before it is removed.
     * This happens if the user logs out, or decides to remove this portlet from a page.
     *
     * @param session the portlet session
     */
    public void logout(HttpServletRequest req, HttpServletResponse res) throws PortletException {
        log.info("in PortletWrapper: logout");
        req.setAttribute(PortletProperties.PORTLET_LIFECYCLE_METHOD, PortletProperties.LOGOUT);
        include(req, res);
    }

    /**
     * Called by the portlet container to ask this portlet to perform the required operational logic
     * using the given portlet request.
     * Notifies this listener that the action which the listener is watching for has been performed.
     *
     * @param event the action event
     * @throws PortletException if the listener has trouble fulfilling the request
     */
    public void actionPerformed(PortletAction action, HttpServletRequest req, HttpServletResponse res) throws PortletException {
        log.info("in PortletWrapper: actionPerformed");
        req.setAttribute(PortletProperties.ACTION_EVENT, action);
        req.setAttribute(PortletProperties.PORTLET_LIFECYCLE_METHOD, PortletProperties.SERVICE);
        req.setAttribute(PortletProperties.PORTLET_ACTION_METHOD, PortletProperties.ACTION_PERFORMED);
        include(req, res);
    }

    /**
     * Notifies this listener that the message which the listener is watching for has been performed.
     *
     * @param event the message event
     *
     * @throws PortletException if the listener has trouble fulfilling the request
     */
    public void messageReceived(MessageEvent event, HttpServletRequest req, HttpServletResponse res) throws PortletException {
        log.info("in PortletWrapper: messageReceived");
        req.setAttribute(PortletProperties.MESSAGE_EVENT, event);
        req.setAttribute(PortletProperties.PORTLET_LIFECYCLE_METHOD, PortletProperties.SERVICE);
        req.setAttribute(PortletProperties.PORTLET_ACTION_METHOD, PortletProperties.MESSAGE_RECEIVED);
        include(req, res);
    }

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
    public void doTitle(HttpServletRequest req, HttpServletResponse res) throws PortletException {
        log.info("in PortletWrapper: doTitle");
        req.setAttribute(PortletProperties.PORTLET_LIFECYCLE_METHOD, PortletProperties.SERVICE);
        req.setAttribute(PortletProperties.PORTLET_ACTION_METHOD, PortletProperties.DO_TITLE);
        include(req, res);
    }

    /**
     * Notifies this listener that a portlet window has been detached.
     *
     * @param event the window event
     */
    public void windowDetached(WindowEvent event, HttpServletRequest req, HttpServletResponse res) throws PortletException {
        log.info("in PortletWrapper: windowDetached");
        req.setAttribute(PortletProperties.MESSAGE_EVENT, event);
        req.setAttribute(PortletProperties.PORTLET_LIFECYCLE_METHOD, PortletProperties.SERVICE);
        req.setAttribute(PortletProperties.PORTLET_ACTION_METHOD, PortletProperties.WINDOW_DETACHED);
        include(req, res);
    }

    /**
     * Notifies this listener that a portlet window has been maximized.
     *
     * @param event the window event
     */
    public void windowMaximized(WindowEvent event, HttpServletRequest req, HttpServletResponse res) throws PortletException {
        log.info("in PortletWrapper: windowMaximized");
        req.setAttribute(PortletProperties.MESSAGE_EVENT, event);
        req.setAttribute(PortletProperties.PORTLET_LIFECYCLE_METHOD, PortletProperties.SERVICE);
        req.setAttribute(PortletProperties.PORTLET_ACTION_METHOD, PortletProperties.WINDOW_MAXIMIZED);
        include(req, res);
    }

    /**
     * Notifies this listener that a portlet window has been minimized.
     *
     * @param event the window event
     */
    public void windowMinimized(WindowEvent event, HttpServletRequest req, HttpServletResponse res) throws PortletException {
        log.info("in PortletWrapper: windowMinimized");
        req.setAttribute(PortletProperties.MESSAGE_EVENT, event);
        req.setAttribute(PortletProperties.PORTLET_LIFECYCLE_METHOD, PortletProperties.SERVICE);
        req.setAttribute(PortletProperties.PORTLET_ACTION_METHOD, PortletProperties.WINDOW_MINIMIZED);
        include(req, res);
    }

    /**
     * Notifies this listener that a portlet window is about to be closed.
     *
     * @param event the window event
     */
    public void windowClosing(WindowEvent event, HttpServletRequest req, HttpServletResponse res) throws PortletException {
        log.info("in PortletWrapper: windowClosing");
        req.setAttribute(PortletProperties.MESSAGE_EVENT, event);
        req.setAttribute(PortletProperties.PORTLET_LIFECYCLE_METHOD, PortletProperties.SERVICE);
        req.setAttribute(PortletProperties.PORTLET_ACTION_METHOD, PortletProperties.WINDOW_CLOSING);
        include(req, res);
    }

    /**
     * Notifies this listener that a portlet window has been closed.
     *
     * @param event the window event
     */
    public void windowClosed(WindowEvent event, HttpServletRequest req, HttpServletResponse res) throws PortletException {
        log.info("in PortletWrapper: windowClosed");
        req.setAttribute(PortletProperties.MESSAGE_EVENT, event);
        req.setAttribute(PortletProperties.PORTLET_LIFECYCLE_METHOD, PortletProperties.SERVICE);
        req.setAttribute(PortletProperties.PORTLET_ACTION_METHOD, PortletProperties.WINDOW_CLOSED);
        include(req, res);
    }

    /**
     * Notifies this listener that a portlet window has been restored from being minimized or maximized, respectively.
     *
     * @param event the window event
     */
    public void windowRestored(WindowEvent event, HttpServletRequest req, HttpServletResponse res) throws PortletException {
        log.info("in PortletWrapper: windowRestored");
        req.setAttribute(PortletProperties.MESSAGE_EVENT, event);
        req.setAttribute(PortletProperties.PORTLET_LIFECYCLE_METHOD, PortletProperties.SERVICE);
        req.setAttribute(PortletProperties.PORTLET_ACTION_METHOD, PortletProperties.WINDOW_RESTORED);
        include(req, res);
    }

    protected void include(HttpServletRequest req, HttpServletResponse res) throws PortletException {
        try {
            rd.include(req, res);
        } catch (Exception e) {
            log.error("in PortletWrapper:", e);
            throw new PortletException(e);
        }
    }

}






