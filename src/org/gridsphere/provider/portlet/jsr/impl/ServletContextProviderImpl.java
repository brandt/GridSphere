package org.gridsphere.provider.portlet.jsr.impl;

import org.apache.portals.bridges.common.ServletContextProvider;
import org.gridsphere.portlet.impl.PortletContextImpl;

import javax.portlet.GenericPortlet;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * ServletContextProvider
 * 
 * @author <a href="mailto:novotny@gridsphere.org>Jason Novotny</a>
 * @version $Id$
 */
public class ServletContextProviderImpl implements ServletContextProvider {

    public ServletContext getServletContext(GenericPortlet portlet) {
        PortletContextImpl ctx = (PortletContextImpl)(portlet.getPortletContext());
        return ctx.getServletContext();
    }

    public HttpServletRequest getHttpServletRequest(GenericPortlet portlet, PortletRequest request) {
        return (HttpServletRequest) ((HttpServletRequestWrapper) request).getRequest();
    }

    public HttpServletResponse getHttpServletResponse(GenericPortlet portlet, PortletResponse response) {
        return (HttpServletResponse) ((HttpServletResponseWrapper) response).getResponse();
    }

}
