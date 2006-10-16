/**
 * Copyright (c) 2004 Grad-Soft Ltd, Kiev, Ukraine
 * http://www.gradsoft.ua
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.gridsphere.servlets;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gridsphere.layout.PortletLayoutEngine;
import org.gridsphere.portlet.impl.SportletProperties;
import org.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridsphere.services.core.registry.PortletManagerService;
import org.gridsphere.services.core.persistence.PersistenceManagerService;
import org.gridsphere.services.core.persistence.PersistenceManagerRdbms;
import org.gridsphere.services.core.security.role.PortletRole;
import org.gridsphere.services.core.security.role.RoleManagerService;
import org.hibernate.StaleObjectStateException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.File;
import java.util.StringTokenizer;

/**
 * GridSphereFilter is used for first time portal initialization including portlets
 */
public class GridSphereFilter implements Filter {

    private static Boolean firstDoGet = Boolean.TRUE;

    private Log log = LogFactory.getLog(GridSphereFilter.class);
    private RoleManagerService roleService = null;
    private ServletContext context = null;

    public void init(FilterConfig filterConfig) {
        context = filterConfig.getServletContext();
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        if ((request instanceof HttpServletRequest) && (response instanceof HttpServletResponse)) {
            HttpServletRequest req = (HttpServletRequest)request;
            HttpServletResponse res = (HttpServletResponse)response;

            PersistenceManagerService pms = null;

            // If first time being called, instantiate all portlets
            if (firstDoGet.equals(Boolean.TRUE)) {

                

                // check if database file exists
                PortletLayoutEngine layoutEngine = PortletLayoutEngine.getInstance();
                layoutEngine.init(context);

                String release = SportletProperties.getInstance().getProperty("gridsphere.release");
                int idx = release.lastIndexOf(" ");
                String gsversion = release.substring(idx+1);

                //System.err.println("gsversion=" + gsversion);

                String dbpath = context.getRealPath("/WEB-INF/CustomPortal/database/GS_" + gsversion);

                File dbfile = new File(dbpath);

                if (!dbfile.exists()) {
                    request.setAttribute("setup", "true");
                    RequestDispatcher rd = request.getRequestDispatcher("/setup");
                    rd.forward(request, response);
                    return;
                }
                
                roleService = (RoleManagerService) PortletServiceFactory.createPortletService(RoleManagerService.class, true);
                if ((roleService.getUsersInRole(PortletRole.ADMIN)).isEmpty()) {
                    request.setAttribute("setup", "true");
                    RequestDispatcher rd = request.getRequestDispatcher("/setup");
                    rd.forward(request, response);
                    return;
                }

                System.err.println("Initializing portlets!!!");
                log.info("Initializing portlets");
                try {
                    // initialize all portlets
                    PortletManagerService portletManager = (PortletManagerService)PortletServiceFactory.createPortletService(PortletManagerService.class, true);
                    portletManager.initAllPortletWebApplications(req, res);
                    firstDoGet = Boolean.FALSE;
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("GridSphere initialization failed!", e);
                    RequestDispatcher rd = request.getRequestDispatcher("/jsp/errors/init_error.jsp");
                    request.setAttribute("error", e);
                    rd.forward(request, response);
                    return;
                }

            }

            System.err.println("created a PM!!!");
                           log.debug("created a PM!!!");
                           log.info("created a PM!!!");

            String pathInfo = req.getPathInfo();
            StringBuffer requestURL = req.getRequestURL();
            String requestURI = req.getRequestURI();
            String query = req.getQueryString();

            log.info("\n pathInfo= " + pathInfo + " query= " + query);
            log.info(" requestURL= " + requestURL + " requestURI= " + requestURI + "\n");
            if (query == null) {
                query = "";
            } else {
                query = "&" + query;
            }

            String extraInfo = "";

            if (pathInfo != null) {
                extraInfo = "?";
                StringTokenizer st = new StringTokenizer(pathInfo, "/");

                if (st.hasMoreTokens()) {
                    String layoutId = (String)st.nextElement();
                    extraInfo += SportletProperties.LAYOUT_PAGE_PARAM + "=" + layoutId;
                }
                if (st.hasMoreTokens()) {
                    String cid = (String)st.nextElement();
                    extraInfo += "&" + SportletProperties.COMPONENT_ID+ "=" + cid;
                }
                if (st.hasMoreTokens()) {
                    String action = (String)st.nextElement();
                    extraInfo += "&" + SportletProperties.DEFAULT_PORTLET_ACTION + "=" + action;
                }
                extraInfo +=  query;

                //String ctxPath = hreq.getContextPath();
                //String ctxPath = "/" + SportletProperties.getInstance().getProperty("gridsphere.context");
                String ctxPath = "/" + SportletProperties.getInstance().getProperty("gridsphere.context");
                log.info("forwarded URL: " + ctxPath + extraInfo);
                context.getRequestDispatcher(ctxPath + extraInfo).forward(req, res);

            } else {

                pms = (PersistenceManagerService)PortletServiceFactory.createPortletService(PersistenceManagerService.class, true);

                PersistenceManagerRdbms pm = pms.createGridSphereRdbms();

                try {
                    log.info("Starting a database transaction");
                    pm.beginTransaction();

                    chain.doFilter(request, response);
                    // Commit and cleanup
                    log.info("Committing the database transaction");
                    pm.endTransaction();
                } catch (StaleObjectStateException staleEx) {
                    log.error("This interceptor does not implement optimistic concurrency control!");
                    log.error("Your application will not work until you add compensation actions!");
                    // Rollback, close everything, possibly compensate for any permanent changes
                    // during the conversation, and finally restart business conversation. Maybe
                    // give the user of the application a chance to merge some of his work with
                    // fresh data... what you do here depends on your applications design.
                    throw staleEx;
                } catch (Throwable ex) {

                    try {
                        pm.rollbackTransaction();
                    } catch (Throwable rbEx) {
                        log.error("Could not rollback transaction after exception!", rbEx);
                    }

                    // Let others handle it... maybe another interceptor for exceptions?
                    //throw new ServletException(ex);
                }

            }

        }
    }
}