/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: Portlet.java 4849 2006-06-10 18:08:07Z novotny $
 */
package org.gridsphere.portlet.impl;

import org.gridsphere.portlet.*;
import org.gridsphere.portletcontainer.impl.PortletSessionManager;
import org.gridsphere.portletcontainer.impl.descriptor.ApplicationSportletConfig;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.Enumeration;

/**
 * A portlet is a small Java program that runs within a portlet container.
 * Portlets receive and respond to requests from the portlet container.
 * There is ever only one portlet object instance per portlet configuration in the ui deployment descriptor.
 * There may be many PortletSettings objects parameterisng the same portlet object according to the
 * Flyweight pattern, provided on a per-request basis. A concrete parameterization of a portlet object
 * is referred to as a concrete portlet. The settings of concrete portlets may change at any time caused
 * by administrators modifying portlet settings, e.g. using the config mode of a portlet.
 * <p/>
 * Additionally, user can have personal views of concrete portlets. Therefore, the transient portlet session
 * and persistent concrete portlet data carries vital information for the portlet to create a personalized
 * user experience. A concrete portlet in conjunction with portlet data creates a concrete portlet instance.
 * This is similar to why a servlet may not store things depending on requests or sessions in instance variables.
 * As a consequence, the portlet should not attempt to store any data that depends on portlet settings,
 * portlet data or the portlet session or any other user-related information as instance or class variables.
 * The general programming rules for servlets also apply to portlets - instance variables should only used
 * when the intent is to share them between all the parallel threads that concurrently execute a portlet, and
 * portlet code should avoid synchronization unless absolutely required.
 * <p/>
 * As part of running within the portlet container each portlet has a life-cycle.
 * The corresponding methods are called in the following sequence:
 * <p/>
 * <ol>
 * <li>The portlet is constructed, then initialized with the init() method.</li>
 * <li>A concrete portlet is initialized with the {@link #initConcrete} method for each PortletSettings.</li>
 * <li>Any calls from the portlet container to the service() method are handled.</li>
 * <li>The concrete portlet is taken out of service with the destroyConcrete() method.</li>
 * <li>The portlet is taken out of service, then destroyed with the destroy() method,
 * then garbage collected and finalized.</li>
 * </ol>
 * <p/>
 * The <it>concrete portlet instance</it> is created and destroyed with the login() and logout() methods, respectively.
 * If a portlet provides personalized views these methods should be implemented.
 * <p/>
 * The portlet container loads and instantiates the portlet class.
 * This can happen during startup of the portal server or later,
 * but no later then when the first request to the portlet has to be serviced.
 * Also, if a portlet is taken out of service temporarily, for example while administrating it,
 * the portlet container may finish the life-cycle before taking the portlet out of service.
 * When the administration is done, the portlet will be newly initialized.
 */
