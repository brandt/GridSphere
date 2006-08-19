/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: InitTag.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.provider.portlet.tags.gs;

import org.gridsphere.portlet.PortletContext;
import org.gridsphere.portlet.PortletRequest;
import org.gridsphere.portlet.PortletResponse;
import org.gridsphere.portlet.impl.SportletContext;
import org.gridsphere.portlet.impl.SportletRequest;
import org.gridsphere.portlet.impl.SportletResponse;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * The <code>InitTag</code> sets the <code>PortletRequest</code>, <code>PortletResponse</code> and
 * <code>PortletContext</code> objects in the request to make them available to other tags.
 */
public class InitTag extends TagSupport {

    public int doStartTag() throws JspException {
        ServletRequest req = pageContext.getRequest();
        PortletRequest portletRequest = null;
        if (req instanceof HttpServletRequest) {
            HttpServletRequest hReq = (HttpServletRequest) req;
            portletRequest = new SportletRequest(hReq);
            pageContext.setAttribute("portletRequest", portletRequest);
        }
        ServletResponse res = pageContext.getResponse();
        if (res instanceof HttpServletResponse) {
            HttpServletResponse hRes = (HttpServletResponse) res;
            PortletResponse portletResponse = new SportletResponse(hRes, portletRequest);
            pageContext.setAttribute("portletResponse", portletResponse);
        }
        PortletContext portletContext = new SportletContext(pageContext.getServletConfig());
        pageContext.setAttribute("portletContext", portletContext);
        return EVAL_BODY_INCLUDE;
    }

}
