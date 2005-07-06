/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.services.core.tracker.TrackerService;

import javax.servlet.http.HttpServletRequest;
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

    public ActionLinkBean(HttpServletRequest req) {
        this.request = req;
    }

    /**
     * Constructs an action link bean from a portlet request and supplied bean identifier
     */
    public ActionLinkBean(String beanId) {
        this.beanId = beanId;
    }

    /**
     * Constructs an action link bean from a portlet request and supplied bean identifier
     */
    public ActionLinkBean(HttpServletRequest req, String beanId) {
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

        // now if we added some params which should be actions....
        /*
        if (paramBeanList != null) {
            Iterator it = paramBeanList.iterator();
            while (it.hasNext()) {
                ActionParamBean apBean = (ActionParamBean) it.next();
                this.portletURI.addParameter(apBean.getName(), apBean.getValue());
            }
        }
        */
        // now do the string rendering
        action = this.portletURI.toString();

        if (anchor != null) action += "#" + anchor;

        //String hlink = "<a href=\"" + action + "\"" + " onClick=\"this.href='" + action + "&JavaScript=enabled'\"/>" + value + "</a>";
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
        if (name != null) sb.append(" name=\"" + name + "\"");
        if (trackMe != null) {
            try {
                if (extUrl != null) {
                    sb.append(" href=\"" + "?" + TrackerService.TRACK_PARAM + "=" + trackMe + "&" + TrackerService.REDIRECT_URL + "=" + URLEncoder.encode(extUrl, "UTF-8") + "\"" + getFormattedCss() + "\">" + value);
                } else {
                    sb.append(" href=\"" + "?" + TrackerService.TRACK_PARAM + "=" + trackMe + "&" + TrackerService.REDIRECT_URL + "=" + URLEncoder.encode(action, "UTF-8") + "\"" + getFormattedCss() + "\">" + value);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            sb.append(" href=\"" + action + "\"" + getFormattedCss() + " onClick=\"this.href='" + action + "&JavaScript=enabled'\">" + value);
        }
        sb.append("</a>");
        return sb.toString();
    }

}