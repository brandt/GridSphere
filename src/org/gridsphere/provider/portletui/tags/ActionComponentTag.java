package org.gridsphere.provider.portletui.tags;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gridsphere.portlet.jsrimpl.SportletProperties;
import org.gridsphere.portlet.jsrimpl.StoredPortletResponseImpl;
import org.gridsphere.provider.portletui.beans.ActionComponentBean;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: ActionComponentTag.java 4666 2006-03-27 17:47:56Z novotny $
 * <p>
 * Includes jsp pages from any web application.
 */

public class ActionComponentTag extends IncludeTag {

    private static Log log = LogFactory.getLog(ActionComponentTag.class);
    private String activeCompId = "";

    protected String getActiveComponentId() {
        return activeCompId;
    }

    protected void setActionComponentId(String compId) {
        this.activeCompId = compId;
    }

    protected void includePage() {
        //log.debug("includePage(" + page + ")");

        RequestDispatcher rd = servletContext.getRequestDispatcher(page);
        ServletRequest request = pageContext.getRequest();
        ServletResponse response = pageContext.getResponse();

        String baseCompId = (String)pageContext.findAttribute(SportletProperties.GP_COMPONENT_ID);

        if (includeBean != null) {
            //log.debug("Using active component id ");
            activeCompId = ((ActionComponentBean) includeBean).getActiveComponentId();
        } else {
            //log.debug("Using request component id ");
            activeCompId = (String)pageContext.findAttribute(SportletProperties.GP_COMPONENT_ID);
        }

        //log.debug("Changing component id from " + baseCompId + " to " + activeCompId);
        pageContext.setAttribute(SportletProperties.GP_COMPONENT_ID, activeCompId, PageContext.REQUEST_SCOPE);

        // Include message box tag automagically
        MessageBoxTag messageBoxTag = new MessageBoxTag();
        messageBoxTag.setBeanId("messageBox");
        messageBoxTag.setPageContext(pageContext);
        try {
            messageBoxTag.doEndTag();
        } catch (Exception e) {
            log.error("Unable to include message box bean ", e);
        }

        try {

            // Very important here... must pass it the appropriate jsp writer!!!
            // Or else this include won't be contained within the parent content
            // but either before or after it.
            //rd.include(request, new ServletResponseWrapperInclude(response, pageContext.getOut()));
            rd.include(request, new StoredPortletResponseImpl((HttpServletRequest)request, (HttpServletResponse) response, pageContext.getOut()));
            //rd.include(pageContext.getRequest(), pageContext.getResponse());
        } catch (Exception e) {
            log.error("Unable to include page ", e);
        }
        //log.debug("Resetting component id to " + baseCompId);
        pageContext.setAttribute(SportletProperties.GP_COMPONENT_ID, baseCompId, PageContext.REQUEST_SCOPE);
    }
}
