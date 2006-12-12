package org.gridsphere.portletcontainer;

import org.gridsphere.layout.event.PortletWindowEvent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: PortletDispatcher.java 4496 2006-02-08 20:27:04Z wehrens $
 */
public interface PortletDispatcher {

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
     * @throws PortletDispatcherException if an exception has occurrred during dispatching
     */
    public void init(HttpServletRequest req, HttpServletResponse res) throws PortletDispatcherException;

    /**
     * Called by the portlet container to indicate to this portlet that it is taken out of service.
     * This method is only called once all threads within the portlet's service() method have exited
     * or after a timeout period has passed. After the portlet container calls this method,
     * it will not call the service() method again on this portlet.
     * <p/>
     * This method gives the portlet an opportunity to clean up any resources that are
     * being held (for example, memory, file handles, threads).
     *
     * @param req the servlet request
     * @param res the servlet response
     * @throws PortletDispatcherException if an exception has occurrred during dispatching
     */
    public void destroy(HttpServletRequest req, HttpServletResponse res) throws PortletDispatcherException;

    /**
     * Called by the portlet container to ask this portlet to generate its markup using the given
     * request/response pair. Depending on the mode of the portlet and the requesting client device,
     * the markup will be different. Also, the portlet can take language preferences and/or
     * personalized settings into account.
     *
     * @param render the default portlet render
     * @param req    the servlet request
     * @param res    the servlet response
     * @throws PortletDispatcherException if an exception has occurrred during dispatching
     */
    public void service(DefaultPortletRender render, HttpServletRequest req, HttpServletResponse res) throws PortletDispatcherException;

    /**
     * Performs a portlet login dispatch request.
     *
     * @param req the servlet request
     * @param res the servlet response
     * @throws PortletDispatcherException if an exception has occurrred during dispatching
     */
    public void login(HttpServletRequest req, HttpServletResponse res) throws PortletDispatcherException;

    /**
     * Performs a portlet logout dispatch request.
     *
     * @param req the servlet request
     * @param res the servlet response
     * @throws PortletDispatcherException if an exception has occurrred during dispatching
     */
    public void logout(HttpServletRequest req, HttpServletResponse res) throws PortletDispatcherException;

    /**
     * Called by the portlet container to ask this portlet to perform the required operational logic
     * using the given portlet request.
     * Notifies this listener that the action which the listener is watching for has been performed.
     *
     * @param action the default portlet action
     * @param req    the servlet request
     * @param res    the servlet response
     * @throws PortletDispatcherException if an exception has occurrred during dispatching
     */
    public void actionPerformed(DefaultPortletAction action, HttpServletRequest req, HttpServletResponse res) throws PortletDispatcherException;

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
     * @throws PortletDispatcherException if an exception has occurrred during dispatching
     */
    public void doTitle(HttpServletRequest req, HttpServletResponse res) throws PortletDispatcherException;

    /**
     * Notifies this listener that a portlet window has been maximized.
     *
     * @param event the window event
     * @param req   the servlet request
     * @param res   the servlet response
     * @throws PortletDispatcherException if an exception has occurrred during dispatching
     */
    public void windowEvent(PortletWindowEvent event, HttpServletRequest req, HttpServletResponse res) throws PortletDispatcherException;

}
