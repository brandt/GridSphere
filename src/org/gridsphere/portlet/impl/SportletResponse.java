/*
* @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
* @version $Id: SportletResponse.java 5032 2006-08-17 18:15:06Z novotny $
*/
package org.gridsphere.portlet.impl;

import org.gridsphere.portlet.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.Cookie;

/**
 * A <code>SportletResponse</code> provides an implementation of the
 * <code>PortletResponse</code> by following the decorator patterm.
 * A HttpServletresponse object is used in composition to perform most of
 * the required methods.
 */
public class SportletResponse extends HttpServletResponseWrapper implements PortletResponse {

    private HttpServletRequest req = null;

    public SportletResponse(HttpServletResponse res) {
        super(res);
    }

    /**
     * Constructs an instance of SportletResponse using an
     * <code>HttpServletResponse</code> and a <code>PortletRequest</code>
     *
     * @param res the <code>HttpServletRequest</code>
     * @param req the <code>PortletRequest</code>
     */
    public SportletResponse(HttpServletResponse res, PortletRequest req) {
        super(res);
        this.req = req;
    }

    /**
     * Creates a portlet URI pointing at the referrer of the portlet.
     *
     * @return the portletURI
     */
    public PortletURI createReturnURI() {
        SportletURI sportletURI = new SportletURI(req, this.getHttpServletResponse());
        addURIParameters(sportletURI);
        sportletURI.setReturn(true);
        return sportletURI;
    }


    /**
     * Creates a portlet URI pointing to the current portlet mode.
     *
     * @return the portlet URI
     */
    public PortletURI createURI() {
        SportletURI sportletURI = new SportletURI(req, this.getHttpServletResponse());
        addURIParameters(sportletURI);
        sportletURI.setReturn(false);
        return sportletURI;
    }

    /**
     * Creates a portlet URI pointing to the current portlet mode with the specified security.
     *
     * @return the portlet URI
     */
    public PortletURI createURI(boolean isSecure) {
        SportletURI sportletURI = new SportletURI(req, this.getHttpServletResponse(), isSecure);
        addURIParameters(sportletURI);
        sportletURI.setReturn(false);
        return sportletURI;
    }

    /**
     * Creates a portlet URI pointing to another portal component with the specified security setting
     *
     * @return the portlet component label
     */
    public PortletURI createURI(String componentLabel, boolean isSecure) {
        SportletURI sportletURI = new SportletURI(req, this.getHttpServletResponse(), isSecure);
        String compVar = (String)req.getAttribute(SportletProperties.COMPONENT_ID_VAR);
        if (compVar == null) compVar = SportletProperties.COMPONENT_ID;
        sportletURI.addParameter(compVar, componentLabel);
        sportletURI.setReturn(false);
        return sportletURI;
    }

    /**
     * Creates a portlet URI pointing to the given portlet mode and current
     * portlet window state.
     *
     * @param mode the portlet mode
     */
    public PortletURI createURI(Mode mode) {
        SportletURI sportletURI = new SportletURI(req, this.getHttpServletResponse());
        addURIParameters(sportletURI);
        sportletURI.setPortletMode(mode);
        return sportletURI;
    }

    /**
     * Creates a portlet URI pointing to the current portlet mode and given portlet window state.
     *
     * @param state the portlet window state
     * @see #addURIParameters
     */
    public PortletURI createURI(PortletWindow.State state) {
        SportletURI sportletURI = new SportletURI(req, this.getHttpServletResponse());
        addURIParameters(sportletURI);
        sportletURI.setWindowState(state);
        return sportletURI;
    }

    /**
     * Add any additional parameters to the URI:
     * <ul><li>
     * SportletProperties.COMPONENT_ID
     * </li></ul>
     */
    protected void addURIParameters(PortletURI sportletURI) {
        String compVar = (String)req.getAttribute(SportletProperties.COMPONENT_ID_VAR);
        if (compVar == null) compVar = SportletProperties.COMPONENT_ID;
        String cid = (String)req.getAttribute(compVar);
        if (cid != null) sportletURI.addParameter(compVar, cid );
    }

    /**
     * Maps the given string value into this portlet's namespace.
     *
     * @param aValue the string value
     */
    public String encodeNamespace(String aValue) {
        return aValue + "_" + (String) req.getAttribute(SportletProperties.COMPONENT_ID);
    }

    public void setContentType(String type) {
        this.getHttpServletResponse().setContentType(type);
    }

    /*
    public String getContentType() {
        return this.getHttpServletResponse().getContentType();
    }
    */

    public void addCookie(Cookie cookie) {
        this.getHttpServletResponse().addCookie(cookie);
    }
    
    private HttpServletResponse getHttpServletResponse() {
        return (HttpServletResponse) super.getResponse();
    }
}

