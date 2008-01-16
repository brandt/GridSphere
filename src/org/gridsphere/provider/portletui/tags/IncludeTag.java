package org.gridsphere.provider.portletui.tags;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gridsphere.portlet.impl.StoredPortletResponseImpl;
import org.gridsphere.provider.portletui.beans.IncludeBean;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 * <p>
 * Includes jsp pages from any web application.
 */

public class IncludeTag extends BaseBeanTag {

    private Log log = LogFactory.getLog(IncludeTag.class);
    protected IncludeBean includeBean = null;
    protected ServletContext servletContext = null;
    protected String page = null;

    public ServletContext getServletContext() {
        return servletContext;
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public int doStartTag() throws JspException {
        if (beanId.equals("")) {
            // If no bean id, create new bean
            includeBean = new IncludeBean();
            if (servletContext == null) {
                // If no servlet context provided, then provide this context
                servletContext = pageContext.getServletContext();
            }
        } else {
            // Else get bean with bean id
            includeBean = (IncludeBean) getTagBean();
            if (includeBean == null) {
                // If no bean with given id, exit early
                return SKIP_BODY;
            }
            if (includeBean.getServletContext() == null) {
                // If no servlet context provided, then provide this context
                servletContext = pageContext.getServletContext();
            } else {
                log.debug("Using include bean context");
                servletContext = includeBean.getServletContext();
            }
            page = includeBean.getPage();
        }
        // Set portlet request and response attributes
        //includeBean.setJspWriter(pageContext.getOut());

        includePage();
        return SKIP_BODY;
    }

    protected void includePage() {
        RequestDispatcher rd = servletContext.getRequestDispatcher(page);
        try {
            ServletRequest request = pageContext.getRequest();
            ServletResponse response = pageContext.getResponse();
            // Very important here... must pass it the appropriate jsp writer!!!
            // Or else this include won't be contained within the parent content
            // but either before or after it.
            //rd.include(request, new ServletResponseWrapperInclude(response, pageContext.getOut()));
            rd.include(request, new StoredPortletResponseImpl((HttpServletRequest) request, (HttpServletResponse) response, pageContext.getOut()));
            //rd.include(pageContext.getRequest(), pageContext.getResponse());
        } catch (Exception e) {
            log.error("Unable to include page ", e);
        }
    }
}
