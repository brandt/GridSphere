/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: SportletDispatcher.java 4848 2006-06-10 18:06:15Z novotny $
 */
package org.gridsphere.portletcontainer.impl;

import org.gridsphere.event.WindowEvent;
import org.gridsphere.portlet.*;
import org.gridsphere.portlet.impl.SportletLog;
import org.gridsphere.portlet.impl.SportletProperties;
import org.gridsphere.portletcontainer.PortletDispatcher;
import org.gridsphere.portletcontainer.impl.descriptor.ApplicationSportletConfig;

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
public class SportletDispatcher implements PortletDispatcher {

    public static PortletLog log = SportletLog.getInstance(SportletDispatcher.class);

    private RequestDispatcher rd;
    private ApplicationSportletConfig appPortletConfig = null;

    /**
     * Default constructor is kept private
     */
    private SportletDispatcher() {
    }

    /**
     * Constructs a PortletDispatcher from a <code>RequestDispatcher</code> and an <code>ApplicationPortletConfig</code>
     *
     * @param rd               the <code>RequestDispatcher</code>
     * @param appPortletConfig the <code>ApplicationPortletConfig</code>
     */
    public SportletDispatcher(RequestDispatcher rd, ApplicationSportletConfig appPortletConfig) {
        this.rd = rd;
        this.appPortletConfig = appPortletConfig;
    }

    /**
     * Called by the portlet container to indicate to this portlet that it is put into service.
     * <p/>
     * The portlet container calls the init() method for the whole life-cycle of the portlet.
     * The init() method must complete successfully before concrete portlets are created through
     * the initConcrete() method.
     * <p/>
     * The portlet container cannot place the portlet into service if the init() method
     * <p/>
     * 1. throws UnavailableException
     * 2. does not return within a time period defined by the portlet container.
     *
     * @param req the servlet request
     * @param res the servlet response
     * @throws java.io.IOException if an I/O errors occurs
     * @throws org.gridsphere.portlet.PortletException
     *                             if an exception has occurrred during dispatching
     */
    public void init(HttpServletRequest req, HttpServletResponse res) throws IOException, PortletException {
        if (appPortletConfig != null) req.setAttribute(SportletProperties.PORTLET_APPLICATION, appPortletConfig);
        req.setAttribute(SportletProperties.PORTLET_LIFECYCLE_METHOD, SportletProperties.INIT);
        try {
            include(req, res);
        } catch (ServletException e) {
            throw new PortletException("Unable to initialize portlet: " + appPortletConfig.getApplicationPortletID(), e);
        }
    }

