/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
package org.gridsphere.provider.portlet.tags.jsr;

import org.gridsphere.portlet.impl.SportletProperties;

import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.tagext.VariableInfo;

/**
 * The <code>DefineObjectsTag</code> sets the <code>RenderRequest</code>, <code>RenderResponse</code> and
 * <code>PortletConfig</code> objects in the request to make them available to other tags.
 */
public class DefineObjectsTag extends TagSupport {

    public static class TEI extends TagExtraInfo {

        public VariableInfo[] getVariableInfo(TagData tagData) {
            return new VariableInfo[]{
                new VariableInfo("renderRequest",
                        "javax.portlet.RenderRequest",
                        true,
                        VariableInfo.AT_BEGIN),
                new VariableInfo("renderResponse",
                        "javax.portlet.RenderResponse",
                        true,
                        VariableInfo.AT_BEGIN),
                new VariableInfo("portletConfig",
                        "javax.portlet.PortletConfig",
                        true,
                        VariableInfo.AT_BEGIN)
            };
        }
    }

    public int doStartTag() throws JspException {
        ServletRequest req = pageContext.getRequest();
        RenderRequest renderRequest = null;
        HttpServletRequest hReq = null;
        if (req instanceof HttpServletRequest) {
            hReq = (HttpServletRequest) req;
            renderRequest = (RenderRequest) hReq.getAttribute(SportletProperties.RENDER_REQUEST);
            pageContext.setAttribute("renderRequest", renderRequest);
        }
        ServletResponse res = pageContext.getResponse();
        if (res instanceof HttpServletResponse) {
            RenderResponse renderResponse = (RenderResponse) hReq.getAttribute(SportletProperties.RENDER_RESPONSE);
            renderResponse.setContentType("text/html");
            pageContext.setAttribute("renderResponse", renderResponse);
        }
        PortletConfig portletConfig = (PortletConfig) hReq.getAttribute(SportletProperties.PORTLET_CONFIG);
        pageContext.setAttribute("portletConfig", portletConfig);

        return SKIP_BODY;
    }

}
