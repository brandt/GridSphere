/*
 * Created by IntelliJ IDEA.
 * User: novotny
 * Date: Jan 13, 2003
 * Time: 2:38:24 AM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.tags.portlet;

import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.PortletContext;
import org.gridlab.gridsphere.portlet.impl.SportletRequestImpl;
import org.gridlab.gridsphere.portlet.impl.SportletResponse;
import org.gridlab.gridsphere.portlet.impl.SportletContext;
import org.gridlab.gridsphere.portlet.impl.SportletProperties;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InitTag extends TagSupport {

    public int doStartTag() throws JspException {
        ServletRequest req = pageContext.getRequest();
        PortletRequest portletRequest = null;
        if (req instanceof HttpServletRequest) {
            HttpServletRequest hReq = (HttpServletRequest)req;
            portletRequest = new SportletRequestImpl(hReq);
            pageContext.setAttribute("portletRequest", portletRequest);
        }
        ServletResponse res = pageContext.getResponse();
        if (res instanceof HttpServletResponse) {
            HttpServletResponse hRes = (HttpServletResponse)res;
            PortletResponse portletResponse = new SportletResponse(hRes, portletRequest);
            pageContext.setAttribute("portletResponse", portletResponse);
        }
        PortletContext portletContext = new SportletContext(pageContext.getServletConfig());
        pageContext.setAttribute("portletContext", portletContext);
        return EVAL_BODY_INCLUDE;
    }

}
