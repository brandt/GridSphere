/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.portlet.PortletRequest;

/**
 * An <code>ActionLinkBean</code> is a visual bean that represents a hyperlink containing a portlet action
 */
public class ActionLinkBean extends ActionBean implements TagBean {

    protected String style = "none";

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

    /**
     * Returns the style of the text: Available styles are
     * <ul>
     * <li>nostyle - plain text</li>
     * <li>error - error text</li>
     * <li>info - default info text</li>
     * <li>status - status text</li>
     * <li>alert - alert text</li>
     * <li>success - success text</li>
     *
     * @return the text style
     */
    public String getStyle() {
        return style;
    }

    /**
     * Sets the style of the text: Available styles are
     * <ul>
     * <li>error</li>
     * <li>info</li>
     * <li>status</li>
     * <li>alert</li>
     * <li>success</li>
     *
     * @param style the text style
     */
    public void setStyle(String style) {
        this.style = style;
    }

    public String toStartString() {
        return "";
    }

    public String toEndString() {
        String hlink = "<a href=\"" + action + "\"" + " onClick=\"this.href='" + action + "&JavaScript=enabled'\"/>" + value + "</a>";
        if (style.equals("none")) {
            return hlink;
        } else if (style.equalsIgnoreCase("error") || (style.equalsIgnoreCase("err"))) {
            this.cssStyle = TextBean.MSG_ERROR;
        } else if (style.equalsIgnoreCase("status")) {
            this.cssStyle = TextBean.MSG_STATUS;
        } else if (style.equalsIgnoreCase("info")) {
            this.cssStyle = TextBean.MSG_INFO;
        } else if (style.equalsIgnoreCase("alert")) {
            this.cssStyle = TextBean.MSG_ALERT;
        } else if (style.equalsIgnoreCase("success")) {
            this.cssStyle = TextBean.MSG_SUCCESS;
        } else if (style.equalsIgnoreCase(TextBean.MSG_BOLD)) {
            return "<b>" + hlink + "</b>";
        } else if (style.equalsIgnoreCase(TextBean.MSG_ITALIC)) {
            return "<i>" + hlink + "</i>";
        } else if (style.equalsIgnoreCase(TextBean.MSG_UNDERLINE)) {
            return "<u>" + hlink + "</u>";
        }
        return "<a href=\"" + action + "\"" + " class=" + cssStyle + " onClick=\"this.href='" + action + "&JavaScript=enabled'\"/>" + value + "</a>";
    }

}
