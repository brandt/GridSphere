package org.gridlab.gridsphere.provider.portletui.tags;

import org.gridlab.gridsphere.provider.portletui.beans.IncludeBean;
import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.ServletContext;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 * <p>
 * Includes jsp pages from any web application.
 */

public class IncludeTag extends BaseBeanTag {

    private static PortletLog log = SportletLog.getInstance(IncludeTag.class);
    private IncludeBean includeBean = null;
    private ServletContext servletContext = null;
    private String page = null;

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
            includeBean.setPortletRequest((PortletRequest)pageContext.getAttribute("portletRequest"));
            includeBean.setPortletResponse((PortletResponse)pageContext.getAttribute("portletResponse"));
            if (servletContext == null) {
                // If no servlet context provided, then provide this context
                includeBean.setServletContext(pageContext.getServletContext());
            } else {
                includeBean.setServletContext(servletContext);
            }
            includeBean.setPage(page);
        } else {
            // Else get bean with bean id
            includeBean = (IncludeBean) pageContext.getAttribute(getBeanKey(), PageContext.REQUEST_SCOPE);
            if (includeBean == null) {
                // If no bean with given id, exit early
                return SKIP_BODY;
            }
            if (includeBean.getServletContext() == null) {
                // If no servlet context provided, then provide this context
                includeBean.setServletContext(pageContext.getServletContext());
            }
        }
        // Set portlet request and response attributes
        includeBean.setJspWriter(pageContext.getOut());
        includeBean.includePage();
        return SKIP_BODY;
    }
}
