/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer;

import org.gridlab.gridsphere.event.WindowEvent;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletProperties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The <code>PortletDispatcher</code> provides a mechanism for invoking portlet lifecycle methods by using a
 * <code>RequestDispatcher</code> from the Servlet API to forward requests to other portlets (servlets).
 *
 * @see <code>PortletInvoker</code>
 */
public class PortletDispatcher {

    public static PortletLog log = SportletLog.getInstance(PortletDispatcher.class);

    private RequestDispatcher rd;
    private ApplicationPortletConfig appPortletConfig = null;

    /**
     * Default constructor is kept private
     */
    private PortletDispatcher() {}

    /**
     * Constructs a PortletDispatcher from a <code>RequestDispatcher</code> and an <code>ApplicationPortletConfig</code>
     *
     * @param rd the <code>RequestDispatcher</code>
     * @param appPortletConfig the <code>ApplicationPortletConfig</code>
     */
    public PortletDispatcher(RequestDispatcher rd, ApplicationPortletConfig appPortletConfig) {
        this.rd = rd;
        this.appPortletConfig = appPortletConfig;
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
     * @param req the servlet request
     * @param res the servlet response
     * @throws IOException if an I/O errors occurs
     * @throws PortletException if an exception has occurrred during dispatching
     */
    public void init(HttpServletRequest req, HttpServletResponse res) throws IOException, PortletException {
        req.setAttribute(SportletProperties.PORTLET_APPLICATION, appPortletConfig);
        req.setAttribute(SportletProperties.PORTLET_LIFECYCLE_METHOD, SportletProperties.INIT);
        try {
            include(req, res);
        } catch (ServletException e) {
            log.error("Unable to perform init");
            throw new PortletException("Unable to perform init", e);
        }
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
     * @param req the portlet request
     * @param res the portlet response
     */
    public void destroy(HttpServletRequest req, HttpServletResponse res) throws IOException, PortletException {
        req.setAttribute(SportletProperties.PORTLET_LIFECYCLE_METHOD, SportletProperties.DESTROY);
        try {
            include(req, res);
        } catch (ServletException e) {
            log.error("Unable to perform destroy");
            throw new PortletException("Unable to perform destroy", e);
        }
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
    public void initConcrete(PortletSettings settings, HttpServletRequest req, HttpServletResponse res) throws IOException, PortletException {
        req.setAttribute(SportletProperties.PORTLET_SETTINGS, settings);
        req.setAttribute(SportletProperties.PORTLET_LIFECYCLE_METHOD, SportletProperties.INIT_CONCRETE);
        try {
            include(req, res);
        } catch (ServletException e) {
            log.error("Unable to perform initConcrete");
            throw new PortletException("Unable to perform initConcrete", e);
        }
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
    public void destroyConcrete(PortletSettings settings, HttpServletRequest req, HttpServletResponse res) throws IOException, PortletException {
        log.info("in destroyConcrete");
        req.setAttribute(SportletProperties.PORTLET_SETTINGS, settings);
        req.setAttribute(SportletProperties.PORTLET_LIFECYCLE_METHOD, SportletProperties.DESTROY_CONCRETE);
        try {
            include(req, res);
        } catch (ServletException e) {
            log.error("Unable to perform destroyConcrete");
            throw new PortletException("Unable to perform destroyConcrete", e);
        }
    }

    /**
     * Called by the portlet container to ask this portlet to generate its markup using the given
     * request/response pair. Depending on the mode of the portlet and the requesting client device,
     * the markup will be different. Also, the portlet can take language preferences and/or
     * personalized settings into account.
     *
     * @param req the portlet request
     * @param res the portlet response
     *
     * @throws PortletException if the portlet has trouble fulfilling the rendering request
     * @throws IOException if the streaming causes an I/O problem
     */
    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, PortletException {
        req.setAttribute(SportletProperties.PORTLET_LIFECYCLE_METHOD, SportletProperties.SERVICE);
        try {
            include(req, res);
        } catch (ServletException e) {
            log.error("Unable to perform service");
            throw new PortletException("Unable to perform service", e);
        }
    }

    /**
     * Performs a portlet login dispatch request.
     *
     * @param req the portlet request
     * @param res the portlet response
     */
    public void login(HttpServletRequest req, HttpServletResponse res) throws IOException, PortletException {
        req.setAttribute(SportletProperties.PORTLET_LIFECYCLE_METHOD, SportletProperties.LOGIN);
        try {
            include(req, res);
        } catch (ServletException e) {
            log.error("Unable to perform login");
            throw new PortletException("Unable to perform login", e);
        }
    }

    /**
     * Performs a portlet logout dispatch request.
     *
     * @param req the servlet request
     * @param res the servlet response
     * @throws IOException if an I/O error occurs
     * @throws PortletException is an error occurs dispatching the request
     */
    public void logout(HttpServletRequest req, HttpServletResponse res) throws IOException, PortletException {
        req.setAttribute(SportletProperties.PORTLET_LIFECYCLE_METHOD, SportletProperties.LOGOUT);
        try {
            include(req, res);
        } catch (ServletException e) {
            log.error("Unable to perform logout");
            throw new PortletException("Unable to perform logout", e);
        }
    }

    /**
     * Called by the portlet container to ask this portlet to perform the required operational logic
     * using the given portlet request.
     * Notifies this listener that the action which the listener is watching for has been performed.
     *
     * @param action the default portlet action
     * @param req the servlet request
     * @param res the servlet response
     * @throws IOException if an I/O error occurs
     * @throws PortletException if the listener has trouble fulfilling the request
     */
    public void actionPerformed(DefaultPortletAction action, HttpServletRequest req, HttpServletResponse res) throws IOException, PortletException {
        req.setAttribute(SportletProperties.ACTION_EVENT, action);
        req.setAttribute(SportletProperties.PORTLET_LIFECYCLE_METHOD, SportletProperties.SERVICE);
        req.setAttribute(SportletProperties.PORTLET_ACTION_METHOD, SportletProperties.ACTION_PERFORMED);

        try {
            include(req, res);
        } catch (ServletException e) {
            log.error("Unable to perform actionPerformed");
            throw new PortletException("Unable to perform actionPerformed", e);
        }
    }

    /**
     * Notifies this listener that the message which the listener is watching for has been performed.
     *
     * @param message the default portlet message
     * @param req the servlet request
     * @param res the servlet response
     * @throws IOException if an I/O error occurs during dispatching
     * @throws PortletException if the listener has trouble fulfilling the request
     */
    public void messageEvent(DefaultPortletMessage message, HttpServletRequest req, HttpServletResponse res) throws IOException, PortletException {
        req.setAttribute(SportletProperties.MESSAGE_EVENT, message);
        req.setAttribute(SportletProperties.PORTLET_LIFECYCLE_METHOD, SportletProperties.SERVICE);
        req.setAttribute(SportletProperties.PORTLET_ACTION_METHOD, SportletProperties.MESSAGE_RECEIVED);
        try {
            include(req, res);
        } catch (ServletException e) {
            log.error("Unable to perform messageEvent");
            throw new PortletException("Unable to perform messageEvent", e);
        }
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
     * @param req the servlet request
     * @param res the servlet response
     *
     * @throws IOException if an I/O error occurs during dispatching
     * @throws PortletException if the portlet title has trouble fulfilling the rendering request
     */
    public void doTitle(HttpServletRequest req, HttpServletResponse res) throws IOException, PortletException {
        req.setAttribute(SportletProperties.PORTLET_LIFECYCLE_METHOD, SportletProperties.SERVICE);
        req.setAttribute(SportletProperties.PORTLET_ACTION_METHOD, SportletProperties.DO_TITLE);
        try {
            include(req, res);
        } catch (ServletException e) {
            log.error("Unable to perform doTitle");
            throw new PortletException("Unable to perform doTitle", e);
        }
    }

    /**
     * Notifies this listener that a portlet window has been maximized.
     *
     * @param event the window event
     * @param req the servlet request
     * @param res the servlet response
     * @throws IOException if an
     */
    public void windowEvent(WindowEvent event, HttpServletRequest req, HttpServletResponse res) throws IOException, PortletException {
        req.setAttribute(SportletProperties.WINDOW_EVENT, event);
        req.setAttribute(SportletProperties.PORTLET_LIFECYCLE_METHOD, SportletProperties.SERVICE);
        req.setAttribute(SportletProperties.PORTLET_ACTION_METHOD, SportletProperties.WINDOW_EVENT);
        try {
            include(req, res);
        } catch (ServletException e) {
            log.error("Unable to perform windowEvent");
            throw new PortletException("Unable to perform windowEvent", e);
        }
    }

    /**
     * Underlying method used by all other dispatcher methods to invoke the appropriate portlet lifecycle method
     *
     * @param req a <code>HttpServletRequest</code>
     * @param res a <code>HttpServletResponse</code>
     */
    protected void include(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        rd.include(req, res);
    }

}






