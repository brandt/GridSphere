package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.portlet.PortletResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 * <p>
 * Includes jsp pages from any web application.
 */

public class IncludeBean extends BaseBean implements TagBean {

    private ServletContext servletContext = null;
    private JspWriter jspWriter = null;
    private String page = null;

    /**
     * Constructs default include bean
     */
    public IncludeBean() {
        super();
    }

    /**
     * Constructs an include bean
     */
    public IncludeBean(String beanId) {
        super(beanId);
    }

    /**
     * Constructs an include bean
     */
    public IncludeBean(HttpServletRequest request, String beanId) {
        this.request = request;
        this.beanId = beanId;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public JspWriter getJspWriter() {
        return jspWriter;
    }

    public void setJspWriter(JspWriter jspWriter) {
        this.jspWriter = jspWriter;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String toStartString() {
        return "";
    }

    public String toEndString() {
        return "";
    }
    
    public void store(HttpServletRequest request) {
        this.request = request;
        super.store();
    }
    
}