    /**
     * Called by the portlet container to indicate to this portlet that it is taken out of service.
     * This method is only called once all threads within the portlet's service() method have exited
     * or after a timeout period has passed. After the portlet container calls this method,
     * it will not call the service() method again on this portlet.
     * <p/>
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
            throw new PortletException("Unable to perform destroy on: ", e);
        }
    }

    /**
     * Called by the portlet container to indicate that the concrete portlet is put into service.
     * The portlet container calls the initConcrete() method for the whole life-cycle of the portlet.
     * The initConcrete() method must complete successfully before concrete portlet instances can be
     * created through the login() method.
     * <p/>
     * The portlet container cannot place the portlet into service if the initConcrete() method
     * <p/>
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
            throw new PortletException("Unable to perform initConcrete", e);
        }
    }

    /**
     * Called by the portlet container to indicate that the concrete portlet is taken out of service.
     * This method is only called once all threads within the portlet's service() method have exited
     * or after a timeout period has passed. After the portlet container calls this method,
     * it will not call the service() method again on this portlet.
     * <p/>
     * This method gives the portlet an opportunity to clean up any resources that are being
     * held (for example, memory, file handles, threads).
     *
     * @param settings the portlet settings
     */
    public void destroyConcrete(PortletSettings settings, HttpServletRequest req, HttpServletResponse res) throws IOException, PortletException {
        req.setAttribute(SportletProperties.PORTLET_SETTINGS, settings);
        req.setAttribute(SportletProperties.PORTLET_LIFECYCLE_METHOD, SportletProperties.DESTROY_CONCRETE);
        try {
            include(req, res);
        } catch (ServletException e) {
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
     * @throws org.gridsphere.portlet.PortletException
     *                             if the portlet has trouble fulfilling the rendering request
     * @throws java.io.IOException if the streaming causes an I/O problem
     */
    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, PortletException {
        req.setAttribute(SportletProperties.PORTLET_LIFECYCLE_METHOD, SportletProperties.SERVICE);
        try {
            include(req, res);
        } catch (ServletException e) {
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
            throw new PortletException("Unable to perform login", e);
        }
    }

    /**
     * Performs a portlet logout dispatch request.
     *
     * @param req the servlet request
     * @param res the servlet response
     * @throws java.io.IOException if an I/O error occurs
     * @throws org.gridsphere.portlet.PortletException
     *                             is an error occurs dispatching the request
     */
    public void logout(HttpServletRequest req, HttpServletResponse res) throws IOException, PortletException {
        req.setAttribute(SportletProperties.PORTLET_LIFECYCLE_METHOD, SportletProperties.LOGOUT);
        try {
            include(req, res);
        } catch (ServletException e) {
            throw new PortletException("Unable to perform logout", e);
        }
    }

    /**
     * Called by the portlet container to ask this portlet to perform the required operational logic
     * using the given portlet request.
     * Notifies this listener that the action which the listener is watching for has been performed.
     *
     * @param action the default portlet action
     * @param req    the servlet request
     * @param res    the servlet response
     * @throws java.io.IOException if an I/O error occurs
     * @throws org.gridsphere.portlet.PortletException
     *                             if the listener has trouble fulfilling the request
     */
    public void actionPerformed(PortletAction action, HttpServletRequest req, HttpServletResponse res) throws IOException, PortletException {
        req.setAttribute(SportletProperties.ACTION_EVENT, action);
        req.setAttribute(SportletProperties.PORTLET_LIFECYCLE_METHOD, SportletProperties.SERVICE);
        req.setAttribute(SportletProperties.PORTLET_ACTION_METHOD, SportletProperties.ACTION_PERFORMED);
        try {
            include(req, res);
        } catch (ServletException e) {
            throw new PortletException("Unable to perform actionPerformed", e);
        }
    }

    /**
     * Notifies this listener that the message which the listener is watching for has been performed.
     *
     * @param concreteID the concrete portlet id
     * @param message    the default portlet message
     * @param req        the servlet request
     * @param res        the servlet response
     * @throws java.io.IOException if an I/O error occurs during dispatching
     * @throws org.gridsphere.portlet.PortletException
     *                             if the listener has trouble fulfilling the request
     */
    public void messageEvent(String concreteID, PortletMessage message, HttpServletRequest req, HttpServletResponse res) throws IOException, PortletException {
        req.setAttribute(SportletProperties.MESSAGE_EVENT, message);
        req.setAttribute(SportletProperties.PORTLET_LIFECYCLE_METHOD, SportletProperties.SERVICE);
        req.setAttribute(SportletProperties.PORTLET_ACTION_METHOD, SportletProperties.MESSAGE_RECEIVED);
        try {
            include(req, res);
        } catch (ServletException e) {
            throw new PortletException("Unable to perform messageEvent", e);
        }
    }

    /**
     * Called by the portlet container to render the portlet title.
     * The information in the portlet request (like locale, client, and session information) can
     * but doesn't have to be considered to render dynamic titles.. Examples are
     * <p/>
     * language-dependant titles for multi-lingual portals
     * shorter titles for WAP phones
     * the number of messages in a mailbox portlet
     * The session may be null, if the user is not logged in.
     *
     * @param req the servlet request
     * @param res the servlet response
     * @throws java.io.IOException if an I/O error occurs during dispatching
     * @throws org.gridsphere.portlet.PortletException
     *                             if the portlet title has trouble fulfilling the rendering request
     */
    public void doTitle(HttpServletRequest req, HttpServletResponse res) throws IOException, PortletException {
        req.setAttribute(SportletProperties.PORTLET_LIFECYCLE_METHOD, SportletProperties.SERVICE);
        req.setAttribute(SportletProperties.PORTLET_ACTION_METHOD, SportletProperties.DO_TITLE);
        try {
            include(req, res);
        } catch (ServletException e) {
            throw new PortletException("Unable to perform doTitle", e);
        }
    }


    public void windowEvent(WindowEvent event, HttpServletRequest req, HttpServletResponse res) throws IOException, PortletException {
        req.setAttribute(SportletProperties.WINDOW_EVENT, event);
        req.setAttribute(SportletProperties.PORTLET_LIFECYCLE_METHOD, SportletProperties.SERVICE);
        req.setAttribute(SportletProperties.PORTLET_ACTION_METHOD, SportletProperties.WINDOW_EVENT);
        try {
            include(req, res);
        } catch (ServletException e) {
            throw new PortletException("Unable to perform windowEvent", e);
        }
    }

    /**
     * Underlying method used by all other dispatcher methods to invoke the appropriate portlet lifecycle method
     *
     * @param req a <code>HttpServletRequest</code>
     * @param res a <code>HttpServletResponse</code>
     */
    protected synchronized void include(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        rd.include(req, res);
    }

}






