/*
 * Copyright 2000-2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gridlab.gridsphere.provider.portlet.jsr.impl;

import org.gridlab.gridsphere.portlet.jsrimpl.PortletContextImpl;

import javax.portlet.GenericPortlet;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.portals.bridges.common.ServletContextProvider;

/**
 * ServletContextProvider
 * 
 * @author <a href="mailto:novotny@aei.mpg.de>Jason Novotny</a>
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
