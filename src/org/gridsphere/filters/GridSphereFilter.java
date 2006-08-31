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

package org.gridsphere.filters;

import org.gridsphere.portlet.PortletResponse;
import org.gridsphere.portlet.PortletLog;
import org.gridsphere.portlet.PortletRequest;
import org.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridsphere.portlet.impl.SportletLog;
import org.gridsphere.portlet.impl.SportletRequest;
import org.gridsphere.portlet.impl.SportletResponse;
import org.gridsphere.portlet.impl.SportletProperties;
import org.gridsphere.services.core.registry.PortletManagerService;
import org.gridsphere.layout.PortletLayoutEngine;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * GridSphereFilter is used for first time portal initialization including portlets
 */
public class GridSphereFilter implements Filter {

    private static Boolean firstDoGet = Boolean.TRUE;
    private PortletLog log = SportletLog.getInstance(GridSphereFilter.class);
    private ServletContext context = null;

    public void init(FilterConfig filterConfig) {
        context = filterConfig.getServletContext();
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        // If first time being called, instantiate all portlets
        if (firstDoGet.equals(Boolean.TRUE)) {
            firstDoGet = Boolean.FALSE;
            PortletLayoutEngine layoutEngine = PortletLayoutEngine.getInstance();
            layoutEngine.init(context);
            log.debug("Initializing portlets");
            try {
                // initialize needed services
                PortletManagerService portletManager = (PortletManagerService)PortletServiceFactory.createPortletService(PortletManagerService.class, true);

                // initialize all portlets
                if ((request instanceof HttpServletRequest) && (response instanceof HttpServletResponse)) {
                    PortletRequest portletRequest = new SportletRequest((HttpServletRequest)request);
                    PortletResponse portletResponse = new SportletResponse((HttpServletResponse)response, portletRequest);
                    portletManager.initAllPortletWebApplications(portletRequest, portletResponse);
                }
            } catch (Exception e) {
                log.error("GridSphere initialization failed!", e);
                RequestDispatcher rd = request.getRequestDispatcher("/jsp/errors/init_error.jsp");
                request.setAttribute("error", e);
                rd.forward(request, response);
                return;
            }
        }


        if ((request instanceof HttpServletRequest) && (response instanceof HttpServletResponse)) {
            HttpServletRequest req = (HttpServletRequest)request;
            HttpServletResponse res = (HttpServletResponse)response;
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
                return;
            }

        }
        chain.doFilter(request, response);

    }
}