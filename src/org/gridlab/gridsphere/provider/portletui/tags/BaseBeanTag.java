/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.tags;

import org.gridlab.gridsphere.portlet.impl.SportletProperties;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.PortletLog;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.ServletRequest;
import java.util.Enumeration;

/**
 * The abstract <code>BaseBeanTag</code> is a base class used by all UI tags that provides support for
 * a bean identifier and a flag for indicating whether JavaScript is enabled.
 */
public abstract class BaseBeanTag extends BodyTagSupport {

    private transient static PortletLog log = SportletLog.getInstance(BaseBeanTag.class);

    protected String beanId = "";

    /**
     * Returns the bean identifier
     *
     * @return the bean identifier
     */
    public String getBeanId() {
        return beanId;
    }

    /**
     * Sets the bean identifier
     *
     * @param beanId the bean identifier
     */
    public void setBeanId(String beanId) {
        this.beanId = beanId;
    }

    /**
     * Returns the unique bean key
     *
     * @return the unique bean key
     */
    protected String getBeanKey() {
        String cid = (String) pageContext.findAttribute(SportletProperties.COMPONENT_ID);
        ServletRequest request = pageContext.getRequest();
        String compId = (String)request.getAttribute("compId");
        String beanKey = null;
        if (compId == null) {
            beanKey = beanId + "_" + cid;
        } else {
            beanKey = compId + "%" + beanId + "_" + cid;
        }
        //log.debug("getBeanKey(" + beanId + ") = " + beanKey);
        return beanKey;
    }

    /**
     * Indicates whether this tag supports JavaScript or not
     *
     * @return true if JavaScript is supported, false otherwise
     */
    protected boolean supportsJavaScript() {
        //System.err.println("in supportsJavaScript");
        String isEnabled = pageContext.getRequest().getParameter("JavaScript");
        //String isEnabled = (String)pageContext.getAttribute("JavaScript", PageContext.REQUEST_SCOPE);
        if (isEnabled != null) {
            return ((isEnabled.equals("enabled")) ? true : false);
        } else {
            return false;
        }
    }

    /**
     * Prints out all request attributes. Used for debugging
     */
    public void debug() {
        Enumeration enum = pageContext.getAttributeNamesInScope(PageContext.REQUEST_SCOPE);
        System.err.println("Printing attribues in request scope");
        while (enum.hasMoreElements()) {
            System.err.println((String) enum.nextElement());
        }
        enum = pageContext.getAttributeNamesInScope(PageContext.SESSION_SCOPE);
        System.err.println("Printing attribues in session scope");
        while (enum.hasMoreElements()) {
            System.err.println((String) enum.nextElement());
        }
        enum = pageContext.getAttributeNamesInScope(PageContext.PAGE_SCOPE);
        System.err.println("Printing attribues in page scope");
        while (enum.hasMoreElements()) {
            System.err.println((String) enum.nextElement());
        }
        enum = pageContext.getAttributeNamesInScope(PageContext.APPLICATION_SCOPE);
        System.err.println("Printing attribues in application scope");
        while (enum.hasMoreElements()) {
            System.err.println((String) enum.nextElement());
        }
    }

    protected String getUniqueId(String varName) {
        String uniqueId = (String) pageContext.getAttribute(varName, PageContext.REQUEST_SCOPE);

        // use a counter to continually increase form number to provide unique form name
        int ctr = 0;
        if (uniqueId == null) {
            ctr = 1;
        } else {
            ctr = Integer.parseInt(uniqueId) + 1;
        }
        uniqueId = String.valueOf(ctr);
        pageContext.setAttribute(varName, uniqueId, PageContext.REQUEST_SCOPE);
        return uniqueId;
    }

    protected boolean isJSR() {
        // simply check for existence of jsr portlet objects
        //Object o = pageContext.getRequest().getAttribute(SportletProperties.RENDER_REQUEST);
        Object o = pageContext.getAttribute("renderRequest");
        return (o == null) ? false : true;
    }
}
