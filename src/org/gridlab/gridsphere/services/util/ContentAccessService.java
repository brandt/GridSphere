/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.util;

import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.PortletRequest;

import java.net.MalformedURLException;
import java.net.URL;
import java.io.InputStream;

/**
 * Service to access external and internal resources.
 *
 * Example usage:
 *
 * ContentAccessService service = (ContentAccessService)portletContext.getService(ContentAccessService.class);
 * // get a url
 * Url url = service.getURL("http://www.ibm.com", request, response);
 * // include the content of a website into a portlet
 * service.include("http://www.ibm.com", request, response);
 * // include a servlet or JSP belonging to a web application
 * service.include("template/weather.jsp", request, response);
 *
 */
public interface ContentAccessService extends PortletService {

    public InputStream getInputStream(String urlString, PortletRequest req, PortletResponse resp)
            throws PortletServiceException, MalformedURLException;

    /**
     *
     */
    public String getMarkup(String urlString, PortletRequest req, PortletResponse resp)
            throws PortletServiceException, MalformedURLException;

    /**
     * Returns an URL object after following redirects using a proxy if necessary
     */
    public URL getURL(String urlString, PortletRequest request, PortletResponse response)
            throws PortletServiceException, MalformedURLException;

    /**
     * Writes the content of the given url to the output stream of the portlet following redirects
     * and using a proxy if necessary trying to use the RequestDispatcher to include local servlets or JSPs
     *
     * @param urlString
     * @param request
     * @param response
     * @throws PortletServiceException
     * @throws MalformedIOException
     */
    public void include(String urlString, PortletRequest request, PortletResponse response)
            throws PortletServiceException, MalformedURLException;



}
