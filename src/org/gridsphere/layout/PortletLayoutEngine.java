/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
package org.gridsphere.layout;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gridsphere.portlet.impl.SportletProperties;
import org.gridsphere.portletcontainer.GridSphereEvent;

import javax.portlet.PortletRequest;
import javax.portlet.RenderResponse;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.zip.GZIPOutputStream;

/**
 * The <code>PortletLayoutEngine</code> is a singleton that is responsible for managing
 * user's layouts. It also manages portlet web application default layout
 * configurations that can be potentially added to a user layout
 * via the PortletLayout Service.
 * <p/>
 * The portlet layout engine is a higher level manager of portlet containers
 * that represent a users customized layout. The portlet layout engine is used
 * by the {@link org.gridsphere.servlets.GridSphereServlet}
 * Expect the PortletLayoutEngine methods to change possibly....
 */
public class PortletLayoutEngine {

    protected Log log = LogFactory.getLog(PortletLayoutEngine.class);
    private static PortletLayoutEngine instance = new PortletLayoutEngine();

    private PortletPageFactory pageFactory = PortletPageFactory.getInstance();

    private PortletFrameRegistry registry = PortletFrameRegistry.getInstance();

    private boolean inited = false;

    public static final String DEFAULT_THEME = "default";
    public static final String DEFAULT_RENDERKIT = "brush";

    /**
     * Constructs a concrete instance of the PortletLayoutEngine
     */
    private PortletLayoutEngine() {
    }

    public void init(ServletContext ctx) {
        if (!inited) {
            pageFactory.init(ctx);
            inited = true;
        }
    }

    /**
     * Returns the single instance of the PortletLayoutEngine
     *
     * @return the PortletLayoutEngine instance
     */
    public static PortletLayoutEngine getInstance() {
        return instance;
    }

    public PortletPage getPortletPage(GridSphereEvent event) {

        PortletRequest req = event.getRenderRequest();

        // Check for framework errors
        Exception portletException = (Exception) req.getAttribute(SportletProperties.ERROR);
        if (portletException != null) {
            PortletPage errorPage = pageFactory.createErrorPage();
            errorPage.init(req, new ArrayList<ComponentIdentifier>());
            return errorPage;
        }

        return pageFactory.getPortletPage(event);
    }

    public void setHeaders(GridSphereEvent event) {
        HttpServletRequest req = event.getHttpServletRequest();
        HttpServletResponse res = event.getHttpServletResponse();
        res.setContentType("text/html; charset=utf-8"); // Necessary to display UTF-8 encoded characters
        res.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
        res.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
        res.setHeader("Expires", "0"); //Causes the proxy cache to see the page as "stale"
        res.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
        String ae = req.getHeader("accept-encoding");
        if (ae != null && ae.indexOf("gzip") != -1) {
            res.setHeader("Content-Encoding", "gzip");
        }
    }

