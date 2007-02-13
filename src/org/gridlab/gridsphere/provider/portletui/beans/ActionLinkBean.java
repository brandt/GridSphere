/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.services.core.tracker.TrackerService;

import java.net.URLEncoder;

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
     * 
     * @param beanId the bean id
     */
    public ActionLinkBean(String beanId) {
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
        // now do the string rendering
        action = this.portletURI.toString();

        if (anchor != null) action += "#" + anchor;

        //String hlink = "<a href=\"" + action + "\"" + " onClick=\"this.href='" + action + "&amp;JavaScript=enabled'\"/>" + value + "</a>";
        if (style.equalsIgnoreCase("error") || (style.equalsIgnoreCase("err"))) {
            this.cssClass = MessageStyle.MSG_ERROR;
        } else if (style.equalsIgnoreCase("status")) {
            this.cssClass = MessageStyle.MSG_STATUS;
        } else if (style.equalsIgnoreCase("info")) {
            this.cssClass = MessageStyle.MSG_INFO;
        } else if (style.equalsIgnoreCase("alert")) {
            this.cssClass = MessageStyle.MSG_ALERT;
        } else if (style.equalsIgnoreCase("success")) {
            this.cssClass = MessageStyle.MSG_SUCCESS;
        } else if (style.equalsIgnoreCase(MessageStyle.MSG_BOLD)) {
            this.addCssStyle("font-weight: bold;");
        } else if (style.equalsIgnoreCase(MessageStyle.MSG_ITALIC)) {
            this.addCssStyle("font-weight: italic;");
        } else if (style.equalsIgnoreCase(MessageStyle.MSG_UNDERLINE)) {
            this.addCssStyle("font-weight: underline;");
        }
        StringBuffer sb = new StringBuffer();
        sb.append("<a");
        if (id != null) sb.append("id=\"").append(id).append("\" ");
        if (name != null) sb.append(" name=\"").append(name).append("\"");
        if (trackMe != null) {
            try {
                if (extUrl != null) {
                    sb.append(" href=\"" + "?" + TrackerService.TRACK_PARAM + "=").append(trackMe).append("&amp;" + TrackerService.REDIRECT_URL + "=").append(URLEncoder.encode(extUrl, "UTF-8")).append("\"").append(getFormattedCss()).append("\">").append(value);
                } else {
                    sb.append(" href=\"" + "?" + TrackerService.TRACK_PARAM + "=").append(trackMe).append("&amp;" + TrackerService.REDIRECT_URL + "=").append(URLEncoder.encode(action, "UTF-8")).append("\"").append(getFormattedCss()).append("\">").append(value);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (useAjax) action = "#";
            sb.append(" href=\"").append(action).append("\"");
        }
        sb.append(getFormattedCss());
        if (onClick != null) sb.append(" onclick=\"").append(onClick).append("\"");
        sb.append(">").append(value).append("</a>");
        return sb.toString();
    }

}