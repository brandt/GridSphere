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
import org.gridlab.gridsphere.portlet.impl.SportletRequestImpl;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

public class InitTag extends TagSupport {

    public int doStartTag() throws JspException {
        ServletRequest req = pageContext.getRequest();
        if (req instanceof HttpServletRequest) {
            HttpServletRequest hReq = (HttpServletRequest)req;
            PortletRequest portletRequest = new SportletRequestImpl(hReq);
            pageContext.setAttribute("portletRequest", portletRequest);
        }
        return EVAL_BODY_INCLUDE;
    }

}
