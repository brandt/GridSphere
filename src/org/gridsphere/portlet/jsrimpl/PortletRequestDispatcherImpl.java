/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: PortletRequestDispatcherImpl.java 4894 2006-06-28 22:57:23Z novotny $
 */
package org.gridsphere.portlet.jsrimpl;

import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.RequestDispatcher;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;


/**
 * The <code>PortletRequestDispatcher</code> interface
 * defines an object that receives requests from the client
 * and sends them to the specified resources (such as a servlet,
 * HTML file, or JSP file) on the server. The portlet
 * container creates the <code>PortletRequestDispatcher</code> object,
 * which is used as a wrapper around a server resource located
 * at a particular path or given by a particular name.
 */

public class PortletRequestDispatcherImpl implements PortletRequestDispatcher {

    protected RequestDispatcher rd = null;
    protected String path = null;

    private PortletRequestDispatcherImpl() {
    }

    public PortletRequestDispatcherImpl(RequestDispatcher rd) {
        this.rd = rd;
    }

    public PortletRequestDispatcherImpl(RequestDispatcher rd, String path) {
        this.rd = rd;
        this.path = path;
    }

    /**
     * Includes the content of a resource (servlet, JSP page,
     * HTML file) in the response. In essence, this method enables
     * programmatic server-side includes.
     * <p/>
     * The included servlet cannot set or change the response status code
     * or set headers; any attempt to make a change is ignored.
     *
     * @param request  a {@link RenderRequest} object
     *                 that contains the client request
     * @param response a {@link RenderResponse} object
     *                 that contains the render response
     * @throws PortletException    if the included resource throws a ServletException,
     *                             or other exceptions that are not Runtime-
     *                             or IOExceptions.
     * @throws java.io.IOException if the included resource throws this exception
     */
    public void include(RenderRequest request, RenderResponse response)
            throws PortletException, java.io.IOException {
        if ((request instanceof PortletRequestImpl) && (response instanceof PortletResponseImpl)) {
            try {
                ((PortletRequestImpl) request).setIncluded(true);
                if (path != null) {
                    Map params = parseQueryParams(path);
                    if (!params.isEmpty()) {
                        ((PortletRequestImpl) request).addRenderParams(params);
                    }
                }
                rd.include((PortletRequestImpl) request, (PortletResponseImpl) response);
            } catch (java.io.IOException e) {
                throw e;
            } catch (javax.servlet.ServletException e) {
                if (e.getRootCause() != null) {
                    throw new PortletException(e.getRootCause());
                } else {
                    throw new PortletException(e);
                }
            } finally {
                ((PortletRequestImpl) request).setIncluded(false);
            }
        }
    }

    /**
     * Returns a map containing any query parameters contained in the path to obtain a request dispatcher
     * @param path
     * @return a map containing any query parameters contained in the path
     */
    private Map parseQueryParams(String path) {
        Map map = new HashMap();
        int idx = path.indexOf("?");
        if (idx < 0) return map;
        String parms = path.substring(idx+1);
        StringTokenizer st = new StringTokenizer(parms, "&");
        while(st.hasMoreTokens()) {
            String pair = st.nextToken();
            if (pair.indexOf("=") > 0) {
                String key = pair.substring(0, pair.indexOf("="));
                String val = pair.substring(pair.indexOf("=")+1);
                map.put(key, new String[] {val});
            }
        }
        return map;
    }
}








