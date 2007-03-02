/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: PortletInvoker.java 4848 2006-06-10 18:06:15Z novotny $
 */
package org.gridsphere.portletcontainer.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gridsphere.layout.event.PortletWindowEvent;
import org.gridsphere.portlet.service.PortletServiceException;
import org.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridsphere.portletcontainer.*;
import org.gridsphere.services.core.registry.PortletRegistryService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The <code>PortletInvoker</code> provides static lifecycle routines for performing portlet operations on
 * concrete portlets.
 *
 * @see org.gridsphere.portletcontainer.impl.PortletDispatcherImpl
 */
public class PortletInvoker {

    private Log log = LogFactory.getLog(PortletInvoker.class);
    private PortletRegistryService registry = null;

    public PortletInvoker() {
        try {
            registry = (PortletRegistryService) PortletServiceFactory.createPortletService(PortletRegistryService.class, true);
        } catch (PortletServiceException e) {
            log.error("Unable to init services! ", e);
        }
    }

    /**
     * Performs service method on a concrete portlet instance
     *
     * @param concretePortletID the concrete portlet id
     * @param render            the default portlet render
     * @param req               the <code>HttpServletRequest</code>
     * @param res               the <code>HttpServletResponse</code>
     * @throws PortletDispatcherException if a dispatching error occurs
     * @throws IOException                if an I/O error occurs
     */
    public void service(String concretePortletID, DefaultPortletRender render, HttpServletRequest req, HttpServletResponse res) throws IOException, PortletDispatcherException {
        log.debug("in service " + concretePortletID);
        String appID = registry.getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        if (appPortlet != null) {
            PortletDispatcher dispatcher = appPortlet.getPortletDispatcher(req, res);
            dispatcher.service(render, req, res);
        } else {
            log.info("in service: Unable to find portlet in registry: " + concretePortletID);
        }
    }

    /**
     * Performs action performed method on a concrete portlet instance
     *
     * @param concretePortletID the concrete portlet id
     * @param action            the default portlet action
     * @param req               the <code>HttpServletRequest</code>
     * @param res               the <code>HttpServletResponse</code>
     * @throws PortletDispatcherException if a dispatching error occurs
     */
    public void actionPerformed(String concretePortletID, DefaultPortletAction action, HttpServletRequest req, HttpServletResponse res) throws PortletDispatcherException {
        log.debug("in actionPerformed " + concretePortletID);
        String appID = registry.getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        if (appPortlet != null) {
            PortletDispatcher dispatcher = appPortlet.getPortletDispatcher(req, res);
            dispatcher.actionPerformed(action, req, res);
        } else {
            log.info("in actionPerformed: Unable to find portlet in registry: " + concretePortletID);
        }
    }

    /**
     * Performs doTitle method on a concrete portlet instance
     *
     * @param concretePortletID the concrete portlet id
     * @param req               the <code>HttpServletRequest</code>
     * @param res               the <code>HttpServletResponse</code>
     * @throws PortletDispatcherException if a dispatching error occurs
     */
    public void doTitle(String concretePortletID, HttpServletRequest req, HttpServletResponse res) throws PortletDispatcherException {
        log.debug("in doTitle " + concretePortletID);
        String appID = registry.getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        if (appPortlet != null) {
            PortletDispatcher dispatcher = appPortlet.getPortletDispatcher(req, res);
            dispatcher.doTitle(req, res);
        } else {
            log.info("in actionPerformed: Unable to find portlet in registry: " + concretePortletID);
        }
    }

    /**
     * Performs window event method on a concrete portlet instance
     *
     * @param concretePortletID the concrete portlet id
     * @param winEvent          the window event
     * @param req               the <code>HttpServletRequest</code>
     * @param res               the <code>HttpServletResponse</code>
     * @throws PortletDispatcherException if a dispatching error occurs
     */
    public void windowEvent(String concretePortletID, PortletWindowEvent winEvent, HttpServletRequest req, HttpServletResponse res) throws PortletDispatcherException {
        log.debug("in windowEvent " + concretePortletID);
        String appID = registry.getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        if (appPortlet != null) {
            PortletDispatcher dispatcher = appPortlet.getPortletDispatcher(req, res);
            dispatcher.windowEvent(winEvent, req, res);
        } else {
            log.info("in windowEvent: Unable to find portlet in registry: " + concretePortletID);
        }
    }

    public void initPortletWebApp(PortletWebApplicationLoader appLoader, HttpServletRequest req, HttpServletResponse res) throws PortletDispatcherException {
        // Initialize all concrete portlets for each application portlet
        PortletDispatcher portletDispatcher = appLoader.getPortletDispatcher();
        // initialize the application portlet
        log.debug("initializing portlet web app " + appLoader.getWebApplicationName());
        portletDispatcher.init(req, res);

    }

    /**
     * Lohgout a portlet web application
     *
     * @param appLoader the web application loader
     * @param req       the <code>HttpServletRequest</code>
     * @param res       the <code>HttpServletResponse</code>
     * @throws PortletDispatcherException if a dispatching error occurs
     */
    public void logoutPortletWebApp(PortletWebApplicationLoader appLoader, HttpServletRequest req, HttpServletResponse res) throws PortletDispatcherException {
        PortletDispatcher portletDispatcher = appLoader.getPortletDispatcher();
        portletDispatcher.logout(req, res);
    }


    /**
     * Shuts down a portlet web application
     *
     * @param webAppLoader the name of the JSR portlet web application loader
     * @param req          the <code>HttpServletRequest</code>
     * @param res          the <code>HttpServletResponse</code>
     * @throws PortletDispatcherException if a dispatching error occurs
     */
    public void destroyPortletWebApp(PortletWebApplicationLoader webAppLoader, HttpServletRequest req, HttpServletResponse res) throws PortletDispatcherException {
        // First destroy all concrete portlets for each application portlet
        log.debug("in destroyPortletWebApp " + webAppLoader.getWebApplicationName());
        // destroy the application portlet
        webAppLoader.getPortletDispatcher().destroy(req, res);
    }

    /**
     * Returns the application portlet id from the supplied concrete portlet id
     *
     * @param concretePortletID the concrete portlet id
     * @return the application portlet id
     */
    protected static String getApplicationPortletID(String concretePortletID) {
        int i = concretePortletID.lastIndexOf(".");
        if (i < 0) return "";
        return concretePortletID.substring(0, i);
    }

}
