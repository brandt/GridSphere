/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: JSRPortletDispatcher.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.portletcontainer.jsrimpl;

import org.gridsphere.event.WindowEvent;
import org.gridsphere.portlet.*;
import org.gridsphere.portlet.impl.SportletLog;
import org.gridsphere.portlet.impl.SportletProperties;
import org.gridsphere.portletcontainer.ApplicationPortletConfig;
import org.gridsphere.portletcontainer.PortletDispatcher;
import org.gridsphere.portletcontainer.PortletDispatcherException;

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
public class JSRPortletDispatcher implements PortletDispatcher {

    public static PortletLog log = SportletLog.getInstance(JSRPortletDispatcher.class);

    private RequestDispatcher rd;
    private ApplicationPortletConfig appPortletConfig = null;

    /**
     * Default constructor is kept private
     */
    private JSRPortletDispatcher() {
    }

    /**
     * Constructs a PortletDispatcher from a <code>RequestDispatcher</code> and an <code>ApplicationPortletConfig</code>
     *
     * @param rd               the <code>RequestDispatcher</code>
     * @param appPortletConfig the <code>ApplicationPortletConfig</code>
     */
    public JSRPortletDispatcher(RequestDispatcher rd, ApplicationPortletConfig appPortletConfig) {
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
     * @throws IOException      if an I/O errors occurs
     * @throws PortletException if an exception has occurrred during dispatching
     */
    public void init(HttpServletRequest req, HttpServletResponse res) throws PortletDispatcherException {
        req.setAttribute(SportletProperties.PORTLET_LIFECYCLE_METHOD, SportletProperties.INIT);
        try {
            include(req, res);
        } catch (Exception e) {
            throw new PortletDispatcherException("Unable to initialize portlet: " + appPortletConfig.getApplicationPortletID(), e);
        }
        req.removeAttribute(SportletProperties.PORTLET_LIFECYCLE_METHOD);
    }

    public void destroy(HttpServletRequest req, HttpServletResponse res) throws PortletDispatcherException {
        req.setAttribute(SportletProperties.PORTLET_LIFECYCLE_METHOD, SportletProperties.DESTROY);
        try {
            include(req, res);
        } catch (Exception e) {
            throw new PortletDispatcherException("Unable to perform destroy on: ", e);
        }
        req.removeAttribute(SportletProperties.PORTLET_LIFECYCLE_METHOD);
    }

    public void initConcrete(PortletSettings settings, HttpServletRequest req, HttpServletResponse res) throws PortletDispatcherException {
        req.setAttribute(SportletProperties.PORTLET_SETTINGS, settings);
        req.setAttribute(SportletProperties.PORTLET_LIFECYCLE_METHOD, SportletProperties.INIT_CONCRETE);
        try {
            include(req, res);
        } catch (Exception e) {
            throw new PortletDispatcherException("Unable to perform initConcrete", e);
        }
    }

    public void destroyConcrete(PortletSettings settings, HttpServletRequest req, HttpServletResponse res) throws PortletDispatcherException {
        req.setAttribute(SportletProperties.PORTLET_SETTINGS, settings);
        req.setAttribute(SportletProperties.PORTLET_LIFECYCLE_METHOD, SportletProperties.DESTROY_CONCRETE);
        try {
            include(req, res);
        } catch (Exception e) {
            throw new PortletDispatcherException("Unable to perform destroyConcrete", e);
        }
    }

    public void service(HttpServletRequest req, HttpServletResponse res) throws PortletDispatcherException {
        req.setAttribute(SportletProperties.PORTLET_LIFECYCLE_METHOD, SportletProperties.SERVICE);
        try {
            include(req, res);
        } catch (Exception e) {
            throw new PortletDispatcherException("Unable to perform service", e);
        }
    }

    public void login(HttpServletRequest req, HttpServletResponse res) throws PortletDispatcherException {
        req.setAttribute(SportletProperties.PORTLET_LIFECYCLE_METHOD, SportletProperties.LOGIN);
        try {
            include(req, res);
        } catch (Exception e) {
            throw new PortletDispatcherException("Unable to perform login", e);
        }
    }

    public void logout(HttpServletRequest req, HttpServletResponse res) throws PortletDispatcherException {
        req.setAttribute(SportletProperties.PORTLET_LIFECYCLE_METHOD, SportletProperties.LOGOUT);
        try {
            include(req, res);
        } catch (Exception e) {
            throw new PortletDispatcherException("Unable to perform logout", e);
        }
    }

    public void actionPerformed(PortletAction action, HttpServletRequest req, HttpServletResponse res) throws PortletDispatcherException {
        req.setAttribute(SportletProperties.ACTION_EVENT, action);
        req.setAttribute(SportletProperties.PORTLET_LIFECYCLE_METHOD, SportletProperties.SERVICE);
        req.setAttribute(SportletProperties.PORTLET_ACTION_METHOD, SportletProperties.ACTION_PERFORMED);
        try {
            include(req, res);
        } catch (Exception e) {
            throw new PortletDispatcherException("Unable to perform actionPerformed", e);
        }
    }

    public void messageEvent(String concreteID, PortletMessage message, HttpServletRequest req, HttpServletResponse res) throws PortletDispatcherException {
        req.setAttribute(SportletProperties.MESSAGE_EVENT, message);
        req.setAttribute(SportletProperties.PORTLET_LIFECYCLE_METHOD, SportletProperties.SERVICE);
        req.setAttribute(SportletProperties.PORTLET_ACTION_METHOD, SportletProperties.MESSAGE_RECEIVED);
        try {
            include(req, res);
        } catch (Exception e) {
            throw new PortletDispatcherException("Unable to perform messageEvent", e);
        }
    }

    public void doTitle(HttpServletRequest req, HttpServletResponse res) throws PortletDispatcherException {
        req.setAttribute(SportletProperties.PORTLET_LIFECYCLE_METHOD, SportletProperties.SERVICE);
        req.setAttribute(SportletProperties.PORTLET_ACTION_METHOD, SportletProperties.DO_TITLE);
        try {
            include(req, res);
        } catch (Exception e) {
            throw new PortletDispatcherException("Unable to perform doTitle", e);
        }
    }

    public void windowEvent(WindowEvent event, HttpServletRequest req, HttpServletResponse res) throws PortletDispatcherException {
        req.setAttribute(SportletProperties.WINDOW_EVENT, event);
        req.setAttribute(SportletProperties.PORTLET_LIFECYCLE_METHOD, SportletProperties.SERVICE);
        req.setAttribute(SportletProperties.PORTLET_ACTION_METHOD, SportletProperties.WINDOW_EVENT);
        try {
            include(req, res);
        } catch (Exception e) {
            log.error("Unable to perform windowEvent");
            throw new PortletDispatcherException("Unable to perform windowEvent", e);
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