public abstract class Portlet extends HttpServlet
        implements PortletSessionListener, Servlet, ServletConfig, Serializable, ServletContextListener,
        HttpSessionAttributeListener, HttpSessionListener {

    private static PortletSessionManager sessionManager = PortletSessionManager.getInstance();

    protected PortletConfig portletConfig = null;
    protected PortletSettings portletSettings = null;

    protected transient static PortletLog log = SportletLog.getInstance(Portlet.class);

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
     * @param config the portlet configuration
     * @throws UnavailableException if an exception has occurrred that interferes with the portlet's
     *                              normal initialization
     */
    public abstract void init(PortletConfig config) throws UnavailableException;

    /**
     * Called by the portlet container to indicate to this portlet that it is taken out of service.
     * This method is only called once all threads within the portlet's service() method have exited
     * or after a timeout period has passed. After the portlet container calls this method,
     * it will not call the service() method again on this portlet.
     * <p/>
     * This method gives the portlet an opportunity to clean up any resources that are
     * being held (for example, memory, file handles, threads).
     *
     * @param config the portlet configuration
     */
    public abstract void destroy(PortletConfig config);

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
    public abstract void initConcrete(PortletSettings settings) throws UnavailableException;

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
    public abstract void destroyConcrete(PortletSettings settings);

    /**
     * Called by the portlet container to ask this portlet to generate its markup using the given
     * request/response pair. Depending on the mode of the portlet and the requesting client device,
     * the markup will be different. Also, the portlet can take language preferences and/or
     * personalized settings into account.
     *
     * @param request  the portlet request
     * @param response the portlet response
     * @throws PortletException if the portlet has trouble fulfilling the rendering request
     * @throws IOException      if the streaming causes an I/O problem
     */
    public abstract void service(PortletRequest request, PortletResponse response)
            throws PortletException, IOException;

    /**
     * Description copied from interface: PortletSessionListener
     * Called by the portlet container to ask the portlet to initialize a personalized user experience.
     * In addition to initializing the session this method allows the portlet to initialize the
     * concrete portlet instance, for example, to store attributes in the session.
     *
     * @param request the portlet request
     */
    public abstract void login(PortletRequest request);

    /**
     * Description copied from interface: PortletSessionListener
     * Called by the portlet container to indicate that a concrete portlet instance is being removed.
     * This method gives the concrete portlet instance an opportunity to clean up any resources
     * (for example, memory, file handles, threads), before it is removed.
     * This happens if the user logs out, or decides to remove this portlet from a page.
     *
     * @param session the portlet session
     */
    public abstract void logout(PortletSession session);

    /**
     * Returns the time the response of the PortletInfo  object was last modified, in milliseconds since midnight
     * January 1, 1970 GMT. If the time is unknown, this method returns a negative number (the default).
     * <p/>
     * Portlets that can quickly determine their last modification time should override this method.
     * This makes browser and proxy caches work more effectively, reducing the load on server and network resources.
     *
     * @param request the portlet request
     * @return long a long integer specifying the time the response of the PortletInfo
     *         object was last modified, in milliseconds since midnight, January 1, 1970 GMT, or -1 if the time is not known
     */
    public abstract long getLastModified(PortletRequest request);

    /**
     * Returns the PortletConfig object of the portlet
     *
     * @return the PortletConfig object
     */
    protected abstract PortletConfig getPortletConfig();

    /**
     * Returns the PortletSettings object of the concrete portlet.
     *
     * @return the PortletSettings object, or NULL if no PortletSettings object is available.
     */
    protected PortletSettings getPortletSettings() {
        return this.portletSettings;
    }

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        /*
        String propsFile = config.getServletContext().getRealPath("/WEB-INF/classes/log4j.properties");
        File f = new File(propsFile);
        if (f.exists()) {
            //System.err.println("configuring to use " + propsFile);
            SportletLog.setConfigureURL(propsFile);
        }
        */
    }

    public void init() throws ServletException {
        super.init();
    }

    public final ServletConfig getServletConfig() {
        return super.getServletConfig();
    }

    public final String getInitParameter(String name) {
        return super.getInitParameter(name);
    }

    public final Enumeration getInitParameterNames() {
        return super.getInitParameterNames();
    }

    public final ServletContext getServletContext() {
        return super.getServletContext();
    }

    protected long getLastModified(HttpServletRequest req) {
        return super.getLastModified(req);
    }

    public String getServletInfo() {
        return super.getServletInfo();
    }

    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        super.service(request, response);
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // create portlet request and response objects
        PortletRequest portletRequest = new SportletRequest(request);
        PortletResponse portletResponse = new SportletResponse(response, portletRequest);

        
        String method = (String) request.getAttribute(SportletProperties.PORTLET_LIFECYCLE_METHOD);
        if (method != null) {

            if (method.equals(SportletProperties.INIT)) {
                ApplicationSportletConfig app = (ApplicationSportletConfig) request.getAttribute(SportletProperties.PORTLET_APPLICATION);
                if (app != null) {
                    this.portletConfig = new SportletConfig(getServletConfig(), app);
                    try {
                        init(this.portletConfig);
                    } catch (Exception e) {
                        log.error("Unable to initialize portlet", e);
                    }
                } else {
                    log.error("Unable to perform init(): Received NULL PortletApplication in request");
                }
            } else if (method.equals(SportletProperties.SERVICE)) {
                service(portletRequest, portletResponse);
            } else if (method.equals(SportletProperties.DESTROY)) {
                destroy(this.portletConfig);
            } else if (method.equals(SportletProperties.INIT_CONCRETE)) {
                PortletSettings settings = (PortletSettings) request.getAttribute(SportletProperties.PORTLET_SETTINGS);
                if (settings != null) {
                    try {
                        initConcrete(settings);
                    } catch (Exception e) {
                        log.error("Unable to initialize concrete portlet", e);
                    }
                } else {
                    log.error("Unable to perform initConcrete(): Received NULL PortletSettings in request");
                }
            } else if (method.equals(SportletProperties.DESTROY_CONCRETE)) {
                PortletSettings settings = (PortletSettings) request.getAttribute(SportletProperties.PORTLET_SETTINGS);
                if (settings != null) {
                    destroyConcrete(settings);
                } else {
                    log.error("Unable to perform destroyConcrete(): Received NULL PortletSettings in request");
                }
            } else if (method.equals(SportletProperties.LOGIN)) {
                login(portletRequest);
            } else if (method.equals(SportletProperties.LOGOUT)) {
                PortletSession portletSession = portletRequest.getPortletSession();
                logout(portletSession);
            }

        }
        request.removeAttribute(SportletProperties.PORTLET_LIFECYCLE_METHOD);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        super.doGet(req, res);
    }

    protected void doPut(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        super.doPut(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        super.doPost(req, res);
    }

    protected void doTrace(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        super.doTrace(req, res);
    }

    protected void doDelete(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        super.doDelete(req, res);
    }

    public void destroy() {
        super.destroy();
    }


    /**
     * Record the fact that a servlet context attribute was added.
     *
     * @param event The session attribute event
     */
    public void attributeAdded(HttpSessionBindingEvent event) {

        log.debug("attributeAdded('" + event.getSession().getId() + "', '" +
                event.getName() + "', '" + event.getValue() + "')");

    }


    /**
     * Record the fact that a servlet context attribute was removed.
     *
     * @param event The session attribute event
     */
    public void attributeRemoved(HttpSessionBindingEvent event) {

        log.debug("attributeRemoved('" + event.getSession().getId() + "', '" +
                event.getName() + "', '" + event.getValue() + "')");

    }


    /**
     * Record the fact that a servlet context attribute was replaced.
     *
     * @param event The session attribute event
     */
    public void attributeReplaced(HttpSessionBindingEvent event) {

        log.debug("attributeReplaced('" + event.getSession().getId() + "', '" +
                event.getName() + "', '" + event.getValue() + "')");

    }


    /**
     * Record the fact that this ui application has been destroyed.
     *
     * @param event The servlet context event
     */
    public void contextDestroyed(ServletContextEvent event) {
        ServletContext ctx = event.getServletContext();
        log.debug("contextDestroyed()");
        log.debug("contextName: " + ctx.getServletContextName());
        log.debug("context path: " + ctx.getRealPath(""));

    }


    /**
     * Record the fact that this ui application has been initialized.
     *
     * @param event The servlet context event
     */
    public void contextInitialized(ServletContextEvent event) {
        log.debug("contextInitialized()");
        ServletContext ctx = event.getServletContext();
        log.debug("contextName: " + ctx.getServletContextName());
        log.debug("context path: " + ctx.getRealPath(""));

    }

    /**
     * Record the fact that a session has been created.
     *
     * @param event The session event
     */
    public void sessionCreated(HttpSessionEvent event) {
        log.debug("in Portlet sessionCreated('" + event.getSession().getId() + "')");
        sessionManager.sessionCreated(event);
    }


    /**
     * Record the fact that a session has been destroyed.
     *
     * @param event The session event
     */
    public void sessionDestroyed(HttpSessionEvent event) {
        sessionManager.sessionDestroyed(event);
        log.debug("in Portlet: sessionDestroyed('" + event.getSession().getId() + "')");
    }

}










