    /**
     * Services a portlet container instance by rendering its presentation
     *
     * @param event the gridsphere event
     */
    public void service(GridSphereEvent event) {
        HttpServletRequest req = event.getHttpServletRequest();
        HttpServletResponse res = event.getHttpServletResponse();
        // set content to UTF-8 for il8n and compression if supported
        try {
            req.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            log.error("UTF-8 is unsupported?!?", e);
        }
        PortletPage page = getPortletPage(event);
        setHeaders(event);
        StringBuffer pageBuffer = new StringBuffer();
        if (req.getParameter("ajax") != null) {


            String portlet = req.getParameter("portlet");
            System.err.println("it's ajax: " + portlet);
            String cid = event.getComponentID();
            if ((cid != null) && (cid.startsWith("portlet"))) {
                portlet = cid.substring("portlet".length() + 1);
            }
            if (portlet != null) {
                PortletFrameRegistry registry = PortletFrameRegistry.getInstance();
                int idx = portlet.indexOf("#");
                String portletName = portlet.substring(idx + 1, portlet.length());
                PortletFrame frame = registry.getPortletFrame(portletName, portlet, event);
                frame.setInnerPadding("");
                frame.setOuterPadding("");
                frame.setTransparent(false);

                req.getSession().setAttribute(SportletProperties.LAYOUT_THEME, DEFAULT_THEME);
                req.getSession().setAttribute(SportletProperties.LAYOUT_RENDERKIT, DEFAULT_RENDERKIT);

                req.setAttribute(SportletProperties.USE_AJAX, "true");
                req.setAttribute(SportletProperties.PORTLET_NAME, portlet);
                String compName = req.getParameter("compname");

                System.err.println("compname= " + compName);

                req.setAttribute(SportletProperties.COMPONENT_NAME, compName);

                if (event.hasAction()) {
                    frame.actionPerformed(event);
                }
                frame.doRender(event);
                pageBuffer = frame.getBufferedOutput(event.getRenderRequest());
                res.setContentType("text/html");
            } else {
                PortletComponent comp = page.getActiveComponent(cid);
                if (comp != null) {
                    String reqRole = comp.getRequiredRole();
                    Principal user = event.getRenderRequest().getUserPrincipal();
                    if (user != null) {
                        if (req.isUserInRole(reqRole)) comp.doRender(event);
                    } else {
                        if (reqRole.equals("")) comp.doRender(event);
                    }
                    pageBuffer = comp.getBufferedOutput(event.getRenderRequest());
                    res.setContentType("text/html");
                }
            }
        } else {
            page.doRender(event);
            pageBuffer = page.getBufferedOutput(event.getRenderRequest());
        }

        log.info("\twriting page to output");
        try {
            String ae = req.getHeader("accept-encoding");
            if (ae != null && ae.indexOf("gzip") != -1) {
                GZIPOutputStream gzos = new GZIPOutputStream(res.getOutputStream());
                gzos.write(pageBuffer.toString().getBytes(req.getCharacterEncoding()));
                gzos.close();
            } else {
                PrintWriter out = res.getWriter();
                out.print(pageBuffer.toString());
            }
        } catch (IOException e) {
            // means the writer has already been obtained
            log.error("Error writing page!", e);
        }
    }

    public void doAction(GridSphereEvent event) {
        String cid = event.getComponentID();
        if (!cid.equals("")) {
            PortletFrame frame = registry.getPortletFrame(cid, null, event);
            if (frame != null) {
                try {
                    frame.actionPerformed(event);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Performs an action on the portlet container referenced by the
     * gridsphere event
     *
     * @param event a gridsphere event
     */
    public void actionPerformed(GridSphereEvent event) {
        log.info("\tstart actionPerformed");
        PortletPage page = getPortletPage(event);

        if (!event.getComponentID().equals("")) {
            page.actionPerformed(event);

            // sometimes the page needs reinitializing
            if (event.getActionRequest().getAttribute(SportletProperties.INIT_PAGE) != null) {
                log.info("\n\n\n\n\nreiniting and saving page!!!!!\n\n\n\n\n\n");
                page.init(event.getActionRequest(), new ArrayList<ComponentIdentifier>());
                PortletTabbedPane pane = pageFactory.getUserTabbedPane(event.getActionRequest());
                if (pane != null) {
                    try {
                        pane.save();
                    } catch (IOException e) {
                        log.error("Unable to save tab pane", e);
                    }
                }
            }
        }

        log.info("\tend actionPerformed");
    }

    public void doRenderError(RenderResponse res, Throwable t) {
        PrintWriter out = null;
        try {
            out = res.getWriter();
        } catch (IOException e) {
            log.error("in doRenderError: ", e);
        }
        out.println("<html><body>");
        out.println("<b>An error occurred: " + t.getMessage() + "</b>");
        t.printStackTrace(out);
        out.println("</body></html>");
    }


}
