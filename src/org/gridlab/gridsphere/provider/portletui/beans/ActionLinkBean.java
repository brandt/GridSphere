/**
 * @author <a href="novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.portlet.PortletRequest;

/**
 * An <code>ActionLinkBean</code> is a visual bean that represents a hyperlink containing a portlet action
 */
public class ActionLinkBean extends ActionBean implements TagBean {

    /**
     * Constructs a default action link bean
     */
    public ActionLinkBean() {
    }

    /**
     * Constructs an action link bean from a portlet request and supplied bean identifier
     */
    public ActionLinkBean(PortletRequest req, String beanId) {
        this.request = req;
        this.beanId = beanId;
    }

    public String toStartString() {
        return "";
    }

    public String toEndString() {
        return "<a href=\"" + action + "\"" + " onClick=\"this.href='" + action + "&JavaScript=enabled'\"/>" + value + "</a>";
    }

}
