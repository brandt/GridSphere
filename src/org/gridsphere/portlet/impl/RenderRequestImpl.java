/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
package org.gridsphere.portlet.impl;

import javax.portlet.ActionRequest;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.servlet.http.HttpServletRequest;


/**
 * The <CODE>RenderRequest</CODE> represents the request sent to the portlet
 * to handle a render.
 * It extends the PortletRequest interface to provide render request
 * information to portlets.<br>
 * The portlet container creates a <CODE>RenderRequest</CODE> object and
 * passes it as argument to the portlet's <CODE>render</CODE> method.
 * 
 * @see PortletRequest
 * @see ActionRequest
 */
public class RenderRequestImpl extends PortletRequestImpl implements RenderRequest {

    /**
     * Constructor creates a proxy for a HttpServletRequest
     * All PortletRequest objects come from request or session attributes
     *
     * @param req the HttpServletRequest
     */
    public RenderRequestImpl(HttpServletRequest req, PortletContext portletContext) {
        super(req, portletContext);
    }


}